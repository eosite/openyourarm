package com.glaf.form.core.service;

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

import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.domain.ObjectCategory;
import com.glaf.form.core.mapper.FormTemplateMapper;
import com.glaf.form.core.mapper.MutilDataBeanMapper;
import com.glaf.form.core.mapper.ObjectCategoryMapper;
import com.glaf.form.core.query.ObjectCategoryQuery;
import com.glaf.form.core.service.MutilDataBeanService;

@Service("com.glaf.form.core.service.mutilDataBeanService")
@Transactional(readOnly = true) 
public class MutilDataBeanServiceImpl implements MutilDataBeanService {
	protected MutilDataBeanMapper mutilDataBeanMapper;
	
	
	@Override
	public void updateTableData(TableModel model) {
		// TODO Auto-generated method stub
		mutilDataBeanMapper.updateTableDataByPrimaryKey(model);
	}
	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.MutilDataBeanMapper")
	public void setMutilDataBeanMapper(MutilDataBeanMapper mutilDataBeanMapper) {
		this.mutilDataBeanMapper = mutilDataBeanMapper;
	}



}
