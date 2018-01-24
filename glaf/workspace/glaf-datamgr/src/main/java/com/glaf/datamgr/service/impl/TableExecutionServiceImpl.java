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
import com.glaf.datamgr.service.TableExecutionService;

@Service("com.glaf.datamgr.service.tableExecutionService")
@Transactional(readOnly = true)
public class TableExecutionServiceImpl implements TableExecutionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableExecutionMapper tableExecutionMapper;

	protected TableExecutionColumnMapper tableExecutionColumnMapper;

	public TableExecutionServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TableExecution> list) {
		for (TableExecution tableExecution : list) {
			if (StringUtils.isEmpty(tableExecution.getId())) {
				tableExecution.setId(idGenerator.getNextId("SYS_TABLE_EXECUTION"));
			}
		}

		int batch_size = 100;
		List<TableExecution> rows = new ArrayList<TableExecution>(batch_size);

		for (TableExecution bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					tableExecutionMapper.bulkInsertTableExecution_oracle(rows);
				} else {
					tableExecutionMapper.bulkInsertTableExecution(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				tableExecutionMapper.bulkInsertTableExecution_oracle(rows);
			} else {
				tableExecutionMapper.bulkInsertTableExecution(rows);
			}
			rows.clear();
		}
	}

	public int count(TableExecutionQuery query) {
		return tableExecutionMapper.getTableExecutionCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			tableExecutionColumnMapper.deleteTableExecutionColumnsByExecutionId(id);
			tableExecutionMapper.deleteTableExecutionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				tableExecutionColumnMapper.deleteTableExecutionColumnsByExecutionId(id);
				tableExecutionMapper.deleteTableExecutionById(id);
			}
		}
	}

	public TableExecution getTableExecution(String id) {
		if (id == null) {
			return null;
		}
		TableExecution tableExecution = tableExecutionMapper.getTableExecutionById(id);
		if (tableExecution != null) {
			List<TableExecutionColumn> columns = tableExecutionColumnMapper.getTableExecutionColumnsByExecutionId(id);
			tableExecution.setColumns(columns);
		}
		return tableExecution;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableExecutionCountByQueryCriteria(TableExecutionQuery query) {
		return tableExecutionMapper.getTableExecutionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableExecution> getTableExecutionsByQueryCriteria(int start, int pageSize, TableExecutionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableExecution> rows = sqlSessionTemplate.selectList("getTableExecutions", query, rowBounds);
		return rows;
	}

	public List<TableExecution> list(TableExecutionQuery query) {
		List<TableExecution> list = tableExecutionMapper.getTableExecutions(query);
		return list;
	}

	@Transactional
	public void save(TableExecution tableExecution) {
		if (StringUtils.isEmpty(tableExecution.getId())) {
			tableExecution.setId(UUID32.getUUID());
			tableExecution.setCreateTime(new Date());
			tableExecutionMapper.insertTableExecution(tableExecution);
		} else {
			tableExecutionMapper.updateTableExecution(tableExecution);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.TableExecutionMapper")
	public void setTableExecutionMapper(TableExecutionMapper tableExecutionMapper) {
		this.tableExecutionMapper = tableExecutionMapper;
	}
}
