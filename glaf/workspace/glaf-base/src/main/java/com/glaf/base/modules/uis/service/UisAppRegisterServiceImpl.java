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

@Service("com.glaf.base.modules.uis.service.uisAppRegisterService")
@Transactional(readOnly = true) 
public class UisAppRegisterServiceImpl implements UisAppRegisterService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UisAppRegisterMapper uisAppRegisterMapper;

	public UisAppRegisterServiceImpl() {

	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		uisAppRegisterMapper.deleteUisAppRegisterById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> appIds) {
	    if(appIds != null && !appIds.isEmpty()){
		for(String id : appIds){
		    uisAppRegisterMapper.deleteUisAppRegisterById(id);
		}
	    }
	}

	public int count(UisAppRegisterQuery query) {
		return uisAppRegisterMapper.getUisAppRegisterCount(query);
	}

	public List<UisAppRegister> list(UisAppRegisterQuery query) {
		List<UisAppRegister> list = uisAppRegisterMapper.getUisAppRegisters(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getUisAppRegisterCountByQueryCriteria(UisAppRegisterQuery query) {
		return uisAppRegisterMapper.getUisAppRegisterCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UisAppRegister> getUisAppRegistersByQueryCriteria(int start, int pageSize,
			UisAppRegisterQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UisAppRegister> rows = sqlSessionTemplate.selectList(
				"getUisAppRegisters", query, rowBounds);
		return rows;
	}


	public UisAppRegister getUisAppRegister(String id) {
	        if(id == null){
		    return null;
		}
		UisAppRegister uisAppRegister = uisAppRegisterMapper.getUisAppRegisterById(id);
		return uisAppRegister;
	}

	@Transactional
	public void save(UisAppRegister uisAppRegister) {
           if (StringUtils.isEmpty(uisAppRegister.getAppId())) {
	        uisAppRegister.setAppId(idGenerator.getNextId("UIS_APP_REGISTER"));
		//uisAppRegister.setCreateDate(new Date());
		//uisAppRegister.setDeleteFlag(0);
		uisAppRegisterMapper.insertUisAppRegister(uisAppRegister);
	       } else {
		uisAppRegisterMapper.updateUisAppRegister(uisAppRegister);
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

	@javax.annotation.Resource(name = "com.glaf.base.modules.uis.mapper.UisAppRegisterMapper")
	public void setUisAppRegisterMapper(UisAppRegisterMapper uisAppRegisterMapper) {
		this.uisAppRegisterMapper = uisAppRegisterMapper;
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
