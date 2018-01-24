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

package com.glaf.matrix.data.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataFile;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.resource.ResourceFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.matrix.data.mapper.DataFileMapper;
import com.glaf.matrix.data.query.DataFileQuery;
import com.glaf.matrix.data.service.IDataFileService;
import com.glaf.matrix.data.util.DataFileJsonFactory;

@Service("dataFileService")
@Transactional(readOnly = true)
public class DataFileServiceImpl implements IDataFileService {

	protected final static Log logger = LogFactory.getLog(DataFileServiceImpl.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected DataFileMapper dataFileMapper;

	protected SqlSession sqlSession;

	public DataFileServiceImpl() {

	}

	@Transactional
	public void copyDataFile(String sourceId, String destId) {
		DataFile source = this.getDataFileWithBytesById(sourceId);
		DataFile dest = this.getDataFileById(destId);
		dest.setLastModified(System.currentTimeMillis());
		dest.setFilename(source.getFilename());
		dest.setData(source.getData());
		dest.setSize(source.getSize());
		dest.setContentType(source.getContentType());
		this.updateDataFileInfo(dest);
	}

	public int count(DataFileQuery query) {
		return dataFileMapper.getDataFileCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "mx_blob_" + id;
			CacheFactory.remove("blob", cacheKey);
		}
		DataFile dataFile = this.getDataFileById(id);
		if (dataFile != null) {
			DataFileQuery query = new DataFileQuery();
			query.setId(id);

			dataFileMapper.deleteDataFilesByFileId(query);

			query.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			dataFileMapper.deleteDataFileById(query);

		}
	}

	@Transactional
	public void deleteDataFileByBusinessKey(String businessKey) {
		DataFileQuery query = new DataFileQuery();
		query.businessKey(businessKey);

		List<DataFile> list = dataFileMapper.getDataFiles(query);

		dataFileMapper.deleteDataFilesByBusinessKey(query);

		if (list != null && !list.isEmpty()) {
			for (DataFile dataFile : list) {
				query.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
				dataFileMapper.deleteDataFileById(query);
			}
		}
	}

	@Transactional
	public void deleteDataFileByFileId(String fileId) {
		if (SystemConfig.getBoolean("use_query_cache")) {
			String cacheKey = "mx_blob_" + fileId;
			CacheFactory.remove("blob", cacheKey);
		}
		DataFile dataFile = this.getDataFileByFileId(fileId);
		if (dataFile != null) {
			DataFileQuery query = new DataFileQuery();
			query.fileId(fileId);

			dataFileMapper.deleteDataFilesByFileId(query);

			query.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			dataFileMapper.deleteDataFileById(query);

		}
	}

	public byte[] getBytesByFileId(String fileId) {
		DataFile dataFile = this.getDataFileByFileId(fileId);
		String cacheKey = "mx_blob_" + fileId;

		if (SystemConfig.getBoolean("use_file_cache")) {
			byte[] data = ResourceFactory.getData("mx_blob", cacheKey);
			if (data != null) {
				logger.debug("fetch byte[] data from cache.");
				return data;
			}
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			dataFile = (DataFile) entityDAO.getSingleObject("getDataFileFileInfoByFileId_postgres", fileId);
		} else {
			dataFile = (DataFile) entityDAO.getSingleObject("getDataFileFileInfoByFileId", fileId);
		}
		if (dataFile != null && dataFile.getData() != null) {
			if (SystemConfig.getBoolean("use_file_cache")
					&& dataFile.getData().length < conf.getInt("cache_file_size", 500) * FileUtils.KB_SIZE) {
				ResourceFactory.put("mx_blob", cacheKey, dataFile.getData());
				logger.debug("put byte[] data into cache.");
			}
			return dataFile.getData();
		}
		return null;
	}

	public byte[] getBytesById(String id) {
		DataFile dataFile = this.getDataFileById(id);
		String cacheKey = "mx_blob_" + id;

		if (SystemConfig.getBoolean("use_file_cache")) {
			byte[] data = ResourceFactory.getData("mx_blob", cacheKey);
			if (data != null) {
				logger.debug("fetch byte[] data from cache.");
				return data;
			}
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			dataFile = (DataFile) entityDAO.getSingleObject("getDataFileFileInfoById_postgres", id);
		} else {
			dataFile = (DataFile) entityDAO.getSingleObject("getDataFileFileInfoById", id);
		}
		if (dataFile != null && dataFile.getData() != null) {
			if (SystemConfig.getBoolean("use_file_cache")
					&& dataFile.getData().length < conf.getInt("cache_file_size", 500) * FileUtils.KB_SIZE) {
				ResourceFactory.put("mx_blob", cacheKey, dataFile.getData());
				logger.debug("put byte[] data into cache.");
			}
			return dataFile.getData();
		}
		return null;
	}

