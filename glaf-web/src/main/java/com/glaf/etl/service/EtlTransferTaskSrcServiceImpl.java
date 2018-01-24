package com.glaf.etl.service;

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

import com.glaf.etl.mapper.*;
import com.glaf.etl.domain.*;
import com.glaf.etl.query.*;

@Service("com.glaf.etl.service.etlTransferTaskSrcService")
@Transactional(readOnly = true)
public class EtlTransferTaskSrcServiceImpl implements EtlTransferTaskSrcService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EtlTransferTaskSrcMapper etlTransferTaskSrcMapper;

	public EtlTransferTaskSrcServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			etlTransferTaskSrcMapper.deleteEtlTransferTaskSrcById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> id_s) {
		if (id_s != null && !id_s.isEmpty()) {
			for (String id : id_s) {
				etlTransferTaskSrcMapper.deleteEtlTransferTaskSrcById(id);
			}
		}
	}

	public int count(EtlTransferTaskSrcQuery query) {
		return etlTransferTaskSrcMapper.getEtlTransferTaskSrcCount(query);
	}

	public List<EtlTransferTaskSrc> list(EtlTransferTaskSrcQuery query) {
		List<EtlTransferTaskSrc> list = etlTransferTaskSrcMapper.getEtlTransferTaskSrcs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEtlTransferTaskSrcCountByQueryCriteria(EtlTransferTaskSrcQuery query) {
		return etlTransferTaskSrcMapper.getEtlTransferTaskSrcCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlTransferTaskSrc> getEtlTransferTaskSrcsByQueryCriteria(int start, int pageSize,
			EtlTransferTaskSrcQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlTransferTaskSrc> rows = sqlSessionTemplate.selectList("getEtlTransferTaskSrcs", query, rowBounds);
		return rows;
	}

	public EtlTransferTaskSrc getEtlTransferTaskSrc(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTaskSrc etlTransferTaskSrc = etlTransferTaskSrcMapper.getEtlTransferTaskSrcById(id);
		return etlTransferTaskSrc;
	}
	
	@Override
	public EtlTransferTaskSrc getEtlTransferTaskSrcByTaskId(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTaskSrc etlTransferTaskSrc = etlTransferTaskSrcMapper.getEtlTransferTaskSrcByTaskId(id);
		return etlTransferTaskSrc;
	}
	
	@Transactional
	public void save(EtlTransferTaskSrc etlTransferTaskSrc) {
		if (StringUtils.isEmpty(etlTransferTaskSrc.getId_())) {
			etlTransferTaskSrc.setId_(UUID32.getUUID());
			// etlTransferTaskSrc.setCreateDate(new Date());
			// etlTransferTaskSrc.setDeleteFlag(0);
			etlTransferTaskSrcMapper.insertEtlTransferTaskSrc(etlTransferTaskSrc);
		} else {
			etlTransferTaskSrcMapper.updateEtlTransferTaskSrc(etlTransferTaskSrc);
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

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.EtlTransferTaskSrcMapper")
	public void setEtlTransferTaskSrcMapper(EtlTransferTaskSrcMapper etlTransferTaskSrcMapper) {
		this.etlTransferTaskSrcMapper = etlTransferTaskSrcMapper;
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
