package com.glaf.matrix.transform.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.matrix.transform.domain.*;
import com.glaf.matrix.transform.query.*;

@Transactional(readOnly = true)
public interface MatrixTableTransformService {

	@Transactional
	void bulkInsert(List<MatrixTableTransform> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String transformId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	MatrixTableTransform getMatrixTableTransform(String transformId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getMatrixTableTransformCountByQueryCriteria(MatrixTableTransformQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<MatrixTableTransform> getMatrixTableTransformsByQueryCriteria(int start, int pageSize, MatrixTableTransformQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<MatrixTableTransform> list(MatrixTableTransformQuery query);
	
	@Transactional
	void resetAllMatrixTableTransformStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(MatrixTableTransform matrixTableTransform);
	
	@Transactional
	void updateMatrixTableTransformStatus(MatrixTableTransform model);

}
