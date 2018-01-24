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

@Component("com.glaf.datamgr.mapper.TableExecutionColumnMapper")
public interface TableExecutionColumnMapper {

	void bulkInsertTableExecutionColumn(List<TableExecutionColumn> list);

	void bulkInsertTableExecutionColumn_oracle(List<TableExecutionColumn> list);

	void deleteTableExecutionColumnById(String id);

	void deleteTableExecutionColumnsByExecutionId(String executionId);

	TableExecutionColumn getTableExecutionColumnById(String id);

	int getTableExecutionColumnCount(TableExecutionColumnQuery query);
	
	List<TableExecutionColumn> getTableExecutionColumns(TableExecutionColumnQuery query);

	List<TableExecutionColumn> getTableExecutionColumnsByExecutionId(String executionId);

	void insertTableExecutionColumn(TableExecutionColumn model);

	void updateTableExecutionColumn(TableExecutionColumn model);

}
