package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.isdp.domain.Pinfo;
import com.glaf.isdp.mapper.PinfoMapper;
import com.glaf.isdp.query.PinfoQuery;
import com.glaf.isdp.service.PinfoService;

@Service("com.glaf.isdp.service.pinfoService")
@Transactional(readOnly = true)
public class PinfoServiceImpl implements PinfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PinfoMapper pinfoMapper;

	public PinfoServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			pinfoMapper.deletePinfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				pinfoMapper.deletePinfoById(id);
			}
		}
	}

	public int count(PinfoQuery query) {
		query.ensureInitialized();
		return pinfoMapper.getPinfoCount(query);
	}

	public List<Pinfo> list(PinfoQuery query) {
		query.ensureInitialized();
		List<Pinfo> list = pinfoMapper.getPinfos(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getPinfoCountByQueryCriteria(PinfoQuery query) {
		return pinfoMapper.getPinfoCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<Pinfo> getPinfosByQueryCriteria(int start, int pageSize,
			PinfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Pinfo> rows = sqlSessionTemplate.selectList("getPinfos", query,
				rowBounds);
		return rows;
	}

	public Pinfo getPinfo(String id) {
		if (id == null) {
			return null;
		}
		Pinfo pinfo = pinfoMapper.getPinfoById(id);
		return pinfo;
	}

	@Transactional
	public void save(Pinfo pinfo) {
		if (StringUtils.isEmpty(pinfo.getId())) {
			pinfo.setId(idGenerator.getNextId("PINFO"));
			// pinfo.setCreateDate(new Date());
			// pinfo.setDeleteFlag(0);
			pinfoMapper.insertPinfo(pinfo);
		} else {
			pinfoMapper.updatePinfo(pinfo);
		}
	}

	@Override
	@Transactional
	public int update(List<FieldInterface> interfaceList,Map<String, Object> paramsMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE pinfo SET ");
		int index = 1;
		for(FieldInterface field : interfaceList){
			
			switch(field.getDtype()){
				case "string":
				case "char":
					Object value = paramsMap.get(field.getDname())==null?"":paramsMap.get(field.getDname());
					sb.append(field.getDname()).append("='").append(value).append("'");
					break;
				case "int":
				case "r8":
				case "i4":
					sb.append(field.getDname()).append("=").append(paramsMap.get(field.getDname()));
					break;
				case "datetime":
					Date date = (Date)paramsMap.get(field.getDname());
					sb.append(field.getDname()).append("='").append(new SimpleDateFormat("yyyy-MM-dd").format(date)).append("'");
					
					break;
				default:
					value = paramsMap.get(field.getDname())==null?"":paramsMap.get(field.getDname());
					sb.append(field.getDname()).append("='").append(value).append("'");
			}
			if(index<interfaceList.size()){
				sb.append(",");
				index++;
			}
		}
		sb.append(" WHERE ").append("id='").append(paramsMap.get("id")).append("'");
		
		return jdbcTemplate.update(sb.toString());
		
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

	@Override
	public Pinfo getPinfoListByDomainIndexId(Integer domainIndexId) {
		return pinfoMapper.getPinfoListByDomainIndexId(domainIndexId);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.PinfoMapper")
	public void setPinfoMapper(PinfoMapper pinfoMapper) {
		this.pinfoMapper = pinfoMapper;
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
