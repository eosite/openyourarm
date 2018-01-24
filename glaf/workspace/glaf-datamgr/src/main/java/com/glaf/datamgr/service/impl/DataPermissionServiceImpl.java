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
import com.glaf.datamgr.service.DataPermissionService;

@Service("dataPermissionService")
@Transactional(readOnly = true)
public class DataPermissionServiceImpl implements DataPermissionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataPermissionMapper dataPermissionMapper;

	public DataPermissionServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataPermission> list) {
		for (DataPermission dataPermission : list) {
			dataPermission.setId(idGenerator.nextId());
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			dataPermissionMapper.bulkInsertDataPermission_oracle(list);
		} else {
			dataPermissionMapper.bulkInsertDataPermission(list);
		}
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			dataPermissionMapper.deleteDataPermissionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				dataPermissionMapper.deleteDataPermissionById(id);
			}
		}
	}

	public int count(DataPermissionQuery query) {
		return dataPermissionMapper.getDataPermissionCount(query);
	}

	public List<DataPermission> list(DataPermissionQuery query) {
		List<DataPermission> list = dataPermissionMapper.getDataPermissions(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataPermissionCountByQueryCriteria(DataPermissionQuery query) {
		return dataPermissionMapper.getDataPermissionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataPermission> getDataPermissionsByQueryCriteria(int start, int pageSize, DataPermissionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataPermission> rows = sqlSessionTemplate.selectList("getDataPermissions", query, rowBounds);
		return rows;
	}

	public DataPermission getDataPermission(Long id) {
		if (id == null) {
			return null;
		}
		DataPermission dataPermission = dataPermissionMapper.getDataPermissionById(id);
		return dataPermission;
	}

	/**
	 * 保存多条记录
	 * 
	 * @return
	 */
	@Transactional
	public void saveAll(DataPermissionQuery query, List<DataPermission> dataPermissions) {
		List<DataPermission> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			for (DataPermission model : list) {
				dataPermissionMapper.deleteDataPermissionById(model.getId());
			}
		}
		if (dataPermissions != null && !dataPermissions.isEmpty()) {
			for (DataPermission dataPermission : dataPermissions) {
				dataPermission.setId(idGenerator.nextId());
				dataPermission.setCreateTime(new Date());
				dataPermissionMapper.insertDataPermission(dataPermission);
			}
		}
	}

	@Transactional
	public void save(DataPermission dataPermission) {
		dataPermission.setId(idGenerator.nextId());
		dataPermission.setCreateTime(new Date());
		dataPermissionMapper.insertDataPermission(dataPermission);
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
	public void setDataPermissionMapper(DataPermissionMapper dataPermissionMapper) {
		this.dataPermissionMapper = dataPermissionMapper;
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
