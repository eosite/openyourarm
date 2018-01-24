package com.glaf.datamgr.web.springmvc;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.domain.DataItemDefinition;
import com.glaf.datamgr.domain.SyntheticFlow;
import com.glaf.datamgr.query.SyntheticFlowQuery;
import com.glaf.datamgr.service.DataItemDefinitionService;
import com.glaf.datamgr.service.SyntheticFlowService;
import com.glaf.datamgr.util.DataItemFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/sys/syntheticFlow")
@RequestMapping("/sys/syntheticFlow")
public class SyntheticFlowController {
	protected static final Log logger = LogFactory.getLog(SyntheticFlowController.class);

	protected DataItemDefinitionService dataItemDefinitionService;

	protected SyntheticFlowService syntheticFlowService;

	public SyntheticFlowController() {

	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		JSONArray array = null;
		String currentType = null;
		String code = request.getParameter("code");
		String currentStep = request.getParameter("currentStep");
		if (StringUtils.isNotEmpty(currentStep) && StringUtils.isNotEmpty(code)) {
			array = DataItemFactory.getJSONArray(code);
			request.setAttribute("array", array);
			DataItemDefinition item = dataItemDefinitionService.getDataItemDefinitionByCode(code);
			if (item != null) {
				request.setAttribute("item", item);
				currentType = item.getType();
				request.setAttribute("currentType", currentType);
				SyntheticFlowQuery query = new SyntheticFlowQuery();
				query.currentStep(currentStep);
				query.currentType(currentType);
				List<SyntheticFlow> rows = syntheticFlowService.list(query);
				request.setAttribute("rows", rows);
			}
		}

		List<DataItemDefinition> definitions = dataItemDefinitionService.getDataItemDefinitions("synthetic");
		request.setAttribute("definitions", definitions);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("syntheticFlow.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/syntheticFlow/edit", modelMap);
	}

	@ResponseBody
	@RequestMapping("/save")
	public byte[] save(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		String actorId = RequestUtils.getActorId(request);
		String currentType = null;
		String code = request.getParameter("code");
		String currentStep = request.getParameter("currentStep");
		String previousType = request.getParameter("previousType");
		String nextType = request.getParameter("nextType");
		String previousObjectIds = request.getParameter("previousObjectIds");
		String nextObjectIds = request.getParameter("nextObjectIds");
		if (StringUtils.isNotEmpty(currentStep) && StringUtils.isNotEmpty(code)) {
			DataItemDefinition current = dataItemDefinitionService.getDataItemDefinitionByCode(code);
			if (current != null) {
				currentType = current.getType();
				int sort = 0;
				List<SyntheticFlow> flows = new ArrayList<SyntheticFlow>();
				if (StringUtils.isNotEmpty(previousObjectIds)) {
					StringTokenizer token = new StringTokenizer(previousObjectIds, ",");
					while (token.hasMoreTokens()) {
						String str = token.nextToken();
						sort++;
						SyntheticFlow flow = new SyntheticFlow();
						flow.setPreviousStep(str);
						flow.setPreviousType(previousType);
						flow.setSort(sort);
						flow.setCreateBy(actorId);
						flows.add(flow);
					}
				}

				if (StringUtils.isNotEmpty(nextObjectIds)) {
					sort = 0;
					StringTokenizer token = new StringTokenizer(nextObjectIds, ",");
					while (token.hasMoreTokens()) {
						String str = token.nextToken();
						sort++;
						SyntheticFlow flow = new SyntheticFlow();
						flow.setNextStep(str);
						flow.setNextType(nextType);
						flow.setSort(sort);
						flow.setCreateBy(actorId);
						flows.add(flow);
					}
				}

				syntheticFlowService.saveAll(currentStep, currentType, flows);
				return ResponseUtils.responseResult(true);
			}
		}

		return ResponseUtils.responseResult(false);
	}

	@javax.annotation.Resource
	public void setDataItemDefinitionService(DataItemDefinitionService dataItemDefinitionService) {
		this.dataItemDefinitionService = dataItemDefinitionService;
	}

	@javax.annotation.Resource
	public void setSyntheticFlowService(SyntheticFlowService syntheticFlowService) {
		this.syntheticFlowService = syntheticFlowService;
	}

}
