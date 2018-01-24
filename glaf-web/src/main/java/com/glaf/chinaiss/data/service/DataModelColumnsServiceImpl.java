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

@Service("com.glaf.chinaiss.data.service.dataModelColumnsService")
@Transactional(readOnly = true)
public class DataModelColumnsServiceImpl implements DataModelColumnsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataModelColumnsMapper dataModelColumnsMapper;

	public DataModelColumnsServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataModelColumns> list) {
		for (DataModelColumns dataModelColumns : list) {
			if (StringUtils.isEmpty(dataModelColumns.getId())) {
				dataModelColumns.setId(idGenerator.getNextId("DATA_MODEL_COLUMNS"));
			}
		}

		int batch_size = 100;
		List<DataModelColumns> rows = new ArrayList<DataModelColumns>(batch_size);

		for (DataModelColumns bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					// dataModelColumnsMapper.bulkInsertDataModelColumns_oracle(list);
				} else {
					// dataModelColumnsMapper.bulkInsertDataModelColumns(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				// dataModelColumnsMapper.bulkInsertDataModelColumns_oracle(list);
			} else {
				// dataModelColumnsMapper.bulkInsertDataModelColumns(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataModelColumnsMapper.deleteDataModelColumnsById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataModelColumnsMapper.deleteDataModelColumnsById(id);
			}
		}
	}

	public int count(DataModelColumnsQuery query) {
		return dataModelColumnsMapper.getDataModelColumnsCount(query);
	}

	public List<DataModelColumns> list(DataModelColumnsQuery query) {
		List<DataModelColumns> list = dataModelColumnsMapper.getDataModelColumnss(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataModelColumnsCountByQueryCriteria(DataModelColumnsQuery query) {
		return dataModelColumnsMapper.getDataModelColumnsCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataModelColumns> getDataModelColumnssByQueryCriteria(int start, int pageSize,
			DataModelColumnsQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataModelColumns> rows = sqlSessionTemplate.selectList("getDataModelColumnss", query, rowBounds);
		return rows;
	}

	public DataModelColumns getDataModelColumns(String id) {
		if (id == null) {
			return null;
		}
		DataModelColumns dataModelColumns = dataModelColumnsMapper.getDataModelColumnsById(id);
		return dataModelColumns;
	}

	@Transactional
	public void save(DataModelColumns dataModelColumns) {
		if (StringUtils.isEmpty(dataModelColumns.getId())) {
			dataModelColumns.setId(idGenerator.getNextId("DATA_MODEL_COLUMNS"));
			// dataModelColumns.setCreateDate(new Date());
			// dataModelColumns.setDeleteFlag(0);
			dataModelColumnsMapper.insertDataModelColumns(dataModelColumns);
		} else {
			dataModelColumnsMapper.updateDataModelColumns(dataModelColumns);
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

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.mapper.DataModelColumnsMapper")
	public void setDataModelColumnsMapper(DataModelColumnsMapper dataModelColumnsMapper) {
		this.dataModelColumnsMapper = dataModelColumnsMapper;
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
