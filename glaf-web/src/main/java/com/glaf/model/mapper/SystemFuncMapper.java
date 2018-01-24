package com.glaf.model.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.model.domain.*;
import com.glaf.model.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.model.mapper.SystemFuncMapper")
public interface SystemFuncMapper {

	void deleteSystemFuncs(SystemFuncQuery query);

	void deleteSystemFuncById(String id);
	
	void deleteSystemFuncBySysId(String id);

	SystemFunc getSystemFuncById(String id);

	int getSystemFuncCount(SystemFuncQuery query);

	List<SystemFunc> getSystemFuncs(SystemFuncQuery query);

	void insertSystemFunc(SystemFunc model);

	void updateSystemFunc(SystemFunc model);
	
	void updateFuncNameType(SystemFunc systemFunc);

}
