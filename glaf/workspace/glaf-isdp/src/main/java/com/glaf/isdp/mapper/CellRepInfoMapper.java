package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.query.CellRepInfoQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellRepInfoMapper")
public interface CellRepInfoMapper {

	void deleteCellRepInfos(CellRepInfoQuery query);

	void deleteCellRepInfoById(String id);

	CellRepInfo getCellRepInfoById(String id);

	int getCellRepInfoCount(CellRepInfoQuery query);

	List<CellRepInfo> getCellRepInfos(CellRepInfoQuery query);

	void insertCellRepInfo(CellRepInfo model);

	void updateCellRepInfo(CellRepInfo model);

}
