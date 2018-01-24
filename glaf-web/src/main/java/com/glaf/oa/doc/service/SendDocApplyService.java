package com.glaf.oa.doc.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.oa.doc.domain.*;
import com.glaf.oa.doc.query.*;

 
@Transactional(readOnly = true)
public interface SendDocApplyService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Integer id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Integer> ids);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SendDocApply> list(SendDocApplyQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSendDocApplyCountByQueryCriteria(SendDocApplyQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SendDocApply> getSendDocApplysByQueryCriteria(int start, int pageSize,
			SendDocApplyQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SendDocApply getSendDocApply(Integer id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SendDocApply sendDocApply);

}
