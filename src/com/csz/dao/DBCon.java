package com.csz.dao;

import java.sql.*;

public class DBCon {
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=shop";

			conn = DriverManager.getConnection(URL, "sa", "okmijnuhb");// userName是你数据库的用户名如sa,
			if(conn != null)
			{
				System.out.println("连接数据库成功");
			}

		} catch (Exception e) {
			System.out.println("连接数据库失败");
		}
		return conn;
	}

	public static void main(String args[]) {
		DBCon.getConn();
	}
}
