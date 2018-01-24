package com.glaf.etl.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Columns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.util.SelectSegmentJsonFactory;
import com.glaf.etl.domain.ETLDataSrc;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.domain.EtlTransferTask;
import com.glaf.etl.domain.EtlTransferTaskSrc;
import com.glaf.etl.domain.EtlTransferTaskTarget;
import com.glaf.etl.service.ETLDataSrcService;
import com.glaf.etl.service.ETLDataTransferService;
import com.glaf.etl.service.EtlTransferTaskService;
import com.glaf.etl.service.EtlTransferTaskSrcService;
import com.glaf.etl.service.EtlTransferTaskTargetService;
import com.glaf.etl.util.ETLDataSrcJsonFactory;
 

@Controller("/etl/model")
@RequestMapping("/etl/model")
public class ETLModelController {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ETLDataTransferService etlDataTransferService;
	@Autowired
	protected EtlTransferTaskService etlTransferTaskService;
	@Autowired
	protected EtlTransferTaskSrcService etlTransferTaskSrcService;
	@Autowired
	protected EtlTransferTaskTargetService etlTransferTaskTargetService;
	
	@Autowired
	protected ETLDataSrcService etlDataInService;
	
	/**
	 * 行转列工具
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/datatransfer_source")
	public ModelAndView etlSource(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/datasource", modelMap);
	}
	
	@RequestMapping("/datasrcedit")
	public ModelAndView datasrcedit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/datasrcedit", modelMap);
	}
	/**
	 * 行转列数据输入
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/datain")
	public ModelAndView datain(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/datain", modelMap);
	}
	
	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		
		ETLDataSrc etlDataSrc = etlDataInService.getETLDataSrc(request.getParameter("id"));
		
		String json = null;
		String columns = null;
	
		List<SelectSegment> list  = new ArrayList<SelectSegment>();
		DataSet dataSet = new DataSet();
		try {
			columns = new String(etlDataSrc.getColumnInfos(),"UTF-8");
			JSONArray columnsArray = JSON.parseArray(columns);
			for (Object obj : columnsArray) {
				JSONObject jo = (JSONObject) obj;
				SelectSegment segment = new SelectSegment();
				segment.setColumnName(jo.getString("columnName"));
				segment.setTitle(jo.getString("columnNameCN"));
				segment.setOutput("true");
				list.add(segment);
			}
			dataSet.setSelectSegments(list);
			dataSet.setId(request.getParameter("id"));
			dataSet.setName(etlDataSrc.getSourceName());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
//		try {
//			json = new String(etlDataSrc.getRestoreJson(),"UTF-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONObject jsonObject = new JSONObject();
//		DataSet dataSet = new DataSet();
//		if(StringUtils.isNotEmpty(json)){
//			jsonObject = JSONObject.parseObject(json);
//			JSONArray selectSegments = jsonObject.getJSONArray("selectSegments");
//			List<SelectSegment> list = SelectSegmentJsonFactory.arrayToList(selectSegments);
//			dataSet.setSelectSegments(list);
//			dataSet.setId(jsonObject.getString("id"));
//			dataSet.setName(etlDataSrc.getSourceName());
//		}
		
		request.setAttribute("dataSet", dataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataSet.preview");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}
		return new ModelAndView("/datamgr/etl/datasrc_preview");
	}
	
	
	
	@RequestMapping("/datatransfer_transfer")
	public ModelAndView etlTransfer(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/datatransfer", modelMap);
	}
	@RequestMapping("/transferedit")
	public ModelAndView transferedit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/transferedit", modelMap);
	}
	/**
	 * 行转列数据转换
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/transfer_config")
	public ModelAndView transfer(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/transferconfig", modelMap);
	}
	
	@RequestMapping("/transfer_task")
	public ModelAndView transferTask(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/transfer_task_list", modelMap);
	}
	
	
	@RequestMapping("/transfer_task_edit")
	public ModelAndView transferTaskEdit(HttpServletRequest request,ModelMap modelMap) {
		//RequestUtils.setRequestParameterToAttribute(request);
		String taskId = request.getParameter("taskId");
		if(StringUtils.isNotEmpty(taskId)){
			
			EtlTransferTask task = etlTransferTaskService.getEtlTransferTask(taskId);
			EtlTransferTaskSrc taskSrc = etlTransferTaskSrcService.getEtlTransferTaskSrcByTaskId(taskId);
			EtlTransferTaskTarget taskTarget = etlTransferTaskTargetService.getEtlTransferTaskTargetByTaskId(taskId);
			String transferId = task.getTransId_();
			ETLDataTransfer transfer = etlDataTransferService.getETLDataTransfer(transferId);
			
//			JSONObject taskObj = task.toJsonObject();
			modelMap.put("task", task);		
			modelMap.put("transfer", transfer);
			JSONObject taskSrcObj = taskSrc.toJsonObject();
			modelMap.put("taskSrc", taskSrcObj);		
			JSONObject taskTargetObj = taskTarget.toJsonObject();
			modelMap.put("taskTarget", taskTargetObj);
		}
		return new ModelAndView("/datamgr/etl/transfer_task_edit", modelMap);
	}
	
	@RequestMapping("/transfer_task_edit_selecttrans")
	public ModelAndView transferTaskEditSelectTrans(HttpServletRequest request,ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/datamgr/etl/transfer_task_edit_selecttrans", modelMap);
	}
	
}
