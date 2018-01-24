package com.glaf.report.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.report.core.domain.SysReportTemplate;
import com.glaf.report.core.query.SysReportTemplateQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.report.mapper.SysReportTemplateMapper")
public interface SysReportTemplateMapper {

	void deleteSysReportTemplates(SysReportTemplateQuery query);

	void deleteSysReportTemplateById(String id);

	SysReportTemplate getSysReportTemplateById(String id);

	int getSysReportTemplateCount(SysReportTemplateQuery query);

	List<SysReportTemplate> getSysReportTemplates(SysReportTemplateQuery query);

	void insertSysReportTemplate(SysReportTemplate model);

	void updateSysReportTemplate(SysReportTemplate model);

}
