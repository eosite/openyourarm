package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemPropTmpl;
import com.glaf.convert.query.ConvertElemPropTmplQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemPropTmplMapper")
public interface ConvertElemPropTmplMapper {

	void deleteConvertElemPropTmpls(ConvertElemPropTmplQuery query);

	void deleteConvertElemPropTmplById(Long id);

	ConvertElemPropTmpl getConvertElemPropTmplById(Long id);

	int getConvertElemPropTmplCount(ConvertElemPropTmplQuery query);

	List<ConvertElemPropTmpl> getConvertElemPropTmpls(ConvertElemPropTmplQuery query);

	void insertConvertElemPropTmpl(ConvertElemPropTmpl model);

	void updateConvertElemPropTmpl(ConvertElemPropTmpl model);

}
