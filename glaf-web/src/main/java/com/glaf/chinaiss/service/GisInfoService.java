package com.glaf.chinaiss.service;

import java.util.List;

import com.glaf.chinaiss.model.GisInfo;

public interface GisInfoService {

	//获取已填写完成的文档总数
	int getProjDocNum(String id);
	
	List<GisInfo> getgisInfoListById(String id);
	
	List<GisInfo> getgisInfoListByName(String name);
	
	List<GisInfo> 	getGisGinttTree(GisInfo gis);

}
