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

@Component("com.glaf.dep.report.mapper.DepReportCellMapper")
public interface DepReportCellMapper {

	void deleteDepReportCells(DepReportCellQuery query);

	void deleteDepReportCellById(Long id);

	DepReportCell getDepReportCellById(Long id);

	int getDepReportCellCount(DepReportCellQuery query);

	List<DepReportCell> getDepReportCells(DepReportCellQuery query);

	void insertDepReportCell(DepReportCell model);

	void updateDepReportCell(DepReportCell model);

}
