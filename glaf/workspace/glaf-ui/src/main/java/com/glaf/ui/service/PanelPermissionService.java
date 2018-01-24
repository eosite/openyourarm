package com.glaf.ui.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.ui.model.PanelPermission;
import com.glaf.ui.query.*;

@Transactional(readOnly = true)
public interface PanelPermissionService {

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
	void deleteByPanelId(String panelId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<PanelPermission> list(PanelPermissionQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPanelPermissionCountByQueryCriteria(PanelPermissionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<PanelPermission> getPanelPermissionsByQueryCriteria(int start,
			int pageSize, PanelPermissionQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	PanelPermission getPanelPermission(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(PanelPermission panelPermission);

	/**
	 * 保存多条记录
	 * 
	 * @param panelId
	 * @param panelPermissions
	 */
	@Transactional
	void saveAll(String panelId, List<PanelPermission> panelPermissions);

}
