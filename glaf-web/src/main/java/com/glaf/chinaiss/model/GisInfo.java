package com.glaf.chinaiss.model;

import java.io.Serializable;

public class GisInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;//联合编号（1|95|222|230|263|）
	 
	private String name;//名称
	 
	private String index_id;//编号
	
	private String parent_id;//父编号
	
	private String bDate;//计划开始时间
	
	private String eDate;//计划结束时间
	
	private String nlevel;
	
	public String getNlevel() {
		return nlevel;
	}

	public void setNlevel(String nlevel) {
		this.nlevel = nlevel;
	}

	public String getbDate() {
		return bDate;
	}

	public void setbDate(String bDate) {
		this.bDate = bDate;
	}

	public String geteDate() {
		return eDate;
	}

	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	private String num;//累计已填写数量
	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getIndex_id() {
		return index_id;
	}

	public void setIndex_id(String index_id) {
		this.index_id = index_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	


}
