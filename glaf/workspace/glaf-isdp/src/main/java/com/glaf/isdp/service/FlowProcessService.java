package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowProcess;
import com.glaf.isdp.query.FlowProcessQuery;

@Transactional(readOnly = true)
public interface FlowProcessService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FlowProcess> list(FlowProcessQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowProcessCountByQueryCriteria(FlowProcessQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowProcess> getFlowProcesssByQueryCriteria(int start, int pageSize,
			FlowProcessQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowProcess getFlowProcess(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowProcess flowProcess);

}
