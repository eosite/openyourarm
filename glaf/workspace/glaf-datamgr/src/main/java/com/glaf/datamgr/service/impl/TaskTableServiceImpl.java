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
import com.glaf.datamgr.service.TaskTableService;

@Service("com.glaf.datamgr.service.taskTableService")
@Transactional(readOnly = true)
public class TaskTableServiceImpl implements TaskTableService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TaskTableMapper taskTableMapper;

	public TaskTableServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TaskTable> list) {
		for (TaskTable taskTable : list) {
			if (taskTable.getId() == 0) {
				taskTable.setId(idGenerator.nextId("SYS_TASK_TABLE"));
			}
		}

		int batch_size = 100;
		List<TaskTable> rows = new ArrayList<TaskTable>(batch_size);

		for (TaskTable bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					taskTableMapper.bulkInsertTaskTable_oracle(rows);
				} else {
					taskTableMapper.bulkInsertTaskTable(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				taskTableMapper.bulkInsertTaskTable_oracle(rows);
			} else {
				taskTableMapper.bulkInsertTaskTable(rows);
			}
			rows.clear();
		}
	}

	public int count(TaskTableQuery query) {
		return taskTableMapper.getTaskTableCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			taskTableMapper.deleteTaskTableById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				taskTableMapper.deleteTaskTableById(id);
			}
		}
	}

	public TaskTable getTaskTable(Long id) {
		if (id == null) {
			return null;
		}
		TaskTable taskTable = taskTableMapper.getTaskTableById(id);
		return taskTable;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTaskTableCountByQueryCriteria(TaskTableQuery query) {
		return taskTableMapper.getTaskTableCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TaskTable> getTaskTablesByQueryCriteria(int start, int pageSize, TaskTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TaskTable> rows = sqlSessionTemplate.selectList("getTaskTables", query, rowBounds);
		return rows;
	}

	public List<TaskTable> list(TaskTableQuery query) {
		List<TaskTable> list = taskTableMapper.getTaskTables(query);
		return list;
	}

	@Transactional
	public void save(TaskTable taskTable) {
		if (taskTable.getId() == 0) {
			taskTable.setId(idGenerator.nextId("SYS_TASK_TABLE"));
			taskTable.setCreateTime(new Date());
			taskTable.setDeleteFlag(0);
			taskTableMapper.insertTaskTable(taskTable);
		} else {
			taskTableMapper.updateTaskTable(taskTable);
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

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.TaskTableMapper")
	public void setTaskTableMapper(TaskTableMapper taskTableMapper) {
		this.taskTableMapper = taskTableMapper;
	}

}
