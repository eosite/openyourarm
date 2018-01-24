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

@Component("com.glaf.report.core.mapper.ReportTmpDataSetMapper")
public interface ReportTmpDataSetMapper {

	void deleteReportTmpDataSets(ReportTmpDataSetQuery query);

	void deleteReportTmpDataSetById(String id);

	ReportTmpDataSet getReportTmpDataSetById(String id);

	int getReportTmpDataSetCount(ReportTmpDataSetQuery query);

	List<ReportTmpDataSet> getReportTmpDataSets(ReportTmpDataSetQuery query);

	void insertReportTmpDataSet(ReportTmpDataSet model);

	void updateReportTmpDataSet(ReportTmpDataSet model);

}
