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

@Component("com.glaf.theme.mapper.SysThemeTmpLayoutAreaControlMapper")
public interface SysThemeTmpLayoutAreaControlMapper {

	void deleteSysThemeTmpLayoutAreaControls(SysThemeTmpLayoutAreaControlQuery query);

	void deleteSysThemeTmpLayoutAreaControlById(String id);

	SysThemeTmpLayoutAreaControl getSysThemeTmpLayoutAreaControlById(String id);

	int getSysThemeTmpLayoutAreaControlCount(SysThemeTmpLayoutAreaControlQuery query);

	List<SysThemeTmpLayoutAreaControl> getSysThemeTmpLayoutAreaControls(SysThemeTmpLayoutAreaControlQuery query);

	void insertSysThemeTmpLayoutAreaControl(SysThemeTmpLayoutAreaControl model);

	void updateSysThemeTmpLayoutAreaControl(SysThemeTmpLayoutAreaControl model);
	
	SysThemeTmpLayoutAreaControl getThumbnailById(String id);

}
