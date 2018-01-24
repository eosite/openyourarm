/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.datamgr.task;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.DatabaseConnectionConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.SysDataLog;
import com.glaf.core.factory.SysLogFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.bean.TaskTableBean;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.handler.ExecutionHandlerFactory;
import com.glaf.datamgr.service.ExecutionLogService;

public class TaskTableAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	protected long databaseId;

	protected long taskTableId;

	protected Map<String, Object> parameter;

	public TaskTableAction(long databaseId, long taskTableId, Map<String, Object> parameter) {
		this.databaseId = databaseId;
		this.taskTableId = taskTableId;
		this.parameter = parameter;
	}

	@Override
	protected void compute() {
		execute();
	}

	public void execute() {
		long start = System.currentTimeMillis();
		ExecutionLog log = new ExecutionLog();
		log.setStartTime(new Date());
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("databaseId", databaseId);
		TaskTableBean bean = new TaskTableBean();
		boolean result = false;
		try {
			ExecutionHandlerFactory.doBefore("task_table_" + taskTableId, context);
			result = bean.execute(databaseId, taskTableId, parameter);
		} catch (Exception ex) {
			result = false;
			log.setExitMessage(ex.getMessage());
		} finally {
			if (result) {
				context.put("result", result);
				ExecutionHandlerFactory.doAfter("task_table_" + taskTableId, context);
			}
		}
		InetAddress addr = null;
		String ipAddr = null;
		try {
			addr = InetAddress.getLocalHost();
			ipAddr = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
		}

		JSONObject json = new JSONObject();
		json.put("databaseId", databaseId);
		json.put("taskTableId", taskTableId);
		json.put("result", result);
		if (result) {
			json.put("message", "执行成功");
		} else {
			json.put("message", "执行失败");
		}

		try {
			DatabaseConnectionConfig cfg = new DatabaseConnectionConfig();
			Database database = cfg.getDatabase(databaseId);
			if (database != null) {
				json.put("title", database.getTitle());
				json.put("section", database.getSection());
				json.put("db", database.getDbname());
			}
		} catch (Exception ex) {
		}
		long ts = System.currentTimeMillis() - start;
		SysDataLog sysLog = new SysDataLog();
		sysLog.setIp(ipAddr);
		sysLog.setActorId("system");
		sysLog.setContent(json.toJSONString());
		sysLog.setTimeMS((int) ts);
		sysLog.setCreateTime(new Date());
		sysLog.setFlag(result ? 1 : -1);
		sysLog.setServiceKey("task_table");
		sysLog.setModuleId("task_table_" + taskTableId);
		sysLog.setBusinessKey("task_table_" + taskTableId);
		SysLogFactory.getInstance().addLog(sysLog);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		log.setBusinessKey(String.valueOf(taskTableId));
		log.setContent(json.toJSONString());
		log.setCreateBy("system");
		log.setEndTime(new Date());
		log.setRunTime(ts);
		log.setRunHour(calendar.get(Calendar.HOUR));
		log.setRunDay(DateUtils.getNowYearMonthDay());
		log.setJobNo("task_table_" + taskTableId + "_" + databaseId + "_" + log.getRunDay());
		log.setStatus(result ? 1 : -1);
		log.setType("task_table");
		try {
			ExecutionLogService executionLogService = ContextFactory.getBean("executionLogService");
			executionLogService.save(log);
		} catch (Exception ex) {
		}
	}

}