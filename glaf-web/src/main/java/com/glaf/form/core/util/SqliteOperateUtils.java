package com.glaf.form.core.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysApplication;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.UserSqlite;
import com.glaf.form.core.domain.UserSqliteUpload;
import com.glaf.form.core.service.FormPageService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/**
 * 
 * JSON工厂类
 *
 */
@Component("com.glaf.form.core.util.sqliteOperateUtils")
public class SqliteOperateUtils {
	protected static final Log logger = LogFactory.getLog(SqliteOperateUtils.class);
	
	@Autowired
	protected SysApplicationService sysApplicationService;
	@Autowired
	protected FormPageService formPageService;
	
	int i = 0;
	public String createByTables(Connection conn,Connection connBySystemName,JSONArray ruleAry) throws Exception{
		String message = "";
		String sql = "";
		//导出的表的对应的规则信息
		TableDefinition tableRuleDefinition = new TableDefinition();
		tableRuleDefinition.setTableName("table_rule_sqlite_");
		tableRuleDefinition.setName("table_rule_sqlite_");
		
		//设置主键信息
		ColumnDefinition column = new ColumnDefinition();
		column.setColumnName("TABLENAME");	//表名
		column.setJavaType("String");	//java类型
		column.setLength(50);	//长度
		column.setName("TABLENAME");
		tableRuleDefinition.setIdColumn(column);
		
		//设置其他数据信息
		List<ColumnDefinition> tableRuleColumnDefinitions = new ArrayList<>();
		tableRuleColumnDefinitions.add(column);
		//规则字段
		column = new ColumnDefinition();
		column.setColumnName("RULE");	//表名
		column.setJavaType("String");	//java类型
		column.setName("RULE");
		tableRuleColumnDefinitions.add(column);
		//字段信息
		column = new ColumnDefinition();
		column.setColumnName("COLUMNSDEF");	//表名
		column.setJavaType("String");	//java类型
		column.setName("COLUMNSDEF");
		tableRuleColumnDefinitions.add(column);
		tableRuleDefinition.setColumns(tableRuleColumnDefinitions);
		DBUtils.createTable(conn, tableRuleDefinition);
		
		Map<String,Connection> conMap = new HashMap(); 
		List<Map<String, Object>> ruleList = new ArrayList<>();
		JSONObject ruleJson = null;
		String tableName = null;
		String biaoduan = null;
		String tableType = null;
		String idlist = null;
		String idColumnName = null;
		TableDefinition tableDefinition = null;
		List<ColumnDefinition> columnDefinitions = null;
		QueryRunner qr = null;
		List<Map<String,Object>> countlist = null;
		long maxCount = 0;
		BulkInsertBean bean = null;
		List<Map<String, Object>> datalist = null;
		String createSql = null;
		Dialect dialect = null;
		Map<String,Object> rulemap = null;
		Map countMap = null;
		Object count = null;
		for(Object ruleObj : ruleAry){
			ruleJson = (JSONObject)ruleObj;
			tableName = ruleJson.getString("tableName");
			biaoduan = ruleJson.getString("biaoduan");
			tableType = ruleJson.getString("tableType");
			//JSONArray idlist = ruleJson.getJSONArray("idlist");
			idlist = ruleJson.getString("idlist");
			idColumnName = ruleJson.getString("idColumnName");
			
			try{
				//设置表信息
				tableDefinition = new TableDefinition();
				tableDefinition.setTableName(tableName);
				tableDefinition.setName(tableName);
				
				//设置表字段信息
				columnDefinitions = DBUtils.getColumnDefinitions(biaoduan, tableName);
				tableDefinition.setColumns(columnDefinitions);
				
				DBUtils.createTable(conn, tableDefinition);
				conn.commit();
				qr = new QueryRunner(true);
				
				//获取表数据信息
				if(conMap.containsKey(biaoduan)){
					connBySystemName = conMap.get(biaoduan);
				}else{
					connBySystemName = DBConnectionFactory.getConnection(biaoduan);
					logger.debug("标段为"+biaoduan+",数据库类型:"+DBConnectionFactory.getDatabaseType(connBySystemName));
					conMap.put(biaoduan, connBySystemName);
				}
				
				sql = "SELECT * FROM " + tableName;
				if(idColumnName != null && idlist != null){
					sql += " where "+idColumnName+ " in(" + idlist+")";
				}
				
				//源库数据查询(标段库库)
				qr = new QueryRunner(true);
				countlist = qr.query(connBySystemName, "SELECT count(1) as count FROM " + tableName, new MapListHandler());
				maxCount = 0;
				if(countlist != null && countlist.size() > 0){
					countMap = countlist.get(0);
					count =  countMap.get("count");
					if(count != null){
						try{
							maxCount = (long)count;
						}catch(ClassCastException e){
							try{
								maxCount = ((BigDecimal)count).longValue();
							}catch(ClassCastException ex){
								maxCount = (long)(int)count;
							}
							
						}
					}
				}
				int maxPageCount = 900;
				bean = new BulkInsertBean();
				if(maxCount > maxPageCount){
					for(int i = 0 ; i < maxCount; ){
						dialect = DBConfiguration.getDatabaseDialect(connBySystemName);
						createSql  = dialect.getLimitString(sql, i, maxPageCount);
						i += maxPageCount;
						qr = new QueryRunner(true);
						datalist = qr.query(connBySystemName, createSql, new MapListHandler());
						conn.setAutoCommit(false);
						bean.bulkInsert(conn, tableDefinition, datalist);
						conn.commit();
					}
				}else{
					datalist = qr.query(connBySystemName, sql, new MapListHandler());
					//			JdbcUtils.close(connBySystemName);
					//插入数据
					conn.setAutoCommit(false);
					bean.bulkInsert(conn, tableDefinition, datalist);
					conn.commit();
				}
				logger.debug("一共插入数据量为:"+maxCount);
				
				//保存规则信息
				rulemap = new HashMap<>();
				rulemap.put("TABLENAME", tableName);
				rulemap.put("RULE", ruleJson.toString());
				rulemap.put("COLUMNSDEF", tableDefinition.toJsonObject().toJSONString());
				ruleList.add(rulemap);
				
				if(ruleList.size() >= 20){
					bean = new BulkInsertBean();
					bean.bulkInsert(conn, tableRuleDefinition, ruleList);
					ruleList = new ArrayList<>();
				}
				conn.commit();
			}catch(Exception e){
				logger.error("表("+tableName+")操作失败！");
				e.printStackTrace();
				message += "表("+tableName+")操作失败！" + e.toString();
			}
		}
		if(ruleList.size() > 0){
			bean = new BulkInsertBean();
			bean.bulkInsert(conn, tableRuleDefinition, ruleList);
			conn.commit();
		}
		return message;
	}
	
