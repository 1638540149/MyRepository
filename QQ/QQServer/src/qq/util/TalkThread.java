package qq.util;

import java.net.Socket;
import qq.dao.InformationDao;
import qq.entity.Information;

public class TalkThread extends Thread{

	Socket socket;
	InformationDao informationDao=new InformationDao();
	
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public TalkThread(Socket socket) {
		super();
		this.socket=socket;
	}
	
	boolean isRuan=true;
	public void shutDown() {
		isRuan=false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRuan) {
			try {
				Message requestMessage=SocketUtil.getSocketUtil().readMessage(socket);
				if(requestMessage.getMessageType()==MessageType.CHAT_NORMAL) {
					TalkThread talkThread=ThreadManage.threadMap.get(requestMessage.getFriendUser().getUserName()+"-"+requestMessage.getUser().getUserName());
					if(talkThread==null) {
						Information information=new Information();
						information.setUser_name(requestMessage.getUser().getUserName());
						information.setFriend_name(requestMessage.getFriendUser().getUserName());
						information.setContent(requestMessage.getContent());
						informationDao.insertInformation(information);
						
						Socket friendRefreshListSocket=ThreadManage.socketMap.get(requestMessage.getFriendUser().getUserName());
						if(friendRefreshListSocket!=null) {//在线，没有打开窗口
							Message leavingMessage=new Message();
							leavingMessage.setMessageType(MessageType.CHAT_LEAVE);
							leavingMessage.setFriendUser(requestMessage.getUser());
							SocketUtil.getSocketUtil().writeMessage(friendRefreshListSocket, leavingMessage);
						}
					}else {
						Socket friendSocket=talkThread.getSocket();
						Message responseMessage=new Message();
						responseMessage.setMessageType(MessageType.CHAT_NORMAL);
						responseMessage.setContent(requestMessage.getContent());
						SocketUtil.getSocketUtil().writeMessage(friendSocket, responseMessage);
					}
				}else if(requestMessage.getMessageType()==MessageType.CHAT_CLOSE) {
					this.shutDown();
					ThreadManage.threadMap.remove(requestMessage.getFriendUser().getUserName()+"-"+requestMessage.getUser().getUserName());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
