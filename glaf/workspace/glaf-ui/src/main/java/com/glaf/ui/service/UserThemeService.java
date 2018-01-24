package com.glaf.ui.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.ui.model.UserTheme;
import com.glaf.ui.query.UserThemeQuery;

@Transactional(readOnly = true)
public interface UserThemeService {

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
	List<UserTheme> list(UserThemeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getUserThemeCountByQueryCriteria(UserThemeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<UserTheme> getUserThemesByQueryCriteria(int start, int pageSize,
			UserThemeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	UserTheme getUserTheme(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(UserTheme userTheme);

}
