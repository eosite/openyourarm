package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface DepartmentCorrelationService {

	@Transactional
	void bulkInsert(List<DepartmentCorrelation> list);

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
	DepartmentCorrelation getDepartmentCorrelation(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepartmentCorrelationCountByQueryCriteria(DepartmentCorrelationQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepartmentCorrelation> getDepartmentCorrelationsByQueryCriteria(int start, int pageSize,
			DepartmentCorrelationQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepartmentCorrelation> list(DepartmentCorrelationQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepartmentCorrelation departmentCorrelation);

}
