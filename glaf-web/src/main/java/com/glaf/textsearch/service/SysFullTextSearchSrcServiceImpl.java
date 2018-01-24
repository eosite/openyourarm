package com.glaf.textsearch.service;


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

import com.glaf.textsearch.mapper.*;
import com.glaf.textsearch.domain.*;
import com.glaf.textsearch.query.*;

@Service("com.glaf.textsearch.service.sysFullTextSearchSrcService")
@Transactional(readOnly = true) 
public class SysFullTextSearchSrcServiceImpl implements SysFullTextSearchSrcService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysFullTextSearchSrcMapper sysFullTextSearchSrcMapper;

	public SysFullTextSearchSrcServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		sysFullTextSearchSrcMapper.deleteSysFullTextSearchSrcById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    sysFullTextSearchSrcMapper.deleteSysFullTextSearchSrcById(id);
		}
	    }
	}

	public int count(SysFullTextSearchSrcQuery query) {
		return sysFullTextSearchSrcMapper.getSysFullTextSearchSrcCount(query);
	}

	public List<SysFullTextSearchSrc> list(SysFullTextSearchSrcQuery query) {
		List<SysFullTextSearchSrc> list = sysFullTextSearchSrcMapper.getSysFullTextSearchSrcs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSysFullTextSearchSrcCountByQueryCriteria(SysFullTextSearchSrcQuery query) {
		return sysFullTextSearchSrcMapper.getSysFullTextSearchSrcCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysFullTextSearchSrc> getSysFullTextSearchSrcsByQueryCriteria(int start, int pageSize,
			SysFullTextSearchSrcQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysFullTextSearchSrc> rows = sqlSessionTemplate.selectList(
				"getSysFullTextSearchSrcs", query, rowBounds);
		return rows;
	}


	public SysFullTextSearchSrc getSysFullTextSearchSrc(String id) {
	        if(id == null){
		    return null;
		}
		SysFullTextSearchSrc sysFullTextSearchSrc = sysFullTextSearchSrcMapper.getSysFullTextSearchSrcById(id);
		return sysFullTextSearchSrc;
	}

	@Transactional
	public void save(SysFullTextSearchSrc sysFullTextSearchSrc) {
           if (StringUtils.isEmpty(sysFullTextSearchSrc.getId())) {
	        sysFullTextSearchSrc.setId(idGenerator.getNextId("SYS_FULLTEXTSEARCH_SRC"));
		//sysFullTextSearchSrc.setCreateDate(new Date());
		//sysFullTextSearchSrc.setDeleteFlag(0);
		sysFullTextSearchSrcMapper.insertSysFullTextSearchSrc(sysFullTextSearchSrc);
	       } else {
		sysFullTextSearchSrcMapper.updateSysFullTextSearchSrc(sysFullTextSearchSrc);
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

	@javax.annotation.Resource(name = "com.glaf.textsearch.mapper.SysFullTextSearchSrcMapper")
	public void setSysFullTextSearchSrcMapper(SysFullTextSearchSrcMapper sysFullTextSearchSrcMapper) {
		this.sysFullTextSearchSrcMapper = sysFullTextSearchSrcMapper;
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
