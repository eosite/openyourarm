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
import com.glaf.base.modules.sys.service.*;

@Service("departmentCorrelationService")
@Transactional(readOnly = true)
public class DepartmentCorrelationServiceImpl implements DepartmentCorrelationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepartmentCorrelationMapper departmentCorrelationMapper;

	public DepartmentCorrelationServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DepartmentCorrelation> list) {
		for (DepartmentCorrelation departmentCorrelation : list) {
			if (departmentCorrelation.getId() == null) {
				departmentCorrelation.setId(idGenerator.nextId("SYS_DEPARTMENT_CORRELATION"));
			}
		}

		int batch_size = 100;
		List<DepartmentCorrelation> rows = new ArrayList<DepartmentCorrelation>(batch_size);

		for (DepartmentCorrelation bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					departmentCorrelationMapper.bulkInsertDepartmentCorrelation_oracle(rows);
				} else {
					departmentCorrelationMapper.bulkInsertDepartmentCorrelation(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				departmentCorrelationMapper.bulkInsertDepartmentCorrelation_oracle(rows);
			} else {
				departmentCorrelationMapper.bulkInsertDepartmentCorrelation(rows);
			}
			rows.clear();
		}
	}

	public int count(DepartmentCorrelationQuery query) {
		return departmentCorrelationMapper.getDepartmentCorrelationCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			departmentCorrelationMapper.deleteDepartmentCorrelationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				departmentCorrelationMapper.deleteDepartmentCorrelationById(id);
			}
		}
	}

	public DepartmentCorrelation getDepartmentCorrelation(Long id) {
		if (id == null) {
			return null;
		}
		DepartmentCorrelation departmentCorrelation = departmentCorrelationMapper.getDepartmentCorrelationById(id);
		return departmentCorrelation;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepartmentCorrelationCountByQueryCriteria(DepartmentCorrelationQuery query) {
		return departmentCorrelationMapper.getDepartmentCorrelationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepartmentCorrelation> getDepartmentCorrelationsByQueryCriteria(int start, int pageSize,
			DepartmentCorrelationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepartmentCorrelation> rows = sqlSessionTemplate.selectList("getDepartmentCorrelations", query, rowBounds);
		return rows;
	}

	public List<DepartmentCorrelation> list(DepartmentCorrelationQuery query) {
		List<DepartmentCorrelation> list = departmentCorrelationMapper.getDepartmentCorrelations(query);
		return list;
	}

	@Transactional
	public void save(DepartmentCorrelation departmentCorrelation) {
		if (departmentCorrelation.getId() == null) {
			departmentCorrelation.setId(idGenerator.nextId("SYS_DEPARTMENT_CORRELATION"));
			departmentCorrelation.setCreateTime(new Date());

			departmentCorrelationMapper.insertDepartmentCorrelation(departmentCorrelation);
		} else {
			departmentCorrelationMapper.updateDepartmentCorrelation(departmentCorrelation);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.base.modules.sys.mapper.DepartmentCorrelationMapper")
	public void setDepartmentCorrelationMapper(DepartmentCorrelationMapper departmentCorrelationMapper) {
		this.departmentCorrelationMapper = departmentCorrelationMapper;
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
