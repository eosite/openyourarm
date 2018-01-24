package com.glaf.datamgr.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.OperationService;

@Service("operationService")
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected OperationMapper operationMapper;

	public OperationServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			operationMapper.deleteOperationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				operationMapper.deleteOperationById(id);
			}
		}
	}

	public int count(OperationQuery query) {
		return operationMapper.getOperationCount(query);
	}

	public List<Operation> list(OperationQuery query) {
		List<Operation> list = operationMapper.getOperations(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getOperationCountByQueryCriteria(OperationQuery query) {
		return operationMapper.getOperationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Operation> getOperationsByQueryCriteria(int start,
			int pageSize, OperationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Operation> rows = sqlSessionTemplate.selectList("getOperations",
				query, rowBounds);
		return rows;
	}

	public Operation getOperation(Long id) {
		if (id == null) {
			return null;
		}
		Operation operation = operationMapper.getOperationById(id);
		return operation;
	}

	@Transactional
	public void save(Operation operation) {
		if (operation.getId() == 0) {
			operation.setId(idGenerator.nextId("SYS_OPERATION"));
			operation.setCreateTime(new Date());
			// operation.setDeleteFlag(0);
			operationMapper.insertOperation(operation);
		} else {
			operationMapper.updateOperation(operation);
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
	public void setOperationMapper(OperationMapper operationMapper) {
		this.operationMapper = operationMapper;
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
