package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;
 


import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface FormParamsRelationService {
	 
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
	 List<FormParamsRelation> list(FormParamsRelationQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormParamsRelationCountByQueryCriteria(FormParamsRelationQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormParamsRelation> getFormParamsRelationsByQueryCriteria(int start, int pageSize,
			FormParamsRelationQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormParamsRelation getFormParamsRelation(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormParamsRelation formParamsRelation);

	@Transactional
		void saveByParam(FormParamsRelation fromParamsRelation, String datasetId, String outPage, String outid, String param, String ruleId);

	@Transactional
	void delete(FormParamsRelationQuery delete2Query);

	List<Map<String,Object>> queryParamRelation(String pageId, String widgetRuleId, String paramName, String databaseId);

}
