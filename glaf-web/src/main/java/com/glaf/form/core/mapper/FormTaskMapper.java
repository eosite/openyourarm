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

@Component("com.glaf.form.core.mapper.FormTaskMapper")
public interface FormTaskMapper {

	void deleteFormTasks(FormTaskQuery query);

	void deleteFormTaskById(Long id);

	FormTask getFormTaskById(Long id);

	int getFormTaskCount(FormTaskQuery query);

	List<FormTask> getFormTasks(FormTaskQuery query);

	void insertFormTask(FormTask model);

	void updateFormTask(FormTask model);

}
