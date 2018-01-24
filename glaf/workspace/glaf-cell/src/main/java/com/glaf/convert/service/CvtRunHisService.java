package com.glaf.convert.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.CvtRunHis;
import com.glaf.convert.query.CvtRunHisQuery;

 
@Transactional(readOnly = true)
public interface CvtRunHisService {
	 
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
	 List<CvtRunHis> list(CvtRunHisQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getCvtRunHisCountByQueryCriteria(CvtRunHisQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<CvtRunHis> getCvtRunHissByQueryCriteria(int start, int pageSize,
			CvtRunHisQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 CvtRunHis getCvtRunHis(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(CvtRunHis cvtRunHis);

}
