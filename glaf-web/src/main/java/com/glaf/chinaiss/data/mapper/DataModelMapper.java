package com.glaf.chinaiss.data.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.chinaiss.data.mapper.DataModelMapper")
public interface DataModelMapper {

	void deleteDataModels(DataModelQuery query);

	void deleteDataModelById(String id);

	DataModel getDataModelById(String id);

	int getDataModelCount(DataModelQuery query);

	List<DataModel> getDataModels(DataModelQuery query);

	void insertDataModel(DataModel model);

	void updateDataModel(DataModel model);

	List getDataModelsWithTree(Map<String, Object> params);

	List<Map<String, Object>> getDataModelTablesByTreeId(@Param("treeId") String treeId);

}
