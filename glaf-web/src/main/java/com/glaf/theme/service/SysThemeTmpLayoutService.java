package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeTmpLayoutService {
	 
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
	 void deleteByIds(List<String> layoutIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<SysThemeTmpLayout> list(SysThemeTmpLayoutQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getSysThemeTmpLayoutCountByQueryCriteria(SysThemeTmpLayoutQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<SysThemeTmpLayout> getSysThemeTmpLayoutsByQueryCriteria(int start, int pageSize,
			SysThemeTmpLayoutQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 SysThemeTmpLayout getSysThemeTmpLayout(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(SysThemeTmpLayout sysThemeTmpLayout);
	
	@Transactional
	 void save(SysThemeTmpLayout sysThemeTmpLayout,JSONArray layoutAreaDatas);
	
	@Transactional
	 void saveArea(SysThemeTmpLayoutArea sysThemeTmpLayoutArea);

	/**
	 * 根据主键获取缩略图信息
	 * 
	 * @return
	 */
	SysThemeTmpLayout getThumbnailById(String id);
}