	/**
	 * 根据模块导出页面数据，form_page,form_rule,sys_database,table
	 * 页面，页面规则，数据集，表
	 * @param conn
	 * @param connBySystemName
	 * @param ruleAry
	 * @throws Exception
	 */
	public void createByModel(Connection conn,Connection connBySystemName,JSONArray ruleAry) throws Exception{
		for(Object ruleObj : ruleAry){
			JSONObject ruleJson = (JSONObject)ruleObj;
			Long id = ruleJson.getLong("id");
			SysApplication sysApplication = sysApplicationService.findById(id);
			String code = sysApplication.getCode();
			if(code != null && !code.isEmpty()){
				//编码不为空时，找到对应的页面form_page
				FormPage formPage =  formPageService.getFormPage(code);
				//根据pageId查找对应的规则信息form_rule表
				
				//根据pageId查找对应的数据集信息
				
				//根据数据集找到对应的表信息
				
				
			}
			
			
			//插入数据
			conn.setAutoCommit(false);
			BulkInsertBean bean = new BulkInsertBean();
			conn.commit();
		}
	}
	/**
	 * 根据页面id导出页面数据
	 * @param conn
	 * @param connBySystemName
	 * @param pageId
	 */
	public void createByPageId(Connection conn,Connection connBySystemName,JSONArray pageId){
		
	}
	
