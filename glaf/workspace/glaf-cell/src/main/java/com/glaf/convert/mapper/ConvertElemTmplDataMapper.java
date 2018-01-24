package com.glaf.convert.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemTmplData;
import com.glaf.convert.query.ConvertElemTmplDataQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemTmplDataMapper")
public interface ConvertElemTmplDataMapper {

	void deleteConvertElemTmplDatas(ConvertElemTmplDataQuery query);

	void deleteConvertElemTmplDataById(Long id);

	ConvertElemTmplData getConvertElemTmplDataById(Long id);

	int getConvertElemTmplDataCount(ConvertElemTmplDataQuery query);

	List<ConvertElemTmplData> getConvertElemTmplDatas(ConvertElemTmplDataQuery query);

	void insertConvertElemTmplData(ConvertElemTmplData model);

	void updateConvertElemTmplData(ConvertElemTmplData model);

}
