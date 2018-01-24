package com.glaf.sys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
import org.apache.commons.lang3.StringUtils;
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

import com.glaf.sys.mapper.*;
import com.glaf.sys.domain.*;
import com.glaf.sys.query.*;

@Service("com.glaf.sys.service.projMuiprojlistService")
@Transactional(readOnly = true) 
public class ProjMuiprojlistServiceImpl implements ProjMuiprojlistService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjMuiprojlistMapper projMuiprojlistMapper;

	public ProjMuiprojlistServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
	     if(id != null ){
		projMuiprojlistMapper.deleteProjMuiprojlistById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
	    if(indexIds != null && !indexIds.isEmpty()){
		for(Integer id : indexIds){
		    projMuiprojlistMapper.deleteProjMuiprojlistById(id);
		}
	    }
	}

	public int count(ProjMuiprojlistQuery query) {
		return projMuiprojlistMapper.getProjMuiprojlistCount(query);
	}

	public List<ProjMuiprojlist> list(ProjMuiprojlistQuery query) {
		List<ProjMuiprojlist> list = projMuiprojlistMapper.getProjMuiprojlists(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getProjMuiprojlistCountByQueryCriteria(ProjMuiprojlistQuery query) {
		return projMuiprojlistMapper.getProjMuiprojlistCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProjMuiprojlist> getProjMuiprojlistsByQueryCriteria(int start, int pageSize,
			ProjMuiprojlistQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProjMuiprojlist> rows = sqlSessionTemplate.selectList(
				"getProjMuiprojlists", query, rowBounds);
		return rows;
	}


	public ProjMuiprojlist getProjMuiprojlist(Integer id) {
	        if(id == null){
		    return null;
		}
		ProjMuiprojlist projMuiprojlist = projMuiprojlistMapper.getProjMuiprojlistById(id);
		return projMuiprojlist;
	}
	public ProjMuiprojlist getLocalProjMuiprojlist(){
		return projMuiprojlistMapper.getLocalProjMuiprojlist();
	}
	@Transactional
	public void save(ProjMuiprojlist projMuiprojlist) {
            if ( projMuiprojlist.getIndexId()  == null) {
	        projMuiprojlist.setIndexId(idGenerator.nextId("PROJ_MUIPROJLIST").intValue());
		//projMuiprojlist.setCreateDate(new Date());
		//projMuiprojlist.setDeleteFlag(0);
		projMuiprojlistMapper.insertProjMuiprojlist(projMuiprojlist);
	       } else {
		projMuiprojlistMapper.updateProjMuiprojlist(projMuiprojlist);
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

	@javax.annotation.Resource(name = "com.glaf.sys.mapper.ProjMuiprojlistMapper")
	public void setProjMuiprojlistMapper(ProjMuiprojlistMapper projMuiprojlistMapper) {
		this.projMuiprojlistMapper = projMuiprojlistMapper;
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
