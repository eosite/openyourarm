package com.glaf.isdp.service;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.config.Environment;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.Authentication;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.mapper.RdataTableMapper;
import com.glaf.isdp.query.RdataTableQuery;
import com.glaf.isdp.util.ColumnTypeUtils;
import com.glaf.isdp.util.TableActionUtils;

@Service("com.glaf.isdp.service.rdataTableService")
@Transactional(readOnly = true)
public class RdataTableServiceImpl implements RdataTableService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected RdataTableMapper rdataTableMapper;

	@Autowired
	protected RdataFieldService rdataFieldService;

	@Autowired
	protected RinterfaceService rinterfaceService;

	public RdataTableServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<RdataTable> list) {
		for (RdataTable rdataTable : list) {
			if (StringUtils.isEmpty(rdataTable.getId())) {
				rdataTable.setId(idGenerator.getNextId("R_DATA_TABLE"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// rdataTableMapper.bulkInsertRdataTable_oracle(list);
		} else {
			// rdataTableMapper.bulkInsertRdataTable(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			rdataTableMapper.deleteRdataTableById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				rdataTableMapper.deleteRdataTableById(id);
			}
		}
	}

	public int count(RdataTableQuery query) {
		return rdataTableMapper.getRdataTableCount(query);
	}

	public List<RdataTable> list(RdataTableQuery query) {
		List<RdataTable> list = rdataTableMapper.getRdataTables(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getRdataTableCountByQueryCriteria(RdataTableQuery query) {
		return rdataTableMapper.getRdataTableCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<RdataTable> getRdataTablesByQueryCriteria(int start, int pageSize, RdataTableQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<RdataTable> rows = sqlSessionTemplate.selectList("getRdataTables", query, rowBounds);
		return rows;
	}

	public RdataTable getRdataTable(String id) {
		if (id == null) {
			return null;
		}
		RdataTable rdataTable = rdataTableMapper.getRdataTableById(id);
		return rdataTable;
	}

	@Transactional
	public void save(RdataTable rdataTable) {
		if (StringUtils.isEmpty(rdataTable.getId())) {
			if (StringUtils.isEmpty(rdataTable.getUserid())) {
				LoginContext loginContext = Authentication.getLoginContext();
				rdataTable.setUserid(loginContext != null ? loginContext.getActorId() : "admin");
			}
			if (StringUtils.isEmpty(rdataTable.getTablename())) {
				this.initTableName(rdataTable);
			}
			String id = this.getNextId(rdataTable.getUserid());
			rdataTable.setId(id);
			rdataTable.setCtime(new Date());
			rdataTableMapper.insertRdataTable(rdataTable);

			this.createDefaultTable(rdataTable.getTablename());

		} else {
			rdataTableMapper.updateRdataTable(rdataTable);
		}
	}
	@Transactional
	public void saveImpTable(RdataTable rdataTable) {
		if (StringUtils.isEmpty(rdataTable.getId())) {
			if (StringUtils.isEmpty(rdataTable.getUserid())) {
				LoginContext loginContext = Authentication.getLoginContext();
				rdataTable.setUserid(loginContext != null ? loginContext.getActorId() : "admin");
			}
			if (StringUtils.isEmpty(rdataTable.getTablename())) {
				this.initTableName(rdataTable);
			}
			String id = this.getNextId(rdataTable.getUserid());
			rdataTable.setId(id);
			rdataTable.setCtime(new Date());
			rdataTableMapper.insertRdataTable(rdataTable);
			//创建接口表
			this.createImpDefaultTable(rdataTable.getTablename());

		} else {
			rdataTableMapper.updateRdataTable(rdataTable);
		}
	}
	/**
	 * 全局唯一id ，避免与isdp表生成的主键冲突 、、流水号比isdp 多一位
	 * 
	 * @param userId
	 * @return
	 */
	public String getNextId(String userId) {

		String date = DateUtils.getNowYearMonthDay() + "", seq = "00000000";

		String next = new DecimalFormat(seq).format(idGenerator.nextId("r_data_table"));

		String id = String.format("%s/%s-%s", date, userId, next);

		return id;
	}

	public String initTableName(RdataTable rdataTable) {
		String tableName = null;
		Connection conn = null;
		try {
			conn = this.sqlSessionTemplate.getConnection();// 当前conn 不得关闭
			String schema = conn.getCatalog();
			if (schema == null) {
				schema = conn.getMetaData().getUserName();
			}
			int maxUserId = this.getNextMaxUser();

			tableName = RConstant.getTableName(schema, maxUserId, rdataTable.getCodeName());

			if (rdataTable != null) {
				rdataTable.setTablename(tableName);
				rdataTable.setMaxuser(maxUserId);
			}
			logger.debug(tableName);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new RuntimeException("获取最新表格失败!");
		}
		return tableName;
	}

	public String getNextTableName() {
		return this.initTableName(null);
	}

	/**
	 * 创建表格，并添加系统默认字段
	 * 
	 * @param tableName
	 */
	@Transactional
	public void createDefaultTable(String tableName) {
//		if (!DBUtils.isAllowedTable(tableName)) {
//			throw new RuntimeException("不允许访问系统表。");
//		}
//		TableDefinition td = new TableDefinition();
//		td.setTableName(tableName);
//
//		ColumnDefinition c = new ColumnDefinition();
//		c.setColumnName("id");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.setIdColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("topid");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("index_id");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("parent_id");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("treeid");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("nlevel");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("listno");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("old_id");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("tzflag");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("ctime");
//		c.setJavaType("Date");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("type_id");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("type_baseid");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("createdate");
//		c.setJavaType("Date");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("updatedate");
//		c.setJavaType("Date");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("lockrec");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("cell_locate");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("isdel");
//		c.setJavaType("Integer");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("unitid");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		c = new ColumnDefinition();
//		c.setColumnName("sys_id");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("lessessid");
//		c.setLength(100);
//		c.setJavaType("String");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("creater");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//		
//		c = new ColumnDefinition();
//		c.setColumnName("updater");
//		c.setLength(50);
//		c.setJavaType("String");
//		td.addColumn(c);
//
//		DBUtils.createTable(Environment.getCurrentSystemName(), td);

		// StringBuffer sb = new StringBuffer();
		// sb.append("CREATE TABLE ").append(tableName).append(" ( ");
		// sb.append("id varchar(100) PRIMARY KEY,");
		// sb.append("topid varchar(100),");
		// sb.append("index_id int ,parent_id int ,");
		// sb.append("treeid varchar(100) ,");
		// sb.append("nlevel int ,listno int ,");
		// sb.append("old_id varchar(50) ,tzflag int ,");
		// sb.append("repflag int ,tofileflag int ,");
		// sb.append("ctime datetime ,type_id varchar(50) ,");
		// sb.append("type_baseid varchar(50) ,lockrec int ,");
		// sb.append("cell_locate varchar(50) ,isdel int ,");
		// sb.append("unitid varchar(100) ,sys_id varchar(100)");
		// sb.append(")");
		// logger.debug("jdbc execute sql:" + sb.toString());
		//
		// jdbcTemplate.execute(sb.toString());
		TableActionUtils.createDefaultTable(tableName);
	}
	/**
	 * 创建接口表格，并添加系统默认字段
	 * 
	 * @param tableName
	 */
	@Transactional
	public void createImpDefaultTable(String tableName) {
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		TableDefinition td = new TableDefinition();
		td.setTableName(tableName);
        //接口表增加状态和导入时间默认字段
		ColumnDefinition c = new ColumnDefinition();
		c.setColumnName("status");
		c.setJavaType("Integer");
		td.addColumn(c);

		c = new ColumnDefinition();
		c.setColumnName("importtime");
		c.setJavaType("Date");
		td.addColumn(c);
		DBUtils.createTable(Environment.getCurrentSystemName(), td);
	}
	public int getNextMaxUser() {
		return rdataTableMapper.getNextMaxUser();
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.RdataTableMapper")
	public void setRdataTableMapper(RdataTableMapper rdataTableMapper) {
		this.rdataTableMapper = rdataTableMapper;
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
	public void modifyColumn(String tablename, String columnType, RdataField rdataField, Rinterface rinterface) {

		if (!DBUtils.isAllowedTable(tablename)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		this.alterColumn(tablename, "modify", rinterface.getDname(), columnType);

		rdataFieldService.save(rdataField);

		rinterfaceService.save(rinterface);

	}

	@Override
	public void addColumn(String tablename, String fieldName, String columnType, RdataField rdataField,
			Rinterface rinterface) {
		if (!DBUtils.isAllowedTable(tablename)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		// 在表中添加字段
		this.alterColumn(tablename, "add", fieldName, columnType);

		// 如果有引用，则在业务表中增加引用表的关联id
		if (StringUtils.isNotEmpty(rdataField.getUserindex())) {
			this.alterColumn(tablename, "add", rdataField.getUserindex(), "VARCHAR(100)");
		}

		// 添加Cell_Data_Field数据
		// String actorId = rdataField.getUserid();
		// String id = idGenerator.getNextId("cell_data_field", "id", actorId);
		// cellDataField.setId(id);
		// cellDataFieldMapper.insertCellDataField(cellDataField);
		rdataFieldService.save(rdataField);

		// 添加Interface数据
		// id = idGenerator.getNextId("interface", "listId", actorId);
		// fieldInterface.setListId(id);
		// fieldInterfaceMapper.insertFieldInterface(fieldInterface);
		rinterfaceService.save(rinterface);

	}

	protected void alterColumn(String tableName, String alterType, String columnName, String columnType) {
		/*
		 * if (!DBUtils.isAllowedTable(tableName)) { throw new
		 * RuntimeException("不允许访问系统表。"); } columnName =
		 * columnName.toLowerCase(); List<ColumnDefinition> columns =
		 * DBUtils.getColumnDefinitions(tableName); Map<String,
		 * ColumnDefinition> map = new HashMap<String, //
		 * ColumnDefinition>(columns.size()); if
		 * (CollectionUtils.isNotEmpty(columns)) { for (ColumnDefinition column
		 * : columns) { map.put(column.getColumnName().toLowerCase(), column); }
		 * } else { throw new RuntimeException("不允许访问系统表。"); }
		 * 
		 * StringBuffer sb = new StringBuffer();
		 * sb.append("ALTER TABLE ").append(tableName).append(" "); if
		 * ("add".equalsIgnoreCase(alterType) && //
		 * !map.containsKey(columnName)) { // 新增列
		 * sb.append(alterType).append(" ").append(columnName);
		 * sb.append(" ").append(columnType); } else if
		 * ("modify".equalsIgnoreCase(alterType) && //
		 * map.containsKey(columnName)) { // 修改列
		 * sb.append(" ALTER COLUMN ").append(columnName);
		 * sb.append(" ").append(columnType); } else if
		 * ("drop".equalsIgnoreCase(alterType) && //
		 * map.containsKey(columnName)) { // 删除列
		 * sb.append(alterType).append(" ").append(" COLUMN ");
		 * sb.append(columnName); } else { throw new
		 * RuntimeException("无可更新的列!"); } logger.debug("jdbc execute sql:" +
		 * sb.toString());
		 * 
		 * jdbcTemplate.execute(sb.toString());
		 */
		ColumnTypeUtils.alterColumn(tableName, alterType, columnName, columnType, jdbcTemplate);
	}

	public void deleteColumn(String tablename, String dname, String listId, String fieldId) {
		// TODO Auto-generated method stub

		// rdataFieldService.deleteById(id);

		if (!DBUtils.isAllowedTable(tablename)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		// cellDataFieldMapper.deleteCellDataFieldById(fieldId);
		// rdataFieldService.deleteById(fieldId);
		jdbcTemplate.update("DELETE FROM R_DATA_FIELD WHERE FIELDNAME = ?", dname);

		// fieldInterfaceMapper.deleteFieldInterfaceById(listId);
		// rinterfaceService.deleteById(Long.valueOf(listId));
		jdbcTemplate.update("DELETE FROM R_INTERFACE WHERE DNAME = ?", dname);

		this.alterColumn(tablename, "drop", dname, "");
	}

	@Override
	public void deleteTable(String tableid) {
		RdataTable rtable = this.getRdataTable(tableid);
		if (rtable != null) {
			jdbcTemplate.update("DELETE FROM R_DATA_FIELD WHERE TABLEID = ?", tableid);
			jdbcTemplate.update("DELETE FROM R_INTERFACE WHERE FRMTYPE = ?", tableid);
			this.deleteById(tableid);
			try {
				jdbcTemplate.update("DROP TABLE " + rtable.getTablename());
			} catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
	}

	@Override
	public RdataTable getRdataTableByTableName(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			return null;
		}
		RdataTable rdataTable = rdataTableMapper.getRdataTableByTableName(tableName);
		return rdataTable;
	}
}
