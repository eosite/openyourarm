package com.glaf.monitor.server.web.springmvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.monitor.server.domain.MonitorCategory;
import com.glaf.monitor.server.domain.MonitorProc;
import com.glaf.monitor.server.domain.MonitorProcCategory;
import com.glaf.monitor.server.domain.MonitorProcItem;
import com.glaf.monitor.server.domain.MonitorTerminal;
import com.glaf.monitor.server.domain.MonitorTerminalItem;
import com.glaf.monitor.server.query.MonitorCategoryQuery;
import com.glaf.monitor.server.query.MonitorProcCategoryQuery;
import com.glaf.monitor.server.query.MonitorProcItemQuery;
import com.glaf.monitor.server.query.MonitorProcQuery;
import com.glaf.monitor.server.query.MonitorTerminalItemQuery;
import com.glaf.monitor.server.query.MonitorTerminalQuery;
import com.glaf.monitor.server.service.MonitorCategoryService;
import com.glaf.monitor.server.service.MonitorProcCategoryService;
import com.glaf.monitor.server.service.MonitorProcItemService;
import com.glaf.monitor.server.service.MonitorProcService;
import com.glaf.monitor.server.service.MonitorTerminalItemService;
import com.glaf.monitor.server.service.MonitorTerminalService;
import com.glaf.monitor.server.util.MonitorCacheUtil;
import com.glaf.monitor.server.util.MonitorLogUtils;

@Controller("/momitor/server")
@RequestMapping("/momitor/server")
public class MonitorServerController {
	protected static final Log logger = LogFactory.getLog(MonitorServerController.class);
	
//	@Autowired
//	protected MonitorLogService monitorLogService;
	
	@Autowired
	protected MonitorLogUtils monitorLogUtils;
	@Autowired
	protected MonitorProcService monitorProcService;
	
	@Autowired
	protected MonitorTerminalItemService monitorTerminalItemService;
	
	@Autowired
	protected MonitorProcItemService monitorProcItemService;
	
	
	public MonitorServerController() {
		// TODO Auto-generated constructor stub
	}
	
