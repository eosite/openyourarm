package com.glaf.datamgr.service.impl;

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
import com.glaf.datamgr.service.DataItemDefinitionService;

@Service("dataItemDefinitionService")
@Transactional(readOnly = true)
public class DataItemDefinitionServiceImpl implements DataItemDefinitionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataItemDefinitionMapper dataItemDefinitionMapper;

	public DataItemDefinitionServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataItemDefinition> list) {
		for (DataItemDefinition dataItemDefinition : list) {
			if (dataItemDefinition.getId() == null) {
				dataItemDefinition.setId(idGenerator.nextId("SYS_DATA_ITEM_DEF"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			dataItemDefinitionMapper.bulkInsertDataItemDefinition_oracle(list);
		} else {
			dataItemDefinitionMapper.bulkInsertDataItemDefinition(list);
		}
	}

	public int count(DataItemDefinitionQuery query) {
		return dataItemDefinitionMapper.getDataItemDefinitionCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dataItemDefinitionMapper.deleteDataItemDefinitionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				dataItemDefinitionMapper.deleteDataItemDefinitionById(id);
			}
		}
	}

	public DataItemDefinition getDataItemDefinition(Long id) {
		if (id == null) {
			return null;
		}
		DataItemDefinition dataItemDefinition = dataItemDefinitionMapper.getDataItemDefinitionById(id);
		return dataItemDefinition;
	}

	public DataItemDefinition getDataItemDefinitionByCode(String code) {
		if (code == null) {
			return null;
		}
		DataItemDefinition dataItemDefinition = dataItemDefinitionMapper.getDataItemDefinitionByCode(code);
		return dataItemDefinition;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataItemDefinitionCountByQueryCriteria(DataItemDefinitionQuery query) {
		return dataItemDefinitionMapper.getDataItemDefinitionCount(query);
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<DataItemDefinition> getDataItemDefinitions(String category) {
		DataItemDefinitionQuery query = new DataItemDefinitionQuery();
		query.category(category);
		List<DataItemDefinition> list = dataItemDefinitionMapper.getDataItemDefinitions(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	public List<DataItemDefinition> getDataItemDefinitionsByNodeId(long nodeId) {
		DataItemDefinitionQuery query = new DataItemDefinitionQuery();
		query.nodeId(nodeId);
		List<DataItemDefinition> list = dataItemDefinitionMapper.getDataItemDefinitions(query);
		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataItemDefinition> getDataItemDefinitionsByQueryCriteria(int start, int pageSize,
			DataItemDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataItemDefinition> rows = sqlSessionTemplate.selectList("getDataItemDefinitions", query, rowBounds);
		return rows;
	}

	public List<DataItemDefinition> list(DataItemDefinitionQuery query) {
		List<DataItemDefinition> list = dataItemDefinitionMapper.getDataItemDefinitions(query);
		return list;
	}

	@Transactional
	public void save(DataItemDefinition dataItemDefinition) {
		if (dataItemDefinition.getId() == null) {
			dataItemDefinition.setId(idGenerator.nextId("SYS_DATA_ITEM_DEF"));
			dataItemDefinition.setCreateTime(new Date());
			dataItemDefinitionMapper.insertDataItemDefinition(dataItemDefinition);
		} else {
			dataItemDefinitionMapper.updateDataItemDefinition(dataItemDefinition);
		}
	}

	@javax.annotation.Resource
	public void setDataItemDefinitionMapper(DataItemDefinitionMapper dataItemDefinitionMapper) {
		this.dataItemDefinitionMapper = dataItemDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
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
