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
import com.glaf.isdp.domain.CellTreeDot;
import com.glaf.isdp.mapper.CellTreeDotMapper;
import com.glaf.isdp.query.CellTreeDotQuery;
import com.glaf.isdp.service.CellTreeDotService;

@Service("com.glaf.isdp.service.cellTreeDotService")
@Transactional(readOnly = true)
public class CellTreeDotServiceImpl implements CellTreeDotService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellTreeDotMapper cellTreeDotMapper;

	public CellTreeDotServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			cellTreeDotMapper.deleteCellTreeDotById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				cellTreeDotMapper.deleteCellTreeDotById(id);
			}
		}
	}

	public int count(CellTreeDotQuery query) {
		query.ensureInitialized();
		return cellTreeDotMapper.getCellTreeDotCount(query);
	}

	public List<CellTreeDot> list(CellTreeDotQuery query) {
		query.ensureInitialized();
		List<CellTreeDot> list = cellTreeDotMapper.getCellTreeDots(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellTreeDotCountByQueryCriteria(CellTreeDotQuery query) {
		return cellTreeDotMapper.getCellTreeDotCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellTreeDot> getCellTreeDotsByQueryCriteria(int start,
			int pageSize, CellTreeDotQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellTreeDot> rows = sqlSessionTemplate.selectList(
				"getCellTreeDots", query, rowBounds);
		return rows;
	}

	public CellTreeDot getCellTreeDot(Integer id) {
		if (id == null) {
			return null;
		}
		CellTreeDot cellTreeDot = cellTreeDotMapper.getCellTreeDotById(id);
		return cellTreeDot;
	}

	@Transactional
	public void save(CellTreeDot cellTreeDot) {
		if (cellTreeDot.getIndexId() == null) {
			cellTreeDot.setIndexId(idGenerator.nextId("CELL_TREEDOT")
					.intValue());
			// cellTreeDot.setCreateDate(new Date());
			// cellTreeDot.setDeleteFlag(0);
			cellTreeDotMapper.insertCellTreeDot(cellTreeDot);
		} else {
			cellTreeDotMapper.updateCellTreeDot(cellTreeDot);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellTreeDotMapper")
	public void setCellTreeDotMapper(CellTreeDotMapper cellTreeDotMapper) {
		this.cellTreeDotMapper = cellTreeDotMapper;
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
