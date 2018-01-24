package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;
 




import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormDatasetRelationService {
	 
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
	 List<FormDatasetRelation> list(FormDatasetRelationQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormDatasetRelationCountByQueryCriteria(FormDatasetRelationQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormDatasetRelation> getFormDatasetRelationsByQueryCriteria(int start, int pageSize,
			FormDatasetRelationQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormDatasetRelation getFormDatasetRelation(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormDatasetRelation formDatasetRelation);

	@Transactional
		void saveOrUpdate(FormDatasetRelation formDatasetRelation);

	@Transactional
	void deleteByColumns(List<String> pids, String ruleId, String attrName);

	@Transactional
	void saveByParam(FormDatasetRelation formDatasetRelation, String idatasetId, String inpage, String ruleId, String inid, String columnName);

	@Transactional
	void delete(FormDatasetRelationQuery deleteQuery);

}
