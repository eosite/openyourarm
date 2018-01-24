package com.glaf.dep.report.service;

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

import com.glaf.dep.report.mapper.*;
import com.glaf.dep.report.domain.*;
import com.glaf.dep.report.query.*;

@Service("com.glaf.dep.report.service.depReportCategoryService")
@Transactional(readOnly = true)
public class DepReportCategoryServiceImpl implements DepReportCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportCategoryMapper depReportCategoryMapper;

	public DepReportCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			depReportCategoryMapper.deleteDepReportCategoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				depReportCategoryMapper.deleteDepReportCategoryById(id);
			}
		}
	}

	public int count(DepReportCategoryQuery query) {
		return depReportCategoryMapper.getDepReportCategoryCount(query);
	}

	public List<DepReportCategory> list(DepReportCategoryQuery query) {
		List<DepReportCategory> list = depReportCategoryMapper
				.getDepReportCategorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepReportCategoryCountByQueryCriteria(
			DepReportCategoryQuery query) {
		return depReportCategoryMapper.getDepReportCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportCategory> getDepReportCategorysByQueryCriteria(
			int start, int pageSize, DepReportCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportCategory> rows = sqlSessionTemplate.selectList(
				"getDepReportCategorys", query, rowBounds);
		return rows;
	}

	public DepReportCategory getDepReportCategory(Long id) {
		if (id == null) {
			return null;
		}
		DepReportCategory depReportCategory = depReportCategoryMapper
				.getDepReportCategoryById(id);
		return depReportCategory;
	}

	@Transactional
	public void save(DepReportCategory depReportCategory) {
		if (depReportCategory.getId() == null) {
			depReportCategory.setId(idGenerator.nextId("DEP_REPORT_CATEGORY"));
			if (depReportCategory.getPId() > 0L) {
				DepReportCategory parentCategory = this
						.getDepReportCategory(depReportCategory.getPId());
				if (parentCategory != null) {
					depReportCategory.setTreeId(parentCategory.getTreeId()
							+ "|" + depReportCategory.getId());
				}else{
					depReportCategory.setTreeId(depReportCategory.getPId() + "|"
							+ depReportCategory.getId());
				}
			} else {
				depReportCategory.setTreeId(depReportCategory.getPId() + "|"
						+ depReportCategory.getId());
			}
			depReportCategory.setDelFlag("0");
			depReportCategoryMapper.insertDepReportCategory(depReportCategory);
		} else {
			depReportCategoryMapper.updateDepReportCategory(depReportCategory);
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportCategoryMapper")
	public void setDepReportCategoryMapper(
			DepReportCategoryMapper depReportCategoryMapper) {
		this.depReportCategoryMapper = depReportCategoryMapper;
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
