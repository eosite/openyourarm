package com.glaf.datamgr.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.http.HttpClientUtils;

/**
 * 定时 调用远程服务job
 * 
 * @author klaus.wang
 *
 */
public class EsiServiceRunJob extends BaseJob {

	protected static final Log logger = LogFactory.getLog(EsiServiceRunJob.class);

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		try {

			String key = "URL", stringVal = "stringVal";

			Map<String, Object> parameters = context.getJobDetail().getJobDataMap();

			if (MapUtils.isEmpty(parameters) || !parameters.containsKey(key)) {
				return;
			}

			JSONObject params = new JSONObject(parameters);
			parameters = params.getJSONObject(key);

			String url = MapUtils.getString(parameters, stringVal);
			if (StringUtils.isBlank(url)) {
				logger.error("调用服务地址不能为空!");
				return;
			}
			Map map = new HashMap();

			if (params.size() > 1) { // 其他参数
				for (String k : params.keySet()) {
					try {
						parameters = params.getJSONObject(k);
					} catch (Exception ex) {
						continue;
					}
					if (MapUtils.isEmpty(parameters) || StringUtils.equalsAnyIgnoreCase(k, key)) {
						continue;
					}
					map.put(MapUtils.getString(parameters, "keyName"), //
							MapUtils.getString(parameters, stringVal));
				}
			}

			Object result = HttpClientUtils.doPost(url, map);

			logger.debug(result);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
			throw ex;
		}
	}

}
