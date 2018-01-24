package com.glaf.form.core.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.domain.FormComponentProperty;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.domain.StuffByPageIdsModel;
import com.glaf.form.core.mapper.FormRulePropertyMapper;
import com.glaf.form.core.query.FormComponentPropertyQuery;
import com.glaf.form.core.query.FormRulePropertyQuery;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.FormParamsUtil;
import com.glaf.form.core.util.FormRuleJsonFactory;

import net.sourceforge.jtds.jdbc.ClobImpl;
import oracle.sql.CLOB;

@Service("formRulePropertyService")
@Transactional(readOnly = true)
public class FormRulePropertyServiceImpl implements FormRulePropertyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormRulePropertyMapper formRulePropertyMapper;

	@Autowired
	protected FormComponentPropertyService formComponentPropertyService;

	@Resource
	protected FormParamsUtil formParamsUtil;

	public FormRulePropertyServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formRulePropertyMapper.deleteFormRulePropertyById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formRulePropertyMapper.deleteFormRulePropertyById(id);
			}
		}
	}

	@Transactional
	public void deleteByRuleIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			formRulePropertyMapper.deleteFormRulePropertyByRuleIds(ids);
		}
	}

	public int count(FormRulePropertyQuery query) {
		return formRulePropertyMapper.getFormRulePropertyCount(query);
	}

	public List<FormRuleProperty> list(FormRulePropertyQuery query) {
		List<FormRuleProperty> list = formRulePropertyMapper.getFormRulePropertys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormRulePropertyCountByQueryCriteria(FormRulePropertyQuery query) {
		return formRulePropertyMapper.getFormRulePropertyCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormRuleProperty> getFormRulePropertysByQueryCriteria(int start, int pageSize, FormRulePropertyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormRuleProperty> rows = sqlSessionTemplate.selectList("getFormRulePropertys", query, rowBounds);
		return rows;
	}

	public FormRuleProperty getFormRuleProperty(String id) {
		if (id == null) {
			return null;
		}
		FormRuleProperty formRuleProperty = formRulePropertyMapper.getFormRulePropertyById(id);
		return formRuleProperty;
	}

	/**
	 * 获取某个页面某个控件某个规则项的规则
	 * 
	 * @param pageId
	 * @param dataRole
	 * @param propName
	 * @return
	 */
	public FormRuleProperty getPageComponentPropertyRule(String pageId, String dataRole, String propName, String id) {

		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getPageComponentPropertyRule_oracle(pageId, dataRole, propName, id);
		}

		FormRuleProperty formRuleProperty = formRulePropertyMapper.getPageComponentPropertyRule(pageId, dataRole, propName, id);
		return formRuleProperty;
	}

	@Transactional
	public void save(FormRuleProperty formRuleProperty) {
		if (formRuleProperty.getId() == null) {
			formRuleProperty.setId(UUID32.getUUID());
			formRuleProperty.setCreateDate(new Date());
			formRulePropertyMapper.insertFormRuleProperty(formRuleProperty);
		} else {
			formRulePropertyMapper.updateFormRuleProperty(formRuleProperty);
		}
	}

	/**
	 * 拆分规则属性
	 */
	public void isRuleProperties(FormRule fr) {

		/*
		 * if (StringUtils.isNotBlank(fr.getCollection())) {
		 * 
		 * JSONObject json = JSON.parseObject(fr.getCollection()); for (String
		 * type : json.keySet()) { ////System.out.println(type + " :" +
		 * json.get(type)); }
		 * 
		 * }
		 */

		String value = fr.getValue();

		if (StringUtils.isNotEmpty(value)) {

			this.deleteByRuleId(fr.getId());// 清空规则明细

			Connection connection = null;

			PreparedStatement psmt = null;

			JSONObject json = JSON.parseObject(value);

			String sql = "INSERT INTO FORM_RULE_PROPERTY (ID_,RULEID_,NAME_,VALUE_,CREATEDATE_) VALUES (?,?,?,?,?)";

			try {
				
				connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

				psmt = connection.prepareStatement(sql);
				connection.setAutoCommit(false);
				psmt.clearParameters();
				psmt.clearBatch();
				
				String val;
				for (String key : json.keySet()) {

					val = json.getString(key);

					if (StringUtils.isBlank(val)) {
						continue;
					}
					String uuid =  UUID32.getUUID();
					//System.out.println("UUID==="+uuid);
					psmt.setString(1, uuid);
					psmt.setString(2, fr.getId());
					psmt.setString(3, key);
					psmt.setString(4, val);
					psmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
					psmt.addBatch();
				}

				psmt.executeBatch();
				//psmt.close();
				connection.commit();
				logger.debug(sql);

			} catch (Exception ex) {
				ex.printStackTrace();
				logger.error("run batch [saveRuleProperties()] error", ex);
				throw new RuntimeException(ex);
			} finally {
				JdbcUtils.close(psmt);
			}
		}

	}

	/**
	 * 保存属性 拆分保存参数形参信息
	 */
	@Transactional
	public void saveComExt(FormRule fr){
		FormComponentPropertyQuery query = new FormComponentPropertyQuery();
		List<String> names = new ArrayList<String>();
		names.addAll(Arrays.asList(FormParamsUtil.FORM_RELATION));
		query.setNames(names);
		List<FormComponentProperty> pros = formComponentPropertyService.list(query);
		Map<String, String> map = new HashMap<String, String>();
		for (FormComponentProperty formComponentProperty : pros) {
			map.put(formComponentProperty.getId() + "", formComponentProperty.getName());
		}
		
		
		String value = fr.getValue();
		if (StringUtils.isNotEmpty(value)) {

			JSONObject json = JSON.parseObject(value);
			String val;
			for (String key : json.keySet()) {
				val = json.getString(key);
				if (StringUtils.isBlank(val)) {
					continue;
				}
				
				try {
					
					if (map.containsKey(key)) {
						boolean bool = formParamsUtil.init(map.get(key),
								val, fr.getId(), fr.getPageId());//TODO
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" save [formParams] error ");
				}
			}
		}

	}
	
	
	public List<Map<String, Object>> getParametersByPageId(String pageId) {
		if (StringUtils.isBlank(pageId))
			return null;

		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getStuffByPageIdAndNameLike_oracle(pageId, "%Param%");
		}
		List<Map<String, Object>> list = formRulePropertyMapper.getStuffByPageIdAndNameLike(pageId, "%Param%");

		return list;
	}

	public List<Map<String, Object>> getStuffByPageId(String pageId) {
		if (StringUtils.isBlank(pageId))
			return null;
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getStuffByPageIdAndNameLike_oracle(pageId, "%linkPage%");
		}
		List<Map<String, Object>> list = formRulePropertyMapper.getStuffByPageIdAndNameLike(pageId, "%linkPage%");
		return list;
	}
	
	@Transactional
	public void deleteByRuleId(String rid) {
		if (rid != null) {
			formRulePropertyMapper.deleteFormRulePropertyByRuleId(rid);
		}
	}

	public JSONObject getRuleJSON(String ruleId) {

		FormRulePropertyQuery query = new FormRulePropertyQuery();

		query.setRuleId(ruleId);

		List<FormRuleProperty> list = this.list(query);

		if (list != null && !list.isEmpty()) {
			JSONObject json = new JSONObject();
			for (FormRuleProperty frp : list) {
				json.put(frp.getName(), frp.getValue());
			}
			return json;
		}

		return null;
	}

	public Map<String, String> getRuleMap(String rid) {
		String cacheKey = "form_rules_property_" + rid;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_rules_property", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject obj = JSON.parseObject(text);
					return obj.toJavaObject(Map.class);
				} catch (Exception ex) {
				}
			}
		}
		
		List<Map<String, Object>> list = this.getFormRuleListByRid(rid);

		if (list != null) {
			Object name;
			Object value;
			Map<String, String> map = new HashMap<String, String>();
			boolean isOracle = org.apache.commons.codec.binary.StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());
			boolean isDm = org.apache.commons.codec.binary.StringUtils.equals(DBUtils.DM_DBMS, DBConnectionFactory.getDatabaseType());
			for (Map<String, Object> m : list) {

				name = m.get("NAME_");

				value = m.get("VALUE_");

				if (!map.containsKey("pageId")){
					if(m.get("PAGEID_")!= null){
						map.put("pageId", m.get("PAGEID_").toString());
					}else{
						map.put("pageId", m.get("pageid_").toString());
					}
				}

				if (name != null && value != null) {
					if (isOracle) {
						Class<?> className = ClassUtils.classForName("oracle.sql.CLOB");
						if (value.getClass() == className) {
							try {
								Method method = className.getMethod("stringValue", null);
								String valstr = (String) method.invoke(value, null);
								map.put(name.toString(), valstr);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (isDm) {
						Class<?> className = ClassUtils.classForName("dm.jdbc.driver.DmdbClob");
						if (value.getClass() == className) {
							try {
								Method method = className.getMethod("getCharacterStream", null);
								Reader reader = (Reader) method.invoke(value, null);
								BufferedReader r = new BufferedReader(reader);
								StringBuilder b = new StringBuilder();
								String line;
								while ((line = r.readLine()) != null) {
									b.append(line);
								}
								map.put(name.toString(), b.toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}else {
						map.put(name.toString(), value.toString());
					}
				}
			}
			if (SystemConfig.getBoolean("use_query_cache")) {
				CacheFactory.put("form_rules_property", cacheKey, JSON.toJSONString(map));
			}
			return map;
		}

		return null;
	}

	public List<Map<String, Object>> getFormRuleListByRid(String rid) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getFormRuleListByRid_oracle(rid);
		}
		if (StringUtils.equals(DBUtils.POSTGRESQL, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getFormRuleListByRid_postgresql(rid);
		}
		List<Map<String, Object>> list = formRulePropertyMapper.getFormRuleListByRid(rid);
		return list;

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
	public void setFormRulePropertyMapper(FormRulePropertyMapper formRulePropertyMapper) {
		this.formRulePropertyMapper = formRulePropertyMapper;
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
	public List<FormRuleProperty> getAttributeDatasByPageId(String pageId, String attrName) {
		return formRulePropertyMapper.getAttributeDatasByPageId(pageId, attrName);
	}

	@Override
	public FormRuleProperty getRuleByName(String pageId, String widgetId,String name) {
		return formRulePropertyMapper.getRuleByName(pageId,widgetId,name);
	}

	@Override
	public List<Map<String, Object>> getParametersByPageIds(List<String> pageIdList) {
		if (pageIdList==null || pageIdList.size()==0)
			return null;

		Map<String,Object> map = new HashMap<>();
		map.put("pageIdList", pageIdList);
		map.put("name", "%Param%");
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getStuffByPageIdsAndNameLike_oracle(map);
		}
		List<Map<String, Object>> list = formRulePropertyMapper.getStuffByPageIdsAndNameLike(map);

		return list;
	}

	@Override
	public List<Map<String, Object>> getStuffByPageIds(List<String> pageIdList) {
		if (pageIdList==null || pageIdList.size()==0)
			return null;
		Map<String,Object> map = new HashMap<>();
		map.put("pageIdList", pageIdList);
		map.put("name", "%linkPage%");
		long s = System.currentTimeMillis();
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			return formRulePropertyMapper.getStuffByPageIdsAndNameLike_oracle(map);
		}
		List<Map<String, Object>> list = formRulePropertyMapper.getStuffByPageIdsAndNameLike(map);
		//List<StuffByPageIdsModel> list2 = formRulePropertyMapper.getStuffByPageIdsAndNameLike2(map);
		return list;
	}

}
