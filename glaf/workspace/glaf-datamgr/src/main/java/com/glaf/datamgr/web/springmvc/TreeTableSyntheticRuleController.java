package com.glaf.datamgr.web.springmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.TreeTableSynthetic;
import com.glaf.datamgr.domain.TreeTableSyntheticRule;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.TreeTableSyntheticService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/treeTableSyntheticRule")
@RequestMapping("/sys/treeTableSyntheticRule")
public class TreeTableSyntheticRuleController {
	protected static final Log logger = LogFactory.getLog(TreeTableSyntheticRuleController.class);

	protected DataSetService dataSetService;

	protected TreeTableSyntheticService treeTableSyntheticService;

	public TreeTableSyntheticRuleController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		treeTableSyntheticService.deleteRuleById(id);
		return ResponseUtils.responseResult(true);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		TreeTableSynthetic treeTableSynthetic = null;
		List<SelectSegment> columns = null;
		TreeTableSyntheticRule treeTableSyntheticRule = treeTableSyntheticService
				.getTreeTableSyntheticRule(RequestUtils.getLong(request, "id"));
		if (treeTableSyntheticRule != null) {
			request.setAttribute("treeTableSyntheticRule", treeTableSyntheticRule);
			request.setAttribute("syntheticId", treeTableSyntheticRule.getSyntheticId());
			treeTableSynthetic = treeTableSyntheticService
					.getTreeTableSynthetic(treeTableSyntheticRule.getSyntheticId());
			DataSet dataSet = dataSetService.getDataSet(treeTableSyntheticRule.getDatasetId());
			if (dataSet != null) {
				request.setAttribute("datasetName", dataSet.getName());
				request.setAttribute("datasetTitle", dataSet.getTitle());
				columns = dataSet.getSelectSegments();
				if (columns != null && !columns.isEmpty()) {
					for (SelectSegment seg : columns) {
						String column = null;
						if (seg.getColumnName() != null) {
							if ( StringUtils.startsWithIgnoreCase(seg.getColumnLabel(), "field_")) {
								column = seg.getColumnLabel();
								logger.debug("label:"+seg.getColumnLabel());
							} else {
								if (StringUtils.isNotEmpty(seg.getColumnName())) {
									column = seg.getColumnName();
								}
							}
						}
						if(column != null){
							column = column.toLowerCase();
						}
						seg.setColumnName(column);
					}
					request.setAttribute("columns", columns);
				}
			}
		} else {
			long syntheticId = RequestUtils.getLong(request, "syntheticId");
			if (syntheticId > 0) {
				treeTableSynthetic = treeTableSyntheticService.getTreeTableSynthetic(syntheticId);
			}
		}

		if (treeTableSynthetic != null && treeTableSynthetic.getTargetTableName() != null) {
			List<TreeTableSyntheticRule> rules = treeTableSyntheticService
					.getTreeTableSyntheticRulesByTableName(treeTableSynthetic.getTargetTableName());
			List<String> names = new ArrayList<String>();
			for (TreeTableSyntheticRule rule : rules) {
				if (rule.getColumnName() != null) {
					rule.setColumnName(rule.getColumnName().toLowerCase());
					names.add(rule.getColumnName().toLowerCase());
				}
			}
			if (columns != null && !columns.isEmpty()) {
				for (SelectSegment seg : columns) {
					String column = null;
					if (seg.getColumnName() != null) {
						if ( StringUtils.startsWithIgnoreCase(seg.getColumnLabel(), "field_")) {
							column = seg.getColumnLabel();
							logger.debug("label:"+seg.getColumnLabel());
						} else {
							if (StringUtils.isNotEmpty(seg.getColumnName())) {
								column = seg.getColumnName();
							}
						}
						logger.debug(column);
						if (column != null && !names.contains(column.toLowerCase())) {
							TreeTableSyntheticRule rule = new TreeTableSyntheticRule();
							rule.setColumnTitle(seg.getTitle());
							rule.setColumnLabel(seg.getColumnLabel());
							rule.setColumnName(column.toLowerCase());
							rule.setType(rule.getType());
							rules.add(rule);
						}
					}
				}
			}
			request.setAttribute("rules", rules);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("treeTableSyntheticRule.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableSynthetic/rule_edit", modelMap);
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

		return new ModelAndView("/datamgr/treeTableSynthetic/rule_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRule")
	public byte[] saveRule(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableSyntheticRule treeTableSyntheticRule = new TreeTableSyntheticRule();
		try {
			Tools.populate(treeTableSyntheticRule, params);
			treeTableSyntheticRule.setSyntheticId(RequestUtils.getLong(request, "syntheticId"));
			treeTableSyntheticRule.setName(request.getParameter("name"));
			treeTableSyntheticRule.setTitle(request.getParameter("title"));
			treeTableSyntheticRule.setColumnName(request.getParameter("columnName"));
			treeTableSyntheticRule.setColumnLabel(request.getParameter("columnLabel"));
			treeTableSyntheticRule.setColumnTitle(request.getParameter("columnTitle"));
			treeTableSyntheticRule.setColumnSize(RequestUtils.getInt(request, "columnSize"));
			treeTableSyntheticRule.setType(request.getParameter("type"));
			treeTableSyntheticRule.setMappingToSourceIdColumn(request.getParameter("mappingToSourceIdColumn"));
			treeTableSyntheticRule.setMappingToTargetColumn(request.getParameter("mappingToTargetColumn"));
			treeTableSyntheticRule.setMappingToTargetAlias(request.getParameter("mappingToTargetAlias"));
			treeTableSyntheticRule.setDatasetId(request.getParameter("datasetId"));
			treeTableSyntheticRule.setSqlDefId(RequestUtils.getLong(request, "sqlDefId"));
			treeTableSyntheticRule.setLocked(RequestUtils.getInt(request, "locked"));
			this.treeTableSyntheticService.saveRule(treeTableSyntheticRule);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticService(TreeTableSyntheticService treeTableSyntheticService) {
		this.treeTableSyntheticService = treeTableSyntheticService;
	}

}
