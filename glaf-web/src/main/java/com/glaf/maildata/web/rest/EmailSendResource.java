package com.glaf.maildata.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.maildata.domain.EmailSend;
import com.glaf.maildata.domain.EmailSendAtt;
import com.glaf.maildata.query.EmailSendQuery;
import com.glaf.maildata.service.EmailSendAttService;
import com.glaf.maildata.service.EmailSendService;
import com.glaf.maildata.util.EmailSendDomainFactory;
import com.glaf.sys.domain.ProjMuiprojlist;
import com.glaf.sys.service.ProjMuiprojlistService;
import com.glaf.ws.client.SendLinkApplyWebService;
import com.glaf.ws.client.SendLinkApplyWebServiceService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/transdata/emailSend")
public class EmailSendResource {
	protected static final Log logger = LogFactory.getLog(EmailSendResource.class);

	protected EmailSendService emailSendService;
	protected EmailSendAttService emailSendAttService;
	protected ProjMuiprojlistService projMuiprojlistService;
	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("ids");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				emailSendService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		emailSendService.deleteById(request.getParameter("id"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSendQuery query = new EmailSendQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		EmailSendDomainFactory.processDataRequest(dataRequest);

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
		int total = emailSendService.getEmailSendCountByQueryCriteria(query);
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
			List<EmailSend> list = emailSendService.getEmailSendsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailSend emailSend : list) {
					JSONObject rowJSON = emailSend.toJsonObject();
					rowJSON.put("id", emailSend.getId());
					rowJSON.put("emailSendId", emailSend.getId());
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
		EmailSendQuery query = new EmailSendQuery();
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
		int total = emailSendService.getEmailSendCountByQueryCriteria(query);
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
			List<EmailSend> list = emailSendService.getEmailSendsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailSend emailSend : list) {
					JSONObject rowJSON = emailSend.toJsonObject();
					rowJSON.put("id", emailSend.getId());
					rowJSON.put("emailSendId", emailSend.getId());
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
	@Path("/saveEmailSend")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEmailSend(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSend emailSend = new EmailSend();
		try {
			Tools.populate(emailSend, params);

			emailSend.setMsgId(request.getParameter("msgId"));
			emailSend.setInReplyTo(request.getParameter("inReplyTo"));
			emailSend.setFrom(request.getParameter("from"));
			emailSend.setTo(request.getParameter("to"));
			emailSend.setCc(request.getParameter("cc"));
			emailSend.setDate(RequestUtils.getDate(request, "date"));
			emailSend.setSubject(request.getParameter("subject"));
			emailSend.setReplyTo(request.getParameter("replyTo"));
			emailSend.setIntFlag(RequestUtils.getInt(request, "intFlag"));
			emailSend.setEmail(request.getParameter("email"));
			emailSend.setFromSysId(request.getParameter("fromSysId"));
			emailSend.setIntAction(RequestUtils.getInt(request, "intAction"));
			emailSend.setIntOperat(RequestUtils.getInt(request, "intOperat"));
			emailSend.setListNo(RequestUtils.getInt(request, "listNo"));
			emailSend.setToSysId(request.getParameter("toSysId"));

			this.emailSendService.save(emailSend);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@GET
	@POST
	@Path("/linkApply")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] linkApply(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String mailAddress = ParamUtils.getString(params, "address");
		if (StringUtils.isNotEmpty(mailAddress)) {
			// 发送互联邮件 WebService调用
			SendLinkApplyWebServiceService sendLinkApplyWebServiceService = new SendLinkApplyWebServiceService();
			SendLinkApplyWebService sendLinkApplyWebService = sendLinkApplyWebServiceService
					.getSendLinkApplyWebServicePort();
			//获取当前系统ID
			ProjMuiprojlist projMuiprojlist=projMuiprojlistService.getLocalProjMuiprojlist();
			String resultJson=sendLinkApplyWebService.send(projMuiprojlist.getSysId(), mailAddress);
			return resultJson.getBytes("UTF-8");
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.service.emailSendService")
	public void setEmailSendService(EmailSendService emailSendService) {
		this.emailSendService = emailSendService;
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.service.emailSendAttService")
	public void setEmailSendAttService(EmailSendAttService emailSendAttService) {
		this.emailSendAttService = emailSendAttService;
	}
	@javax.annotation.Resource(name = "com.glaf.sys.service.projMuiprojlistService")
	public void setProjMuiprojlistService(ProjMuiprojlistService projMuiprojlistService) {
		this.projMuiprojlistService = projMuiprojlistService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EmailSend EmailSend = null;
		String resultHtml = "";
		if (StringUtils.isNotEmpty(request.getParameter("mailId"))) {
			EmailSend = emailSendService.getEmailSend(request.getParameter("mailId"));
			// 加载模板
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDefaultEncoding("utf-8");
			configuration.setServletContextForTemplateLoading(request.getServletContext(), "/WEB-INF/conf/templates");
			Template temp = configuration.getTemplate("mail/view.tmpl");
			StringWriter stringWriter = new StringWriter();
			Map attributes = new HashMap();
			attributes.put("fromAddr", EmailSend.getFrom().replaceAll("<", "").replaceAll(">", ""));
			attributes.put("toAddr", EmailSend.getTo().replaceAll("<", "").replaceAll(">", ""));
			attributes.put("subject", EmailSend.getSubject());
			if (StringUtils.isNotEmpty(EmailSend.getHtml())) {
				attributes.put("contentHtml", EmailSend.getHtml());
			} else if (StringUtils.isNotEmpty(EmailSend.getText())) {
				attributes.put("contentText", EmailSend.getText());
			}
			attributes.put("contentText", EmailSend.getText());
			attributes.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(EmailSend.getDate()));
			attributes.put("contextPath", request.getContextPath());
			// 获取附件
			List<EmailSendAtt> emailSendAtts = emailSendAttService
					.getEmailSendAttsByMailId(request.getParameter("mailId"));
			if (emailSendAtts != null && emailSendAtts.size() > 0) {
				attributes.put("emailAtts", emailSendAtts);
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
}
