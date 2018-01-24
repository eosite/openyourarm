package com.glaf.theme.service;


import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.theme.mapper.*;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

@Service("com.glaf.theme.service.sysThemeTmpLayoutAreaService")
@Transactional(readOnly = true) 
public class SysThemeTmpLayoutAreaServiceImpl implements SysThemeTmpLayoutAreaService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpLayoutAreaMapper sysThemeTmpLayoutAreaMapper;

	public SysThemeTmpLayoutAreaServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpLayoutArea> list) {
		for (SysThemeTmpLayoutArea sysThemeTmpLayoutArea : list) {
		   if (StringUtils.isEmpty(sysThemeTmpLayoutArea.getAreaId())) {
			sysThemeTmpLayoutArea.setAreaId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_AREA_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpLayoutAreaMapper.bulkInsertSysThemeTmpLayoutArea_oracle(list);
		} else {
//			sysThemeTmpLayoutAreaMapper.bulkInsertSysThemeTmpLayoutArea(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpLayoutAreaMapper.deleteSysThemeTmpLayoutAreaById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> areaIds) {
	    if(areaIds != null && !areaIds.isEmpty()){
		for(String id : areaIds){
		    sysThemeTmpLayoutAreaMapper.deleteSysThemeTmpLayoutAreaById(id);
		}
	    }
	}

	public int count(SysThemeTmpLayoutAreaQuery query) {
		return sysThemeTmpLayoutAreaMapper.getSysThemeTmpLayoutAreaCount(query);
	}

	public List<SysThemeTmpLayoutArea> list(SysThemeTmpLayoutAreaQuery query) {
		List<SysThemeTmpLayoutArea> list = sysThemeTmpLayoutAreaMapper.getSysThemeTmpLayoutAreas(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpLayoutAreaCountByQueryCriteria(SysThemeTmpLayoutAreaQuery query) {
		return sysThemeTmpLayoutAreaMapper.getSysThemeTmpLayoutAreaCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpLayoutArea> getSysThemeTmpLayoutAreasByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutAreaQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpLayoutArea> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpLayoutAreas", query, rowBounds);
		return rows;
	}


	public SysThemeTmpLayoutArea getSysThemeTmpLayoutArea(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpLayoutArea sysThemeTmpLayoutArea = sysThemeTmpLayoutAreaMapper.getSysThemeTmpLayoutAreaById(id);
		return sysThemeTmpLayoutArea;
	}

	@Transactional
	public void save(SysThemeTmpLayoutArea sysThemeTmpLayoutArea) {
           if (StringUtils.isEmpty(sysThemeTmpLayoutArea.getAreaId())) {
	        sysThemeTmpLayoutArea.setAreaId(idGenerator.getNextId("SYS_THEME_TMP_LAYOUT_AREA_"));
		//sysThemeTmpLayoutArea.setCreateDate(new Date());
		//sysThemeTmpLayoutArea.setDeleteFlag(0);
		sysThemeTmpLayoutAreaMapper.insertSysThemeTmpLayoutArea(sysThemeTmpLayoutArea);
	       } else {
		sysThemeTmpLayoutAreaMapper.updateSysThemeTmpLayoutArea(sysThemeTmpLayoutArea);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpLayoutAreaMapper")
	public void setSysThemeTmpLayoutAreaMapper(SysThemeTmpLayoutAreaMapper sysThemeTmpLayoutAreaMapper) {
		this.sysThemeTmpLayoutAreaMapper = sysThemeTmpLayoutAreaMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

        @javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
