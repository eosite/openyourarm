package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormEventTemplateService {
	 
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
	 List<FormEventTemplate> list(FormEventTemplateQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormEventTemplateCountByQueryCriteria(FormEventTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormEventTemplate> getFormEventTemplatesByQueryCriteria(int start, int pageSize,
			FormEventTemplateQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormEventTemplate getFormEventTemplate(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormEventTemplate formEventTemplate);

}
