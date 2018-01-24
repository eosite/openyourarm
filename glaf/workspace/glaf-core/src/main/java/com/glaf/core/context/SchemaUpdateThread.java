package com.glaf.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.domain.TableDefinition;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.xml.XmlReader;

public class SchemaUpdateThread extends Thread{
	protected static final Log logger = LogFactory.getLog(SchemaUpdateThread.class);
	
	protected File file;
	
	
	public SchemaUpdateThread(File file){
		this.file = file;
	}
	
	public void run(){
		Connection conn = null;
		InputStream inputStream = null;
		TableDefinition tableDefinition = null;
		XmlReader reader = new XmlReader();
		try {
			logger.info("load schema mapping:" + file.getAbsolutePath());
			inputStream = new FileInputStream(file);
			tableDefinition = reader.read(inputStream);
			IOUtils.closeQuietly(inputStream);
			conn = DBConnectionFactory.getConnection();
			conn.setAutoCommit(false);
			if (DBUtils.tableExists(conn, tableDefinition.getTableName())) {
				DBUtils.alterTable(conn, tableDefinition);
			} else {
				DBUtils.createTable(conn, tableDefinition);
			}
			conn.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			JdbcUtils.close(conn);
			IOUtils.closeQuietly(inputStream);
		}
		
	}

}
