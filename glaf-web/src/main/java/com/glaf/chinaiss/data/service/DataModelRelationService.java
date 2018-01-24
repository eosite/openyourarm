package com.glaf.chinaiss.data.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

 
@Transactional(readOnly = true)
public interface DataModelRelationService {
	 
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
	 List<DataModelRelation> list(DataModelRelationQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getDataModelRelationCountByQueryCriteria(DataModelRelationQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<DataModelRelation> getDataModelRelationsByQueryCriteria(int start, int pageSize,
			DataModelRelationQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 DataModelRelation getDataModelRelation(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(DataModelRelation dataModelRelation);

}
