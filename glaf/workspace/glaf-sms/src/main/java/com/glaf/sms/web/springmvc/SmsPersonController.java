/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.sms.web.springmvc;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.sms.domain.SmsPerson;
import com.glaf.sms.query.SmsPersonQuery;
import com.glaf.sms.service.SmsPersonService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/smsPerson")
@RequestMapping("/sys/smsPerson")
public class SmsPersonController {
	protected static final Log logger = LogFactory.getLog(SmsPersonController.class);

	protected SmsPersonService smsPersonService;

	public SmsPersonController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SmsPerson smsPerson = smsPersonService.getSmsPerson(String.valueOf(x));
					if (smsPerson != null && (StringUtils.equals(smsPerson.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {

					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			SmsPerson smsPerson = smsPersonService.getSmsPerson(String.valueOf(id));
			if (smsPerson != null && (StringUtils.equals(smsPerson.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {

				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		SmsPerson smsPerson = smsPersonService.getSmsPerson(request.getParameter("id"));
		if (smsPerson != null) {
			request.setAttribute("smsPerson", smsPerson);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("smsPerson.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/sms/smsPerson/edit", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SmsPersonQuery query = new SmsPersonQuery();
		Tools.populate(query, params);
		Tools.populate(query,RequestUtils.getParameterMap(request));
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
			limit = 1;
		}

		JSONObject result = new JSONObject();
		int total = smsPersonService.getSmsPersonCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

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

			List<SmsPerson> list = smsPersonService.getSmsPersonsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SmsPerson smsPerson : list) {
					JSONObject rowJSON = smsPerson.toJsonObject();
					rowJSON.put("id", smsPerson.getId());
					rowJSON.put("rowId", smsPerson.getId());
					rowJSON.put("smsPersonId", smsPerson.getId());
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
	
	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SmsPersonQuery query = new SmsPersonQuery();
		Tools.populate(query, params);
		Tools.populate(query,RequestUtils.getParameterMap(request));
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = smsPersonService.getSmsPersonCountByQueryCriteria(query);
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

			List<SmsPerson> list = smsPersonService.getSmsPersonsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SmsPerson smsPerson : list) {
					JSONObject rowJSON = smsPerson.toJsonObject();
					rowJSON.put("id", smsPerson.getId());
					rowJSON.put("rowId", smsPerson.getId());
					rowJSON.put("smsPersonId", smsPerson.getId());
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

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/sms/smsPerson/list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveSmsPerson")
	public byte[] saveSmsPerson(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SmsPerson smsPerson = new SmsPerson();
		try {
			Tools.populate(smsPerson, params);
			smsPerson.setClientId(request.getParameter("clientId"));
			smsPerson.setName(request.getParameter("name"));
			smsPerson.setMobile(request.getParameter("mobile"));
			smsPerson.setLimit(RequestUtils.getInt(request, "limit"));
			smsPerson.setLocked(RequestUtils.getInt(request, "locked"));
			smsPerson.setCreateBy(actorId);
			this.smsPersonService.save(smsPerson);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.sms.service.smsPersonService")
	public void setSmsPersonService(SmsPersonService smsPersonService) {
		this.smsPersonService = smsPersonService;
	}
	
	@ResponseBody
	@RequestMapping("/able")
	public byte[] able(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String actorId = loginContext.getActorId();

		//用户id
		String ids = RequestUtils.getParameter(request, "ids");
		//禁用或启用，0为启用，1为禁用
		int able = RequestUtils.getInt(request, "able",0);
		SmsPerson smsPerson = new SmsPerson();
		try {
			List<String> list = new ArrayList();
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if(StringUtils.isNotEmpty(x)){
					list.add(x);
				}
			}
			this.smsPersonService.bulkAble(list,able);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") MultipartFile mFile) throws Exception {
		InputStream is = mFile.getInputStream();
		String fileName = mFile.getOriginalFilename();
		boolean flag = true;
		Sheet xssfSheet = null;
		Row xssfRow = null;
		Cell xssfXell = null;
		String str = null;
		List<SmsPerson> list = null;
		SmsPerson person = null;
		int lastRowNum = 0;
		String clientId = RequestUtils.getParameter(request, "clientId");
		Map<String, SmsPerson> personMap = null;
        // 对文件的合法性进行验
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            flag = false;
        }
		try (Workbook xssfWorkbook = flag ?new HSSFWorkbook(is) : new XSSFWorkbook(is)) {
			// 获取第一个工作薄
			xssfSheet = xssfWorkbook.getSheetAt(0);
			list = new ArrayList<SmsPerson>();
			lastRowNum = xssfSheet.getLastRowNum();
			
			if(lastRowNum > 0){
				personMap = new HashMap<String, SmsPerson>();
				SmsPersonQuery query = new SmsPersonQuery();
				query.clientId(clientId);
				List<SmsPerson> persons = smsPersonService.list(query);
				if (persons != null && !persons.isEmpty()) {
					for (SmsPerson p : persons) {
						personMap.put(p.getMobile(), p);
					}
				}
			}
			
			
			// 遍历excel表格，保存进去
			for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
				xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					person = new SmsPerson();
					flag = true;
					person.setClientId(clientId);
					for (int cell = 0; cell < 3; cell++) {
						xssfXell = xssfRow.getCell(cell, Row.CREATE_NULL_AS_BLANK); // 读取excel中的格子数据
						if(cell == 0){
							str = xssfXell.getStringCellValue();
							person.setName(str);
						}else if(cell == 1){
							xssfXell.setCellType(xssfXell.CELL_TYPE_STRING);
							str = xssfXell.getStringCellValue();
							if(StringUtils.isNotEmpty(str)){
								if (personMap.get(str) == null) {
									person.setMobile(str);
									personMap.put(str, person);
								}else{
									flag = false;
								}
							}else{
								flag = false;
							}
						}else if(cell == 2){
							if(str != null){
								try{
									xssfXell.setCellType(xssfXell.CELL_TYPE_STRING);
									str = xssfXell.getStringCellValue();
									person.setLimit(Integer.parseInt(str));
								}catch(Exception e){
									person.setLimit((int)xssfXell.getNumericCellValue());
								}
							}
						}
					}
					if(flag)
						list.add(person);
				}
			}
			smsPersonService.bulkInsert(list);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导入excel异常:"+e.getMessage());
			if(is != null){
				is.close();
			}
		}
		return null;
	}

}
