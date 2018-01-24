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

@Component("com.glaf.model.mapper.SystemDefMapper")
public interface SystemDefMapper {

	void deleteSystemDefs(SystemDefQuery query);

	void deleteSystemDefById(String id);

	void deleteSystemDefBySysId(String id);

	SystemDef getSystemDefById(String id);

	int getSystemDefCount(SystemDefQuery query);
	
	

	List<SystemDef> getSystemDefs(SystemDefQuery query);

	void insertSystemDef(SystemDef model);

	void updateSystemDef(SystemDef model);

}
