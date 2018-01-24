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
import com.glaf.datamgr.service.ColumnTransformService;

@Service("columnTransformService")
@Transactional(readOnly = true)
public class ColumnTransformServiceImpl implements ColumnTransformService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ColumnTransformMapper columnTransformMapper;

	public ColumnTransformServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ColumnTransform> list) {
		for (ColumnTransform columnTransform : list) {
			if (columnTransform.getId() == 0) {
				columnTransform.setId(idGenerator.nextId("SYS_COLUMN_TRANSFORM"));
			}
			if (StringUtils.isEmpty(columnTransform.getTargetColumnName())) {
				columnTransform.setTargetColumnName(columnTransform.getTableName() + "_COL_"
						+ idGenerator.nextId(columnTransform.getTableName() + "_COL"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			columnTransformMapper.bulkInsertColumnTransform_oracle(list);
		} else {
			columnTransformMapper.bulkInsertColumnTransform(list);
		}
	}

	public int count(ColumnTransformQuery query) {
		return columnTransformMapper.getColumnTransformCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			columnTransformMapper.deleteColumnTransformById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				columnTransformMapper.deleteColumnTransformById(id);
			}
		}
	}

	public ColumnTransform getColumnTransform(Long id) {
		if (id == null) {
			return null;
		}
		ColumnTransform columnTransform = columnTransformMapper.getColumnTransformById(id);
		return columnTransform;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getColumnTransformCountByQueryCriteria(ColumnTransformQuery query) {
		return columnTransformMapper.getColumnTransformCount(query);
	}

	public List<ColumnTransform> getColumnTransforms(String tableName) {
		ColumnTransformQuery query = new ColumnTransformQuery();
		query.setLocked(0);
		query.tableName(tableName);
		List<ColumnTransform> list = columnTransformMapper.getColumnTransforms(query);
		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ColumnTransform> getColumnTransformsByQueryCriteria(int start, int pageSize,
			ColumnTransformQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ColumnTransform> rows = sqlSessionTemplate.selectList("getColumnTransforms", query, rowBounds);
		return rows;
	}

	/**
	 * 获取转换的表集合
	 * 
	 * @return
	 */
	public List<String> getTransformTableNames() {
		ColumnTransformQuery query = new ColumnTransformQuery();
		query.setLocked(0);
		List<ColumnTransform> list = columnTransformMapper.getColumnTransforms(query);
		List<String> tables = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (ColumnTransform ct : list) {
				if (!tables.contains(ct.getTableName().toLowerCase())) {
					tables.add(ct.getTableName().toLowerCase());
				}
			}
		}
		return tables;
	}

	public List<ColumnTransform> list(ColumnTransformQuery query) {
		List<ColumnTransform> list = columnTransformMapper.getColumnTransforms(query);
		return list;
	}

	@Transactional
	public void save(ColumnTransform columnTransform) {
		if (columnTransform.getId() == 0) {
			columnTransform.setId(idGenerator.nextId("SYS_COLUMN_TRANSFORM"));
			columnTransform.setCreateTime(new Date());
			if (StringUtils.isEmpty(columnTransform.getTargetColumnName())) {
				columnTransform.setTargetColumnName(columnTransform.getTableName() + "_COL_"
						+ idGenerator.nextId(columnTransform.getTableName() + "_COL"));
			}
			columnTransformMapper.insertColumnTransform(columnTransform);
		} else {
			columnTransformMapper.updateColumnTransform(columnTransform);
		}
	}

	@javax.annotation.Resource
	public void setColumnTransformMapper(ColumnTransformMapper columnTransformMapper) {
		this.columnTransformMapper = columnTransformMapper;
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
