package com.glaf.datamgr.job;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.job.BaseJob;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;

import com.glaf.datamgr.bean.SqliteDBHelper;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetExport;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.query.DataSetExportQuery;
import com.glaf.datamgr.service.DataSetExportService;
import com.glaf.datamgr.service.DataSetService;
import com.zaxxer.hikari.HikariDataSource;

public class DataSetExportJob extends BaseJob {

	protected DataSetService dataSetService;

	protected DataSetExportService dataSetExportService;

	protected IDatabaseService databaseService;

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		databaseService = ContextFactory.getBean("databaseService");
		dataSetService = ContextFactory.getBean("dataSetService");
		dataSetExportService = ContextFactory.getBean("com.glaf.datamgr.service.dataSetExportService");
		DataSetExportQuery query = new DataSetExportQuery();
		query.locked(0);
		query.deleteFlag(0);
		query.scheduleFlag("Y");
		List<DataSetExport> list = dataSetExportService.list(query);
		if (list != null && !list.isEmpty()) {
			DataSetBuilder dataSetBuilder = new DataSetBuilder();
			SqliteDBHelper sqliteDBHelper = new SqliteDBHelper();
			HikariDataSource dataSource = null;
			Connection conn = null;
			DataSet dataSet = null;

			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int week = calendar.get(Calendar.WEEK_OF_YEAR);
			int day = calendar.get(Calendar.DAY_OF_MONTH);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("_date_", now);
			params.put("_now_", now);
			params.put("_year_", year);
			params.put("_month_", month);
			params.put("_week_", week);
			params.put("_day_", day);
			params.put("_nowYearMonth_", DateUtils.getNowYearMonth());
			params.put("_nowYearMonthDay_", DateUtils.getNowYearMonthDay());

			for (DataSetExport dataSetExport : list) {
				if (dataSetExport != null && StringUtils.isNotEmpty(dataSetExport.getDatasetIds())) {
					try {
						String sqliteDB = dataSetExport.getId() + ".db";
						String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + sqliteDB;
						FileUtils.deleteFile(dbpath);
						List<String> datasetIds = StringTools.split(dataSetExport.getDatasetIds());
						for (String datasetId : datasetIds) {
							dataSet = dataSetService.getDataSet(datasetId);
							if (dataSet != null && StringUtils.isNotEmpty(dataSet.getExportTableName())) {
								int pageNo = 1;
								int limit = 500000;
								int start = (pageNo - 1) * limit;
								if (start <= 0) {
									start = 0;
								}
								if (limit <= 0) {
									limit = 500000;
								}
								limit = pageNo * limit;
								JSONObject result = dataSetBuilder.getJson(datasetId, start, limit, params);
								if (result != null && result.getJSONArray("rows") != null) {
									JSONArray array = result.getJSONArray("rows");
									TableDefinition tableDefinition = new TableDefinition();
									tableDefinition.setTableName(dataSet.getExportTableName());
									tableDefinition.setName(dataSet.getExportTableName());
									List<String> columnNames = new ArrayList<String>();
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
											columnNames.add(segment.getColumnMapping().toLowerCase());
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

									boolean databaseid = false;
									boolean databasemapping = false;
									Database database = null;
									if (!columnNames.contains("databaseid_")) {
										ColumnDefinition databaseidColumn = new ColumnDefinition();
										databaseidColumn.setName("databaseid_");
										databaseidColumn.setColumnName("DATABASEID_");
										databaseidColumn.setJavaType("Long");
										tableDefinition.addColumn(databaseidColumn);
										databaseid = true;
										if (dataSet.getDatabaseId() != null && dataSet.getDatabaseId() > 0) {
											database = databaseService.getDatabaseById(dataSet.getDatabaseId());
										}
									}

									if (!columnNames.contains("databasemapping_")) {
										ColumnDefinition databaseMappingColumn = new ColumnDefinition();
										databaseMappingColumn.setName("databasemapping_");
										databaseMappingColumn.setColumnName("DATABASEMAPPING_");
										databaseMappingColumn.setJavaType("String");
										databaseMappingColumn.setLength(50);
										tableDefinition.addColumn(databaseMappingColumn);
										databasemapping = true;
										if (database == null && dataSet.getDatabaseId() != null
												&& dataSet.getDatabaseId() > 0) {
											database = databaseService.getDatabaseById(dataSet.getDatabaseId());
										}
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

										if (database != null) {
											if (databaseid) {
												dataMap.put("databasemapping_", database.getId());
											}
											if (databasemapping) {
												dataMap.put("databasemapping_", database.getMapping());
											}
										}

										datalist.add(dataMap);
									}

									conn.setAutoCommit(false);
									BulkInsertBean bean = new BulkInsertBean();
									bean.bulkInsert(conn, tableDefinition, datalist);
									conn.commit();
									JdbcUtils.close(conn);
									dataSource.close();
								}
							}
						}

						byte[] bytes = FileUtils.getBytes(dbpath);
						FileUtils.save(SystemProperties.getAppPath() + "/export/" + dataSetExport.getId(), bytes);
						FileUtils.save(SystemProperties.getAppPath() + "/export/" + dataSetExport.getExportDBName(),
								bytes);

					} catch (Exception ex) {
						logger.error("export error", ex);
					} finally {
						JdbcUtils.close(conn);
						if (dataSource != null) {
							dataSource.close();
						}
					}
				}
			}
		}
	}

}
