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

@Component("com.glaf.datamgr.mapper.DynamicSqlTreeMapper")
public interface DynamicSqlTreeMapper {

	void bulkInsertDynamicSqlTree(List<DynamicSqlTree> list);

	void bulkInsertDynamicSqlTree_oracle(List<DynamicSqlTree> list);

	void deleteDynamicSqlTreeById(long id);

	void deleteDynamicSqlTrees(DynamicSqlTreeQuery query);

	DynamicSqlTree getDynamicSqlTreeById(long id);

	int getDynamicSqlTreeCount(DynamicSqlTreeQuery query);

	List<DynamicSqlTree> getDynamicSqlTrees(DynamicSqlTreeQuery query);

	void insertDynamicSqlTree(DynamicSqlTree model);

	void updateDynamicSqlTree(DynamicSqlTree model);

}
