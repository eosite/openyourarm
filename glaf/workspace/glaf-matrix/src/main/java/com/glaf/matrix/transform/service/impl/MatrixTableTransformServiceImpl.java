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
import com.glaf.matrix.transform.service.MatrixTableTransformService;

@Service("matrixTableTransformService")
@Transactional(readOnly = true)
public class MatrixTableTransformServiceImpl implements MatrixTableTransformService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MatrixColumnTransformMapper matrixColumnTransformMapper;

	protected MatrixTableTransformMapper matrixTableTransformMapper;

	public MatrixTableTransformServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<MatrixTableTransform> list) {
		for (MatrixTableTransform matrixTableTransform : list) {
			if (StringUtils.isEmpty(matrixTableTransform.getTransformId())) {
				matrixTableTransform.setTransformId(idGenerator.getNextId("MATRIX_TABLE_TRANSFORM"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			matrixTableTransformMapper.bulkInsertMatrixTableTransform_oracle(list);
		} else {
			matrixTableTransformMapper.bulkInsertMatrixTableTransform(list);
		}
	}

	public int count(MatrixTableTransformQuery query) {
		return matrixTableTransformMapper.getMatrixTableTransformCount(query);
	}

	@Transactional
	public void deleteById(String transformId) {
		if (transformId != null) {
			matrixColumnTransformMapper.deleteMatrixColumnTransformsByTransformId(transformId);
			matrixTableTransformMapper.deleteMatrixTableTransformById(transformId);
		}
	}

	public MatrixTableTransform getMatrixTableTransform(String transformId) {
		if (transformId == null) {
			return null;
		}
		MatrixTableTransform matrixTableTransform = matrixTableTransformMapper.getMatrixTableTransformById(transformId);
		if (matrixTableTransform != null) {
			List<MatrixColumnTransform> columns = matrixColumnTransformMapper
					.getMatrixColumnTransformsByTransformId(transformId);
			matrixTableTransform.setColumns(columns);
		}
		return matrixTableTransform;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getMatrixTableTransformCountByQueryCriteria(MatrixTableTransformQuery query) {
		return matrixTableTransformMapper.getMatrixTableTransformCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<MatrixTableTransform> getMatrixTableTransformsByQueryCriteria(int start, int pageSize,
			MatrixTableTransformQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<MatrixTableTransform> rows = sqlSessionTemplate.selectList("getMatrixTableTransforms", query, rowBounds);
		return rows;
	}

	public List<MatrixTableTransform> list(MatrixTableTransformQuery query) {
		List<MatrixTableTransform> list = matrixTableTransformMapper.getMatrixTableTransforms(query);
		return list;
	}

	@Transactional
	public void resetAllMatrixTableTransformStatus() {
		matrixTableTransformMapper.resetAllMatrixTableTransformStatus();
	}

	@Transactional
	public void save(MatrixTableTransform matrixTableTransform) {
		MatrixTableTransform bean = this.getMatrixTableTransform(matrixTableTransform.getTransformId());
		if (bean == null) {
			if (StringUtils.isEmpty(matrixTableTransform.getTransformId())) {
				matrixTableTransform.setTransformId(idGenerator.getNextId("MATRIX_TABLE_TRANSFORM"));
			}
			matrixTableTransform.setCreateTime(new Date());
			matrixTableTransform.setDeleteFlag(0);
			logger.debug(matrixTableTransform.toJsonObject().toJSONString());
			matrixTableTransformMapper.insertMatrixTableTransform(matrixTableTransform);
		} else {
			matrixTableTransformMapper.updateMatrixTableTransform(matrixTableTransform);
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

	@javax.annotation.Resource
	public void setMatrixTableTransformMapper(MatrixTableTransformMapper matrixTableTransformMapper) {
		this.matrixTableTransformMapper = matrixTableTransformMapper;
	}

	@Transactional
	public void updateMatrixTableTransformStatus(MatrixTableTransform model) {
		model.setTransformTime(new java.util.Date());
		matrixTableTransformMapper.updateMatrixTableTransformStatus(model);
	}

}
