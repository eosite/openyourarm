package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemTmplValid;
import com.glaf.convert.query.ConvertElemTmplValidQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemTmplValidMapper")
public interface ConvertElemTmplValidMapper {

	void deleteConvertElemTmplValids(ConvertElemTmplValidQuery query);

	void deleteConvertElemTmplValidById(Long id);

	ConvertElemTmplValid getConvertElemTmplValidById(Long id);

	int getConvertElemTmplValidCount(ConvertElemTmplValidQuery query);

	List<ConvertElemTmplValid> getConvertElemTmplValids(ConvertElemTmplValidQuery query);

	void insertConvertElemTmplValid(ConvertElemTmplValid model);

	void updateConvertElemTmplValid(ConvertElemTmplValid model);

}
