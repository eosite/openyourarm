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

package com.glaf.core.cache.j2cache;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.glaf.core.cache.Cache;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;

public class J2CacheImpl implements Cache {
	protected static final List<String> regions = new CopyOnWriteArrayList<String>();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static CacheChannel cacheChannel = J2Cache.getChannel();

	@Override
	public void clear(String region) {
		try {
			cacheChannel.clear(region);
		} catch (Exception e) {
		}
	}

	@Override
	public java.io.Serializable get(String region, String key) {
		Object object = null;
		try {
			object = cacheChannel.get(region, key);
		} catch (Exception e) {
		}
		if (object != null) {
			logger.debug("get object from j2cache.");
			if (object instanceof CacheObject) {
				CacheObject cacheObject = (CacheObject) object;
				if (cacheObject.getValue() != null) {
					return (Serializable) cacheObject.getValue();
				}
			} else {
				return (java.io.Serializable) object;
			}
		}
		return null;
	}

	@Override
	public void init(Properties props) {

	}

	@Override
	public void put(String region, String key, java.io.Serializable value) {
		if (value != null) {
			if (value instanceof java.io.Serializable) {
				try {
					java.io.Serializable s = (java.io.Serializable) value;
					cacheChannel.set(region, key, s);
					if (!regions.contains(region)) {
						regions.add(region);
					}
					logger.debug("put object into j2cache.");
				} catch (Exception e) {
				}
			} else {
				throw new RuntimeException(" value can't serializable.");
			}
		}
	}

	@Override
	public void remove(String region, String key) {
		try {
			cacheChannel.evict(region, key);
		} catch (Exception e) {
		}
	}

	@Override
	public void shutdown() {
		for (String region : regions) {
			try {
				cacheChannel.clear(region);
			} catch (Exception e) {
			}
		}
		cacheChannel.close();
	}

}
