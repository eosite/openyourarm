package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormWorkFlowRuleMapper")
public interface FormWorkFlowRuleMapper {

	void deleteFormWorkFlowRules(FormWorkFlowRuleQuery query);

	void deleteFormWorkFlowRuleById(Long id);

	FormWorkFlowRule getFormWorkFlowRuleById(Long id);

	int getFormWorkFlowRuleCount(FormWorkFlowRuleQuery query);

	List<FormWorkFlowRule> getFormWorkFlowRules(FormWorkFlowRuleQuery query);

	void insertFormWorkFlowRule(FormWorkFlowRule model);

	void updateFormWorkFlowRule(FormWorkFlowRule model);

	Integer getNextVersionByPageId(@Param("pageId") String pageId);

}
