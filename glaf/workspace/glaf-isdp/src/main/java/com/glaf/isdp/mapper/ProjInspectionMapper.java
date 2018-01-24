package com.glaf.isdp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.ProjInspection;
import com.glaf.isdp.query.ProjInspectionQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.ProjInspectionMapper")
public interface ProjInspectionMapper {

	void deleteProjInspections(ProjInspectionQuery query);

	void deleteProjInspectionById(String id);

	ProjInspection getProjInspectionById(String id);

	int getProjInspectionCount(ProjInspectionQuery query);

	List<ProjInspection> getProjInspections(ProjInspectionQuery query);

	void insertProjInspection(ProjInspection model);

	void updateProjInspection(ProjInspection model);

	Integer getProjInspectionCountByTreepInfoIdLike(String treepInfoIdLike,
			String startDate, String endDate);

	Integer countIntCheck(@Param("intCheck")Integer intCheck);

}
