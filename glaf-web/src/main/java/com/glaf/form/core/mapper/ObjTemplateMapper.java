package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.ObjTemplate;
import com.glaf.form.core.query.ObjTemplateQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.ObjTemplateMapper")
public interface ObjTemplateMapper {

	void deleteObjTemplates(ObjTemplateQuery query);

	void deleteObjTemplateById(Long id);

	ObjTemplate getObjTemplateById(Long id);
	
	List<ObjTemplate> getCategoryTemplates(@Param("category")Long category);

	int getObjTemplateCount(ObjTemplateQuery query);

	List<ObjTemplate> getObjTemplates(ObjTemplateQuery query);

	void insertObjTemplate(ObjTemplate model);

	void updateObjTemplate(ObjTemplate model);

}
