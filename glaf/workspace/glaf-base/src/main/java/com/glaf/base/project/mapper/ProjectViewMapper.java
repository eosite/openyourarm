package com.glaf.base.project.mapper;

import org.springframework.stereotype.Component;
import com.glaf.base.project.domain.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface ProjectViewMapper {
	
	void deleteProjectViewById(String uid);
	
	void deleteProjectViewByProjectId(long projectId);

	void deleteProjectViewByUserId(String userId);

	void insertProjectView(ProjectView model);

}
