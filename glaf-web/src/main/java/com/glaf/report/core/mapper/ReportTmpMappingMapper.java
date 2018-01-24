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

@Component("com.glaf.report.core.mapper.ReportTmpMappingMapper")
public interface ReportTmpMappingMapper {

	void deleteReportTmpMappings(ReportTmpMappingQuery query);

	void deleteReportTmpMappingById(String id);

	ReportTmpMapping getReportTmpMappingById(String id);

	int getReportTmpMappingCount(ReportTmpMappingQuery query);

	List<ReportTmpMapping> getReportTmpMappings(ReportTmpMappingQuery query);

	void insertReportTmpMapping(ReportTmpMapping model);

	void updateReportTmpMapping(ReportTmpMapping model);

}
