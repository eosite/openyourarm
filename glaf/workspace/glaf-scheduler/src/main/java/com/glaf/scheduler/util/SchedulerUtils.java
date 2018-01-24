/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.scheduler.util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.glaf.core.base.Scheduler;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.scheduler.service.ISchedulerService;

public class SchedulerUtils {
	protected final static Log logger = LogFactory.getLog(SchedulerUtils.class);

	protected static ConcurrentMap<String, String> jobCache = new ConcurrentHashMap<String, String>();

	protected static Configuration conf = BaseConfiguration.create();

	protected static volatile org.quartz.Scheduler scheduler;

	protected static volatile ISchedulerService schedulerService;

	public static org.quartz.Scheduler getQuartzScheduler() {
		if (scheduler == null) {
			scheduler = ContextFactory.getBean("scheduler");
			// org.quartz.SchedulerFactory sf = new
			// org.quartz.impl.StdSchedulerFactory();
			// try {
			// scheduler = sf.getScheduler();
			// scheduler.start();
			// logger.info("------- Initialization Complete --------");
			// logger.info("------- Scheduling Jobs ----------------");
			// } catch (org.quartz.SchedulerException ex) {
			// ex.printStackTrace();
			// }
		}
		return scheduler;
	}

	public static Collection<String> getRunningTaskIds() {
		return jobCache.keySet();
	}

	public static ISchedulerService getSchedulerService() {
		if (schedulerService == null) {
			schedulerService = ContextFactory.getBean("com.glaf.scheduler.service.schedulerService");
		}
		return schedulerService;
	}

	@SuppressWarnings("unchecked")
	public static void printSchedulerStatus() throws Exception {
		scheduler = getQuartzScheduler();
		List<JobExecutionContext> jobs = scheduler.getCurrentlyExecutingJobs();
		if (jobs != null && !jobs.isEmpty()) {
			for (JobExecutionContext job : jobs) {
				logger.info("------------" + job.getClass().getName() + "------------");
				logger.info("fireInstanceId:" + job.getFireInstanceId());
				logger.info("jobRunTime:" + job.getJobRunTime());
				logger.info("fireTime:" + DateUtils.getDateTime(job.getFireTime()));
				logger.info("nextFireTime:" + DateUtils.getDateTime(job.getNextFireTime()));
				logger.info("previousFireTime:" + DateUtils.getDateTime(job.getPreviousFireTime()));
				logger.info("result:" + job.getResult());
			}
		}

		List<String> jobGroupNames = scheduler.getJobGroupNames();
		if (jobGroupNames != null && !jobGroupNames.isEmpty()) {
			for (String jobGroupName : jobGroupNames) {
				logger.info("jobGroupName:" + jobGroupName);
				List<Scheduler> list = getSchedulerService().getAllSchedulers();
				Iterator<Scheduler> iterator = list.iterator();
				while (iterator.hasNext()) {
					Scheduler model = iterator.next();
					String jobName = "JOBX_" + model.getId();
					JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
					JobDetail jobDetail = scheduler.getJobDetail(jobKey);
					logger.info("taskId:" + jobDetail.getJobDataMap().getString("taskId"));
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					if (triggers != null && !triggers.isEmpty()) {
						for (Trigger trigger : triggers) {
							logger.info(model.getTaskName() + "->" + trigger.getKey().getName());
							logger.info("previousFireTime:" + DateUtils.getDateTime(trigger.getPreviousFireTime()));
							logger.info("nextFireTime:" + DateUtils.getDateTime(trigger.getNextFireTime()));
						}
					}
				}
			}
		}

	}

