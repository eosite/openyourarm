package com.glaf.workflow.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.workflow.core.domain.PageUserTaskHistory;
import com.glaf.workflow.core.query.PageUserTaskHistoryQuery;

@Transactional(readOnly = true)
public interface IPageUserTaskHistoryService {

	@Transactional
	void deleteById(String id);

	@Transactional
	void deleteByIds(List<String> rowIds);

	@Transactional
	void save(PageUserTaskHistory pageUserTask);

	List<PageUserTaskHistory> list(PageUserTaskHistoryQuery query);

	int getPageUserTaskHistoryCountByQueryCriteria(PageUserTaskHistoryQuery query);

	List<PageUserTaskHistory> getPageUserTaskHistorysByQueryCriteria(int start, int pageSize, PageUserTaskHistoryQuery query);

	PageUserTaskHistory getPageUserTaskHistoryById(String id);

}
