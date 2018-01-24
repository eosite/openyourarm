package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface DataPermissionMapper {

	void bulkInsertDataPermission(List<DataPermission> list);

	void bulkInsertDataPermission_oracle(List<DataPermission> list);

	void deleteDataPermissionById(long id);

	void deleteDataPermissionsByBusinessType(String businessType);

	DataPermission getDataPermissionById(long id);

	int getDataPermissionCount(DataPermissionQuery query);

	List<DataPermission> getDataPermissions(DataPermissionQuery query);

	void insertDataPermission(DataPermission model);

}
