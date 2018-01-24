package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.TreeProj;
import com.glaf.isdp.query.TreeProjQuery;

@Transactional(readOnly = true)
public interface TreeProjService {

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
	List<TreeProj> list(TreeProjQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTreeProjCountByQueryCriteria(TreeProjQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TreeProj> getTreeProjsByQueryCriteria(int start, int pageSize,
			TreeProjQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TreeProj getTreeProj(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TreeProj treeProj);

}
