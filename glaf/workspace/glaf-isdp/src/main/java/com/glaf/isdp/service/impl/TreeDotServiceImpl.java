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
import com.glaf.isdp.domain.TreeDot;
import com.glaf.isdp.mapper.TreeDotMapper;
import com.glaf.isdp.query.TreeDotQuery;
import com.glaf.isdp.service.TreeDotService;

@Service("com.glaf.isdp.service.treeDotService")
@Transactional(readOnly = true)
public class TreeDotServiceImpl implements TreeDotService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreeDotMapper treeDotMapper;

	public TreeDotServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			treeDotMapper.deleteTreeDotById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				treeDotMapper.deleteTreeDotById(id);
			}
		}
	}

	public int count(TreeDotQuery query) {
		query.ensureInitialized();
		return treeDotMapper.getTreeDotCount(query);
	}

	public List<TreeDot> list(TreeDotQuery query) {
		query.ensureInitialized();
		List<TreeDot> list = treeDotMapper.getTreeDots(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreeDotCountByQueryCriteria(TreeDotQuery query) {
		return treeDotMapper.getTreeDotCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreeDot> getTreeDotsByQueryCriteria(int start, int pageSize,
			TreeDotQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreeDot> rows = sqlSessionTemplate.selectList("getTreeDots",
				query, rowBounds);
		return rows;
	}

	public TreeDot getTreeDot(Integer id) {
		if (id == null) {
			return null;
		}
		TreeDot treeDot = treeDotMapper.getTreeDotById(id);
		return treeDot;
	}

	@Transactional
	public void save(TreeDot treeDot) {
		if (treeDot.getIndexId() == null) {
			treeDot.setIndexId(idGenerator.nextId("TREEDOT").intValue());
			// treeDot.setCreateDate(new Date());
			// treeDot.setDeleteFlag(0);
			treeDotMapper.insertTreeDot(treeDot);
		} else {
			treeDotMapper.updateTreeDot(treeDot);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.TreeDotMapper")
	public void setTreeDotMapper(TreeDotMapper treeDotMapper) {
		this.treeDotMapper = treeDotMapper;
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
	public List<TreeDot> getTreeDotsAndChildCountByParentId(Integer parentId){
		return this.treeDotMapper.getTreeDotsAndChildCountByParentId(parentId);
	}

	@Override
	public List<TreeDot> getTreeDotsAndChildCountByIdLike(String idLike) {
		return treeDotMapper.getTreeDotsAndChildCountByIdLike(idLike);
	}

}
