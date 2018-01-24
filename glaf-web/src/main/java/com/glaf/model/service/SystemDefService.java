package com.glaf.model.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Transactional(readOnly = true)
public interface SystemDefService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 刪除系統
	 * 
	 * @param id
	 */
	@Transactional
	void deleteSystem(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> sysIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<SystemDef> list(SystemDefQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemDefCountByQueryCriteria(SystemDefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SystemDef> getSystemDefsByQueryCriteria(int start, int pageSize, SystemDefQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SystemDef getSystemDef(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemDef systemDef);
    /**
     * 发布系统
     * @param systemDef
     */
	@Transactional
	void publish(SystemDef systemDef);

}
