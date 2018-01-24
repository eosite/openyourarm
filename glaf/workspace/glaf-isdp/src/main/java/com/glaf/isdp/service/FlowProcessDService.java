package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowProcessD;
import com.glaf.isdp.query.FlowProcessDQuery;

@Transactional(readOnly = true)
public interface FlowProcessDService {

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
	List<FlowProcessD> list(FlowProcessDQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowProcessDCountByQueryCriteria(FlowProcessDQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowProcessD> getFlowProcessDsByQueryCriteria(int start, int pageSize,
			FlowProcessDQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowProcessD getFlowProcessD(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowProcessD flowProcessD);

}
