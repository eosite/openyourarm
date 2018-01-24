package com.glaf.base.modules.sys.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ServerEntity;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IServerEntityService;

/**
 * 将系统用户同步到云众联
 *
 */
public class GnUserImporter {
	private static final Log logger = LogFactory.getLog(GnUserImporter.class);

	protected String addUser(ServerEntity model, SysUser user) {
		String key = model.getKey();
		String pass = SecurityUtils.decode(key, model.getPassword());
		String str = pass + this.getAuthSeed(model);
		str = org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(model.getHost()).append(":").append(model.getPort())
				.append("/GNRemote.dll?GNFunction=AddUser&UserName").append(user.getAccount()).append("&Password")
				.append(user.getPasswordHash()).append("&AuthCode=").append(str);
		return doGet(buffer.toString());
	}

	protected String changeUser(ServerEntity model, SysUser user) {
		String key = model.getKey();
		String pass = SecurityUtils.decode(key, model.getPassword());
		String str = pass + this.getAuthSeed(model);
		str = org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(model.getHost()).append(":").append(model.getPort())
				.append("/GNRemote.dll?GNFunction=ChangeUser&UserName").append(user.getAccount()).append("&Password")
				.append(user.getPasswordHash()).append("&AuthCode=").append(str);
		return doGet(buffer.toString());
	}

	public void doImport() {
		logger.debug("--------------------------doImport------------------------");
		SysUserService sysUserService = ContextFactory.getBean("sysUserService");
		IServerEntityService serverEntityService = ContextFactory.getBean("serverEntityService");
		ServerEntity serverEntity = serverEntityService.getServerEntityByMapping("GNRemote");
		if (serverEntity != null && StringUtils.isNotEmpty(this.getAuthSeed(serverEntity))) {
			List<SysUser> users = sysUserService.getSysUserList();
			if (users != null && !users.isEmpty()) {
				for (SysUser user : users) {
					logger.debug("process user:" + user.getName());
					try {
						if ("0".equals(user.getStatus())) {
							String code = this.getSysUser(serverEntity, user);
							if (!"00".equals(code)) {
								String code2 = this.addUser(serverEntity, user);
								if (!"00".equals(code2)) {
									this.changeUser(serverEntity, user);
								}
							}
						} else {
							this.removeUser(serverEntity, user);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
	}

	protected String getAuthSeed(ServerEntity model) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(model.getHost()).append(":").append(model.getPort())
				.append("/GNRemote.dll?GNFunction=GetAuthSeed");
		logger.debug(buffer.toString());
		String seed = doGet(buffer.toString());
		logger.debug("seed:" + seed);
		return seed;
	}

	protected String getSysUser(ServerEntity model, SysUser user) {
		String key = model.getKey();
		String pass = SecurityUtils.decode(key, model.getPassword());
		String str = pass + this.getAuthSeed(model);
		str = org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(model.getHost()).append(":").append(model.getPort())
				.append("/GNRemote.dll?GNFunction=GetSysUser&UserName").append(user.getAccount()).append("&AuthCode=")
				.append(str);
		return doGet(buffer.toString());
	}

	protected String removeUser(ServerEntity model, SysUser user) {
		String key = model.getKey();
		String pass = SecurityUtils.decode(key, model.getPassword());
		String str = pass + this.getAuthSeed(model);
		str = org.apache.commons.codec.digest.DigestUtils.md5Hex(str);
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://").append(model.getHost()).append(":").append(model.getPort())
				.append("/GNRemote.dll?GNFunction=DeleteUser&UserName").append(user.getAccount()).append("&AuthCode=")
				.append(str);
		return doGet(buffer.toString());
	}

	public static String doGet(String sUrl) {
		String result = "";
		try {
			BasicCookieStore cookieStore = new BasicCookieStore();
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			try {
				HttpGet httpGet = new HttpGet(sUrl);
				CloseableHttpResponse response1 = httpclient.execute(httpGet);
				try {
					HttpEntity entity = response1.getEntity();
					if (entity != null) {
						InputStream instream = entity.getContent();
						try {
							BufferedReader in = new BufferedReader(new InputStreamReader(instream));
							StringBuffer buffer = new StringBuffer();
							String line = "";
							while ((line = in.readLine()) != null) {
								buffer.append(line);
							}
							result = buffer.toString();
						} catch (IOException ex) {
							throw ex;
						} finally {
							instream.close();
						}
					}
				} finally {
					response1.close();
				}
			} finally {
				httpclient.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
