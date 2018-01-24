package com.glaf.workflow.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.workflow.core.domain.PageUserTask;
import com.glaf.workflow.core.query.PageUserTaskQuery;

@Component
public interface PageUserTaskMapper {
	void insertPageUserTask(PageUserTask pageUserTask);

	void updatePageUserTask(PageUserTask pageUserTask);

	void deletePageUserTaskById(String id);

	void deletePageUserTasks(PageUserTaskQuery query);

	PageUserTask getPageUserTaskById(String id);

	List<PageUserTask> getPageUserTasks(PageUserTaskQuery query);
	
	int getPageUserTaskCount(PageUserTaskQuery query);
}
