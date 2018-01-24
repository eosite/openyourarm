package com.glaf.base.modules.sys.service.impl;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.AuthorityUserService;
import com.glaf.base.modules.sys.service.DeptRoleService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;

@Service("authorityUserService")
@Transactional(readOnly = true)
public class AuthorityUserServiceImpl implements AuthorityUserService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected AuthorityUserMapper authorityUserMapper;

	protected DeptRoleService deptRoleService;

	protected SysTreeService sysTreeService;

	protected SysUserService sysUserService;

	protected SysUserRoleService sysUserRoleService;

	protected SysDepartmentService sysDepartmentService;

	public AuthorityUserServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<AuthorityUser> list) {
		for (AuthorityUser authorityUser : list) {
			if (authorityUser.getId() == 0) {
				authorityUser.setId(idGenerator.nextId("SYS_AUTHORITY_USER"));
			}
		}

		int batch_size = 100;
		List<AuthorityUser> rows = new ArrayList<AuthorityUser>(batch_size);

		for (AuthorityUser bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					authorityUserMapper.bulkInsertAuthorityUser_oracle(list);
				} else {
					authorityUserMapper.bulkInsertAuthorityUser(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				authorityUserMapper.bulkInsertAuthorityUser_oracle(list);
			} else {
				authorityUserMapper.bulkInsertAuthorityUser(list);
			}
			rows.clear();
		}
	}

	public int count(AuthorityUserQuery query) {
		return authorityUserMapper.getAuthorityUserCount(query);
	}

	@Transactional
	public void deleteByActorId(String actorId) {
		authorityUserMapper.deleteAuthorityUserByActorId(actorId);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			authorityUserMapper.deleteAuthorityUserById(id);
		}
	}

	@Transactional
	public void deleteByRoleId(long roleId) {
		authorityUserMapper.deleteAuthorityUserByRoleId(roleId);
	}

	public List<String> getActorIdsByRoleId(long roleId) {
		AuthorityUserQuery query = new AuthorityUserQuery();
		query.roleId(roleId);
		List<AuthorityUser> list = authorityUserMapper.getAuthorityUsers(query);
		List<String> actorIds = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (AuthorityUser bean : list) {
				actorIds.add(bean.getActorId());
			}
		}
		return actorIds;
	}

	public AuthorityUser getAuthorityUser(Long id) {
		if (id == null) {
			return null;
		}
		AuthorityUser authorityUser = authorityUserMapper.getAuthorityUserById(id);
		return authorityUser;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getAuthorityUserCountByQueryCriteria(AuthorityUserQuery query) {
		return authorityUserMapper.getAuthorityUserCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<AuthorityUser> getAuthorityUsersByQueryCriteria(int start, int pageSize, AuthorityUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<AuthorityUser> rows = sqlSessionTemplate.selectList("getAuthorityUsers", query, rowBounds);
		return rows;
	}

	public List<Long> getRoleIdsByActorId(String actorId) {
		/**
		 * 查找直接授权的角色
		 */
		AuthorityUserQuery query = new AuthorityUserQuery();
		query.actorId(actorId);
		List<AuthorityUser> list = authorityUserMapper.getAuthorityUsers(query);
		List<Long> roleIds = new ArrayList<Long>();
		if (list != null && !list.isEmpty()) {
			for (AuthorityUser bean : list) {
				roleIds.add(bean.getRoleId());
			}
		}

		/**
		 * 查找用户所属机构授权的角色
		 */
		SysUser user = sysUserService.findById(actorId);
		long deptId = user.getDeptId();

		if (deptId > 0) {
			SysDepartment sysDepartment = sysDepartmentService.findById(deptId);
			if (sysDepartment != null && sysDepartment.getNode() != null) {
				SysTree node = sysDepartment.getNode();
				List<SysTree> treeList = new ArrayList<SysTree>();
				sysTreeService.getSysTreeParent(treeList, node.getId());
				if (!treeList.contains(node)) {
					treeList.add(node);
				}
				List<Long> nodeIds = new ArrayList<Long>();
				for (SysTree tree : treeList) {
					nodeIds.add(tree.getId());
				}
				SysDepartmentQuery query2 = new SysDepartmentQuery();
				query2.nodeIds(nodeIds);
				List<SysDepartment> depts = sysDepartmentService.getSysDepartmentsByQueryCriteria(0, 1000, query2);
				List<Long> deptIds = new ArrayList<Long>();
				deptIds.add(-1L);
				for (SysDepartment dept : depts) {
					// logger.debug(dept.getId() + "->" + dept.getName());
					deptIds.add(dept.getId());
				}
				DeptRoleQuery q = new DeptRoleQuery();
				q.deptIds(deptIds);
				q.menuFlag(0);//下放角色
				List<DeptRole> deptRoles = deptRoleService.list(q);
				if (deptRoles != null && !deptRoles.isEmpty()) {
					for (DeptRole dr : deptRoles) {
						if (dr.getDeptId() == deptId) {
							roleIds.add(dr.getRoleId());
						} else if (StringUtils.equals(dr.getIsPropagationAllowed(), "Y")) {
							roleIds.add(dr.getRoleId());
						}
					}
				}
			}
		}

		return roleIds;
	}

	public List<AuthorityUser> list(AuthorityUserQuery query) {
		List<AuthorityUser> list = authorityUserMapper.getAuthorityUsers(query);
		return list;
	}

	@Transactional
	public void save(AuthorityUser authorityUser) {
		if (authorityUser.getId() == 0) {
			authorityUser.setId(idGenerator.nextId("SYS_AUTHORITY_USER"));
			authorityUser.setCreateTime(new Date());
			authorityUserMapper.insertAuthorityUser(authorityUser);
		}
	}

	@Transactional
	public void saveAll(Long roleId, List<AuthorityUser> authorityUsers) {
		authorityUserMapper.deleteAuthorityUserByRoleId(roleId);
		if (authorityUsers != null && !authorityUsers.isEmpty()) {
			for (AuthorityUser authorityUser : authorityUsers) {
				authorityUser.setId(idGenerator.nextId("SYS_AUTHORITY_USER"));
				authorityUser.setCreateTime(new Date());
				authorityUser.setRoleId(roleId);
				authorityUserMapper.insertAuthorityUser(authorityUser);
			}
		}
	}

	@Transactional
	public void saveAll(String actorId, List<AuthorityUser> authorityUsers) {
		authorityUserMapper.deleteAuthorityUserByActorId(actorId);
		if (authorityUsers != null && !authorityUsers.isEmpty()) {
			for (AuthorityUser authorityUser : authorityUsers) {
				authorityUser.setId(idGenerator.nextId("SYS_AUTHORITY_USER"));
				authorityUser.setCreateTime(new Date());
				authorityUser.setActorId(actorId);
				authorityUserMapper.insertAuthorityUser(authorityUser);
			}
		}
	}

	@javax.annotation.Resource
	public void setAuthorityUserMapper(AuthorityUserMapper authorityUserMapper) {
		this.authorityUserMapper = authorityUserMapper;
	}

	@javax.annotation.Resource
	public void setDeptRoleService(DeptRoleService deptRoleService) {
		this.deptRoleService = deptRoleService;
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	@javax.annotation.Resource
	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

}
