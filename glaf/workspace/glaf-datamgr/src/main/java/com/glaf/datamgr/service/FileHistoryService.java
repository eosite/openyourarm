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

package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface FileHistoryService {

	/**
	 * 获取更新文件的备份包
	 * 
	 * @param fileId
	 * @return
	 */
	FileHistory getBackupFileHistory(String fileId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FileHistory> getFileHistoriesByQueryCriteria(int start, int pageSize, FileHistoryQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FileHistory getFileHistory(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFileHistoryCountByQueryCriteria(FileHistoryQuery query);

	/**
	 * 获取更新文件的实际更新包
	 * 
	 * @param fileId
	 * @return
	 */
	FileHistory getUpdatePkgFileHistory(String fileId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FileHistory> list(FileHistoryQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FileHistory> listLastest(FileHistoryQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FileHistory fileHistory);

	@Transactional
	void saveUpdatePackage(FileHistory fileHistory);

	@Transactional
	void update(FileHistory fileHistory);

}
