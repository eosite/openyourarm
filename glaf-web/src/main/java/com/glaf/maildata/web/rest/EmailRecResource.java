package com.glaf.maildata.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.IServerEntityService;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.maildata.domain.EmailRec;
import com.glaf.maildata.domain.EmailRecAtt;
import com.glaf.maildata.query.EmailRecQuery;
import com.glaf.maildata.service.EmailRecAttService;
import com.glaf.maildata.service.EmailRecService;
import com.glaf.maildata.util.EmailRecDomainFactory;
import com.glaf.mqdata.service.MQManagerService;
import com.glaf.ws.client.ProcessMailDataWebService;
import com.glaf.ws.client.ProcessMailDataWebServiceService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/transdata/emailRec")
public class EmailRecResource {
	protected static final Log logger = LogFactory.getLog(EmailRecResource.class);

	protected EmailRecService emailRecService;
	protected EmailRecAttService emailRecAttService;
	protected IDatabaseService databaseService;
	protected MQManagerService mqManagerService;
	protected IServerEntityService serverEntityService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("iDs");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				emailRecService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		emailRecService.deleteById(request.getParameter("iD"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRecQuery query = new EmailRecQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		EmailRecDomainFactory.processDataRequest(dataRequest);

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
		}
		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = emailRecService.getEmailRecCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<EmailRec> list = emailRecService.getEmailRecsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailRec emailRec : list) {
					JSONObject rowJSON = emailRec.toJsonObject();
					rowJSON.put("id", emailRec.getID());
					rowJSON.put("emailRecId", emailRec.getID());
					rowJSON.put("startIndex", ++start);
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

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRecQuery query = new EmailRecQuery();
		Tools.populate(query, params);
		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;
		String mailDate = ParamUtils.getString(params, "maildate");
		if (StringUtils.isNoneEmpty(mailDate)) {
			Calendar cr = Calendar.getInstance();
			cr.set(Integer.parseInt(mailDate.split("-")[0]), Integer.parseInt(mailDate.split("-")[1]),
					Integer.parseInt(mailDate.split("-")[2]), 0, 0, 0);
			query.setDateGreaterThanOrEqual(cr.getTime());
			Calendar cr2 = Calendar.getInstance();
			cr2.set(Integer.parseInt(mailDate.split("-")[0]), Integer.parseInt(mailDate.split("-")[1]),
					Integer.parseInt(mailDate.split("-")[2]), 23, 59, 59);
			query.setDateLessThanOrEqual(cr2.getTime());
		}
		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = emailRecService.getEmailRecCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<EmailRec> list = emailRecService.getEmailRecsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailRec emailRec : list) {
					JSONObject rowJSON = emailRec.toJsonObject();
					rowJSON.put("id", emailRec.getID());
					rowJSON.put("emailRecId", emailRec.getID());
					rowJSON.put("startIndex", ++start);
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

	@GET
	@POST
	@Path("/news")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] news(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRecQuery query = new EmailRecQuery();
		Tools.populate(query, params);
		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "easyui";
		}
		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}
		Date currDate = new Date();
		Calendar cr = Calendar.getInstance();
		cr.setTime(currDate);
		cr.add(Calendar.DATE, -7);
		query.setDateGreaterThanOrEqual(cr.getTime());
		query.setDateLessThanOrEqual(currDate);
		JSONObject result = new JSONObject();
		int total = emailRecService.getEmailRecCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<EmailRec> list = emailRecService.getEmailRecsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailRec emailRec : list) {
					JSONObject rowJSON = emailRec.toJsonObject();
					rowJSON.put("id", emailRec.getID());
					rowJSON.put("emailRecId", emailRec.getID());
					rowJSON.put("startIndex", ++start);
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

	@POST
	@Path("/saveEmailRec")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEmailRec(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailRec emailRec = new EmailRec();
		try {
			Tools.populate(emailRec, params);

			emailRec.setEmail(request.getParameter("email"));
			emailRec.setMsgId(request.getParameter("msgId"));
			emailRec.setInReplyTo(request.getParameter("inReplyTo"));
			emailRec.setFrom(request.getParameter("from"));
			emailRec.setTo(request.getParameter("to"));
			emailRec.setCc(request.getParameter("cc"));
			emailRec.setDate(RequestUtils.getDate(request, "date"));
			emailRec.setSubject(request.getParameter("subject"));
			emailRec.setReplyTo(request.getParameter("replyTo"));
			emailRec.setIntFlag(RequestUtils.getInt(request, "intFlag"));
			emailRec.setGuidFrom(request.getParameter("guidFrom"));
			emailRec.setFromSysId(request.getParameter("fromSysId"));
			emailRec.setIntAction(RequestUtils.getInt(request, "intAction"));
			emailRec.setIntOperat(RequestUtils.getInt(request, "intOperat"));
			emailRec.setListNo(RequestUtils.getInt(request, "listNo"));
			emailRec.setToSysId(request.getParameter("toSysId"));

			this.emailRecService.save(emailRec);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.service.emailRecService")
	public void setEmailRecService(EmailRecService emailRecService) {
		this.emailRecService = emailRecService;
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.service.emailRecAttService")
	public void setEmailRecAttService(EmailRecAttService emailRecAttService) {
		this.emailRecAttService = emailRecAttService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setMqManagerService(MQManagerService mqManagerService) {
		this.mqManagerService = mqManagerService;
	}

	@javax.annotation.Resource
	public void setServerEntityService(IServerEntityService serverEntityService) {
		this.serverEntityService = serverEntityService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.TEXT_HTML })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EmailRec emailRec = null;
		String resultHtml = "";
		if (StringUtils.isNotEmpty(request.getParameter("mailId"))) {
			emailRec = emailRecService.getEmailRec(request.getParameter("mailId"));
			// 加载模板
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setServletContextForTemplateLoading(request.getServletContext(), "/WEB-INF/conf/templates");
			Template temp = configuration.getTemplate("mail/view.tmpl");
			StringWriter stringWriter = new StringWriter();
			Map attributes = new HashMap();
			attributes.put("fromAddr", emailRec.getFrom().replaceAll("<", "").replaceAll(">", ""));
			attributes.put("toAddr", emailRec.getTo().replaceAll("<", "").replaceAll(">", ""));
			attributes.put("subject", emailRec.getSubject());
			if (StringUtils.isNotEmpty(emailRec.getHtml())) {
				attributes.put("contentHtml", emailRec.getHtml());
			} else if (StringUtils.isNotEmpty(emailRec.getText())) {
				attributes.put("contentText", emailRec.getText());
			}
			if (emailRec.getDate() != null) {
				attributes.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(emailRec.getDate()));
			}
			attributes.put("contextPath", request.getContextPath());
			// 获取附件
			List<EmailRecAtt> emailRecAtts = emailRecAttService.getEmailRecAttsByMailId(request.getParameter("mailId"));
			if (emailRecAtts != null && emailRecAtts.size() > 0) {
				attributes.put("emailAtts", emailRecAtts);
			}
			try {
				temp.process(attributes, stringWriter);
				resultHtml = stringWriter.toString();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultHtml.getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/receive")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] receive(@Context HttpServletRequest request) throws IOException {
		emailRecService.readMail();
		// 获取当前系统配置
		String defaultDatabse = Environment.DEFAULT_SYSTEM_NAME;
		Database database = databaseService.getDatabaseByCode(defaultDatabse);
		if (database.getServerId() != null && database.getServerId() > 0) {
			// 获取MQ服务器配置
			ServerEntity serverEntity = serverEntityService.getServerEntityById(database.getServerId());
			if (serverEntity != null) {
				try {
					mqManagerService.receiveDataFromMQToDb(serverEntity, database);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("从MQ接收消息出错："+e.getMessage());
				}
			}else{
				logger.info("MQ服务器[id="+database.getServerId()+"]配置不存在！");
			}
		}else{
			logger.info("未选择关联的MQ服务器！");
		}
		// 发送互联邮件 WebService调用
		ProcessMailDataWebServiceService processMailDataWebServiceService = new ProcessMailDataWebServiceService();
		ProcessMailDataWebService processMailDataWebService = processMailDataWebServiceService
				.getProcessMailDataWebServicePort();
		String resultJson = processMailDataWebService.processAll();
		return ResponseUtils.responseJsonResult(true);
	}
}
