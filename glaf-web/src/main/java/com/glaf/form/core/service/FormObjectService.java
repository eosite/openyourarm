package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormObjectService {
	 
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
	 List<FormObject> list(FormObjectQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormObjectCountByQueryCriteria(FormObjectQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormObject> getFormObjectsByQueryCriteria(int start, int pageSize,
			FormObjectQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormObject getFormObject(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormObject formObject);

}
