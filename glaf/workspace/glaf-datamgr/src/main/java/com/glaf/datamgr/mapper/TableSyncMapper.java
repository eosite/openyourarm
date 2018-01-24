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
public interface TableSyncMapper {

	void bulkInsertTableSync(List<TableSync> list);

	void bulkInsertTableSync_oracle(List<TableSync> list);

	void deleteTableSyncById(Long id);

	void deleteTableSyncs(TableSyncQuery query);

	TableSync getTableSyncById(Long id);

	int getTableSyncCount(TableSyncQuery query);

	List<TableSync> getTableSyncs(TableSyncQuery query);

	void insertTableSync(TableSync model);
	
	void resetAllTableSyncStatus();

	void updateTableSync(TableSync model);
	
	void updateTableSyncStatus(TableSync model);

}
