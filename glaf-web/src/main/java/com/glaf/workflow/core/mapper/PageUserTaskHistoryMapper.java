package com.glaf.workflow.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.workflow.core.domain.PageUserTaskHistory;
import com.glaf.workflow.core.query.PageUserTaskHistoryQuery;

@Component
public interface PageUserTaskHistoryMapper {
	void insertPageUserTaskHistory(PageUserTaskHistory pageUserTask);

	void updatePageUserTaskHistory(PageUserTaskHistory pageUserTask);

	void deletePageUserTaskHistoryById(String id);

	void deletePageUserTaskHistorys(PageUserTaskHistoryQuery query);

	PageUserTaskHistory getPageUserTaskHistoryById(String id);

	List<PageUserTaskHistory> getPageUserTaskHistorys(PageUserTaskHistoryQuery query);
	
	int getPageUserTaskHistoryCount(PageUserTaskHistoryQuery query);
}
