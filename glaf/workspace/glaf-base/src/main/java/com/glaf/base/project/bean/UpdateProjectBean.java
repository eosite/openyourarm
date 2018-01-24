package com.glaf.base.project.bean;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.glaf.base.project.domain.Project;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;
import com.glaf.core.context.ContextFactory;

public class UpdateProjectBean {

	protected ProjectService projectService;

	public ProjectService getProjectService() {
		if (projectService == null) {
			projectService = ContextFactory.getBean("projectService");
		}
		return projectService;
	}

	protected String getTreeId(Map<Long, Project> dataMap, Project tree) {
		long parentId = tree.getParentId();
		long id = tree.getId();
		Project parent = dataMap.get(parentId);
		if (parent != null && parent.getId() != 0) {
			if (StringUtils.isEmpty(parent.getTreeId())) {
				return getTreeId(dataMap, parent) + id + "|";
			}
			if (!parent.getTreeId().endsWith("|")) {
				parent.setTreeId(parent.getTreeId() + "|");
			}
			if (parent.getTreeId() != null) {
				return parent.getTreeId() + id + "|";
			}
		}
		return tree.getTreeId();
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	/**
	 * 更新用户项目视图
	 * @param userId
	 */
	public void updateProjectView(String userId){
		getProjectService().updateProjectView(userId);
	}

	public void updateTreeIds() {
		ProjectQuery query = new ProjectQuery();
		query.setParentId(0L);
		List<Project> topProjects = getProjectService().list(query);
		if (topProjects != null && !topProjects.isEmpty()) {
			Map<Long, String> treeIdMap = new HashMap<Long, String>();
			for (Project root : topProjects) {
				if (StringUtils.isEmpty(root.getTreeId())) {
					treeIdMap.put(root.getId(), root.getId() + "|");
				}
			}
			if (!treeIdMap.isEmpty()) {
				getProjectService().updateTreeIds(treeIdMap);
			}
			query = new ProjectQuery();
			List<Project> trees = getProjectService().list(query);
			if (trees != null && !trees.isEmpty()) {
				Map<Long, Project> dataMap = new HashMap<Long, Project>();
				for (Project tree : trees) {
					dataMap.put(tree.getId(), tree);
				}
				treeIdMap.clear();
				for (Project tree : trees) {
					if (StringUtils.isEmpty(tree.getTreeId())) {
						String treeId = this.getTreeId(dataMap, tree);
						if (treeId != null && treeId.endsWith("|")) {
							treeIdMap.put(tree.getId(), treeId);
						}
					}
				}
				getProjectService().updateTreeIds(treeIdMap);
			}
		}
	}

}
