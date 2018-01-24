package com.glaf.etl.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.DataSetDomainFactory;
import com.glaf.datamgr.util.DataSetJsonFactory;
//import com.glaf.datamgr.jdbc.SqlQueryManager
import com.glaf.etl.domain.ETLDataSrc;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.domain.EtlTransferTask;
import com.glaf.etl.domain.EtlTransferTaskConfig;
import com.glaf.etl.domain.EtlTransferTaskSrc;
import com.glaf.etl.domain.EtlTransferTaskTarget;
import com.glaf.etl.query.ETLDataSrcQuery;
import com.glaf.etl.query.ETLDataTransferQuery;
import com.glaf.etl.query.EtlTransferTaskConfigQuery;
import com.glaf.etl.service.ETLDataSrcService;
import com.glaf.etl.service.ETLDataTransferService;
import com.glaf.etl.service.EtlTransferTaskConfigService;
import com.glaf.etl.service.EtlTransferTaskService;
import com.glaf.etl.service.EtlTransferTaskSrcService;
import com.glaf.etl.service.EtlTransferTaskTargetService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.etl.util.ETLDataSrcJsonFactory;
import com.glaf.etl.util.EtlTransferTaskJsonFactory;
import com.glaf.etl.util.EtlTransferTaskSrcJsonFactory;
import com.glaf.etl.util.EtlTransferTaskTargetJsonFactory;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.isdp.util.JSONConvertUtil;

/**
 * 
 * 
 * @author lvd
 *
 */
@Controller("/transferTask")
@RequestMapping("/transferTask")
public class ETLTransferTaskController {
	protected static final Log logger = LogFactory.getLog(ETLTransferTaskController.class);

	
	@Autowired
	protected ETLDataTransferService etlDataTransferService;
	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;
	@Autowired
	protected ITablePageService tablePageService;
	
	
	
