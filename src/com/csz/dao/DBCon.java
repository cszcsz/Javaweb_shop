package com.csz.dao;

import java.sql.*;

public class DBCon {
	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=shop";

			conn = DriverManager.getConnection(URL, "sa", "okmijnuhb");// userName�������ݿ���û�����sa,
			if(conn != null)
			{
				System.out.println("�������ݿ�ɹ�");
			}

		} catch (Exception e) {
			System.out.println("�������ݿ�ʧ��");
		}
		return conn;
	}

	public static void main(String args[]) {
		DBCon.getConn();
	}
}
