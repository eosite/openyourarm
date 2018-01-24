package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.mapper.CellRepInfoMapper;
import com.glaf.isdp.query.CellRepInfoQuery;
import com.glaf.isdp.service.CellRepInfoService;

@Service("com.glaf.isdp.service.cellRepInfoService")
@Transactional(readOnly = true)
public class CellRepInfoServiceImpl implements CellRepInfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CellRepInfoMapper cellRepInfoMapper;

	public CellRepInfoServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			cellRepInfoMapper.deleteCellRepInfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> listIds) {
		if (listIds != null && !listIds.isEmpty()) {
			for (String id : listIds) {
				cellRepInfoMapper.deleteCellRepInfoById(id);
			}
		}
	}

	public int count(CellRepInfoQuery query) {
		query.ensureInitialized();
		return cellRepInfoMapper.getCellRepInfoCount(query);
	}

	public List<CellRepInfo> list(CellRepInfoQuery query) {
		query.ensureInitialized();
		List<CellRepInfo> list = cellRepInfoMapper.getCellRepInfos(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCellRepInfoCountByQueryCriteria(CellRepInfoQuery query) {
		return cellRepInfoMapper.getCellRepInfoCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CellRepInfo> getCellRepInfosByQueryCriteria(int start,
			int pageSize, CellRepInfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CellRepInfo> rows = sqlSessionTemplate.selectList(
				"getCellRepInfos", query, rowBounds);
		return rows;
	}

	public CellRepInfo getCellRepInfo(String id) {
		if (id == null) {
			return null;
		}
		CellRepInfo cellRepInfo = cellRepInfoMapper.getCellRepInfoById(id);
		return cellRepInfo;
	}

	@Transactional
	public void save(CellRepInfo cellRepInfo) {
		if (StringUtils.isEmpty(cellRepInfo.getListId())) {
			cellRepInfo.setListId(idGenerator.getNextId("CELL_REPINFO"));
			// cellRepInfo.setCreateDate(new Date());
			// cellRepInfo.setDeleteFlag(0);
			cellRepInfoMapper.insertCellRepInfo(cellRepInfo);
		} else {
			cellRepInfoMapper.updateCellRepInfo(cellRepInfo);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellRepInfoMapper")
	public void setCellRepInfoMapper(CellRepInfoMapper cellRepInfoMapper) {
		this.cellRepInfoMapper = cellRepInfoMapper;
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
