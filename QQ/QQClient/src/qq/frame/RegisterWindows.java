package qq.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import qq.entity.User;
import qq.service.UserService;
import qq.util.StringUtil;
import qq.util.ValidateUtil;

public class RegisterWindows extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JLabel labe1;
	private JLabel labe2;
	private JLabel labe3;
	private JLabel labe4;
	private JLabel labe5;
	private JLabel labe6;
	private JLabel labe7;
	private JCheckBox radioButton1;
	private JTextField nickNameField;
	private JTextField signatureField;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_re;
	private JComboBox<String> photo;
	private JLabel imageLabel;
	private ImageIcon image1 = new ImageIcon("D:\\Java\\eclipse-workspace\\MyHomeWork\\src\\QQ\\image1.jpeg");
	private ImageIcon image2=new ImageIcon("D:\\Java\\eclipse-workspace\\QQClient\\src\\qq\\image\\qq_icon.png");
	
	public void creatFrame() {
			
		imageLabel=new JLabel(image1);
		imageLabel.setBounds(0, 0, 90, 100);
		Font f=new Font("微软雅黑",Font.BOLD,25);
		radioButton1=new JCheckBox("我已阅读并同意相关服务条款和隐私政策");
		radioButton1.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				}
			
		});
		radioButton1.setBounds(120, 400, 280, 40);
		labe1=new JLabel("QQ注册");
		labe1.setBounds(200, 10, 150, 70);
		labe1.setFont(f);
		labe1.setForeground(new Color(168,118,253));
		labe5=new JLabel("昵称：");
		labe5.setBounds(125, 60, 80, 50);
		labe2=new JLabel("账号：");
		labe2.setBounds(125, 100, 80, 50);
		labe3=new JLabel("密码：");
		labe3.setBounds(125, 150, 80, 50);
		labe4=new JLabel("确认密码：");
		labe4.setBounds(125, 200, 80, 50);
		labe6=new JLabel("个性签名：");
		labe6.setBounds(125, 270, 80, 50);
		labe7=new JLabel("用户头像");
		labe7.setBounds(125, 330, 80, 50);
		nickNameField=new JTextField();
		nickNameField.setBounds(190, 70, 200, 30);
		userNameField=new JTextField();
		userNameField.setBounds(190, 110, 200, 30);
		passwordField=new JPasswordField();
		passwordField.setBounds(190, 160, 200, 30);
		passwordField_re=new JPasswordField();
		passwordField_re.setBounds(190, 210, 200, 30);	
		signatureField=new JTextField();
		signatureField.setBounds(190, 250, 200, 80);
		String[] photoaddress= {"D:\\Java\\QQ_photo\\1.jpg",
				"D:\\Java\\QQ_photo\\2.jpg",
				"D:\\Java\\QQ_photo\\3.jpg",
				"D:\\Java\\QQ_photo\\4.jpg",
				"D:\\Java\\QQ_photo\\5.jpg",
				"D:\\Java\\QQ_photo\\6.jpg",
				"D:\\Java\\QQ_photo\\7.jpg",
				"D:\\Java\\QQ_photo\\8.jpg",
				"D:\\Java\\QQ_photo\\9.jpg"};
		photo=new JComboBox<String>(photoaddress);
		photo.setBounds(190, 340, 200, 40);
		btn1=new JButton("立即注册");
		btn1.setBounds(400, 370, 100, 50);
		btn1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				register();
			}
		});
		btn2=new JButton("重置");
		btn2.setBounds(20, 370, 80, 50);
		btn2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				nickNameField.setText(null);
				userNameField.setText(null);
				passwordField.setText(null);
				passwordField_re.setText(null);
				signatureField.setText(null);
			}
			
		});
		btn3=new JButton();
		btn3.setBounds(0, 0, 0, 0);
		this.add(imageLabel);
		this.add(labe1);
		this.add(labe2);
		this.add(labe3);
		this.add(labe4);
		this.add(labe5);
		this.add(labe6);
		this.add(labe7);
		this.add(nickNameField);
		this.add(userNameField);
		this.add(passwordField);
		this.add(passwordField_re);
		this.add(signatureField);
		this.add(photo);
		this.add(btn1);
		this.add(btn2);
		this.add(radioButton1);
		this.add(btn3);
		
		this.setTitle("QQ注册");
		this.setVisible(true);
		this.setIconImage(image2.getImage());
		this.setSize(534,490);
		this.setLayout(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	@SuppressWarnings("unused")
	private void register() {
		String nick = nickNameField.getText();
		String name = userNameField.getText();
		String password=new String(passwordField.getPassword());
		String password_re=new String(passwordField_re.getPassword());
		String sign = signatureField.getText();
		
		// 验证
		if (StringUtil.isEmpty(nick)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "需要一个帅气的昵称！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (nick.length() > 10) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "昵称长度不可以超过10位！","提示",JOptionPane.WARNING_MESSAGE);
			nickNameField.setText(" ");
			return;
		}
		if (StringUtil.isEmpty(name)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "没有输入账号！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (!ValidateUtil.isNumber(name)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "账号必须为数字！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (name.length() > 11) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "账号长度不可以超过11位！","提示",JOptionPane.WARNING_MESSAGE);
			userNameField.setText(name.substring(0, 10));
			return;
		}
		if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "没有输入密码！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (password.length() > 10) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "密码长度不可以超过10位！","提示",JOptionPane.WARNING_MESSAGE);
			passwordField.setText(" ");
			return;
		}
		if (StringUtil.isEmpty(password_re)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "还需要确认密码！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (password_re.length() > 10) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "确认密码也不可以超过10位！","提示",JOptionPane.WARNING_MESSAGE);
			passwordField_re.setText(" ");
			return;
		}
		if (!StringUtil.isEqual(password, password_re)) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "两次密码输入不一致！","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (sign.length() > 50) {
			JOptionPane.showMessageDialog(RegisterWindows.this, "签名长度不可以超过50位！","提示",JOptionPane.WARNING_MESSAGE);
			signatureField.setText(" ");
			return;
		}
		
		if(radioButton1.isSelected()) {
			User user=new User();
			user.setNickName(nickNameField.getText());
			user.setUserName(userNameField.getText());
			user.setUserPassword(password);
			user.setSignature(signatureField.getText());
			user.setPhoto(photo.getSelectedItem().toString());
			UserService userService=new UserService();
			if(userService.register(user)) {
				RegisterWindows.this.dispose();
				JOptionPane.showMessageDialog(RegisterWindows.this, "注册成功","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(RegisterWindows.this, "该账号已经被注册","提示",JOptionPane.WARNING_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(RegisterWindows.this, "请阅读并同意相关服务条款和隐私政策","提示",JOptionPane.WARNING_MESSAGE);
		}
	}
}
