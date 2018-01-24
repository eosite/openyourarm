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

public class SyncBranchJob  extends BaseJob {

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
				LanxinSyncService lanxinSyncService = ContextFactory.getBean("com.glaf.lanxin.service.lanxinSyncService");
				JSONObject paramsMap=new JSONObject();
				BaseDataManager manager= BaseDataManager.getInstance();
				BaseDataInfo baseData = manager.getBaseData("lanxinInst", "lanxin");
				String baseId = baseData.getValue();
				//String tmpId="d3331d514ead4d3383c166aac0894515";
				baseData = manager.getBaseData("branchTmp", "lanxin");
				String tmpId = baseData.getValue();
				paramsMap.put("orgId", "3");
				paramsMap.put("structId", "0");
				String targetDatabase=baseData.getExt1();
				if(StringUtils.isEmpty(targetDatabase)){
					targetDatabase=null;
				}
				String targetTableName=baseData.getExt2();
				if(StringUtils.isEmpty(targetTableName)){
					targetTableName="LANXIN_BRANCH";
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
