package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.query.ConvertElemTmplQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemTmplMapper")
public interface ConvertElemTmplMapper {

	void deleteConvertElemTmpls(ConvertElemTmplQuery query);

	void deleteConvertElemTmplById(Long id);

	ConvertElemTmpl getConvertElemTmplById(Long id);

	int getConvertElemTmplCount(ConvertElemTmplQuery query);

	List<ConvertElemTmpl> getConvertElemTmpls(ConvertElemTmplQuery query);

	void insertConvertElemTmpl(ConvertElemTmpl model);

	void updateConvertElemTmpl(ConvertElemTmpl model);

}
