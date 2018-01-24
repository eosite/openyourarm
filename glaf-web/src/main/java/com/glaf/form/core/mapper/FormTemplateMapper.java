package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormTemplate;
import com.glaf.form.core.query.FormTemplateQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("formTemplateMapper")
public interface FormTemplateMapper {

	void deleteFormTemplates(FormTemplateQuery query);

	void deleteFormTemplateById(Integer id);

	FormTemplate getFormTemplateById(Integer id);
	
	FormTemplate getFormTemplateById_oracle(Integer id);

	int getFormTemplateCount(FormTemplateQuery query);

	List<FormTemplate> getFormTemplates(FormTemplateQuery query);
	
	List<FormTemplate> getFormTemplates_oracle(FormTemplateQuery query);

	void insertFormTemplate(FormTemplate model);

	void updateFormTemplate(FormTemplate model);

	FormTemplate getTemplateImage(Integer id);

}
