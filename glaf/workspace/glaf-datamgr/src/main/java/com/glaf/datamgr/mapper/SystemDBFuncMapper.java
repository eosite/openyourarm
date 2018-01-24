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

@Component("com.glaf.datamgr.mapper.SystemDBFuncMapper")
public interface SystemDBFuncMapper {

	void deleteSystemDBFuncs(SystemDBFuncQuery query);

	void deleteSystemDBFuncById(String id);

	SystemDBFunc getSystemDBFuncById(String id);

	int getSystemDBFuncCount(SystemDBFuncQuery query);

	List<SystemDBFunc> getSystemDBFuncs(SystemDBFuncQuery query);

	void insertSystemDBFunc(SystemDBFunc model);

	void updateSystemDBFunc(SystemDBFunc model);

}
