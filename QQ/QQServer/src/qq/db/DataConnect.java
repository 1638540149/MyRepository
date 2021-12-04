package qq.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataConnect {

	public static synchronized Connection getConnection() {
		Connection con=null;
		String driver="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://127.0.0.1:3306/qqdata?useUnicode=true&characterEncoding=utf8";
		String user="root";
		String password="zw.5212623";
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
		}catch(Exception e) {
			System.out.println("数据库连接失败！");
		}
		return con;
	}
	public static void main(String[] args)throws Exception{
		Connection con=getConnection();
		if(con!=null) {
			System.out.println(con);
			con.close();
		}
	}
}
