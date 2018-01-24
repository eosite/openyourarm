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
public interface FormRuleMapper {

	void deleteFormRuleById(String id);
	
	void deleteFormRulesByPageId(String pageId);

	FormRule getFormRuleById(String id);

	int getFormRuleCount(FormRuleQuery query);

	List<FormRule> getFormRules(FormRuleQuery query);

	void insertFormRule(FormRule model);

	void updateFormRule(FormRule model);
	
	void deleteFormRuleByIds(List<String> ruleIds);
	

}
