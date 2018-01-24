package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellMyTaskMain;
import com.glaf.isdp.query.CellMyTaskMainQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellMyTaskMainMapper")
public interface CellMyTaskMainMapper {

	void deleteCellMyTaskMains(CellMyTaskMainQuery query);

	void deleteCellMyTaskMainById(String id);

	CellMyTaskMain getCellMyTaskMainById(String id);

	int getCellMyTaskMainCount(CellMyTaskMainQuery query);

	List<CellMyTaskMain> getCellMyTaskMains(CellMyTaskMainQuery query);

	void insertCellMyTaskMain(CellMyTaskMain model);

	void updateCellMyTaskMain(CellMyTaskMain model);

	List<CellMyTaskMain> selectCellMyTaskMainExtProcess(
			CellMyTaskMainQuery query);

	CellMyTaskMain selectCellMyTaskMainByProcessId(String processId);

}
