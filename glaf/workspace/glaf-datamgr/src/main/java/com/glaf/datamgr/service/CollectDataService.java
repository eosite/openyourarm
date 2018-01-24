package com.glaf.datamgr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.CollectData;
import com.glaf.datamgr.query.CollectDataQuery;

@Transactional(readOnly = true)
public interface CollectDataService {
	/**
	 * 查询统计数据
	 * 
	 * @param start
	 * @param pageSize
	 * @param query
	 * @return
	 */
	List<CollectData> getCollectDatasByQueryCriteria(int start, int pageSize,
			CollectDataQuery query);

	/**
	 * 查询统计数据
	 * 
	 * @param query
	 * @return
	 */
	List<CollectData> list(CollectDataQuery query);

	/**
	 * 查询统计数据总数
	 * 
	 * @param query
	 * @return
	 */
	int getCollectDataCount(CollectDataQuery query);

	/**
	 * 查询统计数据中项目数
	 * 
	 * @param query
	 * @return
	 */
	int getCollectProjectCount(CollectDataQuery query);
}
