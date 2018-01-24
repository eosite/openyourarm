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
import com.glaf.isdp.domain.FileAtt;
import com.glaf.isdp.mapper.FileAttMapper;
import com.glaf.isdp.query.FileAttQuery;
import com.glaf.isdp.service.FileAttService;

@Service("com.glaf.isdp.service.fileAttService")
@Transactional(readOnly = true)
public class FileAttServiceImpl implements FileAttService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FileAttMapper fileAttMapper;

	public FileAttServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			fileAttMapper.deleteFileAttById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> fileIDs) {
		if (fileIDs != null && !fileIDs.isEmpty()) {
			for (String id : fileIDs) {
				fileAttMapper.deleteFileAttById(id);
			}
		}
	}

	public int count(FileAttQuery query) {
		query.ensureInitialized();
		return fileAttMapper.getFileAttCount(query);
	}

	public List<FileAtt> list(FileAttQuery query) {
		query.ensureInitialized();
		List<FileAtt> list = fileAttMapper.getFileAtts(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFileAttCountByQueryCriteria(FileAttQuery query) {
		return fileAttMapper.getFileAttCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FileAtt> getFileAttsByQueryCriteria(int start, int pageSize,
			FileAttQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FileAtt> rows = sqlSessionTemplate.selectList("getFileAtts",
				query, rowBounds);
		return rows;
	}

	public FileAtt getFileAtt(String id) {
		if (id == null) {
			return null;
		}
		FileAtt fileAtt = fileAttMapper.getFileAttById(id);
		return fileAtt;
	}

	@Transactional
	public void save(FileAtt fileAtt) {
		if (StringUtils.isEmpty(fileAtt.getFileID())) {
			fileAtt.setFileID(idGenerator.getNextId("FILEATT"));
			// fileAtt.setCreateDate(new Date());
			// fileAtt.setDeleteFlag(0);
			fileAttMapper.insertFileAtt(fileAtt);
		} else {
			fileAttMapper.updateFileAtt(fileAtt);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.FileAttMapper")
	public void setFileAttMapper(FileAttMapper fileAttMapper) {
		this.fileAttMapper = fileAttMapper;
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
