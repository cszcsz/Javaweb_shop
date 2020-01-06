package com.csz.bean;

public class User {
	
	private int id;
	private String name;
	private String password;
	private int age;
	private String sex;
	private int workLevel;
	private String type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getWorkLevel() {
		return workLevel;
	}
	public void setWorkLevel(int workLevel) {
		this.workLevel = workLevel;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		
	}
}
