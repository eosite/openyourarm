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

@Component("com.glaf.dep.report.mapper.DepReportFormulaMapper")
public interface DepReportFormulaMapper {

	void deleteDepReportFormulas(DepReportFormulaQuery query);

	void deleteDepReportFormulaById(Long id);

	DepReportFormula getDepReportFormulaById(Long id);

	int getDepReportFormulaCount(DepReportFormulaQuery query);

	List<DepReportFormula> getDepReportFormulas(DepReportFormulaQuery query);

	void insertDepReportFormula(DepReportFormula model);

	void updateDepReportFormula(DepReportFormula model);

}
