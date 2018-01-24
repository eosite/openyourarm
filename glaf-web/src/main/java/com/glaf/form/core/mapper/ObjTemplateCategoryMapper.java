package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.ObjTemplateCategory;
import com.glaf.form.core.query.ObjTemplateCategoryQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.ObjTemplateCategoryMapper")
public interface ObjTemplateCategoryMapper {

	void deleteObjTemplateCategorys(ObjTemplateCategoryQuery query);

	void deleteObjTemplateCategoryById(Long id);

	ObjTemplateCategory getObjTemplateCategoryById(Long id);

	ObjTemplateCategory getObjTemplateCategoryByTemplateId(Long id);
	
	int getObjTemplateCategoryCount(ObjTemplateCategoryQuery query);

	List<ObjTemplateCategory> getObjTemplateCategorys(ObjTemplateCategoryQuery query);

	void insertObjTemplateCategory(ObjTemplateCategory model);

	void updateObjTemplateCategory(ObjTemplateCategory model);

}
