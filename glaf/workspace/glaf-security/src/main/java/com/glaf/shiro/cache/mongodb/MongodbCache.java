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

package com.glaf.shiro.cache.mongodb;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import com.glaf.core.config.SystemConfig;
import com.glaf.core.factory.MongodbFactory;

public class MongodbCache<K, V> implements Cache<K, V> {

	private static final Log logger = LogFactory.getLog(MongodbCache.class);

	private volatile String region;

	public MongodbCache(String region) {
		if (region == null) {
			throw new IllegalArgumentException("region argument cannot be null.");
		}
		this.region = region;
	}

	public void clear() throws CacheException {

	}

	public String getSessionPrefix() {
		if (region == null) {
			region = "shiro_session";
		}
		region = region + "_" + SystemConfig.getRegionName("shiro");
		return region;
	}

	@SuppressWarnings("unchecked")
	public V get(K key) throws CacheException {
		try {
			return (V) MongodbFactory.getInstance().getObject(getSessionPrefix(), key);
		} catch (Exception ex) {
			logger.error("Error occured when get data from mongodb", ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Set<K> keys() {
		return (Set<K>) MongodbFactory.getInstance().keys(getSessionPrefix());
	}

	public V put(K key, V value) throws CacheException {
		try {
			byte[] data = SerializationUtils.serialize(value);
			MongodbFactory.getInstance().put(getSessionPrefix(), key, data);
		} catch (Exception ex) {
			logger.error("Error occured when put data into mongodb", ex);
		}
		return null;
	}

	public V remove(K key) throws CacheException {
		try {
			MongodbFactory.getInstance().remove(getSessionPrefix(), key.toString());
		} catch (Exception ex) {
			logger.error("Error occured when remove data from mongodb", ex);
		}
		return null;
	}

	public int size() {
		return 0;
	}

	public Collection<V> values() {
		return null;
	}

}
