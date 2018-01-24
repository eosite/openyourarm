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
public interface TableSyntheticMapper {

	void bulkInsertTableSynthetic(List<TableSynthetic> list);

	void bulkInsertTableSynthetic_oracle(List<TableSynthetic> list);

	void deleteTableSyntheticById(Long id);

	void deleteTableSynthetics(TableSyntheticQuery query);

	TableSynthetic getTableSyntheticById(Long id);

	int getTableSyntheticCount(TableSyntheticQuery query);

	List<TableSynthetic> getTableSynthetics(TableSyntheticQuery query);

	void insertTableSynthetic(TableSynthetic model);
	
	void resetAllTableSyntheticStatus();

	void updateTableSynthetic(TableSynthetic model);

	void updateTableSyntheticStatus(TableSynthetic model);

}
