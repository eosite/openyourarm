package com.glaf.datamgr.mapper;

 
import java.util.List;
 

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.datamgr.mapper.DataSetMappingItemMapper")
public interface DataSetMappingItemMapper {

	void deleteDataSetMappingItems(DataSetMappingItemQuery query);

	void deleteDataSetMappingItemById(String id);

	DataSetMappingItem getDataSetMappingItemById(String id);

	int getDataSetMappingItemCount(DataSetMappingItemQuery query);

	List<DataSetMappingItem> getDataSetMappingItems(DataSetMappingItemQuery query);

	void insertDataSetMappingItem(DataSetMappingItem model);

	void updateDataSetMappingItem(DataSetMappingItem model);

	void deleteByParentId(@Param("parentId") String parentId);

}
