package com.glaf.etl.job;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.util.ExecutionLogFactory;
import com.glaf.etl.domain.EtlTransferTask;
import com.glaf.etl.query.EtlTransferTaskQuery;
import com.glaf.etl.service.EtlTransferTaskService;
import com.glaf.etl.task.RankTask;

public class RankForkJoinJob extends BaseJob {
	protected static AtomicLong lastExecuteTime = new AtomicLong(System.currentTimeMillis());

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		if ((System.currentTimeMillis() - lastExecuteTime.get()) < DateUtils.HOUR) {// 1小时
			return;
		}
		try {
			TimeUnit.SECONDS.sleep(3 + new Random().nextInt(120));// 随机等待，避免Job同时执行
		} catch (InterruptedException e) {
		}
		EtlTransferTaskService etlTransferTaskService = ContextFactory
				.getBean("com.glaf.etl.service.etlTransferTaskService");
		EtlTransferTaskQuery query = new EtlTransferTaskQuery();
		query.deleteFlag(0);
		query.setLocked(0);
		// 获取未锁定未删除的转换任务列表
		List<EtlTransferTask> list = etlTransferTaskService.list(query);
		// 获取任务源和目标信息
		Map<String, Map<String, String>> taskSrcTargetMap = etlTransferTaskService.getTaskSrcTargetMap(query);
		if (list != null && !list.isEmpty()) {
			int errorCount = 0;
			StringTokenizer token = null;
			Future<Boolean> future = null;
			ForkJoinPool forkJoinPool = null;
			RankTask task = null;
			List<ExecutionLog> logs = null;
			int runDay = DateUtils.getNowYearMonthDay();
			String srcIds = null;
			String targetId = null;
			String taskId = null;
			String jobNo = null;
			Map<String, String> srcTarget = null;
			for (EtlTransferTask etlTransferTask : list) {
				taskId = etlTransferTask.getId_();
				srcTarget = taskSrcTargetMap.get(etlTransferTask.getId_());
				for (Entry<String, String> entry : srcTarget.entrySet()) {
					srcIds = entry.getValue();
					targetId = entry.getKey();
					if (StringUtils.isNotEmpty(srcIds)) {
						errorCount = 0;
						forkJoinPool = new ForkJoinPool();
						// 更新任务锁定状态和开始时间时间
						etlTransferTaskService.updateTransferTaskStartStatus(etlTransferTask);
						try {
							logs = ExecutionLogFactory.getInstance().getTodayExecutionLogs("table_rank",
									etlTransferTask.getId_());
							token = new StringTokenizer(srcIds, ",");
							while (token.hasMoreTokens()) {
								String srcId = token.nextToken();
								if (StringUtils.isNotEmpty(srcId)) {
									try {
										/**
										 * 判断是否可以执行
										 */
										jobNo = "table_rank_" + taskId + "_" + srcId + "_" + targetId + "_" + runDay;
										if (!ExecutionLogFactory.getInstance().canExecution(logs, jobNo)) {
											continue;
										}
										task = new RankTask(srcId, targetId, etlTransferTask);
										future = forkJoinPool.submit(task);
										if (!future.get()) {
											errorCount++;
										}
									} catch (Exception ex) {
										errorCount++;
										logger.error(ex);
									}
								}
							}

						} catch (Exception ex) {
							logger.error(ex);
						}
						try {
							forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);
						} catch (InterruptedException ex) {
						}
						forkJoinPool.shutdown();
						if (errorCount == 0) {
							etlTransferTask.setSucceed_(1);
						} else {
							etlTransferTask.setSucceed_(-1);
						}
						etlTransferTaskService.updateTransferTaskStatus(etlTransferTask);
					}

				}
			}
		}
		lastExecuteTime.set(System.currentTimeMillis());
	}
}
