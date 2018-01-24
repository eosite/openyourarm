package com.glaf.dep.base.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

 
@Transactional(readOnly = true)
public interface DepBaseWdataSetColumnService {
	 
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
	 List<DepBaseWdataSetColumn> list(DepBaseWdataSetColumnQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getDepBaseWdataSetColumnCountByQueryCriteria(DepBaseWdataSetColumnQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<DepBaseWdataSetColumn> getDepBaseWdataSetColumnsByQueryCriteria(int start, int pageSize,
			DepBaseWdataSetColumnQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 DepBaseWdataSetColumn getDepBaseWdataSetColumn(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(DepBaseWdataSetColumn depBaseWdataSetColumn);

}
