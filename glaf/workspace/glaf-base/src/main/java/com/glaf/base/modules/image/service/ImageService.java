package com.glaf.base.modules.image.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.image.domain.*;
import com.glaf.base.modules.image.query.*;

@Transactional(readOnly = true)
public interface ImageService {

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
	void deleteByIds(List<Long> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<Image> list(ImageQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getImageCountByQueryCriteria(ImageQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<Image> getImagesByQueryCriteria(int start, int pageSize,
			ImageQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	Image getImage(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(Image image);

}
