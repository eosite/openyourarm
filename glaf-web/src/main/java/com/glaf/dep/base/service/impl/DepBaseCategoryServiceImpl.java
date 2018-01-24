package com.glaf.dep.base.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.fop.render.afp.fonts.CharacterSetOrientation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.dao.*;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.dep.base.mapper.*;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.DepBaseCategoryService;

@Service("com.glaf.dep.base.service.depBaseCategoryService")
@Transactional(readOnly = true)
public class DepBaseCategoryServiceImpl implements DepBaseCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseCategoryMapper depBaseCategoryMapper;

	public DepBaseCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			depBaseCategoryMapper.deleteDepBaseCategoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				depBaseCategoryMapper.deleteDepBaseCategoryById(id);
			}
		}
	}

	public int count(DepBaseCategoryQuery query) {
		return depBaseCategoryMapper.getDepBaseCategoryCount(query);
	}

	public List<DepBaseCategory> list(DepBaseCategoryQuery query) {
		List<DepBaseCategory> list = depBaseCategoryMapper
				.getDepBaseCategorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseCategoryCountByQueryCriteria(DepBaseCategoryQuery query) {
		return depBaseCategoryMapper.getDepBaseCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseCategory> getDepBaseCategorysByQueryCriteria(int start,
			int pageSize, DepBaseCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseCategory> rows = sqlSessionTemplate.selectList(
				"getDepBaseCategorys", query, rowBounds);
		return rows;
	}

	public DepBaseCategory getDepBaseCategory(Long id) {
		if (id == null) {
			return null;
		}
		DepBaseCategory depBaseCategory = depBaseCategoryMapper
				.getDepBaseCategoryById(id);
		return depBaseCategory;
	}

	@Transactional
	public void save(DepBaseCategory depBaseCategory) {
		if (depBaseCategory.getId() == null) {
			long id = idGenerator.nextId("DEP_BASE_CATEGORY");
			depBaseCategory.setId(id);
			String treeid = depBaseCategory.getTreeId();
			if (StringUtils.isNotEmpty(treeid)) {
				treeid = treeid + id + "|";
			} else {
				treeid = id + "|";
			}
			depBaseCategory.setTreeId(treeid);
			depBaseCategoryMapper.insertDepBaseCategory(depBaseCategory);
		} else {
			depBaseCategoryMapper.updateDepBaseCategory(depBaseCategory);
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseCategoryMapper")
	public void setDepBaseCategoryMapper(
			DepBaseCategoryMapper depBaseCategoryMapper) {
		this.depBaseCategoryMapper = depBaseCategoryMapper;
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
	public void updateDelFlagByTreeId(String treeId,String delFlag) {
		depBaseCategoryMapper.updateDelFlagByTreeId(treeId,delFlag);
	}

	@Override
	public DepBaseCategory getDepBaseCategorysByCode(String code) {
		if(DBUtils.ORACLE.equals(DBConnectionFactory.getDatabaseType())){
			return depBaseCategoryMapper.getDepBaseCategorysByCode_oracle(code);
		}
		return depBaseCategoryMapper.getDepBaseCategorysByCode(code);
	}

	@Override
	public String getNextCode(long pid) {
		String returnCode = "";
		DepBaseCategory pCategory = depBaseCategoryMapper.getDepBaseCategoryById(pid);
		if(pCategory==null){
			String maxCode = depBaseCategoryMapper.getMaxCodeByPid(-1L,null);
			char c = maxCode.charAt(0);
			returnCode = String.valueOf(((char)(c+1))+"000");
		}else{
			String maxCode = depBaseCategoryMapper.getMaxCodeByPid(pCategory.getId(),pCategory.getCode()+"-%");
			if(StringUtils.isEmpty(maxCode)){
				returnCode = pCategory.getCode()+"-1";
			}else{
				maxCode = maxCode.replaceAll(pCategory.getCode() + "-", "");
				maxCode = (Integer.parseInt(maxCode) + 1) + "";
				returnCode = pCategory.getCode()+"-"+maxCode; 
			}
		}
		return returnCode;
	}

}
