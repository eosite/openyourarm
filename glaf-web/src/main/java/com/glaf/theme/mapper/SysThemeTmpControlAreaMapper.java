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

@Component("com.glaf.theme.mapper.SysThemeTmpControlAreaMapper")
public interface SysThemeTmpControlAreaMapper {

	void deleteSysThemeTmpControlAreas(SysThemeTmpControlAreaQuery query);

	void deleteSysThemeTmpControlAreaById(String id);

	SysThemeTmpControlArea getSysThemeTmpControlAreaById(String id);

	int getSysThemeTmpControlAreaCount(SysThemeTmpControlAreaQuery query);

	List<SysThemeTmpControlArea> getSysThemeTmpControlAreas(SysThemeTmpControlAreaQuery query);

	void insertSysThemeTmpControlArea(SysThemeTmpControlArea model);

	void updateSysThemeTmpControlArea(SysThemeTmpControlArea model);

}
