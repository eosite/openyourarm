package com.glaf.form.cell.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.service.ConvertPageTmplService;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.PropertiesUtils;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.form.cell.util.FormulaToEventUtil;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;

@Service("cellConvertService")
public class CellConvertServiceImpl implements CellConvertService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected Properties sqlProperties = null;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected DataSetAuditService dataSetAuditService;

	@Autowired
	protected DataSetService dataSetService;

	@Autowired
	protected FormRuleService formRuleService;

	@Autowired
	protected FormRulePropertyService formRulePropertyService;

	@Autowired
	protected ConvertPageTmplService convertPageTmplService;

	@Autowired
	protected FormulaToEventUtil formulaToEventUtil;

	@Override
	public boolean convert() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean convert(String cvtId) {
		// TODO Auto-generated method stub
		return false;
	}

	protected String getProperty(String key) {
		return sqlProperties.getProperty(key);
	}

	protected Properties getCellPropertiesByName(String fileName) {
		return PropertiesUtils.loadFilePathResource(
				String.format(SystemProperties.getAppPath() + "/WEB-INF/conf/templates/cell/%s", fileName));
	}

	public String getString(ResultSet rSet, String columnName) throws SQLException {
		return (columnName = rSet.getString(columnName)) != null ? columnName.trim() : columnName;
	}

	public String getResultSetStream2String(ResultSet rs, String key) throws Exception {
		InputStream is = rs.getBinaryStream(key);

		if (is == null) {
			return null;
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		StringWriter out = new StringWriter();
		int c;
		while ((c = in.read()) != -1) {
			out.write(c);
		}
		is.close();
		in.close();
		out.close();
		return out.toString();
	}

	public static void fillPreparedStatement(PreparedStatement pstmt, Object... objects) {
		if (pstmt != null && objects != null) {
			try {
				for (int i = 0; i < objects.length; i++) {
					Object o = objects[i];
					if (o != null) {
						if (o instanceof java.util.Date) {
							o = new java.sql.Date(((java.util.Date) o).getTime());
						}
						pstmt.setObject(i + 1, o);
					} else {
						pstmt.setNull(i + 1, Types.NULL);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected Map<String, Map<String, Map<String, String>>> getComponentMap(Connection connection) {
		ResultSet rSet = null;
		Statement stmt = null;
		Map<String, Map<String, Map<String, String>>> map = new HashMap<String, Map<String, Map<String, String>>>();
		try {
			stmt = connection.createStatement();
			String componentSql = getCellPropertiesByName("page.sql.properties").getProperty("page.component.sql");// 获取所有属性
			rSet = stmt.executeQuery(componentSql);
			String dataRole, NAME;
			Map<String, Map<String, String>> coMap;
			Map<String, String> cMap;
			ResultSetMetaData resultSetMetaData = rSet.getMetaData();
			while (rSet.next()) {
				dataRole = rSet.getString("DATAROLE_").toLowerCase();
				coMap = map.get(dataRole);
				if (coMap == null) {
					coMap = new HashMap<String, Map<String, String>>();
					map.put(dataRole, coMap);
				}
				NAME = rSet.getString("NAME_").toLowerCase();
				cMap = coMap.get(NAME);
				if (cMap == null) {
					cMap = new HashMap<String, String>();
					coMap.put(NAME, cMap);
				}
				for (int i = 1; i < resultSetMetaData.getColumnCount(); i++) {
					cMap.put(resultSetMetaData.getColumnName(i).toLowerCase(), rSet.getString(i));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("run getComponentMap error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rSet);
			JdbcUtils.close(stmt);
		}
		return map;
	}

	protected String setValue(Map<String, Map<String, String>> Cap, String key, JSONObject ruleJson, Object value) {
		Map<String, String> map = Cap.get(key);
		if (map != null) {
			ruleJson.put(map.get("id_"), value);
			return map.get("componentid_");
		}
		return null;
	}

}
