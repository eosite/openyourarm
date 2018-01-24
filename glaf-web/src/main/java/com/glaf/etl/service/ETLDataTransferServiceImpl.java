package com.glaf.etl.service;

import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.etl.domain.ETLDataSrc;
import com.glaf.etl.domain.ETLDataTransfer;
import com.glaf.etl.mapper.ETLDataSrcMapper;
import com.glaf.etl.mapper.ETLDataTransferMapper;
import com.glaf.etl.query.ETLDataTransferQuery;

@Service("com.glaf.etl.service.eTLDataTransferService")
@Transactional(readOnly = true)
public class ETLDataTransferServiceImpl implements ETLDataTransferService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ETLDataTransferMapper eTLDataTransferMapper;

	protected ETLDataSrcMapper etlDataSrcMapper;

	public ETLDataTransferServiceImpl() {

	}

	// @Transactional
	// public void bulkInsert(List<ETLDataTransfer> list) {
	// for (ETLDataTransfer eTLDataTransfer : list) {
	// if (StringUtils.isEmpty(eTLDataTransfer.getId())) {
	// eTLDataTransfer.setId(idGenerator.getNextId("ETL_DATATRANSFER"));
	// }
	// }
	// if (StringUtils.equals(DBUtils.ORACLE,
	// DBConnectionFactory.getDatabaseType())) {
	// eTLDataTransferMapper.bulkInsertETLDataTransfer_oracle(list);
	// } else {
	// eTLDataTransferMapper.bulkInsertETLDataTransfer(list);
	// }
	// }

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			eTLDataTransferMapper.deleteETLDataTransferById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				eTLDataTransferMapper.deleteETLDataTransferById(id);
			}
		}
	}

	public int count(ETLDataTransferQuery query) {
		return eTLDataTransferMapper.getETLDataTransferCount(query);
	}

	public List<ETLDataTransfer> list(ETLDataTransferQuery query) {
		List<ETLDataTransfer> list = eTLDataTransferMapper.getETLDataTransfers(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getETLDataTransferCountByQueryCriteria(ETLDataTransferQuery query) {
		return eTLDataTransferMapper.getETLDataTransferCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ETLDataTransfer> getETLDataTransfersByQueryCriteria(int start, int pageSize,
			ETLDataTransferQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ETLDataTransfer> rows = sqlSessionTemplate.selectList("getETLDataTransfers", query, rowBounds);
		return rows;
	}

	public ETLDataTransfer getETLDataTransfer(String id) {
		if (id == null) {
			return null;
		}
		ETLDataTransfer eTLDataTransfer = eTLDataTransferMapper.getETLDataTransferById(id);
		return eTLDataTransfer;
	}

	@Transactional
	public void save(ETLDataTransfer eTLDataTransfer) {
		if (StringUtils.isEmpty(eTLDataTransfer.getId())) {
			eTLDataTransfer.setId(UUID32.getUUID());
			// eTLDataTransfer.setCreateDate(new Date());
			// eTLDataTransfer.setDeleteFlag(0);
			eTLDataTransferMapper.insertETLDataTransfer(eTLDataTransfer);
		} else {
			eTLDataTransferMapper.updateETLDataTransfer(eTLDataTransfer);
		}
	}

	@Transactional
	public void updateColumnMapping(String targetId, String transId, String columnMapping, String user) throws UnsupportedEncodingException {
		eTLDataTransferMapper.updateColumnMapping(targetId, transId, columnMapping.getBytes("UTF-8"), user, new Date());
	}

	public JSONArray getSrcDefColumns(String srcId) throws UnsupportedEncodingException {
		JSONArray columnJson = new JSONArray();
		ETLDataSrc dataSrc = etlDataSrcMapper.getETLDataSrcById(srcId);
		if (dataSrc != null) {
			byte[] columnByte = dataSrc.getColumnInfos();
			if (columnByte != null && columnByte.length > 0) {
				String columns = new String(columnByte,"UTF-8");
				columnJson = JSONObject.parseArray(columns);
			}
		}
		return columnJson;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.ETLDataTransferMapper")
	public void setETLDataTransferMapper(ETLDataTransferMapper eTLDataTransferMapper) {
		this.eTLDataTransferMapper = eTLDataTransferMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.ETLDataSrcMapper")
	public void setEtlDataSrcMapper(ETLDataSrcMapper etlDataSrcMapper) {
		this.etlDataSrcMapper = etlDataSrcMapper;
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
	public JSONObject getColumnMapping(String columnMapping) {
		// TODO Auto-generated method stub
		return null;
	}

}
