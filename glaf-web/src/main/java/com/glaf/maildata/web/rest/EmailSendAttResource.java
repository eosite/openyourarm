package com.glaf.maildata.web.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.maildata.domain.EmailRecAtt;
import com.glaf.maildata.domain.EmailSendAtt;
import com.glaf.maildata.query.EmailRecAttQuery;
import com.glaf.maildata.query.EmailSendAttQuery;
import com.glaf.maildata.service.EmailSendAttService;
import com.glaf.maildata.util.*;

/**
 * 
 * Rest响应类
 *
 */

@Controller
@Path("/rs/transdata/emailSendAtt")
public class EmailSendAttResource {
	protected static final Log logger = LogFactory.getLog(EmailSendAttResource.class);

	protected EmailSendAttService emailSendAttService;

	@POST
	@Path("/deleteAll")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteAll(@Context HttpServletRequest request) throws IOException {
		String rowIds = request.getParameter("fileIds");
		if (rowIds != null) {
			List<String> ids = StringTools.split(rowIds);
			if (ids != null && !ids.isEmpty()) {
				emailSendAttService.deleteByIds(ids);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/delete")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] deleteById(@Context HttpServletRequest request) throws IOException {
		emailSendAttService.deleteById(request.getParameter("fileId"));
		return ResponseUtils.responseJsonResult(true);
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSendAttQuery query = new EmailSendAttQuery();
		Tools.populate(query, params);
		query.setDataRequest(dataRequest);
		EmailSendAttDomainFactory.processDataRequest(dataRequest);

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
		int total = emailSendAttService.getEmailSendAttCountByQueryCriteria(query);
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
			List<EmailSendAtt> list = emailSendAttService.getEmailSendAttsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailSendAtt emailSendAtt : list) {
					JSONObject rowJSON = emailSendAtt.toJsonObject();
					rowJSON.put("id", emailSendAtt.getFileId());
					rowJSON.put("emailSendAttId", emailSendAtt.getFileId());
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
		EmailSendAttQuery query = new EmailSendAttQuery();
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

		JSONObject result = new JSONObject();
		int total = emailSendAttService.getEmailSendAttCountByQueryCriteria(query);
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
			List<EmailSendAtt> list = emailSendAttService.getEmailSendAttsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (EmailSendAtt emailSendAtt : list) {
					JSONObject rowJSON = emailSendAtt.toJsonObject();
					rowJSON.put("id", emailSendAtt.getFileId());
					rowJSON.put("emailSendAttId", emailSendAtt.getFileId());
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
	@Path("/saveEmailSendAtt")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] saveEmailSendAtt(@Context HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		EmailSendAtt emailSendAtt = new EmailSendAtt();
		try {
			Tools.populate(emailSendAtt, params);

			emailSendAtt.setTopId(request.getParameter("topId"));
			emailSendAtt.setFileName(request.getParameter("fileName"));
			emailSendAtt.setCTime(RequestUtils.getDate(request, "cTime"));
			emailSendAtt.setFileSize(RequestUtils.getInt(request, "fileSize"));

			this.emailSendAttService.save(emailSendAtt);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.maildata.service.emailSendAttService")
	public void setEmailSendAttService(EmailSendAttService emailSendAttService) {
		this.emailSendAttService = emailSendAttService;
	}

	@GET
	@POST
	@Path("/view")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] view(@Context HttpServletRequest request) throws IOException {
		EmailSendAtt emailSendAtt = null;
		if (StringUtils.isNotEmpty(request.getParameter("fileId"))) {
			emailSendAtt = emailSendAttService.getEmailSendAtt(request.getParameter("fileId"));
		}
		JSONObject result = new JSONObject();
		if (emailSendAtt != null) {
			result = emailSendAtt.toJsonObject();
			Map<String, User> userMap = IdentityFactory.getUserMap();
			result.put("id", emailSendAtt.getFileId());
			result.put("emailSendAttId", emailSendAtt.getFileId());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 导出邮件附件
	 * 
	 * @param request
	 * @param response
	 */
	@GET
	@POST
	@Path("/downloadAtt")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void downloadAtt(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		String mailId = RequestUtils.getString(request, "mailId", "");
		String fileId = RequestUtils.getString(request, "fileId", "");
		EmailSendAtt emailSendAtt = null;
		if (StringUtils.isNotEmpty(fileId)) {
			emailSendAtt = emailSendAttService.getEmailSendAtt(fileId);
		} else if (StringUtils.isNotEmpty(mailId)) {
			List<EmailSendAtt> emailSendAtts = emailSendAttService.getEmailSendAttsByMailId(mailId);
			if (emailSendAtts != null && emailSendAtts.size() > 0) {
				emailSendAtt = emailSendAtts.get(0);
			}
		}
		if (emailSendAtt != null) {
			byte[] attbyte = emailSendAtt.getFileContent();
			ByteArrayInputStream in = new ByteArrayInputStream(attbyte);
			String filename = emailSendAtt.getFileName();
			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
			response.setContentType("application/OCTET-STREAM;charset=UTF-8");
			try {
				IOUtils.copy(in, response.getOutputStream());
				response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}