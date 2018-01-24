package com.glaf.isdp.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.mapper.FieldInterfaceMapper;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.DBUtils;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.mapper.CellDataFieldMapper;
import com.glaf.isdp.mapper.CellDataTableMapper;
import com.glaf.isdp.mapper.TableActionMapper;
import com.glaf.isdp.service.TableActionService;
import com.glaf.isdp.util.ColumnTypeUtils;
import com.glaf.isdp.util.TableActionUtils;

@Service("com.glaf.isdp.service.tableActionService")
@Transactional(readOnly = true)
public class TableActionServiceImpl implements TableActionService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableActionMapper tableActionMapper;

	protected CellDataFieldMapper cellDataFieldMapper;

	protected FieldInterfaceMapper fieldInterfaceMapper;
	
	protected CellDataTableMapper cellDataTableMapper;

	public TableActionServiceImpl() {

	}

	@Transactional
	@Override
	public void insertTableByWhereCause(String tableName, String fieldString,
			String valueString) {

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}
		tableActionMapper.insertTableByWhereCause(tableName, fieldString,
				valueString);

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("fieldString", fieldString);
//		jsonObject.put("valueString", valueString);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("insert");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);

	}

	@Transactional
	@Override
	public void updateTableByWhereCause(String tableName, String fieldString,
			String whereCondition) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}
		tableActionMapper.updateTableByWhereCause(tableName, fieldString,
				whereCondition);

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("fieldString", fieldString);
//		jsonObject.put("whereCondition", whereCondition);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("update");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void deleteTableByWhereCause(String tableName, String whereCondition) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}
		tableActionMapper.deleteTableByWhereCause(tableName, whereCondition);

		// 插入日志
//		 JSONObject jsonObject = new JSONObject();
//		 jsonObject.put("tableName", tableName);
//		 jsonObject.put("whereCondition", whereCondition);
//		 jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//		
//		 SysDataLog log = new SysDataLog();
//		 log.setActorId(Authentication.getAuthenticatedActorId());
//		 log.setBusinessKey("");// 业务表主键值
//		 log.setCreateTime(new Date());
//		 log.setOperate("delete");
//		 log.setModuleId(tableName.toUpperCase());
//		 log.setContent(jsonObject.toJSONString());
//		 this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void createDefaultTable(String tableName) {
		
		TableActionUtils.createDefaultTable(tableName);
		
//		if (!DBUtils.isAllowedTable(tableName)) {
//			throw new RuntimeException("不允许访问系统表。");
//		}
//		
//		StringBuffer sb = new StringBuffer();
//		sb.append("CREATE TABLE ").append(tableName).append(" ( ");
//		sb.append("id varchar(100)  PRIMARY KEY,");
//		sb.append("topid varchar(100),");
//		sb.append("index_id int ,parent_id int ,");
//		sb.append("treeid varchar(100) ,");
//		sb.append("nlevel int ,listno int ,");
//		sb.append("old_id varchar(50) ,tzflag int ,");
//		sb.append("repflag int ,tofileflag int ,");
//		if(StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
//			sb.append("ctime date ,type_id varchar(50) ,");
//		}else{
//			sb.append("ctime datetime ,type_id varchar(50) ,");
//		}
//		sb.append("type_baseid varchar(50) ,cell_locate varchar(50) ,lockrec int ,");
//		sb.append("createtime datetime,updatetime datetime,isdel int ,");
//		sb.append("unitid varchar(100) ,sys_id varchar(100),");
//		sb.append("lessessid varchar(100),creater varchar(50),updater varchar(50))");
//		logger.debug("jdbc execute sql:" + sb.toString());
//		
//		jdbcTemplate.execute(sb.toString());

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//		jsonObject.put("create_table_sql", sb.toString());
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("create");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void dropTable(String tableName) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		if(DBUtils.tableExists(tableName)){
			String sql = "drop table " + tableName;
			logger.debug("jdbc execute sql:" + sql);
			jdbcTemplate.execute(sql);
		}

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//		jsonObject.put("drop_sql", sql);
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("drop");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);

	}

	@Transactional
	@Override
	public void alterColumn(String tableName, String alterType,
			String columnName, String columnType) {
		/*if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("ALTER TABLE ").append(tableName).append(" ");
		if ("add".equalsIgnoreCase(alterType)) {
			// 新增列
			sb.append(alterType).append(" ").append(columnName);
			sb.append(" ").append(columnType);
		} else if ("modify".equalsIgnoreCase(alterType)) {
			// 修改列
			sb.append(" ALTER COLUMN ").append(columnName);
			sb.append(" ").append(columnType);
		} else if ("drop".equalsIgnoreCase(alterType)) {
			// 删除列
			sb.append(alterType).append(" ").append(" COLUMN ");
			sb.append(columnName);
		}
		logger.debug("jdbc execute sql:" + sb.toString());

		jdbcTemplate.execute(sb.toString());*/
		ColumnTypeUtils.alterColumn(tableName, alterType, columnName, columnType, jdbcTemplate);

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//		jsonObject.put("alter_sql", sb.toString());
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("alter");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void executeSQL(String sql, String tableName) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		logger.debug("jdbc execute sql:" + sql);
		jdbcTemplate.execute(sql);

		// 插入日志
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("tableName", tableName);
//		jsonObject.put("actorId", Authentication.getAuthenticatedActorId());
//		jsonObject.put("execute_sql", sql);
//
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("");
//		log.setModuleId(tableName.toUpperCase());
//		log.setContent(jsonObject.toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void addColumn(String tableName, String fieldName,
			String columnType, CellDataField cellDataField,
			FieldInterface fieldInterface) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		// 在表中添加字段
		this.alterColumn(tableName, "add", fieldName, columnType);
		
		//如果有引用，则在业务表中增加引用表的关联id
		if(StringUtils.isNotEmpty(cellDataField.getUserIndex())){
			this.alterColumn(tableName, "add", cellDataField.getUserIndex(), "VARCHAR(100)");
		}

		// 添加Cell_Data_Field数据
		String actorId = cellDataField.getUserId();
		String id = idGenerator.getNextId("cell_data_field", "id", actorId);
		cellDataField.setId(id);
		cellDataFieldMapper.insertCellDataField(cellDataField);

