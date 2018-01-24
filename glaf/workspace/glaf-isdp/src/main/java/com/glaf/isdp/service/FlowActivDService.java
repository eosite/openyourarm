package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.FlowActivD;
import com.glaf.isdp.query.FlowActivDQuery;

@Transactional(readOnly = true)
public interface FlowActivDService {

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
	List<FlowActivD> list(FlowActivDQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFlowActivDCountByQueryCriteria(FlowActivDQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FlowActivD> getFlowActivDsByQueryCriteria(int start, int pageSize,
			FlowActivDQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FlowActivD getFlowActivD(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FlowActivD flowActivD);
	/**
	 * 根据流程id查询当前已走到的最后一个流程记录
	 * @param processId
	 * @return
	 */
	FlowActivD getLastFlowActivDByProcessId(String processId);
}
