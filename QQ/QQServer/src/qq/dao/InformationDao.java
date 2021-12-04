package qq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import qq.db.DataConnect;
import qq.entity.Information;

public class InformationDao {

	
	public void insertInformation(Information information) {
		Connection con=null;
		PreparedStatement perparedStatement=null;
		try {
			con=DataConnect.getConnection();
			StringBuffer stringBuffer=new StringBuffer(" insert into fqq_message(user_name,friend_name,content)values(?,?,?)");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, information.getUser_name());
			perparedStatement.setString(2, information.getFriend_name());
			perparedStatement.setString(3, information.getContent());
			
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
	
	@SuppressWarnings("resource")
	public List<Information> getInformationIsOnline(String username,String friendname){
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		List<Information> informations=null;
		try {
			con=DataConnect.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer stringBuffer=new StringBuffer(" select id,user_name,friend_name,content from fqq_message where user_name = ? and friend_name = ? ");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, username);
			perparedStatement.setString(2, friendname);
			rs=perparedStatement.executeQuery();
			if(!rs.wasNull()) {
				informations=new ArrayList<Information>();
			}
			while(rs.next()) {
				Information information=new Information();
				information.setId(rs.getInt("id"));
				information.setUser_name(rs.getString("user_name"));
				information.setFriend_name("friend_name");
				information.setContent(rs.getString("content"));
				
				informations.add(information);
			}
			
			StringBuffer sqlBuffer=new StringBuffer(" delete from fqq_message where user_name = ? and friend_name = ? ");
			perparedStatement=con.prepareStatement(sqlBuffer.toString());
			perparedStatement.setString(1, username);
			perparedStatement.setString(2, friendname);
			perparedStatement.executeUpdate();
			
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
		return informations;
	}
	
	@SuppressWarnings("resource")
	public List<Information> getInformationNotOnline(String username){
		Connection con=null;
		PreparedStatement perparedStatement=null;
		ResultSet rs=null;
		List<Information> informations=null;
		try {
			con=DataConnect.getConnection();
			con.setAutoCommit(false);
			
			StringBuffer stringBuffer=new StringBuffer(" select id,user_name,friend_name,content from fqq_message where friend_name = ? ");
			perparedStatement=con.prepareStatement(stringBuffer.toString());
			perparedStatement.setString(1, username);
			rs=perparedStatement.executeQuery();
			if(!rs.wasNull()) {
				informations=new ArrayList<Information>();
			}
			while(rs.next()) {
				Information information=new Information();
				information.setId(rs.getInt("id"));
				information.setUser_name(rs.getString("user_name"));
				information.setFriend_name("friend_name");
				information.setContent(rs.getString("content"));
				
				informations.add(information);
			}
			
			StringBuffer sqlBuffer=new StringBuffer(" delete from fqq_message where friend_name = ? ");
			perparedStatement=con.prepareStatement(sqlBuffer.toString());
			perparedStatement.setString(1, username);
			perparedStatement.executeUpdate();
			
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
		return informations;
	}
}
