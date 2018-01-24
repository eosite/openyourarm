package com.glaf.teim.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.teim.domain.*;
import com.glaf.teim.query.*;

 
@Transactional(readOnly = true)
public interface EimBaseInfoService {
	 
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
	 List<EimBaseInfo> list(EimBaseInfoQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getEimBaseInfoCountByQueryCriteria(EimBaseInfoQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<EimBaseInfo> getEimBaseInfosByQueryCriteria(int start, int pageSize,
			EimBaseInfoQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 EimBaseInfo getEimBaseInfo(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(EimBaseInfo eimBaseInfo);
	/**
	 * 获取所有应用信息 ID、NAME
	 * @return
	 */
	 List<Map> getAllEimBaseInfo();

}
