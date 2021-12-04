package qq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import qq.db.DataConnect;
import qq.entity.User;

public class userDao {
	public User login(String username,String password) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		User user=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" select id,nick_name,user_name,user_password,user_signature,user_photo from fqq_user where user_name=? and user_password=?");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, username);
			perparedStatement.setString(2, password);
			
			rs=perparedStatement.executeQuery();
			if(rs.next()) {
				user=new User();
				user.setId(rs.getInt("id"));
				user.setNickName(rs.getString("nick_name"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPassword(rs.getString("user_password"));
				user.setSignature(rs.getString("user_signature"));
				user.setPhoto(rs.getString("user_photo"));
				}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(perparedStatement!=null) {
				try {
					perparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return user;
	}

}
