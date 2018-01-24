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

@Component("com.glaf.report.core.mapper.ReportTmpDataSetMappingMapper")
public interface ReportTmpDataSetMappingMapper {

	void deleteReportTmpDataSetMappings(ReportTmpDataSetMappingQuery query);

	void deleteReportTmpDataSetMappingById(String id);

	ReportTmpDataSetMapping getReportTmpDataSetMappingById(String id);

	int getReportTmpDataSetMappingCount(ReportTmpDataSetMappingQuery query);

	List<ReportTmpDataSetMapping> getReportTmpDataSetMappings(ReportTmpDataSetMappingQuery query);

	void insertReportTmpDataSetMapping(ReportTmpDataSetMapping model);

	void updateReportTmpDataSetMapping(ReportTmpDataSetMapping model);

	void deleteReportTmpDataSetMappingByParentId(String id);

}
