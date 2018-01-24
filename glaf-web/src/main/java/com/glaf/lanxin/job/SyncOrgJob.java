package com.glaf.lanxin.job;

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
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.lanxin.service.LanxinSyncService;

public class SyncOrgJob extends BaseJob {

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
			try {
				running.set(true);
				LanxinSyncService lanxinSyncService = ContextFactory
						.getBean("com.glaf.lanxin.service.lanxinSyncService");
				JSONObject paramsMap = new JSONObject();
				BaseDataManager manager = BaseDataManager.getInstance();
				BaseDataInfo baseData = manager.getBaseData("lanxinInst", "lanxin");
				String baseId = baseData.getValue();
				// String tmpId="45f30e37188c45a7a5f7cf3e24c0bc24";
				baseData = manager.getBaseData("orgTmp", "lanxin");
				String tmpId = baseData.getValue();
				String targetDatabase = baseData.getExt1();
				if (StringUtils.isEmpty(targetDatabase)) {
					targetDatabase = null;
				}
				String targetTableName = baseData.getExt2();
				if (StringUtils.isEmpty(targetTableName)) {
					targetTableName = "LANXIN_ORG";
				}
				lanxinSyncService.sync(baseId, tmpId, targetDatabase, targetTableName, null, paramsMap);
			} catch (Exception ex) {
				logger.error(ex);
			} finally {
				running.set(false);
			}
		}
	}

	public void runJob(JobExecutionContext context) throws JobExecutionException {

	}

}
