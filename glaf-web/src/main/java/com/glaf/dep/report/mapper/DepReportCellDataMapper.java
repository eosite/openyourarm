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

@Component("com.glaf.dep.report.mapper.DepReportCellDataMapper")
public interface DepReportCellDataMapper {

	void deleteDepReportCellDatas(DepReportCellDataQuery query);

	void deleteDepReportCellDataById(Long id);

	DepReportCellData getDepReportCellDataById(Long id);

	int getDepReportCellDataCount(DepReportCellDataQuery query);

	List<DepReportCellData> getDepReportCellDatas(DepReportCellDataQuery query);

	void insertDepReportCellData(DepReportCellData model);

	void updateDepReportCellData(DepReportCellData model);

}
