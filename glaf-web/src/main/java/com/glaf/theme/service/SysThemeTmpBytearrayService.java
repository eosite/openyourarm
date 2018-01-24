package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpBytearrayService {
	 
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
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByBuss(String bussType, String bussKey, String type);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SysThemeTmpBytearray> list(SysThemeTmpBytearrayQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpBytearrayCountByQueryCriteria(SysThemeTmpBytearrayQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmpBytearray> getSysThemeTmpBytearraysByQueryCriteria(int start, int pageSize,
			SysThemeTmpBytearrayQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmpBytearray getSysThemeTmpBytearray(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmpBytearray sysThemeTmpBytearray);

}
