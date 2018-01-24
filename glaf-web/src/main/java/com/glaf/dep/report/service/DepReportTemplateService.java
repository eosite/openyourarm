package com.glaf.dep.report.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

@Transactional(readOnly = true)
public interface DepReportTemplateService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepReportTemplate> list(DepReportTemplateQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepReportTemplateCountByQueryCriteria(DepReportTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepReportTemplate> getDepReportTemplatesByQueryCriteria(int start, int pageSize, DepReportTemplateQuery query);

	int getDepReportTemplatesByParamsCount(Map<String, Object> params);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Map<String, Object>> getDepReportTemplatesByParams(int start, int pageSize, Map<String, Object> params);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepReportTemplate getDepReportTemplate(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepReportTemplate depReportTemplate);

	/**
	 * 获取cell主表
	 * 
	 * @param id
	 * @return
	 */
	String getTableName(Long id);

}
