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

@Component("com.glaf.form.core.mapper.FormObjectTreeMapper")
public interface FormObjectTreeMapper {

	void deleteFormObjectTrees(FormObjectTreeQuery query);

	void deleteFormObjectTreeById(String id);

	FormObjectTree getFormObjectTreeById(String id);

	int getFormObjectTreeCount(FormObjectTreeQuery query);

	List<FormObjectTree> getFormObjectTrees(FormObjectTreeQuery query);

	void insertFormObjectTree(FormObjectTree model);

	void updateFormObjectTree(FormObjectTree model);

}