	@Autowired
	protected IDatabaseService databaseService;
	@Autowired
	protected ETLDataSrcService etlDataInService;
	@Autowired
	protected EtlTransferTaskService etlTransferTaskService;
	@Autowired
	protected EtlTransferTaskSrcService etlTransferTaskSrcService;
	@Autowired
	protected EtlTransferTaskTargetService etlTransferTaskTargetService;
	@Autowired
	protected EtlTransferTaskConfigService etlTransferTaskConfigService;
	

	
	private static Map<String, String> fieldMapping = new HashMap<String, String>();
	static {
		fieldMapping.put("taskName_","t.TASK_NAME_");
		fieldMapping.put("taskDesc_","t.TASK_DESC_");
		fieldMapping.put("srcDatabaseName_","SRCDATABASENAME_");
		fieldMapping.put("targetDatabaseName_","TARGETDATABASENAME_");
		fieldMapping.put("createBy_","t.CREATEBY_");
		fieldMapping.put("createTime_","t.CREATETIME_");
	}
	
	
	@RequestMapping("/listAllTask")
	@ResponseBody
	public byte[] listTransferConfigs(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EtlTransferTaskConfigQuery query = new EtlTransferTaskConfigQuery();
		
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
	 
		
		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();
		start = (pageNo - 1) * limit;
		
		if (start < 0) {
			start = 0;
		}
		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}
		
//		orderName = ParamUtils.getString(params, "sort[0][field]");
//		order = ParamUtils.getString(params, "sort[0][dir]");
		SortDescriptor sort = null;
		if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
			sort = dataRequest.getSort().get(0);
			if(StringUtils.isNotEmpty(sort.getField())){
				if(fieldMapping.containsKey(sort.getField())){
					query.setOrderBy(fieldMapping.get(sort.getField()));
					if(StringUtils.isNotEmpty(sort.getDir())){
						query.setOrderBy(query.getOrderBy() + " " + sort.getDir());
					}
				}
			}
		}
		
		JSONObject result = new JSONObject();
		int total = etlTransferTaskConfigService.getEtlTransferTaskCount();
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);
			
            Map<String, User> userMap = IdentityFactory.getUserMap();
            
            
          //  query.setOrderBy("t.TASK_DESC_ asc");
            //query.setSortOrder("asc");
            
            List<EtlTransferTaskConfig> list = etlTransferTaskConfigService.getEtlTransferTasks(start, limit,query);
			
            if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (EtlTransferTaskConfig model : list) {
					JSONObject rowJSON = new JSONObject();
					rowJSON.put("id_", model.getId_());
					if (model.getTaskName_() != null) {
						rowJSON.put("taskName_", model.getTaskName_());
					} 
					if (model.getTaskDesc_() != null) {
						rowJSON.put("taskDesc_", model.getTaskDesc_());
					}
					if (model.getCreateBy_() != null) {
						rowJSON.put("createBy_", model.getCreateBy_());
					}
					if (model.getCreateTime_() != null) {
						 rowJSON.put("createTime_", DateUtils.getDate(model.getCreateTime_()));
	                }
					if (model.getTaskDesc_() != null) {
						rowJSON.put("taskDesc_", model.getTaskDesc_());
					}
					if(model.getSrcDatabaseName_()!=null){
						String taskDatabaseName_ = model.getSrcDatabaseName_();
						rowJSON.put("srcDatabaseName_", taskDatabaseName_);
					}
					if(model.getTargetDatabaseName_()!=null){
						String taskDatabaseName_ = model.getTargetDatabaseName_();
						rowJSON.put("targetDatabaseName_", taskDatabaseName_);
					}
					rowsJSON.add(rowJSON);
				}
			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	
	
	@RequestMapping("/saveTask")
	@ResponseBody
	public byte[] saveTask(@Context HttpServletRequest request) throws IOException {
		
		
		String params = request.getParameter("params");
		JSONObject result = new JSONObject();
		String message,status = null;
		
		if(StringUtils.isNotEmpty(params)){
			JSONObject paramsObj= JSON.parseObject(params);
			JSONObject taskSrcObj = paramsObj.getJSONObject("taskSrc");
			JSONObject taskTragetObj = paramsObj.getJSONObject("taskTraget");	
			//根据id查询是否有记录
			String taskId = paramsObj.getString("id_");
			EtlTransferTask  task =null;
			EtlTransferTaskSrc  taskSrc = null;
			EtlTransferTaskTarget taskTarget = null;
			if(StringUtils.isNotEmpty(taskId)){
				task = etlTransferTaskService.getEtlTransferTask(taskId);
				taskSrc = etlTransferTaskSrcService.getEtlTransferTaskSrcByTaskId(taskId);
				taskTarget = etlTransferTaskTargetService.getEtlTransferTaskTargetByTaskId(taskId);
			}
			//转换传入参数
			EtlTransferTask etlTransferTask = new EtlTransferTask();
			EtlTransferTaskSrc etlTransferTaskSrc = new EtlTransferTaskSrc();
			EtlTransferTaskTarget etlTransferTaskTarget = new EtlTransferTaskTarget();
			String actorId = RequestUtils.getActorId(request);
			Date date = new Date();
			
			if(paramsObj!=null){
				etlTransferTask = EtlTransferTaskJsonFactory.jsonToObject(paramsObj);
			}
			if(taskSrcObj!=null){
				etlTransferTaskSrc = EtlTransferTaskSrcJsonFactory.jsonToObject(taskSrcObj);
				if(etlTransferTaskSrc.getQuerySQL_()==null){//用户没有输入query后填入源内容中的sql	
					String srcId= taskSrcObj.getString("srcId_");
					ETLDataSrc etlDataSrc = etlDataInService.getETLDataSrc(srcId);
					etlTransferTaskSrc.setQuerySQL_(etlDataSrc.getSql());
				}
				Database dBase = databaseService.getDatabaseById(etlTransferTaskSrc.getTaskConnId_()); 
				etlTransferTaskSrc.setTaskDatabaseName_(dBase.getDbname());//设置数据库名称
			}
			if(taskTragetObj!=null){
				etlTransferTaskTarget = EtlTransferTaskTargetJsonFactory.jsonToObject(taskTragetObj);
				Database dBase2 = databaseService.getDatabaseById(etlTransferTaskSrc.getTaskConnId_()); 
				etlTransferTaskSrc.setTaskDatabaseName_(dBase2.getDbname());//设置数据库名称
			}		
			
			if(task!=null){ //覆盖参数
				etlTransferTask.setId_(task.getId_());
				etlTransferTask.setUpdateBy_(actorId);
				etlTransferTask.setUpdateTime_(date);
		    }else{
		    	etlTransferTask.setCreateBy_(actorId);
				etlTransferTask.setCreateTime_(date);
		    }
			
		    if(taskSrc!=null){ //覆盖参数
		    	etlTransferTaskSrc.setId_(taskSrc.getId_());
		    	etlTransferTaskSrc.setUpdateBy_(actorId);
		    	etlTransferTaskSrc.setUpdateTime_(date);
		    }else{
		    	etlTransferTaskSrc.setCreateBy_(actorId);
				etlTransferTaskSrc.setCreateTime_(date);
		    }
		    
		    if(taskTarget!=null){ //覆盖参数
		    	etlTransferTaskTarget.setId_(taskTarget.getId_());
		    	etlTransferTaskTarget.setUpdateBy_(actorId);
		    	etlTransferTaskTarget.setUpdateTime_(date);
		    }else{
		    	etlTransferTaskTarget.setCreateBy_(actorId);
				etlTransferTaskTarget.setCreateTime_(date);
		    }	
			
			etlTransferTaskConfigService.save(etlTransferTask,etlTransferTaskSrc,etlTransferTaskTarget);
			
			status="200";
			message="保存成功";
		}else{
			status="100";
			message = "请输入参数！";
		}
		
		result.put("message", message);
		result.put("status", status);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	
	
	@RequestMapping("/deleteTask")
	@ResponseBody
	public byte[] deleteTransferConfig(@Context HttpServletRequest request)
			throws IOException {
		
		String taskId = request.getParameter("taskId");
		etlTransferTaskConfigService.deleteById(taskId);
		JSONObject result = new JSONObject();
		result.put("message","删除数据源成功");	
		return result.toString().getBytes("UTF-8");
	}
	



}
