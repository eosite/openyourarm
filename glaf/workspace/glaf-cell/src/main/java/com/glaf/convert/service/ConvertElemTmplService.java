package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.query.ConvertElemTmplQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemTmplService {
	 
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
	 void deleteByIds(List<Long> cvtElemIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemTmpl> list(ConvertElemTmplQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemTmplCountByQueryCriteria(ConvertElemTmplQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemTmpl> getConvertElemTmplsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemTmpl getConvertElemTmpl(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemTmpl convertElemTmpl);

}
