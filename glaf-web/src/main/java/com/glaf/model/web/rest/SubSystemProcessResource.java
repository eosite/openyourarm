package com.glaf.model.web.rest;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.identity.User;
import com.glaf.core.util.RequestUtils;
import com.glaf.model.domain.SystemFunc;
import com.glaf.model.domain.SystemProcDef;
import com.glaf.model.service.SystemFuncService;
import com.glaf.model.service.SystemProcDefService;

@Controller
@Path("/rs/modeling/process")
public class SubSystemProcessResource {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected RepositoryService repositoryService;
	 
	protected SystemProcDefService systemProcDefService;
	
	protected SystemFuncService systemFuncService;
	/**
	 * 新建子系统流程定义
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@POST
	@Path("/createProcess")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] createProcess(@Context HttpServletRequest request)
			throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		try {
			String subSysId = RequestUtils.getString(request, "subSysId");
			String sysId= RequestUtils.getString(request, "sysId");
			String systemProcDefId=RequestUtils.getString(request, "systemProcDefId");
			String name = RequestUtils.getString(request, "modelName", "");
			String key = RequestUtils.getString(request, "modelKey", "");
			String description = RequestUtils.getString(request, "modelDesc",
					"");
			if (StringUtils.isEmpty(subSysId)||subSysId.equals("0")||StringUtils.isEmpty(name)||StringUtils.isEmpty(key)) {
				jsonObj.put("result", 0);
			} else {
				ObjectMapper objectMapper = new ObjectMapper();
				ObjectNode editorNode = objectMapper.createObjectNode();
				editorNode.put("id", "canvas");
				editorNode.put("resourceId", "canvas");
				ObjectNode stencilSetNode = objectMapper.createObjectNode();
				stencilSetNode.put("namespace",
						"http://b3mn.org/stencilset/bpmn2.0#");
				editorNode.set("stencilset", stencilSetNode);
				Model modelData = repositoryService.newModel();
				ObjectNode modelObjectNode = objectMapper.createObjectNode();
				modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
				modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
				description = StringUtils.defaultString(description);
				modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
						description);
				modelData.setMetaInfo(modelObjectNode.toString());
				modelData.setName(name);
				modelData.setKey(StringUtils.defaultString(key));
				modelData.setCategory("systemplan");
				repositoryService.saveModel(modelData);
				repositoryService.addModelEditorSource(modelData.getId(),
						editorNode.toString().getBytes("utf-8"));
				User user = RequestUtils.getUser(request);
				String actorId = user.getActorId();
				SystemProcDef systemProcDef =null;
				if(StringUtils.isNoneEmpty(systemProcDefId)){
					systemProcDef=systemProcDefService.getSystemProcDef(systemProcDefId);
					systemProcDef.setCurrProcDefKey(key);
					systemProcDef.setCurrProcModelId(modelData.getId());
					systemProcDef.setDeleteFlag(0);
					systemProcDef.setUpdateBy(actorId);
					systemProcDef.setUpdateTime(new Date());
				}else{
					systemProcDef = new SystemProcDef();
					// 创建系统规划流程定义
					SystemFunc systemFunc = new SystemFunc();
					systemFunc.setFuncName(name);
					systemFunc.setFuncType("subsystem");
					systemFunc.setSysId(sysId);
					systemFunc.setDeleteFlag(0);
					systemFunc.setCreateTime(new Date());			
					systemFunc.setCreateBy(actorId);
					systemFuncService.save(systemFunc);
					//增加子系统与流程关联
					systemProcDef.setCurrProcDefKey(key);
					systemProcDef.setCurrProcModelId(modelData.getId());
					systemProcDef.setFuncId(systemFunc.getFuncId());
					systemProcDef.setSubSysId(subSysId);
					systemProcDef.setSysId(sysId);
					systemProcDef.setEleId(modelData.getId());
					systemProcDef.setEleDesc(description);
					systemProcDef.setEleName(name);
					systemProcDef.setEleResourceId(modelData.getId());
					systemProcDef.setEleType("subsystem");
					systemProcDef.setDeleteFlag(0);
					systemProcDef.setCreateBy(actorId);
					systemProcDef.setCreateTime(new Date());
				}
				systemProcDefService.save(systemProcDef);
				jsonObj.put("modelId", modelData.getId());
				jsonObj.put("result", 1);               
				// response.sendRedirect(request.getContextPath() +
				// "/service/editor?id=" + modelData.getId());
			}
		} catch (Exception e) {
			logger.error("子系统流程定义失败：", e);
			jsonObj.put("result", -1);
		}
		return JSON.toJSONString(jsonObj).getBytes("UTF-8");
	}

	@javax.annotation.Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	@javax.annotation.Resource
	public void setSystemProcDefService(SystemProcDefService systemProcDefService) {
		this.systemProcDefService = systemProcDefService;
	}
	@javax.annotation.Resource
	public void setSystemFuncService(SystemFuncService systemFuncService) {
		this.systemFuncService = systemFuncService;
	}
}
