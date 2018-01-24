package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormPageEventProcDefMapper")
public interface FormPageEventProcDefMapper {

	void deleteFormPageEventProcDefs(FormPageEventProcDefQuery query);

	void deleteFormPageEventProcDefById(String id);
	
	void updateFormPageEventProcDefById(@Param("procDefKey")String PROCDEF_KEY_,
			@Param("procDefId")String PROCDEF_ID_, @Param("procDeployId")String PROCDEPLOY_ID_, @Param("id")String EVENTPRO_ID);
	
	FormPageEventProcDef getFormPageEventProcDefById(String id);

	int getFormPageEventProcDefCount(FormPageEventProcDefQuery query);

	List<FormPageEventProcDef> getFormPageEventProcDefs(FormPageEventProcDefQuery query);

	void insertFormPageEventProcDef(FormPageEventProcDef model);

	void updateFormPageEventProcDef(FormPageEventProcDef model);
	
	void updateDeployStatus(String eventProcId);
}
