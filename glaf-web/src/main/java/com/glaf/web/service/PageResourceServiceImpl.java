package com.glaf.web.service;

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

import com.glaf.web.mapper.*;
import com.glaf.web.domain.*;
import com.glaf.web.query.*;

@Service("com.glaf.web.service.pageResourceService")
@Transactional(readOnly = true) 
public class PageResourceServiceImpl implements PageResourceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
    protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PageResourceMapper pageResourceMapper;

	public PageResourceServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		pageResourceMapper.deletePageResourceById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> resIds) {
	    if(resIds != null && !resIds.isEmpty()){
		for(Long id : resIds){
		    pageResourceMapper.deletePageResourceById(id);
		}
	    }
	}

	public int count(PageResourceQuery query) {
		return pageResourceMapper.getPageResourceCount(query);
	}

	public List<PageResource> list(PageResourceQuery query) {
		List<PageResource> list = pageResourceMapper.getPageResources(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getPageResourceCountByQueryCriteria(PageResourceQuery query) {
		return pageResourceMapper.getPageResourceCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<PageResource> getPageResourcesByQueryCriteria(int start, int pageSize,
			PageResourceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<PageResource> rows = sqlSessionTemplate.selectList(
				"getPageResources", query, rowBounds);
		return rows;
	}


	public PageResource getPageResource(Long id) {
	        if(id == null){
		    return null;
		}
		PageResource pageResource = pageResourceMapper.getPageResourceById(id);
		return pageResource;
	}

	@Transactional
	public void save(PageResource pageResource) {
            if ( pageResource.getResId()  == null) {
	        pageResource.setResId(idGenerator.nextId("PAGE_RESOURCE"));
		//pageResource.setCreateDate(new Date());
		//pageResource.setDeleteFlag(0);
		pageResourceMapper.insertPageResource(pageResource);
	       } else {
		pageResourceMapper.updatePageResource(pageResource);
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

	@javax.annotation.Resource(name = "com.glaf.web.mapper.PageResourceMapper")
	public void setPageResourceMapper(PageResourceMapper pageResourceMapper) {
		this.pageResourceMapper = pageResourceMapper;
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
		public PageResource getPageResourceByFilePath(String filePath) {
			// TODO Auto-generated method stub
			return pageResourceMapper.getPageResourceByFilePath(filePath);
		}

}
