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

package com.glaf.core.factory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.GlobalConfig;
import com.glaf.core.util.ShutdownHookManager;
import com.glaf.core.util.StringTools;

import com.mongodb.client.MongoDatabase;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class MongodbContainer {
	private static class MongodbSingletonHolder {
		public static MongodbContainer instance = new MongodbContainer();
	}

	protected static final Log logger = LogFactory.getLog(MongodbContainer.class);

	protected static volatile Properties properties = new Properties();

	protected static AtomicBoolean loading = new AtomicBoolean(false);

	/**
	 * 构建锁
	 */
	private static final Lock LOCK = new ReentrantLock();

	protected static volatile MongoClient mongoClient;

	private static final int MAX_RETRIES = 10;

	public static boolean getBoolean(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Boolean.valueOf(value).booleanValue();
		}
		return false;
	}

	public static double getDouble(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Double.valueOf(value).doubleValue();
		}
		return 0;
	}

	public static MongodbContainer getInstance() {
		return MongodbSingletonHolder.instance;
	}

	public static int getInt(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Integer.valueOf(value).intValue();
		}
		return 0;
	}

	public static long getLong(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return Long.valueOf(value).longValue();
		}
		return 0;
	}

	public static Properties getProperties() {
		Properties p = new Properties();
		Enumeration<?> e = properties.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			p.put(key, value);
		}
		return p;
	}

	public static String getString(String key) {
		if (hasObject(key)) {
			String value = properties.getProperty(key);
			return value;
		}
		return "";
	}

	public static boolean hasObject(String key) {
		if (key == null || properties == null) {
			return false;
		}
		String value = properties.getProperty(key);
		if (value != null) {
			return true;
		}
		return false;
	}

	private MongodbContainer() {
		reload();
		init();
	}

	public MongoDatabase getDatabase(String dbname) {
		logger.info("get mongo database " + dbname);
		return getMongoClient().getDatabase(dbname);
	}

	@SuppressWarnings("deprecation")
	public DB getDB(String dbName) {
		return getMongoClient().getDB(dbName);
	}

	private MongoClient getMongoClient() {
		int retries = 0;
		while (true) {
			try {
				boolean lockSuccess = LOCK.tryLock(20, TimeUnit.MILLISECONDS);
				if (lockSuccess && mongoClient == null) {
					this.init();
				}
				if (mongoClient != null) {
					return mongoClient;
				}
			} catch (java.lang.Throwable ex) {
				if (retries++ == MAX_RETRIES) {
					throw new RuntimeException(ex);
				}
				try {
					TimeUnit.MILLISECONDS.sleep(200 + new Random().nextInt(1000));// 活锁
				} catch (InterruptedException e) {
				}
			} finally {
				LOCK.unlock();
			}
		}
	}

	private synchronized void init() {
		if (mongoClient == null) {
			String servers = getString("mongodb_servers");
			if (StringUtils.isEmpty(servers)) {
				servers = "127.0.0.1:27017";
			}
			List<String> list = StringTools.split(servers, ",");
			List<ServerAddress> addrList = new ArrayList<ServerAddress>();
			for (String server : list) {
				String host = server.substring(0, server.indexOf(":"));
				int port = Integer.parseInt(server.substring(server.indexOf(":") + 1, server.length()));
				logger.debug(host + ":" + port);
				try {
					ServerAddress addr = new ServerAddress(host, port);
					addrList.add(addr);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			mongoClient = new MongoClient(addrList);
			Runnable shutdownHook = new MongodbShutdownHook(mongoClient);
			ShutdownHookManager.get().addShutdownHook(shutdownHook, Thread.NORM_PRIORITY);
		}
	}

	private synchronized void reload() {
		if (!loading.get()) {
			try {
				loading.set(true);
				Properties props = GlobalConfig.getProperties("sys_mongodb");
				if (props == null || props.isEmpty()) {
					props = GlobalConfig.getConfigProperties("mongodb.properties");
				}
				if (props != null) {
					Enumeration<?> e = props.keys();
					while (e.hasMoreElements()) {
						String key = (String) e.nextElement();
						String value = props.getProperty(key);
						properties.setProperty(key, value);
						properties.setProperty(key.toLowerCase(), value);
						properties.setProperty(key.toUpperCase(), value);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} finally {
				loading.set(false);
			}
		}
	}

	protected synchronized void reloadAndReset() {
		reload();
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
		init();
	}

}