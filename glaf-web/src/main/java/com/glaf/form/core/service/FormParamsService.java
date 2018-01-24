package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;
 


import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormParamsService {
	 
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
	 List<FormParams> list(FormParamsQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormParamsCountByQueryCriteria(FormParamsQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormParams> getFormParamssByQueryCriteria(int start, int pageSize,
			FormParamsQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormParams getFormParams(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormParams formParams);

	@Transactional
	void saveOrUpdate(FormParams formParams);

	@Transactional
	void deleteByParams(List<String> params, String id);

}
