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
import com.glaf.isdp.domain.TreeProj;
import com.glaf.isdp.mapper.TreeProjMapper;
import com.glaf.isdp.query.TreeProjQuery;
import com.glaf.isdp.service.TreeProjService;

@Service("com.glaf.isdp.service.treeProjService")
@Transactional(readOnly = true)
public class TreeProjServiceImpl implements TreeProjService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreeProjMapper treeProjMapper;

	public TreeProjServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			treeProjMapper.deleteTreeProjById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				treeProjMapper.deleteTreeProjById(id);
			}
		}
	}

	public int count(TreeProjQuery query) {
		query.ensureInitialized();
		return treeProjMapper.getTreeProjCount(query);
	}

	public List<TreeProj> list(TreeProjQuery query) {
		query.ensureInitialized();
		List<TreeProj> list = treeProjMapper.getTreeProjs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreeProjCountByQueryCriteria(TreeProjQuery query) {
		return treeProjMapper.getTreeProjCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreeProj> getTreeProjsByQueryCriteria(int start, int pageSize,
			TreeProjQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreeProj> rows = sqlSessionTemplate.selectList("getTreeProjs",
				query, rowBounds);
		return rows;
	}

	public TreeProj getTreeProj(Integer id) {
		if (id == null) {
			return null;
		}
		TreeProj treeProj = treeProjMapper.getTreeProjById(id);
		return treeProj;
	}

	@Transactional
	public void save(TreeProj treeProj) {
		if (treeProj.getIndexId() == null) {
			treeProj.setIndexId(idGenerator.nextId("TREEPROJ").intValue());
			// treeProj.setCreateDate(new Date());
			// treeProj.setDeleteFlag(0);
			treeProjMapper.insertTreeProj(treeProj);
		} else {
			treeProjMapper.updateTreeProj(treeProj);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.TreeProjMapper")
	public void setTreeProjMapper(TreeProjMapper treeProjMapper) {
		this.treeProjMapper = treeProjMapper;
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
