package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpService {
	 
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
	 void deleteByIds(List<String> themeTmpIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SysThemeTmp> list(SysThemeTmpQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpCountByQueryCriteria(SysThemeTmpQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmp> getSysThemeTmpsByQueryCriteria(int start, int pageSize,
			SysThemeTmpQuery query) ;

     /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmp getSysThemeTmp(String id);

	 /**
	 * 根据主键获取缩略图信息
	 * 
	 * @return
	 */
	 SysThemeTmp getThumbnailById(String id);
        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmp sysThemeTmp);

}
