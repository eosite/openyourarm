package com.glaf.report.core.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.Authentication;
import com.glaf.core.util.DBUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.report.core.domain.ReportTmpDataSetMapping;
import com.glaf.report.core.domain.ReportTmpMapping;
import com.glaf.report.core.domain.ReportTmpMappingEntity;
import com.glaf.report.core.mapper.ReportTmpMappingMapper;
import com.glaf.report.core.query.ReportTmpDataSetMappingQuery;
import com.glaf.report.core.query.ReportTmpMappingQuery;

@Service("com.glaf.report.core.service.reportTmpMappingService")
@Transactional(readOnly = true)
public class ReportTmpMappingServiceImpl implements ReportTmpMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpMappingMapper reportTmpMappingMapper;

	protected ReportTmpDataSetMappingService reportTmpDataSetMappingService;

	protected ReportTmpColMappingService reportTmpColMappingService;

	public ReportTmpMappingServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ReportTmpMapping> list) {
		for (ReportTmpMapping reportTmpMapping : list) {
			if (StringUtils.isEmpty(reportTmpMapping.getId())) {
				reportTmpMapping.setId(idGenerator.getNextId("REPORT_TMP_MAPPING"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// reportTmpMappingMapper.bulkInsertReportTmpMapping_oracle(list);
		} else {
			// reportTmpMappingMapper.bulkInsertReportTmpMapping(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			reportTmpMappingMapper.deleteReportTmpMappingById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				reportTmpMappingMapper.deleteReportTmpMappingById(id);
			}
		}
	}

	public int count(ReportTmpMappingQuery query) {
		return reportTmpMappingMapper.getReportTmpMappingCount(query);
	}

	public List<ReportTmpMapping> list(ReportTmpMappingQuery query) {
		List<ReportTmpMapping> list = reportTmpMappingMapper.getReportTmpMappings(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportTmpMappingCountByQueryCriteria(ReportTmpMappingQuery query) {
		return reportTmpMappingMapper.getReportTmpMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpMapping> getReportTmpMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpMapping> rows = sqlSessionTemplate.selectList("getReportTmpMappings", query, rowBounds);
		return rows;
	}

	public ReportTmpMapping getReportTmpMapping(String id) {
		if (id == null) {
			return null;
		}
		ReportTmpMapping reportTmpMapping = reportTmpMappingMapper.getReportTmpMappingById(id);
		return reportTmpMapping;
	}

	@Transactional
	public void save(ReportTmpMapping reportTmpMapping) {
		if (StringUtils.isEmpty(reportTmpMapping.getId())) {
			reportTmpMapping.setId(idGenerator.getNextId("REPORT_TMP_MAPPING"));
			reportTmpMapping.setCreateDatetime(new Date());
			reportTmpMapping.setDeleteFlag(0);
			reportTmpMapping.setSystemId(Environment.DEFAULT_SYSTEM_NAME);
			reportTmpMapping.setCreator(Authentication.getAuthenticatedActorId());
			reportTmpMappingMapper.insertReportTmpMapping(reportTmpMapping);
		} else {
			reportTmpMapping.setModifyDatetime(new Date());
			reportTmpMapping.setModifier(Authentication.getAuthenticatedActorId());
			reportTmpMappingMapper.updateReportTmpMapping(reportTmpMapping);
		}
	}

	public ReportTmpMappingEntity getReportTmpDatasetMapping(String tmpMappingId) {
		ReportTmpMappingEntity reportTmpMappingEntity = new ReportTmpMappingEntity();
		Map<String, String> dataSetMappingMap = reportTmpDataSetMappingService
				.getReportTmpDataSetMappingMap(tmpMappingId);
		reportTmpMappingEntity.setDatasetMapping(dataSetMappingMap);
		Map<String, Map<String, String>> colMappingMap = reportTmpColMappingService
				.getReportTmpColMappingMap(tmpMappingId);
		reportTmpMappingEntity.setDatasetColMapping(colMappingMap);
		return reportTmpMappingEntity;
	}

	public JSONObject getReportDatasetData(String tmpMappingId, JSONObject paramJson) {
		JSONObject reportDataJson = new JSONObject();

		ReportTmpDataSetMappingQuery query = new ReportTmpDataSetMappingQuery();
		query.setTmpMappingId(tmpMappingId);
		List<ReportTmpDataSetMapping> dataSets = this.reportTmpDataSetMappingService.list(query);
		if (CollectionUtils.isNotEmpty(dataSets)) {
			for (ReportTmpDataSetMapping dataSet : dataSets) {
				String dataSetId = dataSet.getMappingDataSetId();
				String dataSetCode = dataSet.getDataSetCode();
				if (StringUtils.isNotBlank(dataSetId)) {
					DataSetBuilder builder = new DataSetBuilder();
					
					Object rows = builder.getJsonArray(dataSetId, paramJson.getJSONObject(dataSetCode));
					reportDataJson.put(dataSetCode, rows);
				}
			}
		}
		return reportDataJson;
	}

	public JSONObject transReportDatasetData(ReportTmpMappingEntity reportTmpMappingEntity, JSONObject data) {
		JSONObject reportDataJson = new JSONObject();
		if (data != null) {
			// 获取报表模板数据集映射
			//Map<String, String> datasetMappingMap = reportTmpMappingEntity.getDatasetMapping();
			// 获取报表模板数据集列映射
			Map<String, Map<String, String>> datasetColMappingMap = reportTmpMappingEntity.getDatasetColMapping();
			//String datasetCode = null;
			Map<String, String> colsMapping = null;
			JSONArray datasetData = null;
			String datasetDataStr = null;
			String colCode = null;
			String mappingColCode = null;
			for (String datasetCode : data.keySet()) {
				//datasetCode = datasetMappingMap.get(mappingDatasetCode);
				// 获取数据集列映射
				colsMapping = datasetColMappingMap.get(datasetCode);
				datasetData = data.getJSONArray(datasetCode);
				// 转换为字符串
				if (datasetData != null && colsMapping != null) {
					datasetDataStr = datasetData.toJSONString();
					for (Entry<String, String> colMapping : colsMapping.entrySet()) {
						colCode = colMapping.getValue();
						mappingColCode = colMapping.getKey();
						// 将映射列CODE替换为模板数据集列CODE
						datasetDataStr=datasetDataStr.replaceAll("\""+mappingColCode+"\"", "\""+colCode+"\"");
					}
					reportDataJson.put(datasetCode, JSONArray.parseArray(datasetDataStr));
				}
			}
		}
		return reportDataJson;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpMappingMapper")
	public void setReportTmpMappingMapper(ReportTmpMappingMapper reportTmpMappingMapper) {
		this.reportTmpMappingMapper = reportTmpMappingMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setReportTmpDataSetMappingService(ReportTmpDataSetMappingService reportTmpDataSetMappingService) {
		this.reportTmpDataSetMappingService = reportTmpDataSetMappingService;
	}

	@javax.annotation.Resource
	public void setReportTmpColMappingService(ReportTmpColMappingService reportTmpColMappingService) {
		this.reportTmpColMappingService = reportTmpColMappingService;
	}

	@Override
	public JSONObject getReportData(String tmpMappingId, JSONObject paramJson) {
		// 获取源报表数据
		JSONObject srcReportData = getReportDatasetData(tmpMappingId, paramJson);
		// 获取报表模板数据集映射
		ReportTmpMappingEntity reportTmpMappingEntity = getReportTmpDatasetMapping(tmpMappingId);
		// 获取转换后的报表数据
		JSONObject desReportData = transReportDatasetData(reportTmpMappingEntity, srcReportData);
		return desReportData;
	}

}
