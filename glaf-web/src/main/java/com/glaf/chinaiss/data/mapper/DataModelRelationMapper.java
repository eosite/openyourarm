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

@Component("com.glaf.chinaiss.data.mapper.DataModelRelationMapper")
public interface DataModelRelationMapper {

	void deleteDataModelRelations(DataModelRelationQuery query);

	void deleteDataModelRelationById(String id);

	DataModelRelation getDataModelRelationById(String id);

	int getDataModelRelationCount(DataModelRelationQuery query);

	List<DataModelRelation> getDataModelRelations(DataModelRelationQuery query);

	void insertDataModelRelation(DataModelRelation model);

	void updateDataModelRelation(DataModelRelation model);

}
