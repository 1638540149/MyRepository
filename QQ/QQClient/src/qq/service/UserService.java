package qq.service;

import java.net.Socket;
import java.util.List;

import qq.entity.User;
import qq.frame.MainWindows;
import qq.util.Message;
import qq.util.MessageType;
import qq.util.RefreshFriendListThread;
import qq.util.SocketUtil;
import qq.util.ThreadManage;

public class UserService {

	public boolean login(User user) {
		try {
			Socket socket=new Socket("127.0.0.1",5555);
			Message requestMessage=new Message();
			requestMessage.setMessageType(MessageType.LOGIN);
			requestMessage.setUser(user);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage=SocketUtil.getSocketUtil().readMessage(socket);
			socket.close();
			
			if(responseMessage.getMessageType()==MessageType.LOGIN_SUCCESS) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(User user) {		
		try {
			Message requestMessage=new Message();
			requestMessage.setMessageType(MessageType.REGISTER);
			requestMessage.setUser(user);
			Socket socket=new Socket("127.0.0.1",5555);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage=SocketUtil.getSocketUtil().readMessage(socket);
			socket.close();
			
			if(responseMessage.getMessageType()==MessageType.REGISTER_SUCCESS) {
				return true;
				
			}else{
				return false;
				
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	public List<User> getUserList(MainWindows mainWindows){
		 List<User> users=null;
		try {
			Message requestMessage=new Message();
			requestMessage.setUser(mainWindows.user);
			requestMessage.setMessageType(MessageType.GET_USER_LIST);
			Socket socket=new Socket("127.0.0.1",5555);
			
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			Message responseMessage=SocketUtil.getSocketUtil().readMessage(socket);
			
			if(responseMessage.getMessageType()==MessageType.GET_USER_LIST_SUCCESS) {
				users=responseMessage.getUsers();
			}
			RefreshFriendListThread refreshFriendListThread=new RefreshFriendListThread(mainWindows, socket);
			refreshFriendListThread.start();
			ThreadManage.threadMap.put(mainWindows.user_name,refreshFriendListThread);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return users;
	}
}
