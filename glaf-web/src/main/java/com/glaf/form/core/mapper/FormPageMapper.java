package com.glaf.form.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.query.FormPageQuery;

/**
 * 
 * Mapper接口
 * 
 */

@Component
public interface FormPageMapper {

	void deleteFormPageById(String id);

	void deleteFormPages(FormPageQuery query);

	List<FormPage> getChildren(String parentId);

	FormPage getFormPageById(String id);

	int getFormPageCount(FormPageQuery query);

	List<FormPage> getFormPages(FormPageQuery query);

	List<FormPage> getFormPageTree(FormPageQuery query);

	void insertFormPage(FormPage model);

	void updateFormPage(FormPage model);

	List<Map<String, Object>> getPageElementsById(@Param("pageId") String pageId);

	void updateFormPageParentId(FormPageQuery query);
	
	void updateThemeId(@Param("id") String id,@Param("themeId") String themeId);
}
