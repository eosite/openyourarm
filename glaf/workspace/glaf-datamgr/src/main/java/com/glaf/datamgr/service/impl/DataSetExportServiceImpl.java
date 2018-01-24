package com.glaf.datamgr.service.impl;

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

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DataSetExportService;

@Service("com.glaf.datamgr.service.dataSetExportService")
@Transactional(readOnly = true)
public class DataSetExportServiceImpl implements DataSetExportService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DataSetExportMapper dataSetExportMapper;

	public DataSetExportServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<DataSetExport> list) {
		for (DataSetExport dataSetExport : list) {
			if (StringUtils.isEmpty(dataSetExport.getId())) {
				dataSetExport.setId(UUID32.getUUID());
				dataSetExport.setCreateTime(new Date());
			}
		}

		int batch_size = 100;
		List<DataSetExport> rows = new ArrayList<DataSetExport>(batch_size);

		for (DataSetExport bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					dataSetExportMapper.bulkInsertDataSetExport_oracle(rows);
				} else {
					dataSetExportMapper.bulkInsertDataSetExport(rows);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				dataSetExportMapper.bulkInsertDataSetExport_oracle(rows);
			} else {
				dataSetExportMapper.bulkInsertDataSetExport(rows);
			}
			rows.clear();
		}
	}

	public int count(DataSetExportQuery query) {
		return dataSetExportMapper.getDataSetExportCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dataSetExportMapper.deleteDataSetExportById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				dataSetExportMapper.deleteDataSetExportById(id);
			}
		}
	}

	public DataSetExport getDataSetExport(String id) {
		if (id == null) {
			return null;
		}
		DataSetExport dataSetExport = dataSetExportMapper.getDataSetExportById(id);
		return dataSetExport;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDataSetExportCountByQueryCriteria(DataSetExportQuery query) {
		return dataSetExportMapper.getDataSetExportCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DataSetExport> getDataSetExportsByQueryCriteria(int start, int pageSize, DataSetExportQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DataSetExport> rows = sqlSessionTemplate.selectList("getDataSetExports", query, rowBounds);
		return rows;
	}

	public List<DataSetExport> list(DataSetExportQuery query) {
		List<DataSetExport> list = dataSetExportMapper.getDataSetExports(query);
		return list;
	}

	@Transactional
	public void save(DataSetExport dataSetExport) {
		if (StringUtils.isEmpty(dataSetExport.getId())) {
			dataSetExport.setId(UUID32.getUUID());
			dataSetExport.setCreateTime(new Date());
			dataSetExportMapper.insertDataSetExport(dataSetExport);
		} else {
			dataSetExportMapper.updateDataSetExport(dataSetExport);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.mapper.DataSetExportMapper")
	public void setDataSetExportMapper(DataSetExportMapper dataSetExportMapper) {
		this.dataSetExportMapper = dataSetExportMapper;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
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
