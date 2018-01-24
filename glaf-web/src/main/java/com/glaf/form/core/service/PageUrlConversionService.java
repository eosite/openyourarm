package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface PageUrlConversionService {

	@Transactional
	void bulkInsert(List<PageUrlConversion> list);

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
	List<PageUrlConversion> list(PageUrlConversionQuery query);
	
	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<PageUrlConversion> getAllList();

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPageUrlConversionCountByQueryCriteria(PageUrlConversionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<PageUrlConversion> getPageUrlConversionsByQueryCriteria(int start, int pageSize, PageUrlConversionQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	PageUrlConversion getPageUrlConversion(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(PageUrlConversion pageUrlConversion);

}
