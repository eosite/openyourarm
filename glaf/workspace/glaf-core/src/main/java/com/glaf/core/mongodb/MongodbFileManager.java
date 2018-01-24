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

package com.glaf.core.mongodb;

import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

import com.glaf.core.base.DataFile;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.factory.MongodbContainer;
import com.glaf.core.util.ByteBlockChopper;
import com.glaf.core.util.hash.JenkinsHash;

public class MongodbFileManager {
	protected static Configuration conf = BaseConfiguration.create();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public MongodbFileManager() {

	}

	/**
	 * 删除指定id的数据
	 * 
	 * @param id
	 */
	public void deleteDataFile(String id) {
		if (id == null || id.length() < 8) {
			return;
		}
		logger.debug("delete row:" + id);
		int hash = JenkinsHash.getInstance().hash(id.getBytes());
		hash = Math.abs(hash % Constants.DATABASE_PARTITION_SIZE);
		DataFile model = new BlobItemEntity();
		model.setId(id);
		MongoDatabase db = MongodbContainer.getInstance()
				.getDatabase(Constants.DATABASE_PREFIX + SystemConfig.getSysCode());
		String collectionName = Constants.TABLE_PREFIX + hash;
		logger.debug("collectionName:" + collectionName);
		MongoCollection<Document> coll = db.getCollection(collectionName);
		boolean embedded = false;
		if (coll != null) {
			Document document = coll.find(eq(Constants.QUALIFIER_ID, id)).first();
			logger.debug("document:" + document);
			// BasicDBObject filter = new BasicDBObject();
			// filter.put(Constants.QUALIFIER_ID, id);
			// logger.debug("filter:" + filter.toString());
			if (document != null && document.containsKey("embedded")) {
				embedded = document.getBoolean("embedded", false);
			}
			if (document != null) {
				coll.deleteOne(document);
			}
		}

		if (embedded) {
			collectionName = Constants.LOB_TABLE_PREFIX + hash;
			logger.debug("collectionName:" + collectionName);
			MongoCollection<Document> coll2 = db.getCollection(collectionName);
			if (coll2 != null) {
				BasicDBObject filter = new BasicDBObject();
				filter.put(Constants.QUALIFIER_ID, id);
				logger.debug("filter:" + filter.toString());
				coll2.deleteMany(filter);
			}
		} else {
			collectionName = Constants.LOB_TABLE_PREFIX + hash;
			logger.debug("->collectionName:" + collectionName);
			MongoCollection<Document> coll2 = db.getCollection(collectionName);
			if (coll2 != null) {
				BasicDBObject filter = new BasicDBObject();
				filter.put(Constants.QUALIFIER_REF_ID, id);
				logger.debug("filter:" + filter.toString());
				coll2.deleteMany(filter);
			}
		}
	}

	/**
	 * 根据编号获取附件，不包含字节流
	 * 
	 * @param id
	 * @return
	 */
	public DataFile getDataFile(String id) {
		if (id == null || id.length() < 8) {
			return null;
		}
		logger.debug("get row:" + id);
		int hash = JenkinsHash.getInstance().hash(id.getBytes());
		hash = Math.abs(hash % Constants.DATABASE_PARTITION_SIZE);
		MongoDatabase db = MongodbContainer.getInstance()
				.getDatabase(Constants.DATABASE_PREFIX + SystemConfig.getSysCode());
		String collectionName = Constants.TABLE_PREFIX + hash;
		logger.debug("collectionName:" + collectionName);
		MongoCollection<Document> coll = db.getCollection(collectionName);
		if (coll != null) {
			Document document = coll.find(eq(Constants.QUALIFIER_ID, id)).first();
			logger.debug("document:" + document);
			if (document != null) {
				DataFile model = new BlobItemEntity();
				model.setId(id);
				model.setCreateBy((String) document.get(Constants.QUALIFIER_CREATEBY));

				if (document.containsKey(Constants.QUALIFIER_FILEID)) {
					model.setFileId((String) document.get(Constants.QUALIFIER_FILEID));
				}
				if (document.containsKey(Constants.QUALIFIER_FILENAME)) {
					model.setFilename((String) document.get(Constants.QUALIFIER_FILENAME));
				}
				if (document.containsKey(Constants.QUALIFIER_CONTENTTYPE)) {
					model.setContentType((String) document.get(Constants.QUALIFIER_CONTENTTYPE));
				}
				if (document.containsKey(Constants.QUALIFIER_SIZE)) {
					model.setSize((Integer) document.get(Constants.QUALIFIER_SIZE));
				}

				if (document.containsKey(Constants.QUALIFIER_LASTMODIFIED)) {
					model.setLastModified((Long) document.get(Constants.QUALIFIER_LASTMODIFIED));
				}

				if (document.containsKey(Constants.QUALIFIER_STATUS)) {
					model.setStatus((Integer) document.get(Constants.QUALIFIER_STATUS));
				}

				if (document.containsKey(Constants.QUALIFIER_CREATEDATE)) {
					long ts = (Long) document.get(Constants.QUALIFIER_CREATEDATE);
					model.setCreateDate(new Date(ts));
				}
				return model;
			}
		}
		return null;
	}

