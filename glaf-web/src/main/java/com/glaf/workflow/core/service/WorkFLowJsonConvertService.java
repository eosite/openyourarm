package com.glaf.workflow.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Transactional(readOnly = true)
public interface WorkFLowJsonConvertService {
    /**
     * 替换ACT_GE_BYTEARRAY表source JSON 流程主属性内容
     * @param json
     * @return
     */
	public JSONObject ConvertMainJson(JSONObject json);  
	/**
	 * 替换ACT_GE_BYTEARRAY表source JSON 用户任务内容
	 * @param json
	 * @return
	 */
	public JSONObject ConvertUserTaskJson(JSONObject json);
	/**
	 * 替换ACT_GE_BYTEARRAY表source JSON 用户任务内容
	 * @param json
	 * @return
	 */
	
	public JSONObject ConvertSequenceflowJson(JSONObject json);
	/**
	 * 转换工作流ACT_GE_BYTEARRAY表source JSON 属性主方法
	 * @param modelId
	 * @return
	 */
	public JSONObject ConvertWorkFlowSourceJson(String modelId);
}
