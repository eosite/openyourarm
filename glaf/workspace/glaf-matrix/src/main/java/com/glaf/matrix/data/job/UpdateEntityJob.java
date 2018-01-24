package com.glaf.matrix.data.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.config.SystemProperties;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.StringTools;
import com.glaf.matrix.data.domain.UpdateEntity;
import com.glaf.matrix.data.util.XmlReader;

public class UpdateEntityJob extends BaseJob {

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	protected static AtomicBoolean running = new AtomicBoolean(false);

	@Override
	public void runJob(JobExecutionContext context) throws JobExecutionException {
		logger.debug("--------------------UpdateEntityJob-------------------------");
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.SECOND * 10) {
			return;
		}

		if (!running.get()) {
			XmlReader xmlReader = new XmlReader();
			List<UpdateEntity> tableModels = null;
			InputStream inputStream = null;
			try {
				running.set(true);
				String config = SystemProperties.getConfigRootPath() + "/conf/matrix/update";
				File directory = new File(config);
				if (directory.exists() && directory.isDirectory()) {
					File[] filelist = directory.listFiles();
					if (filelist != null) {
						for (int i = 0, len = filelist.length; i < len; i++) {
							File file = filelist[i];
							if (file.isFile() && file.getName().endsWith(".xml")) {
								java.sql.Connection conn = null;
								java.sql.PreparedStatement psmt = null;
								java.sql.ResultSet rs = null;
								try {
									inputStream = new FileInputStream(file);
									tableModels = xmlReader.read(inputStream);
									if (tableModels != null && !tableModels.isEmpty()) {
										for (UpdateEntity model : tableModels) {
											if (StringUtils.isNotEmpty(model.getUpdateSql())) {
												if (StringUtils.isNotEmpty(model.getSystemName())) {
													conn = DBConnectionFactory.getConnection(model.getSystemName());
												} else {
													conn = DBConnectionFactory.getConnection();
												}

												String updateSql = " insert into SYS_UPDATE_ENTITY(LASTEXECUTETIME_, ID_) values(?, ?) ";
												long ts = 0;
												String sql = " select LASTEXECUTETIME_ from SYS_UPDATE_ENTITY where ID_ = ? ";
												psmt = conn.prepareStatement(sql);
												psmt.setString(1, model.getId());
												rs = psmt.executeQuery();
												if (rs.next()) {
													ts = rs.getLong(1);
												}
												JdbcUtils.close(rs);
												JdbcUtils.close(psmt);
												if (ts > 0) {
													/**
													 * 本次执行时间间隔未到
													 */
													if (System.currentTimeMillis() - ts < model.getIntervalSeconds()
															* 1000) {
														continue;
													}
													updateSql = " update SYS_UPDATE_ENTITY set LASTEXECUTETIME_ = ? where ID_ = ? ";
												} else {
													ts = System.currentTimeMillis();
													updateSql = " insert into SYS_UPDATE_ENTITY(LASTEXECUTETIME_, ID_) values(?, ?) ";
												}

												conn.setAutoCommit(false);
												StringTokenizer tokenizer = new StringTokenizer(model.getUpdateSql(),
														";");
												while (tokenizer.hasMoreTokens()) {
													String sqlStatement = tokenizer.nextToken();
													if (StringUtils.isNotEmpty(sqlStatement)
															&& !sqlStatement.startsWith("#")) {
														if (StringUtils.contains(sqlStatement, "#{lastExecuteTime}")) {
															sqlStatement = StringTools.replace(sqlStatement,
																	"#{lastExecuteTime}", "?");
														} else if (StringUtils.contains(sqlStatement,
																"#{previousAnHour}")) {
															sqlStatement = StringTools.replace(sqlStatement,
																	"#{previousAnHour}", "?");
														} else if (StringUtils.contains(sqlStatement,
																"#{previousDay}")) {
															sqlStatement = StringTools.replace(sqlStatement,
																	"#{previousDay}", "?");
														}
														psmt = conn.prepareStatement(sqlStatement);
														if (StringUtils.contains(sqlStatement, "#{lastExecuteTime}")) {
															psmt.setTimestamp(1, DateUtils.toTimestamp(new Date(ts)));
														} else if (StringUtils.contains(sqlStatement,
																"#{previousAnHour}")) {
															psmt.setTimestamp(1, DateUtils.toTimestamp(new Date(
																	System.currentTimeMillis() - DateUtils.HOUR)));
														} else if (StringUtils.contains(sqlStatement,
																"#{previousDay}")) {
															psmt.setTimestamp(1, DateUtils.toTimestamp(new Date(
																	System.currentTimeMillis() - DateUtils.DAY)));
														}
														psmt.executeUpdate();
														JdbcUtils.close(psmt);
													}
												}

												psmt = conn.prepareStatement(updateSql);
												psmt.setLong(1, ts);
												psmt.setString(2, model.getId());
												psmt.executeUpdate();
												JdbcUtils.close(psmt);
												conn.commit();
												JdbcUtils.close(conn);
											}
										}
									}
								} catch (Exception ex) {
									// throw new RuntimeException(ex);
								} finally {
									JdbcUtils.close(rs);
									JdbcUtils.close(psmt);
									JdbcUtils.close(conn);
									IOUtils.closeStream(inputStream);
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				running.set(false);
				IOUtils.closeStream(inputStream);
			}
		}

		lastExecuteTime.set(System.currentTimeMillis());
	}

}
