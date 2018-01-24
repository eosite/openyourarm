package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormEventComplex;
import com.glaf.form.core.query.FormEventComplexQuery;

 
@Transactional(readOnly = true)
public interface FormEventComplexService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(String id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<String> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<FormEventComplex> list(FormEventComplexQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormEventComplexCountByQueryCriteria(FormEventComplexQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormEventComplex> getFormEventComplexsByQueryCriteria(int start, int pageSize,
			FormEventComplexQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormEventComplex getFormEventComplex(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormEventComplex formEventComplex);

	List<FormEventComplex> queryComplexByPageId(FormEventComplexQuery query);

}
