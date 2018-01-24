package com.glaf.report.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.report.core.domain.SysReportTemplate;
import com.glaf.report.core.query.SysReportTemplateQuery;

@Transactional(readOnly = true)
public interface SysReportTemplateService {

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
	List<SysReportTemplate> list(SysReportTemplateQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSysReportTemplateCountByQueryCriteria(SysReportTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<SysReportTemplate> getSysReportTemplatesByQueryCriteria(int start,
			int pageSize, SysReportTemplateQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	SysReportTemplate getSysReportTemplate(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(SysReportTemplate sysReportTemplate);

}
