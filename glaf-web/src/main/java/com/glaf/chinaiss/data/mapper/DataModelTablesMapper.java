package com.glaf.chinaiss.data.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.chinaiss.data.mapper.DataModelTablesMapper")
public interface DataModelTablesMapper {

	void deleteDataModelTabless(DataModelTablesQuery query);

	void deleteDataModelTablesById(String id);

	DataModelTables getDataModelTablesById(String id);

	int getDataModelTablesCount(DataModelTablesQuery query);

	List<DataModelTables> getDataModelTabless(DataModelTablesQuery query);

	void insertDataModelTables(DataModelTables model);

	void updateDataModelTables(DataModelTables model);

}
