package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.glaf.isdp.domain.RUtabletree;
import com.glaf.isdp.query.RUtabletreeQuery;

@Transactional(readOnly = true)
public interface RUtabletreeService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Integer id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Integer> indexIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<RUtabletree> list(RUtabletreeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getRUtabletreeCountByQueryCriteria(RUtabletreeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<RUtabletree> getRUtabletreesByQueryCriteria(int start, int pageSize, RUtabletreeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	RUtabletree getRUtabletree(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(RUtabletree rUtabletree);

	JSONArray getTreeAndChildrenByType(Integer tableType);

}
