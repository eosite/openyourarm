package com.glaf.form.website.springmvc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.ResponseUtils;
import com.glaf.workflow.core.domain.ProcessActivityInstance;
import com.glaf.workflow.core.domain.ProcessInsMapping;
import com.glaf.workflow.core.query.ProcessInsMappingQuery;
import com.glaf.workflow.core.service.ProcessInsMappingService;

/**
 * 
 * Rest响应类
 *
 */

@Controller("/workflow")
@RequestMapping("/workflow")
public class WorkFlowController {
	
	private ProcessInsMappingService processInsMappingService;
	
	private HistoryService historyService;

	@RequestMapping(value="/task/history",produces="text/html;charset=utf-8")
	@ResponseBody
	public byte[] tasklist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//源交互流程实例ID
		String srcProcInstId = request.getParameter("procInstId");
		List<ProcessActivityInstance> insts=new ArrayList<ProcessActivityInstance>();
		Map<Integer,String> processStatusMap=new HashMap<Integer,String>();
		processStatusMap.put(new Integer(0), "数据已接收");
		processStatusMap.put(new Integer(10), "流程已启动");
		processStatusMap.put(new Integer(99), "流程已完成");
		processStatusMap.put(new Integer(888), "回复数据已准备");
		processStatusMap.put(new Integer(999), "回复数据已发送");
		if (StringUtils.isNotEmpty(srcProcInstId)) {
			ProcessInsMappingQuery query=new ProcessInsMappingQuery();
			query.setSrcProcInsId(srcProcInstId);
			query.setOrderBy(" E.PROC_STARTTIME_ asc ");
			List<ProcessInsMapping> processInsMappingList=processInsMappingService.list(query);
			//本地目标流程实例ID
			String desProcInstId=null;
			int processResult=0;
			List<HistoricTaskInstance> list=null;
			ProcessActivityInstance inst=null;
			String procStatus=null;
			Date startTime=null;
			Date endTime=null;
			for(ProcessInsMapping processInsMapping:processInsMappingList){
				desProcInstId=processInsMapping.getDesProcInsId();
				processResult=processInsMapping.getProcResult();
				if(processStatusMap.containsKey(processInsMapping.getProcStatus())){
					procStatus=processStatusMap.get(processInsMapping.getProcStatus());
				}
				  //获取历史步骤
				  list = historyService
			                .createHistoricTaskInstanceQuery()
			                .processInstanceId(desProcInstId)
			                .list();
				  for(HistoricTaskInstance historicTaskInstance:list){
					  inst=new ProcessActivityInstance();
					  inst.setProcessInstId(desProcInstId);
					  inst.setProcessStatus(procStatus);
					  inst.setProcessResult(processResult);
					  inst.setReplyFlag(processInsMapping.getReplyFlag());
					  inst.setActivityName(historicTaskInstance.getName());
					  inst.setAssigner(historicTaskInstance.getAssignee());
					  startTime=historicTaskInstance.getStartTime();
					  inst.setStartTime(startTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime):"");
					  endTime=historicTaskInstance.getEndTime();
					  inst.setEndTime(endTime!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTime):"");
					  inst.setDurationInMillis(historicTaskInstance.getDurationInMillis());
					  insts.add(inst);
				  }
			}
		} else {
			return ResponseUtils.responseResult(false);
		}
		return JSONObject.toJSONString(insts).getBytes("UTF-8");
	}
    @Resource
	public void setProcessInsMappingService(ProcessInsMappingService processInsMappingService) {
		this.processInsMappingService = processInsMappingService;
	}
    @Resource
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
}
