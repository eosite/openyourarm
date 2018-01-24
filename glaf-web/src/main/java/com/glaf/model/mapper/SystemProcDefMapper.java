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

@Component("com.glaf.model.mapper.SystemProcDefMapper")
public interface SystemProcDefMapper {

	void deleteSystemProcDefs(SystemProcDefQuery query);

	void deleteSystemProcDefById(String id);

	void deleteSystemProcDefBySysId(String id);
	
	SystemProcDef getSystemProcDefById(String id);

	int getSystemProcDefCount(SystemProcDefQuery query);

	List<SystemProcDef> getSystemProcDefs(SystemProcDefQuery query);

	void insertSystemProcDef(SystemProcDef model);

	void updateSystemProcDef(SystemProcDef model);
	
	void updateProcDefNameType(SystemProcDef systemProcDef);

}
