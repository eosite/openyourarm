package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpLayoutAreaService {
	 
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
	 void deleteByIds(List<String> areaIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SysThemeTmpLayoutArea> list(SysThemeTmpLayoutAreaQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpLayoutAreaCountByQueryCriteria(SysThemeTmpLayoutAreaQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmpLayoutArea> getSysThemeTmpLayoutAreasByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutAreaQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmpLayoutArea getSysThemeTmpLayoutArea(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmpLayoutArea sysThemeTmpLayoutArea);

}
