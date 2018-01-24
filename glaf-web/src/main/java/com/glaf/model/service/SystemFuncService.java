package com.glaf.model.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Transactional(readOnly = true)
public interface SystemFuncService {

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
	void deleteByIds(List<String> funcIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SystemFunc> list(SystemFuncQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemFuncCountByQueryCriteria(SystemFuncQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SystemFunc> getSystemFuncsByQueryCriteria(int start, int pageSize, SystemFuncQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SystemFunc getSystemFunc(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemFunc systemFunc);
    /**
     * 更新功能类型、名称
     * @param systemFunc
     */
	@Transactional
	void updateFuncNameType(SystemFunc systemFunc);

}
