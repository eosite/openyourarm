package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.datamgr.domain.DataSetExport;
import com.glaf.datamgr.query.DataSetExportQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.datamgr.mapper.DataSetExportMapper")
public interface DataSetExportMapper {

	void bulkInsertDataSetExport(List<DataSetExport> list);

	void bulkInsertDataSetExport_oracle(List<DataSetExport> list);

	void deleteDataSetExportById(String id);

	void deleteDataSetExports(DataSetExportQuery query);

	DataSetExport getDataSetExportById(String id);

	int getDataSetExportCount(DataSetExportQuery query);

	List<DataSetExport> getDataSetExports(DataSetExportQuery query);

	void insertDataSetExport(DataSetExport model);

	void updateDataSetExport(DataSetExport model);

}
