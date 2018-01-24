package com.glaf.form.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.domain.StuffByPageIdsModel;
import com.glaf.form.core.query.FormRulePropertyQuery;

/**
 * 
 * Mapper接口
 * 
 */

@Component
public interface FormRulePropertyMapper {

	void deleteFormRulePropertys(FormRulePropertyQuery query);

	void deleteFormRulePropertyById(String id);

	FormRuleProperty getFormRulePropertyById(String id);

	FormRuleProperty getPageComponentPropertyRule(
			@Param("pageId") String pageId, @Param("dataRole") String dataRole,
			@Param("propName") String propName,@Param("id") String id);
	
	FormRuleProperty getPageComponentPropertyRule_oracle(@Param("pageId") String pageId, 
			@Param("dataRole") String dataRole,
			@Param("propName") String propName,@Param("id") String id);

	int getFormRulePropertyCount(FormRulePropertyQuery query);

	List<FormRuleProperty> getFormRulePropertys(FormRulePropertyQuery query);

	void insertFormRuleProperty(FormRuleProperty model);

	void updateFormRuleProperty(FormRuleProperty model);

	void deleteFormRulePropertyByRuleId(String rid);

	List<Map<String, Object>> getFormRuleListByRid(String rid);
	
	List<Map<String, Object>> getFormRuleListByRid_oracle(String rid);
	List<Map<String, Object>> getFormRuleListByRid_postgresql(String rid);

	List<Map<String, Object>> getParametersByPageId(@Param("pageId") String pageId);
	
	List<Map<String, Object>> getParametersByPageId_oracle(@Param("pageId") String pageId);

	List<Map<String, Object>> getStuffByPageIdAndNameLike(@Param("pageId") String pageId, @Param("name") String name);
	List<Map<String, Object>> getStuffByPageIdAndNameLike_oracle(@Param("pageId") String pageId, @Param("name") String name);

	List<FormRuleProperty> getAttributeDatasByPageId(@Param("pageId")String pageId, @Param("name")String attrName);

	void deleteFormRulePropertyByRuleIds(List<String> ruleIds);

	FormRuleProperty getRuleByName(@Param("pageId")String pageId, @Param("widgetId")String widgetId, @Param("name")String name);

	List<Map<String, Object>> getStuffByPageIdsAndNameLike(Map<String,Object> map);
	List<StuffByPageIdsModel> getStuffByPageIdsAndNameLike2(Map<String,Object> map);

	List<Map<String, Object>> getStuffByPageIdsAndNameLike_oracle(Map<String,Object> map);

}
