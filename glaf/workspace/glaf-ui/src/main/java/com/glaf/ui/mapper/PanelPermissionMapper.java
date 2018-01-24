package com.glaf.ui.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.ui.model.PanelPermission;
import com.glaf.ui.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface PanelPermissionMapper {

	void deletePanelPermissionsByPanelId(String panelId);

	void deletePanelPermissionById(String id);

	PanelPermission getPanelPermissionById(String id);

	int getPanelPermissionCount(PanelPermissionQuery query);

	List<PanelPermission> getPanelPermissions(PanelPermissionQuery query);

	List<PanelPermission> getPanelPermissionsByPanelId(String panelId);

	void insertPanelPermission(PanelPermission model);

}
