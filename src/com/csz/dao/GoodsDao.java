package com.csz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csz.bean.Goods;

public class GoodsDao {
	public GoodsDao() {
		// TODO Auto-generated constructor stub
	}
	

	public String findAllGoods(){
		String res = "";
		String sql = "select * from goods";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();

			List<Goods> goodsList = new ArrayList<Goods>();
			while(rs.next()){
				Goods goods = new Goods();
				goods.setId(rs.getInt("goodsID"));
				goods.setName(rs.getString("goodsName").trim());
				goods.setPrice(rs.getFloat("price"));
				goods.setOffPrice(rs.getFloat("offPrice"));
				goods.setcNum(rs.getInt("currentNum"));
				goods.setwNum(rs.getInt("warningNum"));
				if(rs.getString("offDateStart") != null) {
					goods.setsDate(rs.getString("offDateStart").trim());
				}
				if(rs.getString("offDateEnd") != null) {
					goods.seteDate(rs.getString("offDateEnd").trim());
				}
				goods.setProviderId(rs.getInt("providerID"));
				goods.setFactoryId(rs.getInt("factoryID"));
				goods.setType(rs.getString("goodsType").trim());
				goodsList.add(goods);
			}
			JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(goodsList));
			res = "{\"total\":"+goodsList.size()+" ,\"rows\":"+jsonArray.toString()+"}";
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return res;
	}
	
	
	public int updateGoods(Goods goods) {
		int i = 0;
		try {
			String sql = "update goods set goodsName = ?, goodsType = ?, price = ?, "
					+ "currentNum = ?, warningNum = ?, offPrice = ?, offDateStart = ?, "
					+ "offDateEnd = ?, providerID = ?, factoryID = ? where goodsID = ?";
			PreparedStatement pst = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);	
			pst.setString(1, goods.getName());
			pst.setString(2, goods.getType());
			pst.setFloat(3, goods.getPrice());
			pst.setInt(4, goods.getcNum());
			pst.setInt(5, goods.getwNum());
			pst.setFloat(6, goods.getOffPrice());
			pst.setString(7, goods.getsDate());
			pst.setString(8, goods.geteDate());
			pst.setInt(9, goods.getProviderId());
			pst.setInt(10, goods.getFactoryId());
			pst.setInt(11, goods.getId());
			i = pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public int updateGoodsSale(Goods goods) {
		int i = 0;
		try {
			Connection conn = DBCon.getConn();
			String sql = "select currentNum from goods where goodsName = ?";
			ResultSet rs = null;
			PreparedStatement pst = null;
			pst = conn.prepareStatement(sql);	
			pst.setString(1, goods.getName());
			rs = pst.executeQuery();
			int curNum = 0;
			while(rs.next()){
				curNum = rs.getInt(1);
				curNum = curNum - goods.getcNum();
			}
			
			sql = "update goods set currentNum = ? where goodsName = ?";
			pst = conn.prepareStatement(sql);	
			pst.setInt(1, curNum);
			pst.setString(2, goods.getName());
			i = pst.executeUpdate();
			pst.close();
			rs.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
			return -1;
		}
		return i;
	}
	
	public int deleteGoods(Goods goods) {
		int i = 0;
		try {
			String sql = "delete from goods where goodsID = ?";
			Connection conn = DBCon.getConn();	
			PreparedStatement pst = null;
			pst = conn.prepareStatement(sql);	
			pst.setInt(1, goods.getId());
			i = pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
			return 0;
		}
		return i;
	}

	public int addGoods(Goods goods) {
		// TODO Auto-generated method stub
		int i = 0;
		String sql = "select goodsName from goods";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				if(rs.getString(1).trim().equals(goods.getName())) 
				{
					System.out.println("数据库中已存在该该货物");
					return -1;
				}
			}
			sql = "insert into goods (goodsName,goodsType,price,currentNum,warningNum,providerID,factoryID) values (?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, goods.getName());
			pst.setString(2, goods.getType());
			pst.setFloat(3, goods.getPrice());
			pst.setInt(4, goods.getcNum());
			pst.setInt(5, goods.getwNum());
			pst.setInt(6, goods.getProviderId());
			pst.setInt(7, goods.getFactoryId());
			i = pst.executeUpdate();
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return i;
		
	}

	public String queryGoods(String name) {
		// TODO Auto-generated method stub
		String res = "";
		String sql = "select goodsId,goodsName,goodsType,price,currentNum,imgUrl from goods where goodsName = ?";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
			JSONObject json = new JSONObject();
			while(rs.next()){
				json.put("id", rs.getInt("goodsID"));
				if(rs.getString("goodsName")==null) {
					System.out.println("不存在该商品");
					json.put("message","不存在该商品");
					res = JSON.toJSONString(json);
					return res;
				}
				json.put("name", rs.getString("goodsName").trim());
				json.put("goodsType", rs.getString("goodsType").trim());
				json.put("price",rs.getFloat("price"));
				json.put("cNum", rs.getInt("currentNum"));
				json.put("imgUrl", rs.getString("imgUrl"));
			}
			json.put("message","查询成功~");
			res = JSON.toJSONString(json);
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return res;

	}
}
