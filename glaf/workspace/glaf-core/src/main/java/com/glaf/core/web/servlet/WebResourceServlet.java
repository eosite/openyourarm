/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.core.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.resource.ResourceFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.IOUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.xml.MimeMappingReader;

public class WebResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected static final Log logger = LogFactory.getLog(WebResourceServlet.class);

	protected static Configuration conf = BaseConfiguration.create();

	protected static final Semaphore semaphore = new Semaphore(20);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ipAddr = RequestUtils.getIPAddress(request);
		// if (!StringUtils.startsWith(ipAddr, "192.168.")) {
		// int num = ContextUtils.increase(ipAddr);
		// if (num > 5000000) {// 从某个IP地址来的访问量超过5000000就不再响应
		// response.sendRedirect("http://www.google.com");
		// return;
		// }
		// }

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		String resPath = requestURI.substring(contextPath.length(), requestURI.length());

		// logger.debug("contextPath:" + contextPath);
		// logger.debug("requestURI:" + requestURI);
		// logger.debug("resPath:" + resPath);

		int slash = request.getRequestURI().lastIndexOf("/");
		String file = request.getRequestURI().substring(slash + 1);
		if (StringUtils.endsWithIgnoreCase(file, ".class")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".jsp")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".conf")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".exe")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".bat")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".cmd")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".dll")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".lib")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, ".sh")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "-config.properties")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "-spring-context.xml")) {
			return;
		}
		if (StringUtils.endsWithIgnoreCase(file, "Mapper.xml")) {
			return;
		}

		boolean debugMode = false;
		if (StringUtils.equalsIgnoreCase(ipAddr, "localhost") || StringUtils.equalsIgnoreCase(ipAddr, "127.0.0.1")
				|| SystemConfig.getBoolean("debugModeEnable")) {
			debugMode = true;
		}

		// logger.debug("debugMode:" + debugMode);

		int dot = file.lastIndexOf(".");
		String ext = file.substring(dot + 1);
		String contentType = "";
		boolean requiredZip = false;
		if (StringUtils.equalsIgnoreCase(ext, "jpg") || StringUtils.equalsIgnoreCase(ext, "jpeg")
				|| StringUtils.equalsIgnoreCase(ext, "gif") || StringUtils.equalsIgnoreCase(ext, "png")) {
			contentType = "image/" + ext;
			requiredZip = false;
		} else if (StringUtils.equalsIgnoreCase(ext, "bmp")) {
			contentType = "image/bmp";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "svg")) {
			contentType = "image/svg+xml";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "css")) {
			contentType = "text/css";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "txt")) {
			contentType = "text/plain";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "htm") || StringUtils.equalsIgnoreCase(ext, "html")) {
			contentType = "text/html";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "js")) {
			contentType = "application/javascript";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "ttf")) {
			contentType = "application/x-font-ttf";
			requiredZip = true;
		} else if (StringUtils.equalsIgnoreCase(ext, "eot")) {
			contentType = "application/vnd.ms-fontobject";
		} else if (StringUtils.equalsIgnoreCase(ext, "woff")) {
			contentType = "application/x-font-woff";
		} else if (StringUtils.equalsIgnoreCase(ext, "swf")) {
			contentType = "application/x-shockwave-flash";
		} else {
			contentType = ResourceFactory.getMimeMapping().get(ext.trim().toLowerCase());
		}

		if (requiredZip && isGZIPSupported(request) && conf.getBoolean("gzipEnabled", true)) {
			requiredZip = true;
		} else {
			requiredZip = false;
		}

		// logger.debug("contentType:" + contentType);
		// logger.debug("requiredZip:" + requiredZip);

		InputStream inputStream = null;
		ServletOutputStream output = null;
		boolean zipFlag = false;
		byte[] raw = null;

		try {
			if (!StringUtils.startsWith(ipAddr, "192.168.")) {
				if (SystemConfig.getConcurrentAccessLimit()) {
					// logger.debug("启用并发访问控制,可用许可数:" +
					// semaphore.availablePermits());
					if (semaphore.availablePermits() == 0) {
						try {
							TimeUnit.MILLISECONDS.sleep(50 + new Random().nextInt(1000));
						} catch (InterruptedException e) {
						}
					}
					semaphore.acquire();
				}
			}
			output = response.getOutputStream();

			String etag = request.getHeader("If-None-Match");

			if (etag != null && ResourceFactory.getEtagMap().containsKey(etag) && !debugMode) {
				Date date = new Date();
				date.setTime(System.currentTimeMillis() - DateUtils.DAY);
				String modDate = this.getGMT(date);
				date.setTime(System.currentTimeMillis() + DateUtils.DAY * 30);
				String expDate = this.getGMT(date);
				response.setHeader("Age", String.valueOf(DateUtils.DAY));
				response.setHeader("Cache-Control", "max-age=315360000");
				response.setHeader("Connection", "keep-alive");
				response.setHeader("ETag", etag);
				response.setHeader("Last-Modified", modDate);
				response.setHeader("Expires", expDate);
				response.setDateHeader("Date", System.currentTimeMillis());
				response.setHeader("Pragma", "Pragma"); // HTTP/1.0
				if (ResourceFactory.getEtag3Map().get(etag) && requiredZip) {
					response.setHeader("Content-Encoding", "gzip");
				}
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setContentType(ResourceFactory.getEtag2Map().get(etag));
				response.setContentLength(ResourceFactory.getEtagMap().get(etag));
				output.flush();
				IOUtils.closeStream(output);
				response.flushBuffer();
				// logger.debug(resPath + "内容已经在浏览器中缓存。");
				return;
			}

			if (!debugMode) {
				if (requiredZip) {
					raw = ResourceFactory.getData("web_res", resPath + ".gz");
					if (raw != null) {
						zipFlag = true;
					}
				} else {
					raw = ResourceFactory.getData("web_res", resPath);
					zipFlag = false;
				}
			}

			if (raw == null || debugMode) {
				zipFlag = false;
				String filename = SystemProperties.getAppPath() + resPath;
				File filex = new File(filename);
				if (filex.exists() && filex.isFile()) {
					inputStream = FileUtils.getInputStream(filename);
				}
				if (inputStream == null) {
					inputStream = WebResourceServlet.class.getResourceAsStream(resPath);
				}

				// logger.debug("load resource:" + resPath);
				if (inputStream != null) {

					raw = FileUtils.getBytes(inputStream);

					if (!debugMode) {
						ResourceFactory.put("web_res", resPath, raw);
					}

					GZIPOutputStream gzipStream = null;
					ByteArrayOutputStream compressedContent = null;
					try {
						// prepare a gzip stream
						compressedContent = new ByteArrayOutputStream();
						gzipStream = new GZIPOutputStream(compressedContent);
						gzipStream.write(raw);
						gzipStream.finish();
						// get the compressed content
						byte[] compressedBytes = compressedContent.toByteArray();
						ResourceFactory.put("web_res", resPath + ".gz", compressedBytes);
						// logger.debug(resPath + " raw size:[" + raw.length
						// + "] gzip compressed size:["
						// + compressedBytes.length + "]");
						if (requiredZip) {
							raw = compressedBytes;
							zipFlag = true;
						}
					} catch (Exception ex) {
						zipFlag = false;
					} finally {
						IOUtils.closeStream(compressedContent);
						IOUtils.closeStream(gzipStream);
					}
				}
			}

			if (zipFlag) {
				response.addHeader("Content-Encoding", "gzip");
			}

			if (raw != null) {
				etag = DigestUtils.sha1Hex(raw);
				ResourceFactory.getEtagMap().put(etag, raw.length);
				ResourceFactory.getEtag2Map().put(etag, contentType != null ? contentType : "");
				ResourceFactory.getEtag3Map().put(etag, zipFlag);

				if (!(StringUtils.containsIgnoreCase(requestURI, "/scripts/form/") || debugMode)) {
					Date date = new Date();
					date.setTime(System.currentTimeMillis() - DateUtils.DAY);
					String modDate = this.getGMT(date);
					date.setTime(System.currentTimeMillis() + DateUtils.DAY * 30);
					String expDate = this.getGMT(date);
					response.setHeader("Age", String.valueOf(DateUtils.DAY));
					response.setHeader("Cache-Control", "max-age=315360000");
					response.setHeader("Connection", "keep-alive");
					response.setHeader("ETag", etag);
					response.setHeader("Last-Modified", modDate);
					response.setHeader("Expires", expDate);
					response.setDateHeader("Date", System.currentTimeMillis());
					response.setHeader("Pragma", "Pragma"); // HTTP/1.0
				}

				if (StringUtils.equals(etag, request.getHeader("If-None-Match")) && !debugMode) {
					response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
					response.setContentType(contentType);
					response.setContentLength(ResourceFactory.getEtagMap().get(etag));
					// logger.debug("^^内容已经在浏览器中缓存。");
				} else {
					response.setStatus(HttpServletResponse.SC_OK);
					response.setContentType(contentType);
					response.setContentLength(raw.length);
					output.write(raw);
					// logger.debug(resPath + "内容已经输出。");
				}
				output.flush();
				IOUtils.closeStream(output);
			} else {
				// logger.debug(resPath + "内容为空。");
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			raw = null;
			IOUtils.closeStream(inputStream);
			IOUtils.closeStream(output);
			if (!StringUtils.startsWith(ipAddr, "192.168.")) {
				if (SystemConfig.getConcurrentAccessLimit()) {
					semaphore.release();
				}
			}
		}

		response.flushBuffer();
	}

	public String getGMT(Date dateCST) {
		DateFormat df = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT")); // modify Time Zone.
		return (df.format(dateCST));
	}

	@Override
	public void init(ServletConfig config) {
		logger.info("--------------WebResourceServlet init----------------");
		try {
			if (System.getProperty("debugMode") != null) {
				logger.info("---------------WebResource开启调试模式---------------");
			}
			MimeMappingReader reader = new MimeMappingReader();
			Map<String, String> mapping = reader.read();
			Set<Entry<String, String>> entrySet = mapping.entrySet();
			for (Entry<String, String> entry : entrySet) {
				String key = entry.getKey();
				String value = entry.getValue();
				ResourceFactory.getMimeMapping().put(key, value);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected boolean isGZIPSupported(HttpServletRequest req) {
		String browserEncodings = req.getHeader("accept-encoding");
		boolean supported = ((browserEncodings != null) && (browserEncodings.indexOf("gzip") != -1));
		String userAgent = req.getHeader("user-agent");
		if ((userAgent != null) && userAgent.startsWith("httpunit")) {
			logger.debug("httpunit detected, disabling filter...");
			return false;
		} else {
			return supported;
		}
	}

}
