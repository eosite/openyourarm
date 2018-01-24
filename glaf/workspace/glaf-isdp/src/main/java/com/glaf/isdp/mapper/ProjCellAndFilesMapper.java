package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.ProjCellAndFiles;
import com.glaf.isdp.query.ProjCellAndFilesQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.ProjCellAndFilesMapper")
public interface ProjCellAndFilesMapper {

	void deleteProjCellAndFiless(ProjCellAndFilesQuery query);

	void deleteProjCellAndFilesById(String id);

	ProjCellAndFiles getProjCellAndFilesById(String id);

	int getProjCellAndFilesCount(ProjCellAndFilesQuery query);

	List<ProjCellAndFiles> getProjCellAndFiless(ProjCellAndFilesQuery query);

	void insertProjCellAndFiles(ProjCellAndFiles model);

	void updateProjCellAndFiles(ProjCellAndFiles model);

	List<ProjCellAndFiles> getProjCellList(Integer indexId);
}
