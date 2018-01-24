package com.glaf.base.project.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.project.domain.ProjectAccess;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.query.ProjectQuery;

@Transactional(readOnly = true)
public interface ProjectService {

	/**
	 * 新增项目的人员授权
	 * 
	 * @param projectId
	 * @param actorId
	 */
	@Transactional
	void createAccessor(long projectId, String actorId);

	/**
	 * 删除项目的人员授权
	 * 
	 * @param projectId
	 * @param actorId
	 */
	@Transactional
	void deleteAccessor(long projectId, String actorId);

	/**
	 * 根据主键删除项目信息
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 获取全部项目的授权信息
	 * 
	 * @return
	 */
	List<ProjectAccess> getAllProjectAccesses();

	/**
	 * 
	 * @param projectIds
	 * @return
	 */
	List<Long> getDatabaseIds(List<Long> projectIds);

	/**
	 * 获取某个项目的标段信息
	 * 
	 * @param projectId
	 * @return
	 */
	List<Long> getDatabaseIds(Long projectId);

	/**
	 * 根据主键获取一个项目信息
	 * 
	 * @return
	 */
	Project getProject(Long id);

	/**
	 * 根据code获取一个项目信息
	 * 
	 * @return
	 */
	Project getProjectByCode(String code);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getProjectCountByQueryCriteria(ProjectQuery query);

	/**
	 * 某个用户能看到的项目信息
	 * 
	 * @param actorId
	 * @return
	 */
	List<Project> getProjects(String actorId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Project> getProjectsByQueryCriteria(int start, int pageSize, ProjectQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Project> list(ProjectQuery query);

	/**
	 * 保存一条项目信息
	 * 
	 * @return
	 */
	@Transactional
	void save(Project project);

	/**
	 * 保存项目的人员信息
	 * 
	 * @param projectId
	 * @param accessors
	 */
	@Transactional
	void saveAccessors(long projectId, Collection<String> accessors);

	/**
	 * 保存人员的项目信息
	 * 
	 * @param accessor
	 * @param projectIds
	 */
	@Transactional
	void saveAccessors(String accessor, Collection<Long> projectIds);

	/**
	 * 保存项目的标段信息
	 * 
	 * @param projectId
	 * @param databaseIds
	 */
	@Transactional
	void saveAll(Long projectId, List<Long> databaseIds);

	/**
	 * 保存一条项目信息
	 * 
	 * @return
	 */
	@Transactional
	void update(Project project);

	/**
	 * 更新用户项目视图
	 * 
	 * @param userId
	 */
	@Transactional
	void updateProjectView(String userId);

	/**
	 * 更新节点的treeId
	 * 
	 * @param treeIdMap
	 */
	@Transactional
	void updateTreeIds(Map<Long, String> treeIdMap);

}
