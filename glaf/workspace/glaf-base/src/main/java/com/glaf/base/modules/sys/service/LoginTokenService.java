package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface LoginTokenService {

	@Transactional
	void bulkInsert(List<LoginToken> list);

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

	@Transactional
	void deleteLoginTokenByUserId(String userId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	LoginToken getLoginToken(String id);
	
	LoginToken getLoginTokenBySignature(String signature);
	
	LoginToken getLoginTokenByToken(String token);

	List<LoginToken> getLoginTokenByUserId(String userId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getLoginTokenCountByQueryCriteria(LoginTokenQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<LoginToken> getLoginTokensByQueryCriteria(int start, int pageSize, LoginTokenQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<LoginToken> list(LoginTokenQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(LoginToken loginToken);
	
	@Transactional
	void update(LoginToken loginToken);

}
