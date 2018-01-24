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
import com.glaf.core.security.Authentication;
import com.glaf.core.util.*;

import com.glaf.report.core.mapper.*;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

@Service("com.glaf.report.core.service.reportTmpDataSetMappingService")
@Transactional(readOnly = true)
public class ReportTmpDataSetMappingServiceImpl implements ReportTmpDataSetMappingService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReportTmpDataSetMappingMapper reportTmpDataSetMappingMapper;

	protected ReportTmpColMappingService reportTmpColMappingService;

	public ReportTmpDataSetMappingServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ReportTmpDataSetMapping> list) {
		for (ReportTmpDataSetMapping reportTmpDataSetMapping : list) {
			if (StringUtils.isEmpty(reportTmpDataSetMapping.getId())) {
				reportTmpDataSetMapping.setId(idGenerator.getNextId("REPORT_TMP_DATASET_MAPPING"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// reportTmpDataSetMappingMapper.bulkInsertReportTmpDataSetMapping_oracle(list);
		} else {
			// reportTmpDataSetMappingMapper.bulkInsertReportTmpDataSetMapping(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			reportTmpDataSetMappingMapper.deleteReportTmpDataSetMappingById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				reportTmpDataSetMappingMapper.deleteReportTmpDataSetMappingById(id);
			}
		}
	}

	public int count(ReportTmpDataSetMappingQuery query) {
		return reportTmpDataSetMappingMapper.getReportTmpDataSetMappingCount(query);
	}

	public List<ReportTmpDataSetMapping> list(ReportTmpDataSetMappingQuery query) {
		List<ReportTmpDataSetMapping> list = reportTmpDataSetMappingMapper.getReportTmpDataSetMappings(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getReportTmpDataSetMappingCountByQueryCriteria(ReportTmpDataSetMappingQuery query) {
		return reportTmpDataSetMappingMapper.getReportTmpDataSetMappingCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ReportTmpDataSetMapping> getReportTmpDataSetMappingsByQueryCriteria(int start, int pageSize,
			ReportTmpDataSetMappingQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReportTmpDataSetMapping> rows = sqlSessionTemplate.selectList("getReportTmpDataSetMappings", query,
				rowBounds);
		return rows;
	}

	public ReportTmpDataSetMapping getReportTmpDataSetMapping(String id) {
		if (id == null) {
			return null;
		}
		ReportTmpDataSetMapping reportTmpDataSetMapping = reportTmpDataSetMappingMapper
				.getReportTmpDataSetMappingById(id);
		return reportTmpDataSetMapping;
	}

	@Transactional
	public void save(ReportTmpDataSetMapping reportTmpDataSetMapping) {
		if (StringUtils.isEmpty(reportTmpDataSetMapping.getId())) {
			reportTmpDataSetMapping.setCreateDatetime(new Date());
			reportTmpDataSetMapping.setCreator(Authentication.getAuthenticatedActorId());
			reportTmpDataSetMapping.setId(idGenerator.getNextId("REPORT_TMP_DATASET_MAPPING"));
			reportTmpDataSetMappingMapper.insertReportTmpDataSetMapping(reportTmpDataSetMapping);
		} else {
			reportTmpDataSetMapping.setModifyDatetime(new Date());
			reportTmpDataSetMappingMapper.updateReportTmpDataSetMapping(reportTmpDataSetMapping);
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

	@javax.annotation.Resource(name = "com.glaf.report.core.mapper.ReportTmpDataSetMappingMapper")
	public void setReportTmpDataSetMappingMapper(ReportTmpDataSetMappingMapper reportTmpDataSetMappingMapper) {
		this.reportTmpDataSetMappingMapper = reportTmpDataSetMappingMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	/**
	 * 获取报表模板数据集映射
	 * 
	 * @param tmpMappingId
	 * @return Map<String, String> key 模板dataset code，value 定义平台dataset ID
	 */
	@Override
	public Map<String, String> getReportTmpDataSetMappingMap(String tmpMappingId) {
		// TODO Auto-generated method stub
		Map<String, String> dataSetMappingMap = new HashMap<String, String>();
		ReportTmpDataSetMappingQuery query = new ReportTmpDataSetMappingQuery();
		query.setTmpMappingId(tmpMappingId);
		List<ReportTmpDataSetMapping> dataSetMappingList = list(query);
		for (ReportTmpDataSetMapping reportTmpDataSetMapping : dataSetMappingList) {
			dataSetMappingMap.put(reportTmpDataSetMapping.getDataSetCode(),reportTmpDataSetMapping.getMappingDataSetId());
		}
		return dataSetMappingMap;
	}

	@Transactional
	public void save(List<ReportTmpDataSetMapping> reportTmpDataSetMappings) {
		if (CollectionUtils.isNotEmpty(reportTmpDataSetMappings)) {
			for (ReportTmpDataSetMapping reportTmpDataSetMapping : reportTmpDataSetMappings) {
				this.save(reportTmpDataSetMapping);
				List<ReportTmpColMapping> reportTmpColMappings = reportTmpDataSetMapping.getReportTmpColMappings();
				if (CollectionUtils.isNotEmpty(reportTmpColMappings)) {
					for (ReportTmpColMapping reportTmpColMapping : reportTmpColMappings) {
						reportTmpColMapping.setDataSetMappingId(reportTmpDataSetMapping.getId());
						this.reportTmpColMappingService.save(reportTmpColMapping);
					}
				}
			}
			reportTmpColMappingService.deleteIfDataSetNotExists();
		}
	}

	@Transactional
	public void deleteByParentId(String id) {
		ReportTmpDataSetMappingQuery query = new ReportTmpDataSetMappingQuery();
		query.setTmpMappingId(id);
		List<ReportTmpDataSetMapping> list = this.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			for (ReportTmpDataSetMapping m : list) {
				this.reportTmpColMappingService.deleteByParentId(m.getId());
			}
		}
		reportTmpDataSetMappingMapper.deleteReportTmpDataSetMappingByParentId(id);
	}

	@javax.annotation.Resource(name = "com.glaf.report.core.service.reportTmpColMappingService")
	public void setReportTmpColMappingService(ReportTmpColMappingService reportTmpColMappingService) {
		this.reportTmpColMappingService = reportTmpColMappingService;
	}
}
