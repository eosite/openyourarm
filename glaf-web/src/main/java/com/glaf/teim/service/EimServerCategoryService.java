package com.glaf.teim.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

@Transactional(readOnly = true)
public interface EimServerCategoryService {

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
	List<EimServerCategory> list(EimServerCategoryQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getEimServerCategoryCountByQueryCriteria(EimServerCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<EimServerCategory> getEimServerCategorysByQueryCriteria(int start, int pageSize, EimServerCategoryQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	EimServerCategory getEimServerCategory(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(EimServerCategory eimServerCategory);
    /**
     * 移動到分類
     * @param moveType
     * @param categoryId
     * @param pId
     * @param treeId
     * @param actorId
     * @param date
     */
	@Transactional
	void move(String moveType, Long categoryId, Long pId, String treeId, String actorId, Date date);
    /**
     * 重命名
     * @param categoryId
     * @param name
     * @param actorId
     * @param date
     */
	@Transactional
	void rename(Long categoryId, String name, String actorId, Date date);

}
