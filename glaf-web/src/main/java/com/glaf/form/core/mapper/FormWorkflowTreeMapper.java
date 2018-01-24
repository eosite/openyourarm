package com.glaf.form.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormWorkflowTree;
import com.glaf.form.core.query.FormWorkflowTreeQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormWorkflowTreeMapper")
public interface FormWorkflowTreeMapper {

	void deleteFormWorkflowTrees(FormWorkflowTreeQuery query);

	void deleteFormWorkflowTreeById(Long id);

	FormWorkflowTree getFormWorkflowTreeById(Long id);

	int getFormWorkflowTreeCount(FormWorkflowTreeQuery query);

	List<FormWorkflowTree> getFormWorkflowTrees(FormWorkflowTreeQuery query);

	void insertFormWorkflowTree(FormWorkflowTree model);

	void updateFormWorkflowTree(FormWorkflowTree model);

	void deleteFormWorkflowTreesByPdefId(@Param("defId") String defId);

}
