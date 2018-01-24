package com.glaf.base.modules.website.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.domain.Database;
import com.glaf.core.query.DatabaseQuery;
import com.glaf.core.service.IDatabaseService;

@Controller("/gneslVerify")
@RequestMapping("/gneslVerify")
public class GnESLVerifyController {

	protected IDatabaseService databaseService;

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	/**
	 * 提交
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public void verify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String sessionkey = request.getParameter("sessionkey");
		String token = request.getParameter("tk");
		boolean verifyOK = false;
		if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(sessionkey) && StringUtils.isNotEmpty(token)) {
			DatabaseQuery query = new DatabaseQuery();
			query.userNameKey(username);
			query.ticket(sessionkey);
			List<Database> databases = databaseService.list(query);
			if (databases != null && !databases.isEmpty()) {
				for (Database database : databases) {
					if (StringUtils.equals(token, database.getToken())) {
						verifyOK = true;
						break;
					}
				}
			}
		}

		JSONObject json = new JSONObject();
		if (verifyOK) {
			json.put("Code", "00");
			json.put("Message", "验证通过");
		} else {
			json.put("Code", "01");
			json.put("Message", "验证失败");
		}

		PrintWriter writer = response.getWriter();
		writer.write(json.toJSONString());
		writer.flush();
	}
}
