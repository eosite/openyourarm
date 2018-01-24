package com.glaf.chinaiss.data.service;


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

import com.glaf.chinaiss.data.mapper.*;
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

@Service("com.glaf.chinaiss.data.service.dataModelTablesService")
@Transactional(readOnly = true) 
public class DataModelTablesServiceImpl implements DataModelTablesService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataModelTablesMapper dataModelTablesMapper;

	public DataModelTablesServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<DataModelTables> list) {
		for (DataModelTables dataModelTables : list) {
		   if (StringUtils.isEmpty(dataModelTables.getId())) {
			dataModelTables.setId(idGenerator.getNextId("DATA_MODEL_TABLES"));
		   }
		}
		
		int batch_size = 100;
                List<DataModelTables> rows = new ArrayList<DataModelTables>(batch_size);

		for (DataModelTables bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	dataModelTablesMapper.bulkInsertDataModelTables_oracle(list);
				} else {
				//	dataModelTablesMapper.bulkInsertDataModelTables(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	dataModelTablesMapper.bulkInsertDataModelTables_oracle(list);
			} else {
			//	dataModelTablesMapper.bulkInsertDataModelTables(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		dataModelTablesMapper.deleteDataModelTablesById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    dataModelTablesMapper.deleteDataModelTablesById(id);
		}
	    }
	}

	public int count(DataModelTablesQuery query) {
		return dataModelTablesMapper.getDataModelTablesCount(query);
	}

	public List<DataModelTables> list(DataModelTablesQuery query) {
		List<DataModelTables> list = dataModelTablesMapper.getDataModelTabless(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDataModelTablesCountByQueryCriteria(DataModelTablesQuery query) {
		return dataModelTablesMapper.getDataModelTablesCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataModelTables> getDataModelTablessByQueryCriteria(int start, int pageSize,
			DataModelTablesQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataModelTables> rows = sqlSessionTemplate.selectList(
				"getDataModelTabless", query, rowBounds);
		return rows;
	}


	public DataModelTables getDataModelTables(String id) {
	        if(id == null){
		    return null;
		}
		DataModelTables dataModelTables = dataModelTablesMapper.getDataModelTablesById(id);
		return dataModelTables;
	}

	@Transactional
	public void save(DataModelTables dataModelTables) {
           if (StringUtils.isEmpty(dataModelTables.getId())) {
	        dataModelTables.setId(idGenerator.getNextId("DATA_MODEL_TABLES"));
		//dataModelTables.setCreateDate(new Date());
		//dataModelTables.setDeleteFlag(0);
		dataModelTablesMapper.insertDataModelTables(dataModelTables);
	       } else {
		dataModelTablesMapper.updateDataModelTables(dataModelTables);
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

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.mapper.DataModelTablesMapper")
	public void setDataModelTablesMapper(DataModelTablesMapper dataModelTablesMapper) {
		this.dataModelTablesMapper = dataModelTablesMapper;
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
