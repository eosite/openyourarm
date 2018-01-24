package com.glaf.isdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CellUserAddService {

	List<Map<String, Object>> getDataListByQueryCriteria(String tableName,
			String whereCause, String orderBy, int start, int pageSize);

	List<Map<String, Object>> getDataList(String tableName, String whereCause,
			String orderBy);

	int getDataCount(String tableName, String whereCause);

	List<Map<String, Object>> getDataListBySql(String sql);

	Map<String, Object> getDataMapBySql(String sql);

	int getDataCountBySql(String sql);

	List<Map<String, Object>> getDataListByQueryCriteria(String sql, int start,
			int pageSize);

	public List<Map<String, Object>> getDataListBySqlCriteria(String sql,
			int start, int pageSize);
}
