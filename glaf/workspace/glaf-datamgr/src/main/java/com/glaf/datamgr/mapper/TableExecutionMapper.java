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

@Component("com.glaf.datamgr.mapper.TableExecutionMapper")
public interface TableExecutionMapper {

	void bulkInsertTableExecution(List<TableExecution> list);

	void bulkInsertTableExecution_oracle(List<TableExecution> list);

	void deleteTableExecutions(TableExecutionQuery query);

	void deleteTableExecutionById(String id);

	TableExecution getTableExecutionById(String id);

	int getTableExecutionCount(TableExecutionQuery query);

	List<TableExecution> getTableExecutions(TableExecutionQuery query);

	void insertTableExecution(TableExecution model);

	void updateTableExecution(TableExecution model);

}
