package qq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import qq.db.DataConnect;
import qq.entity.User;

public class UserDao {

	@SuppressWarnings("resource")
	public User login(String username,String password) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		User user=null;
		try {
			con=DataConnect.getConnection();
			con.setAutoCommit(false);
			
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
			if(user!=null) {
				StringBuffer sqlBuffer=new StringBuffer(" update fqq_user set isonline=1 where user_name=?");
				perparedStatement=con.prepareStatement(sqlBuffer.toString());
				perparedStatement.setString(1, user.getUserName());
				perparedStatement.executeUpdate();
			}
			
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
	
	public User getUserByUserName(String userName) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		User user=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" select id,nick_name,user_name,user_password,user_signature,user_photo from fqq_user where user_name=?");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, userName);
			
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
	
	public void insertUser(User user) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" insert into fqq_user(nick_name,user_name,user_password,user_signature,user_photo)values(?,?,?,?,?)");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, user.getNickName());
			perparedStatement.setString(2, user.getUserName());
			perparedStatement.setString(3, user.getUserPassword());
			perparedStatement.setString(4, user.getSignature());
			perparedStatement.setString(5, user.getPhoto());
			
			perparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
	}
	
	public List<User> getUserList(){
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		List<User> users=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" select id,nick_name,user_name,user_password,user_signature,user_photo,isonline from fqq_user");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			rs=perparedStatement.executeQuery();
			if(!rs.wasNull()) {
				users=new ArrayList<User>();
			}
			while(rs.next()) {
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setNickName(rs.getString("nick_name"));
				user.setUserName(rs.getString("user_name"));
				user.setUserPassword(rs.getString("user_password"));
				user.setSignature(rs.getString("user_signature"));
				user.setPhoto(rs.getString("user_photo"));
				user.setIsonline(rs.getInt("isonline"));
				
				users.add(user);
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
		return users;
	}
	public void userExit(User user) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" update fqq_user set isonline=0 where user_name=?");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, user.getUserName());
			
			perparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
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
	}
}
