package com.glaf.theme.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.factory.RedisFactory;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;

import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;
import com.glaf.theme.service.*;
import com.glaf.theme.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/theme/sysThemeTmpLayoutAreaControl")
@RequestMapping("/theme/sysThemeTmpLayoutAreaControl")
public class SysThemeTmpLayoutAreaControlController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutAreaControlController.class);

	protected SysThemeTmpLayoutAreaControlService sysThemeTmpLayoutAreaControlService;

	public SysThemeTmpLayoutAreaControlController() {

	}

	@javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutAreaControlService")
	public void setSysThemeTmpLayoutAreaControlService(
			SysThemeTmpLayoutAreaControlService sysThemeTmpLayoutAreaControlService) {
		this.sysThemeTmpLayoutAreaControlService = sysThemeTmpLayoutAreaControlService;
	}

	@ResponseBody
	@RequestMapping("/saveSysThemeTmpLayoutAreaControl")
	public byte[] saveSysThemeTmpLayoutAreaControl(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = new SysThemeTmpLayoutAreaControl();
		try {
			Tools.populate(sysThemeTmpLayoutAreaControl, params);

			Date nowDate = new Date();
			if (null == sysThemeTmpLayoutAreaControl.getControlId()
					|| sysThemeTmpLayoutAreaControl.getControlId().isEmpty()) {
				sysThemeTmpLayoutAreaControl.setCreateBy(actorId);
				sysThemeTmpLayoutAreaControl.setCreateTime(nowDate);
				sysThemeTmpLayoutAreaControl.setDeleteFlag(0);
			} else {
				sysThemeTmpLayoutAreaControl.setUpdateBy(actorId);
				sysThemeTmpLayoutAreaControl.setUpdateTime(nowDate);
			}

			if (mFile != null) {
				byte[] bytes = mFile.getBytes();
				if(bytes != null && bytes.length > 0)
				sysThemeTmpLayoutAreaControl.setThumbnail(bytes);
			}

			this.sysThemeTmpLayoutAreaControlService.save(sysThemeTmpLayoutAreaControl);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	/**
     * 下载缩略图
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downloadThumbnail")
    public void downloadThumbnail(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			// 从数据库中读取
			String controlId = RequestUtils.getParameter(request, "controlId");
			if(controlId != null && !controlId.isEmpty()){
				ResponseUtils.download(request, response, this.getThumbnail(controlId),"缩略图.jpg");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    /**
	 * 获取文件内容，从redis缓存中加载文件流
	 * 
	 * @param sysThemeTmp
	 * @return
	 */
	private byte[] getThumbnail(String id) {
		byte[] data = null;
		String cacheKey = "sys_theme_tmp_layout_area_control_" + SystemConfig.getIntToken() + "_" + id;
		if (SystemConfig.getBoolean("use_file_cache")) {
//			try {
//				data = RedisFactory.getInstance().getBytes(cacheKey.getBytes());
//			} catch (Exception ex) {
//			}
		}
		if (data != null) {
			return data;
		}
		SysThemeTmpLayoutAreaControl bean = this.sysThemeTmpLayoutAreaControlService.getThumbnailById(id);
		if(bean != null){
			//如果该节点存在，则取该节点的缩略图
			data = bean.getThumbnail();
//			if (data != null) {
//				if (SystemConfig.getBoolean("use_file_cache")) {
//					try {
//						RedisFactory.getInstance().set(cacheKey.getBytes(), data, 3600);
//					} catch (Exception ex) {
//					}
//				}
//			}
			return data;
		}else{
			//如果该节点不存在，则取默认缩略图
			return null;
		}
		
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
					SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = sysThemeTmpLayoutAreaControlService
							.getSysThemeTmpLayoutAreaControl(String.valueOf(x));
					if (sysThemeTmpLayoutAreaControl != null
							&& (StringUtils.equals(sysThemeTmpLayoutAreaControl.getCreateBy(),
									loginContext.getActorId()) || loginContext.isSystemAdministrator())) {
						sysThemeTmpLayoutAreaControl.setDeleteFlag(1);
						sysThemeTmpLayoutAreaControlService.save(sysThemeTmpLayoutAreaControl);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (controlId != null) {
			SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl = sysThemeTmpLayoutAreaControlService
					.getSysThemeTmpLayoutAreaControl(String.valueOf(controlId));
			if (sysThemeTmpLayoutAreaControl != null
					&& (StringUtils.equals(sysThemeTmpLayoutAreaControl.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				sysThemeTmpLayoutAreaControl.setDeleteFlag(1);
				sysThemeTmpLayoutAreaControlService.save(sysThemeTmpLayoutAreaControl);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayoutAreaControlQuery query = new SysThemeTmpLayoutAreaControlQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpLayoutAreaControlDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutAreaControlService.getSysThemeTmpLayoutAreaControlCountByQueryCriteria(query);
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
			List<SysThemeTmpLayoutAreaControl> list = sysThemeTmpLayoutAreaControlService
					.getSysThemeTmpLayoutAreaControlsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl : list) {
					JSONObject rowJSON = sysThemeTmpLayoutAreaControl.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayoutAreaControl.getControlId());
					rowJSON.put("sysThemeTmpLayoutAreaControlId", sysThemeTmpLayoutAreaControl.getControlId());
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

}
