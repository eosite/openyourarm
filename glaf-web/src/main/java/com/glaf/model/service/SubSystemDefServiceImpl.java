package com.glaf.model.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.model.domain.SubSystemDef;
import com.glaf.model.domain.SystemFunc;
import com.glaf.model.domain.SystemProcDef;
import com.glaf.model.mapper.SubSystemDefMapper;
import com.glaf.model.query.SubSystemDefQuery;
import com.glaf.workflow.core.domain.ActReElementDef;
import com.glaf.workflow.core.util.MyJSONObject;

@Service("com.glaf.model.service.subSystemDefService")
@Transactional(readOnly = true)
public class SubSystemDefServiceImpl implements SubSystemDefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SubSystemDefMapper subSystemDefMapper;

	protected SystemFuncService systemFuncService;

	protected SystemProcDefService systemProcDefService;
	
	protected SystemFuncDataObjService systemFuncDataObjService;

	public SubSystemDefServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			subSystemDefMapper.deleteSubSystemDefById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> subSysIds) {
		if (subSysIds != null && !subSysIds.isEmpty()) {
			for (String id : subSysIds) {
				subSystemDefMapper.deleteSubSystemDefById(id);
			}
		}
	}

	public int count(SubSystemDefQuery query) {
		return subSystemDefMapper.getSubSystemDefCount(query);
	}

	public List<SubSystemDef> list(SubSystemDefQuery query) {
		List<SubSystemDef> list = subSystemDefMapper.getSubSystemDefs(query);
		return list;
	}

	public List<SubSystemDef> getSystemSubSystems(String sysId) {
		List<SubSystemDef> list = subSystemDefMapper.getSystemSubSystems(sysId);
		return list;
	}

	public List<SubSystemDef> getSystemSubSystemsByTreeId(String treeId) {
		List<SubSystemDef> list = subSystemDefMapper.getSystemSubSystemsByTreeId(treeId);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSubSystemDefCountByQueryCriteria(SubSystemDefQuery query) {
		return subSystemDefMapper.getSubSystemDefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SubSystemDef> getSubSystemDefsByQueryCriteria(int start, int pageSize, SubSystemDefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SubSystemDef> rows = sqlSessionTemplate.selectList("getSubSystemDefs", query, rowBounds);
		return rows;
	}

	public SubSystemDef getSubSystemDef(String id) {
		if (id == null) {
			return null;
		}
		SubSystemDef subSystemDef = subSystemDefMapper.getSubSystemDefById(id);
		return subSystemDef;
	}

	@Transactional
	public void save(SubSystemDef subSystemDef) {
		if (subSystemDef.getSubSysId() == null) {
			subSystemDef.setSubSysId(UUID32.getUUID());
			subSystemDef.setOId(idGenerator.nextId("SUB_SYSTEM_DEF_"));
			// subSystemDef.setCreateDate(new Date());
			// subSystemDef.setDeleteFlag(0);
			if (StringUtils.isNotEmpty(subSystemDef.getTreeId())) {
				String treeId = subSystemDef.getTreeId() + subSystemDef.getOId() + "|";
				subSystemDef.setTreeId(treeId);
			} else {
				subSystemDef.setTreeId(subSystemDef.getOId() + "|");
			}
			subSystemDefMapper.insertSubSystemDef(subSystemDef);
		} else {
			subSystemDefMapper.updateSubSystemDef(subSystemDef);
		}
	}

	public void updateNameType(SubSystemDef subSystemDef) {
		subSystemDefMapper.updateNameType(subSystemDef);
	}

	@Transactional
	public JSONObject saveSystemPlanByModelId(byte[] processModelBytes, String modelId, SubSystemDef subSystemDef,
			String user) {
		if (processModelBytes == null) {
			return null;
		}
		// 设置有序
		JSONObject jsonObj = new JSONObject(true);
		try {
			String srcJsonStr = new String(processModelBytes, "utf-8");
			// 转换为JSONObject,实现第一层有序
			LinkedHashMap<String, Object> jsonObjMap = MyJSONObject.parseData(srcJsonStr);
			jsonObj.putAll(jsonObjMap);
			// 获取流程图子图形
			JSONArray childShapesJsonArr = jsonObj.getJSONArray("childShapes");
			JSONObject shapeJson = null;
			String resourceId = null, stencilId = null;
			Map<String, SystemProcDef> subSystemProcDefMap = systemProcDefService.getSystemProcDefMap(modelId);
			// if (subSystemProcDefMap.size() == 1) {
			// 当前流程定义主属性
			// JSONObject mainJsonObj = jsonObj.getJSONObject("properties");
			// }
			JSONObject mainJsonObj = null;
			String type;
			// 记录已保存的功能或数据对象
			Map<String, String> funcMap = new HashMap<String, String>();
			// 保存节点、功能以及功能类型之间的关系 key:resouceId value:funcId,type
			Map<String, String[]> resourceFuncTypeMap = new HashMap<String, String[]>();
			//功能流出
			Map<String,String> funcflow =  new HashMap<String,String>();
			//功能流入
			Map<String,String> flowtarget =  new HashMap<String,String>();
			//功能ID
			String funcId=null;
			for (int i = 0; i < childShapesJsonArr.size(); i++) {
				type = null;
				shapeJson = childShapesJsonArr.getJSONObject(i);
				// 获取图形类型
				resourceId = shapeJson.getString("resourceId");
				stencilId = shapeJson.getJSONObject("stencil").getString("id");
				mainJsonObj = shapeJson.getJSONObject("properties");
				// 页面功能
				if (stencilId.equals("ScriptTask")) {
					type = "page";
				}
				// 后台服务
				else if (stencilId.equals("ServiceTask")) {
					type = "service";
				}
				// 报表功能
				else if (stencilId.equals("BusinessRule")) {
					type = "report";
				}
				// 子系統
				else if (stencilId.equals("CallActivity")) {
					type = "subsystem";
				}
				// 数据对象
				else if (stencilId.equals("ReceiveTask")) {
					type = "dataobj";
				} else if (stencilId.equals("SequenceFlow")) {
					 saveFlowTarget(flowtarget,shapeJson);
					 continue;
				} else {
					 continue;
				}
				saveSrcFlow(funcflow,shapeJson);
				if (!subSystemProcDefMap.containsKey(resourceId)) {
					funcId=createSubSystemFunc(shapeJson, subSystemDef.getSubSysId(), subSystemDef.getTreeId(),
							subSystemDef.getSysId(), type, modelId, user, funcMap, resourceFuncTypeMap);
				} else {
					SystemProcDef subSystemProc = subSystemProcDefMap.get(resourceId);
					funcId=updateSubSystemFunc(shapeJson, subSystemProc, type, user, funcMap, resourceFuncTypeMap);
				}
				shapeJson=updateFuncPropId(shapeJson,funcId);
				childShapesJsonArr.remove(i);
				childShapesJsonArr.add(i, shapeJson);
			}
			//保存流程功能数据对象关系
			saveSubSystemFuncDataObj(user,flowtarget,funcflow,resourceFuncTypeMap);
			//更新流程图JSONObject补充FuncId
			jsonObj.put("childShapes", childShapesJsonArr);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonObj;
	}
	/**
	 * 更新流程描述文件中的编码为数据库中的功能ID
	 * @param shapeJson
	 * @param funcId
	 * @return
	 */
	public JSONObject updateFuncPropId(JSONObject shapeJson,String funcId){
		JSONObject propertiesJsonObj=shapeJson.getJSONObject("properties");
		propertiesJsonObj.put("overrideid", funcId);
		shapeJson.put("properties", propertiesJsonObj);
		return shapeJson;
	}
	/**
	 * 保存功能输入输出数据对象
	 * @param user
	 * @param flowtarget
	 * @param funcflow
	 * @param resourceFuncTypeMap
	 */
	public void saveSubSystemFuncDataObj(String user,Map<String,String> flowtarget,Map<String,String> funcflow,Map<String, String[]> resourceFuncTypeMap){
        String flowId=null;
		String resourceId=null;
		String funcType=null;
		String funcResouceId=null;
		String funcId=null;
		String dataObjFuncId=null;
		String[] funcIdType=null;
		int dataObjType=0;
		//遍历输入线路（流向数据对象）
		for(Entry<String,String> entry: flowtarget.entrySet()){
			flowId=entry.getKey();
			resourceId=entry.getValue();
			if(resourceFuncTypeMap.containsKey(resourceId)){
				funcIdType=resourceFuncTypeMap.get(resourceId);
				funcType=funcIdType[0];
				if(funcType.equals("dataobj")){
					dataObjType=1;
					dataObjFuncId=funcIdType[1];
					//获取线路的源头（输出数据对象的功能）
					if(funcflow.containsKey(flowId)){
						funcResouceId=funcflow.get(flowId);
						if(resourceFuncTypeMap.containsKey(funcResouceId)){
							funcId=resourceFuncTypeMap.get(funcResouceId)[1];
						}
					}
					if(StringUtils.isNotEmpty(dataObjFuncId)&&StringUtils.isNotEmpty(funcId)){
						//保存功能数据对象关系
						systemFuncDataObjService.saveFuncDataObj(user,funcId,dataObjFuncId,dataObjType);
					}
				}else{
					continue;
				}
			}
		
		}
		//遍历输出线路（数据对象流出）
		for(Entry<String,String> entry: funcflow.entrySet()){
			flowId=entry.getKey();
			resourceId=entry.getValue();
			if(resourceFuncTypeMap.containsKey(resourceId)){
				funcIdType=resourceFuncTypeMap.get(resourceId);
				funcType=funcIdType[0];
				if(funcType.equals("dataobj")){
					dataObjType=0;
					dataObjFuncId=funcIdType[1];
					//获取线路的目标（输入数据对象的功能）
					if(flowtarget.containsKey(flowId)){
						funcResouceId=flowtarget.get(flowId);
						if(resourceFuncTypeMap.containsKey(funcResouceId)){
							funcId=resourceFuncTypeMap.get(funcResouceId)[1];
						}
					}
					if(StringUtils.isNotEmpty(dataObjFuncId)&&StringUtils.isNotEmpty(funcId)){
						//保存功能数据对象关系
						systemFuncDataObjService.saveFuncDataObj(user,funcId,dataObjFuncId,dataObjType);
					}
				}else{
					continue;
				}
			}
		
		}
	}
	
    /**
     * 保存线路指向目标
     * @param flowList
     * @param propJsonObj
     */
	public void saveFlowTarget(Map<String,String> flowtarget,JSONObject propJsonObj) {
		String resourceId = propJsonObj.getString("resourceId");
		String targetResourceId = propJsonObj.getJSONObject("target").getString("resourceId");
		flowtarget.put(resourceId, targetResourceId);
	}
	
	/**
	 * 保存功能流出线路
	 * @param funcflow
	 * @param propJsonObj
	 */
	public void saveSrcFlow(Map<String,String>funcflow,JSONObject propJsonObj){
		String resourceId = propJsonObj.getString("resourceId");
		JSONArray flowJSONArr=propJsonObj.getJSONArray("outgoing");
		JSONObject flowJSON=null;
		for(int i=0;i<flowJSONArr.size();i++){
			flowJSON=flowJSONArr.getJSONObject(i);
			funcflow.put(flowJSON.getString("resourceId"), resourceId);
		}		
	}

	/**
	 * 新建子系统信息
	 * 
	 * @param propJsonObj
	 * @param parentSystemId
	 * @param sysId
	 * @param funcType
	 */
	public String createSubSystemFunc(JSONObject propJsonObj, String parentSystemId, String parentTreeId, String sysId,
			String funcType, String modelId, String user, Map<String, String> funcMap,
			Map<String, String[]> resourceFuncTypeMap) {
		JSONObject mainJsonObj = propJsonObj.getJSONObject("properties");
		String funcName = mainJsonObj.getString("name");
		String resourceId = propJsonObj.getString("resourceId");
		String stencilId = propJsonObj.getJSONObject("stencil").getString("id");
		SubSystemDef subSystemDef = new SubSystemDef();
		subSystemDef.setParentId_(parentSystemId);
		subSystemDef.setSysId(sysId);
		subSystemDef.setName(funcName);
		subSystemDef.setTreeId(parentTreeId);
		subSystemDef.setDeleteFlag(0);
		subSystemDef.setCreateTime(new Date());
		subSystemDef.setCreateBy(user);
		save(subSystemDef);
		// 获取功能编号/数据对象编号
		String funcCode = mainJsonObj.getString("overrideid");
		String funcId = null;
		// 新增功能
		if (StringUtils.isEmpty(funcCode) && !funcMap.containsKey(funcName)) {
			SystemFunc systemFunc = new SystemFunc();
			systemFunc.setFuncName(funcName);
			systemFunc.setFuncType(funcType);
			systemFunc.setSysId(sysId);
			systemFunc.setDeleteFlag(0);
			systemFunc.setCreateTime(new Date());
			systemFunc.setCreateBy(user);
			systemFuncService.save(systemFunc);
			funcId = systemFunc.getFuncId();
		} else if (StringUtils.isNotEmpty(funcCode)) {
			funcId = funcCode;
		} else if (funcMap.containsKey(funcName)) {
			funcId = funcMap.get(funcName);
		}
		SystemProcDef systemProcDef = new SystemProcDef();
		systemProcDef.setProcDefKey(mainJsonObj.getString("process_id"));
		systemProcDef.setProcModelId(modelId);
		systemProcDef.setFuncId(funcId);
		systemProcDef.setSubSysId(subSystemDef.getSubSysId());
		systemProcDef.setSysId(sysId);
		systemProcDef.setEleName(funcName);
		systemProcDef.setEleId(stencilId);
		systemProcDef.setEleDesc(mainJsonObj.getString("documentation"));
		systemProcDef.setEleResourceId(resourceId);
		systemProcDef.setEleType(funcType);
		systemProcDef.setDeleteFlag(0);
		systemProcDef.setCreateBy(user);
		systemProcDef.setCreateTime(new Date());
		systemProcDefService.save(systemProcDef);
		funcMap.put(funcName, funcId.toString());
		String[] resourceFuncType = new String[2];
		resourceFuncType[0] = funcType;
		resourceFuncType[1] = funcId.toString();
		resourceFuncTypeMap.put(resourceId, resourceFuncType);
		return funcId;
	}

	/**
	 * 更新子系统信息
	 * 
	 * @param propJsonObj
	 * @param parentSystemId
	 * @param sysId
	 * @param funcType
	 */
	public String updateSubSystemFunc(JSONObject propJsonObj, SystemProcDef systemProcDef, String funcType, String user,
			Map<String, String> funcMap, Map<String, String[]> resourceFuncTypeMap) {
		JSONObject mainJsonObj = propJsonObj.getJSONObject("properties");
		String funcName = mainJsonObj.getString("name");
		String resourceId = propJsonObj.getString("resourceId");
		String stencilId = propJsonObj.getJSONObject("stencil").getString("id");
		SubSystemDef subSystemDef = new SubSystemDef();
		subSystemDef.setSubSysId(systemProcDef.getSubSysId());
		subSystemDef.setName(funcName);
		subSystemDef.setDesc(mainJsonObj.getString("documentation"));
		subSystemDef.setDeleteFlag(0);
		subSystemDef.setUpdateTime(new Date());
		subSystemDef.setUpdateBy(user);
		updateNameType(subSystemDef);
		// 获取功能编号/数据对象编号
		String funcCode = mainJsonObj.getString("overrideid");
		String funcId = null;
		// 新增功能
		if (StringUtils.isEmpty(funcCode) && !funcMap.containsKey(funcName)) {
			SystemFunc systemFunc = new SystemFunc();
			systemFunc.setFuncName(funcName);
			systemFunc.setFuncType(funcType);
			systemFunc.setSysId(subSystemDef.getSysId());
			systemFunc.setDeleteFlag(0);
			systemFunc.setCreateTime(new Date());
			systemFunc.setCreateBy(user);
			systemFuncService.save(systemFunc);
			funcId = systemFunc.getFuncId();
		} else if (StringUtils.isNotEmpty(funcCode)) {
			funcId = funcCode;
		} else if (funcMap.containsKey(funcName)) {
			funcId = funcMap.get(funcName);
		}
		/*
		 * SystemFunc systemFunc = new SystemFunc();
		 * systemFunc.setFuncId(systemProcDef.getFuncId());
		 * systemFunc.setFuncName(funcName); systemFunc.setFuncType(funcType);
		 * systemFunc.setDeleteFlag(0); systemFunc.setUpdateTime(new Date());
		 * systemFunc.setUpdateBy(user);
		 * systemFuncService.updateFuncNameType(systemFunc);
		 */
		systemProcDef.setEleName(funcName);
		systemProcDef.setProcDefKey(mainJsonObj.getString("process_id"));
		systemProcDef.setFuncId(funcId);
		systemProcDef.setSubSysId(subSystemDef.getSubSysId());
		systemProcDef.setEleId(stencilId);
		systemProcDef.setEleDesc(mainJsonObj.getString("documentation"));
		systemProcDef.setEleResourceId(resourceId);
		systemProcDef.setEleType(funcType);
		systemProcDef.setDeleteFlag(0);
		systemProcDef.setUpdateBy(user);
		systemProcDef.setUpdateTime(new Date());
		systemProcDefService.updateProcDefNameType(systemProcDef);
		funcMap.put(funcName + "_" + funcId.toString(), funcId.toString());
		String[] resourceFuncType = new String[2];
		resourceFuncType[0] = funcType;
		resourceFuncType[1] = funcId.toString();
		resourceFuncTypeMap.put(resourceId, resourceFuncType);
		return funcId;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.model.mapper.SubSystemDefMapper")
	public void setSubSystemDefMapper(SubSystemDefMapper subSystemDefMapper) {
		this.subSystemDefMapper = subSystemDefMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource(name = "com.glaf.model.service.systemProcDefService")
	public void setSystemProcDefService(SystemProcDefService systemProcDefService) {
		this.systemProcDefService = systemProcDefService;
	}

	@javax.annotation.Resource(name = "com.glaf.model.service.systemFuncService")
	public void setSystemFuncService(SystemFuncService systemFuncService) {
		this.systemFuncService = systemFuncService;
	}
	@javax.annotation.Resource(name = "com.glaf.model.service.systemFuncDataObjService")
	public void setSystemFuncDataObjService(SystemFuncDataObjService systemFuncDataObjService) {
		this.systemFuncDataObjService = systemFuncDataObjService;
	}

	@Override
	public void deleteSubSystemDefBySysId(String sysId) {
		// TODO Auto-generated method stub
		subSystemDefMapper.deleteSubSystemDefBySysId(sysId);
	}

}
