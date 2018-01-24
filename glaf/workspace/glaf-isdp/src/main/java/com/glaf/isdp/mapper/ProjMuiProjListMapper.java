package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.ProjMuiProjList;
import com.glaf.isdp.query.ProjMuiProjListQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.ProjMuiProjListMapper")
public interface ProjMuiProjListMapper {

	void deleteProjMuiProjLists(ProjMuiProjListQuery query);

	void deleteProjMuiProjListById(Integer id);

	ProjMuiProjList getProjMuiProjListById(Integer id);

	int getProjMuiProjListCount(ProjMuiProjListQuery query);

	List<ProjMuiProjList> getProjMuiProjLists(ProjMuiProjListQuery query);

	void insertProjMuiProjList(ProjMuiProjList model);

	void updateProjMuiProjList(ProjMuiProjList model);

	ProjMuiProjList getLocalProjMuiProjList(Integer intLocal);
}
