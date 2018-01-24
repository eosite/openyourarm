package com.glaf.platforms.rule.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectUtil {
	private Connection connection;
	private Statement statement;
	
	private String path = "E:\\" ;
	private String expName = "kk01" ;
	private String databaseName = "glafDBTest";

	public void connect() {
		String path;
		String driverName;
		String URLName;
		String user;
		String password;
		try {
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			URLName = "jdbc:sqlserver://192.168.1.27:1433";
			user = "sa";
			password = "1";
			System.out.println("url=" + URLName);
			System.out.println("driver:" + driverName);
			Class.forName(driverName);
			if (connection == null)
				connection = java.sql.DriverManager.getConnection(URLName, user, password);
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		} catch (Exception er) {
			System.out.println("连接参数文件没找到到,请查看文件");
			er.printStackTrace();
		}
	}

	public void doRestore() {

		try {
		
			
			statement.executeUpdate("USE   master");
			String query;
			String str = "D:\\Program Files\\Microsoft SQL Server\\MSSQL10_50.MSSQLSERVER\\MSSQL\\DATA\\";
			//query = null ;
			query =	"RESTORE   DATABASE   " + databaseName+"2" + " FROM   DISK   =   \'" + path +  expName +".bak" 
					+ "\'  WITH   RECOVERY,   MOVE  \'pMagic\' TO  \'" + str + "glafDBTest2.mdf\' ,  MOVE  \'pMagic_log\'  TO  \'"
					+ str + "glafDBTest_2.ldf\'";

			System.out.println(query);
			
			
			
			
			statement.executeUpdate(query);
			//statement.executeUpdate("ALTER   DATABASE   " + databaseTextField.getText().trim() + " SET   MULTI_USER");
			System.out.println("数据库恢复成功");
		} catch (SQLException e) {
			System.out.println("数据库恢复失败");
			e.printStackTrace();
		}

	}

	public void doBackup() {
		// statement.executeUpdate("ALTER   DATABASE   state   SET   SINGLE_USER   WITH   ROLLBACK   IMMEDIATE");
		try {
			statement.executeUpdate("USE   master");
			
		//	statement.executeUpdate("net share sqlbakup=E:\\sqlbak");  //开启本机共享目录
			


			/*String q = " sp_configure 'show advanced options',1 reconfigure   " ;

			statement.executeUpdate(q);  //建立共享信用关系
			String q1 = "  sp_configure 'xp_cmdshell',1 reconfigure   " ;
			
			statement.executeUpdate(q1);  //建立共享信用关系
*/			
			Runtime runtime=Runtime.getRuntime();


			try {
				runtime.exec("cmd /c start C:\\Program Files\\install4j6\\bin\\install4j.exe");
			} catch (IOException e) {
				e.printStackTrace();
			}

			
			System.out.println("start....");
			String k = " exec master..xp_cmdshell 'net use  \\\\192.168.1.123\\sqlbakup 0000 /user:J' ";
			statement.execute(k);
			//statement.executeUpdate(k);  //建立共享信用关系
			System.out.println("end....");
			
			String query;
			path = "\\\\192.168.1.123\\sqlbakup\\" ;
			query = "BACKUP   DATABASE   " + databaseName + " TO   DISK   =   \'" + path + expName + ".bak\' ";

			System.out.println(query);
			statement.executeUpdate(query);

			System.out.println("数据库备份成功");
		} catch (SQLException e) {
			System.out.println("数据库备份失败");
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		ConnectUtil con = new ConnectUtil();
		//con.connect();
		//con.doRestore();
		//con.doBackup();
		//Properties pros = System.getProperties();
		//Set<Object> keys = pros.keySet();
		//for (Object key : keys) {
		//	System.out.println(key+"-->"+pros.getProperty((String) key));
		//}
		
		/*InetAddress  netAddress  = InetAddress.getLocalHost();
		System.out.println(netAddress.getHostAddress());
		System.out.println(netAddress.getHostName());
		
		Runtime runtime=Runtime.getRuntime();


		try {
			// 空格   转换为  "空格"
			 Process pro  = runtime.exec("cmd /c start C:\\Program\" \"Files\\install4j6\\bin\\install4j.exe");
			 Thread.sleep(5000);
			 System.out.println(pro.exitValue());
			 pro.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		String[] str = new String[10];
		
		str[0] = new String("1");
		//str[11] = "2";
		
		System.out.println(str[0]);
	}

}
