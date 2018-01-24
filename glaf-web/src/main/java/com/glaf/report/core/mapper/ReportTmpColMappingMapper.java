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

@Component("com.glaf.report.core.mapper.ReportTmpColMappingMapper")
public interface ReportTmpColMappingMapper {

	void deleteReportTmpColMappings(ReportTmpColMappingQuery query);

	void deleteReportTmpColMappingById(String id);

	ReportTmpColMapping getReportTmpColMappingById(String id);

	int getReportTmpColMappingCount(ReportTmpColMappingQuery query);

	List<ReportTmpColMapping> getReportTmpColMappings(ReportTmpColMappingQuery query);

	void insertReportTmpColMapping(ReportTmpColMapping model);

	void updateReportTmpColMapping(ReportTmpColMapping model);

	List<ReportTmpColMapping> getReportTmpColMappingsByTmpMappingId(String id);

	void deleteReportTmpColMappingByParentId(String id);

	void deleteIfDataSetNotExists();

}
