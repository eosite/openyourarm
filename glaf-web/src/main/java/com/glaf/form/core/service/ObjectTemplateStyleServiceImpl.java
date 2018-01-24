package com.glaf.form.core.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.form.core.domain.ObjectTemplateStyle;
import com.glaf.form.core.mapper.ObjectTemplateStyleMapper;
import com.glaf.form.core.query.ObjectTemplateStyleQuery;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("com.glaf.form.core.service.objectTemplateStyleService")
@Transactional(readOnly = true)
public class ObjectTemplateStyleServiceImpl implements ObjectTemplateStyleService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ObjectTemplateStyleMapper objectTemplateStyleMapper;

	public ObjectTemplateStyleServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			objectTemplateStyleMapper.deleteObjectTemplateStyleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> styleIds) {
		if (styleIds != null && !styleIds.isEmpty()) {
			for (Long id : styleIds) {
				objectTemplateStyleMapper.deleteObjectTemplateStyleById(id);
			}
		}
	}

	public int count(ObjectTemplateStyleQuery query) {
		return objectTemplateStyleMapper.getObjectTemplateStyleCount(query);
	}

	public List<ObjectTemplateStyle> list(ObjectTemplateStyleQuery query) {
		List<ObjectTemplateStyle> list = objectTemplateStyleMapper.getObjectTemplateStyles(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getObjectTemplateStyleCountByQueryCriteria(ObjectTemplateStyleQuery query) {
		return objectTemplateStyleMapper.getObjectTemplateStyleCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ObjectTemplateStyle> getObjectTemplateStylesByQueryCriteria(int start, int pageSize,
			ObjectTemplateStyleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ObjectTemplateStyle> rows = sqlSessionTemplate.selectList("getObjectTemplateStyles", query, rowBounds);
		return rows;
	}

	public ObjectTemplateStyle getObjectTemplateStyle(Long id) {
		if (id == null) {
			return null;
		}
		ObjectTemplateStyle objectTemplateStyle = objectTemplateStyleMapper.getObjectTemplateStyleById(id);
		return objectTemplateStyle;
	}

	@Transactional
	public void save(ObjectTemplateStyle objectTemplateStyle) {
		objectTemplateStyleMapper.deleteObjectTemplateStyleById(objectTemplateStyle.getStyleId());
		objectTemplateStyleMapper.insertObjectTemplateStyle(objectTemplateStyle);
	}

	@Transactional
	@Override
	public void saveTemplateStyle(Long templateId, String stypeRuleContent, String creator) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.parseObject(stypeRuleContent);
		// 样式规则内容集合
		Map<String, Map<String, String>> ruleMap = new HashMap<>();
		Set<Entry<String, Object>> jsonSet = json.entrySet();
		String componentCode = null;
		String componentStyle = null;
		for (Entry<String, Object> entry : jsonSet) {
			componentCode = entry.getKey();
			componentStyle = (String) entry.getValue();
			ruleMap.put(componentCode, (Map) JSONObject.parseObject(componentStyle));
		}
		// 加载模板
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		Template temp;
		try {
			temp = configuration.getTemplate("page_theme.ccss");
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			temp.process(ruleMap, stringWriter);
			String styleContentStr = stringWriter.toString();

			writer.flush();
			writer.close();
			if (StringUtils.isNotEmpty(styleContentStr)) {
				ObjectTemplateStyle objectTemplateStyle = new ObjectTemplateStyle();
				objectTemplateStyle.setRuleContent(stypeRuleContent.getBytes());
				objectTemplateStyle.setStyleContent(styleContentStr.getBytes());
				objectTemplateStyle.setStyleId(templateId);
				objectTemplateStyle.setCreateTime(new Date());
				objectTemplateStyle.setCreator(creator);
				save(objectTemplateStyle);
			}

		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.ObjectTemplateStyleMapper")
	public void setObjectTemplateStyleMapper(ObjectTemplateStyleMapper objectTemplateStyleMapper) {
		this.objectTemplateStyleMapper = objectTemplateStyleMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
