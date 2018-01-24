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
package com.glaf.core.identity.impl;

import com.glaf.core.identity.Tenant;

public class TenantImpl implements Tenant {

	private static final long serialVersionUID = 1L;

	protected long id;

	protected long databaseId;

	protected int locked;

	protected String name;

	protected String tenantId;

	protected int tenantType;

	public TenantImpl() {

	}

	public long getDatabaseId() {
		return databaseId;
	}

	public long getId() {
		return id;
	}

	public int getLocked() {
		return locked;
	}

	public String getName() {
		return name;
	}

	public String getTenantId() {
		return tenantId;
	}

	public int getTenantType() {
		return tenantType;
	}

	public void setDatabaseId(long databaseId) {
		this.databaseId = databaseId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setTenantType(int tenantType) {
		this.tenantType = tenantType;
	}

}
