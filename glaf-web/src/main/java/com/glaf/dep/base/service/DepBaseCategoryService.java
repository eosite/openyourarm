package com.glaf.dep.base.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBaseCategoryService {

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
	List<DepBaseCategory> list(DepBaseCategoryQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBaseCategoryCountByQueryCriteria(DepBaseCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBaseCategory> getDepBaseCategorysByQueryCriteria(int start,
			int pageSize, DepBaseCategoryQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBaseCategory getDepBaseCategory(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBaseCategory depBaseCategory);

	/**
	 * 根据treeId更新删除标记
	 * @param treeId
	 * @param delFlag
	 */
	void updateDelFlagByTreeId(String treeId,String delFlag);

	/**
	 * 根据code获取分类
	 * @param code
	 * @return
	 */
	DepBaseCategory getDepBaseCategorysByCode(String code);

	/**
	 * 根据父分类取下一个code
	 * @param pid
	 * @return
	 */
	String getNextCode(long pid);
}
