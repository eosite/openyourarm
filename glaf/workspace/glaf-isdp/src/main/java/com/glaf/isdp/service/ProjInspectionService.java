package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.ProjInspection;
import com.glaf.isdp.query.ProjInspectionQuery;

@Transactional(readOnly = true)
public interface ProjInspectionService {

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
	List<ProjInspection> list(ProjInspectionQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getProjInspectionCountByQueryCriteria(ProjInspectionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ProjInspection> getProjInspectionsByQueryCriteria(int start,
			int pageSize, ProjInspectionQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	ProjInspection getProjInspection(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ProjInspection projInspection);
	/**
	 * 查询TreepInfo表中id以treepInfoIdLike开头的记录总数
	 * @param treepInfoIdLike
	 * @param startDate id开始时间
	 * @param endDate id结束时间
	 * @return
	 */
	Integer getProjInspectionCountByTreepInfoIdLike(String treepInfoIdLike,
			String startDate, String endDate);
	
	/**
	 * 查询intCheck状态对应的index数量
	 * @param intCheck
	 * @return
	 */
	Integer countIntCheck(Integer intCheck);
}
