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

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.dao.*;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormByteArrayService;
import com.glaf.form.core.service.FormLayoutService;
import com.glaf.form.core.util.ScaleImage;

@Service("formLayoutService")
@Transactional(readOnly = true)
public class FormLayoutServiceImpl implements FormLayoutService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormLayoutMapper formLayoutMapper;

	protected FormByteArrayService formByteArrayService;

	public FormLayoutServiceImpl() {

	}

	public int count(FormLayoutQuery query) {
		return formLayoutMapper.getFormLayoutCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formLayoutMapper.deleteFormLayoutById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formLayoutMapper.deleteFormLayoutById(id);
			}
		}
	}

	public FormLayout getFormLayout(String id) {
		if (id == null) {
			return null;
		}
		FormLayout formLayout = formLayoutMapper.getFormLayoutById(id);
		return formLayout;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormLayoutCountByQueryCriteria(FormLayoutQuery query) {
		return formLayoutMapper.getFormLayoutCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormLayout> getFormLayoutsByQueryCriteria(int start,
			int pageSize, FormLayoutQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormLayout> rows = sqlSessionTemplate.selectList("getFormLayouts",
				query, rowBounds);
		return rows;
	}

	public List<FormLayout> list(FormLayoutQuery query) {
		List<FormLayout> list = formLayoutMapper.getFormLayouts(query);
		return list;
	}

	@Transactional
	public void save(FormLayout layout) {
		if (layout.getBytes() != null) {
			layout.setImageFileId("form_layout_" + layout.getId());
			layout.setImagePath("/deploy/form/layout/" + layout.getId() + "."
					+ FileUtils.getFileExt(layout.getImageFileName()));
			layout.setSmallImagePath("/deploy/form/layout/" + layout.getId()
					+ "_small."
					+ FileUtils.getFileExt(layout.getImageFileName()));
			layout.setMediumImagePath("/deploy/form/layout/" + layout.getId()
					+ "_medium."
					+ FileUtils.getFileExt(layout.getImageFileName()));
		}

		if (StringUtils.isEmpty(layout.getId())) {
			layout.setId(idGenerator.getNextId());
			layout.setCreateDate(new Date());
			formLayoutMapper.insertFormLayout(layout);
		} else {
			formLayoutMapper.updateFormLayout(layout);
		}

		if (layout.getBytes() != null) {
			FormByteArray formByteArray = new FormByteArray();
			formByteArray.setId(UUID32.getUUID());
			formByteArray.setDeploymentId(layout.getDeploymentId());
			formByteArray.setCreateBy(layout.getCreateBy());
			formByteArray.setCreateDate(new Date());
			formByteArray.setBytes(layout.getBytes());
			formByteArray.setKey("form_layout_" + layout.getId());
			formByteArray.setName(layout.getName());
			formByteArray.setType("FORM_LAYOUT");
			formByteArray.setStatus(9);
			formByteArrayService.save(formByteArray);

			String filename = SystemProperties.getAppPath()
					+ layout.getImagePath();
			String filename_1 = SystemProperties.getAppPath()
					+ layout.getSmallImagePath();
			String filename_2 = SystemProperties.getAppPath()
					+ layout.getMediumImagePath();

			ScaleImage img = new ScaleImage();
			try {
				FileUtils.save(filename, layout.getBytes());
				img.saveImageAsJPG(filename, filename_1, 220, 120);
				img.saveImageAsJPG(filename, filename_2, 440, 210);
				logger.debug("save image success");
			} catch (Exception ex) {
				logger.error("save image error", ex);
			}
		}
	}

	/**
	 * 保存多条记录
	 * 
	 * @return
	 */
	@Transactional
	public void saveAll(List<FormLayout> formLayouts) {
		FormLayoutQuery query = new FormLayoutQuery();
		List<FormLayout> rows = this.list(query);
		Map<String, FormLayout> layoutMap = new HashMap<String, FormLayout>();
		if (rows != null && !rows.isEmpty()) {
			for (FormLayout layout : rows) {
				layoutMap.put(layout.getName(), layout);
			}
		}
		if (formLayouts != null && !formLayouts.isEmpty()) {
			for (FormLayout layout : formLayouts) {
				if (layoutMap.get(layout.getName()) == null) {
					layout.setId(idGenerator.getNextId("FORM_LAYOUT"));
					layout.setCreateDate(new Date());
					formLayoutMapper.insertFormLayout(layout);
				} else {
					formLayoutMapper.updateFormLayout(layout);
				}
			}
		}
	}

	@javax.annotation.Resource
	public void setFormByteArrayService(
			FormByteArrayService formByteArrayService) {
		this.formByteArrayService = formByteArrayService;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormLayoutMapper(FormLayoutMapper formLayoutMapper) {
		this.formLayoutMapper = formLayoutMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
