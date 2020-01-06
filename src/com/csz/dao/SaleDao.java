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
import com.csz.bean.Sale;
import com.csz.bean.SaleDetail;
import com.csz.bean.Sale;

public class SaleDao {
	public SaleDao() {
		// TODO Auto-generated constructor stub
	}
	
	public int insertSale(Sale sale) {
		int i = 0;
		String sql = "insert into sale(saleID, saleDate, salePrice, staffName) values (?, ?, ?, ?)";
		try {
			PreparedStatement pst = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			pst.setString(1, sale.getsId());
			pst.setString(2, sale.getSaleDate());
			pst.setFloat(3, sale.getSalePrice());
			pst.setString(4, sale.getStaffName());
			i = pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return i;
	}

	public String findAllSale() {
		String res = "";
		String sql = "select saleID,staffName,salePrice,saleDate from sale";
		try {
			PreparedStatement pst = null;
			ResultSet rs = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			List<Sale> saleList = new ArrayList<Sale>();
			while(rs.next()){
				Sale sale = new Sale();
				sale.setsId(rs.getString("saleID"));
				sale.setStaffName(rs.getString("staffName"));
				sale.setSalePrice(rs.getFloat("salePrice"));
				sale.setSaleDate(rs.getString("saleDate"));
				saleList.add(sale);
			}
			JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(saleList));
			res = "{\"total\":"+saleList.size()+" ,\"rows\":"+jsonArray.toString()+"}";
			rs.close();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		return res;
	}

	public void insertSaleDeatil(SaleDetail sd) {
		String sql = "insert into saleDetail(sale_id, goods_id, nums) values (?, ?, ?)";
		try {
			PreparedStatement pst = null;
			Connection conn = DBCon.getConn();	
			pst = conn.prepareStatement(sql);
			pst.setString(1, sd.getSaleId());
			pst.setInt(2, sd.getGoodsId());
			pst.setInt(3, sd.getNums());

			pst.executeUpdate();
			pst.close();
			conn.close();
		}catch (SQLException e) {		
			e.printStackTrace();
		}
		
	}
}
