package com.glaf.form.core.service.impl;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.PageUrlConversionService;
import com.glaf.form.core.util.PageUrlConversionJsonFactory;

@Service("pageUrlConversionService")
@Transactional(readOnly = true)
public class PageUrlConversionServiceImpl implements PageUrlConversionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PageUrlConversionMapper pageUrlConversionMapper;

	public PageUrlConversionServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<PageUrlConversion> list) {
		CacheFactory.clear("form_url_conversion");
		for (PageUrlConversion pageUrlConversion : list) {
			if (StringUtils.isEmpty(pageUrlConversion.getId())) {
				pageUrlConversion.setId(idGenerator.getNextId("PAGE_URL_CONVERSION"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			pageUrlConversionMapper.bulkInsertPageUrlConversion_oracle(list);
		} else {
			pageUrlConversionMapper.bulkInsertPageUrlConversion(list);
		}
	}

	public int count(PageUrlConversionQuery query) {
		return pageUrlConversionMapper.getPageUrlConversionCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		CacheFactory.clear("form_url_conversion");
		if (id != null) {
			pageUrlConversionMapper.deletePageUrlConversionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		CacheFactory.clear("form_url_conversion");
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				pageUrlConversionMapper.deletePageUrlConversionById(id);
			}
		}
	}

	public List<PageUrlConversion> getAllList() {
		String cacheKey = "form_url_conversions";
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_url_conversion", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONArray array = JSON.parseArray(text);
					return PageUrlConversionJsonFactory.arrayToList(array);
				} catch (Exception ex) {
				}
			}
		}

		PageUrlConversionQuery query = new PageUrlConversionQuery();
		query.setLocked(0);
		List<PageUrlConversion> list = pageUrlConversionMapper.getPageUrlConversions(query);
		if (list != null) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONArray array = PageUrlConversionJsonFactory.listToArray(list);
				CacheFactory.put("form_url_conversion", cacheKey, array.toJSONString());
			}
		}
		return list;
	}

	public PageUrlConversion getPageUrlConversion(String id) {
		if (id == null) {
			return null;
		}
		PageUrlConversion pageUrlConversion = pageUrlConversionMapper.getPageUrlConversionById(id);
		return pageUrlConversion;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getPageUrlConversionCountByQueryCriteria(PageUrlConversionQuery query) {
		return pageUrlConversionMapper.getPageUrlConversionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<PageUrlConversion> getPageUrlConversionsByQueryCriteria(int start, int pageSize,
			PageUrlConversionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<PageUrlConversion> rows = sqlSessionTemplate.selectList("getPageUrlConversions", query, rowBounds);
		return rows;
	}

	public List<PageUrlConversion> list(PageUrlConversionQuery query) {
		List<PageUrlConversion> list = pageUrlConversionMapper.getPageUrlConversions(query);
		return list;
	}

	@Transactional
	public void save(PageUrlConversion pageUrlConversion) {
		CacheFactory.clear("form_url_conversion");
		if (StringUtils.isEmpty(pageUrlConversion.getId())) {
			pageUrlConversion.setId(idGenerator.getNextId("PAGE_URL_CONVERSION"));
			pageUrlConversionMapper.insertPageUrlConversion(pageUrlConversion);
		} else {
			pageUrlConversionMapper.updatePageUrlConversion(pageUrlConversion);
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

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setPageUrlConversionMapper(PageUrlConversionMapper pageUrlConversionMapper) {
		this.pageUrlConversionMapper = pageUrlConversionMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
