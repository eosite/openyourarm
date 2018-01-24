package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellTreeDot;
import com.glaf.isdp.query.CellTreeDotQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellTreeDotMapper")
public interface CellTreeDotMapper {

	void deleteCellTreeDots(CellTreeDotQuery query);

	void deleteCellTreeDotById(Integer id);

	CellTreeDot getCellTreeDotById(Integer id);

	int getCellTreeDotCount(CellTreeDotQuery query);

	List<CellTreeDot> getCellTreeDots(CellTreeDotQuery query);

	void insertCellTreeDot(CellTreeDot model);

	void updateCellTreeDot(CellTreeDot model);

}
