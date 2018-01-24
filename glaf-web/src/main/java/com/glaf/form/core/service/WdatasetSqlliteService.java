package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface WdatasetSqlliteService {
	 
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
	 List<WdatasetSqllite> list(WdatasetSqlliteQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getWdatasetSqlliteCountByQueryCriteria(WdatasetSqlliteQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<WdatasetSqllite> getWdatasetSqllitesByQueryCriteria(int start, int pageSize,
			WdatasetSqlliteQuery query) ;

	 /**
	 * 根据查询参数获取一页的数据,不查询出规则信息
	 * 
	 * @return
	 */
	 List<WdatasetSqllite> getWdatasetSqllitesByQueryCriteria2(int start, int pageSize,
			WdatasetSqlliteQuery query) ;
		 
	 
         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 WdatasetSqllite getWdatasetSqllite(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(WdatasetSqllite wdatasetSqllite);

	/**
	 * 获取sqllite规则的输入参数
	 * 
	 * 数据集对应的输入参数 whereParams
	 * 
	 * @param id
	 * @return
	 */
	JSONObject getSqlliteRuleInParam(Long id);
}
