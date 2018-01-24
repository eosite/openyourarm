package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowStation;
import com.glaf.isdp.query.FlowStationQuery;

@Transactional(readOnly = true)
public interface FlowStationService {

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
	List<FlowStation> list(FlowStationQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowStationCountByQueryCriteria(FlowStationQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowStation> getFlowStationsByQueryCriteria(int start, int pageSize,
			FlowStationQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowStation getFlowStation(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowStation flowStation);

}
