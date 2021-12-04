package qq.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import qq.dao.userDao;
import qq.entity.User;
import qq.service.UserService;

public class LoginWindows extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon titleImage=new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\qq_icon.png");
	private ImageIcon touxiangImage=new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\image1.jpeg");
	private JTextField textField;
	private JPasswordField passwordField;
	private JCheckBox radioButton1;	//复选按钮
	private JCheckBox radioButton2;
	private JLabel labe1;
	private JLabel labe2;
	private JLabel labe3;
	private JLabel labe4;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JLabel imageLabel;
	
	public void creatFrame() {
		
		imageLabel=new JLabel(touxiangImage);
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		imageLabel.setBounds(20, 110, 90, 100);
		
		//创建标题字体对象
		Font f=new Font("微软雅黑",Font.BOLD,25);
		Font font=new Font("BOLD",Font.BOLD,25);//登录字体颜色大小
		
		//创建颜色对象
		Color color=new Color(168,118,253);
		//设置按钮
		radioButton1=new JCheckBox("自动登录");
		radioButton1.setBounds(100, 220, 80, 30);
		radioButton2=new JCheckBox("记住密码");
		radioButton2.setBounds(200, 220, 80, 30);
		
		this.add(radioButton1);
		this.add(radioButton2);
		
		btn1=new JButton("登录");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				User user=new User();
				user.setUserName(textField.getText());
				user.setUserPassword(new String(passwordField.getPassword()));
				UserService userService=new UserService();
				if(userService.login(user)) {
					userDao userDao=new userDao();
					LoginWindows.this.dispose();
					MainWindows mainWindows=new MainWindows(userDao.login(user.getUserName(), user.getUserPassword()),user.getUserName());
					mainWindows.creatFrame();
				}else {
					JOptionPane.showMessageDialog(LoginWindows.this, "账号或密码错误！","提示",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btn1.setBounds(120, 260, 300, 40);
		btn1.setBackground(new Color(9,190,254));
		btn1.setFont(font);
		btn2=new JButton("注册账号");
		btn2.setBounds(0, 310, 100, 50);
		btn2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				RegisterWindows registerWindows=new RegisterWindows();
				registerWindows.creatFrame();
			}
			
		});
		btn3=new JButton("找回密码");
		btn3.setBounds(290, 220, 120, 30);
		
		//登录界面标签
		labe1=new JLabel("QQ登录");
		labe1.setBounds(215, 20, 150, 70);
		labe1.setFont(f);
		labe1.setForeground(color);
		//用户名标签
		labe2=new JLabel("账号：");
		labe2.setBounds(125, 110, 80, 50);
		//密码标签
		labe3=new JLabel("密码：");
		labe3.setBounds(125, 160, 80, 50);
		labe4=new JLabel("v1.0.0");
		labe4.setBounds(470, 330, 100, 50);
		//创建组件
		textField=new JTextField();
		textField.setBounds(190, 120, 200, 30);
		//密码框
		passwordField=new JPasswordField();
		passwordField.setBounds(190, 170, 200, 30);
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);
		this.add(imageLabel);
		this.add(labe1);
		this.add(labe2);
		this.add(labe3);
		this.add(labe4);
		this.add(passwordField);
		this.add(radioButton1);
		this.add(radioButton2);
		this.add(textField);
		
		this.setTitle("QQ登录");
		this.setIconImage(titleImage.getImage());
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(534,413);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		LoginWindows loginWindows=new LoginWindows();
		loginWindows.creatFrame();
	}
}
