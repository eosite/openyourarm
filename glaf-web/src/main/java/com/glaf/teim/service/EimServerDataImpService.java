package com.glaf.teim.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

 
@Transactional(readOnly = true)
public interface EimServerDataImpService {
	 
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
	 List<EimServerDataImp> list(EimServerDataImpQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEimServerDataImpCountByQueryCriteria(EimServerDataImpQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EimServerDataImp> getEimServerDataImpsByQueryCriteria(int start, int pageSize,
			EimServerDataImpQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EimServerDataImp getEimServerDataImp(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(EimServerDataImp eimServerDataImp);
	/**
	 * 获取外部服务导入数据实例列表
	 * @return
	 */
	List<Map> getEimServerDataImpData();

}
