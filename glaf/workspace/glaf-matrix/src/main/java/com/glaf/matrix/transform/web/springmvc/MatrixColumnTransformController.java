package com.glaf.matrix.transform.web.springmvc;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.identity.*;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.*;

import com.glaf.matrix.transform.domain.*;
import com.glaf.matrix.transform.query.*;
import com.glaf.matrix.transform.service.*;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/matrixColumnTransform")
@RequestMapping("/sys/matrixColumnTransform")
public class MatrixColumnTransformController {
	protected static final Log logger = LogFactory.getLog(MatrixColumnTransformController.class);

	protected MatrixColumnTransformService matrixColumnTransformService;

	protected MatrixTableTransformService matrixTableTransformService;

	protected IDatabaseService databaseService;

	public MatrixColumnTransformController() {

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
					MatrixColumnTransform matrixColumnTransform = matrixColumnTransformService
							.getMatrixColumnTransform(Long.valueOf(x));
					if (matrixColumnTransform != null
							&& (StringUtils.equals(matrixColumnTransform.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						matrixColumnTransformService.deleteById(id);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			MatrixColumnTransform matrixColumnTransform = matrixColumnTransformService
					.getMatrixColumnTransform(Long.valueOf(id));
			if (matrixColumnTransform != null
					&& (StringUtils.equals(matrixColumnTransform.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
				matrixColumnTransformService.deleteById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		long id = RequestUtils.getLong(request, "id");
		MatrixTableTransform matrixTableTransform = null;
		MatrixColumnTransform matrixColumnTransform = null;
		String transformId = request.getParameter("transformId");
		matrixColumnTransform = matrixColumnTransformService.getMatrixColumnTransform(id);
		if (matrixColumnTransform != null) {
			transformId = matrixColumnTransform.getTransformId();
			request.setAttribute("matrixColumnTransform", matrixColumnTransform);
			request.setAttribute("tableName", matrixColumnTransform.getTableName());
		}

		if (StringUtils.isNotEmpty(transformId)) {
			matrixTableTransform = matrixTableTransformService.getMatrixTableTransform(transformId);
		}

		if (matrixTableTransform != null) {
			String tableName = matrixTableTransform.getTableName();

			List<MatrixColumnTransform> cols = matrixColumnTransformService.getMatrixColumnTransforms(tableName);

			List<Long> databaseIds = StringTools.splitToLong(matrixTableTransform.getDatabaseIds());
			if (databaseIds != null && !databaseIds.isEmpty()) {
				Database database = databaseService.getDatabaseById(databaseIds.get(0));
				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(database.getName(), tableName);
				if (cols != null && !cols.isEmpty()) {
					List<String> existNames = new ArrayList<String>();
					for (ColumnDefinition column : columns) {
						existNames.add(column.getColumnName().toUpperCase());
					}
					for (MatrixColumnTransform ct : cols) {
						if (!existNames.contains(ct.getTargetColumnName().toUpperCase())) {
							existNames.add(ct.getTargetColumnName().toUpperCase());
							ColumnDefinition col = new ColumnDefinition();
							col.setColumnName(ct.getTargetColumnName());
							col.setJavaType(ct.getTargetType());
							col.setTitle(ct.getTitle());
							columns.add(col);
						}
					}
				}
				request.setAttribute("columns", columns);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("matrixColumnTransform.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/matrix/columnTransform/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MatrixColumnTransformQuery query = new MatrixColumnTransformQuery();
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
		int total = matrixColumnTransformService.getMatrixColumnTransformCountByQueryCriteria(query);
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

			List<MatrixColumnTransform> list = matrixColumnTransformService
					.getMatrixColumnTransformsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (MatrixColumnTransform matrixColumnTransform : list) {
					JSONObject rowJSON = matrixColumnTransform.toJsonObject();
					rowJSON.put("id", matrixColumnTransform.getId());
					rowJSON.put("rowId", matrixColumnTransform.getId());
					rowJSON.put("matrixColumnTransformId", matrixColumnTransform.getId());
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

		return new ModelAndView("/matrix/columnTransform/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("matrixColumnTransform.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/matrix/columnTransform/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveMatrixColumnTransform")
	public byte[] saveMatrixColumnTransform(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		MatrixColumnTransform matrixColumnTransform = new MatrixColumnTransform();
		try {
			Tools.populate(matrixColumnTransform, params);
			matrixColumnTransform.setId(RequestUtils.getLong(request, "id"));
			matrixColumnTransform.setName(request.getParameter("name"));
			matrixColumnTransform.setTitle(request.getParameter("title"));
			matrixColumnTransform.setTransformId(request.getParameter("transformId"));
			matrixColumnTransform.setTableName(request.getParameter("tableName"));
			matrixColumnTransform.setColumnName(request.getParameter("columnName"));
			matrixColumnTransform.setTargetType(request.getParameter("targetType"));
			matrixColumnTransform.setTargetColumnName(request.getParameter("targetColumnName"));
			matrixColumnTransform.setTargetColumnPrecision(RequestUtils.getInt(request, "targetColumnPrecision"));
			matrixColumnTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
			matrixColumnTransform.setCondition(request.getParameter("condition"));
			matrixColumnTransform.setExpression(request.getParameter("expression"));
			matrixColumnTransform
					.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
			matrixColumnTransform.setSort(RequestUtils.getInt(request, "sort"));
			matrixColumnTransform.setLocked(RequestUtils.getInt(request, "locked"));
			matrixColumnTransform.setType(request.getParameter("type"));
			matrixColumnTransform.setCreateBy(actorId);
			matrixColumnTransform.setUpdateBy(actorId);
			this.matrixColumnTransformService.save(matrixColumnTransform);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setMatrixColumnTransformService(MatrixColumnTransformService matrixColumnTransformService) {
		this.matrixColumnTransformService = matrixColumnTransformService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setMatrixTableTransformService(MatrixTableTransformService matrixTableTransformService) {
		this.matrixTableTransformService = matrixTableTransformService;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		MatrixColumnTransform matrixColumnTransform = matrixColumnTransformService
				.getMatrixColumnTransform(RequestUtils.getLong(request, "id"));

		Tools.populate(matrixColumnTransform, params);

		matrixColumnTransform.setName(request.getParameter("name"));
		matrixColumnTransform.setTitle(request.getParameter("title"));
		matrixColumnTransform.setTableName(request.getParameter("tableName"));
		matrixColumnTransform.setColumnName(request.getParameter("columnName"));
		matrixColumnTransform.setTargetType(request.getParameter("targetType"));
		matrixColumnTransform.setTargetColumnName(request.getParameter("targetColumnName"));
		matrixColumnTransform.setTargetColumnPrecision(RequestUtils.getInt(request, "targetColumnPrecision"));
		matrixColumnTransform.setSqlCriteria(request.getParameter("sqlCriteria"));
		matrixColumnTransform.setCondition(request.getParameter("condition"));
		matrixColumnTransform.setExpression(request.getParameter("expression"));
		matrixColumnTransform
				.setTransformIfTargetColumnNotEmpty(request.getParameter("transformIfTargetColumnNotEmpty"));
		matrixColumnTransform.setSort(RequestUtils.getInt(request, "sort"));
		matrixColumnTransform.setLocked(RequestUtils.getInt(request, "locked"));
		matrixColumnTransform.setType(request.getParameter("type"));
		matrixColumnTransform.setCreateBy(actorId);
		matrixColumnTransform.setUpdateBy(actorId);
		matrixColumnTransformService.save(matrixColumnTransform);

		return this.list(request, modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		MatrixColumnTransform matrixColumnTransform = matrixColumnTransformService
				.getMatrixColumnTransform(RequestUtils.getLong(request, "id"));
		request.setAttribute("matrixColumnTransform", matrixColumnTransform);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("matrixColumnTransform.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/matrix/columnTransform/view");
	}

}
