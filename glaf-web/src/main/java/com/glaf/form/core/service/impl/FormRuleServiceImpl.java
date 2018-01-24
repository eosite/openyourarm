package com.glaf.form.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.UUID32;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormRuleAudit;
import com.glaf.form.core.mapper.FormRuleAuditMapper;
import com.glaf.form.core.mapper.FormRuleMapper;
import com.glaf.form.core.query.FormRuleQuery;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.util.FormRuleJsonFactory;

@Service("formRuleService")
@Transactional(readOnly = true)
public class FormRuleServiceImpl implements FormRuleService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormRuleMapper formRuleMapper;

	protected FormRuleAuditMapper formRuleAuditMapper;

	protected FormRulePropertyService formRulePropertyService;

	protected DepReportTemplateService depReportTemplateService;

	public FormRuleServiceImpl() {

	}

	public int count(FormRuleQuery query) {
		return formRuleMapper.getFormRuleCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formRuleMapper.deleteFormRuleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formRuleMapper.deleteFormRuleById(id);
			}
		}
	}
	
	
	@Transactional
	public void deleteByRuleIds(List<String> ruleIds) {
		if (ruleIds != null && !ruleIds.isEmpty()) {
			formRuleMapper.deleteFormRuleByIds(ruleIds);
		}
	}

	public FormRule getFormRule(String id) {
		if (id == null) {
			return null;
		}
		FormRule formRule = formRuleMapper.getFormRuleById(id);
		return formRule;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormRuleCountByQueryCriteria(FormRuleQuery query) {
		return formRuleMapper.getFormRuleCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormRule> getFormRulesByQueryCriteria(int start, int pageSize, FormRuleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormRule> rows = sqlSessionTemplate.selectList("getFormRules", query, rowBounds);
		return rows;
	}

	/**
	 * 获取某个页面的规则
	 * 
	 * @return
	 */
	public List<FormRule> getRules(String pageId) {
		return getRules(pageId, true);
	}

	/**
	 * 
	 */
	@Override
	public List<FormRule> getRules(String pageId, boolean useCache) {
		String cacheKey = "form_rules_" + pageId;
		if(useCache){
			if (SystemConfig.getBoolean("use_query_cache")) {
				String text = CacheFactory.getString("form_rules", cacheKey);
				if (StringUtils.isNotEmpty(text)) {
					try {
						JSONArray array = JSON.parseArray(text);
						return FormRuleJsonFactory.arrayToList(array);
					} catch (Exception ex) {
					}
				}
			}
		}
		FormRuleQuery query = new FormRuleQuery();
		query.pageId(pageId);
		List<FormRule> list = this.list(query);
		if(useCache){
			if (list != null && !list.isEmpty()) {
				if (SystemConfig.getBoolean("use_query_cache")) {
					JSONArray array = FormRuleJsonFactory.listToArray(list);
					CacheFactory.put("form_rules", cacheKey, array.toJSONString());
				}
			}
		}
		return list;
	}
	/**
	 * 获取某个页面某个元素的规则
	 * 
	 * @return
	 */
	public List<FormRule> getRulesByEleId(String pageId, String eleId) {
		List<FormRule> list = new ArrayList<FormRule>();
		List<FormRule> rules = getRules(pageId);
		for (FormRule formRule : rules) {
			if (eleId.equalsIgnoreCase(formRule.getName())) {
				list.add(formRule);
				break;
			}
		}
		return list;
	}

	public List<FormRule> list(FormRuleQuery query) {
		List<FormRule> list = formRuleMapper.getFormRules(query);
		return list;
	}

	@Transactional
	public void save(FormRule formRule) {
		if (StringUtils.isEmpty(formRule.getId())) {
			formRule.setId(UUID32.getUUID());
			formRule.setCreateDate(new Date());
			formRuleMapper.insertFormRule(formRule);
		} else {
			formRuleMapper.updateFormRule(formRule);
		}

		FormRuleAudit audit = new FormRuleAudit();
		audit.setAppId(formRule.getAppId());
		audit.setComponentId(formRule.getComponentId());
		audit.setCreateBy(formRule.getCreateBy());
		audit.setCreateDate(formRule.getCreateDate());
		audit.setDeploymentId(formRule.getDeploymentId());
		audit.setId(formRule.getPageId() + "/" + idGenerator.getNextId("FormRuleAudit"));
		audit.setName(formRule.getName());
		audit.setPageId(formRule.getPageId());
		audit.setSnapshot(formRule.getSnapshot());
		audit.setType(formRule.getType());
		audit.setValue(formRule.getValue());
		audit.setVersion(DateUtils.getNowYearMonthDay());
		formRuleAuditMapper.insertFormRuleAudit(audit);

		String cacheKey = "form_rules_" + formRule.getPageId();
		CacheFactory.remove("form_rules", cacheKey);
		
		//必须要拆开来 可能会导致死锁
		//this.formRulePropertyService.saveRuleProperties(formRule);// 拆分规则属性
	}

	/**
	 * 保存某个页面的规则集合
	 * 
	 * @param pageId
	 * @param formRules
	 */
	@Transactional
	public void saveRules(String pageId, List<FormRule> formRules) {
		String cacheKey = "form_rules_" + pageId;
		CacheFactory.remove("form_rules", cacheKey);
		formRuleMapper.deleteFormRulesByPageId(pageId);
		int index = 0;
		for (FormRule formRule : formRules) {
			index++;
			if (StringUtils.isEmpty(formRule.getId())) {
				formRule.setId(pageId + "_" + index);
			}
			formRuleMapper.insertFormRule(formRule);

			FormRuleAudit audit = new FormRuleAudit();
			audit.setAppId(formRule.getAppId());
			audit.setComponentId(formRule.getComponentId());
			audit.setCreateBy(formRule.getCreateBy());
			audit.setCreateDate(formRule.getCreateDate());
			audit.setDeploymentId(formRule.getDeploymentId());
			audit.setId(formRule.getPageId() + "/" + idGenerator.getNextId("FormRuleAudit"));
			audit.setName(formRule.getName());
			audit.setPageId(formRule.getPageId());
			audit.setSnapshot(formRule.getSnapshot());
			audit.setType(formRule.getType());
			audit.setValue(formRule.getValue());
			audit.setVersion(DateUtils.getNowYearMonthDay());
			formRuleAuditMapper.insertFormRuleAudit(audit);
		}
	}

	/**
	 * 根据页面id 获取主表名称
	 * 
	 * @param pageId
	 * @return
	 */
	@Override
	public String getTableNameByPageId(String pageId) {
		String tableName = null;
		Map<String, String> ruleMap = this.getRuleByPageId(pageId);
		if (ruleMap != null) {
			JSONObject jsonObject;
			String dataSourceSet = "dataSourceSet", saveSourceSet = "saveSourceSet";
			if (ruleMap.containsKey(saveSourceSet)) {
				saveSourceSet = ruleMap.get(saveSourceSet);
				if (StringUtils.isNotBlank(saveSourceSet)) {

					JSONArray tables = JSONArray.parseArray(saveSourceSet);
					if (CollectionUtils.isNotEmpty(tables)) {
						if (tables.size() == 1) {
							tableName = tables.getJSONObject(0).getJSONObject("table").getString("tableName");
						} else {
							for (int i = 0; i < tables.size(); i++) {
								jsonObject = tables.getJSONObject(i);
								if(jsonObject.containsKey("topTableName")){
									tableName = jsonObject.getString("topTableName");
								} else if(StringUtils.isBlank(tableName)){
									tableName = jsonObject.getString("tableName");
								}
							}
						}
					}
					/*jsonObject = JSONArray.parseArray(saveSourceSet).getJSONObject(0);
					if (jsonObject != null) {
						tableName = jsonObject.getJSONObject("table").getString("tableName");
					}*/
				}
			} else if (ruleMap.containsKey(dataSourceSet)) {
				dataSourceSet = ruleMap.get(dataSourceSet);
				if (StringUtils.isNotBlank(dataSourceSet)) {
					jsonObject = JSONArray.parseArray(dataSourceSet).getJSONObject(0);
					if (jsonObject != null) {
						tableName = jsonObject.getJSONArray("tables").getString(0);
					}
				}
			} else {
				// 报表
				FormRuleQuery query = new FormRuleQuery();
				query.setComponentId(3350L);
				query.setPageId(pageId);
				List<FormRule> list = this.list(query);
				if (CollectionUtils.isNotEmpty(list)) {
					FormRule fr = list.get(0);
					ruleMap = this.formRulePropertyService.getRuleMap(fr.getId());
					String linkPage = ruleMap.get("linkPage");
					if (StringUtils.isNotBlank(linkPage)) {
						JSONObject o = JSONArray.parseArray(linkPage).getJSONObject(0);
						if (o != null) {
							Long id = o.getLong("id");
							tableName = this.depReportTemplateService.getTableName(id);
						}
					}
				}
			}
		}
		return tableName;
	}

	@Override
	public String getIdVariable(String pageId, String idKey) {
		Map<String, String> ruleMap = this.getRuleByPageId(pageId);
		if (ruleMap == null) {
			return null;
		}

		/**
		 * 根据【输入表达式定义】
		 */
		String inParameters = ruleMap.get("inParameters");
		String variable = null;
		if (StringUtils.isNotBlank(inParameters)) {
			JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
			if (jsonArray0 != null && !jsonArray0.isEmpty()) {
				h1: for (int i = 0; i < jsonArray0.size(); i++) {
					JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
					for (int ii = 0; ii < jsonArray.size(); ii++) {
						JSONObject jsonObject = jsonArray.getJSONObject(ii);
						String strs = jsonObject.getString("paramData");
						String expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE);
						if (!StringUtils.isBlank(expActVal))
							expActVal = StringUtils.split(expActVal, ".")[0];
						variable = expActVal;
						strs = jsonObject.getString("fieldData");
						expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal,
								ExpressionConvertUtil.DATABASE_TYPE);
						if (!StringUtils.isBlank(expActVal)) {
							String fieldName = StringUtils.split(expActVal, ".")[1];
							if (fieldName.equalsIgnoreCase(idKey)) {
								break h1;
							}
						}
					}
					variable = null;
				}
			}
		}

		/**
		 * 根据【输入输出参数关系定义】
		 */
		if (StringUtils.isBlank(variable)) {
			String paraType = ruleMap.get("paraType");
			if (StringUtils.isNotBlank(paraType)) {
				try {
					JSONArray jsonArray0 = JSONArray.parseArray(paraType);
					JSONObject datas = jsonArray0.getJSONObject(0).getJSONObject("datas");
					if (datas != null) {
						h1: for (String key : datas.keySet()) {
							jsonArray0 = datas.getJSONArray(key);
							if (CollectionUtils.isNotEmpty(jsonArray0)) {
								for (int i = 0; i < jsonArray0.size(); i++) {
									JSONObject o = jsonArray0.getJSONObject(i);
									if (StringUtils.equalsIgnoreCase((variable = o.getString("inparam")), "wbsId")) {
										break h1;
									}
								}
							}
							variable = null;
						}
					}

				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		return variable;
	}

	public Map<String, String> getRuleByPageId(String pageId) {
		List<FormRule> frs = this.getRules(pageId);
		for (FormRule fr : frs) {
			if (fr.getName().equalsIgnoreCase(pageId)) {
				return this.formRulePropertyService.getRuleMap(fr.getId());
			}
		}
		return null;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormRuleAuditMapper(FormRuleAuditMapper formRuleAuditMapper) {
		this.formRuleAuditMapper = formRuleAuditMapper;
	}

	@javax.annotation.Resource
	public void setFormRuleMapper(FormRuleMapper formRuleMapper) {
		this.formRuleMapper = formRuleMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setDepReportTemplateService(DepReportTemplateService depReportTemplateService) {
		this.depReportTemplateService = depReportTemplateService;
	}


}
