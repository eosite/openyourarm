package com.glaf.form.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormEventTemplateMapper")
public interface FormEventTemplateMapper {

	void deleteFormEventTemplates(FormEventTemplateQuery query);

	void deleteFormEventTemplateById(String id);

	FormEventTemplate getFormEventTemplateById(String id);

	int getFormEventTemplateCount(FormEventTemplateQuery query);

	List<FormEventTemplate> getFormEventTemplates(FormEventTemplateQuery query);

	void insertFormEventTemplate(FormEventTemplate model);

	void updateFormEventTemplate(FormEventTemplate model);

}
