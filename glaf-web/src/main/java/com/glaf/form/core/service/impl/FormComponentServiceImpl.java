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

package com.glaf.form.core.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.id.*;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.*;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormByteArrayService;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.util.FormComponentJsonFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("formComponentService")
@Transactional(readOnly = true)
public class FormComponentServiceImpl implements FormComponentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormComponentMapper formComponentMapper;

	protected FormByteArrayService formByteArrayService;

	public FormComponentServiceImpl() {

	}

	public int count(FormComponentQuery query) {
		return formComponentMapper.getFormComponentCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formComponentMapper.deleteFormComponentById(id);
		}
	}

	@Override
	public List<FormComponent> getComponentPropertyList(FormComponentQuery query) {
		List<FormComponent> list = formComponentMapper.getComponentPropertys(query);
		return list;
	}

	public FormComponent getFormComponent(Long id) {
		if (id == null) {
			return null;
		}

		String cacheKey = "form_component_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_component", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject result = JSON.parseObject(text);
					return FormComponentJsonFactory.jsonToObject(result);
				} catch (Exception ex) {
				}
			}
		}

		FormComponent formComponent = formComponentMapper.getFormComponentById(id);
		if (formComponent != null) {
			CacheFactory.put("form_component", cacheKey, formComponent.toJsonObject().toJSONString());
		}
		return formComponent;
	}

	public FormComponent getFormComponentByDataRole(String dataRole) {
		FormComponentQuery query = new FormComponentQuery();
		query.dataRole(dataRole);
		List<FormComponent> list = formComponentMapper.getFormComponents(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormComponentCountByQueryCriteria(FormComponentQuery query) {
		return formComponentMapper.getFormComponentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormComponent> getFormComponentsByQueryCriteria(int start, int pageSize, FormComponentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormComponent> rows = sqlSessionTemplate.selectList("getFormComponents", query, rowBounds);
		return rows;
	}

	public List<FormComponent> list(FormComponentQuery query) {
		List<FormComponent> list = formComponentMapper.getFormComponents(query);
		return list;
	}

	@Transactional
	public void save(FormComponent formComponent) {
		if (formComponent.getJsBytes() != null) {
			formComponent.setJsPath("/deploy/form/js/" + formComponent.getId() + ".js");
		}
		if (formComponent.getImageBytes() != null) {
			formComponent.setImagePath("/deploy/form/component/" + formComponent.getId() + "."
					+ FileUtils.getFileExt(formComponent.getImageFileName()));
		}

		if (formComponent.getSmallImageBytes() != null) {
			formComponent.setSmallImagePath("/deploy/form/component/" + formComponent.getId() + "_small."
					+ FileUtils.getFileExt(formComponent.getSmallImageFileName()));
		}

		if (formComponent.getMediumImageBytes() != null) {
			formComponent.setMediumImagePath("/deploy/form/component/" + formComponent.getId() + "_medium."
					+ FileUtils.getFileExt(formComponent.getMediumImageFileName()));
		}

		if (formComponent.getId() == 0) {
			if (StringUtils.isNotEmpty(formComponent.getDataRole())) {
				FormComponent m = this.getFormComponentByDataRole(formComponent.getDataRole());
				if (m != null) {
					throw new RuntimeException(formComponent.getDataRole() + " is exists");
				}
			}

			formComponent.setId(idGenerator.nextId("FORM_COMPONENT"));
			formComponent.setCreateDate(new Date());
			formComponent.setVersion(1);

			formComponentMapper.insertFormComponent(formComponent);
		} else {
			if (StringUtils.isNotEmpty(formComponent.getDataRole())) {
				FormComponent m = this.getFormComponentByDataRole(formComponent.getDataRole());
				if (m != null && formComponent.getId() != m.getId()) {
					throw new RuntimeException(formComponent.getDataRole() + " is exists");
				}
			}

			formComponent.setVersion(formComponent.getVersion() + 1);
			formComponent.setUpdateDate(new Date());
			formComponentMapper.updateFormComponent(formComponent);

			String cacheKey = "form_component_" + formComponent.getId();
			CacheFactory.remove("form_component", cacheKey);
		}

		if (formComponent.getImageBytes() != null) {
			FormByteArray formByteArray = new FormByteArray();
			formByteArray.setId(UUID32.getUUID());
			formByteArray.setDeploymentId(formComponent.getDeploymentId());
			formByteArray.setCreateBy(formComponent.getCreateBy());
			formByteArray.setCreateDate(new Date());
			formByteArray.setBytes(formComponent.getImageBytes());
			formByteArray.setKey("form_component_img_" + formComponent.getId());
			formByteArray.setName(formComponent.getImagePath());
			formByteArray.setType("FORM_COMPONENT_IMG");
			formByteArray.setStatus(9);
			formByteArrayService.save(formByteArray);

			String filename = SystemProperties.getAppPath() + formComponent.getImagePath();

			try {
				FileUtils.save(filename, formComponent.getImageBytes());

				logger.debug("save image success");
			} catch (Exception ex) {
				logger.error("save image error", ex);
			}
		}

		if (formComponent.getSmallImageBytes() != null) {
			FormByteArray formByteArray = new FormByteArray();
			formByteArray.setId(UUID32.getUUID());
			formByteArray.setDeploymentId(formComponent.getDeploymentId());
			formByteArray.setCreateBy(formComponent.getCreateBy());
			formByteArray.setCreateDate(new Date());
			formByteArray.setBytes(formComponent.getSmallImageBytes());
			formByteArray.setKey("form_component_small_img_" + formComponent.getId());
			formByteArray.setName(formComponent.getSmallImagePath());
			formByteArray.setType("FORM_COMPONENT_SMALL_IMG");
			formByteArray.setStatus(9);
			formByteArrayService.save(formByteArray);

			String filename = SystemProperties.getAppPath() + formComponent.getSmallImagePath();

			try {
				FileUtils.save(filename, formComponent.getSmallImageBytes());

				logger.debug("save small image success");
			} catch (Exception ex) {
				logger.error("save small image error", ex);
			}
		}

		if (formComponent.getMediumImageBytes() != null) {
			FormByteArray formByteArray = new FormByteArray();
			formByteArray.setId(UUID32.getUUID());
			formByteArray.setDeploymentId(formComponent.getDeploymentId());
			formByteArray.setCreateBy(formComponent.getCreateBy());
			formByteArray.setCreateDate(new Date());
			formByteArray.setBytes(formComponent.getMediumImageBytes());
			formByteArray.setKey("form_component_medium_img_" + formComponent.getId());
			formByteArray.setName(formComponent.getMediumImagePath());
			formByteArray.setType("FORM_COMPONENT_MEDIUM_IMG");
			formByteArray.setStatus(9);
			formByteArrayService.save(formByteArray);

			String filename = SystemProperties.getAppPath() + formComponent.getMediumImagePath();

			try {
				FileUtils.save(filename, formComponent.getMediumImageBytes());

				logger.debug("save medium image success");
			} catch (Exception ex) {
				logger.error("save medium image error", ex);
			}
		}

		if (formComponent.getJsBytes() != null) {
			FormByteArray formByteArray = new FormByteArray();
			formByteArray.setId(UUID32.getUUID());
			formByteArray.setDeploymentId(formComponent.getDeploymentId());
			formByteArray.setCreateBy(formComponent.getCreateBy());
			formByteArray.setCreateDate(new Date());
			formByteArray.setBytes(formComponent.getJsBytes());
			formByteArray.setKey("form_component_js_" + formComponent.getId());
			formByteArray.setName(formComponent.getJsPath());
			formByteArray.setType("FORM_COMPONENT_JS");
			formByteArray.setStatus(9);
			formByteArrayService.save(formByteArray);

			String filename = SystemProperties.getAppPath() + formComponent.getJsPath();

			try {
				FileUtils.save(filename, formComponent.getJsBytes());

				logger.debug("save javascript success");
			} catch (Exception ex) {
				logger.error("save javascript error", ex);
			}
		}
	}

	public void createRenderjs(String webRootPath) throws Exception {
		List<FormComponentTemplate> templateRenderList = formComponentMapper.getFormComponentTemplates();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateRenderList", templateRenderList);
		// 加载模板
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		Template temp;
		try {
			temp = configuration.getTemplate("comp_render_template.cjs");
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			temp.process(map, stringWriter);
			String renderJsContent=stringWriter.toString();
			String fileName=webRootPath+"/scripts/designer/comp_render_template.js";
			FileUtils.save(fileName, renderJsContent.getBytes("UTF-8"));
			writer.flush();
			writer.close();
		} catch (IOException | TemplateException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
	
	public void createStyleTemplate(String webRootPath,String contextPath) throws Exception {
		List<FormComponentStyleTemplate> styleTemplateList = formComponentMapper.getFormComponentStyleTemplates();
		// 加载模板
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/");
		List<FormComponentStyleTemplate> compStyleTemplateList=null;
		String componentId=null;
		String preComponentId=null;
		String preDataRole=null;
		String savePath=null;
		//模板生成标识
		boolean generatorFlag=false;
		Template temp = configuration.getTemplate("comp_li_template.chtml");
			for(FormComponentStyleTemplate componentStyleTemplate:styleTemplateList){
				generatorFlag=false;
				componentId=componentStyleTemplate.getComponentId();
				if(preComponentId==null||!preComponentId.equals(componentId)){
					if(preComponentId!=null){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("templateList", compStyleTemplateList);
						map.put("contextPath", contextPath);
						//生成模板
						savePath=webRootPath+"/scripts/designer/components/templates/"+preDataRole+"/"+preDataRole+".html";
						generatorCompLiTemplateHtml(temp,savePath,map);
						generatorFlag=true;
					}
					compStyleTemplateList=new ArrayList<FormComponentStyleTemplate>();
				}
				compStyleTemplateList.add(componentStyleTemplate);
				preDataRole=componentStyleTemplate.getDataRole();
				preComponentId=componentStyleTemplate.getComponentId();
			}
			//最后一个控件模板未生成
			if(!generatorFlag){
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("templateList", compStyleTemplateList);
				map.put("contextPath", contextPath);
				//生成模板
				savePath=webRootPath+"/scripts/designer/components/templates/"+preDataRole+"/"+preDataRole+".html";
				generatorCompLiTemplateHtml(temp,savePath,map);
				generatorFlag=true;
			}
 
	}
	
	public void generatorCompLiTemplateHtml(Template temp,String savePath,Map<String, Object> map) throws Exception{
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		try {
			temp.process(map, stringWriter);
			String htmlTemplateContent=stringWriter.toString();
			FileUtils.save(savePath, htmlTemplateContent.getBytes("UTF-8"));
			writer.flush();
			writer.close();
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("生成模板出错:"+e.getMessage());
			throw e;
		}
		
	}
	
	public void createStyleTemplateContent(String webRootPath) throws UnsupportedEncodingException{
		//获取模板总数
		int templateCount=formComponentMapper.getFormComponentStyleTemplateContentCount();
		//分页处理
		int totalPage=(templateCount+10-1)/10;  
		int start=0;
		List<FormComponentStyleTemplate> rows =null;
		String dataRole=null;
		String id=null;
		String template=null;
		String savePath=null;
		for(int i=0;i<totalPage;i++){
			start = i * 10;
			RowBounds rowBounds = new RowBounds(start, 10);
			rows = sqlSessionTemplate.selectList(
					"getFormComponentStyleTemplates", null, rowBounds);
			for(FormComponentStyleTemplate formComponentStyleTemplate:rows){
				dataRole=formComponentStyleTemplate.getDataRole();
				id=formComponentStyleTemplate.getId();
				template=formComponentStyleTemplate.getTemplate();
				savePath=webRootPath+"/scripts/designer/components/templates/"+dataRole+"/"+dataRole+"_"+id+".html";
				FileUtils.save(savePath, template.getBytes("UTF-8"));
			}
		}
		
	}
	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormByteArrayService(FormByteArrayService formByteArrayService) {
		this.formByteArrayService = formByteArrayService;
	}

	@javax.annotation.Resource
	public void setFormComponentMapper(FormComponentMapper formComponentMapper) {
		this.formComponentMapper = formComponentMapper;
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
	public void updateBaseComp(Long id, int baseComp) {
		this.formComponentMapper.updateBaseComp(id, baseComp);
	}

}
