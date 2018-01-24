package com.glaf.theme.service;


import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.theme.mapper.*;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

@Service("com.glaf.theme.service.sysThemeTmpLayoutService")
@Transactional(readOnly = true) 
public class SysThemeTmpLayoutServiceImpl implements SysThemeTmpLayoutService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpLayoutMapper sysThemeTmpLayoutMapper;
	
	@Autowired
	protected SysThemeTmpLayoutAreaMapper sysThemeTmpLayoutAreaMapper;

	public SysThemeTmpLayoutServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpLayout> list) {
		for (SysThemeTmpLayout sysThemeTmpLayout : list) {
		   if (StringUtils.isEmpty(sysThemeTmpLayout.getLayoutId())) {
			sysThemeTmpLayout.setLayoutId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpLayoutMapper.bulkInsertSysThemeTmpLayout_oracle(list);
		} else {
//			sysThemeTmpLayoutMapper.bulkInsertSysThemeTmpLayout(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpLayoutMapper.deleteSysThemeTmpLayoutById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> layoutIds) {
	    if(layoutIds != null && !layoutIds.isEmpty()){
		for(String id : layoutIds){
		    sysThemeTmpLayoutMapper.deleteSysThemeTmpLayoutById(id);
		}
	    }
	}

	public int count(SysThemeTmpLayoutQuery query) {
		return sysThemeTmpLayoutMapper.getSysThemeTmpLayoutCount(query);
	}

	public List<SysThemeTmpLayout> list(SysThemeTmpLayoutQuery query) {
		List<SysThemeTmpLayout> list = sysThemeTmpLayoutMapper.getSysThemeTmpLayouts(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpLayoutCountByQueryCriteria(SysThemeTmpLayoutQuery query) {
		return sysThemeTmpLayoutMapper.getSysThemeTmpLayoutCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpLayout> getSysThemeTmpLayoutsByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpLayout> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpLayouts", query, rowBounds);
		return rows;
	}


	public SysThemeTmpLayout getSysThemeTmpLayout(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpLayout sysThemeTmpLayout = sysThemeTmpLayoutMapper.getSysThemeTmpLayoutById(id);
		return sysThemeTmpLayout;
	}

	@Transactional
	public void save(SysThemeTmpLayout sysThemeTmpLayout) {
           if (StringUtils.isEmpty(sysThemeTmpLayout.getLayoutId())) {
	        sysThemeTmpLayout.setLayoutId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_"));
		//sysThemeTmpLayout.setCreateDate(new Date());
		//sysThemeTmpLayout.setDeleteFlag(0);
		sysThemeTmpLayoutMapper.insertSysThemeTmpLayout(sysThemeTmpLayout);
	       } else {
		sysThemeTmpLayoutMapper.updateSysThemeTmpLayout(sysThemeTmpLayout);
	      }
	}
	
	@Override
	public void saveArea(SysThemeTmpLayoutArea sysThemeTmpLayoutArea) {
		// TODO Auto-generated method stub
		sysThemeTmpLayoutArea.setAreaId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_AREA_"));
		sysThemeTmpLayoutAreaMapper.insertSysThemeTmpLayoutArea(sysThemeTmpLayoutArea);
	}

	@Override
	public void save(SysThemeTmpLayout sysThemeTmpLayout, JSONArray layoutAreaDatas) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(sysThemeTmpLayout.getLayoutId())) {
	        sysThemeTmpLayout.setLayoutId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_"));
			//sysThemeTmpLayout.setCreateDate(new Date());
			//sysThemeTmpLayout.setDeleteFlag(0);
			sysThemeTmpLayoutMapper.insertSysThemeTmpLayout(sysThemeTmpLayout);
			String atorid = sysThemeTmpLayout.getCreateBy();
			String layoutId = sysThemeTmpLayout.getLayoutId();
			Date nowDate = new Date();
			for(int i = 0 ; i < layoutAreaDatas.size() ; i ++){
				JSONObject layoutArea = layoutAreaDatas.getJSONObject(i);
				SysThemeTmpLayoutArea sysThemeTmpLayoutArea = new SysThemeTmpLayoutArea();
				sysThemeTmpLayoutArea.setAreaCode(layoutArea.getString("areaCode"));
				sysThemeTmpLayoutArea.setAreaName(layoutArea.getString("areaName"));
				sysThemeTmpLayoutArea.setCompType(layoutArea.getString("compType"));
				sysThemeTmpLayoutArea.setElemCode(layoutArea.getString("elemCode"));
				sysThemeTmpLayoutArea.setSelectorExp(layoutArea.getString("selectorExp"));
				sysThemeTmpLayoutArea.setCreateBy(atorid);
				sysThemeTmpLayoutArea.setCreateTime(nowDate);
				sysThemeTmpLayoutArea.setLayoutId(layoutId);
				saveArea(sysThemeTmpLayoutArea);
			}
			
       } else {
    	   	sysThemeTmpLayoutMapper.updateSysThemeTmpLayout(sysThemeTmpLayout);
       }
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	 
	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpLayoutMapper")
	public void setSysThemeTmpLayoutMapper(SysThemeTmpLayoutMapper sysThemeTmpLayoutMapper) {
		this.sysThemeTmpLayoutMapper = sysThemeTmpLayoutMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

        @javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}


		@Override
		public SysThemeTmpLayout getThumbnailById(String id) {
			if(id == null){
			    return null;
			}
			
			return sysThemeTmpLayoutMapper.getThumbnailById(id);
		}


		


		

}
