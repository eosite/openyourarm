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

@Component("com.glaf.form.core.mapper.FormDatasetRelationMapper")
public interface FormDatasetRelationMapper {

	void deleteFormDatasetRelations(FormDatasetRelationQuery query);

	void deleteFormDatasetRelationById(String id);

	FormDatasetRelation getFormDatasetRelationById(String id);

	int getFormDatasetRelationCount(FormDatasetRelationQuery query);

	List<FormDatasetRelation> getFormDatasetRelations(FormDatasetRelationQuery query);

	void insertFormDatasetRelation(FormDatasetRelation model);

	void updateFormDatasetRelation(FormDatasetRelation model);

	void deleteByColumns(@Param("pids") List<String> pids,@Param("widgetId") String ruleId,@Param("attrName") String attrName);

	void delete(FormDatasetRelationQuery deleteQuery);

}
