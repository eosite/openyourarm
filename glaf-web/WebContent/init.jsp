<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.config.*"%>
<%@ page import="com.glaf.core.context.*"%>
<%@ page import="com.glaf.core.jdbc.*"%>
<%@ page import="com.glaf.core.service.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ page import="com.glaf.matrix.data.bean.*"%>
<%@ page import="org.apache.commons.lang3.*"%>
<%
	    String sqliteDB = request.getParameter("sqliteDB");
		String systemName = request.getParameter("systemName");
		if(StringUtils.isEmpty(systemName)){
             systemName = "default";
		}
		if(StringUtils.isEmpty(sqliteDB)){
             sqliteDB = "initdb";
		}
		
		String dbpath = SystemProperties.getConfigRootPath() + "/db/" + sqliteDB;
		File dbfile = new File(dbpath);
		if (dbfile.exists()) {
            /*
			String sql = "";
			Connection conn = null;
			PreparedStatement psmt = null;
			try {
				conn = DBConnectionFactory.getConnection(systemName);
				conn.setAutoCommit(false);
				List<String> tables = DBUtils.getTables();
				for(String tableName: tables){
					if(StringUtils.equals("userinfo", tableName.toLowerCase())){
						continue;
					}
					if(StringUtils.equals("sys_access", tableName.toLowerCase())){
						continue;
					}
					if(StringUtils.startsWith(tableName.toLowerCase(), "act_")){
						continue;
					}
					if(StringUtils.startsWith(tableName.toLowerCase(), "form_")){
						//continue;
					}
					//System.out.println(sql);
					try{
					  sql = " TRUNCATE TABLE "+tableName;
					  System.out.println(sql);
					  psmt = conn.prepareStatement(sql);
					  psmt.executeUpdate();
					  psmt.close();
					  conn.commit();
					  psmt = null;
					} catch (Exception ex) {
						
					}
				}
				conn.commit();
				out.println("<br><div align=center>Commond OK</div><br>");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally{
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		    */
			try {	   
					SQLiteToGenaralDB db = new SQLiteToGenaralDB();
					boolean result = db.importData(systemName, sqliteDB);
					if(result){
						out.println("OK");
					} else {
						out.println("FAIL");
					}
				} catch (Exception ex) {
					out.println(ex.getMessage());
				}
		}
%>