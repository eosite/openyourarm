package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormDatasetMapper")
public interface FormDatasetMapper {

	void deleteFormDatasets(FormDatasetQuery query);

	void deleteFormDatasetById(String id);

	FormDataset getFormDatasetById(String id);

	int getFormDatasetCount(FormDatasetQuery query);

	List<FormDataset> getFormDatasets(FormDatasetQuery query);

	void insertFormDataset(FormDataset model);

	void updateFormDataset(FormDataset model);

	void deleteByColumns(@Param("columns")List<String> params, @Param("widgetId")String ruleId);

	List<FormDataset> queryByParam(@Param("datasetId")String datasetId, @Param("pageId")String pageId, @Param("ruleId")String ruleId, @Param("inId")String inId, @Param("columnName")String columnName);

}
