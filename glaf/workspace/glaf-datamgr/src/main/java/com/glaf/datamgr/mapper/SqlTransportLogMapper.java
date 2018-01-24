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
public interface SqlTransportLogMapper {

	void deleteSqlTransportLogById(Long id);

	void deleteSqlTransportLogs(SqlTransportLogQuery query);

	SqlTransportLog getSqlTransportLogById(Long id);

	int getSqlTransportLogCount(SqlTransportLogQuery query);

	List<SqlTransportLog> getSqlTransportLogs(SqlTransportLogQuery query);

	void insertSqlTransportLog(SqlTransportLog model);

	void updateSqlTransportLog(SqlTransportLog model);

}
