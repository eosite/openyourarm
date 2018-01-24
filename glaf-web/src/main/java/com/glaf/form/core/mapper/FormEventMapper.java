package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.query.FormEventQuery;


/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormEventMapper")
public interface FormEventMapper {

	void deleteFormEvents(FormEventQuery query);

	void deleteFormEventById(String id);

	FormEvent getFormEventById(String id);

	int getFormEventCount(FormEventQuery query);

	List<FormEvent> getFormEvents(FormEventQuery query);

	void insertFormEvent(FormEvent model);

	void updateFormEvent(FormEvent model);

}
