package com.glaf.convert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.convert.query.ConvertPageTmplQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertPageTmplMapper")
public interface ConvertPageTmplMapper {

	void deleteConvertPageTmpls(ConvertPageTmplQuery query);

	void deleteConvertPageTmplById(Long id);

	ConvertPageTmpl getConvertPageTmplById(Long id);

	int getConvertPageTmplCount(ConvertPageTmplQuery query);

	List<ConvertPageTmpl> getConvertPageTmpls(ConvertPageTmplQuery query);

	void insertConvertPageTmpl(ConvertPageTmpl model);

	void updateConvertPageTmpl(ConvertPageTmpl model);
	
	void updatePageTemplate(@Param("cvtId")long cvtId,@Param("cvtDesContent")byte[] pageTemplate);
}