	public JSONObject createSqliteFile(UserSqlite userSqlite) throws IOException {
		JSONObject errorMessageJson = new JSONObject();
		
		HikariDataSource dataSource = null;
		Connection conn = null;
		Connection connBySystemName = null;
		byte buf[] = new byte[256];
		try {
			//获取规则信息
			String ruleJsonStr = userSqlite.getRuleJson();
			if(ruleJsonStr == null || ruleJsonStr.isEmpty()){
				errorMessageJson.put("status", "500");	//错误信息
				errorMessageJson.put("message", "无需要导出的规则信息");	//错误信息
				return errorMessageJson;
			}
			
			
			//创建压缩文件
			String dir = SystemProperties.getConfigRootPath() + "/tablesqlite";
			Path zipDirPath= Paths.get(dir);
			if(!Files.exists(zipDirPath)){
				Files.createDirectory(zipDirPath);
			}
			Path dbPath = Paths.get(dir, userSqlite.getSqliteCode() +".db");
//			String zipPathStr = SystemProperties.getConfigRootPath() + "/tablesqlite/" + userSqlite.getSqliteCode() +".zip";
//			Path zipPath = Paths.get(zipPathStr);
			
			String sqliteDB = userSqlite.getSqliteCode() + ".db";
			String dbpath = SystemProperties.getConfigRootPath() + "/tablesqlite/" + sqliteDB;
			Path filePath = Paths.get(dbpath);
			if(Files.exists(filePath)){
				//清空文件数据
				Files.write(filePath, "".getBytes());
			}
			
			//获取datasource
			String jdbcUrl = "jdbc:sqlite:" + dbpath;
			String jdbcDriverClass = "org.sqlite.JDBC";
			HikariConfig config = new HikariConfig();
			config.setDriverClassName(jdbcDriverClass);
			config.setJdbcUrl(jdbcUrl);
			config.setMaximumPoolSize(10);
			config.setMaxLifetime(1000L * 3600 * 8);
			dataSource = new HikariDataSource(config);
			
			//创建对应的表信息
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			JSONObject ruleJson = JSON.parseObject(ruleJsonStr);
			String type = ruleJson.getString("type");
			String message = "";
			if(type.equals("table")){
				JSONArray ruleAry = ruleJson.getJSONArray("data");
				message = createByTables(conn,connBySystemName,ruleAry);
			}else if(type.equals("model")){
				JSONArray ruleAry = ruleJson.getJSONArray("data");
				createByModel(conn,connBySystemName,ruleAry);
			}
					
			
			
			JdbcUtils.close(conn);
			
			errorMessageJson.put("status", "200");	//错误信息
			errorMessageJson.put("message", !message.isEmpty()?message:"生成成功");	//错误信息
			return errorMessageJson;
		} catch (Exception ex) {
			logger.error("export error", ex);
			errorMessageJson.put("message", ex.toString());	//错误信息
		} finally {
			// this.decreaseCounter(userId);
			JdbcUtils.close(connBySystemName);
			JdbcUtils.close(conn);
			if (dataSource != null) {
				dataSource.close();
			}
		}
		
		errorMessageJson.put("status", "500");	//错误信息
		return errorMessageJson;
	}
	
