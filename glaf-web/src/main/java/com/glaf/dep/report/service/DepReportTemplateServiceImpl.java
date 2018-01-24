package com.glaf.dep.report.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.datamgr.jdbc.Crud;
import com.glaf.datamgr.jdbc.JdbcInsert;
import com.glaf.dep.report.domain.DepReportCell;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.mapper.DepReportTemplateMapper;
import com.glaf.dep.report.query.DepReportTemplateQuery;
import com.glaf.form.rule.Global;
import com.glaf.platforms.rule.model.cell.PageCellModel;

@Service("com.glaf.dep.report.service.depReportTemplateService")
@Transactional(readOnly = true)
public class DepReportTemplateServiceImpl implements DepReportTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepReportTemplateMapper depReportTemplateMapper;

	public DepReportTemplateServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			depReportTemplateMapper.deleteDepReportTemplateById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				depReportTemplateMapper.deleteDepReportTemplateById(id);
			}
		}
	}

	public int count(DepReportTemplateQuery query) {
		return depReportTemplateMapper.getDepReportTemplateCount(query);
	}

	public List<DepReportTemplate> list(DepReportTemplateQuery query) {
		List<DepReportTemplate> list = depReportTemplateMapper.getDepReportTemplates(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepReportTemplateCountByQueryCriteria(DepReportTemplateQuery query) {
		return depReportTemplateMapper.getDepReportTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepReportTemplate> getDepReportTemplatesByQueryCriteria(int start, int pageSize,
			DepReportTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepReportTemplate> rows = sqlSessionTemplate.selectList("getDepReportTemplates", query, rowBounds);
		return rows;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepReportTemplatesByParamsCount(Map<String, Object> params) {
		return depReportTemplateMapper.getDepReportTemplatesByParamsCount(params);
	}

	@Override
	public List<Map<String, Object>> getDepReportTemplatesByParams(int start, int pageSize,
			Map<String, Object> params) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> rows = sqlSessionTemplate.selectList("getDepReportTemplatesByParams", params,
				rowBounds);
		return rows;
	}

	public DepReportTemplate getDepReportTemplate(Long id) {
		if (id == null) {
			return null;
		}
		DepReportTemplate depReportTemplate = depReportTemplateMapper.getDepReportTemplateById(id);
		return depReportTemplate;
	}

	@Override
	public String getTableName(Long id) {
		String tableName = null;
		DepReportTemplate template = this.getDepReportTemplate(id);
		if (StringUtils.isNotBlank(template.getRuleJson())) {
			JSONObject json = JSON.parseObject(template.getRuleJson());
			json = json.getJSONObject("page");
			String data_save_area = json.getString(PageCellModel.data_save_area);
			if (StringUtils.isNotBlank(data_save_area)) {
				JSONArray jsonArray = JSONArray.parseArray(data_save_area);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject data = jsonArray.getJSONObject(i).getJSONObject(PageCellModel.col2)
							.getJSONObject("data");
					JSONObject tableMsg = JSONArray.parseArray(data.getString("tableMsg")).getJSONObject(0);
					if (tableMsg != null) {
						JSONObject table = tableMsg.getJSONObject("table");
						if (StringUtils.equalsIgnoreCase(table.getString("isSubTable"), "0") //
								|| jsonArray.size() == 1) {
							tableName = table.getString("tableName");
							break;
						}
					}
				}
			}
		}
		return tableName;
	}

	@Transactional
	public void save(DepReportTemplate depReportTemplate) {
		if (depReportTemplate.getId() == null) {
			depReportTemplate.setId(idGenerator.nextId("DEP_REPORT_TEMPLATE"));
			depReportTemplate.setCreateDateTime(new Date());
			depReportTemplate.setDelFlag("0");
			depReportTemplateMapper.insertDepReportTemplate(depReportTemplate);
		} else {
			depReportTemplate.setModifyDateTime(new Date());
			depReportTemplateMapper.updateDepReportTemplate(depReportTemplate);
		}
		this.runSaveBatch(depReportTemplate);
	}

	@Transactional
	public void runSaveBatch(DepReportTemplate depReportTemplate) {

		logger.debug("-------------------start run-------------------");
		if (StringUtils.isNotBlank(depReportTemplate.getRuleJson())) { // 拆分属性到子表
			JSONObject ruleJsonObject = JSON.parseObject(depReportTemplate.getRuleJson());
			if (ruleJsonObject != null) {
				String sql;
				JSONObject rule;
				Set<String> sets = ruleJsonObject.keySet();
				Connection connection = null;
				try {
					// connection =
					// DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

					connection = this.sqlSessionTemplate.getConnection();

					Crud<DepReportCell> crud0 = new JdbcInsert<DepReportCell>();
					crud0.setConnection(connection);

					sql = "UPDATE DEP_REPORT_CELL SET DELFLAG_ = '1' WHERE REPTEMPLATE_ID_ = "
							+ depReportTemplate.getId();

					crud0.execute(sql);

					Date date = new Date();
					for (String key : sets) {
						if (StringUtils.isNotBlank(key) && //
								!key.equalsIgnoreCase("page")) {

							rule = ruleJsonObject.getJSONObject(key);

							DepReportCell c = new DepReportCell();
							c.setRepTemplateId(depReportTemplate.getId());
							c.setId(idGenerator.nextId("DEP_REPORT_CELL"));
							c.setName(rule.getString(Global.NAME));
							c.setDesc(c.getName());
							c.setCode(key);
							c.setCreateDateTime(date);
							c.setDelflag("0");

							crud0.addBatch(c);
						}
					}

					if (crud0.batchSize() > 0)
						crud0.executeBatch();
				} catch (SQLException ex) {
					ex.printStackTrace();
					logger.error("run batch error", ex);
					throw new RuntimeException(ex);
				} finally {

				}
			}
		}
		logger.debug("-------------------end run-------------------");
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
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

	@javax.annotation.Resource(name = "com.glaf.dep.report.mapper.DepReportTemplateMapper")
	public void setDepReportTemplateMapper(DepReportTemplateMapper depReportTemplateMapper) {
		this.depReportTemplateMapper = depReportTemplateMapper;
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
