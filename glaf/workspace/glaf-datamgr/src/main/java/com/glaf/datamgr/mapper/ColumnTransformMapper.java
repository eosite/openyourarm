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
public interface ColumnTransformMapper {

	void bulkInsertColumnTransform(List<ColumnTransform> list);

	void bulkInsertColumnTransform_oracle(List<ColumnTransform> list);

	void deleteColumnTransformById(Long id);
	
	void deleteColumnTransformsByByTable(String tableName);

	ColumnTransform getColumnTransformById(Long id);

	int getColumnTransformCount(ColumnTransformQuery query);

	List<ColumnTransform> getColumnTransforms(ColumnTransformQuery query);

	List<ColumnTransform> getColumnTransformsByTable(String tableName);

	void insertColumnTransform(ColumnTransform model);

	void updateColumnTransform(ColumnTransform model);

}
