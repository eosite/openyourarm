package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.ProjInspectionForm;
import com.glaf.isdp.query.ProjInspectionFormQuery;

@Transactional(readOnly = true)
public interface ProjInspectionFormService {

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
	List<ProjInspectionForm> list(ProjInspectionFormQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getProjInspectionFormCountByQueryCriteria(ProjInspectionFormQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ProjInspectionForm> getProjInspectionFormsByQueryCriteria(int start,
			int pageSize, ProjInspectionFormQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ProjInspectionForm getProjInspectionForm(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ProjInspectionForm projInspectionForm);

}
