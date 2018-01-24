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

@Component("com.glaf.theme.mapper.SysThemeTmpLayoutMapper")
public interface SysThemeTmpLayoutMapper {

	void deleteSysThemeTmpLayouts(SysThemeTmpLayoutQuery query);

	void deleteSysThemeTmpLayoutById(String id);

	SysThemeTmpLayout getSysThemeTmpLayoutById(String id);

	int getSysThemeTmpLayoutCount(SysThemeTmpLayoutQuery query);

	List<SysThemeTmpLayout> getSysThemeTmpLayouts(SysThemeTmpLayoutQuery query);

	void insertSysThemeTmpLayout(SysThemeTmpLayout model);

	void updateSysThemeTmpLayout(SysThemeTmpLayout model);

	SysThemeTmpLayout getThumbnailById(String id);
}
