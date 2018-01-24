package com.glaf.form.core.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormPageHistory;
import com.glaf.form.core.mapper.FormPageMapper;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.service.FormPageHistoryService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.util.FormPageJsonFactory;
import com.glaf.form.exception.FormPageDesignerException;

@Service("formPageService")
@Transactional(readOnly = true)
public class FormPageServiceImpl implements FormPageService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormPageMapper formPageMapper;

	protected FormPageHistoryService formPageHistoryService;

	public FormPageServiceImpl() {

	}

	public int count(FormPageQuery query) {
		return formPageMapper.getFormPageCount(query);
	}

	@Transactional
	public void deleteById(String pageId) {
		if (pageId != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				String cacheKey = "form_page_" + pageId;
				CacheFactory.remove("form_page", cacheKey);
			}
			formPageMapper.deleteFormPageById(pageId);
		}
	}

	@Transactional
	public void deleteByIds(List<String> pageIds) {
		if (pageIds != null && !pageIds.isEmpty()) {
			for (String pageId : pageIds) {
				if (SystemConfig.getBoolean("use_query_cache")) {
					String cacheKey = "form_page_" + pageId;
					CacheFactory.remove("form_page", cacheKey);
				}
				formPageMapper.deleteFormPageById(pageId);
			}
		}
	}

	public List<FormPage> getChildren(String parentId) {
		return formPageMapper.getChildren(parentId);
	}
	
	public FormPage getFormPage(String pageId) {
		return getFormPage(pageId, true);
	}

	public FormPage getFormPage(String pageId,boolean useCache) {
		if (pageId == null) {
			return null;
		}
		String cacheKey = "form_page_" + pageId;
		if(useCache){
			if (SystemConfig.getBoolean("use_query_cache")) {
				String text = CacheFactory.getString("form_page", cacheKey);
				if (StringUtils.isNotEmpty(text)) {
					try {
						JSONObject result = JSON.parseObject(text);
						FormPage formPage = FormPageJsonFactory.jsonToObject(result);
						return formPage;
						/*if (formPage.getCacheTime() > 0) {
							long time = System.currentTimeMillis() - formPage.getCacheTime();
							if (time < DateUtils.MINUTE * 5) {// 5分钟过期
								return formPage;
							} else {
								CacheFactory.remove("form_page", cacheKey);
							}
						}*/
					} catch (Exception ex) {
					}
				}
			}
		}

		FormPage formPage = formPageMapper.getFormPageById(pageId);
		if(useCache){
			if (formPage != null) {
				if (SystemConfig.getBoolean("use_query_cache")) {
					formPage.setCacheTime(System.currentTimeMillis());
					CacheFactory.put("form_page", cacheKey, formPage.toJsonObject().toJSONString());
				}
			}
		}
		return formPage;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormPageCountByQueryCriteria(FormPageQuery query) {
		return formPageMapper.getFormPageCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormPage> getFormPagesByQueryCriteria(int start, int pageSize, FormPageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormPage> rows = sqlSessionTemplate.selectList("getFormPages", query, rowBounds);
		return rows;
	}

	@Override
	public List<FormPage> getFormPageTree(FormPageQuery query) {
		return formPageMapper.getFormPageTree(query);
	}

	public List<FormPage> list(FormPageQuery query) {
		List<FormPage> list = formPageMapper.getFormPages(query);
		return list;
	}

	@Transactional
	public void save(FormPage formPage) throws FormPageDesignerException  {
		if (StringUtils.isEmpty(formPage.getId()) || formPage.isExtend()) {
			if (StringUtils.isEmpty(formPage.getId())) {
				formPage.setId(UUID32.getUUID());
			}
			formPage.setCreateDate(new Date());
			// formPage.setDeleteFlag(0);
			formPageMapper.insertFormPage(formPage);
		} else {
			String cacheKey = "form_page_" + formPage.getId();
			CacheFactory.remove("form_page", cacheKey);
			formPageMapper.updateFormPage(formPage);
		}
		// 最多保存10版本
		Integer saveVersionNum = SystemProperties.getInt("saveVersionNum");
		formPageHistoryService.deleteOldVersion(formPage.getId(), saveVersionNum == 0 ? 10 : saveVersionNum);
		
		if("bootstrap".equalsIgnoreCase(formPage.getUiType()) && "0".equals(formPage.getFormType()) && !formPage.getName().equals(formPage.getFormHtml()) && formPage.getDesignerHtml() ==null){
			throw new FormPageDesignerException(formPage.getId()+"->页面设计信息保存丢失出错");
		}

		FormPageHistory history = new FormPageHistory();
		history.setCreateBy(formPage.getCreateBy());
		history.setCreateDate(new Date());
		history.setFormConfig(formPage.getFormConfig());
		history.setFormHtml(formPage.getFormHtml());
		history.setFormType(formPage.getFormType());
		history.setOutputHtml(formPage.getOutputHtml());
		history.setPageId(formPage.getId());
		history.setDeploymentId(formPage.getDeploymentId());
		history.setDesignerHtml(formPage.getDesignerHtml());
		history.setDesignerJson(formPage.getDesignerJson());

		try {
			formPageHistoryService.save(history);
		} catch (java.lang.Throwable ex) {
			ex.printStackTrace();
			logger.error("错误" + ex.getMessage());
		}

	}

	@Override
	public List<Map<String, Object>> getPageElementsById(String pageId) {
		return formPageMapper.getPageElementsById(pageId);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormPageMapper(FormPageMapper formPageMapper) {
		this.formPageMapper = formPageMapper;
	}

	@javax.annotation.Resource
	public void setFormPageHistoryService(FormPageHistoryService formPageHistoryService) {
		this.formPageHistoryService = formPageHistoryService;
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

	@Override
	@Transactional
	public void enabledDisable(String id, int locked) {
		if (id != null && !id.isEmpty()) {
			FormPage formPage = new FormPage();
			formPage.setId(id);
			formPage.setLocked(locked);
			formPageMapper.updateFormPage(formPage);
			String cacheKey = "form_page_" + formPage.getId();
			CacheFactory.remove("form_page", cacheKey);
		}
	}

	@Override
	public void updateFormPageParentId(String parentid, List<String> ids, String actorId) {
		FormPageQuery query = new FormPageQuery();
		query.setNodeParentId(parentid);
		query.setIds(ids);
		query.setActorId(actorId);
		formPageMapper.updateFormPageParentId(query);
	}

	@Override
	public void updateThemeId(String id, String themeId) {
		formPageMapper.updateThemeId(id, themeId);
	}

}
