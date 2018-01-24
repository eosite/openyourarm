package com.glaf.isdp.mapper;

import java.util.*;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

@Component("com.glaf.isdp.mapper.CellUpicInfoMapper")
public interface CellUpicInfoMapper {

	void deleteCellUpicInfos(CellUpicInfoQuery query);

	void deleteCellUpicInfoById(String id);

	CellUpicInfo getCellUpicInfoById(String id);
	
	IsdpDataFile getCellDataFileById(String id);
	
	IsdpDataFile getCellDataFileById_postgres(String id);

	int getCellUpicInfoCount(Map<String, Object> parameter);

	int getCellUpicInfoCountByQueryCriteria(CellUpicInfoQuery query);

	List<CellUpicInfo> getCellUpicInfos(Map<String, Object> parameter);

	List<Map<String,Object>> getCellUpicInfoMapList(Map<String, Object> parameter);

	List<CellUpicInfo> getCellUpicInfosByQueryCriteria(CellUpicInfoQuery query);

	void insertCellUpicInfo(CellUpicInfo model);

	void updateCellUpicInfo(CellUpicInfo model);
	
	
	void deleteFolderById(String id);

	void updateCellDataFile(IsdpDataFile model);

	void insertCellDataFile(IsdpDataFile model);

	int getCellUpicInfoCountByLikeId(String id);

}
