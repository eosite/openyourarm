package com.glaf.form.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormPageEventProcDef;
import com.glaf.form.core.query.FormPageEventProcDefQuery;
 

 
@Transactional(readOnly = true)
public interface FormPageEventProcDefService {
	 
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
	 List<FormPageEventProcDef> list(FormPageEventProcDefQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getFormPageEventProcDefCountByQueryCriteria(FormPageEventProcDefQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<FormPageEventProcDef> getFormPageEventProcDefsByQueryCriteria(int start, int pageSize,
			FormPageEventProcDefQuery query) ;
	 /**
	  * 获取页面事件流程定义
	  * @param pageId
	  * @return
	  */
	 List<FormPageEventProcDef> getFormPageEventProcDefsByPageId(String pageId);
	 
	 Map<String,FormPageEventProcDef> getFormPageEventProcDefMapByPageId(String pageId);
         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 FormPageEventProcDef getFormPageEventProcDef(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(FormPageEventProcDef formPageEventProcDef);
	/**
	 * 更新流程定义和部署ID
	 */
	@Transactional
	void updateFormPageEventProcDefById(String PROCDEF_KEY_, String PROCDEF_ID_, String PROCDEPLOY_ID_,String EVENTPRO_ID);
	/**
	 * 更新流程定义为未发布状态
	 * @param eventProcId
	 */
	@Transactional
	void updateDeployStatus(String eventProcId);

}
