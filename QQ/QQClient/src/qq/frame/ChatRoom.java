package qq.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import qq.entity.Information;
import qq.entity.User;
import qq.util.Message;
import qq.util.MessageType;
import qq.util.SocketUtil;
import qq.util.TalkThread;

public class ChatRoom extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea jTextArea;
	private JScrollPane jScrollPane;
	private JPanel southPanel;
	private JTextField jTextField;
	private JButton sendBtn;
	User friendUser;
	Socket socket;
	User user;
	List<Information> informations;
	TalkThread talkThread;
	private static String type = "yyyy/MM/dd HH:mm:ss";
	SimpleDateFormat sdf = new SimpleDateFormat(type);
	
	public ChatRoom(User friendUser,User user,List<Information> informations) throws HeadlessException {
		super();
		this.friendUser=friendUser;
		this.user=user;
		this.informations=informations;
	}

	public void creatFrame() {
		jTextArea=new JTextArea();
		jTextArea.setEditable(false);
		jTextArea.setFont(new Font(Font.DIALOG,Font.PLAIN,20));
		jScrollPane=new JScrollPane(jTextArea);
		this.add(jScrollPane,BorderLayout.CENTER);
		if(informations!=null) {
			for(int i=0;i<informations.size();i++) {
				jTextArea.append(informations.get(i).getContent()+"\n");
			}
		}
		
		southPanel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jTextField=new JTextField(20);
		jTextField.setFont(new Font(Font.DIALOG,Font.PLAIN,15));
		sendBtn=new JButton();
		sendBtn.setIcon(new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\sendButton.png"));
		sendBtn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					Message requestMessage=new Message();
					requestMessage.setMessageType(MessageType.CHAT_NORMAL);
					requestMessage.setUser(user);
					requestMessage.setFriendUser(friendUser);
					requestMessage.setContent(sdf.format(new Date())+"\n"+user.getNickName()+"对"+friendUser.getNickName()+"说:  "+jTextField.getText());
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			
					jTextArea.append(sdf.format(new Date())+"\n"+user.getNickName()+"对"+friendUser.getNickName()+"说:  "+jTextField.getText()+"\n");
					jTextField.setText(" ");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
			}
		});
		southPanel.add(jTextField);
		southPanel.add(sendBtn,BorderLayout.SOUTH);
		this.add(southPanel,BorderLayout.SOUTH);
		
		try {
			socket=new Socket("127.0.0.1",5555);
			Message requestMessage=new Message();
			requestMessage.setMessageType(MessageType.CHAT_CONNECTION);
			requestMessage.setUser(user);
			requestMessage.setFriendUser(friendUser);
			SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
			
			talkThread=new TalkThread(socket, jTextArea);
			talkThread.start();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		this.setTitle("                                                    		                                   "+friendUser.getNickName());
		this.setIconImage(new ImageIcon(friendUser.getPhoto()).getImage());
		this.setSize(856,619);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Message requestMessage=new Message();
				requestMessage.setMessageType(MessageType.CHAT_CLOSE);
				requestMessage.setUser(user);
				requestMessage.setFriendUser(friendUser);
				try {
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				talkThread.shutDown();
			}
		});
	}
}
