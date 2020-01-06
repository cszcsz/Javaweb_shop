package com.csz.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csz.bean.Goods;
import com.csz.dao.GoodsDao;
import com.csz.dao.GoodsDao;

/**
 * Servlet implementation class GoodsServlet
 */
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsServlet() {
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
		if(type.equals("findAll")) {
			findAllGoods(request, response);
		}
		else if(type.equals("update")){
			updateGoods(request, response);
		}
		else if(type.equals("delete")){
			deleteGoods(request, response);
		}
		else if(type.equals("add")) {
			addGoods(request, response);
		}
		else if(type.equals("query")) {
			queryGoods(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void findAllGoods(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		GoodsDao goodsDao = new GoodsDao();
		String res = goodsDao.findAllGoods();
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}
	
	private void queryGoods(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		GoodsDao goodsDao = new GoodsDao();
		String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
		System.out.println(name);
		String res = goodsDao.queryGoods(name);
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}
	
	
	private void addGoods(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");
		String type = request.getParameter("goodsType");
		float price = Float.parseFloat(request.getParameter("price"));
		int cNum = Integer.parseInt(request.getParameter("cNum"));
		int wNum = Integer.parseInt(request.getParameter("wNum"));
		int providerId = Integer.parseInt(request.getParameter("pId"));
		int factoryId = Integer.parseInt(request.getParameter("fId"));
		
		Goods goods = new Goods();
		goods.setName(name); goods.setType(type); goods.setPrice(price);
		goods.setcNum(cNum);goods.setwNum(wNum);
		goods.setProviderId(providerId);goods.setFactoryId(factoryId);
		
		GoodsDao goodssdao = new GoodsDao();
		int flag = goodssdao.addGoods(goods);
		
		JSONObject json = new JSONObject();
		if(flag != -1) {
			   json.put("message","添加成功~");
		}
		else{
			json.put("message","该货物已存在，请更新~");
		}
		String info = JSON.toJSONString(json);
		
	    PrintWriter out = response.getWriter();
		out.write(info); 
	    out.flush();
	    out.close();
	}
	
	private void updateGoods(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String type = request.getParameter("goodsType");
		float price = Float.parseFloat(request.getParameter("price"));
		float offPrice = Float.parseFloat(request.getParameter("offPrice"));
		int cNum = Integer.parseInt(request.getParameter("cNum"));
		int wNum = Integer.parseInt(request.getParameter("wNum"));
		String sDate = request.getParameter("sDate");
		String eDate = request.getParameter("eDate");
		sDate =  sDate.trim().split(" ")[0]; 
		eDate =  eDate.trim().split(" ")[0];
		int providerId = Integer.parseInt(request.getParameter("pId"));
		int factoryId = Integer.parseInt(request.getParameter("fId"));
		
		Goods goods = new Goods();
		goods.setId(id);
		goods.setName(name); goods.setType(type); goods.setPrice(price);
		goods.setOffPrice(offPrice);goods.setcNum(cNum);goods.setwNum(wNum);
		goods.setsDate(sDate);goods.seteDate(eDate);
		goods.setProviderId(providerId);goods.setFactoryId(factoryId);
		
		GoodsDao goodssdao = new GoodsDao();
		int flag = goodssdao.updateGoods(goods);
		
		JSONObject json = new JSONObject();
		if(flag != 0) {
			   json.put("message","更新成功~");
		}
		else{
			json.put("message","更新失败~");
		}
		String info = JSON.toJSONString(json);
		
	    PrintWriter out = response.getWriter();
		out.write(info); 
	    out.flush();
	    out.close();
	}
	
	private void deleteGoods(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		int id = Integer.parseInt(request.getParameter("id"));
		Goods goods = new Goods();
		goods.setId(id);
	    
		GoodsDao goodssdao = new GoodsDao();
		int res = goodssdao.deleteGoods(goods);
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}

}
