package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellRepInfo2;
import com.glaf.isdp.query.CellRepInfo2Query;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellRepInfo2Mapper")
public interface CellRepInfo2Mapper {

	void deleteCellRepInfo2s(CellRepInfo2Query query);

	void deleteCellRepInfo2ById(String id);

	CellRepInfo2 getCellRepInfo2ById(String id);

	int getCellRepInfo2Count(CellRepInfo2Query query);

	List<CellRepInfo2> getCellRepInfo2s(CellRepInfo2Query query);

	void insertCellRepInfo2(CellRepInfo2 model);

	void updateCellRepInfo2(CellRepInfo2 model);

}
