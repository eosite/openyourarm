package com.glaf.report.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.report.core.domain.SysReportTemplate;
import com.glaf.report.core.mapper.SysReportTemplateMapper;
import com.glaf.report.core.query.SysReportTemplateQuery;

@Service("com.glaf.report.service.sysReportTemplateService")
@Transactional(readOnly = true)
public class SysReportTemplateServiceImpl implements SysReportTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysReportTemplateMapper sysReportTemplateMapper;

	public SysReportTemplateServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			sysReportTemplateMapper.deleteSysReportTemplateById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				sysReportTemplateMapper.deleteSysReportTemplateById(id);
			}
		}
	}

	public int count(SysReportTemplateQuery query) {
		return sysReportTemplateMapper.getSysReportTemplateCount(query);
	}

	public List<SysReportTemplate> list(SysReportTemplateQuery query) {
		List<SysReportTemplate> list = sysReportTemplateMapper
				.getSysReportTemplates(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSysReportTemplateCountByQueryCriteria(
			SysReportTemplateQuery query) {
		return sysReportTemplateMapper.getSysReportTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SysReportTemplate> getSysReportTemplatesByQueryCriteria(
			int start, int pageSize, SysReportTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SysReportTemplate> rows = sqlSessionTemplate.selectList(
				"getSysReportTemplates", query, rowBounds);
		return rows;
	}

	public SysReportTemplate getSysReportTemplate(String id) {
		if (id == null) {
			return null;
		}
		SysReportTemplate sysReportTemplate = sysReportTemplateMapper
				.getSysReportTemplateById(id);
		return sysReportTemplate;
	}

	@Transactional
	public void save(SysReportTemplate sysReportTemplate) {
		if (StringUtils.isEmpty(sysReportTemplate.getId())) {
			sysReportTemplate.setId(idGenerator.getNextId("SYSREPORTTEMPLATE"));
			// sysReportTemplate.setCreateDate(new Date());
			// sysReportTemplate.setDeleteFlag(0);
			sysReportTemplateMapper.insertSysReportTemplate(sysReportTemplate);
		} else {
			sysReportTemplateMapper.updateSysReportTemplate(sysReportTemplate);
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

	@javax.annotation.Resource(name = "com.glaf.report.mapper.SysReportTemplateMapper")
	public void setSysReportTemplateMapper(
			SysReportTemplateMapper sysReportTemplateMapper) {
		this.sysReportTemplateMapper = sysReportTemplateMapper;
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
