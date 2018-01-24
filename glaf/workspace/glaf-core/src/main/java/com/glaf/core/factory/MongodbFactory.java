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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.types.Binary;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import com.glaf.core.util.ByteBlockChopper;
import com.glaf.core.util.FileUtils;

public class MongodbFactory {
	private static class MongodbHolder {
		public static MongodbFactory instance = new MongodbFactory();
	}

	protected static final Log logger = LogFactory.getLog(MongodbFactory.class);

	public static MongodbFactory getInstance() {
		return MongodbHolder.instance;
	}

	protected final ConcurrentMap<String, MongoDatabase> concurrentMap = new ConcurrentHashMap<String, MongoDatabase>();

	protected int expireMinutes = Integer.MAX_VALUE;

	private void addBlob(MongoCollection<Document> dbCollection, String key, byte[] bytes) {
		Document document = new Document();
		document.put("key", key);
		document.put("bytes", bytes);
		document.put("time", System.currentTimeMillis());
		dbCollection.insertOne(document);
	}

	public void clear(String regionName) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		dbCollection.drop();
	}

	public void destroy(String regionName) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		dbCollection.drop();
		db.drop();
	}

	@SuppressWarnings("rawtypes")
	public void evict(String regionName, List keys) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		if (keys != null && !keys.isEmpty()) {
			BasicDBObject filter = new BasicDBObject();
			for (Object key : keys) {
				filter.put("key", key);
				dbCollection.deleteOne(filter);
			}
		}
	}

	public void evict(String regionName, Object key) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		BasicDBObject filter = new BasicDBObject();
		filter.put("key", key);
		dbCollection.deleteOne(filter);
	}

	public Object get(String regionName, Object key) {
		return this.getObject(regionName, key, expireMinutes);
	}

	public Object getObject(String regionName, Object key) {
		return this.getObject(regionName, key, expireMinutes);
	}

	public Object getObject(String regionName, Object key, int expireMinutes) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		BasicDBObject filter = new BasicDBObject();
		filter.put("key", key);

		MongoCursor<Document> cur = null;
		MongoCursor<Document> cur2 = null;
		long now = System.currentTimeMillis();
		Object value = null;
		try {
			cur = dbCollection.find(filter).iterator();
			while (cur.hasNext()) {
				Document doc = cur.next();
				value = doc.get("value");
				if (value == null) {
					value = doc.get("bytes");
				}
				if (value == null) {
					Integer blocks = doc.getInteger("blocks");
					if (blocks != null) {
						BasicDBObject filter2 = new BasicDBObject();

						BasicDBList condList = new BasicDBList();
						for (int i = 0; i < blocks; i++) {
							condList.add(key.toString() + "_" + i);
						}

						// DBObject query = new
						// QueryBuilder().put("key").in(condList).get();
						filter2.put("key", new BasicDBObject("$in", condList));
						cur2 = dbCollection.find(filter2).iterator();
						List<byte[]> list = new ArrayList<byte[]>();
						while (cur2.hasNext()) {
							Document doc2 = cur2.next();
							byte[] bytes = null;
							Object object = doc2.get("bytes");
							if (object instanceof byte[]) {
								bytes = (byte[]) object;
							} else if (object instanceof Binary) {
								Binary binary = (Binary) object;
								bytes = binary.getData();
							}
							if (bytes != null) {
								list.add(bytes);
							}
						}
						if (!list.isEmpty()) {
							value = ByteBlockChopper.glueChopsBackTogether(list, FileUtils.MB_SIZE * 8);
						}
					}
				}
				long saveTime = doc.getLong("time");
				if ((now - saveTime) > (long) (expireMinutes * 60000)) {
					/**
					 * 如果已经过期，就删除对象
					 */
					// dbCollection.deleteOne(doc);
					// logger.debug("remove expire.");
				}
			}
		} finally {
			if (cur != null) {
				try {
					cur.close();
				} catch (Exception ex) {
				}
			}
			if (cur2 != null) {
				try {
					cur2.close();
				} catch (Exception ex) {
				}
			}
		}
		return value;
	}

	public Object getBytes(String regionName, Object key) {
		Object object = this.getObject(regionName, key, expireMinutes);
		byte[] bytes = null;
		if (object instanceof byte[]) {
			bytes = (byte[]) object;
		} else if (object instanceof Binary) {
			Binary binary = (Binary) object;
			bytes = binary.getData();
		}
		return bytes;
	}

	public MongoDatabase getMongoDatabase(String regionName) {
		MongoDatabase db = concurrentMap.get(regionName);
		if (db == null) {
			db = MongodbContainer.getInstance().getDatabase(regionName);
			if (db != null) {
				concurrentMap.put(regionName, db);
			}
		}
		return db;
	}

	protected int getProperty(Properties props, String key, int defaultValue) {
		try {
			return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	protected String getProperty(Properties props, String key, String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	public String getString(String regionName, String key) {
		return (String) this.getObject(regionName, key);
	}

	public Set<?> keys(String regionName) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		FindIterable<Document> iterable = dbCollection.find();
		Set<Object> keys = new HashSet<Object>();
		MongoCursor<Document> cursor = null;
		try {
			cursor = iterable.iterator();
			while (cursor.hasNext()) {
				Document doc = (Document) cursor.next();
				keys.add(doc.get("key"));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return keys;
	}

	public void put(String regionName, Object key, Object value) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		if (value instanceof byte[]) {
			byte[] bytes = (byte[]) value;
			if (bytes.length < FileUtils.MB_SIZE * 8) {
				this.addBlob(dbCollection, key.toString(), bytes);
			} else {
				List<byte[]> list = ByteBlockChopper.chopItUp(bytes, FileUtils.MB_SIZE * 8);
				int index = 0;
				for (byte[] item : list) {
					String xkey = key.toString() + "_" + index++;
					this.addBlob(dbCollection, xkey, item);
					logger.debug("save block" + index);
				}
				Document document = new Document();
				document.put("key", key);
				document.put("blocks", index);
				document.put("time", System.currentTimeMillis());
				dbCollection.insertOne(document);
			}
		} else if (value instanceof InputStream) {
			byte[] bytes = FileUtils.getBytes((InputStream) value);
			if (bytes.length < FileUtils.MB_SIZE * 8) {
				this.addBlob(dbCollection, key.toString(), bytes);
			} else {
				List<byte[]> list = ByteBlockChopper.chopItUp(bytes, FileUtils.MB_SIZE * 8);
				int index = 0;
				for (byte[] item : list) {
					String xkey = key.toString() + "_" + index++;
					this.addBlob(dbCollection, xkey, item);
					logger.debug("save block" + index);
				}
				Document document = new Document();
				document.put("key", key);
				document.put("blocks", index);
				document.put("time", System.currentTimeMillis());
				dbCollection.insertOne(document);
			}
		} else {
			Document document = new Document();
			document.put("key", key);
			document.put("value", value);
			document.put("time", System.currentTimeMillis());
			dbCollection.insertOne(document);
		}
	}

	public void put(String regionName, String key, String value) {
		MongoDatabase db = this.getMongoDatabase(regionName);
		MongoCollection<Document> dbCollection = db.getCollection("collection");
		Document document = new Document();
		document.put("key", key);
		document.put("value", value);
		document.put("time", System.currentTimeMillis());
		dbCollection.insertOne(document);
	}

	public void remove(String regionName, String key) {
		this.evict(regionName, key);
	}

}
