package com.glaf.workflow.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ProcessDefinitionQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.exception.ActivitiConflictException;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.repository.BaseProcessDefinitionResource;
import org.activiti.rest.service.api.repository.ProcessDefinitionActionRequest;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.repository.ProcessDefinitionsPaginateList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.glaf.core.util.RequestUtils;

@RestController
public class ProcessDefinitionRestResource extends BaseProcessDefinitionResource {
	private static final Map<String, QueryProperty> properties = new HashMap<String, QueryProperty>();

	static {
		properties.put("id", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_ID);
		properties.put("key", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_KEY);
		properties.put("category", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_CATEGORY);
		properties.put("name", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_NAME);
		properties.put("version", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_VERSION);
		properties.put("deploymentId", ProcessDefinitionQueryProperty.DEPLOYMENT_ID);
		properties.put("tenantId", ProcessDefinitionQueryProperty.PROCESS_DEFINITION_TENANT_ID);
	}

	protected RestResponseFactory restResponseFactory;

	protected RepositoryService repositoryService;

	protected ProcessDefinitionResponse activateProcessDefinition(ProcessDefinition processDefinition,
			boolean suspendInstances, Date date) {

		if (!processDefinition.isSuspended()) {
			throw new ActivitiConflictException(
					"Process definition with id '" + processDefinition.getId() + " ' is already active");
		}
		repositoryService.activateProcessDefinitionById(processDefinition.getId(), suspendInstances, date);

		ProcessDefinitionResponse response = restResponseFactory.createProcessDefinitionResponse(processDefinition);

		// No need to re-fetch the ProcessDefinition, just alter the suspended
		// state of the result-object
		response.setSuspended(false);
		return response;
	}

	@RequestMapping(value = "/workflow/process-suspend-activa/", method = RequestMethod.POST, produces = "application/json")
	public ProcessDefinitionResponse executeProcessDefinitionAction(HttpServletRequest request) {
		String processDefinitionId = RequestUtils.getString(request, "processDefinitionId");
		ProcessDefinitionResponse processDefinitionResponse = null;
		ProcessDefinition processDefinition = getProcessDefinitionFromRequest(processDefinitionId);
		String action = RequestUtils.getString(request, "action");
		Boolean isIncludeProcessInstances = RequestUtils.getBoolean(request, "isIncludeProcessInstances");
		Date date = RequestUtils.getDate(request, "date");
		// Actual action
		if (action != null) {
			if (ProcessDefinitionActionRequest.ACTION_SUSPEND.equals(action)) {
				processDefinitionResponse = suspendProcessDefinition(processDefinition, isIncludeProcessInstances,
						date);

			} else if (ProcessDefinitionActionRequest.ACTION_ACTIVATE.equals(action)) {
				processDefinitionResponse = activateProcessDefinition(processDefinition, isIncludeProcessInstances,
						date);
			}
		}
		return processDefinitionResponse;
	}

	@RequestMapping(value = "/workflow/process-definitions", produces = "application/json")
	public DataResponse getProcessDefinitions(@RequestParam Map<String, String> allRequestParams,
			HttpServletRequest request) {
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

		// Populate filter-parameters
		if (allRequestParams.containsKey("category")) {
			processDefinitionQuery.processDefinitionCategory(allRequestParams.get("category"));
		}
		if (allRequestParams.containsKey("categoryLike")) {
			processDefinitionQuery.processDefinitionCategoryLike(allRequestParams.get("categoryLike"));
		}
		if (allRequestParams.containsKey("categoryNotEquals")) {
			processDefinitionQuery.processDefinitionCategoryNotEquals(allRequestParams.get("categoryNotEquals"));
		}
		if (allRequestParams.containsKey("key")) {
			processDefinitionQuery.processDefinitionKey(allRequestParams.get("key"));
		}
		if (allRequestParams.containsKey("keyLike")) {
			processDefinitionQuery.processDefinitionKeyLike(allRequestParams.get("keyLike"));
		}
		if (allRequestParams.containsKey("name")) {
			processDefinitionQuery.processDefinitionName(allRequestParams.get("name"));
		}
		if (allRequestParams.containsKey("nameLike")) {
			processDefinitionQuery.processDefinitionNameLike(allRequestParams.get("nameLike"));
		}
		if (allRequestParams.containsKey("resourceName")) {
			processDefinitionQuery.processDefinitionResourceName(allRequestParams.get("resourceName"));
		}
		if (allRequestParams.containsKey("resourceNameLike")) {
			processDefinitionQuery.processDefinitionResourceNameLike(allRequestParams.get("resourceNameLike"));
		}
		if (allRequestParams.containsKey("version")) {
			processDefinitionQuery.processDefinitionVersion(Integer.valueOf(allRequestParams.get("version")));
		}
		if (allRequestParams.containsKey("suspended")) {
			Boolean suspended = Boolean.valueOf(allRequestParams.get("suspended"));
			if (suspended != null) {
				if (suspended) {
					processDefinitionQuery.suspended();
				} else {
					processDefinitionQuery.active();
				}
			}
		}
		if (allRequestParams.containsKey("latest")) {
			Boolean latest = Boolean.valueOf(allRequestParams.get("latest"));
			if (latest != null && latest) {
				processDefinitionQuery.latestVersion();
			}
		}
		if (allRequestParams.containsKey("deploymentId")) {
			processDefinitionQuery.deploymentId(allRequestParams.get("deploymentId"));
		}
		if (allRequestParams.containsKey("startableByUser")) {
			processDefinitionQuery.startableByUser(allRequestParams.get("startableByUser"));
		}
		if (allRequestParams.containsKey("tenantId")) {
			processDefinitionQuery.processDefinitionTenantId(allRequestParams.get("tenantId"));
		}
		if (allRequestParams.containsKey("tenantIdLike")) {
			processDefinitionQuery.processDefinitionTenantIdLike(allRequestParams.get("tenantIdLike"));
		}

		return new ProcessDefinitionsPaginateList(restResponseFactory).paginateList(allRequestParams,
				processDefinitionQuery, "name", properties);
	}

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setRestResponseFactory(RestResponseFactory restResponseFactory) {
		this.restResponseFactory = restResponseFactory;
	}

	protected ProcessDefinitionResponse suspendProcessDefinition(ProcessDefinition processDefinition,
			boolean suspendInstances, Date date) {

		if (processDefinition.isSuspended()) {
			throw new ActivitiConflictException(
					"Process definition with id '" + processDefinition.getId() + " ' is already suspended");
		}
		repositoryService.suspendProcessDefinitionById(processDefinition.getId(), suspendInstances, date);

		ProcessDefinitionResponse response = restResponseFactory.createProcessDefinitionResponse(processDefinition);

		// No need to re-fetch the ProcessDefinition, just alter the suspended
		// state of the result-object
		response.setSuspended(true);
		return response;
	}
}
