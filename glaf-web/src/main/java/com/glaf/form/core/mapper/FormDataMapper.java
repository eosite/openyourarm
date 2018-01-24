package com.glaf.form.core.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.form.core.mapper.FormDataMapper")
public interface FormDataMapper {

	List<Map<String,Object>> getTableNameById(String id);
}
