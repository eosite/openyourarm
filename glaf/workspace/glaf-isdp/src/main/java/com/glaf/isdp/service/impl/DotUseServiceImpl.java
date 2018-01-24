package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

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
import com.glaf.isdp.domain.DotUse;
import com.glaf.isdp.mapper.DotUseMapper;
import com.glaf.isdp.query.DotUseQuery;
import com.glaf.isdp.service.DotUseService;

@Service("com.glaf.isdp.service.dotUseService")
@Transactional(readOnly = true)
public class DotUseServiceImpl implements DotUseService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DotUseMapper dotUseMapper;

	public DotUseServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			dotUseMapper.deleteDotUseById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> fileIDs) {
		if (fileIDs != null && !fileIDs.isEmpty()) {
			for (String id : fileIDs) {
				dotUseMapper.deleteDotUseById(id);
			}
		}
	}

	public int count(DotUseQuery query) {
		query.ensureInitialized();
		return dotUseMapper.getDotUseCount(query);
	}

	public List<DotUse> list(DotUseQuery query) {
		query.ensureInitialized();
		List<DotUse> list = dotUseMapper.getDotUses(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDotUseCountByQueryCriteria(DotUseQuery query) {
		return dotUseMapper.getDotUseCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DotUse> getDotUsesByQueryCriteria(int start, int pageSize, DotUseQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DotUse> rows = sqlSessionTemplate.selectList("getDotUses", query, rowBounds);
		return rows;
	}

	public DotUse getDotUse(String id) {
		if (id == null) {
			return null;
		}
		DotUse dotUse = dotUseMapper.getDotUseById(id);
		return dotUse;
	}

	@Transactional
	public void save(DotUse dotUse) {
		if (StringUtils.isEmpty(dotUse.getFileID())) {
			dotUse.setFileID(idGenerator.getNextId("DOTUSE"));
			// dotUse.setCreateDate(new Date());
			// dotUse.setDeleteFlag(0);
			dotUseMapper.insertDotUse(dotUse);
		} else {
			dotUseMapper.updateDotUse(dotUse);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
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

	@Override
	public int getDotUseCellFillFormCountByIndexId(int indexId) {
		return dotUseMapper.getDotUseCellFillFormCountByIndexId(indexId);
	}

	@Override
	public List<Map<String, Object>> getDotUseCellFillFormByIndexId(int start, int pageSize, int indexId) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> rows = sqlSessionTemplate.selectList("getDotUseCellFillFormByIndexId", indexId,
				rowBounds);
		return rows;
	}

	@Override
	public int getDotUseCellFillFormCountByTreepinfoId(String treepinfoId) {
		return dotUseMapper.getDotUseCellFillFormCountByTreepinfoId(treepinfoId);
	}

	@Override
	public List<Map<String, Object>> getDotUseCellFillFormByTreepinfoId(int start, int pageSize, String treepinfoId) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> rows = sqlSessionTemplate.selectList("getDotUseCellFillFormByTreepinfoId",
				treepinfoId, rowBounds);
		return rows;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.DotUseMapper")
	public void setDotUseMapper(DotUseMapper dotUseMapper) {
		this.dotUseMapper = dotUseMapper;
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
