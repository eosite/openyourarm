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

package com.glaf.core.cache.ehcache;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.glaf.core.cache.Cache;
import com.glaf.core.cache.CacheException;
import com.glaf.core.context.ContextFactory;

public class EHCacheImpl implements Cache {
	protected static final Log logger = LogFactory.getLog(EHCacheImpl.class);

	protected static ConcurrentMap<String, Ehcache> cacheConcurrentMap = new ConcurrentHashMap<String, Ehcache>();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	private CacheManager cacheManager;

	private Ehcache cache;

	private Properties properties;

	private int timeToLive = 1800;// seconds

	public EHCacheImpl() {

	}

	public void clear() {
		try {
			getCache().removeAll();
		} catch (Exception ex) {
		}

		Iterator<String> iterator = cacheConcurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String region = iterator.next();
			try {
				getCache(region).removeAll();
			} catch (Exception ex) {
			}
		}
	}

	public void clear(String region) {
		try {
			getCache(region).removeAll();
		} catch (ClassCastException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public Object get(String key) {
		try {
			if (key == null) {
				return null;
			} else {
				Element element = getCache().get(key);
				if (element == null) {
					return null;
				} else {
					return element.getObjectValue();
				}
			}
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public java.io.Serializable get(String region, String key) {
		try {
			if (key == null) {
				return null;
			} else {
				Element element = getCache(region).get(key);
				if (element == null) {
					return null;
				} else {
					logger.debug("get data from ehcahe.");
					return (java.io.Serializable) element.getObjectValue();
				}
			}
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public Ehcache getCache() {
		if (cache == null) {
			cache = ContextFactory.getBean("ehCache");
		}
		return cache;
	}

	public Ehcache getCache(String region) {
		Ehcache ehCache = cacheConcurrentMap.get(region);
		if (ehCache == null) {
			if (cacheManager == null) {
				cacheManager = ContextFactory.getBean("ehCacheManager");
			}
			ehCache = cacheManager.getCache(region);
			if (ehCache == null) {
				if (!running.get()) {
					try {
						running.set(true);
						if (ehCache == null) {
							ehCache = cacheManager.addCacheIfAbsent(region);
							cacheConcurrentMap.put(region, ehCache);
						}
					} finally {
						running.set(false);
					}
				}
			}
		}
		return ehCache;
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public Properties getProperties() {
		return properties;
	}

	private int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getRegionName() {
		return getCache().getName();
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	@Override
	public void init(Properties props) {
		timeToLive = getProperty(props, "timeToLive", 1800);
	}

	public void put(String key, java.io.Serializable value) {
		try {
			this.remove(key);

			Element element = new Element(key, value);
			if (timeToLive > 0) {
				element.setTimeToLive(timeToLive);
			}

			getCache().put(element);
		} catch (IllegalArgumentException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void put(String region, String key, java.io.Serializable value) {
		try {
			this.remove(region, key);

			Element element = new Element(key, value);
			if (timeToLive > 0) {
				element.setTimeToLive(timeToLive);
			}
			getCache(region).put(element);
			logger.debug("put data into ehcahe.");
		} catch (IllegalArgumentException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void remove(String key) {
		try {
			getCache().remove(key);
		} catch (ClassCastException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void remove(String region, String key) {
		try {
			getCache(region).remove(key);
		} catch (ClassCastException e) {
			throw new CacheException(e);
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public void shutdown() {
		try {
			getCache().getCacheManager().shutdown();
		} catch (IllegalStateException e) {
			throw new CacheException(e);
		} catch (net.sf.ehcache.CacheException e) {
			throw new CacheException(e);
		}

		Iterator<String> iterator = cacheConcurrentMap.keySet().iterator();
		while (iterator.hasNext()) {
			String region = iterator.next();
			try {
				getCache(region).getCacheManager().shutdown();
			} catch (Exception ex) {
			}
		}
	}

	public String toString() {
		return "EHCache(" + getRegionName() + ')';
	}

}
