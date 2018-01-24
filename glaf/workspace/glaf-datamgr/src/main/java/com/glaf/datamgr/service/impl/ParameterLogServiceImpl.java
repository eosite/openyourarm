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
import com.glaf.datamgr.service.ParameterLogService;

@Service("parameterLogService")
@Transactional(readOnly = true)
public class ParameterLogServiceImpl implements ParameterLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ParameterLogMapper parameterLogMapper;

	public ParameterLogServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ParameterLog> list) {
		Date createTime = new Date();
		List<ParameterLog> logs = new ArrayList<ParameterLog>();
		for (ParameterLog parameterLog : list) {
			if (parameterLog.getId() == 0) {
				parameterLog.setId(idGenerator.nextId("SYS_PARAM_LOG"));
			}
			parameterLog.setCreateTime(createTime);
			logs.add(parameterLog);
			if (logs.size() > 0 && logs.size() % 100 == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					parameterLogMapper.bulkInsertParameterLog_oracle(logs);
				} else {
					parameterLogMapper.bulkInsertParameterLog(logs);
				}
				logs.clear();
			}
		}

		if (logs.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				parameterLogMapper.bulkInsertParameterLog_oracle(logs);
			} else {
				parameterLogMapper.bulkInsertParameterLog(logs);
			}
			logs.clear();
		}
	}

	public int count(ParameterLogQuery query) {
		return parameterLogMapper.getParameterLogCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			parameterLogMapper.deleteParameterLogById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				parameterLogMapper.deleteParameterLogById(id);
			}
		}
	}

	@Transactional
	public void deleteOverdueParameterLogs(Date dateBefore) {
		parameterLogMapper.deleteOverdueParameterLogs(dateBefore);
	}

	@Transactional
	public void deleteTodayParameterLogs(String type, String businessKey) {
		ParameterLogQuery query = new ParameterLogQuery();
		query.type(type);
		query.businessKey(businessKey);
		query.runDay(DateUtils.getNowYearMonthDay());
		parameterLogMapper.deleteParameterLogs(query);
	}

	public ParameterLog getParameterLog(Long id) {
		if (id == null) {
			return null;
		}
		ParameterLog parameterLog = parameterLogMapper.getParameterLogById(id);
		return parameterLog;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getParameterLogCountByQueryCriteria(ParameterLogQuery query) {
		return parameterLogMapper.getParameterLogCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ParameterLog> getParameterLogsByQueryCriteria(int start, int pageSize, ParameterLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ParameterLog> rows = sqlSessionTemplate.selectList("getParameterLogs", query, rowBounds);
		return rows;
	}

	public List<ParameterLog> getLatestLogs(String type, Date dataAfter, int offset, int limit) {
		ParameterLogQuery query = new ParameterLogQuery();
		query.type(type);
		query.createTimeGreaterThanOrEqual(dataAfter);
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<ParameterLog> rows = sqlSessionTemplate.selectList("getParameterLogs", query, rowBounds);
		return rows;
	}

	/**
	 * 获取某个类型某个实例当天的执行日志
	 * 
	 * @param type
	 * @param businessKey
	 * @return
	 */
	public List<ParameterLog> getTodayParameterLogs(String type, String businessKey) {
		ParameterLogQuery query = new ParameterLogQuery();
		query.type(type);
		query.businessKey(businessKey);
		query.runDay(DateUtils.getNowYearMonthDay());
		return parameterLogMapper.getParameterLogs(query);
	}

	public int getTotal(String type, Date dataAfter) {
		ParameterLogQuery query = new ParameterLogQuery();
		query.type(type);
		query.createTimeGreaterThanOrEqual(dataAfter);
		return parameterLogMapper.getParameterLogCount(query);
	}

	public List<ParameterLog> list(ParameterLogQuery query) {
		List<ParameterLog> list = parameterLogMapper.getParameterLogs(query);
		return list;
	}

	@Transactional
	public void save(ParameterLog parameterLog) {
		if (parameterLog.getId() == 0) {
			parameterLog.setId(idGenerator.nextId("SYS_PARAM_LOG"));
			parameterLog.setCreateTime(new Date());
			parameterLogMapper.insertParameterLog(parameterLog);
		} else {
			parameterLogMapper.updateParameterLog(parameterLog);
		}
	}

	@Transactional
	public void saveLogs(List<ParameterLog> logs) {
		this.bulkInsert(logs);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setParameterLogMapper(ParameterLogMapper parameterLogMapper) {
		this.parameterLogMapper = parameterLogMapper;
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
