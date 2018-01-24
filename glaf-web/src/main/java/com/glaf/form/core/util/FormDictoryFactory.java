package com.glaf.form.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.service.IFormDictTreeService;
import com.glaf.form.core.service.IFormDictoryService;

public class FormDictoryFactory {

	protected static final Log logger = LogFactory.getLog(FormDictoryFactory.class);

	private static FormDictoryFactory instance;

	private Map<String, List<FormDictory>> dataMap;

	private FormDictoryFactory() {

	}

	/**
	 * 根据类型编码从缓存获取定义平台基础数据
	 * 
	 * @param treeCode
	 *            类型编码
	 * @return
	 */
	public List<FormDictory> getFormDictoryListByTreeCode(String treeCode) {
		if (dataMap == null || dataMap.isEmpty()) {
			initData();
		}

		String complexKey = Environment.getCurrentSystemName() + "_" + treeCode;
		if (dataMap.containsKey(complexKey)) {
			return dataMap.get(complexKey);
		}
		if (dataMap.containsKey(treeCode)) {
			return dataMap.get(treeCode);
		}

		return null;
	}
	
	/**
	 * 根据类型编码从缓存获取定义平台基础数据
	 * @param treeCode
	 * @return 返回JSONArray类型
	 */
	public JSONArray getFormDictoryJSONArrayByTreeCode(String treeCode){
		List<FormDictory> dicts = this.getFormDictoryListByTreeCode(treeCode);
		JSONArray dictArray = new JSONArray();
		for(FormDictory dict : dicts){
			JSONObject obj = new JSONObject();
			obj.put("code", dict.getCode());
			obj.put("desc", dict.getDesc());
			obj.put("name", dict.getName());
			obj.put("text", dict.getName());
			obj.put("value", dict.getValue());
			dictArray.add(obj);
		}
		return dictArray;
	}

	public Iterator<FormDictory> getFormDictoryIteratorByTreeCode(String treeCode) {
		List<FormDictory> list = getFormDictoryListByTreeCode(treeCode);
		if (list != null) {
			return list.iterator();
		} else {
			return null;
		}
	}
	
	/**
	 * 根据字典Code获取对象
	 * @param directoryCode
	 * @return
	 */
	public FormDictory getFormDictoryByCode(String directoryCode){
		FormDictory dict = formDictoryService.getFormDictoryByCode(directoryCode);
		return dict;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		if (dataMap == null) {
			dataMap = new HashMap<String, List<FormDictory>>();
		}
		loadData();
	}

	/**
	 * 数据重载
	 */
	public void reload() {
		if (dataMap == null) {
			initData();
		} else {
			dataMap.clear();
			loadData();
		}
	}

	private void loadData() {
		logger.info("装载定义平台基础数据开始Start!");
		List<FormDictTree> trees = getFormDictoryService().getAllCategories();
		for (int i = 0; i < trees.size(); i++) {
			FormDictTree treeNode = trees.get(i);
			if (treeNode != null) {
				List<FormDictory> list = getFormDictoryService().getAvailableDictoryList(treeNode.getId());

				if (list != null && !list.isEmpty()) {
					String complexKey = Environment.getCurrentSystemName()
							+ "_" + treeNode.getCode();
					dataMap.put(complexKey, list);
				}
			}
		}
		logger.info("装载定义平台基础数据结束End!");
	}

	public static synchronized FormDictoryFactory getInstance() {
		if (instance == null) {
			instance = new FormDictoryFactory();
			instance.initData();
		}
		return instance;
	}

	private IFormDictoryService formDictoryService;

	private IFormDictTreeService formDictTreeService;

	public IFormDictoryService getFormDictoryService() {
		if (formDictoryService == null) {
			formDictoryService = (IFormDictoryService) ContextFactory
					.getBean("formDictoryService");
		}

		return formDictoryService;
	}

	public IFormDictTreeService getFormDictTreeService() {
		if (formDictTreeService == null) {
			formDictTreeService = (IFormDictTreeService) ContextFactory
					.getBean("formDictTreeService");
		}
		return formDictTreeService;
	}

}
