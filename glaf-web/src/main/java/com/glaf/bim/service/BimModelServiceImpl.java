package com.glaf.bim.service;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.bim.domain.BimModel;
import com.glaf.core.id.IdGenerator;
import com.glaf.datamgr.jdbc.Crud;
import com.glaf.datamgr.jdbc.JdbcInsert;

@Service("com.glaf.bim.service.bimModelService")
@Transactional(readOnly = true)
public class BimModelServiceImpl implements BimModelService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	public BimModelServiceImpl() {

	}

	@Override
	public void saveMsg(String bimId, List<BimModel> tree) {
		Crud<BimModel> crud = new JdbcInsert<BimModel>();
		Connection connection = null;
		try {
			connection = DataSourceUtils.getConnection(//
					jdbcTemplate.getDataSource());
			crud.setConnection(connection);
			crud.execute("DELETE FROM FORM_BIMMODEL WHERE MODELCODE_ = '" + bimId + "'");
			for (BimModel bm : tree) {
				bm.setModelCode(bimId);
				bm.setId(idGenerator.getNextId("FORM_BIMMODEL_SEQ"));
				crud.addBatch(bm);
				logger.debug(bm.toString());
			}
			crud.executeBatch();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run [saveMsg()] error", ex);
			throw new RuntimeException(ex);
		}

	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
