package qq.entity;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;//�û�id
	private String nickName;//�û��ǳ�
	private String userName;//�û��ʺ�
	private String userPassword;//�û�����
	private String signature; //�û�ǩ��
	private String Photo;
	private int isonline;
	public int getIsonline() {
		return isonline;
	}
	public void setIsonline(int isonline) {
		this.isonline = isonline;
	}
	public String getPhoto() {
		return Photo;
	}
	public void setPhoto(String photo) {
		Photo = photo;
	}
	public User() {
		
	}
	public User(int id,String nickName) {
		this.id=id;
		this.nickName=nickName;
	}
	public User(String nickName,String userName,String signature) {
		this.nickName=nickName;
		this.userName=userName;
		this.signature=signature;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
