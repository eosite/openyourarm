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
package com.glaf.datamgr.website.springmvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.ZipUtils;
import com.glaf.datamgr.bean.SqliteDBHelper;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataExportService;
import com.glaf.datamgr.service.DataSetService;
import com.zaxxer.hikari.HikariDataSource;

@Controller("/public/dataset/export")
@RequestMapping("/public/dataset/export")
public class DataSetExportController {

	protected static final Log logger = LogFactory.getLog(DataSetExportController.class);

	protected DataSetService dataSetService;

	protected DataExportService dataExportService;

	public DataSetExportController() {

	}

//	protected void decreaseCounter(String userId) {
//		AtomicInteger count = accessCounter.get(userId);
//		if (count == null) {
//			count = new AtomicInteger(0);
//		}
//		count.decrementAndGet();
//		accessCounter.put(userId, count);
//	}
//
//	protected void increaseCounter(String userId) {
//		AtomicInteger count = accessCounter.get(userId);
//		if (count == null) {
//			count = new AtomicInteger(0);
//		}
//
//		count.incrementAndGet();
//		accessCounter.put(userId, count);
//	}

	@javax.annotation.Resource
	public void setDataExportService(DataExportService dataExportService) {
		this.dataExportService = dataExportService;
	}

	@Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@ResponseBody
	@RequestMapping("/sqlite")
	public void sqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
//		ConcurrentMap<String, AtomicInteger> accessCounter = new ConcurrentHashMap<String, AtomicInteger>();
//
//		AuthHelper authHelper = new AuthHelper();

		DataSetBuilder dataSetBuilder = new DataSetBuilder();

