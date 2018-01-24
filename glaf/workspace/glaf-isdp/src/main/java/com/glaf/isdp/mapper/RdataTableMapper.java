package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.RdataTableMapper")
public interface RdataTableMapper {

	void deleteRdataTables(RdataTableQuery query);

	void deleteRdataTableById(String id);

	RdataTable getRdataTableById(String id);

	int getRdataTableCount(RdataTableQuery query);

	List<RdataTable> getRdataTables(RdataTableQuery query);

	void insertRdataTable(RdataTable model);

	void updateRdataTable(RdataTable model);

	int getNextMaxUser();

	RdataTable getRdataTableByTableName(String tableName);

}
