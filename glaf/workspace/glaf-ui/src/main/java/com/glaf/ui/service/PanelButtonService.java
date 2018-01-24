package com.glaf.ui.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.ui.model.PanelButton;
import com.glaf.ui.query.PanelButtonQuery;

 
@Transactional(readOnly = true)
public interface PanelButtonService {
	 
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
	 List<PanelButton> list(PanelButtonQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getPanelButtonCountByQueryCriteria(PanelButtonQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<PanelButton> getPanelButtonsByQueryCriteria(int start, int pageSize,
			PanelButtonQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 PanelButton getPanelButton(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(PanelButton panelButton);

	List<PanelButton> getPanelButtonByParentId(String pid);

}
