 package qq.frame;

import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;

import qq.dao.InformationDao;
import qq.dao.UserDao;
import qq.entity.User;
import qq.util.Message;
import qq.util.MessageType;
import qq.util.SocketUtil;
import qq.util.TalkThread;
import qq.util.ThreadManage;

public class ServerClient extends JFrame{

	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	UserDao userDao=new UserDao();
	InformationDao informationDao=new InformationDao();

	public static void main(String[] args) {
		ServerClient serverClient=new ServerClient();
		serverClient.creatFrame();
	}
	public void creatFrame() {
		JLabel labe=new JLabel("服务器监听在5555端口上",JLabel.CENTER);
		this.add(labe,BorderLayout.CENTER);
		
		this.setTitle("QQ服务器");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(483, 230, 400, 300);
		this.setVisible(true);
		this.setResizable(false);
		
		try {
			this.server();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void server() throws Exception {
		@SuppressWarnings("resource")
		ServerSocket serverSocket=new ServerSocket(5555);//端口为5555
		while(true) {
			Socket socket=serverSocket.accept();
			Message requestMessage=SocketUtil.getSocketUtil().readMessage(socket);
			
			switch(requestMessage.getMessageType()) 
			{
				case MessageType.LOGIN:{
					Message responseMessage=new Message();
					if(userDao.login(requestMessage.getUser().getUserName(), requestMessage.getUser().getUserPassword())!=null) {
						responseMessage.setMessageType(MessageType.LOGIN_SUCCESS);
						
						
						Message refreshFriendListMessage=new Message();
						refreshFriendListMessage.setMessageType(MessageType.REFRESH_FRIEND);
						refreshFriendListMessage.setUser(requestMessage.getUser());
					
						Set<String> keySet=ThreadManage.socketMap.keySet();
						Iterator<String> keys=keySet.iterator();
						while(keys.hasNext()) {
							String key=keys.next();
							Socket friendListSocket=ThreadManage.socketMap.get(key);
							SocketUtil.getSocketUtil().writeMessage(friendListSocket, refreshFriendListMessage);
						}
					}else {
						responseMessage.setMessageType(MessageType.LOGIN_FAILURE);
					}
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					break;
				}
				case MessageType.REGISTER:{
					Message responseMessage=new Message();
					User registerUser=requestMessage.getUser();
					if(userDao.getUserByUserName(registerUser.getUserName())==null) {
						responseMessage.setMessageType(MessageType.REGISTER_SUCCESS);
						userDao.insertUser(registerUser);
						responseMessage.setContent("注册成功！");
					}else {
						responseMessage.setMessageType(MessageType.REGISTER_FAILURE);
						responseMessage.setContent("该账号已经被注册");
					}
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					break;
				}
				case MessageType.GET_USER_LIST:{
					List<User> users=userDao.getUserList();
					Message responseMessage=new Message();
					responseMessage.setUsers(users);
					responseMessage.setMessageType(MessageType.GET_USER_LIST_SUCCESS);
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					ThreadManage.socketMap.put(requestMessage.getUser().getUserName(), socket);
					break;
				}
				case MessageType.CHAT_CONNECTION:{
					TalkThread talkThread=new TalkThread(socket);
					talkThread.start();
					ThreadManage.threadMap.put(requestMessage.getUser().getUserName()+"-"+requestMessage.getFriendUser().getUserName(), talkThread);
					break;
				}
				case MessageType.EXIT:{
					ThreadManage.socketMap.remove(requestMessage.getUser().getUserName());
					userDao.userExit(requestMessage.getUser());
					Message refreshFriendListMessage=new Message();
					refreshFriendListMessage.setMessageType(MessageType.REFRESH_FRIEND_EXIT);
					refreshFriendListMessage.setFriendUser(requestMessage.getUser());
					
					Set<String> keySet=ThreadManage.socketMap.keySet();//遍历告知所有在线的人
					Iterator<String> keys=keySet.iterator();
					while(keys.hasNext()) {
						String key=keys.next();
						Socket friendListSocket=ThreadManage.socketMap.get(key);
						SocketUtil.getSocketUtil().writeMessage(friendListSocket, refreshFriendListMessage);
					}
					break;
				}
				case MessageType.GET_CHAT_LEAVE:{
					Message responseMessage=new Message();
					responseMessage.setInformations(informationDao.getInformationIsOnline(requestMessage.getUser().getUserName(), requestMessage.getFriendUser().getUserName()));
					SocketUtil.getSocketUtil().writeMessage(socket, responseMessage);
					break;
				}
			}
			
		}
	}
}