	public DataFile getDataFileByFileId(String fileId) {
		String cacheKey = "mx_blob_" + fileId;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("blob", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject jsonObject = JSON.parseObject(text);
					DataFileJsonFactory.jsonToObject(jsonObject);
				} catch (Exception ex) {
				}
			}
		}
		DataFile dataFile = null;
		DataFileQuery query = new DataFileQuery();
		query.fileId(fileId);
		List<DataFile> list = dataFileMapper.getDataFilesByFileId(query);
		if (list != null && !list.isEmpty()) {
			dataFile = list.get(0);
			if (dataFile != null) {
				if (SystemConfig.getBoolean("use_query_cache")) {
					CacheFactory.put("blob", cacheKey, dataFile.toJsonObject().toJSONString());
				}
			}
		}
		return dataFile;
	}

	public DataFile getDataFileById(String id) {
		String cacheKey = "mx_blob_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("blob", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject jsonObject = JSON.parseObject(text);
					DataFileJsonFactory.jsonToObject(jsonObject);
				} catch (Exception ex) {
				}
			}
		}
		DataFileQuery query = new DataFileQuery();
		query.setId(id);
		DataFile dataFile = dataFileMapper.getDataFileById(query);
		if (dataFile != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.put("blob", cacheKey, dataFile.toJsonObject().toJSONString());
			}
		}
		return dataFile;
	}

	public List<DataFile> getDataFileList(DataFileQuery query) {
		List<DataFile> list = list(query);
		List<DataFile> rows = new java.util.ArrayList<DataFile>();
		for (DataFile dataFile : list) {
			rows.add(dataFile);
		}
		return rows;
	}

	public List<DataFile> getDataFileList(String serviceKey, String businessKey) {
		DataFileQuery query = new DataFileQuery();
		query.serviceKey(serviceKey);
		query.businessKey(businessKey);
		List<DataFile> list = list(query);
		List<DataFile> rows = new java.util.ArrayList<DataFile>();
		for (DataFile dataFile : list) {
			rows.add(dataFile);
		}
		return rows;
	}

	public DataFile getDataFileWithBytesByFileId(String fileId) {
		DataFile dataFile = this.getDataFileByFileId(fileId);
		if (dataFile != null) {
			byte[] data = this.getBytesById(dataFile.getId());
			dataFile.setData(data);
		}
		return dataFile;
	}

	public DataFile getDataFileWithBytesById(String id) {
		DataFileQuery query = new DataFileQuery();
		query.setId(id);
		DataFile dataFile = dataFileMapper.getDataFileById(query);
		byte[] data = this.getBytesById(id);
		dataFile.setData(data);
		return dataFile;
	}

	public InputStream getInputStreamByFileId(String fileId) {
		byte[] bytes = this.getBytesByFileId(fileId);
		if (bytes != null) {
			return new BufferedInputStream(new ByteArrayInputStream(bytes));
		}
		return null;
	}

	public InputStream getInputStreamById(String id) {
		byte[] bytes = this.getBytesById(id);
		if (bytes != null) {
			return new BufferedInputStream(new ByteArrayInputStream(bytes));
		}
		return null;
	}

	public DataFile getMaxDataFile(DataFileQuery query) {
		DataFile dataFile = null;
		List<DataFile> list = list(query);
		if (list != null && !list.isEmpty()) {
			dataFile = list.get(0);
			Iterator<DataFile> iterator = list.iterator();
			while (iterator.hasNext()) {
				DataFile model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					dataFile = model;
				}
			}
		}
		return dataFile;
	}

	public DataFile getMaxDataFile(String businessKey) {
		DataFile dataFile = null;
		DataFileQuery query = new DataFileQuery();
		query.businessKey(businessKey);
		List<DataFile> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			dataFile = list.get(0);
			Iterator<DataFile> iterator = list.iterator();
			while (iterator.hasNext()) {
				DataFile model = iterator.next();
				if (model.getLastModified() > dataFile.getLastModified()) {
					dataFile = model;
				}
			}
		}
		return dataFile;
	}

	public DataFile getMaxDataFileWithBytes(String businessKey) {
		DataFile dataFile = null;
		DataFileQuery query = new DataFileQuery();
		query.businessKey(businessKey);
		List<DataFile> list = this.list(query);
		if (list != null && !list.isEmpty()) {
			dataFile = list.get(0);
			Iterator<DataFile> iterator = list.iterator();
			while (iterator.hasNext()) {
				DataFile model = iterator.next();
				if (model.getLastModified() > model.getLastModified()) {
					dataFile = model;
				}
			}
		}
		if (dataFile != null) {
			byte[] bytes = this.getBytesById(dataFile.getId());
			dataFile.setData(bytes);
		}
		return dataFile;
	}

	@Transactional
	public void insertDataFile(DataFile dataFile) {
		if (StringUtils.isEmpty(dataFile.getId())) {
			dataFile.setId(DateUtils.getDate(new Date()) + UUID32.getUUID());
		}

		dataFile.setCreateDate(new Date());

		if (dataFile.getFileId() == null) {
			dataFile.setFileId(UUID32.getUUID());
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			dataFileMapper.insertDataFile_postgres(dataFile);

			dataFile.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			dataFileMapper.insertDataFile_postgres(dataFile);
		} else {
			dataFileMapper.insertDataFile(dataFile);

			dataFile.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			dataFileMapper.insertDataFile(dataFile);
		}

	}

	public List<DataFile> list(DataFileQuery query) {
		List<DataFile> list = dataFileMapper.getDataFiles(query);
		return list;
	}

	@Transactional
	public void makeMark(String createBy, String serviceKey, String businessKey) {
		DataFileQuery query = new DataFileQuery();
		query.serviceKey(serviceKey);
		query.createBy(createBy);
		query.status(0);

		List<DataFile> list = this.list(query);
		if (list != null && list.size() > 0) {
			Iterator<DataFile> iterator = list.iterator();
			while (iterator.hasNext()) {
				DataFile model = (DataFile) iterator.next();
				if (StringUtils.isNotEmpty(businessKey)) {
					model.setBusinessKey(businessKey);
					model.setStatus(1);
					this.updateDataFile(model);
				}
			}
		}
	}

	@Transactional
	public void makeMark(String createBy, String serviceKey, String businessKey, int status) {
		DataFileQuery query = new DataFileQuery();
		query.serviceKey(serviceKey);
		query.createBy(createBy);
		query.status(0);

		List<DataFile> list = this.list(query);
		if (list != null && list.size() > 0) {
			Iterator<DataFile> iterator = list.iterator();
			while (iterator.hasNext()) {
				DataFile model = (DataFile) iterator.next();
				if (StringUtils.isNotEmpty(businessKey)) {
					model.setBusinessKey(businessKey);
					model.setStatus(status);
					this.updateDataFile(model);
				}
			}
		}
	}

	@Transactional
	public void saveAll(List<DataFile> dataList) {
		Iterator<DataFile> iterator = dataList.iterator();
		while (iterator.hasNext()) {
			DataFile dataFile = iterator.next();
			if (dataFile.getData() != null) {
				if (dataFile.getSize() <= 0) {
					dataFile.setSize(dataFile.getData().length);
				}
				this.insertDataFile(dataFile);
			}
		}
	}

	@Transactional
	public void saveAll(Map<String, DataFile> dataMap) {
		DataFileQuery query = new DataFileQuery();
		List<String> names = new ArrayList<String>();
		for (String str : dataMap.keySet()) {
			names.add(str);
		}
		query.names(names);
		List<DataFile> dataList = this.list(query);
		Map<String, Object> exists = new java.util.HashMap<String, Object>();
		if (dataList != null && dataList.size() > 0) {
			Iterator<DataFile> iterator = dataList.iterator();
			while (iterator.hasNext()) {
				DataFile dataFile = iterator.next();
				exists.put(dataFile.getName(), dataFile);
			}
		}
		Iterator<DataFile> iterator = dataMap.values().iterator();
		while (iterator.hasNext()) {
			DataFile dataFile = iterator.next();
			if (dataFile.getData() != null) {
				if (exists.get(dataFile.getName()) != null) {
					DataFile model = (DataFile) exists.get(dataFile.getName());
					if (model.getSize() != dataFile.getData().length) {
						model.setFilename(dataFile.getFilename());
						model.setData(dataFile.getData());
						model.setSize(dataFile.getData().length);
						this.updateDataFileInfo(model);
					}
				} else {
					if (dataFile.getSize() <= 0) {
						dataFile.setSize(dataFile.getData().length);
					}
					this.insertDataFile(dataFile);
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setDataFileMapper(DataFileMapper dataFileMapper) {
		this.dataFileMapper = dataFileMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Transactional
	public void updateDataFile(DataFile dataFile) {
		dataFileMapper.updateDataFile(dataFile);

		dataFile.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
		dataFileMapper.updateDataFile(dataFile);
	}

	@Transactional
	public void updateDataFileInfo(DataFile dataFile) {
		if (dataFile.getData() == null) {
			throw new RuntimeException("bytes is null");
		}
		if (dataFile.getSize() <= 0) {
			dataFile.setSize(dataFile.getData().length);
		}

		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			dataFile.setPath(null);
			entityDAO.update("updateDataFileFileInfo_postgres", dataFile);

			dataFile.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			entityDAO.update("updateDataFileFileInfo_postgres", dataFile);
		} else {
			dataFile.setPath(null);
			entityDAO.update("updateDataFileFileInfo", dataFile);

			dataFile.setTableSuffix(String.valueOf(DateUtils.getYearMonthDay(dataFile.getCreateDate())));
			entityDAO.update("updateDataFileFileInfo", dataFile);
		}

	}

}