		SqliteDBHelper sqliteDBHelper = new SqliteDBHelper();
		// String userId = request.getParameter("userId");
		// if (StringUtils.isNotEmpty(userId)) {
		// increaseCounter(userId);
		HikariDataSource dataSource = null;
		Connection conn = null;
		try {
			// authHelper.checkToken(request, response);
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String datasetId = request.getParameter("datasetId");
			if (StringUtils.isNotEmpty(datasetId)) {
				DataSet dataSet = dataSetService.getDataSet(datasetId);
				if (dataSet != null && StringUtils.isNotEmpty(dataSet.getExportTableName())
						&& StringUtils.equals(dataSet.getAccessType(), "PUB")) {
					Map<String, Object> params = RequestUtils.getParameterMap(request);

					if (!params.containsKey("om")) {
						params.put("om", true);// 只获取mapping字段
					}
					int pageNo = RequestUtils.getInt(request, "page", 1);
					int limit = RequestUtils.getInt(request, "rows", 5000);
					int start = (pageNo - 1) * limit;
					if (start <= 0) {
						start = 0;
					}
					if (limit <= 0) {
						limit = 5000;
					}
					limit = pageNo * limit;
					JSONObject result = dataSetBuilder.getJson(datasetId, start, limit, params);
					if (result != null && result.getJSONArray("rows") != null) {
						JSONArray array = result.getJSONArray("rows");
						TableDefinition tableDefinition = new TableDefinition();
						tableDefinition.setTableName(dataSet.getExportTableName());
						tableDefinition.setName(dataSet.getExportTableName());

						String sqliteDB = dataSet.getExportDBName();
						if (StringUtils.isEmpty(sqliteDB)) {
							sqliteDB = dataSet.getId() + ".db";
						}

						List<SelectSegment> segments = dataSet.getSelectSegments();
						for (SelectSegment segment : segments) {
							if (StringUtils.isNotEmpty(segment.getColumnMapping())
									&& StringUtils.equals(segment.getOutput(), "true")) {
								ColumnDefinition col = new ColumnDefinition();
								col.setName(segment.getColumnMapping());
								col.setColumnName(segment.getColumnMapping());
								col.setJavaType("String");
								col.setLength(500);
								if (StringUtils.equalsIgnoreCase(segment.getColumnMapping(), "id")) {
									tableDefinition.setIdColumn(col);
								} else {
									tableDefinition.addColumn(col);
								}
							}
						}
						boolean myIdGen = false;
						if (tableDefinition.getIdColumn() == null) {
							ColumnDefinition idColumn = new ColumnDefinition();
							idColumn.setName("id");
							idColumn.setColumnName("id");
							idColumn.setJavaType("Long");
							tableDefinition.setIdColumn(idColumn);
							myIdGen = true;
						}
						
						String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + sqliteDB;
						Path filePath = Paths.get(dbpath);
						if(Files.exists(filePath)){
							//清空文件数据
							Files.write(filePath, "".getBytes());
						}
						
						dataSource = sqliteDBHelper.getTempDataSource(sqliteDB);
						conn = dataSource.getConnection();
						conn.setAutoCommit(false);
						DBUtils.createTable(conn, tableDefinition);
						conn.commit();

						int len = array.size();
						JSONObject json = null;
						long id = 1;
						List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < len; i++) {
							json = array.getJSONObject(i);
							Map<String, Object> dataMap = new HashMap<String, Object>();
							Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
							while (iterator.hasNext()) {
								Entry<String, Object> entry = iterator.next();
								String key = (String) entry.getKey();
								Object value = entry.getValue();
								if (value != null) {
									dataMap.put(key, value);
								}
							}
							if (myIdGen) {
								dataMap.put("id", id++);
							}
							datalist.add(dataMap);
						}

						conn.setAutoCommit(false);
						BulkInsertBean bean = new BulkInsertBean();
						bean.bulkInsert(conn, tableDefinition, datalist);
						conn.commit();
						JdbcUtils.close(conn);

						byte[] bytes = FileUtils.getBytes(dbpath);
						ResponseUtils.download(request, response, bytes, sqliteDB);
					}
				}
			}
			String dataSetIds = request.getParameter("dataSetIds");
			String paramsStr = request.getParameter("params");
			if (StringUtils.isNotEmpty(dataSetIds)) {
				JSONObject paramsJson = null;
				Set<String> keySet = null;
				if(paramsStr!=null && !paramsStr.isEmpty()){
					paramsJson = JSON.parseObject(paramsStr);
					keySet = paramsJson.keySet();
				}
				StringTokenizer token = new StringTokenizer(dataSetIds, ",");
				
//				String uuid = UUID.randomUUID().toString();
//				String zipPath = SystemProperties.getConfigRootPath() + "/temp/" + uuid+".zip";
//				Path target2 = Paths.get(zipPath);
//				
//				try {
//		            if(!Files.exists(target2))
//		            	java.io.File.File file = Files.createFile(target2);
//		        } catch (IOException e) {
//		            e.printStackTrace();
//		        }
				Map<String, byte[]> zipMap = new HashMap<>();
				
				while (token.hasMoreTokens()) {
					datasetId = token.nextToken();
					if (StringUtils.isNotEmpty(datasetId)) {
						DataSet dataSet = dataSetService.getDataSet(datasetId);
						if (dataSet != null && StringUtils.isNotEmpty(dataSet.getExportTableName())
								&& StringUtils.equals(dataSet.getAccessType(), "PUB")) {
							Map<String, Object> params = RequestUtils.getParameterMap(request);

							for(String str :keySet){
								params.put(str, paramsJson.get(str));
							}
							
							if (!params.containsKey("om")) {
								params.put("om", true);// 只获取mapping字段
							}
							int pageNo = RequestUtils.getInt(request, "page", 1);
							int limit = RequestUtils.getInt(request, "rows", 5000);
							int start = (pageNo - 1) * limit;
							if (start <= 0) {
								start = 0;
							}
							if (limit <= 0) {
								limit = 5000;
							}
							limit = pageNo * limit;
							JSONObject result = dataSetBuilder.getJson(datasetId, start, limit, params);
							if (result != null && result.getJSONArray("rows") != null) {
								JSONArray array = result.getJSONArray("rows");
								TableDefinition tableDefinition = new TableDefinition();
								tableDefinition.setTableName(dataSet.getExportTableName());
								tableDefinition.setName(dataSet.getExportTableName());

								String sqliteDB = dataSet.getExportDBName();
								if (StringUtils.isEmpty(sqliteDB)) {
									sqliteDB = dataSet.getId() + ".db";
								}

								List<SelectSegment> segments = dataSet.getSelectSegments();
								for (SelectSegment segment : segments) {
									if (StringUtils.isNotEmpty(segment.getColumnMapping())
											&& StringUtils.equals(segment.getOutput(), "true")) {
										ColumnDefinition col = new ColumnDefinition();
										col.setName(segment.getColumnMapping());
										col.setColumnName(segment.getColumnMapping());
										col.setJavaType("String");
										col.setLength(500);
										if (StringUtils.equalsIgnoreCase(segment.getColumnMapping(), "id")) {
											tableDefinition.setIdColumn(col);
										} else {
											tableDefinition.addColumn(col);
										}
									}
								}
								boolean myIdGen = false;
								if (tableDefinition.getIdColumn() == null) {
									ColumnDefinition idColumn = new ColumnDefinition();
									idColumn.setName("id");
									idColumn.setColumnName("id");
									idColumn.setJavaType("Long");
									tableDefinition.setIdColumn(idColumn);
									myIdGen = true;
								}
								
								String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + sqliteDB;
								Path filePath = Paths.get(dbpath);
								if(Files.exists(filePath)){
									Files.write(filePath, "".getBytes());
								}
								
								
								dataSource = sqliteDBHelper.getTempDataSource(sqliteDB);
								conn = dataSource.getConnection();
								conn.setAutoCommit(false);
								DBUtils.createTable(conn, tableDefinition);
								conn.commit();

								int len = array.size();
								JSONObject json = null;
								long id = 1;
								List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
								for (int i = 0; i < len; i++) {
									json = array.getJSONObject(i);
									Map<String, Object> dataMap = new HashMap<String, Object>();
									Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
									while (iterator.hasNext()) {
										Entry<String, Object> entry = iterator.next();
										String key = (String) entry.getKey();
										Object value = entry.getValue();
										if (value != null) {
											dataMap.put(key, value);
										}
									}
									if (myIdGen) {
										dataMap.put("id", id++);
									}
									datalist.add(dataMap);
								}

								
								
								conn.setAutoCommit(false);
								BulkInsertBean bean = new BulkInsertBean();
								bean.bulkInsert(conn, tableDefinition, datalist);
								conn.commit();
								JdbcUtils.close(conn);

//								String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + sqliteDB;
								byte[] bytes = FileUtils.getBytes(dbpath);
								
								zipMap.put(sqliteDB, bytes);
//								ResponseUtils.download(request, response, bytes, sqliteDB);
							}
						}
					}
				}
				ResponseUtils.download(request, response, ZipUtils.toZipBytes(zipMap), "sqliteZip.zip");
				
			}
		} catch (Exception ex) {
			logger.error("export error", ex);
		} finally {
			// this.decreaseCounter(userId);
			JdbcUtils.close(conn);
			if (dataSource != null) {
				dataSource.close();
			}
		}
		// }
	}

}
