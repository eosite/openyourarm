package com.glaf.theme.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.RedisFactory;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;
import com.glaf.theme.service.*;
import com.glaf.theme.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/theme/sysThemeTmpControl")
@RequestMapping("/theme/sysThemeTmpControl")
public class SysThemeTmpControlController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpControlController.class);

	protected SysThemeTmpControlService sysThemeTmpControlService;
	// 二进制信息
	@Autowired
	protected SysThemeTmpBytearrayService sysThemeTmpBytearrayService;

	public SysThemeTmpControlController() {

	}

	@javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpControlService")
	public void setSysThemeTmpControlService(SysThemeTmpControlService sysThemeTmpControlService) {
		this.sysThemeTmpControlService = sysThemeTmpControlService;
	}

	/**
	 * 设置默认主题
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/setDefaultTheme")
	@ResponseBody
	public byte[] setDefaultTheme(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControl sysThemeTmpControl = new SysThemeTmpControl();
		try {
			sysThemeTmpControl.setControlId((String) params.get("controlId"));
			sysThemeTmpControl.setDefaultFlag(Integer.valueOf((String) params.get("defaultFlag")));
			this.sysThemeTmpControlService.save(sysThemeTmpControl);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveSysThemeTmpControl")
	public byte[] saveSysThemeTmpControl(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControl sysThemeTmpControl = new SysThemeTmpControl();
		try {
			Tools.populate(sysThemeTmpControl, params);
			Date nowDate = new Date();
			if (null == sysThemeTmpControl.getControlId() || sysThemeTmpControl.getControlId().isEmpty()) {
				sysThemeTmpControl.setCreateBy(actorId);
				sysThemeTmpControl.setCreateTime(nowDate);
				sysThemeTmpControl.setDeleteFlag(0);
			} else {
				sysThemeTmpControl.setUpdateBy(actorId);
				sysThemeTmpControl.setUpdateTime(nowDate);
			}

			if (mFile != null) {
				byte[] bytes = mFile.getBytes();
				if (bytes != null && bytes.length > 0)
					sysThemeTmpControl.setThumbnail(bytes);

				// String cacheKey = "sys_theme_tmp_control_" +
				// SystemConfig.getIntToken() + "_" +
				// sysThemeTmpControl.getControlId();
				// RedisFactory.getInstance().set(cacheKey.getBytes(), bytes,
				// 3600);
			}
			this.sysThemeTmpControlService.save(sysThemeTmpControl);

			// 先删除以前的控件HTML模板
			this.sysThemeTmpBytearrayService.deleteByBuss("CONTROL", sysThemeTmpControl.getControlId(), "HTML");
			String htmlText = RequestUtils.getParameter(request, "htmlText");
			if (StringUtils.isNotEmpty(htmlText)) {
				// 重新插入控件的HTML模板
				SysThemeTmpBytearray sysThemeTmpBytearray = new SysThemeTmpBytearray();
				sysThemeTmpBytearray.setBussType("CONTROL");
				sysThemeTmpBytearray.setBussKey(sysThemeTmpControl.getControlId());
				sysThemeTmpBytearray.setType("HTML");
				sysThemeTmpBytearray.setBytes(htmlText.getBytes("UTF-8"));
				sysThemeTmpBytearray.setCreateBy(actorId);
				sysThemeTmpBytearray.setCreateTime(nowDate);
				this.sysThemeTmpBytearrayService.save(sysThemeTmpBytearray);
			}

			JSONObject result = new JSONObject();
			result.put("statusCode", "200");
			result.put("message", "操作成功");
			result.put("data", sysThemeTmpControl.toJsonObject());
			return result.toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false, "代码重复！");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String controlId = RequestUtils.getString(request, "controlId");
		String controlIds = request.getParameter("controlIds");
		if (StringUtils.isNotEmpty(controlIds)) {
			StringTokenizer token = new StringTokenizer(controlIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysThemeTmpControl sysThemeTmpControl = sysThemeTmpControlService
							.getSysThemeTmpControl(String.valueOf(x));
					if (sysThemeTmpControl != null
							&& (StringUtils.equals(sysThemeTmpControl.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sysThemeTmpControl.setDeleteFlag(1);
						sysThemeTmpControlService.save(sysThemeTmpControl);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (controlId != null) {
			SysThemeTmpControl sysThemeTmpControl = sysThemeTmpControlService
					.getSysThemeTmpControl(String.valueOf(controlId));
			if (sysThemeTmpControl != null
					&& (StringUtils.equals(sysThemeTmpControl.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				sysThemeTmpControl.setDeleteFlag(1);
				sysThemeTmpControlService.save(sysThemeTmpControl);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/getControls")
	@ResponseBody
	public byte[] getControls(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();

		String compType = RequestUtils.getString(request, "compType");
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
		query.setCompType(compType);
		query.setThemeTmpId(RequestUtils.getString(request, "themeTmpId"));
		List<SysThemeTmpControl> list = sysThemeTmpControlService.list(query);
		JSONArray array = new JSONArray();
		for (SysThemeTmpControl sysThemeTmpControl : list) {
			JSONObject obj = sysThemeTmpControl.toJsonObject();
			obj.put("id", sysThemeTmpControl.getControlId());
			// ztree中为唯一码
			obj.put("ztreeId", "control" + sysThemeTmpControl.getControlId());
			obj.put("treeType", "control");
			obj.put("name", sysThemeTmpControl.getControlName());
			obj.put("themeId", sysThemeTmpControl.getThemeTmpId());
			getStyleBytes(obj);
			array.add(obj);
		}
		result.put("data", array);
		return result.toJSONString().getBytes("UTF-8");
	}

	public void getStyleBytes(JSONObject obj) throws Exception {
		// 获取样式信息
		SysThemeTmpBytearrayQuery sysThemeTmpBytearrayQuery = new SysThemeTmpBytearrayQuery();
		sysThemeTmpBytearrayQuery.bussKey(obj.getString("id"));
		sysThemeTmpBytearrayQuery.bussType(obj.getString("treeType").toUpperCase());
		List<SysThemeTmpBytearray> byteList = this.sysThemeTmpBytearrayService.list(sysThemeTmpBytearrayQuery);
		for (SysThemeTmpBytearray sysThemeTmpBytearray : byteList) {
			obj.put(sysThemeTmpBytearray.getType(), new String(sysThemeTmpBytearray.getBytes(), "UTF-8"));
			obj.put(sysThemeTmpBytearray.getType() + "_id", sysThemeTmpBytearray.getId());
		}
	}

	@RequestMapping("/list")
	@ResponseBody
	public byte[] list(HttpServletRequest request, @RequestBody Map<String, Object> params) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		JSONObject result = new JSONObject();
		int total = sysThemeTmpControlService.getSysThemeTmpControlCountByQueryCriteria(query);
		if (total > 0) {
			List<SysThemeTmpControl> list = sysThemeTmpControlService.listAndCssText(query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (SysThemeTmpControl sysThemeTmpControl : list) {
					JSONObject rowJSON = sysThemeTmpControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpControl.getControlId());
					if(sysThemeTmpControl.getHtmlText().length > 0){
						rowJSON.put("HTML", new String(sysThemeTmpControl.getHtmlText(),"UTF-8"));
					}
					rowJSON.put("sysThemeTmpControlId", sysThemeTmpControl.getControlId());
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

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataSourceRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpControlDomainFactory.processDataRequest(dataRequest);
		String paramsStr = dataRequest.getParams();
		JSONObject paramsObj = JSON.parseObject(paramsStr);
		if (paramsObj != null) {
			Tools.populate(query, paramsObj);
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
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
		int total = sysThemeTmpControlService.getSysThemeTmpControlCountByQueryCriteria(query);
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
			List<SysThemeTmpControl> list = sysThemeTmpControlService.getSysThemeTmpControlsByQueryCriteria(start,
					limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysThemeTmpControl sysThemeTmpControl : list) {
					JSONObject rowJSON = sysThemeTmpControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpControl.getControlId());
					rowJSON.put("sysThemeTmpControlId", sysThemeTmpControl.getControlId());
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

	/**
	 * 下载缩略图
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadThumbnail")
	public void downloadThumbnail(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 从数据库中读取
			String themeTmpId = RequestUtils.getParameter(request, "controlId");
			if (themeTmpId != null && !themeTmpId.isEmpty()) {
				ResponseUtils.download(request, response, this.getThumbnail(themeTmpId), "缩略图.jpg");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param sysThemeTmp
	 * @return
	 */
	private byte[] getThumbnail(String themeTmpId) {
		byte[] data = null;
		String cacheKey = "sys_theme_tmp_control_" + SystemConfig.getIntToken() + "_" + themeTmpId;
		if (SystemConfig.getBoolean("use_file_cache")) {
			// try {
			// data = RedisFactory.getInstance().getBytes(cacheKey.getBytes());
			// } catch (Exception ex) {
			// }
		}
		if (data != null) {
			return data;
		}
		SysThemeTmpControl bean = this.sysThemeTmpControlService.getThumbnailById(themeTmpId);
		if (bean != null) {
			// 如果该节点存在，则取该节点的缩略图
			data = bean.getThumbnail();
			// if (data != null) {
			// if (SystemConfig.getBoolean("use_file_cache")) {
			// try {
			// RedisFactory.getInstance().set(cacheKey.getBytes(), data, 3600);
			// } catch (Exception ex) {
			// }
			// }
			// }
			return data;
		} else {
			// 如果该节点不存在，则取默认缩略图
			return null;
		}

	}

}
