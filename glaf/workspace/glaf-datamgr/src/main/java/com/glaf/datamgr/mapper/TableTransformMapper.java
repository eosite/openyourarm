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
public interface TableTransformMapper {

	void bulkInsertTableTransform(List<TableTransform> list);

	void bulkInsertTableTransform_oracle(List<TableTransform> list);

	void deleteTableTransformById(String id);

	TableTransform getTableTransformById(String id);

	int getTableTransformCount(TableTransformQuery query);

	List<TableTransform> getTableTransforms(TableTransformQuery query);

	void insertTableTransform(TableTransform model);
	
	void resetAllTableTransformStatus();

	void updateTableTransform(TableTransform model);
	
	void updateTableTransformStatus(TableTransform model);

}
