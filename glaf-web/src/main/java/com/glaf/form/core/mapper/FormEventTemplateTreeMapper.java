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

@Component("com.glaf.form.core.mapper.FormEventTemplateTreeMapper")
public interface FormEventTemplateTreeMapper {

	void deleteFormEventTemplateTrees(FormEventTemplateTreeQuery query);

	void deleteFormEventTemplateTreeById(String id);

	FormEventTemplateTree getFormEventTemplateTreeById(String id);

	int getFormEventTemplateTreeCount(FormEventTemplateTreeQuery query);

	List<FormEventTemplateTree> getFormEventTemplateTrees(FormEventTemplateTreeQuery query);

	void insertFormEventTemplateTree(FormEventTemplateTree model);

	void updateFormEventTemplateTree(FormEventTemplateTree model);

}
