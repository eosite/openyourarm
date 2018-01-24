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

@Component("com.glaf.dep.report.mapper.DepReportValidationMapper")
public interface DepReportValidationMapper {

	void deleteDepReportValidations(DepReportValidationQuery query);

	void deleteDepReportValidationById(Long id);

	DepReportValidation getDepReportValidationById(Long id);

	int getDepReportValidationCount(DepReportValidationQuery query);

	List<DepReportValidation> getDepReportValidations(DepReportValidationQuery query);

	void insertDepReportValidation(DepReportValidation model);

	void updateDepReportValidation(DepReportValidation model);

}
