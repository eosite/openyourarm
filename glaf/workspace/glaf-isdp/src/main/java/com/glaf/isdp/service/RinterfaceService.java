package com.glaf.isdp.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

 
@Transactional(readOnly = true)
public interface RinterfaceService {
	 
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
	 List<Rinterface> list(RinterfaceQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getRinterfaceCountByQueryCriteria(RinterfaceQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<Rinterface> getRinterfacesByQueryCriteria(int start, int pageSize,
			RinterfaceQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 Rinterface getRinterface(String listId);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(Rinterface rinterface);

}