	/**
	 * 启动调度任务
	 * 
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public static void restart(String taskId) {
		logger.debug("taskId=" + taskId);
		Scheduler model = getSchedulerService().getSchedulerByTaskId(taskId);
		if (model != null && model.isValid()) {
			if (StringUtils.isEmpty(model.getJobClass()) && StringUtils.isNotEmpty(model.getSpringBeanId())) {
				logger.debug("设置SpringJob");
				model.setJobClass(conf.get("GeneralSpringJob", "com.glaf.core.job.GeneralSpringJob"));
				logger.debug("jobClass:" + model.getJobClass());
			}
			logger.debug("scheduler:" + model.toJsonObject().toJSONString());
			try {
				if (getQuartzScheduler() != null && StringUtils.isNotEmpty(model.getJobClass())) {
					JobDataMap jobDataMap = new JobDataMap();
					jobDataMap.put("taskId", taskId);

					Class<?> clazz = ClassUtils.loadClass(model.getJobClass());

					Class<Job> jobClass = (Class<Job>) clazz;

					String jobName = "JOBX_" + model.getId();
					String jobGroup = "EX_JOB_GROUP";
					String triggerName = "TRIGGERX_" + model.getId();
					String triggerGroup = "EX_TRIGGER_GROUP";

					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

					JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).usingJobData(jobDataMap)
							.build();

					Trigger trigger = null;
					boolean startup = false;

					logger.debug("------------jobKey----------- " + jobKey);

					if (StringUtils.isNotEmpty(model.getExpression())) {
						trigger = getQuartzScheduler().getTrigger(triggerKey);
						if (trigger == null) {
							logger.debug("------------create new CronTrigger----------- ");
							trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
									.withSchedule(CronScheduleBuilder.cronSchedule(model.getExpression())).build();

							getQuartzScheduler().scheduleJob(jobDetail, trigger);
							startup = true;
							logger.info(model.getTaskName() + " has scheduled.");
							logger.info(model.getTaskName() + " next fire time:"
									+ DateUtils.getDateTime(trigger.getNextFireTime()));
							if (startup) {
								model.setStartup(1);
								schedulerService.save(model);
								logger.info(model.getTaskName() + " has startup.");
							}
						} else {
							if (trigger instanceof CronTrigger) {
								logger.debug("------------update CronTrigger------------- ");
								CronTrigger cronTrigger = (CronTrigger) trigger;
								cronTrigger = cronTrigger.getTriggerBuilder()
										.withSchedule(CronScheduleBuilder.cronSchedule(model.getExpression())).build();

								getQuartzScheduler().rescheduleJob(triggerKey, cronTrigger);
								startup = true;
								logger.info(model.getTaskName() + " has rescheduled.");
								logger.info(model.getTaskName() + " next fire time:"
										+ DateUtils.getDateTime(trigger.getNextFireTime()));
								if (startup) {
									model.setStartup(1);
									schedulerService.save(model);
									logger.info(model.getTaskName() + " has startup.");
								}
							} else {
								getQuartzScheduler().deleteJob(jobKey);
							}
						}
					} else {
						trigger = getQuartzScheduler().getTrigger(triggerKey);
						if (trigger == null) {
							logger.debug("------------create new SimpleTrigger----------- ");
							if (model.getRepeatCount() == -1) {
								model.setRepeatCount(Integer.MAX_VALUE);
							}
							trigger = TriggerBuilder.newTrigger().startAt(model.getStartDate())
									.endAt(model.getEndDate()).forJob(jobKey).withIdentity(triggerKey)
									.withSchedule(SimpleScheduleBuilder.simpleSchedule()
											.withIntervalInSeconds(model.getRepeatInterval())
											.withRepeatCount(model.getRepeatCount()))
									.build();

							getQuartzScheduler().scheduleJob(jobDetail, trigger);
							startup = true;
							logger.debug("repeatInterval:" + model.getRepeatInterval());
							logger.debug(model.getTaskName() + " has scheduled.");
							logger.info(model.getTaskName() + " next fire time:"
									+ DateUtils.getDateTime(trigger.getNextFireTime()));
							if (startup) {
								model.setStartup(1);
								getSchedulerService().save(model);
								logger.info(model.getTaskName() + " has startup.");
							}
						} else {
							if (trigger instanceof SimpleTrigger) {
								logger.debug("------------update SimpleTrigger----------- ");
								SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
								if (model.getRepeatCount() == -1) {
									model.setRepeatCount(Integer.MAX_VALUE);
								}
								simpleTrigger = simpleTrigger.getTriggerBuilder().startAt(model.getStartDate())
										.endAt(model.getEndDate()).forJob(jobKey).withIdentity(triggerKey)
										.withSchedule(SimpleScheduleBuilder.simpleSchedule()
												.withIntervalInSeconds(model.getRepeatInterval())
												.withRepeatCount(model.getRepeatCount()))
										.build();
								getQuartzScheduler().rescheduleJob(triggerKey, simpleTrigger);
								startup = true;
								logger.info(model.getTaskName() + " has rescheduled.");
								logger.info(model.getTaskName() + " next fire time:"
										+ DateUtils.getDateTime(trigger.getNextFireTime()));
								if (startup) {
									model.setStartup(1);
									getSchedulerService().save(model);
									logger.info(model.getTaskName() + " has startup.");
								}
							} else {
								getQuartzScheduler().deleteJob(jobKey);
							}
						}
					}
				}
			} catch (org.quartz.SchedulerException ex) {
				logger.error(ex);
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} catch (Exception ex) {
				logger.error(ex);
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 立即执行调度任务
	 * 
	 * @param taskId
	 */
	@SuppressWarnings("unchecked")
	public static void runJob(String taskId) {
		Scheduler model = getSchedulerService().getSchedulerByTaskId(taskId);
		if (model != null && model.isValid()) {
			if (StringUtils.isEmpty(model.getJobClass()) && StringUtils.isNotEmpty(model.getSpringBeanId())) {
				logger.debug("设置SpringJob");
				model.setJobClass(conf.get("GeneralSpringJob", "com.glaf.core.job.GeneralSpringJob"));
				logger.debug("jobClass:" + model.getJobClass());
			}
			logger.debug("scheduler:" + model.toJsonObject().toJSONString());
			try {
				if (getQuartzScheduler() != null && StringUtils.isNotEmpty(model.getJobClass())) {
					JobDataMap jobDataMap = new JobDataMap();
					jobDataMap.put("taskId", taskId);

					Class<?> clazz = ClassUtils.loadClass(model.getJobClass());

					Class<Job> jobClass = (Class<Job>) clazz;

					String jobName = "JOB2X_" + model.getId();
					String jobGroup = "EX_JOB_GROUP2";
					String triggerName = "TRIGGER2X_" + model.getId();
					String triggerGroup = "EX_TRIGGER_GROUP2";

					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);

					JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobKey).usingJobData(jobDataMap)
							.build();

					Trigger trigger = null;

					logger.debug("------------jobKey----------- " + jobKey);

					trigger = getQuartzScheduler().getTrigger(triggerKey);
					if (trigger == null) {
						logger.info("------------create new SimpleTrigger----------- ");
						trigger = TriggerBuilder.newTrigger().startAt(new Date())
								.endAt(new Date(System.currentTimeMillis() + 60000)).forJob(jobKey)
								.withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule()
										.withIntervalInSeconds(1000).withRepeatCount(1))
								.build();

						getQuartzScheduler().scheduleJob(jobDetail, trigger);
						logger.info(model.getTaskName() + " has scheduled.");
					} else {
						if (trigger instanceof SimpleTrigger) {
							logger.info("------------update SimpleTrigger----------- ");
							SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
							simpleTrigger = simpleTrigger.getTriggerBuilder().startAt(new Date())
									.endAt(new Date(System.currentTimeMillis() + 60000)).forJob(jobKey)
									.withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule()
											.withIntervalInSeconds(1000).withRepeatCount(1))
									.build();
							getQuartzScheduler().rescheduleJob(triggerKey, simpleTrigger);
							logger.info(model.getTaskName() + " has rescheduled.");
						}
					}
				}
			} catch (org.quartz.SchedulerException ex) {
				logger.error(ex);
				ex.printStackTrace();
				throw new RuntimeException(ex);
			} catch (Exception ex) {
				logger.error(ex);
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 关闭全部调度任务，同时关闭调度引擎Quartz。
	 */
	public static void shutdown() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
		try {
			if (getQuartzScheduler() != null) {
				logger.warn("------------------------------------------------");
				logger.warn("shutdown scheduler!!!");
				getQuartzScheduler().shutdown(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
	}

	/**
	 * 启动全部调度任务
	 */
	public static void startup() {
		if (getQuartzScheduler() != null) {
			List<Scheduler> list = getSchedulerService().getAllSchedulers();
			logger.debug("schedule list:" + list.size());
			Iterator<Scheduler> iterator = list.iterator();
			while (iterator.hasNext()) {
				Scheduler model = iterator.next();
				if (model.isValid() && model.isSchedulerAutoStartup()) {
					try {
						model.setRunStatus(0);// 设置调度运行状态为未运行
						getSchedulerService().update(model);
						restart(model.getId());
						jobCache.put(model.getId(), model.getId());
						Thread.sleep(1000);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex);
					}
				}
			}
		}
	}

	/**
	 * 停止调度任务
	 * 
	 * @param taskId
	 */
	public static void stop(String taskId) {
		Scheduler model = getSchedulerService().getSchedulerByTaskId(taskId);
		if (model != null) {
			try {
				if (getQuartzScheduler() != null) {
					String jobName = "JOBX_" + model.getId();
					String jobGroup = "EX_JOB_GROUP";
					String triggerName = "TRIGGERX_" + model.getId();
					String triggerGroup = "EX_TRIGGER_GROUP";

					JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
					if (getQuartzScheduler().checkExists(jobKey)) {
						TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
						getQuartzScheduler().unscheduleJob(triggerKey);
						getQuartzScheduler().pauseJob(jobKey);
						getQuartzScheduler().deleteJob(jobKey);
						jobCache.remove(model.getId());
						logger.info(model.getTaskName() + " has delete!!!");
					}
					model.setStartup(0);
					schedulerService.save(model);
					logger.info(model.getTaskName() + " has stop.");
				}
			} catch (org.quartz.SchedulerException ex) {
				ex.printStackTrace();
				logger.error(ex);
				throw new RuntimeException(ex);
			}
		}
	}

	/**
	 * 停止全部调度任务，但不关闭调度引擎Quartz。
	 */
	public static void stopAll() {
		List<Scheduler> list = getSchedulerService().getAllSchedulers();
		Iterator<Scheduler> iterator = list.iterator();
		while (iterator.hasNext()) {
			Scheduler model = iterator.next();
			try {
				stop(model.getId());
				jobCache.remove(model.getId());
			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error(ex);
			}
		}
	}

	private SchedulerUtils() {

	}

}
