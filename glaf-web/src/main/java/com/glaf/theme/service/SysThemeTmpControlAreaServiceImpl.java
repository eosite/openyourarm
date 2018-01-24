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

@Service("com.glaf.theme.service.sysThemeTmpControlAreaService")
@Transactional(readOnly = true) 
public class SysThemeTmpControlAreaServiceImpl implements SysThemeTmpControlAreaService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpControlAreaMapper sysThemeTmpControlAreaMapper;

	public SysThemeTmpControlAreaServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmpControlArea> list) {
		for (SysThemeTmpControlArea sysThemeTmpControlArea : list) {
		   if (StringUtils.isEmpty(sysThemeTmpControlArea.getControlAreaId())) {
			sysThemeTmpControlArea.setControlAreaId(idGenerator.getNextId("SYS_THEME_TMP_CONTROL_AREA_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			sysThemeTmpControlAreaMapper.bulkInsertSysThemeTmpControlArea_oracle(list);
		} else {
//			sysThemeTmpControlAreaMapper.bulkInsertSysThemeTmpControlArea(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpControlAreaMapper.deleteSysThemeTmpControlAreaById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ControlAreaIds) {
	    if(ControlAreaIds != null && !ControlAreaIds.isEmpty()){
		for(String id : ControlAreaIds){
		    sysThemeTmpControlAreaMapper.deleteSysThemeTmpControlAreaById(id);
		}
	    }
	}

	public int count(SysThemeTmpControlAreaQuery query) {
		return sysThemeTmpControlAreaMapper.getSysThemeTmpControlAreaCount(query);
	}

	public List<SysThemeTmpControlArea> list(SysThemeTmpControlAreaQuery query) {
		List<SysThemeTmpControlArea> list = sysThemeTmpControlAreaMapper.getSysThemeTmpControlAreas(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpControlAreaCountByQueryCriteria(SysThemeTmpControlAreaQuery query) {
		return sysThemeTmpControlAreaMapper.getSysThemeTmpControlAreaCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmpControlArea> getSysThemeTmpControlAreasByQueryCriteria(int start, int pageSize,
			SysThemeTmpControlAreaQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmpControlArea> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmpControlAreas", query, rowBounds);
		return rows;
	}


	public SysThemeTmpControlArea getSysThemeTmpControlArea(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmpControlArea sysThemeTmpControlArea = sysThemeTmpControlAreaMapper.getSysThemeTmpControlAreaById(id);
		return sysThemeTmpControlArea;
	}

	@Transactional
	public void save(SysThemeTmpControlArea sysThemeTmpControlArea) {
           if (StringUtils.isEmpty(sysThemeTmpControlArea.getControlAreaId())) {
	        sysThemeTmpControlArea.setControlAreaId(idGenerator.getNextId("SYS_THEME_TMP_CONTROL_AREA_"));
		//sysThemeTmpControlArea.setCreateDate(new Date());
		//sysThemeTmpControlArea.setDeleteFlag(0);
		sysThemeTmpControlAreaMapper.insertSysThemeTmpControlArea(sysThemeTmpControlArea);
	       } else {
		sysThemeTmpControlAreaMapper.updateSysThemeTmpControlArea(sysThemeTmpControlArea);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpControlAreaMapper")
	public void setSysThemeTmpControlAreaMapper(SysThemeTmpControlAreaMapper sysThemeTmpControlAreaMapper) {
		this.sysThemeTmpControlAreaMapper = sysThemeTmpControlAreaMapper;
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
