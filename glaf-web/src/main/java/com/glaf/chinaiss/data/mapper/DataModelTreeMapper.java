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

@Component("com.glaf.chinaiss.data.mapper.DataModelTreeMapper")
public interface DataModelTreeMapper {

	void deleteDataModelTrees(DataModelTreeQuery query);

	void deleteDataModelTreeById(String id);

	DataModelTree getDataModelTreeById(String id);

	int getDataModelTreeCount(DataModelTreeQuery query);

	List<DataModelTree> getDataModelTrees(DataModelTreeQuery query);

	void insertDataModelTree(DataModelTree model);

	void updateDataModelTree(DataModelTree model);

}
