package com.glaf.convert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.convert.domain.ConvertElemTmplFormlExt;
import com.glaf.convert.query.ConvertElemTmplFormlQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemTmplFormlMapper")
public interface ConvertElemTmplFormlMapper {

	void deleteConvertElemTmplFormls(ConvertElemTmplFormlQuery query);

	void deleteConvertElemTmplFormlById(Long id);

	ConvertElemTmplForml getConvertElemTmplFormlById(Long id);

	int getConvertElemTmplFormlCount(ConvertElemTmplFormlQuery query);

	List<ConvertElemTmplForml> getConvertElemTmplFormls(ConvertElemTmplFormlQuery query);

	void insertConvertElemTmplForml(ConvertElemTmplForml model);

	void updateConvertElemTmplForml(ConvertElemTmplForml model);
	
	List<ConvertElemTmplForml> getConvertElemTmplFormlsByCvtId(@Param("cvtId")Long cvtId);
	
	List<ConvertElemTmplFormlExt> getAllConvertElemFormlExts();

}
