package com.glaf.report.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

 
@Transactional(readOnly = true)
public interface ReportCategoryService {
	 
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
	 List<ReportCategory> list(ReportCategoryQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getReportCategoryCountByQueryCriteria(ReportCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ReportCategory> getReportCategorysByQueryCriteria(int start, int pageSize,
			ReportCategoryQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ReportCategory getReportCategory(Integer id);

    /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ReportCategory reportCategory);
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

}
