package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.convert.domain.ConvertElemTmplFormlExt;
import com.glaf.convert.query.ConvertElemTmplFormlQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemTmplFormlService {
	 
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
	 void deleteByIds(List<Long> formlRuleIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemTmplForml> list(ConvertElemTmplFormlQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemTmplFormlCountByQueryCriteria(ConvertElemTmplFormlQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemTmplForml> getConvertElemTmplFormlsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplFormlQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemTmplForml getConvertElemTmplForml(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemTmplForml convertElemTmplForml);
	
	/**
	 * 根据页面ID 获取当前页面的记录
	 * @param cvtId
	 * @return
	 */
	List<ConvertElemTmplForml> getConvertElemTmplFormlsByCvtId(Long cvtId);
	
	List<ConvertElemTmplFormlExt> getAllConvertElemFormlExts();
}
