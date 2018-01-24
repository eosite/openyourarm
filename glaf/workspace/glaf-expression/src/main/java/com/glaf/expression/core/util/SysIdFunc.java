package com.glaf.expression.core.util;

import com.glaf.core.context.ContextFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.impl.MyBatisEntityService;
import com.glaf.core.util.UUID32;

public class SysIdFunc {

//	protected static IdGenerator idGenerator;

	static {
		// idGenerator = ContextFactory.getBean("idGenerator");
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID32.getUUID();
	}

	/**
	 * 获取自增长id
	 * 
	 * @return
	 */
	public static Integer getOid(Object param) {
		return null;
	}

	/**
	 * 获取自增长id
	 * 
	 * @return
	 */
	public static Long getSeq(Object param) {
		if (param != null) {
			
			EntityService entityService = ContextFactory.getBean("entityService");
			
			return entityService.nextId(param.toString());
		}
		return 0L;
	}

	/**
	 * 根据表名，字段名 获取最大值
	 * 
	 * @return
	 */
	public static Long getMaxVal(String tn, String key) {
		
	//	IdGenerator idGenerator = ContextFactory.getBean("entityService");
		EntityService entityService = ContextFactory.getBean("entityService");
		
		Long id = entityService.nextId(tn, key);
		return (id == null ? 0L : id) /*+ 1L*/;
	}

	/**
	 * 获取用户自增id
	 * 
	 * 20160128/admin-0000005
	 * 
	 * @return
	 */
	public static String getUserId(Object param) {
		//return idGenerator.getNextId(tablename, idColumn, createBy);
		return null;
	}

}
