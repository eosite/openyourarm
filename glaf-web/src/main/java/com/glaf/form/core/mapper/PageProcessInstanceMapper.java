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
public interface PageProcessInstanceMapper {

	void bulkInsertPageProcessInstance(List<PageProcessInstance> list);

	void bulkInsertPageProcessInstance_oracle(List<PageProcessInstance> list);

	void deletePageProcessInstances(PageProcessInstanceQuery query);

	void deletePageProcessInstanceById(String id);

	PageProcessInstance getPageProcessInstanceById(String id);

	int getPageProcessInstanceCount(PageProcessInstanceQuery query);

	List<PageProcessInstance> getPageProcessInstances(PageProcessInstanceQuery query);

	void insertPageProcessInstance(PageProcessInstance model);

	void updatePageProcessInstance(PageProcessInstance model);

}
