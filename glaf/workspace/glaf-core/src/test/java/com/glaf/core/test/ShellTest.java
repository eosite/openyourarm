package com.glaf.core.test;

import com.glaf.core.util.ShellUtils;
import com.jcraft.jsch.Session;

public class ShellTest {

	public static void main(String[] args) throws Exception {
		String host = "zygs.fzmt.com.cn";
		int port = 8089;
		String user = "root";
		String pwd = "xxxxxx";
		long start = System.currentTimeMillis();
		System.out.println(ShellUtils.exec(host, port, user, pwd, " yum list installed | grep docker "));
		System.out.println(ShellUtils.exec(host, port, user, pwd, " docker version "));
		System.out.println(ShellUtils.exec(host, port, user, pwd, " docker ps "));

		long ts = System.currentTimeMillis() - start;
		System.out.println("ts1:" + ts);
		start = System.currentTimeMillis();
		
		Session session = ShellUtils.openSession(host, port, user, pwd);
		System.out.println(ShellUtils.exec(session, " yum list installed | grep docker "));
		System.out.println(ShellUtils.exec(session, " docker version "));
		System.out.println(ShellUtils.exec(session, " docker ps "));
		session.disconnect();
		ts = System.currentTimeMillis() - start;
		System.out.println("ts2:" + ts);
	}

}
