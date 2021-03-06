package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowForward;
import com.glaf.isdp.query.FlowForwardQuery;

@Transactional(readOnly = true)
public interface FlowForwardService {

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
	List<FlowForward> list(FlowForwardQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowForwardCountByQueryCriteria(FlowForwardQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowForward> getFlowForwardsByQueryCriteria(int start, int pageSize,
			FlowForwardQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowForward getFlowForward(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowForward flowForward);

}
