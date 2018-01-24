package com.glaf.matrix.transform.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.matrix.transform.domain.*;
import com.glaf.matrix.transform.query.*;

@Transactional(readOnly = true)
public interface MatrixColumnTransformService {

	@Transactional
	void bulkInsert(List<MatrixColumnTransform> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	MatrixColumnTransform getMatrixColumnTransform(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getMatrixColumnTransformCountByQueryCriteria(MatrixColumnTransformQuery query);

	List<MatrixColumnTransform> getMatrixColumnTransforms(String transformId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<MatrixColumnTransform> getMatrixColumnTransformsByQueryCriteria(int start, int pageSize, MatrixColumnTransformQuery query);

	/**
	 * 获取转换的表集合
	 * 
	 * @return
	 */
	List<String> getTransformTableNames();

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<MatrixColumnTransform> list(MatrixColumnTransformQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(MatrixColumnTransform matrixColumnTransform);

}
