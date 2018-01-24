package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.query.ConvertElemTmplRefQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemTmplRefService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Long id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Long> refRuleIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemTmplRef> list(ConvertElemTmplRefQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemTmplRefCountByQueryCriteria(ConvertElemTmplRefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemTmplRef> getConvertElemTmplRefsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplRefQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemTmplRef getConvertElemTmplRef(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemTmplRef convertElemTmplRef);

	/**
	 * 根据页面id返回REF_TYPE_类型不是criterion和criterionParam的引用
	 * @param cvtId
	 * @return
	 */
	List<ConvertElemTmplRef> getConvertElemTmplRefsByCvtId(Long cvtId);
}
