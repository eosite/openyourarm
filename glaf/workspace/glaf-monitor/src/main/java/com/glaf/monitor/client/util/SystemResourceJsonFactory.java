package com.glaf.monitor.client.util;

import com.alibaba.fastjson.JSONObject;
import com.glaf.monitor.client.domain.SystemResource;

public class SystemResourceJsonFactory {
	
	public static JSONObject toJsonObject(SystemResource model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userName", model.getUserName());
		jsonObject.put("computerName", model.getComputerName());
		jsonObject.put("jvmName", model.getJvmName());
		if (model.getJvmVersion() != null) {
			jsonObject.put("jvmVersion", model.getJvmVersion());
		} 
		
		jsonObject.put("cpuProcessors", model.getCpuProcessors());
		
		if (model.getCpuVendor() != null) {
			jsonObject.put("cpuVendor", model.getCpuVendor());
		} 
		if (model.getCpuModel() != null) {
			jsonObject.put("cpuModel", model.getCpuModel());
		} 
		
		jsonObject.put("cpuMHZ", model.getCpuMHZ());
		jsonObject.put("memTotal", model.getMemTotal());
		jsonObject.put("memUsed", model.getMemUsed());
		jsonObject.put("memFree", model.getMemFree());
		jsonObject.put("swapTotal", model.getSwapTotal());
		jsonObject.put("swapUsed", model.getSwapUsed());
		jsonObject.put("swapFree", model.getSwapFree());

		if (model.getJavaVersion() != null) {
			jsonObject.put("javaVersion", model.getJavaVersion());
		}
		if (model.getJavaHome() != null) {
			jsonObject.put("javaHome", model.getJavaHome());
		}
		if (model.getOs() != null) {
			jsonObject.put("os", model.getOs());
		}
		if (model.getPlatform() != null) {
			jsonObject.put("platform", model.getPlatform());
		}
                
		return jsonObject;
	}
}
