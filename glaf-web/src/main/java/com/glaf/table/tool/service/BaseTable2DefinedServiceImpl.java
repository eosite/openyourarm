package com.glaf.table.tool.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.table.tool.model.FieldModel;
import com.glaf.table.tool.model.TableModel;

@Service("baseTable2DefinedService")
public class BaseTable2DefinedServiceImpl implements IBaseTable2DefinedService {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	

	@Override
	public TableModel getTable(String tableName) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("keywords",tableName);
		params.put("searchType", "tableName");
		List<TableModel> list = this.getTables(params);
		
		return list.get(0);
	}
	
	@Override
	public List<TableModel> getTables(Map<String,String> params) {
		//获取当前环境的数据库类型
		String dbType = DBConnectionFactory.getDatabaseType();
		if (StringUtils.equals(dbType, DBUtils.ORACLE)) {

			StringBuffer sb = new StringBuffer();
			//这个是表查询
			sb.append("SELECT a.TABLE_NAME AS tableName, b.name AS name FROM user_tables a ");
			sb.append("LEFT JOIN cell_data_table b ON a.TABLE_NAME=b.tablename ");
			sb.append("WHERE a.TABLE_NAME NOT LIKE 'cell_useradd%' AND a.TABLE_NAME NOT LIKE 'log_%' ");
			if("tableName".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and a.TABLE_NAME like '%"+params.get("keywords")+"%'");
				}
			}else if("name".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and b.name like '%"+params.get("keywords")+"%'");
				}
			}
			List<TableModel> results = jdbcTemplate.query(sb.toString(), new RowMapper<TableModel>(){
				
				@Override
				public TableModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					TableModel table = new TableModel();
					table.setTableName(rs.getString("tableName"));
					table.setName(rs.getString("name")==null?rs.getString("tableName"):rs.getString("name"));
					return table;
				}
				
			});
			//这个是视图查询
			sb = new StringBuffer();
			sb.append("SELECT a.view_name AS tableName, b.name AS name FROM user_views a ");
			sb.append("LEFT JOIN cell_data_table b ON a.view_name=b.tablename ");
			sb.append("WHERE a.view_name NOT LIKE 'cell_useradd%' AND a.view_name NOT LIKE 'log_%' ");
			if("tableName".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and a.view_name like '%"+params.get("keywords")+"%'");
				}
			}else if("name".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and b.name like '%"+params.get("keywords")+"%'");
				}
			}
			List<TableModel> results2 = jdbcTemplate.query(sb.toString(), new RowMapper<TableModel>(){
				
				@Override
				public TableModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					TableModel table = new TableModel();
					table.setTableName(rs.getString("tableName"));
					table.setName(rs.getString("name")==null?rs.getString("tableName"):rs.getString("name"));
					return table;
				}
				
			});
			results.addAll(results2);
			
			return results;
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("select a.name as tableName,b.name as name from sysobjects a ");
			sb.append("left join cell_data_table b on a.name=b.tablename ");
			sb.append("where a.xtype in ('u','v' ) and a.name not like 'cell_useradd%' and a.name not like 'log_%' ");
			if("tableName".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and a.name like '%"+params.get("keywords")+"%'");
				}
			}else if("name".equalsIgnoreCase(params.get("searchType"))){
				if(StringUtils.isNotEmpty(params.get("keywords"))){
					sb.append("and b.name like '%"+params.get("keywords")+"%'");
				}
			}
			
			List<TableModel> results = jdbcTemplate.query(sb.toString(), new RowMapper<TableModel>(){
				
				@Override
				public TableModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					TableModel table = new TableModel();
					table.setTableName(rs.getString("tableName"));
					table.setName(rs.getString("name")==null?rs.getString("tableName"):rs.getString("name"));
					return table;
				}
				
			});
			
			return results;
		}
		
	}

	@Override
	public List<FieldModel> getFieldsByTableName(String tableName) {

		StringBuffer sb = new StringBuffer();
		sb.append("select c.name as fieldName,t.name as fieldType,c.length as fieldLength,f.fname  as name,isnull(f.listno,999) as listno ");
		sb.append("from syscolumns c inner join sysobjects o on c.id = o.id and o.xtype in ('u','v') ");
		sb.append("inner join systypes t on c.xtype = t.xtype left join interface f on f.dname=c.name and f.frmtype=o.name ");
		sb.append("where o.name=? and t.name !='sysname' ");
		sb.append("order by isnull(f.listno,999) asc,c.colorder asc");

		List<FieldModel> results = jdbcTemplate.query(sb.toString(),
			new Object[] { tableName }, new int[] { Types.VARCHAR },
			new RowMapper<FieldModel>() {

				@Override
				public FieldModel mapRow(ResultSet rs, int rowNum)
						throws SQLException {

					FieldModel field = new FieldModel();
					field.setFieldName(rs.getString("fieldName"));
					field.setFieldType(rs.getString("fieldType"));
					field.setFieldLength(rs.getString("fieldLength"));
					field.setName(rs.getString("name"));
					field.setListNo(rs.getInt("listno"));

					return field;
				}
			});

		return results;
	}

	@Override
	public Map<String, FieldModel> getFieldsMapByTableName(String tableName) {
		List<FieldModel> list = this.getFieldsByTableName(tableName);

		Map<String, FieldModel> resultMap = new HashMap<String, FieldModel>();
		for (FieldModel model : list) {
			resultMap.put(model.getFieldName(), model);
		}

		return resultMap;
	}

	@Transactional
	@Override
	public void save(String tableName, String tableDescription,
			List<FieldModel> saveList) {
		this.clearMapping(tableName);

		String tablesql = "insert into cell_data_table (id,tablename,index_id,name,addtype,userid,ctime,content,issubtable) values('"
				+ tableName
				+ "','"
				+ tableName
				+ "',1,'"
				+ tableDescription
				+ "',3,'system',GETDATE(),'" + tableDescription + "',0)";
		jdbcTemplate.execute(tablesql);

		String interfaceSql = "insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) values (?,?,1,?,?,?,?,'edt',?,1)";
		List<Object[]> interfaceParams = new ArrayList<Object[]>();
		
		String fieldSql = "insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)values(?,?,?,'admin',?,GETDATE())";
		List<Object[]> fieldParams = new ArrayList<Object[]>();
		for (int i = 0; i < saveList.size(); i++) {
			FieldModel model = saveList.get(i);
			int index = i + 1;
			Object[] obj = new Object[7];
			obj[0] = tableName;
			obj[1] = tableName + "_" + index;
			obj[2] = model.getName();
			obj[3] = model.getFieldName();
			obj[4] = convertFieldType(model.getFieldType());
			obj[5] = model.getFieldLength();
			obj[6] = index;
			interfaceParams.add(obj);
			
			Object[] obj2 = new Object[4];
			obj2[0] = tableName + "_" + index;
			obj2[1] = tableName;
			obj2[2] = model.getFieldName();
			obj2[3] = index;
			fieldParams.add(obj2);
		}
		 jdbcTemplate.batchUpdate(interfaceSql, interfaceParams, new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER});
		 jdbcTemplate.batchUpdate(fieldSql, fieldParams, new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER});
	}

	/**
	 * 转换类型
	 * @param fieldType
	 * @return
	 */
	private String convertFieldType(String fieldType) {
		String type = "string";
		switch (fieldType) {
		case "bigint":
		case "int":
		case "tinyint":
			type = "i4";
			break;
		case "char":
			type = "char";
			break;
		case "datetime":
		case "datetime2":
			type = "datetime";
			break;
		case "varchar":
		case "nvarchar":
			type = "string";
			break;
		case "image":
		case "varbinary":
			type = "image";
			break;
		case "text":
			type = "text";
			break;
		case "numeric":
		case "float":
			type = "r8";
			break;
		default:
			type = "string";
		}
		return type;
	}

	@Transactional
	@Override
	public void clearMapping(String tableName) {
		jdbcTemplate.update("delete from cell_data_table where id='"+tableName+"'");
		jdbcTemplate.update("delete from interface where frmtype='"+tableName+"'");
		jdbcTemplate.update("delete from cell_data_field where tableid='"+tableName+"'");
	}

}
