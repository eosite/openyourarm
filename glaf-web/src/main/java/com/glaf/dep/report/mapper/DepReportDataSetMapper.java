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

@Component("com.glaf.dep.report.mapper.DepReportDataSetMapper")
public interface DepReportDataSetMapper {

	void deleteDepReportDataSets(DepReportDataSetQuery query);

	void deleteDepReportDataSetById(Long id);

	DepReportDataSet getDepReportDataSetById(Long id);

	int getDepReportDataSetCount(DepReportDataSetQuery query);

	List<DepReportDataSet> getDepReportDataSets(DepReportDataSetQuery query);

	void insertDepReportDataSet(DepReportDataSet model);

	void updateDepReportDataSet(DepReportDataSet model);

}
