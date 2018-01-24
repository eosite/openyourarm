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

@Component("com.glaf.dep.report.mapper.DepReportCellPosMapper")
public interface DepReportCellPosMapper {

	void deleteDepReportCellPoss(DepReportCellPosQuery query);

	void deleteDepReportCellPosById(Long id);

	DepReportCellPos getDepReportCellPosById(Long id);

	int getDepReportCellPosCount(DepReportCellPosQuery query);

	List<DepReportCellPos> getDepReportCellPoss(DepReportCellPosQuery query);

	void insertDepReportCellPos(DepReportCellPos model);

	void updateDepReportCellPos(DepReportCellPos model);

}
