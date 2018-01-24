package com.glaf.datamgr.service;


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

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Service("com.glaf.datamgr.service.systemDBFuncService")
@Transactional(readOnly = true) 
public class SystemDBFuncServiceImpl implements SystemDBFuncService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemDBFuncMapper systemDBFuncMapper;

	public SystemDBFuncServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SystemDBFunc> list) {
		for (SystemDBFunc systemDBFunc : list) {
		   if (StringUtils.isEmpty(systemDBFunc.getId())) {
			systemDBFunc.setId(idGenerator.getNextId("SYSTEM_DB_FUNC_"));
		   }
		}
		
		int batch_size = 100;
                List<SystemDBFunc> rows = new ArrayList<SystemDBFunc>(batch_size);

		for (SystemDBFunc bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//		systemDBFuncMapper.bulkInsertSystemDBFunc_oracle(list);
				} else {
			//		systemDBFuncMapper.bulkInsertSystemDBFunc(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	systemDBFuncMapper.bulkInsertSystemDBFunc_oracle(list);
			} else {
			//	systemDBFuncMapper.bulkInsertSystemDBFunc(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		systemDBFuncMapper.deleteSystemDBFuncById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    systemDBFuncMapper.deleteSystemDBFuncById(id);
		}
	    }
	}

	public int count(SystemDBFuncQuery query) {
		return systemDBFuncMapper.getSystemDBFuncCount(query);
	}

	public List<SystemDBFunc> list(SystemDBFuncQuery query) {
		List<SystemDBFunc> list = systemDBFuncMapper.getSystemDBFuncs(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSystemDBFuncCountByQueryCriteria(SystemDBFuncQuery query) {
		return systemDBFuncMapper.getSystemDBFuncCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemDBFunc> getSystemDBFuncsByQueryCriteria(int start, int pageSize,
			SystemDBFuncQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemDBFunc> rows = sqlSessionTemplate.selectList(
				"getSystemDBFuncs", query, rowBounds);
		return rows;
	}


	public SystemDBFunc getSystemDBFunc(String id) {
	        if(id == null){
		    return null;
		}
		SystemDBFunc systemDBFunc = systemDBFuncMapper.getSystemDBFuncById(id);
		return systemDBFunc;
	}

	@Transactional
	public void save(SystemDBFunc systemDBFunc) {
           if (StringUtils.isEmpty(systemDBFunc.getId())) {
	        systemDBFunc.setId(idGenerator.getNextId("SYSTEM_DB_FUNC_"));
		//systemDBFunc.setCreateDate(new Date());
		//systemDBFunc.setDeleteFlag(0);
		systemDBFuncMapper.insertSystemDBFunc(systemDBFunc);
	       } else {
		systemDBFuncMapper.updateSystemDBFunc(systemDBFunc);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.SystemDBFuncMapper")
	public void setSystemDBFuncMapper(SystemDBFuncMapper systemDBFuncMapper) {
		this.systemDBFuncMapper = systemDBFuncMapper;
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
