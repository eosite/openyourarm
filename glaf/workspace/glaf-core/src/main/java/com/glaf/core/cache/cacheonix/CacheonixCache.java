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

package com.glaf.core.cache.cacheonix;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.cacheonix.Cacheonix;
import org.cacheonix.ShutdownMode;
import org.cacheonix.cache.Cache;

import com.glaf.core.util.SerializationUtils;

public class CacheonixCache implements com.glaf.core.cache.Cache {
	protected static final Log logger = LogFactory.getLog(CacheonixCache.class);

	protected static ConcurrentMap<String, Cache<String, byte[]>> cacheConcurrentMap = new ConcurrentHashMap<String, Cache<String, byte[]>>();

	protected static Cacheonix cacheonix = Cacheonix.getInstance();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected Cache<String, byte[]> cache;

	protected int cacheSize = 50000;

	protected int expireMinutes = 10;

	public CacheonixCache() {

	}

	public void clear() {
		getCache().clear();

		Iterator<String> iterator = cacheConcurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String region = iterator.next();
			getCache(region).clear();
		}
		cacheConcurrentMap.clear();
	}

	public void clear(String region) {
		getCache(region).clear();
	}

	public Object get(String key) {
		byte[] bytes = getCache().get(key);
		if (bytes != null) {
			logger.debug("get cache data from cacheonix.");
			return SerializationUtils.unserialize(bytes);
		}
		return null;
	}

	public java.io.Serializable get(String region, String key) {
		byte[] bytes = getCache(region).get(key);
		if (bytes != null) {
			logger.debug("get cache data from cacheonix.");
			return (java.io.Serializable) SerializationUtils.unserialize(bytes);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Cache<String, byte[]> getCache() {
		if (cache == null) {
			cache = cacheonix.getCache("default");
			if (cache == null) {
				if (!running.get()) {
					try {
						running.set(true);
						if (cache == null) {
							cache = cacheonix.createCache("default");
						}
					} finally {
						running.set(false);
					}
				}
			}
		}
		return cache;
	}

	@SuppressWarnings("unchecked")
	public Cache<String, byte[]> getCache(String region) {
		Cache<String, byte[]> regionCache = cacheConcurrentMap.get(region);
		if (regionCache == null) {
			regionCache = cacheonix.getCache(region);
			if (regionCache == null) {
				if (!running.get()) {
					try {
						running.set(true);
						if (regionCache == null) {
							regionCache = cacheonix.createCache(region);
						}
					} finally {
						running.set(false);
					}
				}
			}
		}
		return regionCache;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public int getExpireMinutes() {
		return expireMinutes;
	}

	private int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	@Override
	public void init(Properties props) {
		cacheSize = getProperty(props, "cacheSize", 50000);
		expireMinutes = getProperty(props, "expireMinutes", 30);
	}

	public void put(String key, java.io.Serializable value) {
		Cache<String, byte[]> cache = this.getCache();
		cache.remove(key);
		if (value != null) {
			byte[] data = SerializationUtils.serialize(value);
			cache.put(key, data, expireMinutes, TimeUnit.MINUTES);
			logger.debug("put data into cacheonix.");
		}
	}

	public void put(String region, String key, java.io.Serializable value) {
		Cache<String, byte[]> cache = this.getCache(region);
		cache.remove(key);
		if (value != null) {
			byte[] data = SerializationUtils.serialize(value);
			cache.put(key, data, expireMinutes, TimeUnit.MINUTES);
			logger.debug("put data into cacheonix.");
		}
	}

	public void remove(String key) {
		getCache().remove(key);
	}

	public void remove(String region, String key) {
		getCache(region).remove(key);
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public void setExpireMinutes(int expireMinutes) {
		this.expireMinutes = expireMinutes;
	}

	public void shutdown() {
		getCache().clear();
		Iterator<String> iterator = cacheConcurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String region = iterator.next();
			getCache(region).clear();
		}
		cacheConcurrentMap.clear();
		cacheonix.shutdown(ShutdownMode.GRACEFUL_SHUTDOWN, true);
	}
}
