package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface FormRuleAuditMapper {

	FormRuleAudit getFormRuleAuditById(String id);

	int getFormRuleAuditCount(FormRuleAuditQuery query);

	List<FormRuleAudit> getFormRuleAudits(FormRuleAuditQuery query);

	void insertFormRuleAudit(FormRuleAudit model);

}
