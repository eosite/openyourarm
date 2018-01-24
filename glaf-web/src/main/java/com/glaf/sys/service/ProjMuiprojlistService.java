package com.glaf.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;
 
import com.glaf.sys.domain.*;
import com.glaf.sys.query.*;

 
@Transactional(readOnly = true)
public interface ProjMuiprojlistService {
	 
         /**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteById(Integer id);

        /**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	 void deleteByIds(List<Integer> indexIds);

          /**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	 List<ProjMuiprojlist> list(ProjMuiprojlistQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getProjMuiprojlistCountByQueryCriteria(ProjMuiprojlistQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<ProjMuiprojlist> getProjMuiprojlistsByQueryCriteria(int start, int pageSize,
			ProjMuiprojlistQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 ProjMuiprojlist getProjMuiprojlist(Integer id);
	 
	 ProjMuiprojlist getLocalProjMuiprojlist();

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(ProjMuiprojlist projMuiprojlist);

}
