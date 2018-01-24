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

@Component("com.glaf.form.core.mapper.FormPageCategoryMapper")
public interface FormPageCategoryMapper {

	void deleteFormPageCategorys(FormPageCategoryQuery query);

	void deleteFormPageCategoryById(Integer id);

	FormPageCategory getFormPageCategoryById(Integer id);

	int getFormPageCategoryCount(FormPageCategoryQuery query);

	List<FormPageCategory> getFormPageCategorys(FormPageCategoryQuery query);

	void insertFormPageCategory(FormPageCategory model);

	void updateFormPageCategory(FormPageCategory model);

}
