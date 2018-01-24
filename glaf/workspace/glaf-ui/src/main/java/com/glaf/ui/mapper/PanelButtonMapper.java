package com.glaf.ui.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.ui.model.PanelButton;
import com.glaf.ui.query.PanelButtonQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.ui.mapper.PanelButtonMapper")
public interface PanelButtonMapper {

	void deletePanelButtons(PanelButtonQuery query);

	void deletePanelButtonById(String id);

	PanelButton getPanelButtonById(String id);

	int getPanelButtonCount(PanelButtonQuery query);

	List<PanelButton> getPanelButtons(PanelButtonQuery query);

	void insertPanelButton(PanelButton model);

	void updatePanelButton(PanelButton model);
	
	List<PanelButton> getPanelButtonByParentId(String pid);

}
