package com.glaf.chinaiss.data.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

 
@Transactional(readOnly = true)
public interface DataModelTreeService {
	 
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
	 List<DataModelTree> list(DataModelTreeQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getDataModelTreeCountByQueryCriteria(DataModelTreeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<DataModelTree> getDataModelTreesByQueryCriteria(int start, int pageSize,
			DataModelTreeQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 DataModelTree getDataModelTree(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(DataModelTree dataModelTree);

}
