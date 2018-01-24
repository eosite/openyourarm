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
import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.mapper.TreepNameMapper;
import com.glaf.isdp.query.TreepNameQuery;
import com.glaf.isdp.service.TreepNameService;

@Service("com.glaf.isdp.service.treepNameService")
@Transactional(readOnly = true)
public class TreepNameServiceImpl implements TreepNameService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreepNameMapper treepNameMapper;

	public TreepNameServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			treepNameMapper.deleteTreepNameById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				treepNameMapper.deleteTreepNameById(id);
			}
		}
	}

	public int count(TreepNameQuery query) {
		query.ensureInitialized();
		return treepNameMapper.getTreepNameCount(query);
	}

	public List<TreepName> list(TreepNameQuery query) {
		query.ensureInitialized();
		List<TreepName> list = treepNameMapper.getTreepNames(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreepNameCountByQueryCriteria(TreepNameQuery query) {
		return treepNameMapper.getTreepNameCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreepName> getTreepNamesByQueryCriteria(int start,
			int pageSize, TreepNameQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreepName> rows = sqlSessionTemplate.selectList("getTreepNames",
				query, rowBounds);
		return rows;
	}

	public TreepName getTreepName(Integer id) {
		if (id == null) {
			return null;
		}
		TreepName treepName = treepNameMapper.getTreepNameById(id);
		return treepName;
	}

	@Transactional
	public void save(TreepName treepName) {
		if (treepName.getIndexId() == null) {
			treepName.setIndexId(idGenerator.nextId("TREEPNAME").intValue());
			// treepName.setCreateDate(new Date());
			// treepName.setDeleteFlag(0);
			treepNameMapper.insertTreepName(treepName);
		} else {
			treepNameMapper.updateTreepName(treepName);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.TreepNameMapper")
	public void setTreepNameMapper(TreepNameMapper treepNameMapper) {
		this.treepNameMapper = treepNameMapper;
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
	public TreepName getTreepNameByDomainIndexId(Integer domainIndexId) {
		return treepNameMapper.getTreepNameByDomainIndexId(domainIndexId);
	}

}
