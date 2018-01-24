package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertPageParam;
import com.glaf.convert.query.ConvertPageParamQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertPageParamMapper")
public interface ConvertPageParamMapper {

	void deleteConvertPageParams(ConvertPageParamQuery query);

	void deleteConvertPageParamById(Long id);

	ConvertPageParam getConvertPageParamById(Long id);

	int getConvertPageParamCount(ConvertPageParamQuery query);

	List<ConvertPageParam> getConvertPageParams(ConvertPageParamQuery query);

	void insertConvertPageParam(ConvertPageParam model);

	void updateConvertPageParam(ConvertPageParam model);

}
