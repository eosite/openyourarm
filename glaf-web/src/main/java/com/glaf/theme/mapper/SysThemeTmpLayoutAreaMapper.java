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

@Component("com.glaf.theme.mapper.SysThemeTmpLayoutAreaMapper")
public interface SysThemeTmpLayoutAreaMapper {

	void deleteSysThemeTmpLayoutAreas(SysThemeTmpLayoutAreaQuery query);

	void deleteSysThemeTmpLayoutAreaById(String id);

	SysThemeTmpLayoutArea getSysThemeTmpLayoutAreaById(String id);

	int getSysThemeTmpLayoutAreaCount(SysThemeTmpLayoutAreaQuery query);

	List<SysThemeTmpLayoutArea> getSysThemeTmpLayoutAreas(SysThemeTmpLayoutAreaQuery query);

	void insertSysThemeTmpLayoutArea(SysThemeTmpLayoutArea model);

	void updateSysThemeTmpLayoutArea(SysThemeTmpLayoutArea model);

}
