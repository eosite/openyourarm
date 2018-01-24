package com.glaf.lanxin.job;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.lanxin.service.LanxinSyncService;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.service.EimBaseInfoService;
import com.glaf.teim.service.EimServerTmpService;

public class SyncMemberJob extends BaseJob {

	protected static Configuration conf = BaseConfiguration.create();

	protected static AtomicBoolean running = new AtomicBoolean(false);

	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.MINUTE * 1) {
			return;
		}
		logger.debug("--------------------开始执行接口数据同步任务-----------------------------");
		if (!running.get()) {
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			try {
				running.set(true);
				LanxinSyncService lanxinSyncService = ContextFactory
						.getBean("com.glaf.lanxin.service.lanxinSyncService");
				JSONObject paramsMap = new JSONObject();
				BaseDataManager manager = BaseDataManager.getInstance();
				BaseDataInfo baseData = manager.getBaseData("lanxinInst", "lanxin");
				String baseId = baseData.getValue();
				// String tmpId = "b916b20d6a7e4c5db1e6589dacf89e11";
				baseData = manager.getBaseData("memberTmp", "lanxin");
				String tmpId = baseData.getValue();
				String targetDatabase=baseData.getExt1();
				if(StringUtils.isEmpty(targetDatabase)){
					targetDatabase=null;
				}
				String targetTableName=baseData.getExt2();
				if(StringUtils.isEmpty(targetTableName)){
					targetTableName="LANXIN_MEMBER";
				}
				// 获取所有分支
				conn = DBConnectionFactory.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery("select distinct id from LANXIN_BRANCH where type=0");
				long branchId = 0;
				EimBaseInfoService baseInfoService = ContextFactory.getBean("com.glaf.teim.service.eimBaseInfoService");
				EimBaseInfo baseInfo = baseInfoService.getEimBaseInfo(baseId);
				if (baseInfo == null) {
					logger.error("未找到应用实例" + baseId);
					return;
				}
				EimServerTmpService tmpService = ContextFactory.getBean("com.glaf.teim.service.eimServerTmpService");
				EimServerTmp tmp = tmpService.getEimServerTmp(tmpId);
				if (tmp == null) {
					logger.error("未找到服务模板" + tmpId);
					return;
				}
				while (rs.next()) {
					branchId = rs.getLong(1);
					paramsMap.put("structId", branchId);
					paramsMap.put("currentPage", "1");
					lanxinSyncService.sync(baseInfo, tmp, targetDatabase, targetTableName, null, paramsMap);
				}

			} catch (Exception ex) {
				logger.error(ex);
				ex.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (stm != null)
						stm.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				running.set(false);
			}
		}
	}

	public void runJob(JobExecutionContext context) throws JobExecutionException {

	}

}
