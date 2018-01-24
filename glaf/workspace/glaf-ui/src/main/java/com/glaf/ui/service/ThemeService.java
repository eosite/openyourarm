package com.glaf.ui.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.ui.model.Theme;
import com.glaf.ui.query.ThemeQuery;

@Transactional(readOnly = true)
public interface ThemeService {

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
	void deleteByIds(List<Integer> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Theme> list(ThemeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getThemeCountByQueryCriteria(ThemeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Theme> getThemesByQueryCriteria(int start, int pageSize,
			ThemeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Theme getTheme(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Theme theme);

}
