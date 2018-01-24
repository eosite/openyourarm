package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;
 


import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormDatasetService {
	 
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
	 List<FormDataset> list(FormDatasetQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormDatasetCountByQueryCriteria(FormDatasetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormDataset> getFormDatasetsByQueryCriteria(int start, int pageSize,
			FormDatasetQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormDataset getFormDataset(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormDataset formDataset);

	@Transactional
		void saveOrUpdate(FormDataset formDataset);

	@Transactional
	void deleteByColumns(List<String> params, String ruleId);

}
