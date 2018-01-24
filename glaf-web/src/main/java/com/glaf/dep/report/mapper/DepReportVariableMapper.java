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

@Component("com.glaf.dep.report.mapper.DepReportVariableMapper")
public interface DepReportVariableMapper {

	void deleteDepReportVariables(DepReportVariableQuery query);

	void deleteDepReportVariableById(Long id);

	DepReportVariable getDepReportVariableById(Long id);

	int getDepReportVariableCount(DepReportVariableQuery query);

	List<DepReportVariable> getDepReportVariables(DepReportVariableQuery query);

	void insertDepReportVariable(DepReportVariable model);

	void updateDepReportVariable(DepReportVariable model);

}
