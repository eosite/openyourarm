package com.glaf.report.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.report.core.mapper.ReportTmpCategoryMapper")
public interface ReportTmpCategoryMapper {

	void deleteReportTmpCategorys(ReportTmpCategoryQuery query);

	void deleteReportTmpCategoryById(Long id);

	ReportTmpCategory getReportTmpCategoryById(Long id);

	int getReportTmpCategoryCount(ReportTmpCategoryQuery query);

	List<ReportTmpCategory> getReportTmpCategorys(ReportTmpCategoryQuery query);

	void insertReportTmpCategory(ReportTmpCategory model);

	void updateReportTmpCategory(ReportTmpCategory model);

}
