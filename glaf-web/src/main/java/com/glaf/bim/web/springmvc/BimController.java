package com.glaf.bim.web.springmvc;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.glaf.bim.BimUpload;
import com.glaf.bim.domain.BimModel;
import com.glaf.bim.service.BimModelService;
import com.glaf.core.util.RequestUtils;

@Controller("/bim")
@RequestMapping("/bim")
public class BimController {

	protected BimModelService bimModelService;

	protected static final Log logger = LogFactory.getLog(BimController.class);

	@RequestMapping("getToken")
	@ResponseBody
	public byte[] getToken(HttpServletRequest request, HttpServletResponse response) {

		BimUpload bimUpload = new BimUpload();

		try {
			return bimUpload.getToken().getBytes("UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "".getBytes();
		}
	}

	@RequestMapping("saveMsg")
	@ResponseBody
	public byte[] saveMsg(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String bimId = MapUtils.getString(params, "bimId");
		String tree = MapUtils.getString(params, "tree");
		if (StringUtils.isNotBlank(tree)) {
			List<BimModel> array = JSON.parseArray(tree, BimModel.class);
			int size = array.size();
			if (size > 0)
				this.bimModelService.saveMsg(bimId, array);
		}
		return com.glaf.core.util.ResponseUtils.responseJsonResult(true);

	}

	@Resource
	public void setBimModelService(BimModelService bimModelService) {
		this.bimModelService = bimModelService;
	}
}
