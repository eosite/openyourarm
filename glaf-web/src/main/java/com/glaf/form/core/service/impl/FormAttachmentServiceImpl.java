package com.glaf.form.core.service.impl;

import java.util.List;

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
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.factory.RedisFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.domain.FormAttachment;
import com.glaf.form.core.mapper.FormAttachmentMapper;
import com.glaf.form.core.query.FormAttachmentQuery;
import com.glaf.form.core.service.IFormAttachmentService;
import com.glaf.form.core.util.FormAttachmentJsonFactory;

@Service("formAttachmentService")
@Transactional(readOnly = true)
public class FormAttachmentServiceImpl implements IFormAttachmentService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormAttachmentMapper formAttachmentMapper;

	@Override
	@Transactional
	public void deleteById(String id) {
		formAttachmentMapper.deleteFormAttachmentById(id);
		String cacheKey = "form_attach_" + id;
		CacheFactory.remove("form_attach", cacheKey);
		if (SystemConfig.getBoolean("use_file_cache")) {
			try {
				// 删除对象时从缓存删除
				RedisFactory.getInstance().del(cacheKey.getBytes());
			} catch (Exception ex) {
			}
		}
	}

	@Override
	@Transactional
	public void deleteByIds(List<String> ids) {
		// formAttachmentMapper.deleteFormAttachmentByIds(ids);
		for (String id : ids) {
			this.deleteById(id);
		}
	}

	@Override
	public void deleteByParent(String parent) {
		formAttachmentMapper.deleteFormAttachmentByParent(parent);
	}

	public FormAttachment getCachedFormAttachmentNotContentById(String id) {
		String cacheKey = "form_attach_" + id;
		String text = CacheFactory.getString("form_attach", cacheKey);
		if (StringUtils.isNotEmpty(text)) {
			try {
				JSONObject result = JSON.parseObject(text);
				FormAttachment model = FormAttachmentJsonFactory.jsonToObject(result);
				return model;
			} catch (Exception ex) {
			}
		}

		FormAttachment model = formAttachmentMapper.getFormAttachmentNotContentById(id);
		if (model != null) {
			CacheFactory.put("form_attach", cacheKey, model.toJsonObject().toJSONString());
		}
		return model;
	}

	public FormAttachment getFormAttachmentById(String id) {
		FormAttachment model = formAttachmentMapper.getFormAttachmentById(id);
		if (model != null) {

		}
		return model;
	}

	@Override
	public List<FormAttachment> getFormAttachmentByParent(String parent) {
		FormAttachmentQuery query = new FormAttachmentQuery();
		query.setParent(parent);
		return this.list(query);
	}

	@Override
	public List<FormAttachment> getFormAttachmentByParentNotContent(String parent) {
		FormAttachmentQuery query = new FormAttachmentQuery();
		query.setParent(parent);
		query.setOrderBy(" CREATEDATE ASC");
		return formAttachmentMapper.getFormAttachmentsNotContent(query);
	}

	@Override
	public List<FormAttachment> getFormAttachmentByQueryCriteria(int start, int pageSize, FormAttachmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);

		List<FormAttachment> results = sqlSessionTemplate.selectList("getFormAttachments", query, rowBounds);
		return results;
	}

	@Override
	public int getFormAttachmentCountByQueryCriteria(FormAttachmentQuery query) {
		return formAttachmentMapper.getFormAttachmentCount(query);
	}

	@Override
	public List<FormAttachment> list(FormAttachmentQuery query) {
		return formAttachmentMapper.getFormAttachments(query);
	}

	@Override
	public List<FormAttachment> listNotContent(FormAttachmentQuery query) {
		return formAttachmentMapper.getFormAttachmentsNotContent(query);
	}

	@Override
	@Transactional
	public FormAttachment save(FormAttachment model) {
		if (model.getId() == null) {
			// 修改为用uuid保存
			model.setId(UUID32.getUUID());
			formAttachmentMapper.insertFormAttachment(model);
		} else {
			update(model);
		}
		return model;
	}

	@javax.annotation.Resource
	public void setFormAttachmentMapper(FormAttachmentMapper formAttachmentMapper) {
		this.formAttachmentMapper = formAttachmentMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	@Transactional
	public void update(FormAttachment model) {
		formAttachmentMapper.updateFormAttachment(model);
		String cacheKey = "form_attach_" + model.getId();
		CacheFactory.remove("form_attach", cacheKey);
		if (SystemConfig.getBoolean("use_file_cache")) {
			try {
				// 更新对象时从缓存删除
				RedisFactory.getInstance().del(cacheKey.getBytes());
			} catch (Exception ex) {
			}
		}
	}
}
