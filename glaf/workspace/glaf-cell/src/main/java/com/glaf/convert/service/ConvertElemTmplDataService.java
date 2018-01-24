package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmplData;
import com.glaf.convert.query.ConvertElemTmplDataQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemTmplDataService {
	 
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
	 void deleteByIds(List<Long> dataRuleIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemTmplData> list(ConvertElemTmplDataQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemTmplDataCountByQueryCriteria(ConvertElemTmplDataQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemTmplData> getConvertElemTmplDatasByQueryCriteria(int start, int pageSize,
			ConvertElemTmplDataQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemTmplData getConvertElemTmplData(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemTmplData convertElemTmplData);

}
