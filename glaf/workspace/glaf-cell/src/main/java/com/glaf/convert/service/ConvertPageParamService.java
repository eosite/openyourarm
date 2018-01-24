package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertPageParam;
import com.glaf.convert.query.ConvertPageParamQuery;

 
@Transactional(readOnly = true)
public interface ConvertPageParamService {
	 
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
	 void deleteByIds(List<Long> cvtParamIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertPageParam> list(ConvertPageParamQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertPageParamCountByQueryCriteria(ConvertPageParamQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertPageParam> getConvertPageParamsByQueryCriteria(int start, int pageSize,
			ConvertPageParamQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertPageParam getConvertPageParam(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertPageParam convertPageParam);

}
