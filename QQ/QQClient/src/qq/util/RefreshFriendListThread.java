package qq.util;

import java.awt.Color;
import java.net.Socket;
import javax.swing.JLabel;
import qq.frame.MainWindows;

public class RefreshFriendListThread extends Thread{

	MainWindows mainWindows;
	Socket socket;
	public RefreshFriendListThread(MainWindows mainWindows, Socket socket) {
		super();
		this.mainWindows = mainWindows;
		this.socket = socket;
	}

	boolean isRun=true;
	public void shutDown() {
		isRun=false;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRun) {
			try {
				Message message=SocketUtil.getSocketUtil().readMessage(socket);
				if(message.getMessageType()==MessageType.REFRESH_FRIEND) {
					String[] strings=mainWindows.strings;
					JLabel[] jLabels=mainWindows.jLabels;
					for(int i=0;i<strings.length;i++) {
						if(message.getUser().getUserName().equals(strings[i])) {
							jLabels[i].setForeground(Color.black);
							jLabels[i].setEnabled(true);
							mainWindows.validate();
						}
					}
				}else if (message.getMessageType()==MessageType.REFRESH_FRIEND_EXIT) {
					String[] strings=mainWindows.strings;
					JLabel[] jLabels=mainWindows.jLabels;
					for(int i=0;i<strings.length;i++) {
						if(message.getFriendUser().getUserName().equals(strings[i])) {
							jLabels[i].setForeground(Color.GRAY);
							jLabels[i].setEnabled(false);
							mainWindows.validate();
						}
					}
				}else if (message.getMessageType()==MessageType.CHAT_LEAVE) {
					String[] strings=mainWindows.strings;
					JLabel[] jLabels=mainWindows.jLabels;
					for(int i=0;i<strings.length;i++) {
						if(message.getFriendUser().getUserName().equals(strings[i])) {
							jLabels[i].setForeground(Color.red);
							mainWindows.validate();
						}
					}
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
