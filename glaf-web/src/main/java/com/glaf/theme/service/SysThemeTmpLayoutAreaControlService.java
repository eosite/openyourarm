package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpLayoutAreaControlService {
	 
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
	 List<SysThemeTmpLayoutAreaControl> list(SysThemeTmpLayoutAreaControlQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpLayoutAreaControlCountByQueryCriteria(SysThemeTmpLayoutAreaControlQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmpLayoutAreaControl> getSysThemeTmpLayoutAreaControlsByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutAreaControlQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmpLayoutAreaControl getSysThemeTmpLayoutAreaControl(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmpLayoutAreaControl sysThemeTmpLayoutAreaControl);

	
	 /**
	 * 根据主键获取缩略图信息
	 * 
	 * @return
	 */
	SysThemeTmpLayoutAreaControl getThumbnailById(String id);
}