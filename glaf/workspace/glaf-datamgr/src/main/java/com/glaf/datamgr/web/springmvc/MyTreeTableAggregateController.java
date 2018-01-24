package com.glaf.datamgr.web.springmvc;

import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.core.domain.Database;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.domain.TreeTableAggregate;
import com.glaf.datamgr.service.ExecutionLogService;
import com.glaf.datamgr.service.TableExecutionService;
import com.glaf.datamgr.service.TreeTableAggregateService;
import com.glaf.datamgr.task.TreeTableAggregateTask;
import com.glaf.datamgr.util.ExceptionUtils;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/my/treeTableAggregate")
@RequestMapping("/my/treeTableAggregate")
public class MyTreeTableAggregateController {
	protected static final Log logger = LogFactory.getLog(MyTreeTableAggregateController.class);

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected ExecutionLogService executionLogService;

	protected TableExecutionService tableExecutionService;

	protected TreeTableAggregateService treeTableAggregateService;

	public MyTreeTableAggregateController() {

	}

	@ResponseBody
	@RequestMapping("/execute")
	public byte[] execute(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long treeTableAggregateId = RequestUtils.getLong(request, "treeTableAggregateId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		StringBuilder buffer = new StringBuilder();
		TreeTableAggregate treeTableAggregate = treeTableAggregateService.getTreeTableAggregate(treeTableAggregateId);
		if (treeTableAggregate != null && StringUtils.isNotEmpty(treeTableAggregate.getDatabaseIds())) {
			if (StringUtils.isNotEmpty(treeTableAggregate.getDatabaseIds())) {
				boolean dyncCondition = false;
				if (StringUtils.equals(treeTableAggregate.getDynamicCondition(), "Y")) {
					dyncCondition = true;

					for (int i = 1; i <= 10; i++) {
						String pname = request.getParameter("param_name_" + i);
						String ptype = request.getParameter("param_type_" + i);
						String pvalue = request.getParameter("param_value_" + i);
						if (StringUtils.isNotEmpty(pname) && StringUtils.isNotEmpty(pvalue)) {
							switch (ptype) {
							case "Integer":
								params.put(pname, Integer.parseInt(pvalue));
								break;
							case "Long":
								params.put(pname, Long.parseLong(pvalue));
								break;
							case "Double":
								params.put(pname, Double.parseDouble(pvalue));
								break;
							case "Date":
								params.put(pname, DateUtils.toDate(pvalue));
								break;
							default:
								params.put(pname, pvalue);
								break;
							}
						}
					}
				}

				treeTableAggregate.setSyncTime(new Date());
				StringTokenizer token = new StringTokenizer(treeTableAggregate.getDatabaseIds(), ",");
				while (token.hasMoreTokens()) {
					String x = token.nextToken();
					if (StringUtils.isNotEmpty(x) && StringUtils.isNumeric(x)) {
						jobNo = "treetable_aggregate_" + treeTableAggregate.getId() + "_" + x + "_" + runDay;
						try {
							database = databaseService.getDatabaseById(Long.parseLong(x));
							if (database != null) {
								TreeTableAggregateTask task = new TreeTableAggregateTask(database.getId(),
										treeTableAggregate.getId(), true, jobNo, dyncCondition ? params : null);
								if (task.execute()) {
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行成功。\n");
								} else {
									errorCount++;
									buffer.append(database.getTitle()).append("[").append(database.getMapping())
											.append("]执行失败。\n");
									StringBuffer sb = ExceptionUtils
											.getMsgBuffer("treetable_aggregate_" + treeTableAggregate.getId());
									buffer.append(sb.toString());
								}
							}
						} catch (Exception ex) {
							errorCount++;
						} finally {
							ExceptionUtils.clear("treetable_aggregate_" + treeTableAggregate.getId());
						}
					}
				}

				if (errorCount == 0) {
					treeTableAggregate.setSyncStatus(1);
				} else {
					treeTableAggregate.setSyncStatus(-1);
				}
				treeTableAggregateService.updateTreeTableAggregateStatus(treeTableAggregate);
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@ResponseBody
	@RequestMapping("/executeSpec")
	public byte[] executeSpec(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		long databaseId = RequestUtils.getLong(request, "databaseId");
		long treeTableAggregateId = RequestUtils.getLong(request, "treeTableAggregateId");
		int errorCount = 0;
		String jobNo = null;
		Database database = null;
		StringBuilder buffer = new StringBuilder();
		String runDay = DateUtils.getNowYearMonthDayHHmmss();
		TreeTableAggregate treeTableAggregate = treeTableAggregateService.getTreeTableAggregate(treeTableAggregateId);
		if (treeTableAggregate != null && databaseId > 0) {
			boolean dyncCondition = false;
			if (StringUtils.equals(treeTableAggregate.getDynamicCondition(), "Y")) {
				dyncCondition = true;
			}
			try {
				database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					jobNo = "treetable_aggregate_" + treeTableAggregate.getId() + "_" + databaseId + "_" + runDay;
					TreeTableAggregateTask task = new TreeTableAggregateTask(database.getId(),
							treeTableAggregate.getId(), false, jobNo, dyncCondition ? params : null);
					if (task.execute()) {
						buffer.append(database.getTitle()).append("[").append(database.getMapping()).append("]执行成功。\n");
					} else {
						errorCount++;
						buffer.append(database.getTitle()).append("[").append(database.getMapping()).append("]执行失败。\n");
						StringBuffer sb = ExceptionUtils
								.getMsgBuffer("treetable_aggregate_" + treeTableAggregate.getId());
						buffer.append(sb.toString());
					}
				}
			} catch (Exception ex) {
				errorCount++;
				logger.error(ex);
			} finally {
				ExceptionUtils.clear("treetable_aggregate_" + treeTableAggregate.getId());
			}
		}

		return ResponseUtils.responseJsonResult(errorCount == 0 ? true : false, buffer.toString());
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setExecutionLogService(ExecutionLogService executionLogService) {
		this.executionLogService = executionLogService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.tableExecutionService")
	public void setTableExecutionService(TableExecutionService tableExecutionService) {
		this.tableExecutionService = tableExecutionService;
	}

	@javax.annotation.Resource
	public void setTreeTableAggregateService(TreeTableAggregateService treeTableAggregateService) {
		this.treeTableAggregateService = treeTableAggregateService;
	}

}
