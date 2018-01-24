package com.glaf.workflow.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

 
@Transactional(readOnly = true)
public interface ActReCategoryService {
	 
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
	 void deleteByIds(List<Long> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ActReCategory> list(ActReCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getActReCategoryCountByQueryCriteria(ActReCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ActReCategory> getActReCategorysByQueryCriteria(int start, int pageSize,
			ActReCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ActReCategory getActReCategory(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ActReCategory actReCategory);
	/**
	 * 修改分类名称
	 * @param categoryId
	 * @param name
	 * @throws Exception
	 */
	@Transactional
	void rename(Long categoryId, String name,String actorId,Date modifyDatatime) throws Exception;
	/**
	 * 移动
	 * @param categoryId
	 * @param pId
	 * @param actorId
	 * @param modifyDatatime
	 * @throws Exception
	 */
	@Transactional
	void move(String moveType,Long categoryId, Long pId,String treeId,String actorId,Date modifyDatatime) throws Exception;
	/**
	 * 工作流模型移动到分类
	 * @param modelId
	 * @param categoryId
	 */
	@Transactional
	void workFlowMoveToCategory(String modelId,String categoryId);

}
