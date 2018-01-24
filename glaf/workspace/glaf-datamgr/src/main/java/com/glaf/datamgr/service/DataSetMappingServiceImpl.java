package com.glaf.datamgr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.datamgr.domain.DataSetMapping;
import com.glaf.datamgr.mapper.DataSetMappingMapper;
import com.glaf.datamgr.query.DataSetMappingQuery;

@Service("com.glaf.datamgr.service.dataSetMappingService")
@Transactional(readOnly = true)
public class DataSetMappingServiceImpl implements DataSetMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetMappingMapper dataSetMappingMapper;

	protected DataSetMappingItemService dataSetMappingItemService;

	public DataSetMappingServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataSetMapping> list) {
		for (DataSetMapping dataSetMapping : list) {
			if (StringUtils.isEmpty(dataSetMapping.getId())) {
				dataSetMapping.setId(idGenerator.getNextId("SYS_DATASET_MAPPING"));
			}
		}

		int batch_size = 100;
		List<DataSetMapping> rows = new ArrayList<DataSetMapping>(batch_size);

		for (DataSetMapping bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					// dataSetMappingMapper.bulkInsertDataSetMapping_oracle(list);
				} else {
					// dataSetMappingMapper.bulkInsertDataSetMapping(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				// dataSetMappingMapper.bulkInsertDataSetMapping_oracle(list);
			} else {
				// dataSetMappingMapper.bulkInsertDataSetMapping(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataSetMappingMapper.deleteDataSetMappingById(id);
		}
	}

	@Transactional
	public void deleteByIdWithItems(String id) {
		if (id != null) {
			this.dataSetMappingItemService.deleteByParentId(id);
			dataSetMappingMapper.deleteDataSetMappingById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataSetMappingMapper.deleteDataSetMappingById(id);
			}
		}
	}

	public int count(DataSetMappingQuery query) {
		return dataSetMappingMapper.getDataSetMappingCount(query);
	}

	public List<DataSetMapping> list(DataSetMappingQuery query) {
		List<DataSetMapping> list = dataSetMappingMapper.getDataSetMappings(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetMappingCountByQueryCriteria(DataSetMappingQuery query) {
		return dataSetMappingMapper.getDataSetMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSetMapping> getDataSetMappingsByQueryCriteria(int start, int pageSize, DataSetMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSetMapping> rows = sqlSessionTemplate.selectList("getDataSetMappings", query, rowBounds);
		return rows;
	}

	public DataSetMapping getDataSetMapping(String id) {
		if (id == null) {
			return null;
		}
		DataSetMapping dataSetMapping = dataSetMappingMapper.getDataSetMappingById(id);
		return dataSetMapping;
	}

	@Transactional
	public void save(DataSetMapping dataSetMapping) {
		if (StringUtils.isEmpty(dataSetMapping.getId())) {
			dataSetMapping.setId(idGenerator.getNextId("SYS_DATASET_MAPPING"));
			// dataSetMapping.setCreateDate(new Date());
			// dataSetMapping.setDeleteFlag(0);
			dataSetMappingMapper.insertDataSetMapping(dataSetMapping);
		} else {
			dataSetMappingMapper.updateDataSetMapping(dataSetMapping);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.DataSetMappingMapper")
	public void setDataSetMappingMapper(DataSetMappingMapper dataSetMappingMapper) {
		this.dataSetMappingMapper = dataSetMappingMapper;
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
	public void setDataSetMappingItemService(DataSetMappingItemService dataSetMappingItemService) {
		this.dataSetMappingItemService = dataSetMappingItemService;
	}

}
