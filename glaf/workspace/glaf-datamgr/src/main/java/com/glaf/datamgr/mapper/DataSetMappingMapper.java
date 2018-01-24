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

@Component("com.glaf.datamgr.mapper.DataSetMappingMapper")
public interface DataSetMappingMapper {

	void deleteDataSetMappings(DataSetMappingQuery query);

	void deleteDataSetMappingById(String id);

	DataSetMapping getDataSetMappingById(String id);

	int getDataSetMappingCount(DataSetMappingQuery query);

	List<DataSetMapping> getDataSetMappings(DataSetMappingQuery query);

	void insertDataSetMapping(DataSetMapping model);

	void updateDataSetMapping(DataSetMapping model);

}
