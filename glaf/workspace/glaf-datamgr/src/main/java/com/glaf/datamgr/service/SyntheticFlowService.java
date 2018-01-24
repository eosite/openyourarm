package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface SyntheticFlowService {

	@Transactional
	void bulkInsert(List<SyntheticFlow> list);

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
	SyntheticFlow getSyntheticFlow(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSyntheticFlowCountByQueryCriteria(SyntheticFlowQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SyntheticFlow> getSyntheticFlowsByQueryCriteria(int start, int pageSize, SyntheticFlowQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SyntheticFlow> list(SyntheticFlowQuery query);

	/**
	 * 保存记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAll(String currentStep, String currentType, List<SyntheticFlow> rows);

}
