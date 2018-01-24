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
import com.glaf.datamgr.service.TableExecutionColumnService;

@Service("com.glaf.datamgr.service.tableExecutionColumnService")
@Transactional(readOnly = true)
public class TableExecutionColumnServiceImpl implements TableExecutionColumnService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableExecutionColumnMapper tableExecutionColumnMapper;

	public TableExecutionColumnServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TableExecutionColumn> list) {
		for (TableExecutionColumn tableExecutionColumn : list) {
			if (StringUtils.isEmpty(tableExecutionColumn.getId())) {
				tableExecutionColumn.setId(idGenerator.getNextId("SYS_TABLE_EXECUTION_COLUMN"));
			}
		}

		int batch_size = 100;
		List<TableExecutionColumn> rows = new ArrayList<TableExecutionColumn>(batch_size);

		for (TableExecutionColumn bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					tableExecutionColumnMapper.bulkInsertTableExecutionColumn_oracle(rows);
				} else {
					tableExecutionColumnMapper.bulkInsertTableExecutionColumn(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				tableExecutionColumnMapper.bulkInsertTableExecutionColumn_oracle(rows);
			} else {
				tableExecutionColumnMapper.bulkInsertTableExecutionColumn(rows);
			}
			rows.clear();
		}
	}

	public int count(TableExecutionColumnQuery query) {
		return tableExecutionColumnMapper.getTableExecutionColumnCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			tableExecutionColumnMapper.deleteTableExecutionColumnById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				tableExecutionColumnMapper.deleteTableExecutionColumnById(id);
			}
		}
	}

	public TableExecutionColumn getTableExecutionColumn(String id) {
		if (id == null) {
			return null;
		}
		TableExecutionColumn tableExecutionColumn = tableExecutionColumnMapper.getTableExecutionColumnById(id);
		return tableExecutionColumn;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableExecutionColumnCountByQueryCriteria(TableExecutionColumnQuery query) {
		return tableExecutionColumnMapper.getTableExecutionColumnCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableExecutionColumn> getTableExecutionColumnsByQueryCriteria(int start, int pageSize,
			TableExecutionColumnQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableExecutionColumn> rows = sqlSessionTemplate.selectList("getTableExecutionColumns", query, rowBounds);
		return rows;
	}

	public List<TableExecutionColumn> list(TableExecutionColumnQuery query) {
		List<TableExecutionColumn> list = tableExecutionColumnMapper.getTableExecutionColumns(query);
		return list;
	}

	@Transactional
	public void save(TableExecutionColumn tableExecutionColumn) {
		if (StringUtils.isEmpty(tableExecutionColumn.getId())) {
			tableExecutionColumn.setId(UUID32.getUUID());
			tableExecutionColumnMapper.insertTableExecutionColumn(tableExecutionColumn);
		} else {
			tableExecutionColumnMapper.updateTableExecutionColumn(tableExecutionColumn);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.TableExecutionColumnMapper")
	public void setTableExecutionColumnMapper(TableExecutionColumnMapper tableExecutionColumnMapper) {
		this.tableExecutionColumnMapper = tableExecutionColumnMapper;
	}

}
