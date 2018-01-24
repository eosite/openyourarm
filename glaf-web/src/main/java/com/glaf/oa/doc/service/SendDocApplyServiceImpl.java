package com.glaf.oa.doc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;

import com.glaf.oa.doc.mapper.*;
import com.glaf.oa.doc.domain.*;
import com.glaf.oa.doc.query.*;

@Service("com.glaf.oa.doc.service.sendDocApplyService")
@Transactional(readOnly = true)
public class SendDocApplyServiceImpl implements SendDocApplyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SendDocApplyMapper sendDocApplyMapper;

	public SendDocApplyServiceImpl() {

	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			sendDocApplyMapper.deleteSendDocApplyById(id);
		}
	}

	@Override
	@Transactional
	public void deleteByIds(List<Integer> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Integer id : ids) {
				sendDocApplyMapper.deleteSendDocApplyById(id);
			}
		}
	}

	public int count(SendDocApplyQuery query) {
		return sendDocApplyMapper.getSendDocApplyCount(query);
	}

	@Override
	public List<SendDocApply> list(SendDocApplyQuery query) {
		List<SendDocApply> list = sendDocApplyMapper.getSendDocApplys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	@Override
	public int getSendDocApplyCountByQueryCriteria(SendDocApplyQuery query) {
		return sendDocApplyMapper.getSendDocApplyCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	@Override
	public List<SendDocApply> getSendDocApplysByQueryCriteria(int start,
			int pageSize, SendDocApplyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SendDocApply> rows = sqlSessionTemplate.selectList(
				"getSendDocApplys", query, rowBounds);
		return rows;
	}

	@Override
	public SendDocApply getSendDocApply(Integer id) {
		if (id == null) {
			return null;
		}
		SendDocApply sendDocApply = sendDocApplyMapper.getSendDocApplyById(id);
		return sendDocApply;
	}

	@Override
	@Transactional
	public void save(SendDocApply sendDocApply) {
		if (sendDocApply.getId() == null) {
			sendDocApply.setId(idGenerator.nextId("SENDDOCAPPLY").intValue());

			sendDocApply.setCreateDate(new Date());
			sendDocApply.setStatus(0);
			// sendDocApply.setDeleteFlag(0);
			sendDocApplyMapper.insertSendDocApply(sendDocApply);
		} else {
			sendDocApplyMapper.updateSendDocApply(sendDocApply);
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

	@javax.annotation.Resource(name = "com.glaf.oa.doc.mapper.SendDocApplyMapper")
	public void setSendDocApplyMapper(SendDocApplyMapper sendDocApplyMapper) {
		this.sendDocApplyMapper = sendDocApplyMapper;
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
