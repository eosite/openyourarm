package com.glaf.report.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.report.core.domain.ReportTemplate;
import com.glaf.report.core.query.ReportTemplateQuery;

 
@Transactional(readOnly = true)
public interface ReportTemplateService {
	 
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
	 List<ReportTemplate> list(ReportTemplateQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportTemplateCountByQueryCriteria(ReportTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportTemplate> getReportTemplatesByQueryCriteria(int start, int pageSize,
			ReportTemplateQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportTemplate getReportTemplate(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportTemplate reportTemplate);

}
