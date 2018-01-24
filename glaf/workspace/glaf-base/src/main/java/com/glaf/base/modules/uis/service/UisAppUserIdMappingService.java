package com.glaf.base.modules.uis.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.base.modules.uis.domain.*;
import com.glaf.base.modules.uis.query.*;

 
@Transactional(readOnly = true)
public interface UisAppUserIdMappingService {
	 
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
	 List<UisAppUserIdMapping> list(UisAppUserIdMappingQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getUisAppUserIdMappingCountByQueryCriteria(UisAppUserIdMappingQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<UisAppUserIdMapping> getUisAppUserIdMappingsByQueryCriteria(int start, int pageSize,
			UisAppUserIdMappingQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 UisAppUserIdMapping getUisAppUserIdMapping(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(UisAppUserIdMapping uisAppUserIdMapping);

}
