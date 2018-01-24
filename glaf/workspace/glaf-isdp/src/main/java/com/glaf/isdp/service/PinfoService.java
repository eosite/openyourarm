package com.glaf.isdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.isdp.domain.Pinfo;
import com.glaf.isdp.query.PinfoQuery;

@Transactional(readOnly = true)
public interface PinfoService {

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
	List<Pinfo> list(PinfoQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getPinfoCountByQueryCriteria(PinfoQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Pinfo> getPinfosByQueryCriteria(int start, int pageSize,
			PinfoQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Pinfo getPinfo(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Pinfo pinfo);

	@Transactional
	int update(List<FieldInterface> interfaceList,Map<String,Object> paramsMap);
	
	Pinfo getPinfoListByDomainIndexId(Integer domainIndexId);
}
