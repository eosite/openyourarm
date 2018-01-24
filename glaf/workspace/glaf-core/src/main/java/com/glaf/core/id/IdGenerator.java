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

package com.glaf.core.id;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IdGenerator {

	@Transactional
	String getNextId();

	@Transactional
	String getNextId(String name);

	@Transactional
	String getNextId(String tablename, String idColumn, String createBy);

	@Transactional
	Long nextId();

	@Transactional
	Long nextId(String name);

	@Transactional
	Long nextId(String tablename, String idColumn);

}