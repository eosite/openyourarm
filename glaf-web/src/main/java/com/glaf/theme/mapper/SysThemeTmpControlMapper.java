package com.glaf.theme.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.theme.mapper.SysThemeTmpControlMapper")
public interface SysThemeTmpControlMapper {

	void deleteSysThemeTmpControls(SysThemeTmpControlQuery query);

	void deleteSysThemeTmpControlById(String id);

	SysThemeTmpControl getSysThemeTmpControlById(String id);
	
	List<SysThemeTmpControl> getSysThemeTmpControlsAndCssText(SysThemeTmpControlQuery query);

	int getSysThemeTmpControlCount(SysThemeTmpControlQuery query);

	List<SysThemeTmpControl> getSysThemeTmpControls(SysThemeTmpControlQuery query);

	void insertSysThemeTmpControl(SysThemeTmpControl model);

	void updateSysThemeTmpControl(SysThemeTmpControl model);

	SysThemeTmpControl getThumbnailById(String id);
}
