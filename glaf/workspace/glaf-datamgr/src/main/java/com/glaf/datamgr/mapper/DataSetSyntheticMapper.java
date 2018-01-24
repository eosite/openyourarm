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
public interface DataSetSyntheticMapper {

	void bulkInsertDataSetSynthetic(List<DataSetSynthetic> list);

	void bulkInsertDataSetSynthetic_oracle(List<DataSetSynthetic> list);

	void deleteDataSetSyntheticById(Long id);

	void deleteDataSetSynthetics(DataSetSyntheticQuery query);

	DataSetSynthetic getDataSetSyntheticById(Long id);

	int getDataSetSyntheticCount(DataSetSyntheticQuery query);

	List<DataSetSynthetic> getDataSetSynthetics(DataSetSyntheticQuery query);

	void insertDataSetSynthetic(DataSetSynthetic model);

	void resetAllDataSetSyntheticStatus();
	
	void updateDataSetSynthetic(DataSetSynthetic model);
	
	void updateDataSetSyntheticStatus(DataSetSynthetic model);

}
