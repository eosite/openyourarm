package com.glaf.chinaiss.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.glaf.chinaiss.mapper.GisInfoMapper;
import com.glaf.chinaiss.model.GisInfo;

@Service("gisInfoService")
public class GisInfoServiceImpl implements GisInfoService {

	protected GisInfoMapper gisInfoMapper;

	@Override
	public List<GisInfo> getGisGinttTree(GisInfo gis) {
		return gisInfoMapper.getGisGinttTree(gis);
	}

	@Override
	public List<GisInfo> getgisInfoListById(String id) {
		return gisInfoMapper.getgisInfoListById(id);
	}

	@Override
	public List<GisInfo> getgisInfoListByName(String name) {
		return gisInfoMapper.getgisInfoListByName(name);
	}

	@Override
	public int getProjDocNum(String id) {
		return gisInfoMapper.getProjDocNum(id);
	}

	@Resource
	public void setGisInfoMapper(GisInfoMapper gisInfoMapper) {
		this.gisInfoMapper = gisInfoMapper;
	}

}
