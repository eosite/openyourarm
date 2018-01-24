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

package com.glaf.datamgr.parse;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataFile;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.TableDataManager;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.IBlobService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.ExpressionConstants;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.core.xml.XmlReader;

public class ParserFacede {
	protected static final Log logger = LogFactory.getLog(ParserFacede.class);

	public static void main(String[] args) throws Exception {
		String mappingDir = "./report/mapping";
		String dataDir = "./report/data";
		ParserFacede facede = new ParserFacede();
		facede.process(mappingDir, dataDir);
	}

	protected volatile IBlobService blobService;

	protected volatile ITableDefinitionService tableDefinitionService;

	public IBlobService getBlobService() {
		if (blobService == null) {
			blobService = ContextFactory.getBean("blobService");
		}
		return blobService;
	}

	public ITableDefinitionService getTableDefinitionService() {
		if (tableDefinitionService == null) {
			tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
		}
		return tableDefinitionService;
	}

	/**
	 * 解析数据模型
	 * 
	 * @param targetSystemName
	 *            目标系统名
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(String targetSystemName, byte[] mappingFile, String filename, InputStream dataFile,
			String seqNo, boolean saveToDB) {
		List<TableModel> rows = null;
		TableModel tableModel = null;
		TableDefinition tableDefinition = null;
		XmlReader reader = new XmlReader();
		XmlMappingReader xmlReader = new XmlMappingReader();
		try {
			logger.debug(new String(mappingFile, "UTF-8"));
		} catch (IOException e) {
		}

		String currentSystemName = Environment.getCurrentSystemName();
		BufferedInputStream bis = null;
		ByteArrayInputStream bais = null;
		Parser parser = null;
		try {
			bais = new ByteArrayInputStream(mappingFile);
			bis = new BufferedInputStream(bais);
			tableDefinition = reader.read(bis);
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);

			Environment.setCurrentSystemName(targetSystemName);

			if (tableDefinition != null) {
				ColumnDefinition column4 = new ColumnDefinition();
				column4.setTitle("聚合主键");
				column4.setName("aggregationKey");
				column4.setColumnName("AGGREGATIONKEY_");
				column4.setJavaType("String");
				column4.setLength(500);
				tableDefinition.addColumn(column4);
				if (DBUtils.tableExists(targetSystemName, tableDefinition.getTableName())) {
					com.glaf.core.util.DBUtils.alterTable(targetSystemName, tableDefinition);
				} else {
					com.glaf.core.util.DBUtils.createTable(targetSystemName, tableDefinition);
				}
				getTableDefinitionService().save(tableDefinition);
			}

			bais = new ByteArrayInputStream(mappingFile);
			bis = new BufferedInputStream(bais);
			tableModel = xmlReader.read(bis);
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);

			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("json".equals(parseType)) {
					parser = new JsonParser();
				} else if ("access".equals(parseType)) {
					parser = new AccessParser();
				} else if ("sqlite".equals(parseType)) {
					parser = new SqliteParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
					if (StringUtils.endsWithIgnoreCase(filename, ".xlsx")) {
						parser = new POIExcelXlsxParser();
					}
				}
			}
			if (parser != null) {
				rows = parser.parse(tableModel, dataFile);
				logger.debug("saveToDB:" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					if (saveToDB) {
						logger.debug("save data to " + tableModel.getTableName());
						TableDataManager manager = new TableDataManager();
						manager.saveAll(targetSystemName, tableDefinition, seqNo, rows);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("parse data error", ex);
			throw new RuntimeException(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
			IOUtils.closeStream(bais);
			IOUtils.closeStream(bis);
		}
		return rows;
	}

	/**
	 * 
	 * @param targetSystemName
	 *            目标系统名
	 * @param file
	 *            数据文件
	 * @param prefixs
	 *            文件前缀
	 * @param tplMap
	 *            模板
	 */
	public void parse(String targetSystemName, File file, Set<String> prefixs, Map<String, TableModel> tplMap) {
		InputStream inputStream = null;
		Parser parser = null;
		boolean insert = false;
		DataFile dataFile = getBlobService().getBlobByFilename(file.getAbsolutePath());
		if (dataFile == null) {
			insert = true;
			dataFile = new BlobItemEntity();
			dataFile.setFileId(UUID32.getUUID());
			dataFile.setFilename(file.getAbsolutePath());
			dataFile.setData(FileUtils.getBytes(file));
			dataFile.setCreateDate(new Date());
			dataFile.setLastModified(file.lastModified());
			dataFile.setServiceKey("DTS");
			dataFile.setId(UUID32.getUUID());
			dataFile.setName(file.getName());
			dataFile.setSize(file.length());
		} else {
			if (dataFile.getLastModified() == file.lastModified()) {
				logger.debug(file.getAbsolutePath() + " 已经成功处理了，不再重复处理。");
				return;
			}
			insert = false;
			dataFile.setData(FileUtils.getBytes(file));
			dataFile.setCreateDate(new Date());
			dataFile.setLastModified(file.lastModified());
			dataFile.setSize(file.length());
		}

		String filename = file.getName();
		for (String prefix : prefixs) {
			parser = null;
			if (filename.indexOf(prefix) != -1) {
				TableModel tableModel = tplMap.get(prefix);
				String parseClass = tableModel.getParseClass();
				if (StringUtils.isNotEmpty(parseClass)) {
					// 加载自定义的解析器
					parser = (Parser) ClassUtils.instantiateClass(parseClass);
				} else {
					String parseType = tableModel.getParseType();
					if ("csv".equals(parseType)) {
						parser = new CsvTextParser();
					} else if ("json".equals(parseType)) {
						parser = new JsonParser();
					} else if ("access".equals(parseType)) {
						parser = new AccessParser();
					} else if ("sqlite".equals(parseType)) {
						parser = new SqliteParser();
					} else if ("text".equals(parseType)) {
						parser = new PlainTextParser();
					} else if ("xls".equals(parseType)) {
						parser = new POIExcelParser();
						if (StringUtils.endsWithIgnoreCase(file.getName(), ".xlsx")) {
							parser = new POIExcelXlsxParser();
						}
					}
				}
				if (parser != null) {
					try {
						inputStream = new FileInputStream(file);
						List<TableModel> rows = parser.parse(tableModel, inputStream);
						if (rows != null && !rows.isEmpty()) {
							TableDataManager manager = new TableDataManager();
							manager.saveAll(targetSystemName, tableModel.getTableName(), null, rows);
							if (insert) {
								dataFile.setStatus(9);
								getBlobService().insertBlob(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经存储。");
							} else {
								dataFile.setStatus(9);
								getBlobService().updateBlobFileInfo(dataFile);
								logger.debug(dataFile.getFilename() + "内容已经更新。");
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("parse data error", ex);
						throw new RuntimeException(ex);
					} finally {
						IOUtils.closeStream(inputStream);
					}
				}
			}
		}
	}

	/**
	 * 解析数据模型
	 * 
	 * @param targetSystemName
	 *            目标系统名
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(String targetSystemName, InputStream mappingFile, String filename,
			InputStream dataFile, String seqNo, boolean saveToDB) {
		List<TableModel> rows = null;
		TableModel tableModel = null;
		TableDefinition tableDefinition = null;
		XmlReader reader = new XmlReader();
		XmlMappingReader xmlReader = new XmlMappingReader();

		tableDefinition = reader.read(mappingFile);

		if (tableDefinition != null) {
			ColumnDefinition column4 = new ColumnDefinition();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY_");
			column4.setJavaType("String");
			column4.setLength(500);
			tableDefinition.addColumn(column4);
			if (DBUtils.tableExists(targetSystemName, tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(targetSystemName, tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(targetSystemName, tableDefinition);
			}
		}

		String currentSystemName = Environment.getCurrentSystemName();
		Parser parser = null;
		try {
			tableModel = xmlReader.read(mappingFile);
			Environment.setCurrentSystemName(targetSystemName);
			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("json".equals(parseType)) {
					parser = new JsonParser();
				} else if ("access".equals(parseType)) {
					parser = new AccessParser();
				} else if ("sqlite".equals(parseType)) {
					parser = new SqliteParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
					if (StringUtils.endsWithIgnoreCase(filename, ".xlsx")) {
						parser = new POIExcelXlsxParser();
					}
				}
			}
			if (parser != null) {
				rows = parser.parse(tableModel, dataFile);
				logger.debug("saveToDB:" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					for (TableModel row : rows) {
						if (row.getColumns() != null) {
							for (ColumnModel col : row.getColumns()) {
								if (StringUtils.isNotEmpty(col.getValueExpression()) && StringUtils
										.equals(col.getValueExpression(), ExpressionConstants.SEQNO_EXPRESSION)) {
									col.setValue(seqNo);
								}
							}
						}
					}
					if (saveToDB) {
						logger.debug("save data to " + tableModel.getTableName());
						TableDataManager manager = new TableDataManager();
						manager.saveAll(targetSystemName, tableDefinition, seqNo, rows);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("parse data error", ex);
			throw new RuntimeException(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return rows;
	}

	/**
	 * 解析数据模型
	 * 
	 * @param targetSystemName
	 *            目标系统名
	 * @param mappingFile
	 *            元数据配置文件
	 * @param dataFile
	 *            解析数据文件
	 * @param saveToDB
	 *            是否保存到数据库
	 * @return
	 */
	public List<TableModel> parse(String targetSystemName, String mappingFile, String dataFile, String seqNo,
			boolean saveToDB) {
		TableModel tableModel = null;
		TableDefinition tableDefinition = null;
		XmlReader reader = new XmlReader();
		try {
			tableDefinition = reader.read(new FileInputStream(mappingFile));
			if (tableDefinition != null) {
				ColumnDefinition column4 = new ColumnDefinition();
				column4.setTitle("聚合主键");
				column4.setName("aggregationKey");
				column4.setColumnName("AGGREGATIONKEY_");
				column4.setJavaType("String");
				column4.setLength(500);
				tableDefinition.addColumn(column4);
				if (DBUtils.tableExists(tableDefinition.getTableName())) {
					DBUtils.alterTable(tableDefinition);
				} else {
					DBUtils.createTable(tableDefinition);
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		String currentSystemName = Environment.getCurrentSystemName();
		XmlMappingReader xmlReader = new XmlMappingReader();
		InputStream inputStream = null;
		Parser parser = null;
		try {
			Environment.setCurrentSystemName(targetSystemName);
			inputStream = new FileInputStream(mappingFile);
			tableModel = xmlReader.read(inputStream);
			IOUtils.closeStream(inputStream);

			String parseClass = tableModel.getParseClass();
			if (StringUtils.isNotEmpty(parseClass)) {
				// 加载自定义的解析器
				parser = (Parser) ClassUtils.instantiateClass(parseClass);
			} else {
				String parseType = tableModel.getParseType();
				if ("csv".equals(parseType)) {
					parser = new CsvTextParser();
				} else if ("json".equals(parseType)) {
					parser = new JsonParser();
				} else if ("access".equals(parseType)) {
					parser = new AccessParser();
				} else if ("sqlite".equals(parseType)) {
					parser = new SqliteParser();
				} else if ("text".equals(parseType)) {
					parser = new PlainTextParser();
				} else if ("xls".equals(parseType)) {
					parser = new POIExcelParser();
					if (StringUtils.endsWithIgnoreCase(dataFile, ".xlsx")) {
						parser = new POIExcelXlsxParser();
					}
				}
			}
			if (parser != null) {
				logger.debug("parser:" + parser.getClass().getName());
				inputStream = new FileInputStream(dataFile);
				List<TableModel> rows = parser.parse(tableModel, inputStream);
				IOUtils.closeStream(inputStream);
				logger.debug("saveToDB:" + saveToDB);
				if (rows != null && !rows.isEmpty()) {
					if (saveToDB) {
						logger.debug("aggregationKeys " + tableDefinition.getAggregationKeys());
						logger.debug("save data to " + tableModel.getTableName());
						TableDataManager manager = new TableDataManager();
						manager.saveAll(targetSystemName, tableDefinition, seqNo, rows);
					}
				}
				return rows;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("parse data error", ex);
			throw new RuntimeException(ex);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
			IOUtils.closeStream(inputStream);
		}
		return null;
	}

	public void process(String mappingDir, String dataDir) {
		Set<String> prefixs = new HashSet<String>();
		XmlMappingReader reader = new XmlMappingReader();
		Map<String, TableModel> tplMap = new java.util.HashMap<String, TableModel>();
		java.io.File directory = new java.io.File(mappingDir);
		if (directory.exists()) {
			File[] filelist = directory.listFiles();
			if (filelist != null) {
				for (int i = 0, len = filelist.length; i < len; i++) {
					File file = filelist[i];
					try {
						if (file.getName().endsWith(".xml")) {
							TableModel tableModel = reader.read(new FileInputStream(file));
							// System.out.println("tableModel="+tableModel.toString());
							if (tableModel.getFilePrefix() != null) {
								tplMap.put(tableModel.getFilePrefix(), tableModel);
								prefixs.add(tableModel.getFilePrefix());
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		/**
		 * 下面读取文件只考虑两层
		 */
		java.io.File directory2 = new java.io.File(dataDir);
		if (directory2.exists()) {
			File[] filelist = directory2.listFiles();
			if (filelist != null) {
				for (int i = 0, len = filelist.length; i < len; i++) {
					File file = filelist[i];
					if (file.isFile()) {
						boolean success = false;
						int retry = 0;
						while (retry < 2 && !success) {
							try {
								retry++;
								this.parse(Environment.DEFAULT_SYSTEM_NAME, file, prefixs, tplMap);
								success = true;
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						File[] filelist2 = file.listFiles();
						if (filelist2 != null) {
							for (int ii = 0, len2 = filelist2.length; ii < len2; ii++) {
								File f = filelist2[ii];
								if (f.isFile()) {
									boolean success = false;
									int retry = 0;
									while (retry < 2 && !success) {
										try {
											retry++;
											this.parse(Environment.DEFAULT_SYSTEM_NAME, f, prefixs, tplMap);
											success = true;
										} catch (Exception ex) {
											ex.printStackTrace();
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void setBlobService(IBlobService blobService) {
		this.blobService = blobService;
	}

	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

}