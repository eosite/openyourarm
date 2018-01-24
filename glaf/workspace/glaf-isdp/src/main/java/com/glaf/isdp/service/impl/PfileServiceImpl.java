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
import com.glaf.isdp.domain.Pfile;
import com.glaf.isdp.mapper.PfileMapper;
import com.glaf.isdp.query.PfileQuery;
import com.glaf.isdp.service.PfileService;

@Service("com.glaf.isdp.service.pfileService")
@Transactional(readOnly = true)
public class PfileServiceImpl implements PfileService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PfileMapper pfileMapper;

	public PfileServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			pfileMapper.deletePfileById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				pfileMapper.deletePfileById(id);
			}
		}
	}

	public int count(PfileQuery query) {
		query.ensureInitialized();
		return pfileMapper.getPfileCount(query);
	}

	public List<Pfile> list(PfileQuery query) {
		query.ensureInitialized();
		List<Pfile> list = pfileMapper.getPfiles(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getPfileCountByQueryCriteria(PfileQuery query) {
		return pfileMapper.getPfileCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Pfile> getPfilesByQueryCriteria(int start, int pageSize,
			PfileQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Pfile> rows = sqlSessionTemplate.selectList("getPfiles", query,
				rowBounds);
		return rows;
	}

	public Pfile getPfile(String id) {
		if (id == null) {
			return null;
		}
		Pfile pfile = pfileMapper.getPfileById(id);
		return pfile;
	}

	@Transactional
	public void save(Pfile pfile) {
		if (StringUtils.isEmpty(pfile.getId())) {
			pfile.setId(idGenerator.getNextId("PFILE"));
			// pfile.setCreateDate(new Date());
			// pfile.setDeleteFlag(0);
			pfileMapper.insertPfile(pfile);
		} else {
			pfileMapper.updatePfile(pfile);
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

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.PfileMapper")
	public void setPfileMapper(PfileMapper pfileMapper) {
		this.pfileMapper = pfileMapper;
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
	public List<Pfile> getPfileDataSum(List<String> idLikeList, String sDate, String eDate) {
		return pfileMapper.getPfileDataSum(idLikeList, sDate, eDate);
	}
}
