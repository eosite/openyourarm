package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowActiv;
import com.glaf.isdp.query.FlowActivQuery;

@Transactional(readOnly = true)
public interface FlowActivService {

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
	List<FlowActiv> list(FlowActivQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowActivCountByQueryCriteria(FlowActivQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowActiv> getFlowActivsByQueryCriteria(int start, int pageSize,
			FlowActivQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowActiv getFlowActiv(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowActiv flowActiv);

}
