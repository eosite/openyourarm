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

package com.glaf.core.config.mongodb;

import java.util.List;

import com.glaf.core.factory.MongodbFactory;

public class MongodbConfig implements com.glaf.core.config.Config {

	protected String regionName = "configdb";

	protected int expireMinutes = 30;

	public MongodbConfig(String regionName, int expireMinutes) {
		this.regionName = regionName;
		this.expireMinutes = expireMinutes;
	}

	public void clear() {
		MongodbFactory.getInstance().clear(regionName);
	}

	public void destroy() {
		MongodbFactory.getInstance().destroy(regionName);
	}

	@SuppressWarnings("rawtypes")
	public void evict(List keys) {
		if (keys != null && !keys.isEmpty()) {
			MongodbFactory.getInstance().evict(regionName, keys);
		}
	}

	public void evict(Object key) {
		MongodbFactory.getInstance().evict(regionName, key);
	}

	public Object get(Object key) {
		return MongodbFactory.getInstance().getObject(regionName, key, expireMinutes);
	}

	public String getString(String key) {
		return (String) this.get(key);
	}

	public List<?> keys() {
		return null;
	}

	public void put(Object key, Object value) {
		MongodbFactory.getInstance().put(regionName, key, value);
	}

	public void put(String key, String value) {
		MongodbFactory.getInstance().put(regionName, key, value);
	}

	public void remove(String key) {
		MongodbFactory.getInstance().evict(regionName, key);
	}

	public void update(Object key, Object value) {
		MongodbFactory.getInstance().put(regionName, key, value);
	}

}