	public List<MonitorProcItem> getMonitorProcItemByid(){
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/buildConnection")
	public byte[] buildConnection(HttpServletRequest request) throws Exception{
		JSONObject result = new JSONObject();
		try{
			String id = RequestUtils.getString(request, "id");
			String serverUrl = RequestUtils.getString(request, "serverUrl");
			String clientUrl = RequestUtils.getString(request, "clientUrl");
			
			JSONObject ret = new JSONObject(),
					variable = new JSONObject(),
					serverData = new JSONObject(),
					procData = new JSONObject();
			if(id != null){
				ret.put("serverDatas", variable);
				ret.put("procDatas", procData);
				variable.put(id,serverData);
				//服务器信息
				serverData.put("id", id);
				serverData.put("serverUrl", serverUrl);
				
				MonitorTerminal terminal = monitorTerminalService.getMonitorTerminal(id);
				if(terminal != null && terminal.getDeleteFlag() == 0){
					//服务器基础属性
					serverData.put("monitorServiceAddress", terminal.getMonitorServiceAddress());
					serverData.put("type", terminal.getType());
					
					JSONArray variableAry = null;	//中间变量
					JSONObject variableJson = null,variableJson2 = null,monitorProcData = null;	//中间变量
					//获取服务器的预警项(或基础属性，根目录)
					MonitorTerminalItemQuery terminalItemQuery = new MonitorTerminalItemQuery();
					terminalItemQuery.setTerminalId(id);
					terminalItemQuery.setDeleteFlag(0);
					List<MonitorTerminalItem> terminalItemAry = monitorTerminalItemService.list(terminalItemQuery);
					variableAry = new JSONArray();
					variableJson2 = new JSONObject();
					for(MonitorTerminalItem terminalItem : terminalItemAry){
						if(StringUtils.equals(terminalItem.getType(),"2")){
							variableJson2.put(terminalItem.getCode(), terminalItem.getAlarmVal());
						}else{
							variableJson = new JSONObject();
							variableJson.put("code", terminalItem.getCode());
							variableJson.put("value", terminalItem.getAlarmVal());
							variableJson.put("monitorServiceAddress",terminalItem.getMonitorServiceAddress());
							variableAry.add(variableJson);
						}
					}
					serverData.put("alarmItems", variableAry);
					serverData.put("baseItems", variableJson2);
					
					//获取服务器中的进程及其预警项/基础属性
					JSONArray procDatas = new JSONArray();
					MonitorProcQuery query = new MonitorProcQuery();
					query.setTerminalId(id);
					query.setDeleteFlag(0);
					List<MonitorProc> list = monitorProcService.list(query);
					
					MonitorProcItemQuery monitorProcItemQuery;
					List<MonitorProcItem> MonitorProcItemAry= null;
					for(MonitorProc monitorProc : list){
						monitorProcData = new JSONObject();
						monitorProcData.put("id", monitorProc.getId());
						monitorProcData.put("monitorServiceAddress", monitorProc.getMonitorServiceAddress());
						monitorProcData.put("type", monitorProc.getType());
						
						monitorProcItemQuery = new MonitorProcItemQuery();
						monitorProcItemQuery.setProcId(monitorProc.getId());
						monitorProcItemQuery.setDeleteFlag(0);
						MonitorProcItemAry = monitorProcItemService.list(monitorProcItemQuery);
						variableAry = new JSONArray();
						variableJson2 = new JSONObject();
						for(MonitorProcItem monitorProcItem : MonitorProcItemAry){
							if(StringUtils.equals(monitorProcItem.getType(),"2")){
								variableJson2.put(monitorProcItem.getCode(), monitorProcItem.getValue());
							}else{
								variableJson = new JSONObject();
								variableJson.put("code", monitorProcItem.getCode());
								variableJson.put("value", monitorProcItem.getAlarmVal());
								variableJson.put("monitorServiceAddress",monitorProcItem.getMonitorServiceAddress());
								variableAry.add(variableJson);
							}
						}
						monitorProcData.put("alarmItems", variableAry);
						variableJson2.put("port", monitorProc.getPort());
						monitorProcData.put("baseItems", variableJson2);
						
						procData.put(monitorProc.getId(), monitorProcData);
					}
					
					return ret.toJSONString().getBytes("UTF-8");
					
					//发送请求建立连接
//					NameValuePair[] param = {new NameValuePair("data",ret.toJSONString())};
//					result = getClientSystemInfo(clientUrl,param);
//					if(result.getString("statusCode") == "200"){
//						terminal.setStatus(1);
//						monitorTerminalService.save(terminal);
//					}
				}
			}
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLastSystemInfo")
	@ResponseBody
	public byte[] getLastSystemInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try{
			String id = RequestUtils.getString(request, "id");
			JSONObject obj = MonitorCacheUtil.getLastMonitorTerminalData(id);
			if(obj != null){
				result.put("data", obj);
				result.put("statusCode","200");
			}else{
				result.put("statusCode","500");
			}
			
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSystemInfo")
	@ResponseBody
	public byte[] getSystemInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		try{
			String id = RequestUtils.getString(request, "id");
			JSONArray list = MonitorCacheUtil.getMonitorTerminalData(id);
			if(list != null){
				result.put("rows", list);
				result.put("statusCode","200");
			}else{
				result.put("statusCode","500");
			}
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 发送请求
	 * @param url	客户端url地址
	 * @return
	 */
	public JSONObject getClientSystemInfo(String url, NameValuePair[] param){
		HttpClient client = new HttpClient();// 定义client对象
		PostMethod post = null;
		JSONObject result = new JSONObject();	//请求后返回的结果
		
		try{
			post = new PostMethod(url);
			if(param != null)
				post.setRequestBody(param);
			
			int statusCode = client.executeMethod(post); // 打印服务器返回的状态
			result.put("statusCode", statusCode);
			if (statusCode == HttpStatus.SC_OK) {  
				//连接请求失败
				result.put("result",post.getResponseBodyAsString());	//反馈结果
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			if(post != null){
				post.releaseConnection();
			}
		}
		
		return result;
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/startProc")
	@ResponseBody
	public byte[] startProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		try{
			MonitorProc monitorProc = monitorProcService.getMonitorProc(id);
			if(monitorProc != null){
				//发送请求开启进程
				NameValuePair[] param = { new NameValuePair("name",monitorProc.getProcessName()),
						new NameValuePair("id",monitorProc.getId()),
						new NameValuePair("port",String.valueOf(monitorProc.getPort())),
						new NameValuePair("command",String.valueOf(monitorProc.getStartCommand()))};
				
				JSONObject ret = getClientSystemInfo(monitorProc.getStartAddress(),param);
				String retStr = null;
				if(ret.getIntValue("statusCode") == HttpStatus.SC_OK){
					result = JSONObject.parseObject(ret.getString("result"));//反馈结果
					retStr = result.getString("message");
				}else{
					retStr = "开始服务/进程操作请求超时，请重新操作";
					result.put("message","请求超时，请重新操作");
					result.put("statusCode","500");
				}
				retStr +="\n请求url("+monitorProc.getStartAddress()+")";
				retStr += "\n请求参数(id:"+monitorProc.getId()
					+",port:"+String.valueOf(monitorProc.getPort())
					+",command"+String.valueOf(monitorProc.getStartCommand()) +")"; 
				monitorLogUtils.saveProcLog(monitorLogUtils.getOrSave(monitorProc),retStr);
			}
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 获取计算机操作系统的信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stopProc")
	@ResponseBody
	public byte[] stopProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		try{
			MonitorProc monitorProc = monitorProcService.getMonitorProc(id);
			if(monitorProc != null){
				//发送请求关闭进程
				NameValuePair[] param = { new NameValuePair("name",monitorProc.getProcessName()),
						new NameValuePair("id",monitorProc.getId()),
						new NameValuePair("port",String.valueOf(monitorProc.getPort())),
						new NameValuePair("command",String.valueOf(monitorProc.getStopCommand()))};
				JSONObject ret = getClientSystemInfo(monitorProc.getStopAddress(),param);
				String retStr = null;
				if(ret.getIntValue("statusCode") == HttpStatus.SC_OK){
					result = JSONObject.parseObject(ret.getString("result"));//反馈结果
					retStr = result.getString("message");
				}else{
					retStr = "停止服务/进程操作请求超时，请重新操作";
					result.put("message","请求超时，请重新操作");
					result.put("statusCode","500");
				}
				retStr += "\n";
				retStr += "请求参数(id:"+monitorProc.getId()
					+",port:"+String.valueOf(monitorProc.getPort())
					+",command"+String.valueOf(monitorProc.getStartCommand()); 
				monitorLogUtils.saveProcLog(monitorLogUtils.getOrSave(monitorProc),retStr);
			}
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	/**
	 * 终止进程
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/terminateProc")
	@ResponseBody
	public byte[] terminateProc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id =  RequestUtils.getParameter(request, "id");
		try{
			MonitorProc monitorProc = monitorProcService.getMonitorProc(id);
			if(monitorProc != null){
				//发送请求关闭进程
				NameValuePair[] param = { new NameValuePair("name",monitorProc.getProcessName()),
						new NameValuePair("id",monitorProc.getId()),
						new NameValuePair("port",String.valueOf(monitorProc.getPort())),
						new NameValuePair("command",String.valueOf(monitorProc.getTerminateCommand()))};
				JSONObject ret = getClientSystemInfo(monitorProc.getTerminateAddress(),param);
				String retStr = null;
				if(ret.getIntValue("statusCode") == HttpStatus.SC_OK){
					result = JSONObject.parseObject(ret.getString("result"));//反馈结果
					retStr = result.getString("message");
				}else{
					retStr = "终止服务/进程操作请求超时，请重新操作";
					result.put("message","请求超时，请重新操作");
					result.put("statusCode","500");
				}
				retStr += "\n";
				retStr += "请求参数(id:"+monitorProc.getId()
					+",port:"+String.valueOf(monitorProc.getPort())
					+",command"+String.valueOf(monitorProc.getStartCommand()); 
				monitorLogUtils.saveProcLog(monitorLogUtils.getOrSave(monitorProc),retStr);
			}
		}catch(Exception e){
			result.put("statusCode","500");
			result.put("message",e.getMessage());
		}
		return result.toJSONString().getBytes("UTF-8");
	}
	
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		return new ModelAndView("/monitor/server/monitorServerManagement");
	}
	
	@RequestMapping("/procView")
	public ModelAndView procView(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		return new ModelAndView("/monitor/server/monitorProcManagement");
	}
	
	@RequestMapping("/view2")
	public ModelAndView view2(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		return new ModelAndView("/monitor/server/monitorServerManagement_new");
	}
	
	@RequestMapping("/procView2")
	public ModelAndView procView2(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);

		return new ModelAndView("/monitor/server/monitorProcManagement_new");
	}

	@ResponseBody
	@RequestMapping("/downloadProcInfo")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id = RequestUtils.getString(request, "id");
		String serverUrl = RequestUtils.getString(request, "serverUrl");
		
		JSONObject ret = new JSONObject();
		if(id != null){
			ret.put("id", id);
			ret.put("serverUrl", serverUrl);
			
			JSONArray procDatas = new JSONArray();
			MonitorProcQuery query = new MonitorProcQuery();
			query.setTerminalId(id);
			List<MonitorProc> list = monitorProcService.list(query);
			for(MonitorProc monitorProc : list){
				procDatas.add(monitorProc.toJsonObject());
			}
			
			ret.put("procDatas", procDatas.toJSONString());
			
			ResponseUtils.download(request, response, ret.toJSONString().getBytes("UTF-8"), "clientConfig");
		}
	}

	@Autowired
	protected MonitorCategoryService monitorCategoryService;
	@Autowired
	protected MonitorTerminalService monitorTerminalService;
	@Autowired
	protected MonitorProcCategoryService monitorProcCategoryService;
	
	public JSONArray convertTree(JSONArray array){
		JSONArray newtree = new JSONArray(),children = null;
		JSONObject hash = new JSONObject(),hashVP = null,item = null;
		int i = 0,length = array.size();
		for(i =0 ; i <  length; i ++){
			item = array.getJSONObject(i);
			hash.put(item.getString("id"),item);
		}
		for(i =0 ; i < length ; i ++){
			item = array.getJSONObject(i);
			hashVP = hash.getJSONObject(item.getString("pid"));
			if(hashVP != null){
				children = hashVP.getJSONArray("children");
				if(children == null){
					children = new JSONArray();
					hashVP.put("children", children);
				}
				children.add(item);
			}else{
				newtree.add(item);
			}
			hash.put(item.getString("id"),item);
		}
		return newtree;
	}
	
	/**
	 * 查询树形数据
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/treeData")
	@ResponseBody
	public byte[] treeData(HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		JSONObject result = new JSONObject();
		JSONArray rowsJSON = new JSONArray();
		JSONArray children = null;
		JSONObject rowJSON = null;
		
		//终端的分类
		MonitorCategoryQuery query = new MonitorCategoryQuery();
		query.setSortColumn("createtime");
		query.setSortOrder(" desc ");
		query.setDeleteFlag(0);
		List<MonitorCategory> list = monitorCategoryService.list(query);
		
		for (MonitorCategory monitorCategory : list) {
			//获取分类信息
			rowJSON = monitorCategory.toJsonObject();
			children = new JSONArray();
			rowJSON.put("id", monitorCategory.getId());
			rowJSON.put("monitorCategoryId", monitorCategory.getId());
			rowJSON.put("ztreeType", "terminalCategory");
			rowJSON.put("children", children);
			//插入分类信息
			rowsJSON.add(rowJSON);
			
//			insertMonitorterminal(children,monitorCategory.getId());
		}
		result.put("rows",convertTree(rowsJSON));
		
		return result.toJSONString().getBytes("UTF-8");
	}
	
	private void insertMonitorterminal(JSONArray rowsJSON, Integer categoryId){
		List<MonitorTerminal> terlist = null;
		MonitorTerminalQuery terquery = null;
		JSONObject rowJSON = null;
		JSONArray children = null;
		
		//查询对应的终端信息
		terquery = new MonitorTerminalQuery();
		terquery.setCategoryId(categoryId);
		terquery.setDeleteFlag(0);
		terlist = monitorTerminalService.list(terquery);
		
		for(MonitorTerminal monitorTerminal : terlist){
			//获取终端信息
			rowJSON = monitorTerminal.toJsonObject();
			rowJSON.put("id", monitorTerminal.getId());
			rowJSON.put("monitorTerminalId", monitorTerminal.getId());
			rowJSON.put("ztreeType", "terminal");
			children = new JSONArray();
			rowJSON.put("children", children);
			rowsJSON.add(rowJSON);
			
			insertProcCategory(children);
		}
	}
	
	private void insertProcCategory(JSONArray rowsJSON){
		List<MonitorTerminal> terlist = null;
		MonitorTerminalQuery terquery = null;
		JSONObject rowJSON = null;
		JSONArray children = null;
		
		//服务/进程的分类
		MonitorProcCategoryQuery pcquery = new MonitorProcCategoryQuery();
		pcquery.setSortColumn("createtime");
		pcquery.setSortOrder(" desc ");
		pcquery.setDeleteFlag(0);
		List<MonitorProcCategory> pclist = monitorProcCategoryService.list(pcquery);
		
		for (MonitorProcCategory monitorProcCategory : pclist) {
			//获取分类信息
			rowJSON = monitorProcCategory.toJsonObject();
			children = new JSONArray();
			rowJSON.put("id", monitorProcCategory.getId());
			rowJSON.put("monitorProcCategoryId", monitorProcCategory.getId());
			rowJSON.put("ztreeType", "procCategory");
			rowJSON.put("children", children);
			//插入分类信息
			rowsJSON.add(rowJSON);
			
			insertMonitorProc(children,monitorProcCategory.getId());
		}
	}
	
	private void insertMonitorProc(JSONArray rowsJSON, Integer categoryId){
		List<MonitorProc> list = null;
		MonitorProcQuery query = null;
		JSONObject rowJSON = null;
		JSONArray children = null;
		
		//查询对应的终端信息
		query = new MonitorProcQuery();
		query.setCategoryId(categoryId);
		query.setDeleteFlag(0);
		list = monitorProcService.list(query);
		
		for(MonitorProc item : list){
			//获取终端信息
			rowJSON = item.toJsonObject();
			rowJSON.put("id", item.getId());
			rowJSON.put("monitorProcId", item.getId());
			rowJSON.put("ztreeType", "proc");
			rowsJSON.add(rowJSON);
		}
	}
}