//		SysDataLog log = new SysDataLog();
//		log.setActorId(actorId);
//		log.setBusinessKey("id");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("insert");
//		log.setModuleId("cell_data_field");
//		log.setContent(cellDataField.toJsonObject().toJSONString());
//		this.sysDataLogService.save(log);

		// 添加Interface数据
		id = idGenerator.getNextId("interface", "listId", actorId);
		fieldInterface.setListId(id);
		fieldInterfaceMapper.insertFieldInterface(fieldInterface);

//		log = new SysDataLog();
//		log.setActorId(actorId);
//		log.setBusinessKey("listId");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("insert");
//		log.setModuleId("interface");
//		log.setContent(fieldInterface.toJsonObject().toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void modifyColumn(String tableName, String columnType,
			CellDataField cellDataField, FieldInterface fieldInterface) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		this.alterColumn(tableName, "modify", fieldInterface.getDname(),
				columnType);
		fieldInterfaceMapper.updateFieldInterface(fieldInterface);

//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("listId");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("update");
//		log.setModuleId("interface");
//		log.setContent(fieldInterface.toJsonObject().toJSONString());
//		this.sysDataLogService.save(log);

		cellDataFieldMapper.updateCellDataField(cellDataField);

//		log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("id");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("update");
//		log.setModuleId("cell_data_field");
//		log.setContent(cellDataField.toJsonObject().toJSONString());
//		this.sysDataLogService.save(log);
	}

	@Transactional
	@Override
	public void deleteColumn(String tableName, String dname, String listId,
			String fieldId) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		cellDataFieldMapper.deleteCellDataFieldById(fieldId);
//		SysDataLog log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("id");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("delete");
//		log.setModuleId("cell_data_field");
//		log.setContent("delete by id:" + fieldId);
//		this.sysDataLogService.save(log);

		fieldInterfaceMapper.deleteFieldInterfaceById(listId);

//		log = new SysDataLog();
//		log.setActorId(Authentication.getAuthenticatedActorId());
//		log.setBusinessKey("listId");// 业务表主键值
//		log.setCreateTime(new Date());
//		log.setOperate("delete");
//		log.setModuleId("interface");
//		log.setContent("delete by listId:" + listId);
//		this.sysDataLogService.save(log);

		this.alterColumn(tableName, "drop", dname, "");
	}

	@Transactional
	@Override
	public void deleteTable(List<String> tableids, List<String> tableNames) {
		// 删除Interface记录
		this.deleteTableByWhereCause("Interface", " and frmtype in ('"
				+ StringUtils.join(tableids, "','") + "')");

		// 删除Cell_Data_Field记录
		this.deleteTableByWhereCause("cell_data_table", " and id in ('"
				+ StringUtils.join(tableids, "','") + "')");
		// 删除表
		for (String tableName : tableNames) {
			if (!DBUtils.isAllowedTable(tableName)) {
				throw new RuntimeException("不允许访问系统表。");
			}
			this.dropTable(tableName);
		}
	}
	
	

	@Transactional
	@Override
	public void createTable(String tableName, CellDataTable cellDataTable) {
		this.createDefaultTable(tableName);
		
		String id = idGenerator.getNextId("cell_data_table", "id", cellDataTable.getUserId());
		cellDataTable.setId(id);
		cellDataTableMapper.insertCellDataTable(cellDataTable);
		
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.TableActionMapper")
	public void setTableActionMapper(TableActionMapper tableActionMapper) {
		this.tableActionMapper = tableActionMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.CellDataFieldMapper")
	public void setCellDataFieldMapper(CellDataFieldMapper cellDataFieldMapper) {
		this.cellDataFieldMapper = cellDataFieldMapper;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceMapper(
			FieldInterfaceMapper fieldInterfaceMapper) {
		this.fieldInterfaceMapper = fieldInterfaceMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource(name="com.glaf.isdp.mapper.CellDataTableMapper")
	public void setCellDataTableMapper(CellDataTableMapper cellDataTableMapper) {
		this.cellDataTableMapper = cellDataTableMapper;
	}

	@Override
	public boolean isExistsTable(String tableName) {
		try {
			
			return DBUtils.tableExists(tableName);
			
			/*Connection conn = jdbcTemplate.getDataSource().getConnection();
			ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null);
			if(rs.next()){
				return true;
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
