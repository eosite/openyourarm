package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.CvtRuntime;
import com.glaf.convert.query.CvtRuntimeQuery;

 
@Transactional(readOnly = true)
public interface CvtRuntimeService {
	 
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
	 void deleteByIds(List<String> taskCodes);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<CvtRuntime> list(CvtRuntimeQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getCvtRuntimeCountByQueryCriteria(CvtRuntimeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<CvtRuntime> getCvtRuntimesByQueryCriteria(int start, int pageSize,
			CvtRuntimeQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 CvtRuntime getCvtRuntime(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(CvtRuntime cvtRuntime);

}
