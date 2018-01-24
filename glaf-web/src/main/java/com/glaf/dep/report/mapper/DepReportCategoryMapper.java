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

@Component("com.glaf.dep.report.mapper.DepReportCategoryMapper")
public interface DepReportCategoryMapper {

	void deleteDepReportCategorys(DepReportCategoryQuery query);

	void deleteDepReportCategoryById(Long id);

	DepReportCategory getDepReportCategoryById(Long id);

	int getDepReportCategoryCount(DepReportCategoryQuery query);

	List<DepReportCategory> getDepReportCategorys(DepReportCategoryQuery query);

	void insertDepReportCategory(DepReportCategory model);

	void updateDepReportCategory(DepReportCategory model);

}
