package com.glaf.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DaoService {
	/**
	 * 查询记录集合
	 * @param sqlid
	 * @param query
	 * @return
	 */
	public List<Object> selectList(String sqlid, Object query);

	/**
	 * 查询单条记录
	 * @param sqlid
	 * @param query
	 * @return
	 */
	public Object selectOne(String sqlid, Object query);

	/**
	 * 插入记录
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int insert(String sqlid, Object param);

	/**
	 * 更新记录
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int update(String sqlid, Object param);

	/**
	 * 删除记录
	 * @param sqlid
	 * @param param
	 * @return
	 */
	public int delete(String sqlid, Object param);
}
