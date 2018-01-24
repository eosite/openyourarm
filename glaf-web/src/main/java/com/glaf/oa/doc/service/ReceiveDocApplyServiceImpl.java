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

@Service("com.glaf.oa.doc.service.receiveDocApplyService")
@Transactional(readOnly = true) 
public class ReceiveDocApplyServiceImpl implements ReceiveDocApplyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ReceiveDocApplyMapper receiveDocApplyMapper;

	public ReceiveDocApplyServiceImpl() {

	}

	@Override
	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		receiveDocApplyMapper.deleteReceiveDocApplyById(id);
	     }
	}

	@Override
	@Transactional
	public void deleteByIds(List<Integer> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Integer id : ids){
		    receiveDocApplyMapper.deleteReceiveDocApplyById(id);
		}
	    }
	}

	public int count(ReceiveDocApplyQuery query) {
		return receiveDocApplyMapper.getReceiveDocApplyCount(query);
	}

	@Override
	public List<ReceiveDocApply> list(ReceiveDocApplyQuery query) {
		List<ReceiveDocApply> list = receiveDocApplyMapper.getReceiveDocApplys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	@Override
	public int getReceiveDocApplyCountByQueryCriteria(ReceiveDocApplyQuery query) {
		return receiveDocApplyMapper.getReceiveDocApplyCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	@Override
	public List<ReceiveDocApply> getReceiveDocApplysByQueryCriteria(int start, int pageSize,
			ReceiveDocApplyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ReceiveDocApply> rows = sqlSessionTemplate.selectList(
				"getReceiveDocApplys", query, rowBounds);
		return rows;
	}


	@Override
	public ReceiveDocApply getReceiveDocApply(Integer id) {
	        if(id == null){
		    return null;
		}
		ReceiveDocApply receiveDocApply = receiveDocApplyMapper.getReceiveDocApplyById(id);
		return receiveDocApply;
	}

	@Override
	@Transactional
	public void save(ReceiveDocApply receiveDocApply) {
            if ( receiveDocApply.getId()  == null) {
	        receiveDocApply.setId(idGenerator.nextId("RECEIVEDOCAPPLY").intValue());
		//receiveDocApply.setCreateDate(new Date());
		//receiveDocApply.setDeleteFlag(0);
		receiveDocApplyMapper.insertReceiveDocApply(receiveDocApply);
	       } else {
		receiveDocApplyMapper.updateReceiveDocApply(receiveDocApply);
	      }
	}


	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
 		String sql = "  ";//要运行的SQL语句
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

	@javax.annotation.Resource(name = "com.glaf.oa.doc.mapper.ReceiveDocApplyMapper")
	public void setReceiveDocApplyMapper(ReceiveDocApplyMapper receiveDocApplyMapper) {
		this.receiveDocApplyMapper = receiveDocApplyMapper;
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
