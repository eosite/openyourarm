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
import com.glaf.datamgr.service.TableSyntheticService;

@Service("tableSyntheticService")
@Transactional(readOnly = true)
public class TableSyntheticServiceImpl implements TableSyntheticService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableSyntheticMapper tableSyntheticMapper;

	public TableSyntheticServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TableSynthetic> list) {
		for (TableSynthetic tableSynthetic : list) {
			if (tableSynthetic.getId() == 0) {
				tableSynthetic.setId(idGenerator.nextId("SYS_TABLE_SYNTHETIC"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			tableSyntheticMapper.bulkInsertTableSynthetic_oracle(list);
		} else {
			tableSyntheticMapper.bulkInsertTableSynthetic(list);
		}
	}

	public int count(TableSyntheticQuery query) {
		return tableSyntheticMapper.getTableSyntheticCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			tableSyntheticMapper.deleteTableSyntheticById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				tableSyntheticMapper.deleteTableSyntheticById(id);
			}
		}
	}

	public TableSynthetic getTableSynthetic(Long id) {
		if (id == null) {
			return null;
		}
		TableSynthetic tableSynthetic = tableSyntheticMapper.getTableSyntheticById(id);
		return tableSynthetic;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableSyntheticCountByQueryCriteria(TableSyntheticQuery query) {
		return tableSyntheticMapper.getTableSyntheticCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableSynthetic> getTableSyntheticsByQueryCriteria(int start, int pageSize, TableSyntheticQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableSynthetic> rows = sqlSessionTemplate.selectList("getTableSynthetics", query, rowBounds);
		return rows;
	}

	public List<TableSynthetic> list(TableSyntheticQuery query) {
		List<TableSynthetic> list = tableSyntheticMapper.getTableSynthetics(query);
		return list;
	}
	
	@Transactional
	public void resetAllTableSyntheticStatus(){
		tableSyntheticMapper.resetAllTableSyntheticStatus();
	}

	@Transactional
	public void save(TableSynthetic tableSynthetic) {
		if (StringUtils.isNotEmpty(tableSynthetic.getSyncColumns())) {
			tableSynthetic.setSyncColumns(tableSynthetic.getSyncColumns().toLowerCase());
		}
		if (tableSynthetic.getId() == 0) {
			tableSynthetic.setId(idGenerator.nextId("SYS_TABLE_SYNTHETIC"));
			tableSynthetic.setCreateTime(new Date());
			tableSyntheticMapper.insertTableSynthetic(tableSynthetic);
		} else {
			tableSyntheticMapper.updateTableSynthetic(tableSynthetic);
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
	public void setTableSyntheticMapper(TableSyntheticMapper tableSyntheticMapper) {
		this.tableSyntheticMapper = tableSyntheticMapper;
	}

	@Transactional
	public void updateTableSyntheticStatus(TableSynthetic model) {
		model.setSyncTime(new java.util.Date());
		tableSyntheticMapper.updateTableSyntheticStatus(model);
	}

}
