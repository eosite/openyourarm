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

@Component("com.glaf.theme.mapper.SysThemeTmpMapper")
public interface SysThemeTmpMapper {

	void deleteSysThemeTmps(SysThemeTmpQuery query);

	void deleteSysThemeTmpById(String id);

	SysThemeTmp getSysThemeTmpById(String id);

	SysThemeTmp getThumbnailById(String id);
	
	int getSysThemeTmpCount(SysThemeTmpQuery query);

	List<SysThemeTmp> getSysThemeTmps(SysThemeTmpQuery query);

	void insertSysThemeTmp(SysThemeTmp model);

	void updateSysThemeTmp(SysThemeTmp model);

}
