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

package com.glaf.scheduler.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.Scheduler;
import com.glaf.scheduler.query.ExSchedulerQuery;

@Transactional(readOnly = true)
public interface ISchedulerService {

	/**
	 * 删除调度任务
	 * 
	 * @param scheduler
	 */
	@Transactional
	void deleteScheduler(String taskId);

	/**
	 * 获取全部调度任务
	 * 
	 * @return
	 */
	List<Scheduler> getAllSchedulers();

	/**
	 * 根据任务编号获取调度任务
	 * 
	 * @param taskId
	 * @return
	 */
	Scheduler getSchedulerByTaskId(String taskId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getSchedulerCountByQueryCriteria(ExSchedulerQuery query);

	/**
	 * 根据任务类型获取调度任务
	 * 
	 * @param taskType
	 * @return
	 */
	List<Scheduler> getSchedulers(String taskType);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Scheduler> getSchedulersByQueryCriteria(int start, int pageSize,
			ExSchedulerQuery query);

	/**
	 * 获取用户自行定义的调度任务
	 * 
	 * @param createBy
	 * @return
	 */
	List<Scheduler> getUserSchedulers(String createBy);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Scheduler> list(ExSchedulerQuery query);

	/**
	 * 锁定调度任务
	 * 
	 * @param taskId
	 * @param locked
	 */
	@Transactional
	void locked(String taskId, int locked);

	/**
	 * 保存调度任务
	 * 
	 * @param model
	 */
	@Transactional
	void save(Scheduler model);

	/**
	 * 修改调度任务
	 * 
	 * @param model
	 */
	@Transactional
	void update(Scheduler model);
}