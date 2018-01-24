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

@Service("com.glaf.datamgr.service.dataSetMappingItemService")
@Transactional(readOnly = true)
public class DataSetMappingItemServiceImpl implements DataSetMappingItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetMappingItemMapper dataSetMappingItemMapper;

	public DataSetMappingItemServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataSetMappingItem> list) {
		for (DataSetMappingItem dataSetMappingItem : list) {
			if (StringUtils.isEmpty(dataSetMappingItem.getId())) {
				dataSetMappingItem.setId(idGenerator.getNextId("SYS_DATASET_MAPPING_ITEM"));
			}
		}

		int batch_size = 100;
		List<DataSetMappingItem> rows = new ArrayList<DataSetMappingItem>(batch_size);

		for (DataSetMappingItem bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					// dataSetMappingItemMapper.bulkInsertDataSetMappingItem_oracle(list);
				} else {
					// dataSetMappingItemMapper.bulkInsertDataSetMappingItem(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				// dataSetMappingItemMapper.bulkInsertDataSetMappingItem_oracle(list);
			} else {
				// dataSetMappingItemMapper.bulkInsertDataSetMappingItem(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataSetMappingItemMapper.deleteDataSetMappingItemById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataSetMappingItemMapper.deleteDataSetMappingItemById(id);
			}
		}
	}

	public int count(DataSetMappingItemQuery query) {
		return dataSetMappingItemMapper.getDataSetMappingItemCount(query);
	}

	public List<DataSetMappingItem> list(DataSetMappingItemQuery query) {
		List<DataSetMappingItem> list = dataSetMappingItemMapper.getDataSetMappingItems(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetMappingItemCountByQueryCriteria(DataSetMappingItemQuery query) {
		return dataSetMappingItemMapper.getDataSetMappingItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSetMappingItem> getDataSetMappingItemsByQueryCriteria(int start, int pageSize,
			DataSetMappingItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSetMappingItem> rows = sqlSessionTemplate.selectList("getDataSetMappingItems", query, rowBounds);
		return rows;
	}

	public DataSetMappingItem getDataSetMappingItem(String id) {
		if (id == null) {
			return null;
		}
		DataSetMappingItem dataSetMappingItem = dataSetMappingItemMapper.getDataSetMappingItemById(id);
		return dataSetMappingItem;
	}

	@Transactional
	public void save(DataSetMappingItem dataSetMappingItem) {
		if (StringUtils.isEmpty(dataSetMappingItem.getId())) {
			dataSetMappingItem.setId(idGenerator.getNextId("SYS_DATASET_MAPPING_ITEM"));
			dataSetMappingItem.setCreateDate(new Date());
			// dataSetMappingItem.setDeleteFlag(0);
			dataSetMappingItemMapper.insertDataSetMappingItem(dataSetMappingItem);
		} else {
			dataSetMappingItemMapper.updateDataSetMappingItem(dataSetMappingItem);
		}
	}

	@Override
	@Transactional
	public void saveBatch(List<DataSetMappingItem> dataSetMappingItems) {
		if (CollectionUtils.isEmpty(dataSetMappingItems)) {
			return;
		}
		for (DataSetMappingItem item : dataSetMappingItems) {
			this.save(item);
		}
	}

	@Override
	@Transactional
	public void deleteByParentId(String parentId) {
		dataSetMappingItemMapper.deleteByParentId(parentId);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.DataSetMappingItemMapper")
	public void setDataSetMappingItemMapper(DataSetMappingItemMapper dataSetMappingItemMapper) {
		this.dataSetMappingItemMapper = dataSetMappingItemMapper;
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
