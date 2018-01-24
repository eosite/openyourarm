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
import com.glaf.isdp.domain.FileDot;
import com.glaf.isdp.mapper.FileDotMapper;
import com.glaf.isdp.query.FileDotQuery;
import com.glaf.isdp.service.FileDotService;

@Service("com.glaf.isdp.service.fileDotService")
@Transactional(readOnly = true)
public class FileDotServiceImpl implements FileDotService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FileDotMapper fileDotMapper;

	public FileDotServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			fileDotMapper.deleteFileDotById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> fileIDs) {
		if (fileIDs != null && !fileIDs.isEmpty()) {
			for (String id : fileIDs) {
				fileDotMapper.deleteFileDotById(id);
			}
		}
	}

	public int count(FileDotQuery query) {
		query.ensureInitialized();
		return fileDotMapper.getFileDotCount(query);
	}

	public List<FileDot> list(FileDotQuery query) {
		query.ensureInitialized();
		List<FileDot> list = fileDotMapper.getFileDots(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFileDotCountByQueryCriteria(FileDotQuery query) {
		return fileDotMapper.getFileDotCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FileDot> getFileDotsByQueryCriteria(int start, int pageSize,
			FileDotQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FileDot> rows = sqlSessionTemplate.selectList("getFileDots",
				query, rowBounds);
		return rows;
	}

	public FileDot getFileDot(String id) {
		if (id == null) {
			return null;
		}
		FileDot fileDot = fileDotMapper.getFileDotById(id);
		return fileDot;
	}

	@Transactional
	public void save(FileDot fileDot) {
		if (StringUtils.isEmpty(fileDot.getFileID())) {
			fileDot.setFileID(idGenerator.getNextId("FILEDOT"));
			// fileDot.setCreateDate(new Date());
			// fileDot.setDeleteFlag(0);
			fileDotMapper.insertFileDot(fileDot);
		} else {
			fileDotMapper.updateFileDot(fileDot);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FileDotMapper")
	public void setFileDotMapper(FileDotMapper fileDotMapper) {
		this.fileDotMapper = fileDotMapper;
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
