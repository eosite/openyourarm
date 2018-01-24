package com.glaf.datamgr.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.datamgr.mapper.SystemDBFuncMappingMapper")
public interface SystemDBFuncMappingMapper {

	void deleteSystemDBFuncMappings(SystemDBFuncMappingQuery query);

	void deleteSystemDBFuncMappingById(String id);

	SystemDBFuncMapping getSystemDBFuncMappingById(String id);

	int getSystemDBFuncMappingCount(SystemDBFuncMappingQuery query);

	List<SystemDBFuncMapping> getSystemDBFuncMappings(SystemDBFuncMappingQuery query);

	void insertSystemDBFuncMapping(SystemDBFuncMapping model);

	void updateSystemDBFuncMapping(SystemDBFuncMapping model);

}
