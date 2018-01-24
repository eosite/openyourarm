package com.glaf.table.tool.web.springmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.ResponseUtils;
import com.glaf.table.tool.model.FieldModel;
import com.glaf.table.tool.model.TableModel;
import com.glaf.table.tool.service.IBaseTable2DefinedService;

/**
 * 数据表到定义平台基础表的映射
 * <p>
 * 添加数据至cell_data_table、interface、cell_data_field中，以便能在定义平台中选择表
 * 
 * @author Rocky 2016-01-26
 *
 */

@Controller("/table/mapping")
@RequestMapping("/table/mapping")
public class BaseTable2DefinedMappingController {

	@Autowired
	protected IBaseTable2DefinedService baseTable2DefinedService;
	
	@RequestMapping("/tool/{category}")
	public ModelAndView category(HttpServletRequest request,@PathVariable String category) throws Exception{
		ModelAndView modelAndView = new ModelAndView("/table/tool/"+category);
		
		return modelAndView;
	}

	/**
	 * @return
	 */
	@RequestMapping(params = "method=tablesJson")
	@ResponseBody
	public byte[] tablesJson(HttpServletRequest request) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		String keywords = request.getParameter("keywords");
		params.put("keywords", keywords);
		params.put("searchType", request.getParameter("searchType"));
		List<TableModel> tables = baseTable2DefinedService.getTables(params);
		JSONArray rows = new JSONArray();
		for(TableModel model : tables){
			JSONObject obj = new JSONObject();
			obj.put("tableName", model.getTableName());
			obj.put("name", model.getName());
			rows.add(obj);
		}
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", rows.size());

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(params = "method=fieldList")
	public ModelAndView fieldList(HttpServletRequest request) {

		String tableName = request.getParameter("table");
		if (StringUtils.isEmpty(tableName)) {
			// 如果没有表名
			return null;
		}

		TableModel table = baseTable2DefinedService.getTable(tableName);
		request.setAttribute("table", table);

		List<FieldModel> fields = baseTable2DefinedService
				.getFieldsByTableName(tableName);
		request.setAttribute("fields", fields);

		return new ModelAndView("/table/tool/field_list");
	}
	
	@RequestMapping(params = "method=fieldsJson")
	@ResponseBody
	public byte[] fieldsJson(HttpServletRequest request) throws Exception {

		String tableName = request.getParameter("table");
		if (StringUtils.isEmpty(tableName)) {
			// 如果没有表名
			return null;
		}

		TableModel table = baseTable2DefinedService.getTable(tableName);
		request.setAttribute("table", table);

		List<FieldModel> fields = baseTable2DefinedService
				.getFieldsByTableName(tableName);
		JSONArray rows = new JSONArray();
		for(FieldModel model : fields){
			JSONObject obj = new JSONObject();
			obj.put("fieldName", model.getFieldName());
			obj.put("fieldType", model.getFieldType());
			obj.put("fieldLength", model.getFieldLength());
			obj.put("name", model.getName());
			obj.put("listNo", model.getListNo());
			rows.add(obj);
		}
		JSONObject result = new JSONObject();
		result.put("rows", rows);
		result.put("total", rows.size());

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(params = "method=genMapping")
	@ResponseBody
	public byte[] genMapping(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			String tableName = request.getParameter("table");
			String fieldNames = request.getParameter("fieldNames");
			String names = request.getParameter("names");
			String tableDescription = request.getParameter("tableDescription");

			String[] fieldNameArr = fieldNames.split(";");
			String[] nameArr = names.split(";");

			Map<String, FieldModel> fieldMap = baseTable2DefinedService
					.getFieldsMapByTableName(tableName);

			List<FieldModel> saveList = new ArrayList<FieldModel>();
			for (int i = 0; i < fieldNameArr.length; i++) {
				String fieldName = fieldNameArr[i];
				FieldModel model = fieldMap.get(fieldName);
				model.setName(nameArr[i]);
				saveList.add(model);
			}

			baseTable2DefinedService
					.save(tableName, tableDescription, saveList);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}

	@RequestMapping(params = "method=clearMapping")
	@ResponseBody
	public byte[] clearMapping(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String tableName = request.getParameter("table");
			baseTable2DefinedService.clearMapping(tableName);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
	}
}
