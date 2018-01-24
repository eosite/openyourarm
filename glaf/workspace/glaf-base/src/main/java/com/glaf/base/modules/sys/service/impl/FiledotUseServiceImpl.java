package com.glaf.base.modules.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.mapper.MyFiledotUseMapper;
import com.glaf.base.modules.sys.model.FiledotUse;
import com.glaf.base.modules.sys.query.FiledotUseQuery;
import com.glaf.base.modules.sys.service.FiledotUseService;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;

@Service("com.glaf.base.modules.sys.service.filedotUseService")
@Transactional(readOnly = true)
public class FiledotUseServiceImpl implements FiledotUseService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected MyFiledotUseMapper myFiledotUseMapper;

	public FiledotUseServiceImpl() {

	}

	public int count(FiledotUseQuery query) {
		return myFiledotUseMapper.getFiledotUseCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			myFiledotUseMapper.deleteFiledotUseById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> fileIDs) {
		if (fileIDs != null && !fileIDs.isEmpty()) {
			for (String id : fileIDs) {
				myFiledotUseMapper.deleteFiledotUseById(id);
			}
		}
	}

	public FiledotUse getFiledotUseById(String id) {
		if (id == null) {
			return null;
		}
		FiledotUse filedotUse = myFiledotUseMapper.getFiledotUseById(id);
		return filedotUse;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFiledotUseCountByQueryCriteria(FiledotUseQuery query) {
		return myFiledotUseMapper.getFiledotUseCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FiledotUse> getFiledotUsesByQueryCriteria(int start,
			int pageSize, FiledotUseQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FiledotUse> rows = sqlSessionTemplate.selectList("getFiledotUses",
				query, rowBounds);
		return rows;
	}

	public List<FiledotUse> list(FiledotUseQuery query) {
		List<FiledotUse> list = myFiledotUseMapper.getFiledotUses(query);
		return list;
	}

	@Transactional
	public void save(FiledotUse filedotUse) {
		if (StringUtils.isEmpty(filedotUse.getFileID())) {
			filedotUse.setFileID(idGenerator.getNextId("DOTUSE"));
			filedotUse.setCreateTime(new Date());
			myFiledotUseMapper.insertFiledotUse(filedotUse);
		} else {
			myFiledotUseMapper.updateFiledotUse(filedotUse);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource 
	public void setMyFiledotUseMapper(MyFiledotUseMapper myFiledotUseMapper) {
		this.myFiledotUseMapper = myFiledotUseMapper;
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

}
