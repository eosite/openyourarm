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

@Component("com.glaf.report.core.mapper.ReportTmpColMapper")
public interface ReportTmpColMapper {

	void deleteReportTmpCols(ReportTmpColQuery query);

	void deleteReportTmpColById(String id);

	ReportTmpCol getReportTmpColById(String id);

	int getReportTmpColCount(ReportTmpColQuery query);

	List<ReportTmpCol> getReportTmpCols(ReportTmpColQuery query);

	void insertReportTmpCol(ReportTmpCol model);

	void updateReportTmpCol(ReportTmpCol model);

}
