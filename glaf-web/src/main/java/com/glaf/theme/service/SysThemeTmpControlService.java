package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpControlService {
	 
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
	 void deleteByIds(List<String> controlIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SysThemeTmpControl> list(SysThemeTmpControlQuery query);

     /**
	 * 根据查询参数获取记录列表
	 * 包括CSS
	 * @return
	 */
	 List<SysThemeTmpControl> listAndCssText(SysThemeTmpControlQuery query) ;
	 
         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpControlCountByQueryCriteria(SysThemeTmpControlQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmpControl> getSysThemeTmpControlsByQueryCriteria(int start, int pageSize,
			SysThemeTmpControlQuery query) ;
	 
	 /**
	 * 根据查询参数获取记录列表
	 * 包括CSS
	 * @return
	 */
	 List<SysThemeTmpControl> getSysThemeTmpControlsAndCssText(int start, int pageSize,
				SysThemeTmpControlQuery query) ;
	 
	 
         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmpControl getSysThemeTmpControl(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmpControl sysThemeTmpControl);

	SysThemeTmpControl getThumbnailById(String id);
}
