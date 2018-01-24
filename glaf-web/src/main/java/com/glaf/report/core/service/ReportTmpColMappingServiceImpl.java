package com.glaf.report.core.service;

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

import com.glaf.report.core.mapper.*;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

@Service("com.glaf.report.core.service.reportTmpColMappingService")
@Transactional(readOnly = true)
public class ReportTmpColMappingServiceImpl implements ReportTmpColMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpColMappingMapper reportTmpColMappingMapper;

	public ReportTmpColMappingServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ReportTmpColMapping> list) {
		for (ReportTmpColMapping reportTmpColMapping : list) {
			if (StringUtils.isEmpty(reportTmpColMapping.getId())) {
				reportTmpColMapping.setId(idGenerator.getNextId("REPORT_TMP_COL_MAPPING"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// reportTmpColMappingMapper.bulkInsertReportTmpColMapping_oracle(list);
		} else {
			// reportTmpColMappingMapper.bulkInsertReportTmpColMapping(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			reportTmpColMappingMapper.deleteReportTmpColMappingById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				reportTmpColMappingMapper.deleteReportTmpColMappingById(id);
			}
		}
	}

	public int count(ReportTmpColMappingQuery query) {
		return reportTmpColMappingMapper.getReportTmpColMappingCount(query);
	}

	public List<ReportTmpColMapping> list(ReportTmpColMappingQuery query) {
		List<ReportTmpColMapping> list = reportTmpColMappingMapper.getReportTmpColMappings(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportTmpColMappingCountByQueryCriteria(ReportTmpColMappingQuery query) {
		return reportTmpColMappingMapper.getReportTmpColMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpColMapping> getReportTmpColMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpColMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpColMapping> rows = sqlSessionTemplate.selectList("getReportTmpColMappings", query, rowBounds);
		return rows;
	}

	public ReportTmpColMapping getReportTmpColMapping(String id) {
		if (id == null) {
			return null;
		}
		ReportTmpColMapping reportTmpColMapping = reportTmpColMappingMapper.getReportTmpColMappingById(id);
		return reportTmpColMapping;
	}

	@Transactional
	public void save(ReportTmpColMapping reportTmpColMapping) {
		if (StringUtils.isEmpty(reportTmpColMapping.getId())) {
			reportTmpColMapping.setId(idGenerator.getNextId("REPORT_TMP_COL_MAPPING"));
			// reportTmpColMapping.setCreateDate(new Date());
			// reportTmpColMapping.setDeleteFlag(0);
			reportTmpColMappingMapper.insertReportTmpColMapping(reportTmpColMapping);
		} else {
			reportTmpColMappingMapper.updateReportTmpColMapping(reportTmpColMapping);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpColMappingMapper")
	public void setReportTmpColMappingMapper(ReportTmpColMappingMapper reportTmpColMappingMapper) {
		this.reportTmpColMappingMapper = reportTmpColMappingMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public Map<String, Map<String, String>> getReportTmpColMappingMap(String tmpMappingId) {
		List<ReportTmpColMapping> reportTmpColMapperList = reportTmpColMappingMapper
				.getReportTmpColMappingsByTmpMappingId(tmpMappingId);
		Map<String, Map<String, String>> reportTmpColMappingMap = new HashMap<String, Map<String, String>>();
		Map<String, String> colMappingMap = null;
		String dataSetCode = null;
		for (ReportTmpColMapping reportTmpColMapping : reportTmpColMapperList) {
			dataSetCode = reportTmpColMapping.getDataSetCode();
			if (reportTmpColMappingMap.containsKey(dataSetCode)) {
				colMappingMap = reportTmpColMappingMap.get(dataSetCode);
			} else {
				colMappingMap = new HashMap<String, String>();
			}
			colMappingMap.put(reportTmpColMapping.getColMappingCode(), reportTmpColMapping.getColCode());
			reportTmpColMappingMap.put(dataSetCode, colMappingMap);
		}
		return reportTmpColMappingMap;
	}

	@Override
	public void deleteByParentId(String id) {
		reportTmpColMappingMapper.deleteReportTmpColMappingByParentId(id);
	}

	@Override
	public void deleteIfDataSetNotExists() {
		reportTmpColMappingMapper.deleteIfDataSetNotExists();
	}

}
