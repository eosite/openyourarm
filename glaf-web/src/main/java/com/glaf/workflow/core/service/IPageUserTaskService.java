package com.glaf.workflow.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.workflow.core.domain.PageUserTask;
import com.glaf.workflow.core.query.PageUserTaskQuery;

@Transactional(readOnly = true)
public interface IPageUserTaskService {

	@Transactional
	void deleteById(String id);

	@Transactional
	void deleteByIds(List<String> rowIds);

	@Transactional
	void save(PageUserTask pageUserTask);

	List<PageUserTask> list(PageUserTaskQuery query);

	int getPageUserTaskCountByQueryCriteria(PageUserTaskQuery query);

	List<PageUserTask> getPageUserTasksByQueryCriteria(int start, int pageSize, PageUserTaskQuery query);

	PageUserTask getPageUserTaskById(String id);

}
