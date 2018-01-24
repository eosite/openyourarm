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

@Component("com.glaf.dep.report.mapper.DepReportWdataSetMapper")
public interface DepReportWdataSetMapper {

	void deleteDepReportWdataSets(DepReportWdataSetQuery query);

	void deleteDepReportWdataSetById(Long id);

	DepReportWdataSet getDepReportWdataSetById(Long id);

	int getDepReportWdataSetCount(DepReportWdataSetQuery query);

	List<DepReportWdataSet> getDepReportWdataSets(DepReportWdataSetQuery query);

	void insertDepReportWdataSet(DepReportWdataSet model);

	void updateDepReportWdataSet(DepReportWdataSet model);

}
