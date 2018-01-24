package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.ProjInspectionForm;
import com.glaf.isdp.query.ProjInspectionFormQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.ProjInspectionFormMapper")
public interface ProjInspectionFormMapper {

	void deleteProjInspectionForms(ProjInspectionFormQuery query);

	void deleteProjInspectionFormById(String id);

	ProjInspectionForm getProjInspectionFormById(String id);

	int getProjInspectionFormCount(ProjInspectionFormQuery query);

	List<ProjInspectionForm> getProjInspectionForms(ProjInspectionFormQuery query);

	void insertProjInspectionForm(ProjInspectionForm model);

	void updateProjInspectionForm(ProjInspectionForm model);

}
