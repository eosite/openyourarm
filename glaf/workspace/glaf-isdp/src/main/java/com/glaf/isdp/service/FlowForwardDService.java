package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowForwardD;
import com.glaf.isdp.query.FlowForwardDQuery;

@Transactional(readOnly = true)
public interface FlowForwardDService {

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
	List<FlowForwardD> list(FlowForwardDQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowForwardDCountByQueryCriteria(FlowForwardDQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowForwardD> getFlowForwardDsByQueryCriteria(int start, int pageSize,
			FlowForwardDQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowForwardD getFlowForwardD(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowForwardD flowForwardD);

}
