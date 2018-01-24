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

@Service("com.glaf.theme.service.sysThemeTmpService")
@Transactional(readOnly = true) 
public class SysThemeTmpServiceImpl implements SysThemeTmpService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpMapper sysThemeTmpMapper;

	public SysThemeTmpServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SysThemeTmp> list) {
		for (SysThemeTmp sysThemeTmp : list) {
		   if (StringUtils.isEmpty(sysThemeTmp.getThemeTmpId())) {
			sysThemeTmp.setThemeTmpId(idGenerator.getNextId("SYS_THEME_TMP_"));
		   }
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysThemeTmpMapper.deleteSysThemeTmpById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> themeTmpIds) {
	    if(themeTmpIds != null && !themeTmpIds.isEmpty()){
		for(String id : themeTmpIds){
		    sysThemeTmpMapper.deleteSysThemeTmpById(id);
		}
	    }
	}

	public int count(SysThemeTmpQuery query) {
		return sysThemeTmpMapper.getSysThemeTmpCount(query);
	}

	public List<SysThemeTmp> list(SysThemeTmpQuery query) {
		List<SysThemeTmp> list = sysThemeTmpMapper.getSysThemeTmps(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysThemeTmpCountByQueryCriteria(SysThemeTmpQuery query) {
		return sysThemeTmpMapper.getSysThemeTmpCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysThemeTmp> getSysThemeTmpsByQueryCriteria(int start, int pageSize,
			SysThemeTmpQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysThemeTmp> rows = sqlSessionTemplate.selectList(
				"getSysThemeTmps", query, rowBounds);
		return rows;
	}


	public SysThemeTmp getSysThemeTmp(String id) {
	        if(id == null){
		    return null;
		}
		SysThemeTmp sysThemeTmp = sysThemeTmpMapper.getSysThemeTmpById(id);
		return sysThemeTmp;
	}

	@Transactional
	public void save(SysThemeTmp sysThemeTmp) {
           if (StringUtils.isEmpty(sysThemeTmp.getThemeTmpId())) {
	        sysThemeTmp.setThemeTmpId(idGenerator.getNextId("SYS_THEME_TMP_"));
		//sysThemeTmp.setCreateDate(new Date());
		//sysThemeTmp.setDeleteFlag(0);
		sysThemeTmpMapper.insertSysThemeTmp(sysThemeTmp);
	       } else {
		sysThemeTmpMapper.updateSysThemeTmp(sysThemeTmp);
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

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpMapper")
	public void setSysThemeTmpMapper(SysThemeTmpMapper sysThemeTmpMapper) {
		this.sysThemeTmpMapper = sysThemeTmpMapper;
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
		public SysThemeTmp getThumbnailById(String id) {
			if(id == null){
			    return null;
			}
			SysThemeTmp sysThemeTmp = sysThemeTmpMapper.getThumbnailById(id);
			return sysThemeTmp;
		}

}
