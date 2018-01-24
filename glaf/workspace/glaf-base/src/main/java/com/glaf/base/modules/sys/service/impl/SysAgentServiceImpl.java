package com.glaf.base.modules.sys.service.impl;

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
import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.SysAgentService;

@Service("sysAgentService")
@Transactional(readOnly = true)
public class SysAgentServiceImpl implements SysAgentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysAgentMapper sysAgentMapper;

	public SysAgentServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<SysAgent> list) {
		for (SysAgent sysAgent : list) {
			if (StringUtils.isEmpty(sysAgent.getId())) {
				sysAgent.setId(idGenerator.getNextId("SYS_AGENT"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			sysAgentMapper.bulkInsertSysAgent_oracle(list);
		} else {
			sysAgentMapper.bulkInsertSysAgent(list);
		}
	}

	public int count(SysAgentQuery query) {
		return sysAgentMapper.getSysAgentCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysAgentMapper.deleteSysAgentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysAgentMapper.deleteSysAgentById(id);
			}
		}
	}

	public SysAgent getSysAgent(String id) {
		if (id == null) {
			return null;
		}
		SysAgent sysAgent = sysAgentMapper.getSysAgentById(id);
		return sysAgent;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSysAgentCountByQueryCriteria(SysAgentQuery query) {
		return sysAgentMapper.getSysAgentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysAgent> getSysAgentsByQueryCriteria(int start, int pageSize, SysAgentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysAgent> rows = sqlSessionTemplate.selectList("getSysAgents", query, rowBounds);
		return rows;
	}

	public List<SysAgent> list(SysAgentQuery query) {
		List<SysAgent> list = sysAgentMapper.getSysAgents(query);
		return list;
	}

	@Transactional
	public void save(SysAgent sysAgent) {
		if (StringUtils.isEmpty(sysAgent.getId())) {
			sysAgent.setId(idGenerator.getNextId("SYS_AGENT"));
			sysAgent.setCreateDate(new Date());
			sysAgentMapper.insertSysAgent(sysAgent);
		} else {
			sysAgentMapper.updateSysAgent(sysAgent);
		}
	}

	@Transactional
	public void saveAgents(String assignFrom, List<SysAgent> agents) {
		SysAgentQuery query = new SysAgentQuery();
		query.assignFrom(assignFrom);
		List<SysAgent> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (SysAgent agent : list) {
				this.deleteById(agent.getId());
			}
		}

		for (SysAgent agent : agents) {
			agent.setId(idGenerator.getNextId("SYS_AGENT"));
			agent.setAssignFrom(assignFrom);
			agent.setCreateDate(new Date());
			sysAgentMapper.insertSysAgent(agent);
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
	public void setSysAgentMapper(SysAgentMapper sysAgentMapper) {
		this.sysAgentMapper = sysAgentMapper;
	}

}
