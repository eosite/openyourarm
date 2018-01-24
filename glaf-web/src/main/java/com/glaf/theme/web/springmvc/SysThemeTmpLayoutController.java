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

import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;
import com.glaf.theme.service.*;
import com.glaf.theme.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/theme/sysThemeTmpLayout")
@RequestMapping("/theme/sysThemeTmpLayout")
public class SysThemeTmpLayoutController {
	protected static final Log logger = LogFactory.getLog(SysThemeTmpLayoutController.class);

	protected SysThemeTmpLayoutService sysThemeTmpLayoutService;

	public SysThemeTmpLayoutController() {

	}

	@javax.annotation.Resource(name = "com.glaf.theme.service.sysThemeTmpLayoutService")
	public void setSysThemeTmpLayoutService(SysThemeTmpLayoutService sysThemeTmpLayoutService) {
		this.sysThemeTmpLayoutService = sysThemeTmpLayoutService;
	}

	/**
	 * 修改或新增，带图片
	 * 
	 * @param request
	 * @param response
	 * @param mFiles
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveSysThemeTmpLayout")
	public byte[] saveSysThemeTmpLayout(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile mFile) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		SysThemeTmpLayout sysThemeTmpLayout = new SysThemeTmpLayout();
		try {
			Tools.populate(sysThemeTmpLayout, params);

			Date nowDate = new Date();
			if (null == sysThemeTmpLayout.getLayoutId() || sysThemeTmpLayout.getLayoutId().isEmpty()) {
				sysThemeTmpLayout.setCreateBy(actorId);
				sysThemeTmpLayout.setCreateTime(nowDate);
				sysThemeTmpLayout.setDeleteFlag(0);
			} else {
				sysThemeTmpLayout.setUpdateBy(actorId);
				sysThemeTmpLayout.setUpdateTime(nowDate);
			}

			if (mFile != null) {
				byte[] bytes = mFile.getBytes();
				if(bytes != null && bytes.length > 0)
					sysThemeTmpLayout.setThumbnail(bytes);
			}
			
			Object obj = params.get("layoutAreaDatas");
			JSONArray layoutAreaDatas = null;
			if(obj != null){
				String str = (String)obj;
				if(!str.isEmpty()){
					layoutAreaDatas = JSON.parseArray(str);
				}
			}
			

			this.sysThemeTmpLayoutService.save(sysThemeTmpLayout,layoutAreaDatas);

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
			String id = RequestUtils.getParameter(request, "layoutId");
			if(id != null && !id.isEmpty()){
				ResponseUtils.download(request, response, this.getThumbnail(id),"缩略图.jpg");
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
	private byte[] getThumbnail(String themeTmpId) {
		byte[] data = null;
		String cacheKey = "sys_theme_tmp_layout_" + SystemConfig.getIntToken() + "_" + themeTmpId;
		if (SystemConfig.getBoolean("use_file_cache")) {
//			try {
//				data = RedisFactory.getInstance().getBytes(cacheKey.getBytes());
//			} catch (Exception ex) {
//			}
		}
		if (data != null) {
			return data;
		}
		SysThemeTmpLayout bean = this.sysThemeTmpLayoutService.getThumbnailById(themeTmpId);
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
		String layoutId = RequestUtils.getString(request, "layoutId");
		String layoutIds = request.getParameter("layoutIds");
		if (StringUtils.isNotEmpty(layoutIds)) {
			StringTokenizer token = new StringTokenizer(layoutIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					SysThemeTmpLayout sysThemeTmpLayout = sysThemeTmpLayoutService
							.getSysThemeTmpLayout(String.valueOf(x));
					if (sysThemeTmpLayout != null
							&& (StringUtils.equals(sysThemeTmpLayout.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						sysThemeTmpLayout.setDeleteFlag(1); // 删除只修改删除标识
						sysThemeTmpLayoutService.save(sysThemeTmpLayout);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (layoutId != null) {
			SysThemeTmpLayout sysThemeTmpLayout = sysThemeTmpLayoutService
					.getSysThemeTmpLayout(String.valueOf(layoutId));
			if (sysThemeTmpLayout != null
					&& (StringUtils.equals(sysThemeTmpLayout.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				sysThemeTmpLayout.setDeleteFlag(1);
				sysThemeTmpLayoutService.save(sysThemeTmpLayout);
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
		SysThemeTmpLayoutQuery query = new SysThemeTmpLayoutQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		SysThemeTmpLayoutDomainFactory.processDataRequest(dataRequest);

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
		int total = sysThemeTmpLayoutService.getSysThemeTmpLayoutCountByQueryCriteria(query);
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
			List<SysThemeTmpLayout> list = sysThemeTmpLayoutService.getSysThemeTmpLayoutsByQueryCriteria(start, limit,
					query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (SysThemeTmpLayout sysThemeTmpLayout : list) {
					JSONObject rowJSON = sysThemeTmpLayout.toJsonObject();
					rowJSON.put("id", sysThemeTmpLayout.getLayoutId());
					rowJSON.put("sysThemeTmpLayoutId", sysThemeTmpLayout.getLayoutId());
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
