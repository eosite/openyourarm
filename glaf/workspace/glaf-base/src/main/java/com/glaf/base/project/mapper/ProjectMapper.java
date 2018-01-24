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
public interface ProjectMapper {

	void bulkInsertProject(List<Project> list);

	void bulkInsertProject_oracle(List<Project> list);

	void deleteProjectById(Long id);

	Project getProjectById(Long id);

	Project getProjectByCode(String code);

	int getProjectCount(ProjectQuery query);

	List<Project> getProjects(ProjectQuery query);

	void insertProject(Project model);

	void updateProject(Project model);

}
