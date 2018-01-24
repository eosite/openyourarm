package com.glaf.isdp.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 用于执行表的自定义CRUD操作
 * 
 * @author Rocky
 *
 */
@Component("com.glaf.isdp.mapper.TableActionMapper")
public interface TableActionMapper {
	void insertTableByWhereCause(@Param("tableName") String tableName, @Param("fieldString") String fieldString,
			@Param("valueString") String valueString);
	
	void updateTableByWhereCause(@Param("tableName") String tableName, @Param("fieldString") String fieldString,
			@Param("whereCondition") String whereCondition);

	void deleteTableByWhereCause(@Param("tableName") String tableName, @Param("whereCondition") String whereCondition);
}
