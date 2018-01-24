/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.base.modules;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.core.base.ConnectionDefinition;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.db.DbTableChecker;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.entity.hibernate.HibernateBeanFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.EntityService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.tree.helper.TreeUpdateBean;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.Tools;

import com.glaf.base.config.BaseConfiguration;
import com.glaf.base.modules.sys.SysConstants;
import com.glaf.base.modules.sys.business.UpdateTreeBean;
import com.glaf.base.modules.sys.model.BaseDataInfo;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysDepartment;
import com.glaf.base.modules.sys.model.SysFunction;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.base.modules.sys.service.SysDepartmentService;
import com.glaf.base.modules.sys.service.SysFunctionService;
import com.glaf.base.modules.sys.service.SysRoleService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.base.modules.sys.service.SysUserRoleService;
import com.glaf.base.modules.sys.service.SysUserService;

public class BaseDataManager {
	private static class BaseDataManagerHolder {
		public static BaseDataManager instance = new BaseDataManager();
	}

	public class RefreshTask implements Runnable {

		public void run() {
			try {
				outputLog = false;
				refreshBaseData();
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

	}

	protected static ConcurrentMap<String, List<BaseDataInfo>> baseDataMap = new ConcurrentHashMap<String, List<BaseDataInfo>>();

	protected static ConcurrentMap<String, List<Object>> dataListMap = new ConcurrentHashMap<String, List<Object>>();

	protected static ConcurrentMap<String, String> jsonDataMap = new ConcurrentHashMap<String, String>();

	protected static final Configuration conf = BaseConfiguration.create();

	protected static final Log logger = LogFactory.getLog(BaseDataManager.class);

	protected static final AtomicBoolean loading = new AtomicBoolean(false);

	protected static final String CUSTOM_CONFIG = "/conf/props/base_data.properties";

	protected static final String CUSTOM_HANDLER = "/conf/props/data_handler.properties";

	protected static boolean outputLog = true;

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public static BaseDataManager getInstance() {
		return BaseDataManagerHolder.instance;
	}

	protected ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

	protected volatile DictoryService dictoryService;

	protected volatile SysApplicationService sysApplicationService;

	protected volatile SysDepartmentService sysDepartmentService;

	protected volatile SysFunctionService sysFunctionService;

	protected volatile SysRoleService sysRoleService;

	protected volatile SysTreeService sysTreeService;

	protected volatile SysUserRoleService sysUserRoleService;

	protected volatile SysUserService sysUserService;

	protected volatile EntityService entityService;

	private BaseDataManager() {

	}

	/**
	 * 获取某种类型的基础数据
	 * 
	 * @param key
	 * @return
	 */
	public List<BaseDataInfo> getBaseData(String key) {
		String complexKey = Environment.getCurrentSystemName() + "_" + key;
		List<BaseDataInfo> result = baseDataMap.get(complexKey);
		if (result != null) {
			return result;
		}
		result = baseDataMap.get(key);
		if (result != null) {
			return result;
		}
		return null;
	}

	/**
	 * 根据数据对象code和类型返回对象
	 * 
	 * @param code
	 *            sys_dictory的编码CODE
	 * @param categoryKey
	 *            sys_tree的编码CODE
	 * @return
	 */
	public BaseDataInfo getBaseData(String code, String categoryKey) {
		BaseDataInfo ret = null;
		Iterator<BaseDataInfo> iter = getList(categoryKey);
		if (iter != null) {
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (StringUtils.equals(temp.getName(), code)) {
					ret = temp;
					break;
				}
				if (StringUtils.equals(temp.getCode(), code)) {
					ret = temp;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 根据数据对象no和类型返回对象
	 * 
	 * @param value
	 * @param key
	 * @return
	 */
	public BaseDataInfo getBaseDataWithNo(String no, String key) {
		BaseDataInfo ret = null;
		Iterator<BaseDataInfo> iter = getList(key);
		if (iter != null) {
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (StringUtils.equals(temp.getNo(), no)) {
					ret = temp;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 根据bean编号获取bean
	 * 
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId) {
		return ContextFactory.getBean(beanId);
	}

	/**
	 * 根据类型返回对象列表
	 * 
	 * @param
	 * 
	 * @return
	 */
	public List<BaseDataInfo> getDataList(String key) {
		List<BaseDataInfo> list = getBaseData(key);
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据分类码获取字典列表项
	 * 
	 * @param category
	 * @return
	 */
	public List<Dictory> getDictoryList(String category) {
		List<Dictory> list = getDictoryService().getDictoryList(category);
		return list;
	}

	public DictoryService getDictoryService() {
		if (dictoryService == null) {
			dictoryService = ContextFactory.getBean("dictoryService");
		}
		return dictoryService;
	}

	public EntityService getEntityService() {
		if (entityService == null) {
			entityService = ContextFactory.getBean("entityService");
		}
		return entityService;
	}

	public JSONArray getJSONArray(String key) {
		String complexKey = Environment.getCurrentSystemName() + "_" + key;
		String result = jsonDataMap.get(complexKey);
		if (result != null) {
			return JSON.parseArray(result);
		}
		result = jsonDataMap.get(key);
		if (result != null) {
			return JSON.parseArray(result);
		}
		return null;
	}

	/**
	 * 根据类型返回对象列表
	 * 
	 * @param key
	 * @return
	 */
	public Iterator<BaseDataInfo> getList(String key) {
		List<BaseDataInfo> list = getBaseData(key);
		if (list != null) {
			return list.iterator();
		} else {
			return null;
		}
	}

	/**
	 * 获取某种类型的基础数据
	 * 
	 * @param key
	 * @return
	 */
	public List<Object> getListData(String key) {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		String complexKey = Environment.getCurrentSystemName() + "_" + key;
		List<Object> result = dataListMap.get(complexKey);
		if (result != null) {
			return result;
		}
		result = dataListMap.get(key);
		if (result != null) {
			return result;
		}
		return null;
	}

	/**
	 * 获取上一级科目名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getParentName(long valueId, String key) {
		String str = "";
		BaseDataInfo bdi = getValue(valueId, key);
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				str = bdi2.getName();
			}
		}
		return str;
	}

	/**
	 * 根据数据对象id和类型返回根对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getParentStringValue(int valueId, String key) {
		BaseDataInfo ret = getParentValue(valueId, key);
		return ret == null ? "" : ret.getName();
	}

	/**
	 * 获取上一级科目
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getParentSubjectValue(long valueId, String key) {
		BaseDataInfo bdi = getValue(valueId, key);
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				bdi = bdi2;
			}
		}
		return bdi;
	}

	/**
	 * 根据数据对象id和类型返回根对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getParentValue(long valueId, String key) {
		BaseDataInfo ret = getValue(valueId, key);
		if (ret != null && ret.getParentId() != 0 && valueId != ret.getParentId()) {
			ret = getParentValue(ret.getParentId(), key);
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Integer valueId, String key) {
		Integer v = (valueId == null ? Integer.valueOf(0) : valueId);
		BaseDataInfo obj = getValue(v.intValue(), key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 根据数据对象id和类型返回对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(long valueId, String key) {
		BaseDataInfo obj = getValue(valueId, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(Long valueId, String key) {
		Long v = (valueId == null ? Long.valueOf(0) : valueId);
		BaseDataInfo obj = getValue(v.intValue(), key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 根据数据对象id和类型返回对象名称
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getStringValue(String code, String key) {
		BaseDataInfo obj = getValue(code, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	/**
	 * 根据编号和类型返回对象名称
	 * 
	 * @param no
	 * @param key
	 * @return
	 */
	public String getStringValueByNo(String no, String key) {
		BaseDataInfo obj = getBaseDataWithNo(no, key);
		if (obj != null) {
			return obj.getName();
		} else {
			return "";
		}
	}

	public SysApplicationService getSysApplicationService() {
		if (sysApplicationService == null) {
			sysApplicationService = ContextFactory.getBean("sysApplicationService");
		}
		return sysApplicationService;
	}

	public SysDepartmentService getSysDepartmentService() {
		if (sysDepartmentService == null) {
			sysDepartmentService = ContextFactory.getBean("sysDepartmentService");
		}
		return sysDepartmentService;
	}

	public SysFunctionService getSysFunctionService() {
		if (sysFunctionService == null) {
			sysFunctionService = ContextFactory.getBean("sysFunctionService");
		}
		return sysFunctionService;
	}

	public SysRoleService getSysRoleService() {
		if (sysRoleService == null) {
			sysRoleService = ContextFactory.getBean("sysRoleService");
		}
		return sysRoleService;
	}

	public SysTreeService getSysTreeService() {
		if (sysTreeService == null) {
			sysTreeService = ContextFactory.getBean("sysTreeService");
		}

		return sysTreeService;
	}

	public SysUserRoleService getSysUserRoleService() {
		if (sysUserRoleService == null) {
			sysUserRoleService = ContextFactory.getBean("sysUserRoleService");
		}
		return sysUserRoleService;
	}

	public SysUserService getSysUserService() {
		if (sysUserService == null) {
			sysUserService = ContextFactory.getBean("sysUserService");
		}
		return sysUserService;
	}

	/**
	 * 根据数据对象id和类型返回对象
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(int valueId, String key) {
		BaseDataInfo ret = null;
		Iterator<BaseDataInfo> iter = getList(key);
		while (iter != null && iter.hasNext()) {
			BaseDataInfo temp = (BaseDataInfo) iter.next();
			if (temp.getId() == valueId) {
				ret = temp;
				break;
			}
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Integer valueId, String key) {
		if (valueId != null) {
			return getValue(valueId.intValue(), key);
		}
		return null;
	}

	/**
	 * 
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public BaseDataInfo getValue(Long valueId, String key) {
		if (valueId != null) {
			return getValue(valueId.intValue(), key);
		}
		return null;
	}

	/**
	 * 根据数据对象code和类型返回对象
	 * 
	 * @param code
	 *            基础数据代码
	 * @param key
	 *            分类代码
	 * @return
	 */
	public BaseDataInfo getValue(String code, String key) {
		BaseDataInfo ret = null;
		Iterator<BaseDataInfo> iter = getList(key);
		if (iter != null) {
			while (iter.hasNext()) {
				BaseDataInfo temp = (BaseDataInfo) iter.next();
				if (StringUtils.equals(temp.getCode(), code)) {
					ret = temp;
					break;
				}
			}
		}
		return ret;
	}

	/**
	 * 根据数据对象id和类型返回对象详细目录名称（包含父信息,中间用省略号）
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentString(int valueId, String key) {
		String rst = getWithParentValue(valueId, key);
		rst = rst.replaceAll("([^\\\\]*\\\\)(.*)(\\\\[^\\\\]*)", "$1...$3");
		return rst;
	}

	public String getWithParentString(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	public String getWithParentString(long valueId, String key) {
		return getWithParentValue(valueId, key);
	}

	public String getWithParentString(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	/**
	 * 获取上一级科目
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentStringValue(int valueId, String key) {
		String str = "";
		BaseDataInfo bdi = getValue(valueId, key);
		str = bdi == null ? "" : bdi.getName();
		if (bdi != null) {
			BaseDataInfo bdi2 = getValue(bdi.getParentId(), key);
			if (bdi2 != null) {
				bdi = bdi2;
				str = bdi2.getName() + "\\" + str;
			}
		}
		return str;
	}

	public String getWithParentStringValue(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";

	}

	public String getWithParentStringValue(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentStringValue(valueId.intValue(), key);
		}
		return "";
	}

	/**
	 * 根据数据对象id和类型返回对象详细目录名称（包含父信息）
	 * 
	 * @param valueId
	 * @param key
	 * @return
	 */
	public String getWithParentValue(int valueId, String key) {
		BaseDataInfo ret = getValue(valueId, key);
		String s = ret == null ? "" : ret.getName();
		if (ret != null && ret.getParentId() != 0 && valueId != ret.getParentId()) {
			s = getWithParentValue(ret.getParentId(), key) + "\\" + s;
		}
		return s;
	}

	public String getWithParentValue(Integer valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	public String getWithParentValue(Long valueId, String key) {
		if (valueId != null) {
			return getWithParentValue(valueId.intValue(), key);
		} else {
			return "";
		}
	}

	/**
	 * 初始化内存中基础数据
	 * 
	 */
	protected void initBaseData() {
		logger.debug("----------------initBaseData---------------------");

		try {
			UpdateTreeBean bean = new UpdateTreeBean();
			bean.setSysTreeService(getSysTreeService());
			bean.updateTreeIds();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			boolean updateSchema = true;
			List<ColumnDefinition> columns = DBUtils.getColumnDefinitions("PROJECT_QUERY");
			if (columns != null && !columns.isEmpty()) {
				for (ColumnDefinition column : columns) {
					if (StringUtils.equalsIgnoreCase(column.getColumnName(), "TREEID_")) {
						updateSchema = false;
						break;
					}
				}
			}
			if (updateSchema || !DBUtils.tableExists("SYS_IDENTITY_TOKEN")) {
				HibernateBeanFactory.reload();
			}
			// UpdateProjectBean bean = new UpdateProjectBean();
			// bean.updateTreeIds();
			TreeUpdateBean bean = new TreeUpdateBean();
			bean.updateTreeIds("default", "PROJECT_QUERY", null, "ID_", "PARENTID_", "TREEID_", "LEVEL_", null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
		logger.debug("----------------装载用户自定义数据----------------------");
		// 用户自定义数据
		loadCustomInfo();
		logger.debug("----------------装载用户自定义数据处理程序----------------");
		// 用户自定义数据处理程序
		loadCustomHandler();
		logger.debug("----------------装载用户自定义JSON数据处理程序------------");
		// 用户自定义JSON数据处理程序
		loadCustomJsonData();
		logger.debug("----------------装载用户信息---------------------------");
		// 用户信息
		loadUsers();
		logger.debug("----------------装载部门结构--------------------------");
		// 部门结构
		loadDepartments();
		logger.debug("----------------装载模块功能--------------------------");
		// 模块功能
		loadFunctions();
		logger.debug("----------------装载 数据字典--------------------------");
		// 数据字典
		loadDictInfo();
		logger.debug("----------------装载数据表定义信息----------------------");
		// 数据表定义信息
		loadTableMeta();

		Environment.removeCurrentSystemName();

		if (conf.getBoolean("multi.project.support", false)) {
			Collection<ConnectionDefinition> list = DBConfiguration.getConnectionDefinitions();
			if (list != null && !list.isEmpty()) {
				for (ConnectionDefinition def : list) {
					String name = def.getName();
					if (name != null && name.trim().length() > 0) {
						if (StringUtils.equals(name, Environment.DEFAULT_SYSTEM_NAME)) {
							continue;
						}
						logger.info("swtich system name:" + name);
						Environment.setCurrentSystemName(name);
						try {
							UpdateTreeBean bean = new UpdateTreeBean();
							bean.setSysTreeService(getSysTreeService());
							bean.updateTreeIds();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						// 用户自定义数据
						loadCustomInfo();
						// 用户自定义数据处理程序
						loadCustomHandler();
						// 用户自定义JSON数据处理程序
						loadCustomJsonData();
						// 用户信息
						loadUsers();
						// 部门结构
						loadDepartments();
						// 模块功能
						loadFunctions();
						// 数据字典
						loadDictInfo();
						// 数据表定义信息
						loadTableMeta();

						Environment.removeCurrentSystemName();
					}
				}
			}
		}

		Environment.setCurrentSystemName(Environment.DEFAULT_SYSTEM_NAME);
	}

	private void loadCustomHandler() {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		try {
			File file = new File(SystemProperties.getConfigRootPath() + CUSTOM_HANDLER);
			if (file.exists() && file.isFile()) {
				Properties props = com.glaf.core.util.PropertiesUtils.loadFilePathResource(file);
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = props.getProperty(key);
					Object object = com.glaf.core.util.ReflectUtils.instantiate(value);
					logger.debug("object:" + object.getClass().getName());
					if (object instanceof BaseDataHandler) {
						BaseDataHandler handler = (BaseDataHandler) object;
						List<BaseDataInfo> list = handler.loadData();
						// baseDataMap.put(key, list);
						String complexKey = Environment.getCurrentSystemName() + "_" + key;
						logger.debug("complexKey:" + complexKey);
						baseDataMap.put(complexKey, list);
						baseDataMap.put(key, list);
					} else if (object instanceof DataHandler) {
						DataHandler handler = (DataHandler) object;
						List<Object> list = handler.loadData();
						// dataMap.put(key, list);
						String complexKey = Environment.getCurrentSystemName() + "_" + key;
						logger.debug("complexKey:" + complexKey);
						dataListMap.put(complexKey, list);
						dataListMap.put(key, list);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("用户自定义数据处理程序出错！");
			logger.error(ex);
		}
	}

	private void loadCustomInfo() {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		try {
			File file = new File(SystemProperties.getConfigRootPath() + CUSTOM_CONFIG);
			if (file.exists() && file.isFile()) {
				Properties props = com.glaf.core.util.PropertiesUtils.loadFilePathResource(file);
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = props.getProperty(key);
					JSONObject json = JSON.parseObject(value);
					String statementId = json.getString("statementId");
					Map<String, Object> parameterObject = new HashMap<String, Object>();

					List<Object> list = this.getEntityService().getList(statementId, parameterObject);
					if (list != null && !list.isEmpty()) {
						List<BaseDataInfo> dataList = new ArrayList<BaseDataInfo>();
						for (Object object : list) {
							if (object instanceof BaseDataInfo) {
								BaseDataInfo bdf = (BaseDataInfo) object;
								dataList.add(bdf);
							} else {
								Map<String, Object> dataMap = Tools.getDataMap(object);
								BaseDataInfo bdf = new BaseDataInfo();
								Tools.populate(bdf, dataMap);
								dataList.add(bdf);
							}
						}
						// baseDataMap.put(key, dataList);
						String complexKey = Environment.getCurrentSystemName() + "_" + key;
						logger.debug("complexKey:" + complexKey);
						baseDataMap.put(complexKey, dataList);
						baseDataMap.put(key, dataList);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("提取用户自定义数据失败！");
		}
	}

	private void loadCustomJsonData() {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		try {
			File file = new File(SystemProperties.getConfigRootPath() + CUSTOM_HANDLER);
			if (file.exists() && file.isFile()) {
				Properties props = com.glaf.core.util.PropertiesUtils.loadFilePathResource(file);
				Enumeration<?> e = props.keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = props.getProperty(key);
					Object object = com.glaf.core.util.ReflectUtils.instantiate(value);
					if (object instanceof JsonDataHandler) {
						JsonDataHandler handler = (JsonDataHandler) object;
						JSONArray jsonArray = handler.loadData();
						if (jsonArray != null) {
							String complexKey = Environment.getCurrentSystemName() + "_" + key;
							logger.debug("complexKey:" + complexKey);
							jsonDataMap.put(complexKey, jsonArray.toJSONString());
							jsonDataMap.put(key, jsonArray.toJSONString());
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("提取用户自定义数据失败！");
		}
	}

	/**
	 * 装载部门信息
	 */
	private void loadDepartments() {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		try {
			SysTree parent = getSysTreeService().getSysTreeByCode(SysConstants.TREE_DEPT);
			if (parent == null) {
				return;
			}
			List<SysTree> list = new ArrayList<SysTree>();
			getSysTreeService().getSysTree(list, parent.getId(), 0);
			SysTreeQuery query = new SysTreeQuery();
			query.setDiscriminator("D");
			query.setTreeIdLike(parent.getTreeId() + "%");
			List<SysTree> deptTrees = getSysTreeService().getSysTreesByQueryCriteria(0, 5000, query);
			Map<Long, SysTree> deptTreeMap = new HashMap<Long, SysTree>();
			if (deptTrees != null && !deptTrees.isEmpty()) {
				for (SysTree t : deptTrees) {
					deptTreeMap.put(t.getId(), t);
				}
			}
			// 显示所有部门列表
			if (list != null) {
				Iterator<SysTree> iter = list.iterator();
				List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
				while (iter.hasNext()) {
					SysTree tree = (SysTree) iter.next();
					SysDepartment bean = tree.getDepartment();
					if (bean != null) {
						BaseDataInfo bdi = new BaseDataInfo();
						bdi.setId(bean.getId());// 部门id
						bdi.setName(bean.getName());// 部门名称
						bdi.setCode(bean.getCode());// 部门代码
						bdi.setNo(bean.getNo());// 部门编号
						bdi.setDeep(tree.getDeep());
						// bdi.setParentId(tree.getParent());
						SysTree parentTree = deptTreeMap.get(tree.getParentId());
						if (parentTree != null && parentTree.getDepartment() != null
								&& parent.getId() != parentTree.getId()) {// 不等于部门结构,则取部门
							bdi.setParentId(parentTree.getDepartment().getId());
						} else {
							bdi.setParentId(parent.getParentId());
						}
						if (outputLog) {
							// logger.debug("id:" + bean.getId() + ",name:" + bean.getName() + "&no:" +
							// bean.getNo());
						}
						tmp.add(bdi);
					}
				}
				// baseDataMap.put(Constants.SYS_DEPTS, tmp);
				String complexKey = Environment.getCurrentSystemName() + "_" + SysConstants.SYS_DEPTS;
				baseDataMap.put(complexKey, tmp);
			}
			logger.info("装载部门信息结束");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
			logger.error("提取部门数据失败！");
		}
	}

	/**
	 * 装载字典信息
	 */
	public void loadDictInfo() {
		logger.debug("Environment.getCurrentSystemName():" + Environment.getCurrentSystemName());
		try {
			logger.info("装载字典信息开始...");
			List<SysTree> trees = getDictoryService().getAllCategories();
			for (int i = 0, len = trees.size(); i < len; i++) {
				SysTree treeNode = trees.get(i);
				if (treeNode != null) {
					List<Dictory> list = getDictoryService().getAvailableDictoryList(treeNode.getId());
					if (list != null && !list.isEmpty()) {
						Iterator<Dictory> iter = list.iterator();
						List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
						while (iter.hasNext()) {
							Dictory bean = (Dictory) iter.next();
							BaseDataInfo bdi = new BaseDataInfo();
							bdi.setId(bean.getId());// 字典id
							bdi.setName(bean.getName());// 字典名称
							bdi.setCode(bean.getCode());// 字典代码
							bdi.setValue(bean.getValue());// 字典代码
							bdi.setDesc(bean.getDesc());// 字典描述
							bdi.setExt1(bean.getExt1());// 扩展字段1
							bdi.setExt2(bean.getExt2());// 扩展字段2
							bdi.setDesc(bean.getDesc());// 备注
							bdi.setExt3(bean.getExt3());// 扩展字段3
							bdi.setExt4(bean.getExt4());// 扩展字段4

							bdi.setExt5(bean.getExt5());// 扩展字段5
							bdi.setExt6(bean.getExt6());// 扩展字段6
							bdi.setExt7(bean.getExt7());
							bdi.setExt8(bean.getExt8());
							bdi.setExt9(bean.getExt9());

							bdi.setExt10(bean.getExt10());
							bdi.setExt11(bean.getExt11());
							bdi.setExt12(bean.getExt12());
							bdi.setExt13(bean.getExt13());
							bdi.setExt14(bean.getExt14());
							bdi.setExt15(bean.getExt15());
							bdi.setExt16(bean.getExt16());
							bdi.setExt17(bean.getExt17());
							bdi.setExt18(bean.getExt18());
							bdi.setExt19(bean.getExt19());
							bdi.setExt20(bean.getExt20());
							bdi.setCategory(bean.getCategory());
							bdi.setDeep(0);
							if (outputLog) {
								logger.debug("id:" + bean.getId() + ",name:" + bean.getName() + ",code:"
										+ bean.getCode() + ",value:" + bean.getValue());
							}
							tmp.add(bdi);
						}
						// baseDataMap.put(treeNode.getCode(), tmp);
						String complexKey = Environment.getCurrentSystemName() + "_" + treeNode.getCode();
						baseDataMap.put(complexKey, tmp);
					}
				}
			}
			logger.info("装载字典信息结束.");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("提取字典数据失败！");
		}
	}

	/**
	 * 装载模块信息
	 */
	public void loadFunctions() {
		try {
			logger.info("装载模块信息开始...");
			List<SysFunction> list = getSysFunctionService().getSysFunctionList();
			// 显示所有模块列表
			if (list != null && !list.isEmpty()) {
				Iterator<SysFunction> iter = list.iterator();
				List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
				while (iter.hasNext()) {
					SysFunction bean = (SysFunction) iter.next();
					if (bean != null) {
						BaseDataInfo bdi = new BaseDataInfo();
						bdi.setId(bean.getId());// 模块id
						bdi.setName(bean.getName());// 模块名称
						bdi.setCode(bean.getFuncMethod());// 模块方法
						if (outputLog) {
							logger.debug("id:" + bean.getId() + ",name:" + bean.getName() + ", method:"
									+ bean.getFuncMethod());
						}
						tmp.add(bdi);
					}
				}
				// baseDataMap.put(Constants.SYS_FUNCTIONS, tmp);
				String complexKey = Environment.getCurrentSystemName() + "_" + SysConstants.SYS_FUNCTIONS;
				baseDataMap.put(complexKey, tmp);
			}
			logger.info("装载模块信息结束");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("提取模块数据失败！");
		}
	}

	private void loadTableMeta() {
		// 需要在glaf-base-site.xml中配置load.table.meta=true
		/**
		 * 
		 * <property> <name>load.table.meta</name> <value>true</value></property>
		 * 
		 */
		if (conf.getBoolean("load.table.meta", false)) {
			logger.debug("load table meta...");
			ITableDefinitionService tableDefinitionService = null;
			try {
				tableDefinitionService = ContextFactory.getBean("tableDefinitionService");
				if (tableDefinitionService != null) {
					DbTableChecker checker = new DbTableChecker();
					checker.setTableDefinitionService(tableDefinitionService);
					checker.checkTables();
				}
			} catch (Exception ex) {
				logger.error(ex);
				ex.printStackTrace();
			}
			logger.debug("load table meta finish.");
		}
	}

	/**
	 * 装载用户信息
	 */
	private void loadUsers() {
		try {
			List<SysUser> list = getSysUserService().getSysUserList();
			if (list != null) {
				Iterator<SysUser> iter = list.iterator();
				List<BaseDataInfo> tmp = new ArrayList<BaseDataInfo>();
				while (iter.hasNext()) {
					SysUser bean = (SysUser) iter.next();
					if (bean != null) {
						BaseDataInfo bdi = new BaseDataInfo();
						bdi.setName(bean.getName());// 用户名称
						bdi.setCode(bean.getAccount());// 用户招聘号
						bdi.setValue(bean.getAccount());
						bdi.setExt1(bean.getTelephone());// 用户电话
						if (outputLog) {
							// logger.debug("id:" + bean.getAccount() + ",name:" + bean.getName() +
							// ",telephone:"
							// + bean.getTelephone());
						}
						tmp.add(bdi);
					}
				}
				// baseDataMap.put(Constants.SYS_USERS, tmp);
				String complexKey = Environment.getCurrentSystemName() + "_" + SysConstants.SYS_USERS;
				baseDataMap.put(complexKey, tmp);
			}
			logger.info("装载用户信息结束");
		} catch (Exception ex) {
			logger.error(ex);
			ex.printStackTrace();
			logger.error("提取用户信息失败！");
		}
	}

	/**
	 * 刷新基础信息数据（有基础信息变更时调用）
	 * 
	 */
	public void refreshBaseData() {
		/**
		 * 确保只有一个线程能装载基础数据
		 */
		if (!loading.get()) {
			java.sql.Connection conn = null;
			try {
				loading.set(true);
				long start = System.currentTimeMillis();
				// baseDataMap.cleanUp();
				// dataListMap.cleanUp();
				// jsonDataMap.cleanUp();
				// com.glaf.core.config.ConfigFactory.clearAll();
				// com.glaf.core.resource.ResourceFactory.clearAll();

				initBaseData();

				TreeUpdateBean updateBean = new TreeUpdateBean();
				updateBean.updateTreeIds("default", "sys_tree", null, "ID", "PARENT", "TREEID", null, null);

				long ts = System.currentTimeMillis() - start;
				logger.info("重新装载基础数据用时（毫秒）：" + ts);

				List<String> tables = DBUtils.getTables();
				conn = DBConnectionFactory.getConnection();
				conn.setAutoCommit(false);
				String ddlStatements = "";
				for (String table : tables) {
					if (StringUtils.startsWithIgnoreCase(table, "LOG_")) {
						ddlStatements = " TRUNCATE TABLE " + table;
						DBUtils.executeSchemaResourceIgnoreException(conn, ddlStatements);
						logger.debug(ddlStatements);
						ddlStatements = " DROP TABLE " + table;
						DBUtils.executeSchemaResourceIgnoreException(conn, ddlStatements);
						logger.debug(ddlStatements);
						conn.commit();
					}
				}
				conn.commit();
			} catch (Exception ex) {
				logger.error(ex);
			} finally {
				JdbcUtils.close(conn);
				loading.set(false);
			}
		}
	}

	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}

	public void setEntityService(EntityService entityService) {
		this.entityService = entityService;
	}

	public void setSysApplicationService(SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	public void setSysDepartmentService(SysDepartmentService sysDepartmentService) {
		this.sysDepartmentService = sysDepartmentService;
	}

	public void setSysFunctionService(SysFunctionService sysFunctionService) {
		this.sysFunctionService = sysFunctionService;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}

	public void setSysUserRoleService(SysUserRoleService sysUserRoleService) {
		this.sysUserRoleService = sysUserRoleService;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void startScheduler() {
		RefreshTask command = new RefreshTask();
		scheduledThreadPool.scheduleAtFixedRate(command, 15, 30, TimeUnit.MINUTES);
	}

}