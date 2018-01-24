package com.glaf.monitor.server.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;

public class MonitorCacheUtil {
	static Map<String, Map<String,Object>> monitorTerminal = new HashMap<String, Map<String,Object>>();
	static Map<String, Map<String,Object>> monitorProc = new HashMap<String, Map<String,Object>>();
	static int maxNum = 10;	//最多可保留多少次数据
	
	public MonitorCacheUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取终端数据(共10次数据)
	 * @param id
	 * @return
	 */
	public static JSONArray getMonitorTerminalData(String id){
		if(monitorTerminal.containsKey(id)){
			return (JSONArray) monitorTerminal.get(id).get("datas");
		}
		return null;
	}
	
	/**
	 * 获取最后一次终端数据
	 * @param id
	 * @return
	 */
	public static JSONObject getLastMonitorTerminalData(String id){
		Map<String,Object> obj = null; 
		if(monitorTerminal.containsKey(id)){
			obj = monitorTerminal.get(id);
			AtomicLong lastTime = (AtomicLong) obj.get("lastTime");
			if((System.currentTimeMillis() - lastTime.get()) > DateUtils.MINUTE * 2){
				//2分钟内无响应,返回null
				return null;
			}
			
			JSONArray array = (JSONArray) monitorTerminal.get(id).get("datas");
			if(array != null && array.size() > 0){
				JSONObject obj2 = array.getJSONObject(array.size()-1);
				return obj2;
			}
		}
		return null;
	}
	
	/**
	 * 设置终端数据
	 * @param id
	 * @param data
	 * @return
	 */
	public static void setMonitorTerminalData(String id,JSONObject data){
		//获取缓存数据
		Map<String,Object> obj = null; 
		if(monitorTerminal.containsKey(id)){
			obj = monitorTerminal.get(id);
			AtomicLong lastTime = (AtomicLong) obj.get("lastTime");
			if((System.currentTimeMillis() - lastTime.get()) > DateUtils.MINUTE * 10){
				//10分钟内无响应,预警
				
			}
		}else{
			obj = new HashMap<String,Object>();
			obj.put("datas", new JSONArray());
			monitorTerminal.put(id, obj);
		}
		
		//放入缓存中
		JSONArray list =  (JSONArray)obj.get("datas");
		//限制条数
		if(maxNum == list.size()){
			list.remove(0);
		}
		data.put("acceptTime", new Date());
		list.add(data);
		//更新lastTime
		obj.put("lastTime", new AtomicLong(System.currentTimeMillis()));
	}
	
	public static JSONArray getMonitorProcData(String id){
		if(monitorProc.containsKey(id)){
			return (JSONArray) monitorProc.get(id).get("datas");
		}
		return null;
	}
	
	/**
	 * 设置服务/进程数据
	 * @param id
	 * @param data
	 * @return
	 */
	public static void setMonitorProcData(String id,JSONObject data){
		//获取缓存数据
		Map<String,Object> obj = null; 
		if(monitorProc.containsKey(id)){
			obj = monitorProc.get(id);
			AtomicLong lastTime = (AtomicLong) obj.get("lastTime");
			if((System.currentTimeMillis() - lastTime.get()) > DateUtils.MINUTE * 10){
				//10分钟内无响应,预警
				
			}
		}else{
			obj = new HashMap<String,Object>();
			obj.put("lastTime", new AtomicLong(System.currentTimeMillis()));
			obj.put("datas", new JSONArray());
			monitorProc.put(id, obj);
		}
		
		//放入缓存中
		JSONArray list = (JSONArray) obj.get("datas");
		//限制条数
		if(maxNum == list.size()){
			list.remove(0);
		}
		data.put("acceptTime", new Date());
		list.add(data);
		//更新lastTime
		obj.put("lastTime", new AtomicLong(System.currentTimeMillis()));
	}
}
