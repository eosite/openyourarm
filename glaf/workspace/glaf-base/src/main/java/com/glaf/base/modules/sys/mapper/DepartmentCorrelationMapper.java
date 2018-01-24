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

@Component("com.glaf.base.modules.sys.mapper.DepartmentCorrelationMapper")
public interface DepartmentCorrelationMapper {

	void bulkInsertDepartmentCorrelation(List<DepartmentCorrelation> list);

	void bulkInsertDepartmentCorrelation_oracle(List<DepartmentCorrelation> list);

	void deleteDepartmentCorrelations(DepartmentCorrelationQuery query);

	void deleteDepartmentCorrelationById(Long id);

	DepartmentCorrelation getDepartmentCorrelationById(Long id);

	int getDepartmentCorrelationCount(DepartmentCorrelationQuery query);

	List<DepartmentCorrelation> getDepartmentCorrelations(DepartmentCorrelationQuery query);

	void insertDepartmentCorrelation(DepartmentCorrelation model);

	void updateDepartmentCorrelation(DepartmentCorrelation model);

}
