package com.glaf.form.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormParamsRelation;
import com.glaf.form.core.query.FormParamsRelationQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormParamsRelationMapper")
public interface FormParamsRelationMapper {

	void deleteFormParamsRelations(FormParamsRelationQuery query);

	void deleteFormParamsRelationById(String id);

	FormParamsRelation getFormParamsRelationById(String id);

	int getFormParamsRelationCount(FormParamsRelationQuery query);

	List<FormParamsRelation> getFormParamsRelations(FormParamsRelationQuery query);

	void insertFormParamsRelation(FormParamsRelation model);

	void updateFormParamsRelation(FormParamsRelation model);

	void delete(FormParamsRelationQuery deleteQuery);

	List<Map<String, Object>> queryParamRelation(@Param("pageId") String pageId, @Param("widgetRuleId") String widgetRuleId, @Param("paramName") String paramName,
			@Param("databaseId") String databaseId);

}
