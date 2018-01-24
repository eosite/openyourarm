package com.glaf.convert.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.convert.domain.ConvertElemPropTmpl;
import com.glaf.convert.query.ConvertElemPropTmplQuery;

 
@Transactional(readOnly = true)
public interface ConvertElemPropTmplService {
	 
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
	 void deleteByIds(List<Long> elemPropIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ConvertElemPropTmpl> list(ConvertElemPropTmplQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getConvertElemPropTmplCountByQueryCriteria(ConvertElemPropTmplQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ConvertElemPropTmpl> getConvertElemPropTmplsByQueryCriteria(int start, int pageSize,
			ConvertElemPropTmplQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ConvertElemPropTmpl getConvertElemPropTmpl(Long id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ConvertElemPropTmpl convertElemPropTmpl);
	
	/**
	 * 批量保存
	 * @param propMap
	 * @param cvtId
	 * @param rowIndex
	 * @param colIndex
	 */
	void batchSave(Map<String,String> propMap,Long cvtId,int rowIndex,int colIndex);

}
