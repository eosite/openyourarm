package com.glaf.base.modules.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface DeptRoleMapper {

	void bulkInsertDeptRole(List<DeptRole> list);

	void bulkInsertDeptRole_oracle(List<DeptRole> list);

	void deleteById(Long id);

	void deleteByDeptId(long deptId);

	DeptRole getDeptRoleById(Long id);

	int getDeptRoleCount(DeptRoleQuery query);

	List<DeptRole> getDeptRoles(DeptRoleQuery query);

	void insertDeptRole(DeptRole model);

}
