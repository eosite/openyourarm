package com.glaf.etl.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import org.apache.commons.codec.binary.Base64;
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
import com.glaf.etl.query.ETLDataSrcQuery;
import com.glaf.etl.query.ETLDataTransferQuery;
import com.glaf.etl.service.ETLDataSrcService;
import com.glaf.etl.service.ETLDataTransferService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.etl.util.ETLDataSrcJsonFactory;
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
@Controller("/datatransfer")
@RequestMapping("/datatransfer")
public class ETLDataTransferController {
	protected static final Log logger = LogFactory.getLog(ETLDataTransferController.class);

	@Autowired
	protected ETLDataSrcService etlDataInService;
	
	@Autowired
	protected ETLDataTransferService etlDataTransferService;
	
	@Autowired
	protected MutilDatabaseBean mutilDatabaseBean;
	
	@Autowired
	protected ITablePageService tablePageService;
	
	@Autowired
	protected IDatabaseService databaseService;

	
	
	
	@RequestMapping("/listTransferConfigs")
	@ResponseBody
	public byte[] listTransferConfigs(@Context HttpServletRequest request,
			@RequestBody DataRequest dataRequest) throws IOException {
		
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		
		ETLDataTransferQuery query = new ETLDataTransferQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		
		if(!loginContext.isSystemAdministrator()){
		  String actorId = loginContext.getActorId();
		  query.createBy(actorId);
		}
		
		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
		}

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
		
		if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
			SortDescriptor sort = dataRequest.getSort().get(0);
			orderName = sort.getField();
			order = sort.getDir();
			logger.debug("orderName:" + orderName);
			logger.debug("order:" + order);
		}
