package cn.edu.zucc.freetravel.util;

import java.sql.Connection;

public class DBUtil {
	private static final String jdbcUrl="jdbc:mysql://localhost:3306/FreeTravel?serverTimezone=UTC";
	private static final String dbUser="root";
	private static final String dbPwd="shengshiyan1311";

	static{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			System.out.println("�������سɹ�!");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			System.out.println("��������ʧ��!");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws java.sql.SQLException{
		return java.sql.DriverManager.getConnection(jdbcUrl, dbUser, dbPwd);
	}
}
