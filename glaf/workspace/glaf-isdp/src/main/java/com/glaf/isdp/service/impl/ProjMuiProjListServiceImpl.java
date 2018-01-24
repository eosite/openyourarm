package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.domain.ProjMuiProjList;
import com.glaf.isdp.mapper.ProjMuiProjListMapper;
import com.glaf.isdp.query.ProjMuiProjListQuery;
import com.glaf.isdp.service.ProjMuiProjListService;

@Service("com.glaf.isdp.service.projMuiProjListService")
@Transactional(readOnly = true)
public class ProjMuiProjListServiceImpl implements ProjMuiProjListService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ProjMuiProjListMapper projMuiProjListMapper;

	public ProjMuiProjListServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			projMuiProjListMapper.deleteProjMuiProjListById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				projMuiProjListMapper.deleteProjMuiProjListById(id);
			}
		}
	}

	public int count(ProjMuiProjListQuery query) {
		query.ensureInitialized();
		return projMuiProjListMapper.getProjMuiProjListCount(query);
	}

	public List<ProjMuiProjList> list(ProjMuiProjListQuery query) {
		query.ensureInitialized();
		List<ProjMuiProjList> list = projMuiProjListMapper
				.getProjMuiProjLists(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getProjMuiProjListCountByQueryCriteria(ProjMuiProjListQuery query) {
		return projMuiProjListMapper.getProjMuiProjListCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ProjMuiProjList> getProjMuiProjListsByQueryCriteria(int start,
			int pageSize, ProjMuiProjListQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ProjMuiProjList> rows = sqlSessionTemplate.selectList(
				"getProjMuiProjLists", query, rowBounds);
		return rows;
	}

	public ProjMuiProjList getProjMuiProjList(Integer id) {
		if (id == null) {
			return null;
		}
		ProjMuiProjList projMuiProjList = projMuiProjListMapper
				.getProjMuiProjListById(id);
		return projMuiProjList;
	}

	@Transactional
	public void save(ProjMuiProjList projMuiProjList) {
		if (projMuiProjList.getIndexId() == null) {
			projMuiProjList.setIndexId(idGenerator.nextId("PROJ_MUIPROJLIST")
					.intValue());
			// projMuiProjList.setCreateDate(new Date());
			// projMuiProjList.setDeleteFlag(0);
			projMuiProjListMapper.insertProjMuiProjList(projMuiProjList);
		} else {
			projMuiProjListMapper.updateProjMuiProjList(projMuiProjList);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.ProjMuiProjListMapper")
	public void setProjMuiProjListMapper(
			ProjMuiProjListMapper projMuiProjListMapper) {
		this.projMuiProjListMapper = projMuiProjListMapper;
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
	public ProjMuiProjList getLocalProjMuiProjList(Integer intLocal) {
		return projMuiProjListMapper.getLocalProjMuiProjList(intLocal);
	}

}
