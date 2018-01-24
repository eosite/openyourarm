package com.glaf.web.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.web.domain.*;
import com.glaf.web.query.*;

 
@Transactional(readOnly = true)
public interface PageResourceService {
	 
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
	 void deleteByIds(List<Long> resIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<PageResource> list(PageResourceQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getPageResourceCountByQueryCriteria(PageResourceQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<PageResource> getPageResourcesByQueryCriteria(int start, int pageSize,
			PageResourceQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 PageResource getPageResource(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(PageResource pageResource);
	/**
	 * 根据资源路径获取资源
	 * @param filePath
	 * @return
	 */
	PageResource getPageResourceByFilePath(String filePath);

}
