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
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.DeptRoleService;

@Service("deptRoleService")
@Transactional(readOnly = true)
public class DeptRoleServiceImpl implements DeptRoleService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DeptRoleMapper deptRoleMapper;

	public DeptRoleServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DeptRole> list) {
		for (DeptRole deptRole : list) {
			if (deptRole.getId() == 0) {
				deptRole.setId(idGenerator.nextId("SYS_DEPT_ROLE"));
			}
		}

		int batch_size = 100;
		List<DeptRole> rows = new ArrayList<DeptRole>(batch_size);

		for (DeptRole bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					deptRoleMapper.bulkInsertDeptRole_oracle(list);
				} else {
					deptRoleMapper.bulkInsertDeptRole(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				deptRoleMapper.bulkInsertDeptRole_oracle(list);
			} else {
				deptRoleMapper.bulkInsertDeptRole(list);
			}
			rows.clear();
		}
	}

	public int count(DeptRoleQuery query) {
		return deptRoleMapper.getDeptRoleCount(query);
	}

	@Transactional
	public void deleteByDeptId(long deptId) {
		deptRoleMapper.deleteByDeptId(deptId);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			deptRoleMapper.deleteById(id);
		}
	}

	public DeptRole getDeptRole(Long id) {
		if (id == null) {
			return null;
		}
		DeptRole deptRole = deptRoleMapper.getDeptRoleById(id);
		return deptRole;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDeptRoleCountByQueryCriteria(DeptRoleQuery query) {
		return deptRoleMapper.getDeptRoleCount(query);
	}

	public List<DeptRole> getDeptRolesByDeptId(long deptId) {
		DeptRoleQuery query = new DeptRoleQuery();
		query.deptId(deptId);
		List<DeptRole> list = deptRoleMapper.getDeptRoles(query);
		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DeptRole> getDeptRolesByQueryCriteria(int start, int pageSize, DeptRoleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DeptRole> rows = sqlSessionTemplate.selectList("getDeptRoles", query, rowBounds);
		return rows;
	}

	public List<DeptRole> list(DeptRoleQuery query) {
		List<DeptRole> list = deptRoleMapper.getDeptRoles(query);
		return list;
	}

	@Transactional
	public void save(DeptRole deptRole) {
		if (deptRole.getId() == 0) {
			deptRole.setId(idGenerator.nextId("SYS_DEPT_ROLE"));
			deptRoleMapper.insertDeptRole(deptRole);
		}
	}

	@Transactional
	public void saveAll(long deptId, int menuFlag, List<DeptRole> rows) {
		DeptRoleQuery query = new DeptRoleQuery();
		query.deptId(deptId);
		query.menuFlag(menuFlag);
		List<DeptRole> list = deptRoleMapper.getDeptRoles(query);
		if (list != null && !list.isEmpty()) {
			for (DeptRole dr : list) {
				deptRoleMapper.deleteById(dr.getId());
			}
		}
		if (rows != null && !rows.isEmpty()) {
			for (DeptRole bean : rows) {
				bean.setId(idGenerator.nextId("SYS_DEPT_ROLE"));
				bean.setDeptId(deptId);
				bean.setMenuFlag(menuFlag);
				deptRoleMapper.insertDeptRole(bean);
			}
		}
	}

	@javax.annotation.Resource
	public void setDeptRoleMapper(DeptRoleMapper deptRoleMapper) {
		this.deptRoleMapper = deptRoleMapper;
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

}
