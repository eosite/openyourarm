package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.UserSqliteUpload;
import com.glaf.form.core.query.UserSqliteUploadQuery;
 

 
@Transactional(readOnly = true)
public interface UserSqliteUploadService {
	 
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
	 List<UserSqliteUpload> list(UserSqliteUploadQuery query);

         /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	 int getUserSqliteUploadCountByQueryCriteria(UserSqliteUploadQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	 List<UserSqliteUpload> getUserSqliteUploadsByQueryCriteria(int start, int pageSize,
			UserSqliteUploadQuery query) ;

         /**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	 UserSqliteUpload getUserSqliteUpload(String id);

        /**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	 void save(UserSqliteUpload userSqliteUpload);

}
