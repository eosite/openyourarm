package com.glaf.monitor.server.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.glaf.core.config.SystemProperties;
import com.glaf.monitor.server.domain.MonitorLog;
import com.glaf.monitor.server.domain.MonitorProc;
import com.glaf.monitor.server.domain.MonitorTerminal;
import com.glaf.monitor.server.query.MonitorLogQuery;
import com.glaf.monitor.server.service.MonitorLogService;

@Component("com.glaf.monitor.server.util.monitorServerLogUtils")
public class MonitorLogUtils {

	@Autowired
	protected MonitorLogService monitorLogService;
	
	public MonitorLogUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public MonitorLog getOrSave(MonitorTerminal monitorTerminal){
		//获取对应的日志文件，或生成新的日志信息
		MonitorLog monitorLog = null;
		MonitorLogQuery monitorLogQuery = new MonitorLogQuery();
		monitorLogQuery.setObjectId(monitorTerminal.getId());
		monitorLogQuery.setObjectType("TERMINAL");
		List<MonitorLog> list = monitorLogService.list(monitorLogQuery);
		if(list != null && list.size() >0){
			monitorLog = list.get(0);
		}else{
			monitorLog = new MonitorLog();
			String monitorLogId = monitorLogService.createId();
			monitorLog.setId(monitorLogId);
			monitorLog.setObjectId(monitorTerminal.getId());
			monitorLog.setObjectType("TERMINAL");
			String projectpath = SystemProperties.getConfigRootPath();
			String logFilePath = projectpath + File.separator + "monitor" +File.separator + monitorLogId + ".log";
			monitorLog.setLogPath(logFilePath.toString());
			monitorLogService.add(monitorLog);
		}
		return monitorLog;
	}
	
	public MonitorLog getOrSave(MonitorProc monitorProc){
		//获取对应的日志文件，或生成新的日志信息
		MonitorLog monitorLog = null;
		MonitorLogQuery monitorLogQuery = new MonitorLogQuery();
		monitorLogQuery.setObjectId(monitorProc.getId());
		monitorLogQuery.setObjectType("PROC");
		List<MonitorLog> list = monitorLogService.list(monitorLogQuery);
		if(list != null && list.size() >0){
			monitorLog = list.get(0);
		}else{
			monitorLog = new MonitorLog();
			String monitorLogId = monitorLogService.createId();
			monitorLog.setId(monitorLogId);
			monitorLog.setObjectId(monitorProc.getId());
			monitorLog.setObjectType("PROC");
			String projectpath = SystemProperties.getConfigRootPath();
			String logFilePath = projectpath + File.separator + "monitor" +File.separator + monitorLogId + ".log";
			monitorLog.setLogPath(logFilePath.toString());
			monitorLogService.add(monitorLog);
		}
		return monitorLog;
	}
	
	public void saveProcLog(MonitorLog monitorLog,String message){
		Path logFilePath = Paths.get(monitorLog.getLogPath());
		try{
			Path dirPath = logFilePath.getParent();
			if(!Files.exists(dirPath)){
				Files.createDirectory(dirPath);
			}
			if(!Files.exists(logFilePath)){
				Files.createFile(logFilePath);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try(BufferedWriter bufferWriter = Files.newBufferedWriter(logFilePath,StandardCharsets.UTF_8, StandardOpenOption.APPEND);){
				bufferWriter.write("\n");bufferWriter.write("\n");
				bufferWriter.write(sdf.format(new Date()) + "操作\t");
				bufferWriter.write(message);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取Log的Writer
	 * 
	 * @param monitorLog
	 * @param message
	 * @return
	 */
	public BufferedWriter getLogWriter(MonitorTerminal monitorTerminal) {
		MonitorLog monitorLog = getOrSave(monitorTerminal);
		if (monitorLog == null) {
			return null;
		}
		Path logFilePath = Paths.get(monitorLog.getLogPath());
		try {
			Path dirPath = logFilePath.getParent();
			if (!Files.exists(dirPath)) {
				Files.createDirectory(dirPath);
			}
			if (!Files.exists(logFilePath)) {
				Files.createFile(logFilePath);
			}

			BufferedWriter bufferWriter = Files.newBufferedWriter(logFilePath, StandardCharsets.UTF_8,
					StandardOpenOption.APPEND);
			return bufferWriter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取Log的Writer
	 * 
	 * @param monitorLog
	 * @param message
	 * @return
	 */
	public BufferedWriter getLogWriter(MonitorProc monitorProc) {
		MonitorLog monitorLog = getOrSave(monitorProc);
		if (monitorLog == null) {
			return null;
		}
		Path logFilePath = Paths.get(monitorLog.getLogPath());
		try {
			Path dirPath = logFilePath.getParent();
			if (!Files.exists(dirPath)) {
				Files.createDirectory(dirPath);
			}
			if (!Files.exists(logFilePath)) {
				Files.createFile(logFilePath);
			}

			BufferedWriter bufferWriter = Files.newBufferedWriter(logFilePath, StandardCharsets.UTF_8,
					StandardOpenOption.APPEND);
			return bufferWriter;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写日志
	 * 
	 * @param bufferWriter
	 * @param message
	 */
	public void writeLog(BufferedWriter bufferWriter, String message) {
		if (bufferWriter == null) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			bufferWriter.write("\n");
			bufferWriter.write(sdf.format(new Date()) + "\t" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关闭
	 * 
	 * @param bufferWriter
	 */
	public void closeBufferedWriter(BufferedWriter bufferWriter) {
		try {
			if (bufferWriter != null) {
				bufferWriter.flush();
				bufferWriter.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
