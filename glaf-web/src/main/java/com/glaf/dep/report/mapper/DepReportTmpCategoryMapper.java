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

@Component("com.glaf.dep.report.mapper.DepReportTmpCategoryMapper")
public interface DepReportTmpCategoryMapper {

	void deleteDepReportTmpCategorys(DepReportTmpCategoryQuery query);

	void deleteDepReportTmpCategoryById(Long id);

	DepReportTmpCategory getDepReportTmpCategoryById(Long id);

	int getDepReportTmpCategoryCount(DepReportTmpCategoryQuery query);

	List<DepReportTmpCategory> getDepReportTmpCategorys(DepReportTmpCategoryQuery query);

	void insertDepReportTmpCategory(DepReportTmpCategory model);

	void updateDepReportTmpCategory(DepReportTmpCategory model);

}
