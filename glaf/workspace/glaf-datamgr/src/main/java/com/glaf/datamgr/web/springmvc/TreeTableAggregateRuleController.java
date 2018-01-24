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
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.datamgr.domain.TreeTableAggregate;
import com.glaf.datamgr.domain.TreeTableAggregateRule;
import com.glaf.datamgr.service.TreeTableAggregateService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/treeTableAggregateRule")
@RequestMapping("/sys/treeTableAggregateRule")
public class TreeTableAggregateRuleController {
	protected static final Log logger = LogFactory.getLog(TreeTableAggregateRuleController.class);

	protected IDatabaseService databaseService;

	protected TreeTableAggregateService treeTableAggregateService;

	public TreeTableAggregateRuleController() {

	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		Long id = RequestUtils.getLong(request, "id");
		treeTableAggregateService.deleteRuleById(id);
		return ResponseUtils.responseResult(true);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		TreeTableAggregate treeTableAggregate = null;
		TreeTableAggregateRule treeTableAggregateRule = treeTableAggregateService
				.getTreeTableAggregateRule(RequestUtils.getLong(request, "id"));
		if (treeTableAggregateRule != null) {
			request.setAttribute("treeTableAggregateRule", treeTableAggregateRule);
			request.setAttribute("aggregateId", treeTableAggregateRule.getAggregateId());
			treeTableAggregate = treeTableAggregateService
					.getTreeTableAggregate(treeTableAggregateRule.getAggregateId());
		} else {
			long aggregateId = RequestUtils.getLong(request, "aggregateId");
			if (aggregateId > 0) {
				treeTableAggregate = treeTableAggregateService.getTreeTableAggregate(aggregateId);
			}
		}
		logger.debug("treeTableAggregate:" + treeTableAggregate);
		if (treeTableAggregate != null && treeTableAggregate.getTargetTableName() != null) {
			List<TreeTableAggregateRule> rules = treeTableAggregateService
					.getTreeTableAggregateRulesByTableName(treeTableAggregate.getTargetTableName());
			List<String> names = new ArrayList<String>();
			for (TreeTableAggregateRule rule : rules) {
				if (rule.getTargetColumnName() != null) {
					rule.setTargetColumnName(rule.getTargetColumnName().toLowerCase());
					names.add(rule.getTargetColumnName().toLowerCase());
				}
			}
			List<Long> databaseIds = StringTools.splitToLong(treeTableAggregate.getDatabaseIds(), ",");
			logger.debug("databaseIds:" + databaseIds);
			for (Long databaseId : databaseIds) {
				Database targetDatabase = databaseService.getDatabaseById(databaseId);
				List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(targetDatabase.getName(),
						treeTableAggregate.getSourceTableName());
				if (columns != null && columns.size() > 0) {
					request.setAttribute("columns", columns);
					//logger.debug("columns:" + columns);
					break;
				}
			}
			request.setAttribute("rules", rules);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("treeTableAggregateRule.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/treeTableAggregate/rule_edit", modelMap);
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

		return new ModelAndView("/datamgr/treeTableAggregate/rule_list", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRule")
	public byte[] saveRule(HttpServletRequest request) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TreeTableAggregateRule treeTableAggregateRule = new TreeTableAggregateRule();
		try {
			Tools.populate(treeTableAggregateRule, params);
			treeTableAggregateRule.setAggregateId(RequestUtils.getLong(request, "aggregateId"));
			treeTableAggregateRule.setAggregateType(request.getParameter("aggregateType"));
			treeTableAggregateRule.setName(request.getParameter("name"));
			treeTableAggregateRule.setTitle(request.getParameter("title"));
			treeTableAggregateRule.setSourceColumnName(request.getParameter("sourceColumnName"));
			treeTableAggregateRule.setSourceColumnTitle(request.getParameter("sourceColumnTitle"));
			treeTableAggregateRule.setTargetColumnName(request.getParameter("targetColumnName"));
			treeTableAggregateRule.setTargetColumnTitle(request.getParameter("targetColumnTitle"));
			treeTableAggregateRule.setTargetColumnType(request.getParameter("targetColumnType"));
			treeTableAggregateRule.setLocked(RequestUtils.getInt(request, "locked"));
			this.treeTableAggregateService.saveRule(treeTableAggregateRule);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setTreeTableAggregateService(TreeTableAggregateService treeTableAggregateService) {
		this.treeTableAggregateService = treeTableAggregateService;
	}

}
