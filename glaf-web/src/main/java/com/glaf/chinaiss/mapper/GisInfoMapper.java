package com.glaf.chinaiss.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.chinaiss.model.GisInfo;

@Component
public interface GisInfoMapper {

	int getProjDocNum(String id);

	List<GisInfo> getgisInfoListById(String id);

	List<GisInfo> getgisInfoListByName(String name);

	List<GisInfo> getGisGinttTree(GisInfo gis);

}
