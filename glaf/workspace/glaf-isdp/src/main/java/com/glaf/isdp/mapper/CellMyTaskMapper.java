package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellMyTask;
import com.glaf.isdp.query.CellMyTaskQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellMyTaskMapper")
public interface CellMyTaskMapper {

	void deleteCellMyTasks(CellMyTaskQuery query);

	void deleteCellMyTaskById(String id);

	CellMyTask getCellMyTaskById(String id);

	int getCellMyTaskCount(CellMyTaskQuery query);

	List<CellMyTask> getCellMyTasks(CellMyTaskQuery query);

	void insertCellMyTask(CellMyTask model);

	void updateCellMyTask(CellMyTask model);

}
