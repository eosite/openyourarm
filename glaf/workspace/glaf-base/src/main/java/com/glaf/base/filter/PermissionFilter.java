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

package com.glaf.base.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.glaf.core.util.RequestUtils;

public class PermissionFilter extends OncePerRequestFilter {

	protected final static List<String> includes = new java.util.concurrent.CopyOnWriteArrayList<String>();

	static {
		includes.add("/ip.jsp");
		includes.add("/pwd.jsp");
		includes.add("/sso.jsp");
		includes.add("/cert.jsp");
		includes.add("/init.jsp");
		includes.add("/ping.jsp");
		includes.add("/passwd.jsp");
		includes.add("/main.jsp");
		includes.add("/login.jsp");
		includes.add("/index.jsp");
		includes.add("/test.jsp");
		includes.add("/gnoem.jsp");
		includes.add("/update.jsp");
		includes.add("/certificate.jsp");
		includes.add("/unauthorized.jsp");
		includes.add("/inc/globaljs.jsp");
		includes.add("/webfile/jsp/main.jsp");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		if (StringUtils.endsWithIgnoreCase(uri, ".js")) {

		} else if (StringUtils.endsWithIgnoreCase(uri, ".css")) {

		} else if (StringUtils.endsWithIgnoreCase(uri, ".jpg")) {

		} else if (StringUtils.endsWithIgnoreCase(uri, ".gif")) {

		} else if (StringUtils.endsWithIgnoreCase(uri, ".png")) {

		} else if (StringUtils.containsIgnoreCase(uri, ".jsp")) {
			String ipAddr = RequestUtils.getIPAddress(request);
			if (!(StringUtils.equalsIgnoreCase(ipAddr, "localhost") || StringUtils.equalsIgnoreCase(ipAddr, "127.0.0.1")
					|| StringUtils.startsWith(ipAddr, "192.168."))) {
				String contextPath = request.getContextPath();
				String path = uri.substring(uri.indexOf(contextPath) + contextPath.length(), uri.length());
				if (!includes.contains(path.toLowerCase())) {
					logger.warn(RequestUtils.getIPAddress(request) + "试图访问->" + uri);
					response.sendRedirect(request.getContextPath() + "/unauthorized.jsp");
					return;
				}
			}
		}

		filterChain.doFilter(request, response);
	}

}
