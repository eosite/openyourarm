package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface OrderBySegmentService {

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
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<OrderBySegment> list(OrderBySegmentQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getOrderBySegmentCountByQueryCriteria(OrderBySegmentQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<OrderBySegment> getOrderBySegmentsByQueryCriteria(int start,
			int pageSize, OrderBySegmentQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	OrderBySegment getOrderBySegment(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(OrderBySegment orderBySegment);

}
