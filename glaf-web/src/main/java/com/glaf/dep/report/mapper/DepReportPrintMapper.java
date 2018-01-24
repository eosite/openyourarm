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

@Component("com.glaf.dep.report.mapper.DepReportPrintMapper")
public interface DepReportPrintMapper {

	void deleteDepReportPrints(DepReportPrintQuery query);

	void deleteDepReportPrintById(Long id);

	DepReportPrint getDepReportPrintById(Long id);

	int getDepReportPrintCount(DepReportPrintQuery query);

	List<DepReportPrint> getDepReportPrints(DepReportPrintQuery query);

	void insertDepReportPrint(DepReportPrint model);

	void updateDepReportPrint(DepReportPrint model);

}
