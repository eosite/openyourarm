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

@Service("com.glaf.chinaiss.data.service.dataModelRelationService")
@Transactional(readOnly = true) 
public class DataModelRelationServiceImpl implements DataModelRelationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataModelRelationMapper dataModelRelationMapper;

	public DataModelRelationServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<DataModelRelation> list) {
		for (DataModelRelation dataModelRelation : list) {
		   if (StringUtils.isEmpty(dataModelRelation.getId())) {
			dataModelRelation.setId(idGenerator.getNextId("DATA_MODEL_RELATION"));
		   }
		}
		
		int batch_size = 100;
                List<DataModelRelation> rows = new ArrayList<DataModelRelation>(batch_size);

		for (DataModelRelation bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					//dataModelRelationMapper.bulkInsertDataModelRelation_oracle(list);
				} else {
					//dataModelRelationMapper.bulkInsertDataModelRelation(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//dataModelRelationMapper.bulkInsertDataModelRelation_oracle(list);
			} else {
				//dataModelRelationMapper.bulkInsertDataModelRelation(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		dataModelRelationMapper.deleteDataModelRelationById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    dataModelRelationMapper.deleteDataModelRelationById(id);
		}
	    }
	}

	public int count(DataModelRelationQuery query) {
		return dataModelRelationMapper.getDataModelRelationCount(query);
	}

	public List<DataModelRelation> list(DataModelRelationQuery query) {
		List<DataModelRelation> list = dataModelRelationMapper.getDataModelRelations(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getDataModelRelationCountByQueryCriteria(DataModelRelationQuery query) {
		return dataModelRelationMapper.getDataModelRelationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataModelRelation> getDataModelRelationsByQueryCriteria(int start, int pageSize,
			DataModelRelationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataModelRelation> rows = sqlSessionTemplate.selectList(
				"getDataModelRelations", query, rowBounds);
		return rows;
	}


	public DataModelRelation getDataModelRelation(String id) {
	        if(id == null){
		    return null;
		}
		DataModelRelation dataModelRelation = dataModelRelationMapper.getDataModelRelationById(id);
		return dataModelRelation;
	}

	@Transactional
	public void save(DataModelRelation dataModelRelation) {
           if (StringUtils.isEmpty(dataModelRelation.getId())) {
	        dataModelRelation.setId(idGenerator.getNextId("DATA_MODEL_RELATION"));
		//dataModelRelation.setCreateDate(new Date());
		//dataModelRelation.setDeleteFlag(0);
		dataModelRelationMapper.insertDataModelRelation(dataModelRelation);
	       } else {
		dataModelRelationMapper.updateDataModelRelation(dataModelRelation);
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

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.mapper.DataModelRelationMapper")
	public void setDataModelRelationMapper(DataModelRelationMapper dataModelRelationMapper) {
		this.dataModelRelationMapper = dataModelRelationMapper;
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
