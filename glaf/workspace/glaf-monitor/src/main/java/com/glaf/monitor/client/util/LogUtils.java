package com.glaf.monitor.client.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.glaf.core.config.SystemProperties;

public class LogUtils {

	public LogUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取Log的Writer
	 * 
	 * @param monitorLog
	 * @param message
	 * @return
	 */
	public static BufferedWriter getProcLogWriter() {
		String projectpath = SystemProperties.getConfigRootPath();
		Path logFilePath = Paths.get(projectpath,"monitor","clientOperator.log");
		
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
	public  static void writeLog(BufferedWriter bufferWriter, String message) {
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
	public static void closeBufferedWriter(BufferedWriter bufferWriter) {
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
