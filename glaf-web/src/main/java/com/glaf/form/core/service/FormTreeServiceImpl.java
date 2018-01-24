package com.glaf.form.core.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.form.core.domain.FormTree;
import com.glaf.form.core.mapper.FormTreeMapper;
import com.glaf.form.core.query.FormTreeQuery;

@Service("com.glaf.form.core.service.formTreeService")
@Transactional(readOnly = true)
public class FormTreeServiceImpl implements FormTreeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormTreeMapper formTreeMapper;

	public FormTreeServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formTreeMapper.deleteFormTreeById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				formTreeMapper.deleteFormTreeById(id);
			}
		}
	}

	public int count(FormTreeQuery query) {
		return formTreeMapper.getFormTreeCount(query);
	}

	public List<FormTree> list(FormTreeQuery query) {
		List<FormTree> list = formTreeMapper.getFormTrees(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormTreeCountByQueryCriteria(FormTreeQuery query) {
		return formTreeMapper.getFormTreeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormTree> getFormTreesByQueryCriteria(int start, int pageSize, FormTreeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormTree> rows = sqlSessionTemplate.selectList("getFormTrees", query, rowBounds);
		return rows;
	}

	@Override
	public List<FormTree> getTreeByCode(String code, Boolean includeSelf) {
		FormTreeQuery query = new FormTreeQuery();
		query.setCode(code);
		List<FormTree> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			query = new FormTreeQuery();
			query.setTreeIdLike(list.get(0).getTreeId() + (includeSelf ? "" : "|"));
			list = this.list(query);
		}
		return list;
	}

	public FormTree getFormTree(Long id) {
		if (id == null) {
			return null;
		}
		FormTree formTree = formTreeMapper.getFormTreeById(id);
		return formTree;
	}

	@Transactional
	public void save(FormTree formTree) {
		if (formTree.getId() == null) {
			formTree.setId(idGenerator.nextId("FORM_TREE"));
			formTree.setCreateDate(new Date());
			formTree.setLocked(0);
			if (StringUtils.isNotEmpty(formTree.getTreeId())) {
				formTree.setTreeId(formTree.getTreeId() + "|" + formTree.getId());
			}
			formTreeMapper.insertFormTree(formTree);
		} else {
			formTree.setUpdateDate(new Date());
			formTreeMapper.updateFormTree(formTree);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormTreeMapper")
	public void setFormTreeMapper(FormTreeMapper formTreeMapper) {
		this.formTreeMapper = formTreeMapper;
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
