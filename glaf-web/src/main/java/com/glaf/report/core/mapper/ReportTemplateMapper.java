package com.glaf.report.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.report.core.domain.ReportTemplate;
import com.glaf.report.core.query.ReportTemplateQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.report.mapper.ReportTemplateMapper")
public interface ReportTemplateMapper {

	void deleteReportTemplates(ReportTemplateQuery query);

	void deleteReportTemplateById(String id);

	ReportTemplate getReportTemplateById(String id);

	int getReportTemplateCount(ReportTemplateQuery query);

	List<ReportTemplate> getReportTemplates(ReportTemplateQuery query);

	void insertReportTemplate(ReportTemplate model);

	void updateReportTemplate(ReportTemplate model);

}
