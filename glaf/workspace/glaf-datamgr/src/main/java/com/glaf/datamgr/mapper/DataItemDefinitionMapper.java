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
public interface DataItemDefinitionMapper {

	void bulkInsertDataItemDefinition(List<DataItemDefinition> list);

	void bulkInsertDataItemDefinition_oracle(List<DataItemDefinition> list);

	void deleteDataItemDefinitionById(Long id);

	DataItemDefinition getDataItemDefinitionById(Long id);

	DataItemDefinition getDataItemDefinitionByCode(String code);

	int getDataItemDefinitionCount(DataItemDefinitionQuery query);

	List<DataItemDefinition> getDataItemDefinitions(DataItemDefinitionQuery query);

	void insertDataItemDefinition(DataItemDefinition model);

	void updateDataItemDefinition(DataItemDefinition model);

}
