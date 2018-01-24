package com.glaf.theme.web.springmvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.factory.RedisFactory;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.service.FormComponentService;
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

@Controller("/theme/sysThemeTmp")
@RequestMapping("/theme/sysThemeTmp")
public class SysThemeTmpController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpController.class);

	protected SysThemeTmpService sysThemeTmpService;
	// 布局
	@Autowired
	protected SysThemeTmpLayoutService sysThemeTmpLayoutService;
	// 布局区域
	@Autowired
	protected SysThemeTmpLayoutAreaService sysThemeTmpLayoutAreaService;
	// 布局区域控件
	@Autowired
	protected SysThemeTmpLayoutAreaControlService sysThemeTmpLayoutAreaControlService;
	// 控件
	@Autowired
	protected SysThemeTmpControlService sysThemeTmpControlService;
	// 二进制信息
	@Autowired
	protected SysThemeTmpBytearrayService sysThemeTmpBytearrayService;
	// 基础控件信息
	@Autowired
	protected FormComponentService formComponentService;

	private Boolean _flag = null;

	public SysThemeTmpController() {

	}

	@javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpService")
	public void setSysThemeTmpService(SysThemeTmpService sysThemeTmpService) {
		this.sysThemeTmpService = sysThemeTmpService;
	}
	
	/**
	 * 设置默认主题
	 * @param request
	 * @return
	 */
	@RequestMapping("/setDefaultTheme")
	@ResponseBody
	public byte[] setDefaultTheme(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmp sysThemeTmp = new SysThemeTmp();
		try {
			sysThemeTmp.setThemeTmpId((String)params.get("themeTmpId"));
			sysThemeTmp.setDefaultFlag(Integer.valueOf((String) params.get("defaultFlag")));
			this.sysThemeTmpService.save(sysThemeTmp);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 修改或新增主题，带图片
	 * 
	 * @param request
	 * @param response
	 * @param mFiles
	 * @return
	 */
	@RequestMapping("/saveSysThemeTmp")
	@ResponseBody
	public byte[] saveSysThemeTmp(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmp sysThemeTmp = new SysThemeTmp();
		try {
			Tools.populate(sysThemeTmp, params);
			Date nowDate = new Date();
			if (null == sysThemeTmp.getThemeTmpId() || sysThemeTmp.getThemeTmpId().isEmpty()) {
				sysThemeTmp.setCreateBy(actorId);
				sysThemeTmp.setCreateTime(nowDate);
				sysThemeTmp.setDeleteFlag(0);
			} else {
				sysThemeTmp.setUpdateBy(actorId);
				sysThemeTmp.setUpdateTime(nowDate);
			}

			if (mFile != null) {
				byte[] bytes = mFile.getBytes();
				if (bytes != null && bytes.length > 0)
					sysThemeTmp.setThumbnail(bytes);
			}

			this.sysThemeTmpService.save(sysThemeTmp);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 修改或新增主题，带图片
	 * 
	 * @param request
	 * @param response
	 * @param mFiles
	 * @return
	 */
	@RequestMapping("/buildThemeStyle")
	@ResponseBody
	public byte[] buildThemeStyle(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();

		String themeTmpId = RequestUtils.getParameter(request, "themeTmpId");
		if (StringUtils.isNotEmpty(themeTmpId)) {
			SysThemeTmp sysThemeTmp = this.sysThemeTmpService.getSysThemeTmp(themeTmpId);

			if (sysThemeTmp != null) {
				String dirpath = "/theme";
				String projectpath = request.getRealPath("/");
				String dir = projectpath + "/" + dirpath;
				String themeFilePath = dir + "/" + sysThemeTmp.getThemeTmpId() + ".css";

				File themeFile = new File(themeFilePath);
				String cssText = null;
				SysThemeTmpBytearrayQuery sysThemeTmpBytearrayQuery;
				SysThemeTmpControlQuery query;
				List<SysThemeTmpBytearray> byteList;
				List<SysThemeTmpControl> list;
				SysThemeTmpBytearray byteArray;
				try {
					Path dirPath = Paths.get(dir);
					if (!Files.exists(dirPath)) {
						Files.createDirectory(dirPath);
					}
					if (!themeFile.exists()) {
						themeFile.createNewFile();
					}
					try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(themeFile))) {
						// 查询出该主题本身的全局信息，获取其CSS保存在文件中
						sysThemeTmpBytearrayQuery = new SysThemeTmpBytearrayQuery();
						sysThemeTmpBytearrayQuery.setBussKey(themeTmpId);
						sysThemeTmpBytearrayQuery.setBussType("THEME");
						sysThemeTmpBytearrayQuery.setType("CSS");
						byteList = this.sysThemeTmpBytearrayService.list(sysThemeTmpBytearrayQuery);
						if (byteList != null && byteList.size() > 0) {
							byteArray = byteList.get(0);
							if (byteArray.getBytes() != null) {
								cssText = new String(byteArray.getBytes());
								if (StringUtils.isNotEmpty(cssText))
									bufferWriter.write(cssText);
							}
						}
						// 查询出该主题的所有控件信息，获取其CSS保存在文件中
						query = new SysThemeTmpControlQuery();
						query.setThemeTmpId(themeTmpId);
						list = this.sysThemeTmpControlService.listAndCssText(query);
						for (SysThemeTmpControl sysThemeTmpControl : list) {
							if (sysThemeTmpControl.getCssText() != null) {
								cssText = new String(sysThemeTmpControl.getCssText());
								if (StringUtils.isNotEmpty(cssText))
									bufferWriter.write(cssText);
							}

						}
						return ResponseUtils.responseJsonResult(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ResponseUtils.responseJsonResult(false, "异常错误");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String themeTmpId = RequestUtils.getString(request, "themeTmpId");
		String themeTmpIds = request.getParameter("themeTmpIds");
		if (StringUtils.isNotEmpty(themeTmpIds)) {
			StringTokenizer token = new StringTokenizer(themeTmpIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysThemeTmp sysThemeTmp = sysThemeTmpService.getSysThemeTmp(String.valueOf(x));
					if (sysThemeTmp != null) {
						sysThemeTmp.setDeleteFlag(1); // 删除只修改删除标识
						sysThemeTmpService.save(sysThemeTmp);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (themeTmpId != null) {
			SysThemeTmp sysThemeTmp = sysThemeTmpService.getSysThemeTmp(String.valueOf(themeTmpId));
			if (sysThemeTmp != null) {
				sysThemeTmp.setDeleteFlag(1); // 删除只修改删除标识
				sysThemeTmpService.save(sysThemeTmp);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/view/{path}")
	public ModelAndView view(@PathVariable String path, HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmp sysThemeTmp = sysThemeTmpService.getSysThemeTmp(request.getParameter("themeTmpId"));
		request.setAttribute("sysThemeTmp", sysThemeTmp);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("sysThemeTmp.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/theme/sysThemeTmp/" + path);
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
			String themeTmpId = RequestUtils.getParameter(request, "themeTmpId");
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
		String cacheKey = "sys_theme_tmp_" + SystemConfig.getIntToken() + "_" + themeTmpId;
		if (SystemConfig.getBoolean("use_file_cache")) {
			try {
				data = RedisFactory.getInstance().getBytes(cacheKey.getBytes());
			} catch (Exception ex) {
			}
		}
		if (data != null) {
			return data;
		}
		SysThemeTmp bean = this.sysThemeTmpService.getThumbnailById(themeTmpId);
		if (bean != null) {
			// 如果该节点存在，则取该节点的缩略图
			data = bean.getThumbnail();
			if (data != null) {
				if (SystemConfig.getBoolean("use_file_cache")) {
					try {
						RedisFactory.getInstance().set(cacheKey.getBytes(), data, 3600);
					} catch (Exception ex) {
					}
				}
			}
			return data;
		} else {
			// 如果该节点不存在，则取默认缩略图
			return null;
		}

	}

	@RequestMapping("/list")
	@ResponseBody
	public byte[] list(HttpServletRequest request, @RequestBody DataSourceRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpQuery query = new SysThemeTmpQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpDomainFactory.processDataRequest(dataRequest);
		String str = dataRequest.getParams();
		if(StringUtils.isNotEmpty(str)){
			Tools.populate(query, JSON.parseObject(str));
		}

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		JSONObject result = new JSONObject();
		int total = sysThemeTmpService.getSysThemeTmpCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);

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

			List<SysThemeTmp> list = sysThemeTmpService.list(query);
			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (SysThemeTmp sysThemeTmp : list) {
					JSONObject rowJSON = sysThemeTmp.toJsonObject();
					rowJSON.put("id", sysThemeTmp.getThemeTmpId());
					rowJSON.put("sysThemeTmpId", sysThemeTmp.getThemeTmpId());
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
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpQuery query = new SysThemeTmpQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpService.getSysThemeTmpCountByQueryCriteria(query);
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
			List<SysThemeTmp> list = sysThemeTmpService.getSysThemeTmpsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysThemeTmp sysThemeTmp : list) {
					JSONObject rowJSON = sysThemeTmp.toJsonObject();
					rowJSON.put("id", sysThemeTmp.getThemeTmpId());
					rowJSON.put("sysThemeTmpId", sysThemeTmp.getThemeTmpId());
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
	 * 根据 bussKey与bussType获取出对应的样式信息
	 * 
	 * @param obj
	 * @throws Exception
	 */
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

	public void getParentNode(JSONObject nodeJson, JSONArray retDatas) {
		String str = "";
		String nodeStr = "";
		JSONArray children = null;
		boolean k = false;
		JSONObject retDataJson = null;
		int i, maxNum;
		for (i = 0, maxNum = retDatas.size(); i < maxNum; i++) {
			k = false;
			retDataJson = retDatas.getJSONObject(i);
			str = retDataJson.getString("controlId");
			// 判断是否为子节点
			nodeStr = nodeJson.getString("pcontrolId");
			if (str != null && str.equals(nodeStr)) {
				// 找到父节点
				children = retDataJson.getJSONArray("children");
				children.add(nodeJson);
				_flag = true;
				k = true;
			}
			// 判断是否为父节点
			if (!k) {
				str = retDataJson.getString("pcontrolId");
				nodeStr = nodeJson.getString("controlId");
				if (str != null && str.equals(nodeStr)) {
					// 该节点为父节点
					children = nodeJson.getJSONArray("children");
					children.add(retDataJson);

					retDatas.remove(retDataJson);
					retDatas.remove(nodeJson);
					retDatas.add(nodeJson);
					_flag = true;
					k = true;
					i--;
					maxNum--;
				}
			}
			// 遍历子节点
			if (!k) {
				getParentNode(nodeJson, retDataJson.getJSONArray("children"));
			}
		}
	}

	public void createTreeData(JSONArray datas, JSONArray retDatas) {
		for (Object data : datas) {
			JSONObject dataJson = (JSONObject) data;
			_flag = false;
			// 存在父节点，寻找父节点并插入retDatas中
			getParentNode(dataJson, retDatas);
			if (!_flag) {
				// 根节点，直接插入
				retDatas.add(dataJson);
			}
		}
	}
	
	@RequestMapping("/getThemeById")
	@ResponseBody
	public byte[] getThemeById(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		String themeId = RequestUtils.getParameter(request, "themeId");
		
		SysThemeTmp sysThemeTmp = this.sysThemeTmpService.getSysThemeTmp(themeId);
		if(sysThemeTmp != null){
			result.put("statusCode", "200");
			result.put("data", sysThemeTmp.toJsonObject());
		}else{
			result.put("statusCode", "500");
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getThemeTree")
	@ResponseBody
	public byte[] getThemeTree(HttpServletRequest request) throws Exception {
		String themeId = RequestUtils.getParameter(request, "themeId");
		String treeType = RequestUtils.getParameter(request, "treeType");

		JSONObject result = new JSONObject();
		JSONArray datas = new JSONArray();

		if (treeType == null || treeType.isEmpty()) {
			// 查询出主题信息
			SysThemeTmp sysThemeTmp = this.sysThemeTmpService.getSysThemeTmp(themeId);
			JSONObject themeObj = sysThemeTmp.toJsonObject();
			themeObj.put("treeType", "theme");
			// ztree中为唯一码
			themeObj.put("ztreeId", "theme");
			themeObj.put("themeId", themeId);
			themeObj.put("id", themeId);
			themeObj.put("name", sysThemeTmp.getThemeTmpName());
			getStyleBytes(themeObj);
			JSONArray children = new JSONArray();
			JSONObject obj = new JSONObject();
			obj.put("treeType", "layoutPanel");
			// ztree中为唯一码
			obj.put("ztreeId", "layoutPanel");
			obj.put("name", "布局");
			obj.put("isParent", "false");
			obj.put("themeId", themeId);
			children.add(obj);
			obj = new JSONObject();
			obj.put("treeType", "componentPanel");
			obj.put("name", "控件");
			obj.put("isParent", "false");
			obj.put("themeId", themeId);
			children.add(obj);
			obj = new JSONObject();
			obj.put("treeType", "modelPanel");
			// ztree中为唯一码
			obj.put("ztreeId", "modelPanel");
			obj.put("name", "模板");
			obj.put("isParent", "false");
			obj.put("themeId", themeId);
			children.add(obj);
			themeObj.put("children", children);
			datas.add(themeObj);
		} else if (treeType.equals("layoutPanel")) {
			// 根据主题ID查询出布局信息
			SysThemeTmpLayoutQuery layoutQuery = new SysThemeTmpLayoutQuery();
			layoutQuery.setThemeTmpId(themeId);
			List<SysThemeTmpLayout> layoutList = sysThemeTmpLayoutService.list(layoutQuery);
			JSONArray array = null; // 中间变量
			JSONArray array2 = null; // 中间变量
			// 遍历布局信息找出区域或控件信息
			for (SysThemeTmpLayout item : layoutList) {
				JSONObject obj = item.toJsonObject();
				obj.put("id", item.getLayoutId());
				// ztree中为唯一码
				obj.put("ztreeId", "layout" + item.getLayoutId());
				obj.put("treeType", "layout");
				obj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
				obj.put("name", item.getLayoutName());
				obj.put("themeId", themeId);
				obj.put("code", item.getLayoutCode());
				JSONArray layoutChildren = new JSONArray();
				obj.put("children", layoutChildren);
				// 获取样式信息
				getStyleBytes(obj);

				// 查询出区域信息和其下的控件信息
				JSONObject layoutAreaObj = new JSONObject();
				layoutAreaObj.put("treeType", "layoutareapanel");
				// ztree中为唯一码
				layoutAreaObj.put("ztreeId", "layoutareapanel" + item.getLayoutId());
				layoutAreaObj.put("name", "区域");
				layoutAreaObj.put("themeId", themeId);
				layoutAreaObj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息

				JSONArray layoutAreaChildren = new JSONArray();
				layoutAreaObj.put("children", layoutAreaChildren);
				// 加入节点中
				layoutChildren.add(layoutAreaObj);

				SysThemeTmpLayoutAreaQuery query = new SysThemeTmpLayoutAreaQuery();
				query.setLayoutId(item.getLayoutId());
				List<SysThemeTmpLayoutArea> areaList = sysThemeTmpLayoutAreaService.list(query);
				for (SysThemeTmpLayoutArea area : areaList) {
					JSONObject areaObj = area.toJsonObject();
					// 查询区域下面的控件信息
					areaObj.put("treeType", "layoutarea");
					areaObj.put("id", area.getAreaId());
					// ztree中为唯一码
					areaObj.put("ztreeId", "layoutarea" + area.getAreaId());
					areaObj.put("name", area.getAreaName());
					areaObj.put("themeId", themeId);
					areaObj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
					// JSONArray areaObjChildren = new JSONArray();
					// areaObj.put("children", areaObjChildren);
					// 获取样式信息
					getStyleBytes(areaObj);
					// 添加入节点中
					layoutAreaChildren.add(areaObj);

					// 查询区域下的控件/模板信息
					SysThemeTmpLayoutAreaControlQuery controlQuery = new SysThemeTmpLayoutAreaControlQuery();
					controlQuery.setAreaId(area.getAreaId());
					List<SysThemeTmpLayoutAreaControl> controlList = sysThemeTmpLayoutAreaControlService
							.list(controlQuery);
					array = new JSONArray(); // 中间变量，存储控件信息
					for (SysThemeTmpLayoutAreaControl control : controlList) {
						JSONObject controlObj = control.toJsonObject();
						controlObj.put("treeType", "layoutareacontrol");
						controlObj.put("id", control.getControlId());
						// ztree中为唯一码
						controlObj.put("ztreeId", "layoutareacontrol" + control.getControlId());
						controlObj.put("name", control.getControlName());
						controlObj.put("themeId", themeId);
						controlObj.put("parentTreeType", "layoutarea");
						controlObj.put("parentTreeTypeID", area.getAreaId());
						controlObj.put("children", new JSONArray());
						controlObj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
						// 获取样式信息
						getStyleBytes(controlObj);

						array.add(controlObj);
						// areaObjChildren.add(controlObj);
					}
					array2 = new JSONArray();// 中间变量
					// 排序
					createTreeData(array, array2);
					areaObj.put("children", array2);
				}

				// 查询出区域信息和其下的控件信息
				JSONObject layoutControlObj = new JSONObject();
				layoutControlObj.put("treeType", "layoutareacontrolpanel");
				layoutControlObj.put("layoutId", item.getLayoutId());
				// ztree中为唯一码
				layoutControlObj.put("ztreeId", "layoutareacontrolpanel" + item.getLayoutId());
				layoutControlObj.put("name", "控件/模板");
				layoutControlObj.put("themeId", themeId);
				layoutControlObj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
				// JSONArray layoutControlChildren = new JSONArray();
				// layoutControlObj.put("children", layoutControlChildren);
				// 加入节点中
				layoutChildren.add(layoutControlObj);

				// 查询区域下的控件/模板信息
				SysThemeTmpLayoutAreaControlQuery controlQuery = new SysThemeTmpLayoutAreaControlQuery();
				controlQuery.setLayoutId(item.getLayoutId());
				List<SysThemeTmpLayoutAreaControl> controlList = sysThemeTmpLayoutAreaControlService.list(controlQuery);
				JSONObject parentList = new JSONObject();
				array = new JSONArray(); // 中间变量，存储控件信息
				for (SysThemeTmpLayoutAreaControl areaControl : controlList) {
					JSONObject areaControlObj = areaControl.toJsonObject();
					areaControlObj.put("treeType", "layoutareacontrol");
					areaControlObj.put("id", areaControl.getControlId());
					// ztree中为唯一码
					areaControlObj.put("ztreeId", "layoutareacontrol" + areaControl.getControlId());
					areaControlObj.put("name", areaControl.getControlName());
					areaControlObj.put("themeId", themeId);
					areaControlObj.put("parentTreeType", "layout");
					areaControlObj.put("parentTreeTypeID", item.getLayoutId());
					areaControlObj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
					areaControlObj.put("children", new JSONArray());

					// layoutControlChildren.add(areaControlObj);
					array.add(areaControlObj);
					// --组装控件类型
					// String compType = areaControl.getCompType();
					// if(parentList.get(compType) != null){
					// parentList.getJSONObject(compType).getJSONArray("children").add(obj);
					// }else{
					// JSONObject parentItem = new JSONObject();
					// parentItem.put("treeType", "layoutareacontrolType");
					// parentItem.put("themeId", themeId);
					// parentItem.put("compType", compType);
					// JSONArray children = new JSONArray();
					// children.add(areaControlObj);
					// parentItem.put("children", children);
					// layoutControlChildren.add(parentItem);
					// parentList.put(compType, parentItem);
					// }
				}

				array2 = new JSONArray();// 中间变量
				// 排序
				createTreeData(array, array2);
				layoutControlObj.put("children", array2);

				datas.add(obj);
			}
		} else if (treeType.equals("componentPanel")) {
			SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
			query.setThemeTmpId(themeId);
			List<SysThemeTmpControl> list = sysThemeTmpControlService.list(query);

			JSONObject parentList = new JSONObject();
			for (SysThemeTmpControl sysThemeTmpControl : list) {
				JSONObject obj = sysThemeTmpControl.toJsonObject();

				String compType = sysThemeTmpControl.getCompType();
				if (parentList.get(compType) != null) {
					parentList.getJSONObject(compType).getJSONArray("children").add(obj);
				} else {
					JSONObject parentItem = new JSONObject();
					parentItem.put("treeType", "controlType");
					parentItem.put("themeId", themeId);
					// ztree中为唯一码
					parentItem.put("ztreeId", "controlType");
					parentItem.put("compType", compType);
					parentItem.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
					JSONArray children = new JSONArray();
					children.add(obj);
					parentItem.put("children", children);
					datas.add(parentItem);
					parentList.put(compType, parentItem);
				}

				// SysThemeTmpBytearrayQuery sysThemeTmpBytearrayQuery = new
				// SysThemeTmpBytearrayQuery();
				// sysThemeTmpBytearrayQuery.bussKey(sysThemeTmpControl.getControlId());
				// sysThemeTmpBytearrayQuery.bussType("CONTROL");
				// List<SysThemeTmpBytearray> byteList =
				// this.sysThemeTmpBytearrayService.list(sysThemeTmpBytearrayQuery);
				// for(SysThemeTmpBytearray sysThemeTmpBytearray : byteList){
				// obj.put(sysThemeTmpBytearray.getType(),new
				// String(sysThemeTmpBytearray.getBytes(),"UTF-8"));
				// obj.put(sysThemeTmpBytearray.getType()+"_id",
				// sysThemeTmpBytearray.getId());
				// }
				obj.put("id", sysThemeTmpControl.getControlId());
				// ztree中为唯一码
				obj.put("ztreeId", "control" + sysThemeTmpControl.getControlId());
				obj.put("treeType", "control");
				obj.put("parentTreeType", treeType); // 设置父节点类型，用于动态刷新节点信息
				obj.put("name", sysThemeTmpControl.getControlName());
				obj.put("themeId", themeId);

				// 获取样式信息
				getStyleBytes(obj);
				// datas.add(obj);
			}
		}

		result.put("rows", datas);

		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/getAllControl")
	@ResponseBody
	public byte[] getAllControl(HttpServletRequest request) throws Exception {
		String themeId = RequestUtils.getParameter(request, "themeId");

		JSONObject result = new JSONObject();
		JSONArray datas = new JSONArray();
		
		//查询出所有的控件信息
		SysThemeTmpControlQuery query = new SysThemeTmpControlQuery();
		query.setThemeTmpId(themeId);
		List<SysThemeTmpControl> list = sysThemeTmpControlService.list(query);

		JSONObject parentList = new JSONObject();
		//遍历所有控件信息，根据控件类型，生成类型树
		for (SysThemeTmpControl sysThemeTmpControl : list) {
			JSONObject obj = sysThemeTmpControl.toJsonObject();

			String compType = sysThemeTmpControl.getCompType();
			if (parentList.get(compType) != null) {
				parentList.getJSONObject(compType).getJSONArray("children").add(obj);
			} else {
				JSONObject parentItem = new JSONObject();
				parentItem.put("treeType", "controlType");
				parentItem.put("themeId", themeId);
				// ztree中为唯一码
				parentItem.put("ztreeId", "controlType");
				parentItem.put("compType", compType);
				JSONArray children = new JSONArray();
				children.add(obj);
				parentItem.put("children", children);
				datas.add(parentItem);
				parentList.put(compType, parentItem);
			}

			obj.put("id", sysThemeTmpControl.getControlId());
			// ztree中为唯一码
			obj.put("ztreeId", "control" + sysThemeTmpControl.getControlId());
			obj.put("treeType", "control");
			obj.put("name", sysThemeTmpControl.getControlName());
			obj.put("themeId", themeId);

			// 获取样式信息
			getStyleBytes(obj);
			// datas.add(obj);
		}

		result.put("rows", datas);

		return result.toJSONString().getBytes("UTF-8");
	}

	public void createTreeData(JSONArray datas, List<SysThemeTmpLayoutAreaControl> controlList) {

	}
}
