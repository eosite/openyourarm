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

@Component("com.glaf.form.core.mapper.FormObjectInsMapper")
public interface FormObjectInsMapper {

	void deleteFormObjectInss(FormObjectInsQuery query);

	void deleteFormObjectInsById(String id);

	FormObjectIns getFormObjectInsById(String id);

	int getFormObjectInsCount(FormObjectInsQuery query);

	List<FormObjectIns> getFormObjectInss(FormObjectInsQuery query);

	void insertFormObjectIns(FormObjectIns model);

	void updateFormObjectIns(FormObjectIns model);

}
