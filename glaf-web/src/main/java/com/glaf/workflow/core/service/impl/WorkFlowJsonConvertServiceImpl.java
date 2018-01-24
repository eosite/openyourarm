package com.glaf.workflow.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.service.ActReElementDefService;
import com.glaf.workflow.core.service.WorkFLowJsonConvertService;
import com.glaf.workflow.core.util.MyJSONObject;

@Service("workFlowJsonConvertService")
@Transactional(readOnly = true)
public class WorkFlowJsonConvertServiceImpl implements
		WorkFLowJsonConvertService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private RepositoryService repositoryService;
    @Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
    private ActReElementDefService actReElementDefService;
    @Resource
	public void setActReElementDefService(
			ActReElementDefService actReElementDefService) {
		this.actReElementDefService = actReElementDefService;
	}

	/**
	 * 替换ACT_GE_BYTEARRAY表source JSON 流程主属性内容
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject ConvertMainJson(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 替换ACT_GE_BYTEARRAY表source JSON 用户任务内容
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject ConvertUserTaskJson(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 替换ACT_GE_BYTEARRAY表source JSON 用户任务内容
	 * 
	 * @param json
	 * @return
	 */
	@Override
	public JSONObject ConvertSequenceflowJson(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 转换工作流ACT_GE_BYTEARRAY表source JSON 属性主方法
	 * 
	 * @param modelId
	 * @return
	 */
	@Override
	public JSONObject ConvertWorkFlowSourceJson(String modelId) {
		// TODO Auto-generated method stub
		// 根据modelId获取
		if(StringUtils.isEmpty(modelId)){
			return null;
		}
		byte[] srcBytes=repositoryService.getModelEditorSource(modelId);
		if(srcBytes==null){
			return null;
		}
		//获取扩展配置属性
		Map<String,ActReElementDef> actReElementDefMap=actReElementDefService.getWorkflowActReElementDefMapByModelIdProDefId(modelId, null);
		//设置有序
		JSONObject jsonObj=new JSONObject(true);
		try {
			String srcJsonStr=new String(srcBytes,"utf-8");
			//转换为JSONObject,实现第一层有序
			LinkedHashMap<String, Object> jsonObjMap=MyJSONObject.parseData(srcJsonStr);
			jsonObj.putAll(jsonObjMap);
			//遍历jsonObj
			//获取流程定义扩展主属性 
			ActReElementDef actReElementDef=actReElementDefMap.get(modelId);
			if(actReElementDef!=null){
				JSONObject extJsonObj=actReElementDefService.getWorkflowActReElementDefJsonObj(actReElementDef);
				//当前流程定义主属性
				JSONObject mainJsonObj=jsonObj.getJSONObject("properties");
				mainJsonObj=updateMainProp(mainJsonObj,extJsonObj);
				jsonObj.put("properties", mainJsonObj);
			}
			//获取流程图子图形
			JSONArray childShapesJsonArr=jsonObj.getJSONArray("childShapes");
			JSONObject shapeJson=null;
			String resourceId=null,stencilId=null;
			List<String> resourceIds=new ArrayList<String>();
			for(int i=0;i<childShapesJsonArr.size();i++){
				shapeJson=childShapesJsonArr.getJSONObject(i);
				//获取图形类型
				resourceId=shapeJson.getString("resourceId");
				stencilId=shapeJson.getJSONObject("stencil").getString("id");
				//获取扩展属性
				actReElementDef=actReElementDefMap.get(resourceId);
				if(actReElementDef!=null){
					resourceIds.add(resourceId);
					JSONObject extJsonObj=actReElementDefService.getWorkflowActReElementDefJsonObj(actReElementDef);
					if(extJsonObj==null){
						continue;
					}
					if(stencilId.equals("UserTask")){
						shapeJson=updateUserTaskProp(shapeJson,extJsonObj);
						childShapesJsonArr.remove(i);
						childShapesJsonArr.add(i, shapeJson);
					}
					else if(stencilId.equals("SequenceFlow")){
						shapeJson=updateSequenceFlowPro(shapeJson,extJsonObj);
						childShapesJsonArr.remove(i);
						childShapesJsonArr.add(i, shapeJson);
					}
					else if(stencilId.equals("StartNoneEvent")){
						shapeJson=updateStartNoneEventPro(shapeJson,extJsonObj);
						childShapesJsonArr.remove(i);
						childShapesJsonArr.add(i, shapeJson);
					}
					else if(stencilId.equals("CallActivity")){
						shapeJson=updateCallActivityPro(shapeJson,extJsonObj);
						childShapesJsonArr.remove(i);
						childShapesJsonArr.add(i, shapeJson);
					}else if(stencilId.equals("endNoneEvent")){
						shapeJson=updateEndNoneEventPro(shapeJson,extJsonObj);
						childShapesJsonArr.remove(i);
						childShapesJsonArr.add(i, shapeJson);
					}
				}
				
			}
			jsonObj.put("childShapes", childShapesJsonArr);
			//删除无效的元素
			actReElementDefService.removeDeletedElementsByNotInIds(modelId,resourceIds);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonObj;
	}
   /**
    * 更新工作流定义主属性
    * @param mainJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateMainProp(JSONObject mainJsonObj,JSONObject extJsonObj){
	   //从扩展属性中获取流程ID、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   //替换ACT JSON中对应属性的值
			   mainJsonObj.put("process_id", extPropMap.get("id"));
			   mainJsonObj.put("name", extPropMap.get("name"));
			   mainJsonObj.put("documentation", extPropMap.get("description"));
		   }
	   }
	   return mainJsonObj;
   }
   /**
    * 更新启动流程属性
    * @param startEventJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateStartNoneEventPro(JSONObject startEventJsonObj,JSONObject extJsonObj){
	 //从扩展属性中获取流程ID、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   JSONObject mainJsonObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   mainJsonObj=startEventJsonObj.getJSONObject("properties");
			   //替换ACT JSON中对应属性的值
			   if(extPropMap.get("id")!=null&&!StringUtils.isEmpty(extPropMap.get("id")))
				   mainJsonObj.put("overrideid", extPropMap.get("id"));
			   if(extPropMap.get("name")!=null&&!StringUtils.isEmpty(extPropMap.get("name")))
			   {
				   mainJsonObj.put("name", extPropMap.get("name"));
			   }
			   if(extPropMap.get("description")!=null&&!StringUtils.isEmpty(extPropMap.get("description")))
				   mainJsonObj.put("documentation", extPropMap.get("description"));
		   }
		   //设置启动者保存变量
		   mainJsonObj.put("initiator", "submitter");
		   startEventJsonObj.put("properties", mainJsonObj);
	   }
	   return startEventJsonObj;
   }
   /**
    * 更新结束流程属性
    * @param endEventJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateEndNoneEventPro(JSONObject endEventJsonObj,JSONObject extJsonObj){
	 //从扩展属性中获取流程ID、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   JSONObject mainJsonObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   mainJsonObj=endEventJsonObj.getJSONObject("properties");
			   //替换ACT JSON中对应属性的值
			   if(extPropMap.get("id")!=null&&!StringUtils.isEmpty(extPropMap.get("id")))
				   mainJsonObj.put("overrideid", extPropMap.get("id"));
			   if(extPropMap.get("name")!=null&&!StringUtils.isEmpty(extPropMap.get("name")))
			   {
				   mainJsonObj.put("name", extPropMap.get("name"));
			   }
			   if(extPropMap.get("description")!=null&&!StringUtils.isEmpty(extPropMap.get("description")))
				   mainJsonObj.put("documentation", extPropMap.get("description"));
		   }
		   endEventJsonObj.put("properties", mainJsonObj);
	   }
	   return endEventJsonObj;
   }
   /**
    * 更新用户任务属性
    * @param mainJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateUserTaskProp(JSONObject userTaskJsonObj,JSONObject extJsonObj){
	   //从扩展属性中获取用户任务编号、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   JSONObject mainJsonObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   //替换UserTask对应主属性值（编号、名称、描述）
			   mainJsonObj=userTaskJsonObj.getJSONObject("properties");
			   mainJsonObj.put("overrideid", extPropMap.get("id"));
			   mainJsonObj.put("name", extPropMap.get("name"));
			   mainJsonObj.put("documentation", extPropMap.get("description"));
		   }
		   //替换UserTask对应运行属性（单签、会签标识）
		   if(jsonArr.size()>1){
		      JSONArray taskrunprops=jsonArr.getJSONObject(1)!=null?jsonArr.getJSONObject(1).getJSONArray("properties"):null;
		      if(taskrunprops!=null){
		    	  for(int i=0;i<taskrunprops.size();i++){
					   jObj=taskrunprops.getJSONObject(i);
					   if(jObj.getString("id").equals("actmode")){
						   //获取执行方式
						   String actmode=jObj.getString("value");
						   if(actmode.equals("singlesign")){
							   actmode="None";
							   //增加创建任务监听器
							   mainJsonObj.put("tasklisteners",createUserTaskListenerJson("create","","${assignUserListener}"));
							   mainJsonObj.put("usertaskassignment", "");
							   mainJsonObj.put("multiinstance_collection", "");
							   mainJsonObj.put("multiinstance_variable", "");
			
						   }else if(actmode.equals("countersign")){
							   mainJsonObj.put("tasklisteners","");
							   //获取会签方式 顺序、并行
							   JSONArray actmodepropJsonArr=jObj.getJSONArray("properties");
							   String countersigntype=null;
							   if(actmodepropJsonArr!=null&&actmodepropJsonArr.getJSONObject(0)!=null){
								   countersigntype=actmodepropJsonArr.getJSONObject(0).getString("value");
								   if(countersigntype.equals("sequential")){
									   actmode="Sequential";
								   }
								   else if(countersigntype.equals("parallel")){
									   actmode="Parallel";
								   }
							   }
							   JSONObject usertaskassignmentJson=new JSONObject();
							   JSONObject assignmentJson=new JSONObject();
							   assignmentJson.put("assignee", "${"+mainJsonObj.get("overrideid")+"_assignee}");
							   usertaskassignmentJson.put("assignment", assignmentJson);
							   mainJsonObj.put("usertaskassignment", usertaskassignmentJson);
							   mainJsonObj.put("multiinstance_collection", "${workFLowDefinedService.getUserTaskAssigns(execution)}");
							   mainJsonObj.put("multiinstance_variable", mainJsonObj.get("overrideid")+"_assignee");
						   }
						   //替换多实例类型属性值
						   mainJsonObj.put("multiinstance_type", actmode);
						   break;
					   }
					  
				   }
		      }
		   }
		   userTaskJsonObj.put("properties", mainJsonObj);
	   }
	   return userTaskJsonObj;
   }
   /**
    * 更新连接线属性
    * @param seqFlowJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateSequenceFlowPro(JSONObject seqFlowJsonObj,JSONObject extJsonObj){
	   //从扩展属性中获取流程ID、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   JSONObject propJsonObj=seqFlowJsonObj.getJSONObject("properties");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   //替换ACT JSON中对应属性的值
			   propJsonObj.put("overrideid", extPropMap.get("id"));
			   propJsonObj.put("name", extPropMap.get("name"));
			   propJsonObj.put("documentation", extPropMap.get("description"));
			   propJsonObj.put("defaultflow", extPropMap.get("defaultFlow")!=null&&extPropMap.get("defaultFlow").equals("1")?"true":"false");
			   String expression=extPropMap.get("expression");
			   if(!StringUtils.isEmpty(expression)){
				   JSONObject expObj=JSON.parseObject(expression);
				   String conditionsequenceflow=expObj.getString("expActVal");
				   String conditionsequenceflowName=expObj.getString("expVal");
				   if(!StringUtils.isEmpty(conditionsequenceflow)){
					   propJsonObj.put("conditionsequenceflow","${"+conditionsequenceflow+"}");
					   
				   }
				   if(!StringUtils.isEmpty(conditionsequenceflowName)){
					   if(extPropMap.get("name")==null||StringUtils.isEmpty(extPropMap.get("name")))
					   propJsonObj.put("name", conditionsequenceflowName);
					   if(extPropMap.get("description")==null||StringUtils.isEmpty(extPropMap.get("description")))
					   propJsonObj.put("description", conditionsequenceflowName);
				   }
			   }
			   seqFlowJsonObj.put("properties", propJsonObj);
		   }
	   }
	   return seqFlowJsonObj;
   }
   /**
    * 更新子流程属性
    * @param seqFlowJsonObj
    * @param extJsonObj
    * @return
    */
   public JSONObject updateCallActivityPro(JSONObject callActivityJsonObj,JSONObject extJsonObj){
	   //从扩展属性中获取流程ID、名称、描述
	   JSONArray jsonArr=extJsonObj.getJSONArray("propertyPackages");
	   JSONObject propJsonObj=callActivityJsonObj.getJSONObject("properties");
	   if(jsonArr!=null&&jsonArr.size()>0){
		   JSONArray baseprops=jsonArr.getJSONObject(0)!=null?jsonArr.getJSONObject(0).getJSONArray("properties"):null;
		   JSONObject jObj=null;
		   if(baseprops!=null){
			   Map<String,String> extPropMap=new HashMap<String,String>();
			   for(int i=0;i<baseprops.size();i++){
				   jObj=baseprops.getJSONObject(i);
				   extPropMap.put(jObj.getString("id"), jObj.getString("value"));
			   }
			   //替换ACT JSON中对应属性的值
			   if(extPropMap.containsKey("id"))
			   propJsonObj.put("overrideid", extPropMap.get("id"));
			   if(extPropMap.containsKey("name"))
			   propJsonObj.put("name", extPropMap.get("name"));
			   if(extPropMap.containsKey("description"))
			   propJsonObj.put("documentation", extPropMap.get("description"));
			   if(extPropMap.containsKey("calledProcess")&&extPropMap.get("calledProcess")!=null)
			   {
			      JSONObject calledProcessJson=JSON.parseObject(extPropMap.get("calledProcess"));
			      propJsonObj.put("callactivitycalledelement", calledProcessJson.getString("calledProcessKey"));
			   }
			   if(jsonArr.size()>1){
			   JSONObject inparampropJSON=jsonArr.getJSONObject(1);
			   JSONArray  inparampropJSONArr=inparampropJSON.getJSONArray("properties");
			   //定义输入参数
			   JSONObject callactivityinparametersJSON=new JSONObject();
			   JSONArray inParametersJSONArr=new JSONArray();
			   JSONObject inParameterJSON=null;
			   JSONObject inparampropJSONItem=null;
			   JSONObject inparampropVal=null;
			   //源输入表达式
			   String expVal=null;
			   //目标参数
			   String targetVal=null;
			   for(int i=0;i<inparampropJSONArr.size();i++)
			   {
				   inparampropJSONItem=inparampropJSONArr.getJSONObject(i);
				   inparampropVal=inparampropJSONItem.getJSONObject("value");
				   expVal=inparampropVal.getString("expActVal");
				   targetVal=inparampropVal.getString("targetActVal");
				   if(!StringUtils.isEmpty(expVal)&&!StringUtils.isEmpty(targetVal)){
					   inParameterJSON=new JSONObject();
					   inParameterJSON.put("sourceExpression", "${"+expVal+"}");
					   inParameterJSON.put("target", targetVal);
					   inParametersJSONArr.add(inParameterJSON);
				   }
			   }
			   callactivityinparametersJSON.put("inParameters", inParametersJSONArr);
			   propJsonObj.put("callactivityinparameters", callactivityinparametersJSON);
			   }
			   //定义输出参数
			   if(jsonArr.size()>2){
				   JSONObject outparampropJSON=jsonArr.getJSONObject(2);
				   JSONArray  outparampropJSONArr=outparampropJSON.getJSONArray("properties");
				   //定义输入参数
				   JSONObject callactivityoutparametersJSON=new JSONObject();
				   JSONArray outParametersJSONArr=new JSONArray();
				   JSONObject outParameterJSON=null;
				   JSONObject outparampropJSONItem=null;
				   JSONObject outparampropVal=null;
				   //源输入表达式
				   String expVal=null;
				   //目标参数
				   String targetVal=null;
				   for(int i=0;i<outparampropJSONArr.size();i++)
				   {
					   outparampropJSONItem=outparampropJSONArr.getJSONObject(i);
					   outparampropVal=outparampropJSONItem.getJSONObject("value");
					   expVal=outparampropVal.getString("expActVal");
					   targetVal=outparampropVal.getString("targetActVal");
					   if(!StringUtils.isEmpty(expVal)&&!StringUtils.isEmpty(targetVal)){
						   outParameterJSON=new JSONObject();
						   outParameterJSON.put("sourceExpression", "${"+expVal+"}");
						   outParameterJSON.put("target", targetVal);
						   outParametersJSONArr.add(outParameterJSON);
					   }
				   }
				   callactivityoutparametersJSON.put("outParameters", outParametersJSONArr);
				   propJsonObj.put("callactivityoutparameters", callactivityoutparametersJSON);
				   }
			   callActivityJsonObj.put("properties", propJsonObj);
		   }
	   }
	   return callActivityJsonObj;
   }
   /**
    * 创建监听JSON
    * @param event
    * @param expression
    * @param delegateExpression
    * @return
    */
   public JSONObject createUserTaskListenerJson(String event,String expression,String delegateExpression){
	   JSONObject taskListenersJsonObj=new JSONObject();
	   taskListenersJsonObj=new JSONObject();
	   JSONObject taskListenerJson=new JSONObject();
	   taskListenerJson.put("event", event);
	   if(!StringUtils.isEmpty(expression))
	   taskListenerJson.put("expression", expression);
	   if(!StringUtils.isEmpty(delegateExpression))
	   taskListenerJson.put("delegateExpression", delegateExpression);
	   JSONArray taskListenersJson=new JSONArray();
	   taskListenersJson.add(taskListenerJson);
	   taskListenersJsonObj.put("taskListeners", taskListenersJson);
	   return taskListenersJsonObj;
   }
}
