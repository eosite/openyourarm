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

@Component("com.glaf.chinaiss.data.mapper.DataModelColumnsMapper")
public interface DataModelColumnsMapper {

	void deleteDataModelColumnss(DataModelColumnsQuery query);

	void deleteDataModelColumnsById(String id);

	DataModelColumns getDataModelColumnsById(String id);

	int getDataModelColumnsCount(DataModelColumnsQuery query);

	List<DataModelColumns> getDataModelColumnss(DataModelColumnsQuery query);

	void insertDataModelColumns(DataModelColumns model);

	void updateDataModelColumns(DataModelColumns model);

}
