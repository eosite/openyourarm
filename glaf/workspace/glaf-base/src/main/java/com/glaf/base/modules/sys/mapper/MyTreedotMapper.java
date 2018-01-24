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

package com.glaf.base.modules.sys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaf.base.modules.sys.model.ObjectEntity;
import com.glaf.base.modules.sys.model.Treedot;
import com.glaf.base.modules.sys.query.TreedotQuery;

@Component("com.glaf.base.modules.sys.mapper.TreedotMapper")
public interface MyTreedotMapper {

	void deleteTreedotById(String id);

	void deleteTreedotByIndexId(int indexId);

	void deleteTreedots(TreedotQuery query);

	Treedot getTreedotById(String id);

	Treedot getTreedotByIndexId(int indexId);

	List<ObjectEntity> getTreedotIdsByIndexId(int indexId);

	int getTreedotCount(Map<String, Object> parameter);

	int getTreedotCountByQueryCriteria(TreedotQuery query);

	List<Treedot> getTreedots(Map<String, Object> parameter);

	List<Treedot> getTreedotsByIdLike(Map<String, Object> parameter);

	List<Treedot> getTreedotsByQueryCriteria(TreedotQuery query);

	void insertTreedot(Treedot model);

	void updateTreedot(Treedot model);
	
	List<Treedot> getAllChildrenTreedotByIndexId(int indexId);

}
