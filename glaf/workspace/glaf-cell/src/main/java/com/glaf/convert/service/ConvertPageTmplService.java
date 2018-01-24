package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.convert.query.ConvertPageTmplQuery;

 
@Transactional(readOnly = true)
public interface ConvertPageTmplService {
	 
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
	 void deleteByIds(List<Long> cvtIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertPageTmpl> list(ConvertPageTmplQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertPageTmplCountByQueryCriteria(ConvertPageTmplQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertPageTmpl> getConvertPageTmplsByQueryCriteria(int start, int pageSize,
			ConvertPageTmplQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertPageTmpl getConvertPageTmpl(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertPageTmpl convertPageTmpl);
	
	@Transactional
	 void updatePageTemplate(long cvtId,byte[] pageTemplate);

}