//		orderName = ParamUtils.getString(params, "sort[0][field]");
//		order = ParamUtils.getString(params, "sort[0][dir]");
		
		JSONObject result = new JSONObject();
		int total = etlDataTransferService.getETLDataTransferCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);
			
            if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}
            
            Map<String, User> userMap = IdentityFactory.getUserMap();
			List<ETLDataTransfer> list = etlDataTransferService.getETLDataTransfersByQueryCriteria(start,limit,query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (ETLDataTransfer eTLDataTransfer : list) {
					JSONObject rowJSON = eTLDataTransfer.toJsonObject();
					rowJSON.put("id", eTLDataTransfer.getId());
					rowJSON.put("rowId", eTLDataTransfer.getId());
					rowJSON.put("eTLDataSrcId", eTLDataTransfer.getId());
                    rowJSON.put("startIndex", ++start);
                    User createBy = userMap.get(eTLDataTransfer.getCreateBy());
					if (createBy != null) {
						rowJSON.put("createByName", createBy.getName());
					}
					User updateBy = userMap.get(eTLDataTransfer.getUpdateBy());
					if (updateBy != null) {
						rowJSON.put("updateByName", updateBy.getName());
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
	

	@RequestMapping("/editTransferConfig")
	@ResponseBody
	public byte[] editTransferConfig(@Context HttpServletRequest request,ETLDataTransfer transfer)
			throws IOException {
		
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		ETLDataTransfer src = null;
		String message = null;
		if(StringUtils.isNotEmpty(transfer.getId())){
			src = etlDataTransferService.getETLDataTransfer(transfer.getId());			
			if(StringUtils.isNotEmpty(transfer.getTransName())){			
				src.setTransName(transfer.getTransName());
			}
			
			message = "数据源编辑成功";
		}else{
			src = transfer;
			
			src.setCreateBy(loginContext.getActorId());
			Date date = new Date();
			src.setCreateTime(date);
			
			message = "新增数据源成功";
			
		}
		etlDataTransferService.save(src);
		JSONObject result = new JSONObject();
		result.put("message",message);	
		return result.toString().getBytes("UTF-8");
	}
	
	@RequestMapping("/deleteTransferConfig")
	@ResponseBody
	public byte[] deleteTransferConfig(@Context HttpServletRequest request)
			throws IOException {
		
		String sourceId = request.getParameter("sourceId");
		etlDataTransferService.deleteById(sourceId);
		JSONObject result = new JSONObject();
		result.put("message","删除数据源成功");	
		return result.toString().getBytes("UTF-8");
	}
	
	
	@RequestMapping("/getSelectSource")
	@ResponseBody
	public byte[] getSelectSource(@Context HttpServletRequest request) throws IOException {
		
		String sourceId = request.getParameter("sourceId");
		ETLDataSrc model = etlDataInService.getETLDataSrc(sourceId);
		String columns = new String(model.getColumnInfos());

		return columns.getBytes("UTF-8");
	}
	
	
	@RequestMapping("/dataTransferSave")
	@ResponseBody
	public byte[] dataTransferSava(@Context HttpServletRequest request) throws IOException {
		
		String sourceId = request.getParameter("sourceId");
		String dataTransferId = request.getParameter("dataTransferId");
		String json = request.getParameter("jsonResult");
		JSONArray jsonObject = JSON.parseArray(json);
		String actorId = RequestUtils.getActorId(request);
		
		ETLDataTransfer model = new ETLDataTransfer();
		JSONArray groupArray = new JSONArray();
		JSONArray transferArray = new JSONArray();
		JSONArray valueArray = new JSONArray();
		JSONObject jo = null;
		String routRule = "";
		for (Object object : jsonObject) {
			jo = (JSONObject) object;
			String config = jo.getString("config");
			if(StringUtils.isNotEmpty(config)&&"分组字段".equals(config)){
				groupArray.add(jo);
			}else if(StringUtils.isNotEmpty(config)&&"行转列字段".equals(config)){
				transferArray.add(jo);
			}else if(StringUtils.isNotEmpty(config)&&"值字段".equals(config)){
				valueArray.add(jo);
			}
			Boolean boo = jo.getBoolean("routRule");
			if(boo!=null&&boo){
				routRule = jo.getString("columnName")+ ","+ routRule;
			}
		}
		
		
		if(routRule.lastIndexOf(",")!=-1){			
			routRule = routRule.substring(0,routRule.lastIndexOf(","));
		}
		model.setRoutRule(routRule.getBytes("UTF-8"));
		
		if (StringUtils.isNotEmpty(dataTransferId)) {
			model.setId(dataTransferId);
		}
		model.setGroupColumns(groupArray.toJSONString().getBytes("UTF-8"));
		model.setTransferColumns(transferArray.toJSONString().getBytes("UTF-8"));
		model.setValueColumns(valueArray.toJSONString().getBytes("UTF-8"));
		model.setDataSrcId(sourceId);
//		model.setRestoreJson(json);
		
		model.setUpdateBy(actorId);
		Date date = new Date();
		model.setUpdateTime(date);
		
	    etlDataTransferService.save(model);
	    String message ="保存配置信息成功";
		
		JSONObject result = new JSONObject();
		result.putAll(model.toJsonObject());
		result.put("message", message);
		return result.toJSONString().getBytes("UTF-8");
		
	}
	
	@RequestMapping("/getTransferById")
	@ResponseBody
	public byte[] getTransferById(@Context HttpServletRequest request)
			throws IOException {
		String transferId = request.getParameter("transferId");
		ETLDataTransfer model = etlDataTransferService.getETLDataTransfer(transferId);
		JSONArray columns = new JSONArray();
		String sourceId = "";
		if (model != null) {
			JSONArray groupColumns = new JSONArray();
			JSONArray transferColumns = new JSONArray();
			JSONArray valueColumns = new JSONArray();
			if(model.getGroupColumns()!=null){
				groupColumns = (JSONArray) JSONArray.parse(new String(model.getGroupColumns(),"UTF-8"));				
			}
			if(model.getTransferColumns()!=null){
				transferColumns = (JSONArray) JSONArray.parse(new String(model.getTransferColumns(),"UTF-8"));				
			}
			if(model.getValueColumns()!=null){				
				valueColumns = (JSONArray) JSONArray.parse(new String(model.getValueColumns(),"UTF-8"));
			}
			columns.addAll(groupColumns);
			columns.addAll(transferColumns);
			columns.addAll(valueColumns);
			sourceId = model.getDataSrcId();
		}
		
		JSONObject result = new JSONObject();
		result.put("columns", columns);
		result.put("sourceId",sourceId);
		return result.toJSONString().getBytes("UTF-8");
	}





}
