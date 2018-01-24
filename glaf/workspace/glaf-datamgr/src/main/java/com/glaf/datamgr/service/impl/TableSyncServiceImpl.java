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
import com.glaf.datamgr.service.TableSyncService;

@Service("tableSyncService")
@Transactional(readOnly = true)
public class TableSyncServiceImpl implements TableSyncService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableSyncMapper tableSyncMapper;

	public TableSyncServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TableSync> list) {
		for (TableSync tableSync : list) {
			if (tableSync.getId() == 0) {
				tableSync.setId(idGenerator.nextId("SYS_TABLE_SYNC"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			tableSyncMapper.bulkInsertTableSync_oracle(list);
		} else {
			tableSyncMapper.bulkInsertTableSync(list);
		}
	}

	public int count(TableSyncQuery query) {
		return tableSyncMapper.getTableSyncCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			tableSyncMapper.deleteTableSyncById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				tableSyncMapper.deleteTableSyncById(id);
			}
		}
	}

	public TableSync getTableSync(Long id) {
		if (id == null) {
			return null;
		}
		TableSync tableSync = tableSyncMapper.getTableSyncById(id);
		return tableSync;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableSyncCountByQueryCriteria(TableSyncQuery query) {
		return tableSyncMapper.getTableSyncCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableSync> getTableSyncsByQueryCriteria(int start, int pageSize, TableSyncQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableSync> rows = sqlSessionTemplate.selectList("getTableSyncs", query, rowBounds);
		return rows;
	}

	public List<TableSync> list(TableSyncQuery query) {
		List<TableSync> list = tableSyncMapper.getTableSyncs(query);
		return list;
	}
	
	
	@Transactional
	public void resetAllTableSyncStatus(){
		tableSyncMapper.resetAllTableSyncStatus();
	}

	@Transactional
	public void save(TableSync tableSync) {
		if (StringUtils.isNotEmpty(tableSync.getSyncColumns())) {
			tableSync.setSyncColumns(tableSync.getSyncColumns().toLowerCase());
		}
		if (tableSync.getId() == 0) {
			tableSync.setId(idGenerator.nextId("SYS_TABLE_SYNC"));
			tableSync.setCreateTime(new Date());
			tableSyncMapper.insertTableSync(tableSync);
		} else {
			tableSyncMapper.updateTableSync(tableSync);
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

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTableSyncMapper(TableSyncMapper tableSyncMapper) {
		this.tableSyncMapper = tableSyncMapper;
	}

	@Transactional
	public void updateTableSyncStatus(TableSync model) {
		model.setSyncTime(new java.util.Date());
		tableSyncMapper.updateTableSyncStatus(model);
	}

}
