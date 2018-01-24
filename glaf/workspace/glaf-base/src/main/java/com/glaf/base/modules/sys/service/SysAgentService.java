package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface SysAgentService {

	@Transactional
	void bulkInsert(List<SysAgent> list);

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
	List<SysAgent> list(SysAgentQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSysAgentCountByQueryCriteria(SysAgentQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysAgent> getSysAgentsByQueryCriteria(int start, int pageSize, SysAgentQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysAgent getSysAgent(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SysAgent sysAgent);

	@Transactional
	void saveAgents(String assignFrom, List<SysAgent> agents);

}