	public JSONObject importSqliteFile(UserSqliteUpload userSqliteUpload) throws IOException {
		JSONObject ret = new JSONObject();
		HikariDataSource dataSource = null;
		Connection sourceConn = null;	//源连接，在这里是sqlite文件的连接
		Connection targetConn = null;	//目标连接，在这里是本地库的连接
		BufferedWriter logos = null;
		String sql = "";
		
		if(userSqliteUpload == null){
			userSqliteUpload = new UserSqliteUpload();
			userSqliteUpload.setUserId("admin");
			userSqliteUpload.setFilePath("/tablesqlite/upload/sqlitedbinit.db");
		}
		
		try {
			String logpath = userSqliteUpload.getLogfilePath();
			String dirpath = "/tablesqlite/upload";
			if(logpath == null || logpath.isEmpty()){
				logpath = dirpath + "/" + UUID.randomUUID().toString() + ".log";
				userSqliteUpload.setLogfilePath(logpath);
			}
			logpath = SystemProperties.getConfigRootPath() + logpath;
			Path logPath = Paths.get(logpath);
			if(!Files.exists(logPath)){
				Files.createFile(logPath);
			}
			logos = Files.newBufferedWriter(logPath);
			
			logos.newLine();
			logos.newLine();
			logos.write("-------------------------------------------------------------");
			logos.newLine();
			logos.write(new Date().toString() + "\t用户："+userSqliteUpload.getUserId()+"进行sqlite文件导入操作!");
			logos.newLine();
			logos.write("sqlite文件导入的ID为："+ userSqliteUpload.getId());
			logos.newLine();
			logos.write(new Date().toString() + "\t开始导入操作！");
			logos.newLine();
			
			logos.write(new Date().toString() + "\t获取数据库连接！");
			logos.newLine();
			//创建连接，dataSource
			String dbpath = SystemProperties.getConfigRootPath() + userSqliteUpload.getFilePath();
			String jdbcUrl = "jdbc:sqlite:" + dbpath;
			String jdbcDriverClass = "org.sqlite.JDBC";
			HikariConfig config = new HikariConfig();
			config.setDriverClassName(jdbcDriverClass);
			config.setJdbcUrl(jdbcUrl);
			config.setMaximumPoolSize(10);
			config.setMaxLifetime(1000L * 3600 * 8);
			//创建连接，dataSource
			dataSource = new HikariDataSource(config);
			//获取导入的数据库连接信息
			sourceConn = dataSource.getConnection();
			sourceConn.setAutoCommit(false);
			
			logos.write(new Date().toString() + "\t获取sqlite文件的数据库连接成功！");
			logos.newLine();
			
			List<String> tablesName = DBUtils.getTables(sourceConn);
			//获取本地数据库的连接信息
			targetConn = DBConnectionFactory.getConnection("default");
			logos.write(new Date().toString() + "\t获取本地的数据库连接成功！");
			logos.newLine();
			
			logos.write(new Date().toString() + "\t获取表信息！");
			logos.newLine();
			
			//遍历所有表信息
			for(String tableName : tablesName){
				try{
					
					QueryRunner qr;
					List<Map<String, Object>> countlist;
					List<Map<String, Object>> sqliteRulelist;
					//获取对应表的导入规则信息
					qr = new QueryRunner(true);
					JSONObject ruleJson = new JSONObject();
					logos.write(new Date().toString() + "\t--------------开始获取该表的导入规则信息，表名为："+tableName);
					logos.newLine();
					sql = "SELECT * FROM TABLE_RULE_SQLITE_ WHERE upper(TABLENAME)='"+tableName.toUpperCase()+"'";
					logos.write(new Date().toString() + "\tSQL为："+sql);
					logos.newLine();
					sqliteRulelist = qr.query(sourceConn, sql, new MapListHandler());
					if(sqliteRulelist != null && sqliteRulelist.size() > 0){
						ruleJson = JSON.parseObject(sqliteRulelist.get(0).get("RULE").toString());
					}else{
						logos.write(new Date().toString() + "\t无法获取对应的导入规则信息，跳过，表名为："+tableName);
						logos.newLine();
						continue;
					}
					logos.write(new Date().toString() + "\t成功获取该表的导入规则信息，表名为："+tableName);
					logos.newLine();
					
					if(ruleJson.getInteger("importDataRule") == null){
						//无数据时插入
						logos.write(new Date().toString() + "\t无法获取对应的导入规则信息，跳过，表名为："+tableName);
						logos.newLine();
						continue;
					}
					
					//更新或创建表结构
					TableDefinition tableDefinition = JSON.parseObject(sqliteRulelist.get(0).get("COLUMNSDEF").toString(), TableDefinition.class);
					if (!DBUtils.tableExists(targetConn, tableName)) { // 表格不存在创建
						DBUtils.createTable(targetConn, tableDefinition);
					} else {
						DBUtils.alterTable(targetConn, tableDefinition);
					}
					
					if(ruleJson.getInteger("importDataRule") == 1){
						//无数据时插入
						logos.write(new Date().toString() + "\t进行\"无数据时插入！\"，表名为："+tableName);
						logos.newLine();
						
						//查询本地库是否有数据信息，无才插入，有不插入
						qr = new QueryRunner(true);
						countlist = qr.query(targetConn, "SELECT count(1) as count FROM " + tableName, new MapListHandler());
						if(countlist != null && countlist.size() > 0){
							Map countMap = countlist.get(0);
							Object count =  countMap.get("count");
							long countNum = 0;
							if(count != null){
								try{
									countNum = (long)count;
								}catch(ClassCastException e){
									try{
										countNum = ((BigDecimal)count).longValue();
									}catch(ClassCastException ex){
										countNum = (long)(int)count;
									}
								}
								if(countNum > 0){
									logos.write(new Date().toString() + "\t本地库中对应的表结构已存在数据，跳过，表名为："+tableName);
									logos.newLine();
									continue;
								}
							}
						}
					}else if(ruleJson.getInteger("importDataRule") == 3){
						
					}else if(ruleJson.getInteger("importDataRule") == 4){
						//覆盖导入
						//删除目标表信息
						logos.write(new Date().toString() + "\t开始进行覆盖导入，表名为："+tableName);
						logos.newLine();
						logos.write(new Date().toString() + "\t开始删除该表所有数据，表名为："+tableName);
						logos.newLine();
						
						sql = " delete from " + tableName;
						logos.write(new Date().toString() + "\t开始执行SQL语句："+sql);
						logos.newLine();
						try{
							BulkInsertBean bean = new BulkInsertBean();
							Statement statement = targetConn.createStatement();
							statement.executeUpdate(sql);
							JdbcUtils.close(statement);
						}catch(Exception e){
							
						}
						logos.write(new Date().toString() + "\t执行SQL语句成功，成功删除该表数据，表名为："+tableName);
						logos.newLine();
					}
					
					
					
					//设置表信息
	//				tableDefinition = new TableDefinition();
	//				tableDefinition.setTableName(tableName);
	//				tableDefinition.setName(tableName);
					
					//设置表字段信息
	//				List<ColumnDefinition> columnDefinitions = DBUtils.getColumnDefinitions(targetConn,tableName);
					List<ColumnDefinition> columnDefinitions = tableDefinition.getColumns();
					if(columnDefinitions == null || columnDefinitions.size() == 0){
						logos.write(new Date().toString() + "\t无法获取对应的表结构信息，跳过，表名为："+tableName);
						logos.newLine();
						continue;
					}else{
						logos.write(new Date().toString() + "\t开始插入数据，表名为："+tableName);
						logos.newLine();
					}
					String tableColumnsName = "";
					//主键信息
					List<String> idKeysArray = DBUtils.getPrimaryKeys(targetConn, tableName);
					String idColumnName = "";
					String idType = "";
					if(idKeysArray != null && idKeysArray.size() > 0){
						idColumnName = idKeysArray.get(0);
					}
					
					ColumnDefinition idColumnDefinition = null;
					List<String> blobKeyNameList = new ArrayList();
					List<String> tableColumnNameList = new ArrayList();
					//遍历字段信息，自动过滤二进制数据的字段。
					for(ColumnDefinition columnDefinition : columnDefinitions){
						if(!columnDefinition.getJavaType().equals("Blob")){
							if(!tableColumnsName.isEmpty()){
								tableColumnsName += ",";
							}
							tableColumnsName += columnDefinition.getColumnName();
							tableColumnNameList.add(columnDefinition.getColumnName());
						}else{
							blobKeyNameList.add(columnDefinition.getColumnName());
						}
						if(columnDefinition.getColumnName().toUpperCase().equals(idColumnName.toUpperCase())){
							idColumnDefinition = columnDefinition;
							idType = idColumnDefinition.getJavaType();
						}
					}
					tableDefinition.setColumns(columnDefinitions);
					
					if(ruleJson.getInteger("importDataRule") == 2){
						if(idColumnName == null || idColumnName.isEmpty()){
							logos.write(new Date().toString() + "\t该表无法获取主键信息，直接插入，表名为："+tableName);
							logos.newLine();
	//						logos.write(new Date().toString() + "\t该表无法获取主键信息，无法执行去重插入，跳过，表名为："+tableName);
	//						logos.newLine();
	//						continue;
						}
					}
					
					//源库数据查询(sqlite库)
					qr = new QueryRunner(true);
					countlist = qr.query(sourceConn, "SELECT count(1) as count FROM " + tableName, new MapListHandler());
					long maxCount = 0;
					if(countlist != null && countlist.size() > 0){
						Map countMap = countlist.get(0);
						Object count =  countMap.get("count");
						if(count != null){
							try{
								maxCount = (long)count;
							}catch(ClassCastException e){
								try{
									maxCount = ((BigDecimal)count).longValue();
								}catch(ClassCastException ex){
									maxCount = (long)(int)count;
								}
							}
						}
					}
					
					if(maxCount == 0){
						logos.write(new Date().toString() + "\t源库(导入文件中)中无任何数据，跳过，表名为："+tableName);
						logos.newLine();
						continue;
					}
					
					int maxPageCount = 900;
					
					if(maxCount > maxPageCount){
						for(int i = 0 ; i < maxCount; ){
							Dialect dialect = DBConfiguration.getDatabaseDialect(sourceConn);
	//						sql  = dialect.getLimitString("SELECT "+tableColumnsName+" FROM " + tableName, i, maxPageCount);
							sql  = dialect.getLimitString("SELECT * FROM " + tableName, i, maxPageCount);
							i += maxPageCount;
	//						qr = new QueryRunner(true);
	//						List<Map<String, Object>> datalist = qr.query(sourceConn, sql, new MapListHandler());
							
							//获取表数据信息
							List<Map<String, Object>> datalist = new ArrayList();
							PreparedStatement readps = sourceConn.prepareStatement(sql);
							ResultSet reader = readps.executeQuery();
							while(reader.next()){
								Map<String, Object> data = new HashMap<>();
				            	//遍历普通表名称，赋值
								for(String columnName : tableColumnNameList){
									data.put(columnName.toLowerCase(), reader.getObject(columnName));
								}
								//遍历二进制字节流的字段名,赋值
								for(String blobKeyName : blobKeyNameList){
									data.put(blobKeyName.toLowerCase(), reader.getBytes(blobKeyName));
								}
								datalist.add(data);
				            }
							
							List<Object> idArray = new ArrayList<>();
							if(ruleJson.getInteger("importDataRule") == 2 && (idColumnName != null && !idColumnName.isEmpty())){
								//过滤已存在的数据，插入未有的数据
								String idList = "";
								if(idColumnName != null && !idColumnName.isEmpty()){
									for(Map<String, Object> data : datalist){
										if(!idList.isEmpty()){
											idList += ",";
										}
										
										String value = data.get(idColumnName).toString();
										if(idType.equals("String")){
											idArray.add(value);
											value = "'" + value + "'";
										}else{
											try{
												idArray.add(Integer.parseInt(value));
											}catch(Exception e){
												idArray.add((int)Double.parseDouble(value));
											}
										}
										idList += value;
									}
									
									//查询已有的数据信息
									qr = new QueryRunner(true);
									sql = "SELECT "+idColumnName+" FROM " + tableName + " WHERE "+idColumnName+" in ("+idList+")";
	//								logos.write(new Date().toString() + "\t去掉重复主键，其SQL为："+sql);
	//								logos.newLine();
									countlist = qr.query(targetConn, sql, new MapListHandler());
									String repeatIdList = "";//重复主键
									if(countlist != null && countlist.size() > 0){
										//删除已有的数据信息
										for(Map<String, Object> data : countlist){
											String value = data.get(idColumnName).toString();
											int index = -1;
											if(idType.equals("String")){
												index = idArray.indexOf(value);
											}else{
												try{
													index = idArray.indexOf(Integer.parseInt(value));
												}catch(Exception e){
													index = idArray.indexOf((int)Double.parseDouble(value));
												}
											}
											
											if(index < 0){
												continue;
											}
											idArray.remove(index);
											datalist.remove(index);
											if(!repeatIdList.isEmpty()){
												repeatIdList += ",";
											}
											repeatIdList += value; 
										}
									}
								}
							}
							
							//插入数据
							targetConn.setAutoCommit(false);
							BulkInsertBean bean = new BulkInsertBean();
							bean.bulkInsert(targetConn, tableDefinition, datalist);
							targetConn.commit();
	//						JdbcUtils.close(targetConn);
							logos.write(new Date().toString() + "\t分页数据插入成功，表名为："+tableName);
							logos.newLine();
						}
					}else{
						//获取表数据信息
						List<Map<String, Object>> datalist = new ArrayList();
						List<Map<String, Object>> blobDatalist = new ArrayList();
						sql = "SELECT * FROM " + tableName;
						PreparedStatement readps = sourceConn.prepareStatement(sql);
						ResultSet reader = readps.executeQuery();
						while(reader.next()){
							Map<String, Object> data = new HashMap<>();
			            	//遍历普通表名称，赋值
							for(String columnName : tableColumnNameList){
								data.put(columnName.toLowerCase(), reader.getObject(columnName));
							}
							//遍历二进制字节流的字段名,赋值
							Map<String, Object> blobData = new HashMap<>();
							for(String blobKeyName : blobKeyNameList){
	//							blobData.put(idColumnName, reader.getObject(idColumnName));
	//							blobData.put(blobKeyName, reader.getBytes(blobKeyName));
								data.put(blobKeyName.toLowerCase(), reader.getBytes(blobKeyName));
	//							data.put(blobKeyName, reader.getBinaryStream(blobKeyName));
								//data.put(blobKeyName, reader.getBlob(blobKeyName));
							}
							blobDatalist.add(blobData);
							datalist.add(data);
			            }
						
						if(ruleJson.getInteger("importDataRule") == 2 && (idColumnName != null && !idColumnName.isEmpty())){
							//过滤已存在的数据，插入未有的数据
							String idList = "";
							List<Object> idArray = new ArrayList<>();
							if(idColumnName != null && !idColumnName.isEmpty()){
								for(Map<String, Object> data : datalist){
									if(!idList.isEmpty()){
										idList += ",";
									}
									
									String value = data.get(idColumnName).toString();
									if(idType.equals("String")){
										idArray.add(value);
										value = "'" + value + "'";
									}else{
										try{
											idArray.add(Integer.parseInt(value));
										}catch(Exception e){
											idArray.add((int)Double.parseDouble(value));
										}
									}
									idList += value;
								}
								
								//查询已有的数据信息
								qr = new QueryRunner(true);
								sql = "SELECT "+idColumnName+" FROM " + tableName + " WHERE "+idColumnName+" in ("+idList+")";
	//							logos.write(new Date().toString() + "\t去掉重复主键，其SQL为："+sql);
	//							logos.newLine();
								countlist = qr.query(targetConn, sql, new MapListHandler());
								String repeatIdList = "";//重复主键
								if(countlist != null && countlist.size() > 0){
									//删除已有的数据信息
									for(Map<String, Object> data : countlist){
										String value = data.get(idColumnName).toString();
										int index = -1;
										if(idType.equals("String")){
											index = idArray.indexOf(value);
										}else{
											try{
												index = idArray.indexOf(Integer.parseInt(value));
											}catch(Exception e){
												index = idArray.indexOf((int)Double.parseDouble(value));
											}
										}
										
										if(index < 0){
											continue;
										}
										idArray.remove(index);
										datalist.remove(index);
										if(!repeatIdList.isEmpty()){
											repeatIdList += ",";
										}
										repeatIdList += value; 
									}
								}
	//							logos.write(new Date().toString() + "\t去掉重复主键，其值为："+repeatIdList);
	//							logos.newLine();
							}
						}
						
						//插入数据
						targetConn.setAutoCommit(false);
						BulkInsertBean bean = new BulkInsertBean();
						bean.bulkInsert(targetConn, tableDefinition, datalist);
						targetConn.commit();
					}
					logos.write(new Date().toString() + "\t全部数据插入成功，插入总数为("+maxCount+")，表名为："+tableName);
					logos.newLine();
				}catch(Exception e){
					logos.write(new Date().toString() + "\t该表插入出现异常，跳过");
					logos.newLine();
					StringWriter sw=new StringWriter();  
			        PrintWriter pw=new PrintWriter(sw);  
			        e.printStackTrace(pw);
					logos.write(new Date().toString() + "\t异常信息为:" + sw.toString());
					logos.newLine();
				}
			}
			sourceConn.commit();
			ret.put("status", "200");	//错误信息
		}catch(Exception e){
			e.printStackTrace();
			logos.write(new Date().toString() + "\t出现异常信息");
			logos.newLine();
			logos.write(e.toString());
			logos.newLine();
			ret.put("status", "500");	//错误信息
		}finally {
			JdbcUtils.close(targetConn);
			JdbcUtils.close(sourceConn);
			if (dataSource != null) {
				dataSource.close();
			}
			if(logos != null){
				logos.close();
			}
		}
		return ret;
	}
	
}
