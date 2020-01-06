package com.csz.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csz.bean.Goods;
import com.csz.bean.Sale;
import com.csz.bean.SaleDetail;
import com.csz.dao.GoodsDao;
import com.csz.dao.SaleDao;
import com.csz.dao.UserDao;

/**
 * Servlet implementation class SaleServlet
 */
@WebServlet("/SaleServlet")
public class SaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");

		String type = request.getParameter("type");
		System.out.println(type);
		if(type.equals("insert")) {
			insertSale(request, response);
		}
		else if(type.equals("updateGoods")) {
			updateGoods(request, response);
		}
		else if(type.equals("findAll")){
			findAllSale(request, response);
		}
		else if(type.equals("insertSaleDetail")) {
			insertSaleDetail(request, response);
		}
	
	}

	private void insertSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
	    String sId = request.getParameter("id");
	    String staffName = request.getParameter("staffName");
	    String date = request.getParameter("date");
	    float cost = Float.parseFloat(request.getParameter("cost"));

    	Sale sale = new Sale();
    	sale.setsId(sId); sale.setStaffName(staffName);
    	sale.setSaleDate(date); sale.setSalePrice(cost);
    	
    	SaleDao saleDao = new SaleDao();
    	int flag =  saleDao.insertSale(sale);
		JSONObject json = new JSONObject();
		if(flag != -1) {
			   json.put("message","交易成功~");
		}
		else{
			json.put("message","交易失败~");
		}
		String info = JSON.toJSONString(json);
		
	    PrintWriter out = response.getWriter();
		out.write(info); 
	    out.flush();
	    out.close();
	 
	}

	private void updateGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
	    String jsonStr = request.getParameter("goods");
	    JSONArray jsonArray = JSON.parseArray(jsonStr);
	    int size = jsonArray.size();
	    for(int i = 0; i < size; i++){
	    	JSONObject jsonObject = jsonArray.getJSONObject(i);
	    	System.out.println("name: " + jsonObject.getString("name") + ",nums: " + jsonObject.getInteger("nums"));
	    	
	    	Goods goods = new Goods();
	    	goods.setName(jsonObject.getString("name"));
	    	goods.setcNum(jsonObject.getInteger("nums"));
	    	
	    	GoodsDao goodsDao = new GoodsDao();
	    	goodsDao.updateGoodsSale(goods);
	    }
	}
	
	private void findAllSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		SaleDao saleDao = new SaleDao();
		String res = saleDao.findAllSale();
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}
	
	
	private void insertSaleDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
	    String jsonStr = request.getParameter("goods");
	    String saleId  = request.getParameter("saleId");
	    JSONArray jsonArray = JSON.parseArray(jsonStr);
	    int size = jsonArray.size();
	    for(int i = 0; i < size; i++){
	    	JSONObject jsonObject = jsonArray.getJSONObject(i);
	    	System.out.println("name: " + jsonObject.getString("name") + ",nums: " + jsonObject.getInteger("nums"));
	    	
	    	SaleDetail sd = new SaleDetail();
	    	sd.setGoodsId(jsonObject.getInteger("id"));
	    	sd.setNums(jsonObject.getInteger("nums"));
	    	sd.setSaleId(saleId);
	    	
	    	SaleDao saleDao = new SaleDao();
	    	saleDao.insertSaleDeatil(sd);
	    }
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
