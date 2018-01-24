package com.glaf.base.modules.image.web.springmvc;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.*;
import com.glaf.core.base.DataFile;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.util.*;
import com.glaf.base.modules.image.domain.*;
import com.glaf.base.modules.image.query.*;
import com.glaf.base.modules.image.service.*;
import com.glaf.base.modules.image.util.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/image")
@RequestMapping("/image")
public class ImageController {
	protected static final Log logger = LogFactory.getLog(ImageController.class);

	protected ImageService imageService;

	public ImageController() {

	}

	@RequestMapping("/choose")
	public ModelAndView choose(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/base/image/choose", modelMap);
	}

	@RequestMapping("/data")
	@ResponseBody
	public byte[] data(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ImageQuery query = new ImageQuery();
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

		JSONArray result = new JSONArray();
		int total = imageService.getImageCountByQueryCriteria(query);
		if (total > 0) {

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<Image> list = imageService.getImagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {

				for (Image image : list) {
					JSONObject rowJSON = image.toJsonObject();
					rowJSON.put("id", image.getId());
					rowJSON.put("rowId", image.getId());
					rowJSON.put("imageId", image.getId());
					rowJSON.put("startIndex", ++start);
					result.add(rowJSON);
				}

			}
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long id = RequestUtils.getLong(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					Image image = imageService.getImage(Long.valueOf(x));

					if (image != null && (StringUtils.equals(image.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						image.setDeleteFlag(1);
						imageService.save(image);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			Image image = imageService.getImage(Long.valueOf(id));
			if (image != null && (StringUtils.equals(image.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				image.setDeleteFlag(1);
				imageService.save(image);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/lob")
	public void lob(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Image image = imageService.getImage(RequestUtils.getLong(request, "id"));
		if (image != null && image.getData() != null) {
			try {
				ResponseUtils.download(request, response, image.getData(), image.getFilename());
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		Image image = imageService.getImage(RequestUtils.getLong(request, "id"));
		if (image != null) {
			request.setAttribute("image", image);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("image.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/modules/base/image/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ImageQuery query = new ImageQuery();
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
		int total = imageService.getImageCountByQueryCriteria(query);
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

			List<Image> list = imageService.getImagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Image image : list) {
					JSONObject rowJSON = image.toJsonObject();
					rowJSON.put("id", image.getId());
					rowJSON.put("rowId", image.getId());
					rowJSON.put("imageId", image.getId());
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
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/modules/base/image/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("image.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/modules/base/image/query", modelMap);
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		ImageQuery query = new ImageQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		ImageDomainFactory.processDataRequest(dataRequest);

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
		int total = imageService.getImageCountByQueryCriteria(query);
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

			List<Image> list = imageService.getImagesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (Image image : list) {
					JSONObject rowJSON = image.toJsonObject();
					rowJSON.put("id", image.getId());
					rowJSON.put("imageId", image.getId());
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

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, Object> params = RequestUtils.getParameterMap(req);
			params.remove("status");
			params.remove("wfStatus");

			Image image = new Image();
			Tools.populate(image, params);

			image.setParentId(RequestUtils.getLong(req, "parentId"));
			image.setName(req.getParameter("name"));
			image.setDesc(req.getParameter("desc"));
			image.setLocked(RequestUtils.getInt(req, "locked"));
			image.setDeleteFlag(RequestUtils.getInt(req, "deleteFlag"));
			image.setType("user");
			image.setCreateBy(actorId);
			image.setUpdateBy(actorId);

			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					try {
						DataFile dataFile = new BlobItemEntity();
						dataFile.setFilename(mFile.getOriginalFilename());
						dataFile.setSize(mFile.getSize());
						dataFile.setData(mFile.getBytes());
						image.setDataFile(dataFile);
						break;
					} catch (IOException e) {
					}
				}
			}

			imageService.save(image);
		}

		return this.list(request, modelMap);
	}

	@RequestMapping("/upload")
	@ResponseBody
	public byte[] upload(HttpServletRequest request, HttpServletResponse response,@RequestParam("file") List<MultipartFile> mFiles) throws UnsupportedEncodingException {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		JSONObject ret = new JSONObject();
		String uploadFileName = RequestUtils.getString(request, "fileName");
		Image image = new Image();
		for (MultipartFile mFile : mFiles) {
			try {
				// 保存到服务器目录
				String separator = File.separator;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String projectpath = request.getSession().getServletContext().getRealPath("");
				String dirpath = "/t_image";

				String fileName = mFile.getOriginalFilename();
				String stuffix = fileName.substring(fileName.lastIndexOf("."));

				sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				String newFileName = sdf.format(new Date()) + stuffix;

				String fullFilepath = projectpath + dirpath + separator + newFileName;
				String saveServicePath = dirpath + "/" + newFileName;

				if (!new File(projectpath + dirpath).exists()) {
					new File(projectpath + dirpath).mkdirs();
				}

				if (!new File(fullFilepath).exists()) {
					new File(fullFilepath).createNewFile();
				}
				FileUtils.save(fullFilepath, mFile.getInputStream());
				
				if(uploadFileName == null || uploadFileName.isEmpty()){
					uploadFileName = fileName;
				}
				
				image.setName(uploadFileName);
				image.setImagePath(saveServicePath);
				image.setLocked(0);
				image.setDeleteFlag(0);
				image.setType("user");
				image.setCreateBy(actorId);
				image.setCreateDate(new Date());
				
				imageService.save(image);
				
				ret.put("status", "200");
				ret.put("message", "图片上传成功！");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("图片上传失败！");
				ret.put("status", "500");
				ret.put("message", "图片上传失败！");
			} finally {
				
			}
		}
		return ret.toJSONString().getBytes("UTF-8");
	}
	
	@ResponseBody
	@RequestMapping("/saveImage")
	public byte[] saveImage(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		Image image = new Image();
		try {
			Tools.populate(image, params);
			image.setParentId(RequestUtils.getLong(request, "parentId"));
			image.setName(request.getParameter("name"));
			image.setDesc(request.getParameter("desc"));
			image.setLocked(RequestUtils.getInt(request, "locked"));
			image.setDeleteFlag(RequestUtils.getInt(request, "deleteFlag"));
			image.setCreateBy(actorId);
			image.setUpdateBy(actorId);
			this.imageService.save(image);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, Object> params = RequestUtils.getParameterMap(req);
			params.remove("status");
			params.remove("wfStatus");

			Image image = imageService.getImage(RequestUtils.getLong(req, "id"));

			Tools.populate(image, params);

			image.setParentId(RequestUtils.getLong(req, "parentId"));
			image.setName(req.getParameter("name"));
			image.setDesc(req.getParameter("desc"));
			image.setLocked(RequestUtils.getInt(req, "locked"));
			image.setDeleteFlag(RequestUtils.getInt(req, "deleteFlag"));
			image.setUpdateBy(user.getActorId());

			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			for (Entry<String, MultipartFile> entry : entrySet) {
				MultipartFile mFile = entry.getValue();
				if (mFile.getOriginalFilename() != null && mFile.getSize() > 0) {
					try {
						DataFile dataFile = new BlobItemEntity();
						dataFile.setFilename(mFile.getOriginalFilename());
						dataFile.setSize(mFile.getSize());
						dataFile.setData(mFile.getBytes());
						image.setDataFile(dataFile);
						break;
					} catch (IOException e) {
					}
				}
			}

			imageService.save(image);
		}
		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		Image image = imageService.getImage(RequestUtils.getLong(request, "id"));
		request.setAttribute("image", image);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("image.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/modules/base/image/view");
	}

}
