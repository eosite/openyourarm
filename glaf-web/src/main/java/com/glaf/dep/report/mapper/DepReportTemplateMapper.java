package com.glaf.dep.report.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

/**
 * 
 * Mapper接口
 * 
 */

@Component("com.glaf.dep.report.mapper.DepReportTemplateMapper")
public interface DepReportTemplateMapper {

	void deleteDepReportTemplates(DepReportTemplateQuery query);

	void deleteDepReportTemplateById(Long id);

	DepReportTemplate getDepReportTemplateById(Long id);

	int getDepReportTemplateCount(DepReportTemplateQuery query);

	List<DepReportTemplate> getDepReportTemplates(DepReportTemplateQuery query);

	void insertDepReportTemplate(DepReportTemplate model);

	void updateDepReportTemplate(DepReportTemplate model);

	int getDepReportTemplatesByParamsCount(Map<String, Object> params);

}
