package com.glaf.monitor.client.util;

import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.glaf.monitor.client.job.GetClientInfoJob;

/**
 * 监听任务
 * @author ASUS
 *
 */
@Component
public class MonitorTask {
	protected static boolean isrunning = false;
	
	public MonitorTask() {
		// TODO Auto-generated constructor stub
	}
	
	/**  
     * 心跳更新。启动时执行一次，之后每隔2分钟执行一次  
     */    
    @Scheduled(fixedDelay = 120000)   
    public void getClientInfoTask(){  
    	GetClientInfoJob job = new GetClientInfoJob();
    	try {
			job.runJob(null);
		} catch (JobExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        System.out.println("Annotation：print run");  
    } 
}
