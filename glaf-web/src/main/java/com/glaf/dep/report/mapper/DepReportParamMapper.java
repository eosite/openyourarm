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

@Component("com.glaf.dep.report.mapper.DepReportParamMapper")
public interface DepReportParamMapper {

	void deleteDepReportParams(DepReportParamQuery query);

	void deleteDepReportParamById(Long id);

	DepReportParam getDepReportParamById(Long id);

	int getDepReportParamCount(DepReportParamQuery query);

	List<DepReportParam> getDepReportParams(DepReportParamQuery query);

	void insertDepReportParam(DepReportParam model);

	void updateDepReportParam(DepReportParam model);

}
