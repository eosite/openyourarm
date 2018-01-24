package com.glaf.chinaiss.web.rest;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.glaf.base.utils.ParamUtil;
import com.glaf.chinaiss.model.GisInfo;
import com.glaf.chinaiss.service.GisInfoService;
import com.glaf.core.config.Environment;
import com.glaf.core.util.RequestUtils;

@Controller
@Path("/rs/gisInfoRest")
public class GisInfoResourceRest {

	protected static final Log logger = LogFactory
			.getLog(GisInfoResourceRest.class);

	private GisInfoService gisInfoService;

	/**
	 * 根据传递的参数查询相似名称的节点list，返回JSONArray
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getGisGinttTree")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getGisGinttTree(@Context HttpServletRequest request)
			throws IOException {
		String systemName = RequestUtils.getString(request, "systemName",
				"default");
		String id = ParamUtil.getParameter(request, "id");
		// //System.out.println("request的字符编码："+request.getCharacterEncoding());
		//System.out.println(request.getParameter("name"));
		String name = ParamUtil.getParameter(request, "name");
		//System.out.println(name);
		Environment.setCurrentSystemName(systemName);

		GisInfo gisInfo = new GisInfo();
		if (id != null && !id.equals(""))
			gisInfo.setId(id);
		if (name != null && !name.equals(""))
			gisInfo.setName(name);
		List<GisInfo> list = gisInfoService.getGisGinttTree(gisInfo);
		//System.out.println("行数：" + list.size());
		JSONArray jsonArray = new JSONArray();
		String tmp = null;
		int i = 1;
		for (GisInfo gis : list) {
			JSONObject jobject = new JSONObject();
			jobject.put("id", String.valueOf(gis.getIndex_id()));
			jobject.put("title", String.valueOf(gis.getName()));
			tmp = gis.getParent_id();
			if (name != null && !name.equals("")) {
				jobject.put("parentId", "");
			} else {
				if (tmp != null && !tmp.equals("26533") && i != 1) {
					jobject.put("parentId", tmp);
				} else {
					jobject.put("parentId", "");
				}
			}
			// tmp =gis.getNlevel();
			// if(tmp != null && !tmp.equals("4"))
			jobject.put("summary", true);
			jobject.put("start", String.valueOf(gis.getbDate()));
			jobject.put("end", String.valueOf(gis.geteDate()));
			jobject.put("expanded", true);
			jobject.put("orderID", i);
			jsonArray.put(jobject);
			i++;
		}
		//System.out.println(jsonArray.toString('\n'));
		return jsonArray.toString().getBytes("UTF-8");
	}

	/**
	 * 根据传递的参数查询相似名称的节点list，返回JSONArray
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/getGisInfo")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getGisInfo(@Context HttpServletRequest request)
			throws IOException {
		String systemName = RequestUtils.getString(request, "systemName",
				"default");
		String name = ParamUtil.getParameter(request, "name");
		// //System.out.println("后台获取的indexId为：" + indexId);
		Environment.setCurrentSystemName(systemName);

		List<GisInfo> list = gisInfoService.getgisInfoListByName(name);
		JSONArray jsonArray = new JSONArray();
		for (GisInfo gis : list) {
			JSONObject jobject = new JSONObject();
			jobject.put("id", String.valueOf(gis.getId()));
			jobject.put("pId", String.valueOf(gis.getId()));
			jobject.put("name", String.valueOf(gis.getName()));
			// =[{"id":"311","pId":"301","indexId":"null","systemName":"2","name":"分部工程开工申请批复单及附件","isParent":false},{"

			jsonArray.put(jobject);
		}
		return jsonArray.toString().getBytes("UTF-8");
	}

	@Resource
	public void setGisInfoService(GisInfoService gisInfoService) {
		this.gisInfoService = gisInfoService;
	}

}
