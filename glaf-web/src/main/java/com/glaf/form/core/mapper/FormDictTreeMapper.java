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

package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.query.FormDictTreeQuery;

@Component
public interface FormDictTreeMapper {

	void bulkInsertFormDictTree(List<FormDictTree> list);

	void bulkInsertFormDictTree_oracle(List<FormDictTree> list);

	void deleteFormDictTreeById(Long id);

	void deleteFormDictTrees(FormDictTreeQuery query);

	List<FormDictTree> getDictoryFormDictTrees(FormDictTreeQuery query);

	List<FormDictTree> getRelationFormDictTrees(FormDictTreeQuery query);

	FormDictTree getFormDictTreeById(Long id);

	int getFormDictTreeCount(FormDictTreeQuery query);

	List<FormDictTree> getFormDictTrees(FormDictTreeQuery query);

	List<FormDictTree> getFormDictTreeList(FormDictTreeQuery query);

	List<FormDictTree> getFormDictTreeListByUsers(FormDictTreeQuery query);

	void insertFormDictTree(FormDictTree model);

	void updateFormDictTree(FormDictTree model);

}
