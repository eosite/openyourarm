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

package com.glaf.scheduler.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.scheduler.domain.*;
import com.glaf.scheduler.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.scheduler.mapper.ExSchedulerLogMapper")
public interface ExSchedulerLogMapper {

	void bulkInsertExSchedulerLog(List<ExSchedulerLog> list);

	void bulkInsertExSchedulerLog_oracle(List<ExSchedulerLog> list);

	void deleteExSchedulerLogById(String id);

	void deleteExSchedulerLogByTaskId(String taskId);

	void deleteExSchedulerLogs(ExSchedulerLogQuery query);

	ExSchedulerLog getExSchedulerLogById(String id);

	int getExSchedulerLogCount(ExSchedulerLogQuery query);

	List<ExSchedulerLog> getExSchedulerLogs(ExSchedulerLogQuery query);

	void insertExSchedulerLog(ExSchedulerLog model);

	void updateExSchedulerLog(ExSchedulerLog model);

}
