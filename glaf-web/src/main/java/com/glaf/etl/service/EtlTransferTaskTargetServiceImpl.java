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

@Service("com.glaf.etl.service.etlTransferTaskTargetService")
@Transactional(readOnly = true)
public class EtlTransferTaskTargetServiceImpl implements EtlTransferTaskTargetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EtlTransferTaskTargetMapper etlTransferTaskTargetMapper;

	public EtlTransferTaskTargetServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			etlTransferTaskTargetMapper.deleteEtlTransferTaskTargetById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> id_s) {
		if (id_s != null && !id_s.isEmpty()) {
			for (String id : id_s) {
				etlTransferTaskTargetMapper.deleteEtlTransferTaskTargetById(id);
			}
		}
	}

	public int count(EtlTransferTaskTargetQuery query) {
		return etlTransferTaskTargetMapper.getEtlTransferTaskTargetCount(query);
	}

	public List<EtlTransferTaskTarget> list(EtlTransferTaskTargetQuery query) {
		List<EtlTransferTaskTarget> list = etlTransferTaskTargetMapper.getEtlTransferTaskTargets(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEtlTransferTaskTargetCountByQueryCriteria(EtlTransferTaskTargetQuery query) {
		return etlTransferTaskTargetMapper.getEtlTransferTaskTargetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlTransferTaskTarget> getEtlTransferTaskTargetsByQueryCriteria(int start, int pageSize,
			EtlTransferTaskTargetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlTransferTaskTarget> rows = sqlSessionTemplate.selectList("getEtlTransferTaskTargets", query, rowBounds);
		return rows;
	}

	public EtlTransferTaskTarget getEtlTransferTaskTarget(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTaskTarget etlTransferTaskTarget = etlTransferTaskTargetMapper.getEtlTransferTaskTargetById(id);
		return etlTransferTaskTarget;
	}
	
	@Override
	public EtlTransferTaskTarget getEtlTransferTaskTargetByTaskId(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTaskTarget etlTransferTaskTarget = etlTransferTaskTargetMapper.getEtlTransferTaskTargetByTaskId(id);
		return etlTransferTaskTarget;
	}

	@Transactional
	public void save(EtlTransferTaskTarget etlTransferTaskTarget) {
		if (StringUtils.isEmpty(etlTransferTaskTarget.getId_())) {
			etlTransferTaskTarget.setId_(UUID32.getUUID());
			// etlTransferTaskTarget.setCreateDate(new Date());
			// etlTransferTaskTarget.setDeleteFlag(0);
			etlTransferTaskTargetMapper.insertEtlTransferTaskTarget(etlTransferTaskTarget);
		} else {
			etlTransferTaskTargetMapper.updateEtlTransferTaskTarget(etlTransferTaskTarget);
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

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.EtlTransferTaskTargetMapper")
	public void setEtlTransferTaskTargetMapper(EtlTransferTaskTargetMapper etlTransferTaskTargetMapper) {
		this.etlTransferTaskTargetMapper = etlTransferTaskTargetMapper;
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
