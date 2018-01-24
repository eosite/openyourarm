package com.glaf.datamgr.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component
public interface SqlParameterMapper {

	void deleteSqlParameterByDatasetId(String datasetId);

	void deleteSqlParameterById(Long id);

	void deleteSqlParameterBySqlDefId(Long sqlDefId);

	SqlParameter getSqlParameterById(Long id);

	int getSqlParameterCount(SqlParameterQuery query);

	List<SqlParameter> getSqlParameters(SqlParameterQuery query);

	List<SqlParameter> getSqlParametersByDatasetId(String datasetId);

	List<SqlParameter> getSqlParametersBySqlDefId(Long sqlDefId);

	void insertSqlParameter(SqlParameter model);

	void updateSqlParameter(SqlParameter model);

}
