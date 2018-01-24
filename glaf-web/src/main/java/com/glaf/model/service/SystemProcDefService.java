package com.glaf.model.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Transactional(readOnly = true)
public interface SystemProcDefService {

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
	List<SystemProcDef> list(SystemProcDefQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSystemProcDefCountByQueryCriteria(SystemProcDefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SystemProcDef> getSystemProcDefsByQueryCriteria(int start, int pageSize, SystemProcDefQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SystemProcDef getSystemProcDef(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SystemProcDef systemProcDef);
    /**
     * 更新流程元素类型、名称
     * @param systemProcDef
     */
	@Transactional
	void updateProcDefNameType(SystemProcDef systemProcDef);

	/**
	 * 获取系统规划流程元素集合
	 * 
	 * @return
	 */
	Map<String, SystemProcDef> getSystemProcDefMap(String modelId);

}
