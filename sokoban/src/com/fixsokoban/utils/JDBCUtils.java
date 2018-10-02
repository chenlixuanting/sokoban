package com.fixsokoban.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 连接数据库工具类
 * 
 * @author Shinelon
 *
 */
public class JDBCUtils {

	private static final String DRIVERCLASS;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;

	static {
		DRIVERCLASS = ResourceBundle.getBundle("config").getString(
				"DriverClass");
		URL = ResourceBundle.getBundle("config").getString("Url");
		USERNAME = ResourceBundle.getBundle("config").getString("UserName");
		PASSWORD = ResourceBundle.getBundle("config").getString("PassWord");
	}

	static {
		// 注册驱动，只执行一次
		try {
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 开启事务
			con.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param con
	 */
	public static void closeConnection(Connection con) {
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
