package com.csz.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csz.bean.User;

public class UserDao {
	
	public UserDao() {
		// TODO Auto-generated constructor stub
	}
	
	// 返回值：0表示插入错误，-1表示主键相同，1表示成功
	public int insertUsers(User user) {
		int i = 0;
		String sql = "select name from staff";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
//				System.out.println(rs.getString(1).trim()+ " " + user.getName());
				if(rs.getString(1).trim().equals(user.getName())) 
				{
					System.out.println("数据库中已存在该用户");
					return -1;
				}
			}
			sql = "insert into staff (name,pwd,age,sex,workLevel,type) values (?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getName());
			pst.setString(2, user.getPassword());
			pst.setInt(3, user.getAge());
			pst.setString(4, user.getSex());
			pst.setInt(5, user.getWorkLevel());
			pst.setString(6, user.getType());
			i = pst.executeUpdate();
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return i;
	}
	
	public int checkLogin(User user){
		int flag = 1;
		String sql = "select name from staff";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			int existUser = 0;
			while(rs.next()){
				if(rs.getString(1).trim().equals(user.getName())) {
					existUser = 1;
					break;
				}
			}
			if(existUser == 0){
				System.out.println("数据库中不存在该用户，登陆失败");
				return -1;
			}
			sql = "select pwd from staff where name = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getName());
			rs = pst.executeQuery();
			while(rs.next()){
				if(!rs.getString(1).trim().equals(user.getPassword())){
					return 0;
				}
				else return 1;
			}
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return flag;
	}
	
	public String findAllUser(String keyWord){
		String res = "";
		String sql = "";
		if(keyWord == null || keyWord.equals("all")) {
			 sql = "select staffID,name,sex,age,workLevel,type from staff";			
		}
		else {
			sql = String.format("select staffID,name,sex,age,workLevel,type from staff where name = '%s'", keyWord);
		}
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

		    JSONObject json = new JSONObject();
			String info = JSON.toJSONString(json);
			List<User> userList = new ArrayList<User>();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt("staffID"));
				user.setName(rs.getString("name").trim());
				user.setSex(rs.getString("sex").trim());
				user.setAge(rs.getInt("age"));
				user.setWorkLevel(rs.getInt("workLevel"));
				user.setType(rs.getString("type").trim());
				userList.add(user);
			}
			JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(userList));
			res = "{\"total\":"+userList.size()+" ,\"rows\":"+jsonArray.toString()+"}";
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return res;
	}
	
	
	public int updateUser(User user) {
		int i = 0;
		try {
			String sql = "update staff set age = ?, sex = ?, type = ?, workLevel = ? where name = ?";
			PreparedStatement pst = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);	
			pst.setInt(1, user.getAge());
			pst.setString(2, user.getSex());
			pst.setString(3, user.getType());
			pst.setInt(4, user.getWorkLevel());
			pst.setString(5, user.getName());
			i = pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public int deleteUser(User user) {
		int i = 0;
		try {
			String sql = "delete from staff where staffID = ?";
			PreparedStatement pst = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);	
			pst.setInt(1, user.getId());
			i = pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
			return 0;
		}
		return i;
	}
}
