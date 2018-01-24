package com.glaf.convert.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.query.ConvertElemTmplRefQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.convert.mapper.ConvertElemTmplRefMapper")
public interface ConvertElemTmplRefMapper {

	void deleteConvertElemTmplRefs(ConvertElemTmplRefQuery query);

	void deleteConvertElemTmplRefById(Long id);

	ConvertElemTmplRef getConvertElemTmplRefById(Long id);

	int getConvertElemTmplRefCount(ConvertElemTmplRefQuery query);

	List<ConvertElemTmplRef> getConvertElemTmplRefs(ConvertElemTmplRefQuery query);

	void insertConvertElemTmplRef(ConvertElemTmplRef model);

	void updateConvertElemTmplRef(ConvertElemTmplRef model);
	
	/**
	 * 根据页面id返回REF_TYPE_类型不是criterion和criterionParam的引用
	 * @param cvtId
	 * @return
	 */
	List<ConvertElemTmplRef> getConvertElemTmplRefsByCvtId(@Param("cvtId")Long cvtId ,@Param("isCri")Boolean isCri);
}
