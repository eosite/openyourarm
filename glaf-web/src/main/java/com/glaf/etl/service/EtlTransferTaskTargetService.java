package com.glaf.etl.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

 
@Transactional(readOnly = true)
public interface EtlTransferTaskTargetService {
	 
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
	 void deleteByIds(List<String> id_s);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<EtlTransferTaskTarget> list(EtlTransferTaskTargetQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEtlTransferTaskTargetCountByQueryCriteria(EtlTransferTaskTargetQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EtlTransferTaskTarget> getEtlTransferTaskTargetsByQueryCriteria(int start, int pageSize,
			EtlTransferTaskTargetQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EtlTransferTaskTarget getEtlTransferTaskTarget(String id);
	    /**
		 * 根据主键获取一条记录
		 * 
		 * @return
		 */
	 EtlTransferTaskTarget getEtlTransferTaskTargetByTaskId(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(EtlTransferTaskTarget etlTransferTaskTarget);

}
