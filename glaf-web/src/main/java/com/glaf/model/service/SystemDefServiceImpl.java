package com.glaf.model.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.service.FormPageService;
import com.glaf.model.domain.SubSystemDef;
import com.glaf.model.domain.SystemDef;
import com.glaf.model.mapper.SystemDefMapper;
import com.glaf.model.mapper.SystemFuncMapper;
import com.glaf.model.mapper.SystemProcDefMapper;
import com.glaf.model.query.SystemDefQuery;

@Service("com.glaf.model.service.systemDefService")
@Transactional(readOnly = true)
public class SystemDefServiceImpl implements SystemDefService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemDefMapper systemDefMapper;

	protected SubSystemDefService subSystemDefService;
	
	protected SystemFuncMapper systemFuncMapper;
	
	protected SystemProcDefMapper systemProDefMapper;
	
	protected FormPageService formPageService;

	public SystemDefServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			systemDefMapper.deleteSystemDefById(id);
		}
	}
    /**
     * 系統逻辑删除
     */
	@Transactional
	public void deleteSystem(String id) {
		systemProDefMapper.deleteSystemProcDefBySysId(id);
		systemFuncMapper.deleteSystemFuncBySysId(id);
		subSystemDefService.deleteSubSystemDefBySysId(id);
		systemDefMapper.deleteSystemDefBySysId(id);
	}

	@Transactional
	public void deleteByIds(List<String> sysIds) {
		if (sysIds != null && !sysIds.isEmpty()) {
			for (String id : sysIds) {
				systemDefMapper.deleteSystemDefById(id);
			}
		}
	}

	public int count(SystemDefQuery query) {
		return systemDefMapper.getSystemDefCount(query);
	}

	public List<SystemDef> list(SystemDefQuery query) {
		List<SystemDef> list = systemDefMapper.getSystemDefs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSystemDefCountByQueryCriteria(SystemDefQuery query) {
		return systemDefMapper.getSystemDefCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemDef> getSystemDefsByQueryCriteria(int start, int pageSize, SystemDefQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemDef> rows = sqlSessionTemplate.selectList("getSystemDefs", query, rowBounds);
		return rows;
	}

	public SystemDef getSystemDef(String id) {
		if (id == null) {
			return null;
		}
		SystemDef systemDef = systemDefMapper.getSystemDefById(id);
		return systemDef;
	}

	@Transactional
	public void save(SystemDef systemDef) {
		if (systemDef.getSysId() == null) {
			systemDef.setSysId(UUID32.getUUID());
			systemDef.setCreateTime(new Date());
			systemDef.setUpdateTime(new Date());
			// systemDef.setDeleteFlag(0);
			systemDefMapper.insertSystemDef(systemDef);
			SubSystemDef subSyStemDef = new SubSystemDef();
			subSyStemDef.setSysId(systemDef.getSysId());
			subSyStemDef.setName(systemDef.getSysName());
			subSyStemDef.setParentId_("0");
			subSyStemDef.setDeleteFlag(0);
			subSyStemDef.setEleType("subsystem");
			subSyStemDef.setDesc(systemDef.getSysDesc());
			subSyStemDef.setCreateTime(new Date());
			subSyStemDef.setCreateBy(systemDef.getCreateBy());
			subSystemDefService.save(subSyStemDef);
		} else {
			systemDef.setUpdateTime(new Date());
			systemDefMapper.updateSystemDef(systemDef);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.model.mapper.SystemDefMapper")
	public void setSystemDefMapper(SystemDefMapper systemDefMapper) {
		this.systemDefMapper = systemDefMapper;
	}

	@javax.annotation.Resource
	public void setSubSystemDefService(SubSystemDefService subSystemDefService) {
		this.subSystemDefService = subSystemDefService;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@javax.annotation.Resource
	public void setSystemFuncMapper(SystemFuncMapper systemFuncMapper) {
		this.systemFuncMapper = systemFuncMapper;
	}
	@javax.annotation.Resource
	public void setSystemProDefMapper(SystemProcDefMapper systemProDefMapper) {
		this.systemProDefMapper = systemProDefMapper;
	}
	@javax.annotation.Resource
	public void setFormPageService(FormPageService formPageService) {
		this.formPageService = formPageService;
	}

	@Override
	public void publish(SystemDef systemDef) {
		// TODO Auto-generated method stub
		systemDef.setUpdateTime(new Date());
		systemDefMapper.updateSystemDef(systemDef);
		//产生定义平台系统目录结构
	}

}
