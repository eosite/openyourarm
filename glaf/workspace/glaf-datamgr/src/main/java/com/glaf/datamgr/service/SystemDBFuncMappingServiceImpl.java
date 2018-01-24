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

@Service("com.glaf.datamgr.service.systemDBFuncMappingService")
@Transactional(readOnly = true) 
public class SystemDBFuncMappingServiceImpl implements SystemDBFuncMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemDBFuncMappingMapper systemDBFuncMappingMapper;

	public SystemDBFuncMappingServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<SystemDBFuncMapping> list) {
		for (SystemDBFuncMapping systemDBFuncMapping : list) {
		   if (StringUtils.isEmpty(systemDBFuncMapping.getId())) {
			systemDBFuncMapping.setId(idGenerator.getNextId("SYSTEM_DB_FUNC_MAPPING_"));
		   }
		}
		
		int batch_size = 100;
                List<SystemDBFuncMapping> rows = new ArrayList<SystemDBFuncMapping>(batch_size);

		for (SystemDBFuncMapping bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	systemDBFuncMappingMapper.bulkInsertSystemDBFuncMapping_oracle(list);
				} else {
				//	systemDBFuncMappingMapper.bulkInsertSystemDBFuncMapping(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//systemDBFuncMappingMapper.bulkInsertSystemDBFuncMapping_oracle(list);
			} else {
			//	systemDBFuncMappingMapper.bulkInsertSystemDBFuncMapping(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		systemDBFuncMappingMapper.deleteSystemDBFuncMappingById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    systemDBFuncMappingMapper.deleteSystemDBFuncMappingById(id);
		}
	    }
	}

	public int count(SystemDBFuncMappingQuery query) {
		return systemDBFuncMappingMapper.getSystemDBFuncMappingCount(query);
	}

	public List<SystemDBFuncMapping> list(SystemDBFuncMappingQuery query) {
		List<SystemDBFuncMapping> list = systemDBFuncMappingMapper.getSystemDBFuncMappings(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getSystemDBFuncMappingCountByQueryCriteria(SystemDBFuncMappingQuery query) {
		return systemDBFuncMappingMapper.getSystemDBFuncMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemDBFuncMapping> getSystemDBFuncMappingsByQueryCriteria(int start, int pageSize,
			SystemDBFuncMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemDBFuncMapping> rows = sqlSessionTemplate.selectList(
				"getSystemDBFuncMappings", query, rowBounds);
		return rows;
	}


	public SystemDBFuncMapping getSystemDBFuncMapping(String id) {
	        if(id == null){
		    return null;
		}
		SystemDBFuncMapping systemDBFuncMapping = systemDBFuncMappingMapper.getSystemDBFuncMappingById(id);
		return systemDBFuncMapping;
	}

	@Transactional
	public void save(SystemDBFuncMapping systemDBFuncMapping) {
           if (StringUtils.isEmpty(systemDBFuncMapping.getId())) {
	        systemDBFuncMapping.setId(idGenerator.getNextId("SYSTEM_DB_FUNC_MAPPING_"));
		//systemDBFuncMapping.setCreateDate(new Date());
		//systemDBFuncMapping.setDeleteFlag(0);
		systemDBFuncMappingMapper.insertSystemDBFuncMapping(systemDBFuncMapping);
	       } else {
		systemDBFuncMappingMapper.updateSystemDBFuncMapping(systemDBFuncMapping);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.SystemDBFuncMappingMapper")
	public void setSystemDBFuncMappingMapper(SystemDBFuncMappingMapper systemDBFuncMappingMapper) {
		this.systemDBFuncMappingMapper = systemDBFuncMappingMapper;
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
