package com.glaf.isdp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.query.CellUTableTreeQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellUTableTreeMapper")
public interface CellUTableTreeMapper {

	void deleteCellUTableTrees(CellUTableTreeQuery query);

	void deleteCellUTableTreeByIndexId(Integer indexId);

	CellUTableTree getCellUTableTreeByIndexId(Integer indexId);

	int getCellUTableTreeCount(CellUTableTreeQuery query);

	List<CellUTableTree> getCellUTableTrees(CellUTableTreeQuery query);

	void insertCellUTableTree(CellUTableTree model);

	void updateCellUTableTree(CellUTableTree model);

	List<CellUTableTree> getAllChildCellUTableTrees(Integer indexId);
	List<CellUTableTree> getAllChildCellUTableTrees_oracle(Integer indexId);

	List<CellUTableTree> getCellUTableTreesAndChildCountByTableType(@Param("tableType") Integer tableType,
			@Param("level") Integer level, @Param("parentId") Integer parentId);
}
