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
import com.glaf.core.util.DBUtils;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DataSetSyntheticService;

@Service("dataSetSyntheticService")
@Transactional(readOnly = true)
public class DataSetSyntheticServiceImpl implements DataSetSyntheticService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetSyntheticMapper dataSetSyntheticMapper;

	public DataSetSyntheticServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataSetSynthetic> list) {
		for (DataSetSynthetic dataSetSynthetic : list) {
			if (dataSetSynthetic.getId() == 0) {
				dataSetSynthetic.setId(idGenerator.nextId("SYS_DATASET_SYNTHETIC"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			dataSetSyntheticMapper.bulkInsertDataSetSynthetic_oracle(list);
		} else {
			dataSetSyntheticMapper.bulkInsertDataSetSynthetic(list);
		}
	}

	public int count(DataSetSyntheticQuery query) {
		return dataSetSyntheticMapper.getDataSetSyntheticCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dataSetSyntheticMapper.deleteDataSetSyntheticById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				dataSetSyntheticMapper.deleteDataSetSyntheticById(id);
			}
		}
	}

	public DataSetSynthetic getDataSetSynthetic(Long id) {
		if (id == null) {
			return null;
		}
		DataSetSynthetic dataSetSynthetic = dataSetSyntheticMapper.getDataSetSyntheticById(id);
		return dataSetSynthetic;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetSyntheticCountByQueryCriteria(DataSetSyntheticQuery query) {
		return dataSetSyntheticMapper.getDataSetSyntheticCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSetSynthetic> getDataSetSyntheticsByQueryCriteria(int start, int pageSize,
			DataSetSyntheticQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSetSynthetic> rows = sqlSessionTemplate.selectList("getDataSetSynthetics", query, rowBounds);
		return rows;
	}

	public List<DataSetSynthetic> list(DataSetSyntheticQuery query) {
		List<DataSetSynthetic> list = dataSetSyntheticMapper.getDataSetSynthetics(query);
		return list;
	}

	@Transactional
	public void resetAllDataSetSyntheticStatus() {
		dataSetSyntheticMapper.resetAllDataSetSyntheticStatus();
	}

	@Transactional
	public void save(DataSetSynthetic dataSetSynthetic) {
		if (StringUtils.isNotEmpty(dataSetSynthetic.getSyncColumns())) {
			dataSetSynthetic.setSyncColumns(dataSetSynthetic.getSyncColumns().toLowerCase());
		}
		if (dataSetSynthetic.getId() == 0) {
			dataSetSynthetic.setId(idGenerator.nextId("SYS_DATASET_SYNTHETIC"));
			dataSetSynthetic.setCreateTime(new Date());
			dataSetSyntheticMapper.insertDataSetSynthetic(dataSetSynthetic);
		} else {
			dataSetSyntheticMapper.updateDataSetSynthetic(dataSetSynthetic);
		}
	}

	@Transactional
	public void updateDataSetSyntheticStatus(DataSetSynthetic model) {
		model.setSyncTime(new java.util.Date());
		dataSetSyntheticMapper.updateDataSetSyntheticStatus(model);
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

	@javax.annotation.Resource
	public void setDataSetSyntheticMapper(DataSetSyntheticMapper dataSetSyntheticMapper) {
		this.dataSetSyntheticMapper = dataSetSyntheticMapper;
	}

}
