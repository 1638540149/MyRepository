package qq.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import qq.entity.User;
import qq.service.UserService;
import qq.util.Message;
import qq.util.MessageType;
import qq.util.SocketUtil;
import qq.util.ThreadManage;

public class MainWindows extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel friendPanel;
	private JPanel groupPanel;
	private JPanel recentPanel;
	private JPanel userPanel;
	private JPanel searchPanel;
	private JTextField searchText;
	private JLabel searchButton;
	private JScrollPane jScrollPane;
	private JLabel friend;
	private ImageIcon friend_icon;
	private JLabel user_nickname;
	private JLabel user_signature;
	private JLabel user_photo;
	private JButton btn1;
	private ImageIcon user_icon;
	private JTabbedPane typeInfo=new JTabbedPane();
	
	UserService userService=new UserService();
	public User user=null;
	public String user_name;
	public String[] strings;
	public JLabel[] jLabels;
	
	public MainWindows(User user,String user_name) {
		super();
		this.user = user;
		this.user_name=user_name;
	}
	public void creatFrame() {
		List<User> users=userService.getUserList(this);
		userPanel=new JPanel();
		userPanel.setLayout(null);
		userPanel.setBounds(0, 0, 342, 120);
		userPanel.setBackground(new Color(131,201,251));
		
		user_nickname=new JLabel(user.getNickName());
		user_nickname.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user_nickname.setBounds(120, 40, 50, 30);
		user_signature=new JLabel(user.getSignature());
		user_signature.setBounds(120, 70, 150, 30);
		user_signature.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		user_icon=new ImageIcon(user.getPhoto());
		user_photo=new JLabel();
		user_photo.setIcon(user_icon);
		user_photo.setBounds(10, 25, 80, 80);
		
		userPanel.add(user_nickname);
		userPanel.add(user_photo);
		userPanel.add(user_signature);
		
		jLabels=new JLabel[users.size()];
		strings=new String[users.size()];
		friendPanel=new JPanel(new GridLayout(users.size(),1,10,10));
		if(users!=null) {
			for(int i=0;i<users.size();i++) {
				User user_r=users.get(i);
				String nickname=user_r.getNickName();
				String signature=user_r.getSignature();
				String information="<html><body>" + nickname + "<br><br><br>" + signature + "<body></html>";  
				friend_icon=new ImageIcon(user_r.getPhoto());
				friend=new JLabel(information,friend_icon,JLabel.LEFT);
				
				if(user_r.getIsonline()==0) {
					friend.setForeground(Color.GRAY);
					friend.setEnabled(false);
				}
				friend.setFont(new Font("微软雅黑", Font.PLAIN, 15));
				friend.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if(e.getClickCount()==2) {
							JLabel currentJlabel=(JLabel)e.getSource();
							if(currentJlabel.getForeground()==Color.red) {
								currentJlabel.setForeground(Color.BLACK);
							}
							
							Message requestMessage=new Message();
							Message responseMessage=new Message();
							requestMessage.setMessageType(MessageType.GET_CHAT_LEAVE);
							requestMessage.setUser(user_r);
							requestMessage.setFriendUser(user);
							try {
								Socket socket = new Socket("127.0.0.1", 5555);
								SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
								responseMessage=SocketUtil.getSocketUtil().readMessage(socket);
							}catch(Exception e1) {
								e1.printStackTrace();
							}
							
							ChatRoom chatRoom=new ChatRoom(user_r,user,responseMessage.getInformations());
							chatRoom.creatFrame();
						}
					}
					
				});
				
				friendPanel.add(friend);
				strings[i]=user_r.getUserName();
				jLabels[i]=friend;
			}
		}
		jScrollPane=new JScrollPane(friendPanel);
		jScrollPane.setBounds(0, 180, 342, 500);
		
		searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setOpaque(false);
		searchPanel.setBounds(0, 120, 350, 30);
		searchPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
		searchText = new JTextField();
		searchText.setText("  " + "搜索：联系人、群组");
		searchPanel.add(searchText);
		searchText.setOpaque(false);
		searchText.setFocusable(false);
		searchText.setBounds(1, 1, 253, 30);
		searchText.setBorder(BorderFactory.createEmptyBorder());
	
		searchButton = new JLabel();
		searchPanel.add(searchButton);
		searchButton.setBounds(300, 1, 45, 30);
		searchButton.setOpaque(false);
		searchButton.setBackground(Color.WHITE);
		searchButton.setIcon(new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\search_icon.png"));
	
		groupPanel=new JPanel();
		groupPanel.setLayout(null);
		groupPanel.setBounds(0, 0, 342, 150);
		
		typeInfo.setBounds(0, 147, 341, 530);
		typeInfo.setOpaque(false);
		typeInfo.addTab("联系人", new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\tab_boddy.png"), jScrollPane);
		typeInfo.addTab("群聊", new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\tab_group.png"), groupPanel);
		typeInfo.addTab("最近",new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\tab_recent.png"), recentPanel);
		typeInfo.setPreferredSize(new Dimension(50,50));
		typeInfo.setBorder(BorderFactory.createEmptyBorder());
		
		recentPanel=new JPanel();
		recentPanel.setLayout(null);
		recentPanel.setBounds(0, 0, 350, 150);
		recentPanel.setBackground(Color.WHITE);
		
		JTree tree_1 = new JTree();
		tree_1.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("\u7FA4\u804A") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				{
					add(new DefaultMutableTreeNode("19级计算机类2班"));
					add(new DefaultMutableTreeNode("Java程序设计19406101-02"));
				}
			}
		));
		tree_1.setRootVisible(false);
		tree_1.setFont(new Font("仿宋", Font.BOLD, 15));
		tree_1.setBounds(0, 0, 336, 516);
		groupPanel.add(tree_1);
		btn1=new JButton();
		btn1.setIcon(new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\tianjiahaoyou.png"));
		btn1.setBounds(300, 672, 40, 30);
		
		this.add(btn1);
		this.add(searchPanel);
		this.add(typeInfo);
		this.add(userPanel);
		
		
		
		this.setLayout(null);
		this.setTitle("QQ");
		this.setIconImage(new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\qq_icon.png").getImage());
		this.setSize(359,750);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
				Message requestMessage=new Message();
				requestMessage.setMessageType(MessageType.EXIT);
				requestMessage.setUser(user);
				try {
					Socket socket=new Socket("127.0.0.1", 5555);
					SocketUtil.getSocketUtil().writeMessage(socket, requestMessage);
					socket.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ThreadManage.threadMap.get(user_name).shutDown();
			}
		});
	}
}
