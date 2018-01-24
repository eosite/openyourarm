package com.glaf.matrix.transform.service.impl;

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

import com.glaf.matrix.transform.mapper.*;
import com.glaf.matrix.transform.domain.*;
import com.glaf.matrix.transform.query.*;
import com.glaf.matrix.transform.service.MatrixColumnTransformService;

@Service("matrixColumnTransformService")
@Transactional(readOnly = true)
public class MatrixColumnTransformServiceImpl implements MatrixColumnTransformService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MatrixColumnTransformMapper matrixColumnTransformMapper;

	public MatrixColumnTransformServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<MatrixColumnTransform> list) {
		for (MatrixColumnTransform matrixColumnTransform : list) {
			if (matrixColumnTransform.getId() == 0) {
				matrixColumnTransform.setId(idGenerator.nextId("MATRIX_COLUMN_TRANSFORM"));
			}
			if (StringUtils.isEmpty(matrixColumnTransform.getTargetColumnName())) {
				matrixColumnTransform.setTargetColumnName(matrixColumnTransform.getTableName() + "_COL_"
						+ idGenerator.nextId(matrixColumnTransform.getTableName() + "_COL"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			matrixColumnTransformMapper.bulkInsertMatrixColumnTransform_oracle(list);
		} else {
			matrixColumnTransformMapper.bulkInsertMatrixColumnTransform(list);
		}
	}

	public int count(MatrixColumnTransformQuery query) {
		return matrixColumnTransformMapper.getMatrixColumnTransformCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			matrixColumnTransformMapper.deleteMatrixColumnTransformById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				matrixColumnTransformMapper.deleteMatrixColumnTransformById(id);
			}
		}
	}

	public MatrixColumnTransform getMatrixColumnTransform(Long id) {
		if (id == null) {
			return null;
		}
		MatrixColumnTransform matrixColumnTransform = matrixColumnTransformMapper.getMatrixColumnTransformById(id);
		return matrixColumnTransform;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMatrixColumnTransformCountByQueryCriteria(MatrixColumnTransformQuery query) {
		return matrixColumnTransformMapper.getMatrixColumnTransformCount(query);
	}

	public List<MatrixColumnTransform> getMatrixColumnTransforms(String transformId) {
		MatrixColumnTransformQuery query = new MatrixColumnTransformQuery();
		query.setLocked(0);
		query.transformId(transformId);
		List<MatrixColumnTransform> list = matrixColumnTransformMapper.getMatrixColumnTransforms(query);
		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MatrixColumnTransform> getMatrixColumnTransformsByQueryCriteria(int start, int pageSize,
			MatrixColumnTransformQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MatrixColumnTransform> rows = sqlSessionTemplate.selectList("getMatrixColumnTransforms", query, rowBounds);
		return rows;
	}

	/**
	 * 获取转换的表集合
	 * 
	 * @return
	 */
	public List<String> getTransformTableNames() {
		MatrixColumnTransformQuery query = new MatrixColumnTransformQuery();
		query.setLocked(0);
		List<MatrixColumnTransform> list = matrixColumnTransformMapper.getMatrixColumnTransforms(query);
		List<String> tables = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (MatrixColumnTransform ct : list) {
				if (!tables.contains(ct.getTableName().toLowerCase())) {
					tables.add(ct.getTableName().toLowerCase());
				}
			}
		}
		return tables;
	}

	public List<MatrixColumnTransform> list(MatrixColumnTransformQuery query) {
		List<MatrixColumnTransform> list = matrixColumnTransformMapper.getMatrixColumnTransforms(query);
		return list;
	}

	@Transactional
	public void save(MatrixColumnTransform matrixColumnTransform) {
		if (matrixColumnTransform.getId() == 0) {
			matrixColumnTransform.setId(idGenerator.nextId("MATRIX_COLUMN_TRANSFORM"));
			matrixColumnTransform.setCreateTime(new Date());
			if (StringUtils.isEmpty(matrixColumnTransform.getTargetColumnName())) {
				matrixColumnTransform.setTargetColumnName(matrixColumnTransform.getTableName() + "_COL_"
						+ idGenerator.nextId(matrixColumnTransform.getTableName() + "_COL"));
			}
			logger.debug(matrixColumnTransform.toJsonObject().toJSONString());
			matrixColumnTransformMapper.insertMatrixColumnTransform(matrixColumnTransform);
		} else {
			matrixColumnTransformMapper.updateMatrixColumnTransform(matrixColumnTransform);
		}
	}

	@javax.annotation.Resource
	public void setMatrixColumnTransformMapper(MatrixColumnTransformMapper matrixColumnTransformMapper) {
		this.matrixColumnTransformMapper = matrixColumnTransformMapper;
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
