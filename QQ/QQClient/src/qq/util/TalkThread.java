package qq.util;

import java.net.Socket;
import javax.swing.JTextArea;

public class TalkThread extends Thread{

	Socket socket;
	JTextArea jTextArea;
	
	public TalkThread(Socket socket, JTextArea jTextArea) {
		super();
		this.socket = socket;
		this.jTextArea = jTextArea;
	}
	boolean isRun=true;
	public void shutDown(){
		isRun=false;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRun) {
			Message talkMessage;
			try {
				talkMessage = SocketUtil.getSocketUtil().readMessage(socket);
				if(talkMessage.getMessageType()==MessageType.CHAT_NORMAL) {
					jTextArea.append(talkMessage.getContent()+"\n");
					MusicUtil musicUtil=new MusicUtil();
					MusicUtil.main();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
