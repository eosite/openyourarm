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

@Component("com.glaf.form.core.mapper.FormObjectMapper")
public interface FormObjectMapper {

	void deleteFormObjects(FormObjectQuery query);

	void deleteFormObjectById(String id);

	FormObject getFormObjectById(String id);

	int getFormObjectCount(FormObjectQuery query);

	List<FormObject> getFormObjects(FormObjectQuery query);

	void insertFormObject(FormObject model);

	void updateFormObject(FormObject model);

}
