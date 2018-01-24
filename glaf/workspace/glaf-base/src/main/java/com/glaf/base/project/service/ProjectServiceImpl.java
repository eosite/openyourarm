package com.glaf.base.project.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.Database;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITableDataService;
import com.glaf.base.project.domain.ProjectAccess;
import com.glaf.base.project.domain.ProjectDatabase;
import com.glaf.base.project.domain.ProjectOwner;
import com.glaf.base.project.domain.ProjectSubordinate;
import com.glaf.base.project.domain.ProjectView;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysRole;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.mapper.ProjectAccessMapper;
import com.glaf.base.project.mapper.ProjectDatabaseMapper;
import com.glaf.base.project.mapper.ProjectMapper;
import com.glaf.base.project.mapper.ProjectOwnerMapper;
import com.glaf.base.project.mapper.ProjectSubordinateMapper;
import com.glaf.base.project.mapper.ProjectViewMapper;
import com.glaf.base.project.query.ProjectDatabaseQuery;
import com.glaf.base.project.query.ProjectQuery;
import com.glaf.base.project.service.ProjectService;

@Service("projectService")
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjectMapper projectMapper;

	protected ProjectDatabaseMapper projectDatabaseMapper;

	protected ProjectAccessMapper projectAccessMapper;

	protected ProjectOwnerMapper projectOwnerMapper;

	protected ProjectViewMapper projectViewMapper;

	protected ProjectSubordinateMapper projectSubordinateMapper;

	protected IDatabaseService databaseService;

	protected ITableDataService tableDataService;

	protected SysUserService sysUserService;

	protected SysRoleService sysRoleService;

	protected SysDepartmentService sysDepartmentService;

	public ProjectServiceImpl() {

	}

	public int count(ProjectQuery query) {
		return projectMapper.getProjectCount(query);
	}

	@Transactional
	public void createAccessor(long projectId, String actorId) {
		ProjectAccess model = new ProjectAccess();
		model.setId(idGenerator.nextId("PROJECT_ACCESS"));
		model.setActorId(actorId);
		model.setProjectId(projectId);
		projectAccessMapper.deleteAccessor(model);
		projectAccessMapper.insertProjectAccess(model);
	}

	@Transactional
	public void deleteAccessor(long projectId, String actorId) {
		ProjectAccess model = new ProjectAccess();
		model.setActorId(actorId);
		model.setProjectId(projectId);
		projectAccessMapper.deleteAccessor(model);
	}

	@Transactional
	public void deleteById(Long projectId) {
		if (projectId != null) {
			ProjectQuery query = new ProjectQuery();
			query.parentId(projectId);
			int count = projectMapper.getProjectCount(query);
			if (count > 0) {
				throw new RuntimeException("Children nodes exists");
			}
			TableModel table = new TableModel();
			table.setTableName("PROJECT_SUBORDINATE");
			table.addLongColumn("PROJECTID_", projectId);
			tableDataService.deleteTableData(table);

			TableModel table2 = new TableModel();
			table2.setTableName("PROJECT_SUBORDINATE");
			table2.addLongColumn("SUBORDINATEID_", projectId);
			tableDataService.deleteTableData(table2);

			TableModel table3 = new TableModel();
			table3.setTableName("PROJECT_DATABASE");
			table3.addLongColumn("PROJECTID_", projectId);
			tableDataService.deleteTableData(table3);

			projectAccessMapper.deleteProjectAccessByProjectId(projectId);
			projectViewMapper.deleteProjectViewByProjectId(projectId);
			projectMapper.deleteProjectById(projectId);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> projectIds) {
		if (projectIds != null && !projectIds.isEmpty()) {
			for (Long projectId : projectIds) {
				TableModel table = new TableModel();
				table.setTableName("PROJECT_SUBORDINATE");
				table.addLongColumn("PROJECTID_", projectId);
				tableDataService.deleteTableData(table);

				TableModel table2 = new TableModel();
				table2.setTableName("PROJECT_SUBORDINATE");
				table2.addLongColumn("SUBORDINATEID_", projectId);
				tableDataService.deleteTableData(table2);

				TableModel table3 = new TableModel();
				table3.setTableName("PROJECT_DATABASE");
				table3.addLongColumn("PROJECTID_", projectId);
				tableDataService.deleteTableData(table3);

				projectAccessMapper.deleteProjectAccessByProjectId(projectId);
				projectViewMapper.deleteProjectViewByProjectId(projectId);
				projectMapper.deleteProjectById(projectId);
			}
		}
	}

	public List<ProjectAccess> getAllProjectAccesses() {
		return projectAccessMapper.getAllProjectAccesses();
	}

	@Override
	public List<Long> getDatabaseIds(List<Long> projectIds) {
		List<Long> databaseIds = new ArrayList<Long>();
		ProjectDatabaseQuery query = new ProjectDatabaseQuery();
		query.setProjectIds(projectIds);
		List<ProjectDatabase> list = projectDatabaseMapper.getProjectDatabases(query);
		if (list != null && !list.isEmpty()) {
			for (ProjectDatabase pd : list) {
				databaseIds.add(pd.getDatabaseId());
			}
		}
		return databaseIds;
	}

	public List<Long> getDatabaseIds(Long projectId) {
		List<Long> databaseIds = new ArrayList<Long>();
		List<ProjectDatabase> list = projectDatabaseMapper.getProjectDatabasesByProjectId(projectId);
		if (list != null && !list.isEmpty()) {
			for (ProjectDatabase pd : list) {
				databaseIds.add(pd.getDatabaseId());
			}
		}
		return databaseIds;
	}

	public Project getProject(Long id) {
		if (id == null) {
			return null;
		}
		Project project = projectMapper.getProjectById(id);
		if (project != null) {
			List<ProjectAccess> accesses = projectAccessMapper.getProjectAccessesByProjectId(id);
			if (accesses != null && !accesses.isEmpty()) {
				for (ProjectAccess access : accesses) {
					project.addAccessor(access.getActorId());
				}
			}
			List<ProjectOwner> owners = projectOwnerMapper.getProjectOwneresByProjectId(id);
			project.setOwners(owners);

			List<ProjectSubordinate> subordinates = projectSubordinateMapper.getProjectSubordinatesByProjectId(id);
			if (subordinates != null && !subordinates.isEmpty()) {
				for (ProjectSubordinate sub : subordinates) {
					project.addSubordinate(sub.getSubordinateId());
				}
			}
		}
		return project;
	}

	protected List<ProjectAccess> getProjectAccesses(long projectId) {
		List<ProjectAccess> accesses = projectAccessMapper.getProjectAccessesByProjectId(projectId);
		return accesses;
	}

	/**
	 * 根据code获取一个项目信息
	 * 
	 * @return
	 */
	public Project getProjectByCode(String code) {
		return projectMapper.getProjectByCode(code);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProjectCountByQueryCriteria(ProjectQuery query) {
		return projectMapper.getProjectCount(query);
	}

	/**
	 * 获取某个用户的项目列表，包含全部的下级节点
	 */
	public List<Project> getProjects(String actorId) {
		ProjectQuery query = new ProjectQuery();
		List<Project> projects = new ArrayList<Project>();
		List<Project> list = projectMapper.getProjects(query);
		if (list != null && !list.isEmpty()) {
			/**
			 * 取得某个用户的项目节点
			 */
			List<ProjectAccess> x_accesses = projectAccessMapper.getProjectAccessesByActorId(actorId);
			List<ProjectSubordinate> all_subs = projectSubordinateMapper.getAllProjectSubordinates();
			List<SysRole> roles = sysUserService.getUserRoles(actorId);
			List<String> roleCodes = new ArrayList<String>();
			List<Long> projectIds = new ArrayList<Long>();
			Map<Long, Project> projectMap = new HashMap<Long, Project>();
			Map<Long, List<ProjectSubordinate>> projectSubMap = new HashMap<Long, List<ProjectSubordinate>>();

			if (x_accesses != null && !x_accesses.isEmpty()) {
				for (ProjectAccess access : x_accesses) {
					if (access.getDynamic() == null) {// 原始授权,并非动态权限
						projectIds.add(access.getProjectId());// 用户拥有的项目集合
					}
				}
			}

			if (roles != null && !roles.isEmpty()) {
				for (SysRole role : roles) {
					roleCodes.add(role.getCode());// 用户拥有的角色集合
				}
			}

			for (Project project : list) {
				projectMap.put(project.getId(), project);
				/**
				 * 默认创建者拥有该项目
				 */
				if (StringUtils.equals(project.getCreateBy(), actorId)) {
					if (!projects.contains(project)) {
						projects.add(project);
					}
				}

				/**
				 * 系统管理员拥有该项目
				 */
				if (roleCodes.contains(SysConstants.SYSTEM_ADMINISTRATOR)) {
					if (!projects.contains(project)) {
						projects.add(project);
					}
				}

				/**
				 * 项目管理员拥有该项目
				 */
				if (roleCodes.contains(SysConstants.PROJECT_ADMIN)) {
					if (!projects.contains(project)) {
						projects.add(project);
					}
				}

				if (all_subs != null && !all_subs.isEmpty()) {
					for (ProjectSubordinate sub : all_subs) {
						if (project.getId() == sub.getProjectId()) {
							List<ProjectSubordinate> subs = projectSubMap.get(project.getId());
							if (subs == null) {
								subs = new ArrayList<ProjectSubordinate>();
							}
							subs.add(sub);
							projectSubMap.put(project.getId(), subs);
						}
					}
				}
			}

			for (Project project : list) {
				if (projectIds.contains(project.getId())) {
					/**
					 * 包含项目及全部子项目
					 */
					ProjectQuery q = new ProjectQuery();
					q.treeIdLike(project.getTreeId() + "%");
					List<Project> rows = projectMapper.getProjects(q);
					if (rows != null && !rows.isEmpty()) {
						for (Project p : rows) {
							if (!projects.contains(p)) {
								projects.add(p);
							}
						}
					}
					if (!projects.contains(project)) {
						projects.add(project);
					}
				}
			}

			for (Project project : list) {
				/**
				 * 检查是否为从属项目，如果是从属节点，也一起加入
				 */
				List<ProjectSubordinate> subs = projectSubMap.get(project.getId());
				if (subs != null && !subs.isEmpty()) {
					for (ProjectSubordinate sub : subs) {
						Project p = projectMap.get(sub.getSubordinateId());
						if (p != null && !projects.contains(p)) {
							Project parent = projectMap.get(p.getParentId());
							if (projects.contains(parent)) {// 如果包含了父节点，才能包含子节点
								projects.add(p);
							}
						}
					}
				}
			}
		}
		return projects;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Project> getProjectsByQueryCriteria(int start, int pageSize, ProjectQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Project> rows = sqlSessionTemplate.selectList("getProjects", query, rowBounds);
		return rows;
	}

	public List<Project> list(ProjectQuery query) {
		List<Project> list = projectMapper.getProjects(query);
		if (list != null && !list.isEmpty()) {
			DatabaseQuery q2 = new DatabaseQuery();
			List<Database> databases = databaseService.list(q2);
			Map<Long, Database> dbMap = new HashMap<Long, Database>();
			for (Database database : databases) {
				dbMap.put(database.getId(), database);
			}
			ProjectDatabaseQuery q = new ProjectDatabaseQuery();
			List<ProjectDatabase> rows = projectDatabaseMapper.getProjectDatabases(q);
			for (Project project : list) {
				for (ProjectDatabase pd : rows) {
					Database db = dbMap.get(pd.getDatabaseId());
					if (db != null) {
						project.addDatabase(db);
					}
				}
			}
		}
		return list;
	}

	@Transactional
	public void save(Project project) {
		if (project.getId() == 0) {
			project.setId(idGenerator.nextId("PROJECT_QUERY"));
			project.setCreateTime(new Date());
			long parentId = project.getParentId();
			if (parentId > 0) {
				Project parent = this.getProject(parentId);
				if (parent != null) {
					project.setCategory(parent.getCategory());
					project.setLevel(parent.getLevel() + 1);
					if (parent.getTreeId() != null) {
						project.setTreeId(parent.getTreeId() + project.getId() + "|");
					}
				}
			} else {
				project.setLevel(0);
				project.setTreeId(project.getId() + "|");
			}
			projectMapper.insertProject(project);
		} else {
			long parentId = project.getParentId();
			if (parentId > 0 && parentId != project.getId()) {
				Project parent = this.getProject(parentId);
				if (parent != null) {
					project.setLevel(parent.getLevel() + 1);
					if (parent.getTreeId() != null) {
						project.setTreeId(parent.getTreeId() + project.getId() + "|");
					}
				}
			} else {
				project.setLevel(0);
				project.setTreeId(project.getId() + "|");
			}
			project.setUpdateTime(new Date());
			projectMapper.updateProject(project);
		}

		if (project.getOwners() != null) {
			projectOwnerMapper.deleteProjectOwnerByProjectId(project.getId());
			for (ProjectOwner owner : project.getOwners()) {
				owner.setId(idGenerator.nextId("PROJECT_OWNER"));
				owner.setActorId(owner.getActorId());
				owner.setProjectId(project.getId());
				projectOwnerMapper.insertProjectOwner(owner);
			}
		}

		if (project.getActorIds() != null) {
			projectAccessMapper.deleteProjectAccessByProjectId(project.getId());
			for (String actorId : project.getActorIds()) {
				ProjectAccess model = new ProjectAccess();
				model.setId(idGenerator.nextId("PROJECT_ACCESS"));
				model.setActorId(actorId);
				model.setProjectId(project.getId());
				projectAccessMapper.insertProjectAccess(model);
			}
		}

		if (project.getSubordinateIds() != null) {
			projectSubordinateMapper.deleteProjectSubordinateByProjectId(project.getId());
			for (Long subordinateId : project.getSubordinateIds()) {
				ProjectSubordinate model = new ProjectSubordinate();
				model.setId(idGenerator.nextId("PROJECT_SUBORDINATE"));
				model.setSubordinateId(subordinateId);
				model.setProjectId(project.getId());
				projectSubordinateMapper.insertProjectSubordinate(model);
			}
		}
	}

	/**
	 * 保存项目访问者
	 * 
	 * @return
	 */
	@Transactional
	public void saveAccessors(long projectId, Collection<String> accessors) {
		projectAccessMapper.deleteProjectAccessByProjectId(projectId);
		for (String actorId : accessors) {
			ProjectAccess model = new ProjectAccess();
			model.setId(idGenerator.nextId("PROJECT_ACCESS"));
			model.setActorId(actorId);
			model.setProjectId(projectId);
			projectAccessMapper.deleteAccessor(model);
			projectAccessMapper.insertProjectAccess(model);
		}
	}

	/**
	 * 保存项目访问者
	 * 
	 * @return
	 */
	@Transactional
	public void saveAccessors(String accessor, Collection<Long> projectIds) {
		projectAccessMapper.deleteProjectAccessByActorId(accessor);
		for (Long projectId : projectIds) {
			ProjectAccess model = new ProjectAccess();
			model.setId(idGenerator.nextId("PROJECT_ACCESS"));
			model.setActorId(accessor);
			model.setProjectId(projectId);
			projectAccessMapper.deleteAccessor(model);
			projectAccessMapper.insertProjectAccess(model);
		}
	}

	@Transactional
	public void saveAll(Long projectId, List<Long> databaseIds) {
		projectDatabaseMapper.deleteProjectDatabasesByProjectId(projectId);
		if (databaseIds != null && !databaseIds.isEmpty()) {
			for (Long databaseId : databaseIds) {
				projectDatabaseMapper.deleteProjectDatabasesByDatabaseId(databaseId);
				ProjectDatabase pd = new ProjectDatabase();
				pd.setId(idGenerator.nextId("PROJECT_DATABASE"));
				pd.setDatabaseId(databaseId);
				pd.setProjectId(projectId);
				projectDatabaseMapper.insertProjectDatabase(pd);
			}
		}
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setProjectAccessMapper(ProjectAccessMapper projectAccessMapper) {
		this.projectAccessMapper = projectAccessMapper;
	}

	@javax.annotation.Resource
	public void setProjectDatabaseMapper(ProjectDatabaseMapper projectDatabaseMapper) {
		this.projectDatabaseMapper = projectDatabaseMapper;
	}

	@javax.annotation.Resource
	public void setProjectMapper(ProjectMapper projectMapper) {
		this.projectMapper = projectMapper;
	}

	@javax.annotation.Resource
	public void setProjectOwnerMapper(ProjectOwnerMapper projectOwnerMapper) {
		this.projectOwnerMapper = projectOwnerMapper;
	}

	@javax.annotation.Resource
	public void setProjectSubordinateMapper(ProjectSubordinateMapper projectSubordinateMapper) {
		this.projectSubordinateMapper = projectSubordinateMapper;
	}

	@javax.annotation.Resource
	public void setProjectViewMapper(ProjectViewMapper projectViewMapper) {
		this.projectViewMapper = projectViewMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@javax.annotation.Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	/**
	 * 保存一条项目信息
	 * 
	 * @return
	 */
	@Transactional
	public void update(Project project) {
		long parentId = project.getParentId();
		if (parentId > 0 && parentId != project.getId()) {
			Project parent = this.getProject(parentId);
			if (parent != null) {
				project.setLevel(parent.getLevel() + 1);
				if (parent.getTreeId() != null) {
					project.setTreeId(parent.getTreeId() + project.getId() + "|");
				}
			}
		} else {
			project.setLevel(0);
			project.setTreeId(project.getId() + "|");
		}
		project.setUpdateTime(new Date());
		projectMapper.updateProject(project);
	}

	/**
	 * 更新用户项目视图
	 * 
	 * @param userId
	 */
	@Transactional
	public void updateProjectView(String userId) {
		projectViewMapper.deleteProjectViewByUserId(userId);// 删除项目视图

		TableModel table = new TableModel();
		table.setTableName("PROJECT_ACCESS");
		table.addStringColumn("ACTORID_", userId);
		table.addStringColumn("DYNAMIC_", "Y");
		tableDataService.deleteTableData(table);// 删除动态生成的权限

		List<Project> projects = this.getProjects(userId);
		if (projects != null && !projects.isEmpty()) {
			SysUser user = sysUserService.findByAccount(userId);
			List<SysDepartment> depts = sysDepartmentService.getSysDepartmentList();
			List<ProjectAccess> x_accesses = projectAccessMapper.getProjectAccessesByActorId(userId);
			List<String> accesses = new ArrayList<String>();
			Map<Long, String> deptMap = new HashMap<Long, String>();
			if (depts != null && !depts.isEmpty()) {
				for (SysDepartment dept : depts) {
					deptMap.put(dept.getId(), dept.getName());
				}
			}

			if (x_accesses != null && !x_accesses.isEmpty()) {
				for (ProjectAccess access : x_accesses) {
					if (access.getDynamic() == null) {// 原始授权,并非动态权限
						accesses.add(access.getActorId().toLowerCase() + "_" + access.getProjectId());
					}
				}
			}

			for (Project project : projects) {
				ProjectView model = new ProjectView();

				model.setUid(userId + "/" + project.getId());
				model.setId(project.getId());
				model.setParentId(project.getParentId());
				model.setActive(project.getActive());
				model.setArea(project.getArea());
				model.setName(project.getName());
				model.setCode(project.getCode());
				model.setCategory(project.getCategory());
				model.setDiscriminator(project.getDiscriminator());
				model.setIcon(project.getIcon());
				model.setIconCls(project.getIconCls());
				model.setLevel(project.getLevel());
				model.setSort(project.getSort());
				model.setTitle(project.getTitle());
				model.setTreeId(project.getTreeId());
				model.setType(project.getType());
				model.setUserId(userId);

				if (StringUtils.isNotEmpty(user.getName())) {
					model.setUserName(user.getName());
				} else {
					model.setUserName(userId);
				}
				if (deptMap.get(user.getDeptId()) != null) {
					model.setOrgName(deptMap.get(user.getDeptId()));
				}
				projectViewMapper.insertProjectView(model);

				if (!accesses.contains(userId.toLowerCase() + "_" + project.getId())) {
					ProjectAccess access = new ProjectAccess();
					access.setActorId(userId);
					access.setProjectId(project.getId());
					access.setDynamic("Y");
					access.setId(idGenerator.nextId("PROJECT_ACCESS"));
					projectAccessMapper.insertProjectAccess(access);
				}
			}
		}
	}

	/**
	 * 更新节点的treeId
	 * 
	 * @param treeIdMap
	 */
	@Transactional
	public void updateTreeIds(Map<Long, String> treeIdMap) {
		TableModel tableModel = new TableModel();
		tableModel.setTableName("PROJECT_QUERY");
		ColumnModel idColumn = new ColumnModel();
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableModel.setIdColumn(idColumn);

		ColumnModel treeColumn = new ColumnModel();
		treeColumn.setColumnName("TREEID_");
		treeColumn.setJavaType("String");
		tableModel.addColumn(treeColumn);

		Iterator<Long> iterator = treeIdMap.keySet().iterator();
		while (iterator.hasNext()) {
			Long id = iterator.next();
			String value = treeIdMap.get(id);
			if (value != null) {
				idColumn.setValue(id);
				treeColumn.setValue(value);
				tableDataService.updateTableData(tableModel);
			}
		}
	}

}
