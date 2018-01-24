package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.LoginModule;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface LoginModuleService {

	/**
	 * 用自己的私钥解密数据
	 * 
	 * @param id
	 * @param data
	 * @return
	 */
	byte[] decryptText(String id, byte[] data);

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
	 * 用对方提供的公钥加密数据
	 * 
	 * @param id
	 * @param data
	 * @return
	 */
	byte[] encryptText(String id, byte[] data);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	LoginModule getLoginModule(String id);

	LoginModule getLoginModuleByAppId(String appId);

	/**
	 * 根据sysCode获取一条记录
	 * 
	 * @return
	 */
	LoginModule getLoginModuleBySysCode(String sysCode);
	
	/**
	 * 根据token获取一条记录
	 * 
	 * @return
	 */
	LoginModule getLoginModuleByToken(String token);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getLoginModuleCountByQueryCriteria(LoginModuleQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<LoginModule> getLoginModulesByQueryCriteria(int start, int pageSize, LoginModuleQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<LoginModule> list(LoginModuleQuery query);

	/**
	 * 重置登录密锁
	 * 
	 * @param id
	 */
	@Transactional
	void resetLoginAppSecret(String id);

	/**
	 * 重置登录token
	 * 
	 * @param id
	 */
	@Transactional
	void resetLoginAppToken(String id);

	/**
	 * 重置公钥及私钥
	 * 
	 * @param id
	 */
	@Transactional
	void resetLoginRSAKey(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(LoginModule loginModule);

}
