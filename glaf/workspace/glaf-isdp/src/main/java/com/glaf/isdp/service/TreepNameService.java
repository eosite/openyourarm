package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.query.TreepNameQuery;

@Transactional(readOnly = true)
public interface TreepNameService {

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
	List<TreepName> list(TreepNameQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreepNameCountByQueryCriteria(TreepNameQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreepName> getTreepNamesByQueryCriteria(int start, int pageSize,
			TreepNameQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TreepName getTreepName(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreepName treepName);

	TreepName getTreepNameByDomainIndexId(Integer domainIndexId);
}
