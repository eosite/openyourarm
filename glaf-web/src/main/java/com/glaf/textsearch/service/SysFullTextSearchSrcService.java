package com.glaf.textsearch.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.textsearch.domain.*;
import com.glaf.textsearch.query.*;

 
@Transactional(readOnly = true)
public interface SysFullTextSearchSrcService {
	 
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
	 List<SysFullTextSearchSrc> list(SysFullTextSearchSrcQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysFullTextSearchSrcCountByQueryCriteria(SysFullTextSearchSrcQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysFullTextSearchSrc> getSysFullTextSearchSrcsByQueryCriteria(int start, int pageSize,
			SysFullTextSearchSrcQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysFullTextSearchSrc getSysFullTextSearchSrc(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysFullTextSearchSrc sysFullTextSearchSrc);

}
