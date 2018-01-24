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

@Service("com.glaf.etl.service.etlTransferTaskInstService")
@Transactional(readOnly = true)
public class EtlTransferTaskInstServiceImpl implements EtlTransferTaskInstService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EtlTransferTaskInstMapper etlTransferTaskInstMapper;

	public EtlTransferTaskInstServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			etlTransferTaskInstMapper.deleteEtlTransferTaskInstById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> id_s) {
		if (id_s != null && !id_s.isEmpty()) {
			for (String id : id_s) {
				etlTransferTaskInstMapper.deleteEtlTransferTaskInstById(id);
			}
		}
	}

	public int count(EtlTransferTaskInstQuery query) {
		return etlTransferTaskInstMapper.getEtlTransferTaskInstCount(query);
	}

	public List<EtlTransferTaskInst> list(EtlTransferTaskInstQuery query) {
		List<EtlTransferTaskInst> list = etlTransferTaskInstMapper.getEtlTransferTaskInsts(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEtlTransferTaskInstCountByQueryCriteria(EtlTransferTaskInstQuery query) {
		return etlTransferTaskInstMapper.getEtlTransferTaskInstCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EtlTransferTaskInst> getEtlTransferTaskInstsByQueryCriteria(int start, int pageSize,
			EtlTransferTaskInstQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EtlTransferTaskInst> rows = sqlSessionTemplate.selectList("getEtlTransferTaskInsts", query, rowBounds);
		return rows;
	}

	public EtlTransferTaskInst getEtlTransferTaskInst(String id) {
		if (id == null) {
			return null;
		}
		EtlTransferTaskInst etlTransferTaskInst = etlTransferTaskInstMapper.getEtlTransferTaskInstById(id);
		return etlTransferTaskInst;
	}

	@Transactional
	public void save(EtlTransferTaskInst etlTransferTaskInst) {
		if (StringUtils.isEmpty(etlTransferTaskInst.getId_())) {
			etlTransferTaskInst.setId_(idGenerator.getNextId("ETL_TRANSFER_TASK_INST_"));
			// etlTransferTaskInst.setCreateDate(new Date());
			// etlTransferTaskInst.setDeleteFlag(0);
			etlTransferTaskInstMapper.insertEtlTransferTaskInst(etlTransferTaskInst);
		} else {
			etlTransferTaskInstMapper.updateEtlTransferTaskInst(etlTransferTaskInst);
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

	@javax.annotation.Resource(name = "com.glaf.etl.mapper.EtlTransferTaskInstMapper")
	public void setEtlTransferTaskInstMapper(EtlTransferTaskInstMapper etlTransferTaskInstMapper) {
		this.etlTransferTaskInstMapper = etlTransferTaskInstMapper;
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