	/**
	 * 获取附件及字节流
	 * 
	 * @param id
	 * @return
	 */
	public DataFile getDataFileWithLob(String id) {
		if (id == null || id.length() < 8) {
			return null;
		}
		logger.debug("get row:" + id);
		int hash = JenkinsHash.getInstance().hash(id.getBytes());
		hash = Math.abs(hash % Constants.DATABASE_PARTITION_SIZE);
		DataFile model = new BlobItemEntity();
		model.setId(id);
		MongoDatabase db = MongodbContainer.getInstance()
				.getDatabase(Constants.DATABASE_PREFIX + SystemConfig.getSysCode());
		String collectionName = Constants.TABLE_PREFIX + hash;
		logger.debug("collectionName:" + collectionName);
		MongoCollection<Document> coll = db.getCollection(collectionName);
		boolean embedded = false;
		if (coll != null) {
			Document document = coll.find(eq(Constants.QUALIFIER_ID, id)).first();
			logger.debug("document:" + document);
			// BasicDBObject filter = new BasicDBObject();
			// filter.put(Constants.QUALIFIER_ID, id);
			// logger.debug("filter:" + filter.toString());
			if (document != null && document.containsKey("embedded")) {
				embedded = document.getBoolean("embedded", false);
			}
		}

		byte[] data = null;
		if (embedded) {
			collectionName = Constants.LOB_TABLE_PREFIX + hash;
			logger.debug("collectionName:" + collectionName);
			MongoCollection<Document> coll2 = db.getCollection(collectionName);
			if (coll2 != null) {
				Document document2 = coll2.find(eq(Constants.QUALIFIER_ID, id)).first();
				logger.debug("document2:" + document2);
				if (document2 != null) {
					if (document2.containsKey(Constants.QUALIFIER_BYTES)) {
						Object object = document2.get(Constants.QUALIFIER_BYTES);
						if (object instanceof byte[]) {
							data = (byte[]) object;
						} else if (object instanceof Binary) {
							Binary binary = (Binary) object;
							data = binary.getData();
						}
					}
				}
			}
		} else {
			collectionName = Constants.LOB_TABLE_PREFIX + hash;
			logger.debug("->collectionName:" + collectionName);
			MongoCollection<Document> coll2 = db.getCollection(collectionName);
			if (coll2 != null) {
				List<byte[]> byteBlocks = new ArrayList<byte[]>();
				BasicDBObject filter = new BasicDBObject();
				filter.put(Constants.QUALIFIER_REF_ID, id);
				logger.debug("filter:" + filter.toString());
				MongoCursor<Document> cur = coll2.find(filter).iterator();
				try {
					while (cur.hasNext()) {
						Document doc = cur.next();
						logger.debug("->document:" + doc.get(Constants.QUALIFIER_BLOCK));
						if (doc.containsKey(Constants.QUALIFIER_BYTES) && doc.containsKey(Constants.QUALIFIER_BLOCK)) {
							Integer index = (Integer) doc.get(Constants.QUALIFIER_BLOCK);
							Object object = doc.get(Constants.QUALIFIER_BYTES);
							if (object instanceof byte[]) {
								data = (byte[]) object;
							} else if (object instanceof Binary) {
								Binary binary = (Binary) object;
								data = binary.getData();
							}
							byteBlocks.add(index, data);
						}
					}
				} finally {
					cur.close();
				}

				if (byteBlocks.size() > 0) {
					data = ByteBlockChopper.glueChopsBackTogether(byteBlocks, Constants.FILE_PARTITION_SIZE);
				}
			}
		}

		model.setData(data);

		return model;
	}

	protected DataFile populate(Document document) {
		DataFile model = new BlobItemEntity();
		model.setId((String) document.get(Constants.QUALIFIER_ID));
		model.setCreateBy((String) document.get(Constants.QUALIFIER_CREATEBY));

		if (document.containsKey(Constants.QUALIFIER_FILEID)) {
			model.setFileId((String) document.get(Constants.QUALIFIER_FILEID));
		}

		if (document.containsKey(Constants.QUALIFIER_FILENAME)) {
			model.setFilename((String) document.get(Constants.QUALIFIER_FILENAME));
		}

		if (document.containsKey(Constants.QUALIFIER_CONTENTTYPE)) {
			model.setContentType((String) document.get(Constants.QUALIFIER_CONTENTTYPE));
		}

		if (document.containsKey(Constants.QUALIFIER_SIZE)) {
			model.setSize((Integer) document.get(Constants.QUALIFIER_SIZE));
		}

		if (document.containsKey(Constants.QUALIFIER_LASTMODIFIED)) {
			model.setLastModified((Long) document.get(Constants.QUALIFIER_LASTMODIFIED));
		}

		if (document.containsKey(Constants.QUALIFIER_STATUS)) {
			model.setStatus((Integer) document.get(Constants.QUALIFIER_STATUS));
		}

		if (document.containsKey(Constants.QUALIFIER_CREATEDATE)) {
			long ts = (Long) document.get(Constants.QUALIFIER_CREATEDATE);
			model.setCreateDate(new Date(ts));
		}

		return model;
	}

