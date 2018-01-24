package com.glaf.base.project.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.base.project.domain.*;
import com.glaf.base.project.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface ProjectDatabaseMapper {

	void deleteProjectDatabaseById(Long id);

	void deleteProjectDatabasesByDatabaseId(Long databaseId);

	void deleteProjectDatabasesByProjectId(Long projectId);

	ProjectDatabase getProjectDatabaseById(Long id);

	int getProjectDatabaseCount(ProjectDatabaseQuery query);

	List<ProjectDatabase> getProjectDatabases(ProjectDatabaseQuery query);

	List<ProjectDatabase> getProjectDatabasesByProjectId(Long projectId);

	void insertProjectDatabase(ProjectDatabase model);

}
