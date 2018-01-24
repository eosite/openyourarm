package com.glaf.isdp.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.Environment;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.RequestUtils;

@Controller("/isdp/table")
@RequestMapping("/isdp/table")
public class TableController {

	protected IDatabaseService databaseService;

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping(params = "method=tableHander")
	public ModelAndView tableHander(@Context HttpServletRequest request) {

		String elementId = RequestUtils.getString(request, "elementId", "");
		request.setAttribute("elementId", elementId);

		String objElementId = RequestUtils.getString(request, "objElementId", "");
		request.setAttribute("objElementId", objElementId);

		String fieldObjElementId = RequestUtils.getString(request, "fieldObjElementId", "");
		request.setAttribute("fieldObjElementId", fieldObjElementId);

		String data = RequestUtils.getString(request, "tableHanderJsonArray", "[]");
		try {
			if (StringUtils.isNotEmpty(data)) {
				data = new String(Base64.decodeBase64(data.getBytes()), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tableHanderJsonArray", data);

		return new ModelAndView("/isdp/table/table_hander");
	}

	@RequestMapping(params = "method=modifyField")
	public ModelAndView modifyField(@Context HttpServletRequest request) {
		// 保存字段JSON对象的HTML标签的ID
		String fieldObjElementId = RequestUtils.getString(request, "fieldObjElementId", "");
		request.setAttribute("fieldObjElementId", fieldObjElementId);

		String fieldJson = RequestUtils.getString(request, "fieldJson", "[]");
		try {
			if (StringUtils.isNotEmpty(fieldJson)) {
				fieldJson = new String(Base64.decodeBase64(fieldJson.getBytes()), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("fieldJson", fieldJson);

		String currentSystemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", currentSystemName);
		request.setAttribute("databaseCode", databaseCode);

		return new ModelAndView("/isdp/table/modify_field");
	}

	@RequestMapping(params = "method=selectField")
	public ModelAndView selectField(HttpServletRequest request) {
		// 保存字段的HTML标签的ID
		String fieldIdElementId = RequestUtils.getString(request, "fieldIdElementId", "");
		request.setAttribute("fieldIdElementId", fieldIdElementId);
		// 保存字段中文名称的HTML标签的ID
		String fieldNameElementId = RequestUtils.getString(request, "fieldNameElementId", "");
		request.setAttribute("fieldNameElementId", fieldNameElementId);
		// 保存字段JSON对象的HTML标签的ID
		String fieldObjElementId = RequestUtils.getString(request, "fieldObjElementId", "");
		request.setAttribute("fieldObjElementId", fieldObjElementId);

		String tableJson = RequestUtils.getString(request, "tableJson", "");
		String fieldJson = RequestUtils.getString(request, "fieldJson", "[]");
		try {
			if (StringUtils.isNotEmpty(tableJson)) {
				tableJson = new String(Base64.decodeBase64(tableJson.getBytes()), "GBK");
			}
			if (StringUtils.isNotEmpty(fieldJson)) {
				fieldJson = new String(Base64.decodeBase64(fieldJson.getBytes()), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tableJson", tableJson);
		request.setAttribute("fieldJson", fieldJson);

		String currentSystemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", currentSystemName);
		request.setAttribute("databaseCode", databaseCode);

		return new ModelAndView("/isdp/table/select_field");
	}

	@RequestMapping(params = "method=selectTable")
	public ModelAndView selectTable(@Context HttpServletRequest request) {

		// 保存表的HTML标签的ID
		String tableIdElementId = RequestUtils.getString(request, "tableIdElementId", "");
		request.setAttribute("tableIdElementId", tableIdElementId);
		// 保存表中文名称的HTML标签的ID
		String tableNameElementId = RequestUtils.getString(request, "tableNameElementId", "");
		request.setAttribute("tableNameElementId", tableNameElementId);

		String tableObjElementId = RequestUtils.getString(request, "tableObjElementId");
		request.setAttribute("tableObjElementId", tableObjElementId);

		String tableId = RequestUtils.getString(request, "tableId", "");
		request.setAttribute("tableId", tableId);

		String tableJson = RequestUtils.getString(request, "tableJson", "");
		if (StringUtils.isNotEmpty(tableJson)) {
			try {
				tableJson = new String(Base64.decodeBase64(tableJson.getBytes()), "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setAttribute("tableJson", tableJson);
		}

		String currentSystemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", currentSystemName);
		request.setAttribute("databaseCode", databaseCode);

		return new ModelAndView("/isdp/table/select_table");
	}

	@RequestMapping(params = "method=selectTreeField")
	public ModelAndView selectTreeField(HttpServletRequest request) {
		// 保存字段的HTML标签的ID
		String fieldIdElementId = RequestUtils.getString(request, "fieldIdElementId", "");
		request.setAttribute("fieldIdElementId", fieldIdElementId);
		// 保存字段中文名称的HTML标签的ID
		String fieldNameElementId = RequestUtils.getString(request, "fieldNameElementId", "");
		request.setAttribute("fieldNameElementId", fieldNameElementId);
		// 保存字段JSON对象的HTML标签的ID
		String fieldObjElementId = RequestUtils.getString(request, "fieldObjElementId", "");
		request.setAttribute("fieldObjElementId", fieldObjElementId);

		String tableJson = RequestUtils.getString(request, "tableJson", "");
		String fieldJson = RequestUtils.getString(request, "fieldJson", "[]");
		try {
			if (StringUtils.isNotEmpty(tableJson)) {
				tableJson = new String(Base64.decodeBase64(tableJson.getBytes()), "GBK");
			}
			if (StringUtils.isNotEmpty(fieldJson)) {
				fieldJson = new String(Base64.decodeBase64(fieldJson.getBytes()), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tableJson", tableJson);
		request.setAttribute("fieldJson", fieldJson);

		String currentSystemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", currentSystemName);
		request.setAttribute("databaseCode", databaseCode);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView("/isdp/table/" + view);
		}

		return new ModelAndView("/isdp/table/select_tree_field");
	}
}
