package com.glaf.dep.base.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.dep.base.mapper.*;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.DepBaseUIService;

@Service("com.glaf.dep.base.service.depBaseUIService")
@Transactional(readOnly = true)
public class DepBaseUIServiceImpl implements DepBaseUIService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseUIMapper depBaseUIMapper;

	public DepBaseUIServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			depBaseUIMapper.deleteDepBaseUIById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				depBaseUIMapper.deleteDepBaseUIById(id);
			}
		}
	}

	public int count(DepBaseUIQuery query) {
		return depBaseUIMapper.getDepBaseUICount(query);
	}

	public List<DepBaseUI> list(DepBaseUIQuery query) {
		List<DepBaseUI> list = depBaseUIMapper.getDepBaseUIs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseUICountByQueryCriteria(DepBaseUIQuery query) {
		return depBaseUIMapper.getDepBaseUICount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseUI> getDepBaseUIsByQueryCriteria(int start,
			int pageSize, DepBaseUIQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseUI> rows = sqlSessionTemplate.selectList("getDepBaseUIs",
				query, rowBounds);
		return rows;
	}

	public DepBaseUI getDepBaseUI(String id) {
		if (id == null) {
			return null;
		}
		DepBaseUI depBaseUI = depBaseUIMapper.getDepBaseUIById(id);
		return depBaseUI;
	}

	@Transactional
	public void save(DepBaseUI depBaseUI) {
		if (StringUtils.isEmpty(depBaseUI.getId())) {
			depBaseUI.setId(UUID32.getUUID());
			depBaseUIMapper.insertDepBaseUI(depBaseUI);
		} else {
			depBaseUIMapper.updateDepBaseUI(depBaseUI);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate
					.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseUIMapper")
	public void setDepBaseUIMapper(DepBaseUIMapper depBaseUIMapper) {
		this.depBaseUIMapper = depBaseUIMapper;
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
