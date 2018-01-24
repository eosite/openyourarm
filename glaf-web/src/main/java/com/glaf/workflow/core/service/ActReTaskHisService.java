package com.glaf.workflow.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.workflow.core.domain.*;
import com.glaf.workflow.core.query.*;

 
@Transactional(readOnly = true)
public interface ActReTaskHisService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Integer id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Integer> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ActReTaskHis> list(ActReTaskHisQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getActReTaskHisCountByQueryCriteria(ActReTaskHisQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ActReTaskHis> getActReTaskHissByQueryCriteria(int start, int pageSize,
			ActReTaskHisQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ActReTaskHis getActReTaskHis(Integer id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ActReTaskHis actReTaskHis);

}