	/**
	 * 保存附件内容，附件超过12MB分拆成多个记录存储
	 * @param model
	 * @return
	 */
	public boolean save(DataFile model) {
		if (StringUtils.isNotEmpty(model.getId()) && model.getData() != null) {
			MongoDatabase db = MongodbContainer.getInstance()
					.getDatabase(Constants.DATABASE_PREFIX + SystemConfig.getSysCode());

			String md5 = DigestUtils.md5Hex(model.getData());

			byte[] bytes = model.getData();
			long fileSzie = bytes.length;

			model.setCreateDate(new Date());

			int hash = JenkinsHash.getInstance().hash(model.getId().getBytes());
			hash = Math.abs(hash % Constants.DATABASE_PARTITION_SIZE);

			String collectionName = Constants.TABLE_PREFIX + hash;
			MongoCollection<Document> coll = db.getCollection(collectionName);
			if (coll != null) {
				Document document = new Document();
				document.put(Constants.QUALIFIER_ID, model.getId());
				document.put(Constants.QUALIFIER_MD5, md5);
				document.put(Constants.QUALIFIER_HASH, hash);
				document.put(Constants.QUALIFIER_CONTENTTYPE, model.getContentType());
				document.put(Constants.QUALIFIER_CREATEDATE, model.getCreateDate().getTime());
				document.put(Constants.QUALIFIER_CREATEBY, model.getCreateBy());
				document.put(Constants.QUALIFIER_FILEID, model.getFileId());
				document.put(Constants.QUALIFIER_FILENAME, model.getFilename());
				document.put(Constants.QUALIFIER_LASTMODIFIED, model.getLastModified());
				document.put(Constants.QUALIFIER_SIZE, model.getSize());
				document.put(Constants.QUALIFIER_TYPE, model.getType());

				if (fileSzie < Constants.FILE_PARTITION_SIZE) {
					document.put("embedded", true);
				} else {
					document.put("embedded", false);
				}
				coll.insertOne(document);
				logger.debug(collectionName + " insert doc:" + model.getId());
			}

			if (fileSzie < Constants.FILE_PARTITION_SIZE) {
				/**
				 * 按记录编号维度存放数据
				 */
				collectionName = Constants.LOB_TABLE_PREFIX + hash;
				coll = db.getCollection(collectionName);
				if (coll != null) {
					Document document = new Document();
					document.put(Constants.QUALIFIER_ID, model.getId());
					document.put(Constants.QUALIFIER_MD5, md5);
					document.put(Constants.QUALIFIER_HASH, hash);
					document.put(Constants.QUALIFIER_CONTENTTYPE, model.getContentType());
					document.put(Constants.QUALIFIER_CREATEDATE, model.getCreateDate().getTime());
					document.put(Constants.QUALIFIER_CREATEBY, model.getCreateBy());
					document.put(Constants.QUALIFIER_FILEID, model.getFileId());
					document.put(Constants.QUALIFIER_FILENAME, model.getFilename());
					document.put(Constants.QUALIFIER_LASTMODIFIED, model.getLastModified());
					document.put(Constants.QUALIFIER_SIZE, model.getSize());
					document.put(Constants.QUALIFIER_TYPE, model.getType());
					document.put(Constants.QUALIFIER_BYTES, model.getData());

					if (fileSzie < Constants.FILE_PARTITION_SIZE) {
						document.put("embedded", true);
					}
					coll.insertOne(document);
					logger.debug(collectionName + " insert doc:" + model.getId());
				}
			} else {
				/**
				 * 按记录编号维度存放附件数据
				 */
				List<byte[]> byteBlocks = ByteBlockChopper.chopItUp(model.getData(), Constants.FILE_PARTITION_SIZE);
				int len = byteBlocks.size();
				collectionName = Constants.LOB_TABLE_PREFIX + hash;
				coll = db.getCollection(collectionName);
				if (coll != null) {
					for (int index = 0; index < len; index++) {
						byte[] data = byteBlocks.get(index);
						Document document = new Document();
						document.put(Constants.QUALIFIER_ID, model.getId() + "_" + index);
						document.put(Constants.QUALIFIER_REF_ID, model.getId());
						document.put(Constants.QUALIFIER_BLOCK, index);
						document.put(Constants.QUALIFIER_BYTES, data);
						coll.insertOne(document);
						logger.debug("insert part:" + (index + 1));
					}
				}
			}
			return true;
		}
		return false;
	}

}
