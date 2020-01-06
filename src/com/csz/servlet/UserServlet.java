package com.csz.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csz.bean.User;
import com.csz.dao.UserDao;
import com.csz.tools.Tools;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type = request.getParameter("type");
		if(type.equals("reg")) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
			dispatcher.forward(request, response);
		}
		else if(type.equals("signup")) {
			signupUser(request, response);
		}
		else if(type.equals("userLogin")) {
			userLogin(request, response);
		}
		else if(type.equals("findAll")){
			findAllUser(request, response);
		}
		else if(type.equals("update")){
			updateUser(request, response);
		}
		else if(type.equals("delete")){
			deleteUser(request, response);
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void signupUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");
		String pwd = request.getParameter("password");
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		String type = request.getParameter("staffType");
		
		User user = new User();
		user.setName(name); 
		user.setPassword(pwd);
		user.setAge(age);
		user.setSex(sex);
		user.setWorkLevel(0);
		user.setType(type);
		
		UserDao usersdao = new UserDao();
		int flag = usersdao.insertUsers(user);
	    PrintWriter out = response.getWriter();
	    JSONObject json = new JSONObject();
		if(flag == 1) {
			   json.put("message","注册成功~");
		}
		else if(flag == -1){
			  json.put("message","已存在该用户~");
		}
		else {
			json.put("message","插入数据失败~");
		}
		String info = JSON.toJSONString(json);
		out.write(info); 
	    out.flush();
	    out.close();
	}
	
	private void userLogin(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");

		String name = request.getParameter("name");
		String pwd = request.getParameter("password");
		String type = request.getParameter("staffType");

		User user = new User();
		user.setName(name); 
		user.setPassword(pwd);
		user.setType(type);
		
		UserDao usersdao = new UserDao();
		int flag = usersdao.checkLogin(user);
	    PrintWriter out = response.getWriter();
	    JSONObject json = new JSONObject();
		if(flag == 1) {
			   json.put("message","登录成功~");
		}
		else if(flag == -1){
			json.put("message","不存在该用户~");
		}
		else{
			json.put("message","密码错误~");
		}
		String info = JSON.toJSONString(json);
		out.write(info); 
	    out.flush();
	    out.close();
	}
	
	private void findAllUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		String name = request.getParameter("name");
		
		UserDao usersdao = new UserDao();
		String res = usersdao.findAllUser(name);
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		int level = Integer.parseInt(request.getParameter("level"));
		String type = request.getParameter("staffType");
		
		User user = new User();
		user.setName(name); user.setType(type);
		user.setSex(sex);user.setAge(age);
		user.setWorkLevel(level);

		UserDao usersdao = new UserDao();
		int flag = usersdao.updateUser(user);
		
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
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 将字符串转成utf-8编码，防止乱码
	    request.setCharacterEncoding("utf-8");
	    response.setContentType("text/html;charset=utf-8");
	    
		int id = Integer.parseInt(request.getParameter("id"));
		User user = new User();
		user.setId(id);
	    
		UserDao usersdao = new UserDao();
		int res = usersdao.deleteUser(user);
		
	    PrintWriter out = response.getWriter();
		out.write(res); 
	    out.flush();
	    out.close();
	}


}
