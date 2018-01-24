package com.glaf.etl.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

 
@Transactional(readOnly = true)
public interface ETLDataSrcService {
	 
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
	 List<ETLDataSrc> list(ETLDataSrcQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getETLDataSrcCountByQueryCriteria(ETLDataSrcQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ETLDataSrc> getETLDataSrcsByQueryCriteria(int start, int pageSize,
			ETLDataSrcQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ETLDataSrc getETLDataSrc(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ETLDataSrc eTLDataSrc);

}
