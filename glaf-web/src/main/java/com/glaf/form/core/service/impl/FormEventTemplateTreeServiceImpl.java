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
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormEventTemplateTreeService;

@Service("com.glaf.form.core.service.formEventTemplateTreeService")
@Transactional(readOnly = true)
public class FormEventTemplateTreeServiceImpl implements FormEventTemplateTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormEventTemplateTreeMapper formEventTemplateTreeMapper;

	public FormEventTemplateTreeServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormEventTemplateTree> list) {
		for (FormEventTemplateTree formEventTemplateTree : list) {
			if (StringUtils.isEmpty(formEventTemplateTree.getId())) {
				formEventTemplateTree.setId(idGenerator.getNextId("FORM_EVENT_TEMPLATE_TREE"));
			}
		}

		int batch_size = 100;
		List<FormEventTemplateTree> rows = new ArrayList<FormEventTemplateTree>(batch_size);

		for (FormEventTemplateTree bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					//formEventTemplateTreeMapper.bulkInsertFormEventTemplateTree_oracle(list);
				} else {
					//formEventTemplateTreeMapper.bulkInsertFormEventTemplateTree(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//formEventTemplateTreeMapper.bulkInsertFormEventTemplateTree_oracle(list);
			} else {
				//formEventTemplateTreeMapper.bulkInsertFormEventTemplateTree(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formEventTemplateTreeMapper.deleteFormEventTemplateTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formEventTemplateTreeMapper.deleteFormEventTemplateTreeById(id);
			}
		}
	}

	public int count(FormEventTemplateTreeQuery query) {
		return formEventTemplateTreeMapper.getFormEventTemplateTreeCount(query);
	}

	public List<FormEventTemplateTree> list(FormEventTemplateTreeQuery query) {
		List<FormEventTemplateTree> list = formEventTemplateTreeMapper.getFormEventTemplateTrees(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormEventTemplateTreeCountByQueryCriteria(FormEventTemplateTreeQuery query) {
		return formEventTemplateTreeMapper.getFormEventTemplateTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormEventTemplateTree> getFormEventTemplateTreesByQueryCriteria(int start, int pageSize, FormEventTemplateTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormEventTemplateTree> rows = sqlSessionTemplate.selectList("getFormEventTemplateTrees", query, rowBounds);
		return rows;
	}

	public FormEventTemplateTree getFormEventTemplateTree(String id) {
		if (id == null) {
			return null;
		}
		FormEventTemplateTree formEventTemplateTree = formEventTemplateTreeMapper.getFormEventTemplateTreeById(id);
		return formEventTemplateTree;
	}

	@Transactional
	public void save(FormEventTemplateTree formEventTemplateTree) {
		if (StringUtils.isEmpty(formEventTemplateTree.getId())) {
			formEventTemplateTree.setId(idGenerator.getNextId("FORM_EVENT_TEMPLATE_TREE"));
			Integer indexId = idGenerator.nextId("FORM_EVENT_TEMPLATE_TREE", "INDEXID_").intValue();
			formEventTemplateTree.setIndexId(indexId);
			String treeid = formEventTemplateTree.getTreeId();
			formEventTemplateTree.setTreeId((StringUtils.isEmpty(treeid)?"":treeid)+indexId+"|");
			// formEventTemplateTree.setCreateDate(new Date());
			// formEventTemplateTree.setDeleteFlag(0);
			formEventTemplateTreeMapper.insertFormEventTemplateTree(formEventTemplateTree);
		} else {
			formEventTemplateTreeMapper.updateFormEventTemplateTree(formEventTemplateTree);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormEventTemplateTreeMapper")
	public void setFormEventTemplateTreeMapper(FormEventTemplateTreeMapper formEventTemplateTreeMapper) {
		this.formEventTemplateTreeMapper = formEventTemplateTreeMapper;
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
