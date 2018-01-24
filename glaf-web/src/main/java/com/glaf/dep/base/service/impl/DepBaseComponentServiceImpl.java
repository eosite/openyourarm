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
import com.glaf.dep.base.service.DepBaseComponentService;

@Service("com.glaf.dep.base.service.depBaseComponentService")
@Transactional(readOnly = true)
public class DepBaseComponentServiceImpl implements DepBaseComponentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseComponentMapper depBaseComponentMapper;

	public DepBaseComponentServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			depBaseComponentMapper.deleteDepBaseComponentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				depBaseComponentMapper.deleteDepBaseComponentById(id);
			}
		}
	}

	public int count(DepBaseComponentQuery query) {
		return depBaseComponentMapper.getDepBaseComponentCount(query);
	}

	public List<DepBaseComponent> list(DepBaseComponentQuery query) {
		List<DepBaseComponent> list = depBaseComponentMapper
				.getDepBaseComponents(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseComponentCountByQueryCriteria(
			DepBaseComponentQuery query) {
		return depBaseComponentMapper.getDepBaseComponentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseComponent> getDepBaseComponentsByQueryCriteria(
			int start, int pageSize, DepBaseComponentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseComponent> rows = sqlSessionTemplate.selectList(
				"getDepBaseComponents", query, rowBounds);
		return rows;
	}

	public DepBaseComponent getDepBaseComponent(String id) {
		if (id == null) {
			return null;
		}
		DepBaseComponent depBaseComponent = depBaseComponentMapper
				.getDepBaseComponentById(id);
		return depBaseComponent;
	}

	@Transactional
	public void save(DepBaseComponent depBaseComponent) {
		if (StringUtils.isEmpty(depBaseComponent.getId())) {
			depBaseComponent.setId(UUID32.getUUID());
			depBaseComponentMapper.insertDepBaseComponent(depBaseComponent);
		} else {
			depBaseComponentMapper.updateDepBaseComponent(depBaseComponent);
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseComponentMapper")
	public void setDepBaseComponentMapper(
			DepBaseComponentMapper depBaseComponentMapper) {
		this.depBaseComponentMapper = depBaseComponentMapper;
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
