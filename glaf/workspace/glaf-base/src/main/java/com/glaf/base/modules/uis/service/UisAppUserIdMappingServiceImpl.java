package com.glaf.base.modules.uis.service;


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

import com.glaf.base.modules.uis.mapper.*;
import com.glaf.base.modules.uis.domain.*;
import com.glaf.base.modules.uis.query.*;

@Service("com.glaf.base.modules.uis.service.uisAppUserIdMappingService")
@Transactional(readOnly = true) 
public class UisAppUserIdMappingServiceImpl implements UisAppUserIdMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UisAppUserIdMappingMapper uisAppUserIdMappingMapper;

	public UisAppUserIdMappingServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		uisAppUserIdMappingMapper.deleteUisAppUserIdMappingById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    uisAppUserIdMappingMapper.deleteUisAppUserIdMappingById(id);
		}
	    }
	}

	public int count(UisAppUserIdMappingQuery query) {
		return uisAppUserIdMappingMapper.getUisAppUserIdMappingCount(query);
	}

	public List<UisAppUserIdMapping> list(UisAppUserIdMappingQuery query) {
		List<UisAppUserIdMapping> list = uisAppUserIdMappingMapper.getUisAppUserIdMappings(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getUisAppUserIdMappingCountByQueryCriteria(UisAppUserIdMappingQuery query) {
		return uisAppUserIdMappingMapper.getUisAppUserIdMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UisAppUserIdMapping> getUisAppUserIdMappingsByQueryCriteria(int start, int pageSize,
			UisAppUserIdMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UisAppUserIdMapping> rows = sqlSessionTemplate.selectList(
				"getUisAppUserIdMappings", query, rowBounds);
		return rows;
	}


	public UisAppUserIdMapping getUisAppUserIdMapping(Long id) {
	        if(id == null){
		    return null;
		}
		UisAppUserIdMapping uisAppUserIdMapping = uisAppUserIdMappingMapper.getUisAppUserIdMappingById(id);
		return uisAppUserIdMapping;
	}

	@Transactional
	public void save(UisAppUserIdMapping uisAppUserIdMapping) {
            if ( uisAppUserIdMapping.getId()  == null) {
	        uisAppUserIdMapping.setId(idGenerator.nextId("UIS_APP_USERID_MAPPING"));
		//uisAppUserIdMapping.setCreateDate(new Date());
		//uisAppUserIdMapping.setDeleteFlag(0);
		uisAppUserIdMappingMapper.insertUisAppUserIdMapping(uisAppUserIdMapping);
	       } else {
		uisAppUserIdMappingMapper.updateUisAppUserIdMapping(uisAppUserIdMapping);
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

	@javax.annotation.Resource(name = "com.glaf.base.modules.uis.mapper.UisAppUserIdMappingMapper")
	public void setUisAppUserIdMappingMapper(UisAppUserIdMappingMapper uisAppUserIdMappingMapper) {
		this.uisAppUserIdMappingMapper = uisAppUserIdMappingMapper;
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
