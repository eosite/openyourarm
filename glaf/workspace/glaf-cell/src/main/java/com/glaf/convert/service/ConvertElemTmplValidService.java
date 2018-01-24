package com.glaf.convert.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemTmplValid;
import com.glaf.convert.query.ConvertElemTmplValidQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemTmplValidService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Long id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Long> validRuleIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemTmplValid> list(ConvertElemTmplValidQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemTmplValidCountByQueryCriteria(ConvertElemTmplValidQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemTmplValid> getConvertElemTmplValidsByQueryCriteria(int start, int pageSize,
			ConvertElemTmplValidQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemTmplValid getConvertElemTmplValid(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemTmplValid convertElemTmplValid);
	/**
	 * 创建验证规则
	 * @param pConvertElemTmplValid
	 * @param cvtElemId
	 * @param type
	 * @param params
	 */
	@Transactional
	ConvertElemTmplValid createVaildRule(ConvertElemTmplValid pConvertElemTmplValid,Long cvtElemId, String type,Map<String,String> params);
}
