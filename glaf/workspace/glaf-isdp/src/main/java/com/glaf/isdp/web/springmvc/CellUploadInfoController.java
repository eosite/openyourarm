package com.glaf.isdp.web.springmvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.model.ITree;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;

import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.isdp.domain.CellUpicInfo;
import com.glaf.isdp.domain.IsdpDataFile;
import com.glaf.isdp.domain.TreeFolder;
import com.glaf.isdp.service.ICellUpicInfoService;
import com.glaf.isdp.service.ITreeFolderService;

@Controller("/isdp/cell/file")
@RequestMapping("/isdp/cell/file")
public class CellUploadInfoController {

	protected static final Log logger = LogFactory
			.getLog(CellUploadInfoController.class);

	protected IFieldInterfaceService fieldInterfaceService;

	protected ICellUpicInfoService cellUpicInfoService;
	
	protected ITreeFolderService treeFolderService;
	
	@javax.annotation.Resource(name = "com.glaf.isdp.service.treeFolderService")
	public void setTreeFolderService(ITreeFolderService treeFolderService) {
		this.treeFolderService = treeFolderService;
	}
	
	 

	@RequestMapping("/category")
	public ModelAndView category(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		String frmtype = "pfile";
		List<FieldInterface> fields = null;
		int treetopIndexId = 0;
		if (params != null && params.containsKey("treetopIndexId")) {
			treetopIndexId = ParamUtils.getInt(params, "treetopIndexId");
		}

		logger.debug("treetopIndexId=" + treetopIndexId);

		if (treetopIndexId > 0) {
			fields = fieldInterfaceService.getListShowFields(frmtype,
					treetopIndexId);
		} else {
			FieldInterfaceQuery query = new FieldInterfaceQuery();
			query.frmtype(frmtype).issystem("1").isListShow("1");
			fields = fieldInterfaceService.list(query);
		}

		if (fields != null && !fields.isEmpty()) {
			for (FieldInterface f : fields) {
				if (f.getListweigth() <= 0) {
					f.setListweigth(120);
				}
				if (f.getListweigth() > 500) {
					f.setListweigth(500);
				}
				if (f.getListweigth() < 60) {
					f.setListweigth(60);
				}

			}
			logger.debug("fields size:" + fields.size());
			request.setAttribute("fields", fields);
		}

		return new ModelAndView("/isdp/dispatcher/cell_file_category");
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		//JSONObject jsonObject = null;
		String fileId = request.getParameter("fileId");
		if (StringUtils.isNotEmpty(fileId)) {
			CellUpicInfo info = cellUpicInfoService.getCellUpicInfo(fileId);
			if (info != null) {
				//jsonObject = info.toJsonObject();
				request.setAttribute("cellUpicInfo", info);
				IsdpDataFile isdpFile = cellUpicInfoService.getCellDataFileById(info.getId());
				request.setAttribute("isdpFile", isdpFile);
			}
		}
		
		String treeNodeId = request.getParameter("treeNodeId");
		if(StringUtils.isNotEmpty(treeNodeId)){
			request.setAttribute("treeNodeId", treeNodeId);
		}
		

//		List<FieldInterface> fields = fieldInterfaceService
//				.getFieldsByFrmType("cell_upicinfo");
//		if (jsonObject != null && fields != null && !fields.isEmpty()) {
//			for (FieldInterface f : fields) {
//				if (jsonObject.containsKey(f.getDname())) {
//					if ("i4".equalsIgnoreCase(f.getDtype())
//							|| "int".equalsIgnoreCase(f.getDtype())) {
//						f.setValue(jsonObject.get(f.getDname()));
//					} else if ("i8".equalsIgnoreCase(f.getDtype())
//							|| "long".equalsIgnoreCase(f.getDtype())) {
//						f.setValue(jsonObject.get(f.getDname()));
//					} else if ("r8".equalsIgnoreCase(f.getDtype())
//							|| "double".equalsIgnoreCase(f.getDtype())) {
//						f.setValue(jsonObject.get(f.getDname()));
//					} else if ("date".equalsIgnoreCase(f.getDtype())
//							|| "datetime".equalsIgnoreCase(f.getDtype())) {
//						Object value = jsonObject.get(f.getDname());
//						if (value instanceof Date) {
//							Date date = (Date) value;
//							f.setValue(DateUtils.getDateTime(date));
//						} else {
//							f.setValue(jsonObject.get(f.getDname()));
//						}
//					} else {
//						f.setValue(jsonObject.get(f.getDname()));
//					}
//				}
//			}
//		}
//		request.setAttribute("fields", fields);

		return new ModelAndView("/isdp/dispatcher/cell_file_edit");
	}

	/**
	 * 文件资料列表
	 * @param request
	 * @return
	 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);

		String frmType = RequestUtils.getParameter(request, "frmType",
				"cell_upicinfo");

		List<FieldInterface> fields = fieldInterfaceService
				.getFieldsByFrmType(frmType);
		for (FieldInterface f : fields) {
			if (f.getListweigth() <= 0) {
				f.setListweigth(120);
			}
			if (f.getListweigth() > 500) {
				f.setListweigth(500);
			}

		}
		request.setAttribute("fields", fields);

		return new ModelAndView("/isdp/dispatcher/cell_filelist");
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUpicInfoService")
	public void setCellUpicInfoService(ICellUpicInfoService cellUpicInfoService) {
		this.cellUpicInfoService = cellUpicInfoService;
	}

	@javax.annotation.Resource
	public void setFieldInterfaceService(
			IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}
	
	
//	@POST
	@RequestMapping("/saveFiles")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveFiles(@Context HttpServletRequest request)
			throws IOException {
		String id = request.getParameter("id");
		String treeNodeId = request.getParameter("treeNodeId");
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile>  fileMap = req.getFileMap();
		Set<String> keys = fileMap.keySet();
		CellUpicInfo cellUpicInfo = new CellUpicInfo();
		for (String key : keys) {
			MultipartFile file = fileMap.get(key);
			IsdpDataFile isdpFile = new IsdpDataFile();
			isdpFile.setFileName(file.getOriginalFilename());
			isdpFile.setFileSize(file.getSize());
			isdpFile.setFileContent(file.getBytes());
			if(StringUtils.isNotEmpty(id)){
				cellUpicInfo = cellUpicInfoService.getCellUpicInfo(id);
			}
			cellUpicInfo.setTname(file.getOriginalFilename());
			cellUpicInfo.setCtime(RequestUtils.getDate(request, "ctime"));
			if(StringUtils.isNotEmpty(treeNodeId)){
				cellUpicInfo.setIndexId(RequestUtils.getInt(request, "treeNodeId", 0));
			}
			cellUpicInfoService.save(cellUpicInfo);
			isdpFile.setId(cellUpicInfo.getId());
			
			cellUpicInfoService.saveFile(isdpFile);
		}
		
		JSONObject result = new JSONObject();
		result.put("id", cellUpicInfo.getId());
		result.put("treeNodeId", treeNodeId);
		return result.toJSONString().getBytes("UTF-8");
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public byte[] save(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		String id = request.getParameter("id");
		CellUpicInfo cellUpicInfo = new CellUpicInfo();
		if(StringUtils.isNotEmpty(id)){
			cellUpicInfo = cellUpicInfoService.getCellUpicInfo(id);
		}else{
			cellUpicInfo.setIndexId(RequestUtils.getInt(request, "treeNodeId", 0));
		}
		cellUpicInfo.setTname(RequestUtils.getString(request, "tname","") );
		cellUpicInfo.setTagnum(RequestUtils.getString(request, "tagnum",""));
		
		try {
			DateFormat df  = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			Date ctime = df.parse(RequestUtils.getString(request, "ctime",""));
			cellUpicInfo.setCtime(ctime);
			cellUpicInfo.setDuty(RequestUtils.getString(request, "duty",""));
			cellUpicInfo.setPage(RequestUtils.getInt(request, "page", 0));
			cellUpicInfo.setThematic(RequestUtils.getString(request, "thematic",""));
			cellUpicInfo.setAnnotations(RequestUtils.getString(request, "annotations",""));
		
			cellUpicInfoService.save(cellUpicInfo);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CellUploadInfoController/save 错误异常：" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		String id = request.getParameter("id");
		try {
			cellUpicInfoService.deleteById(id);
			cellUpicInfoService.deleteFolderById(id);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CellUploadInfoController/delete 错误异常：" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	
	@RequestMapping("/addNode")
	public ModelAndView addNode(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		
		//JSONObject jsonObject = null;
		int nodeType = RequestUtils.getInt(request, "nodeType", 0);
		request.setAttribute("nodeType", nodeType);
		int nodeId = RequestUtils.getInt(request, "nodeId", -1);
		//nodeType  1为增加同级  2为增加下级  3为修改 
		TreeFolder treeFolder  = new TreeFolder();
		if(nodeType == 1 || nodeType == 2){//同级增加/下级增加
			//根据parentId 获取 num 最大的元素
			TreeFolder model = treeFolderService.getMaxTreeFolderByParentId(nodeId) ;
			if(model == null ){
				model = treeFolderService.getTreeFolderPrimaryKey(nodeId);
				treeFolder.setNum(spriltStr(model.getNum()+".0"));
			}else{
				treeFolder.setNum(spriltStr(model.getNum()));
			}
			treeFolder.setParentId(nodeId);
		}else if(nodeType == 3){//修改信息
			treeFolder =  treeFolderService.getTreeFolderPrimaryKey(nodeId);
		}
		request.setAttribute("treeFolder", treeFolder);
		
		return new ModelAndView("/isdp/dispatcher/add_node");
	}
	/**
	 * 获取下一个文件分类号值
	 * @param nums
	 * @return
	 */
	private String spriltStr(String nums){
		String returnStr = "" ;
		String lastStr = "0" ;
		if(!"".equals(nums)){
			StringTokenizer token = new StringTokenizer(nums, ".");
			returnStr = nums.substring(0, nums.lastIndexOf(".")+1) ;
			while (token.hasMoreTokens()) {
				lastStr = token.nextToken();
			}
		}
		lastStr = Integer.parseInt(lastStr)+1+"";
		returnStr += lastStr ;
		return returnStr ;
	}
	
	@RequestMapping("/saveNode")
	@ResponseBody
	public byte[] saveNode(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		logger.debug("paramMap:" + paramMap);
		
		try {
			TreeFolder treeFolder = new TreeFolder();
			treeFolder.setIndexId(RequestUtils.getInteger(request, "indexId", null));
			treeFolder.setParentId(RequestUtils.getInteger(request, "parentId", null));
			//treeFolder.setIndexName(RequestUtils.getInteger(request, "indexId", 0)+" "+RequestUtils.getString(request, "sindexName", null));
			treeFolder.setSindexName(RequestUtils.getString(request, "sindexName", null));
			treeFolder.setContent(RequestUtils.getString(request, "content", null));
			treeFolder.setInttype(0);
			treeFolder.setNum(RequestUtils.getString(request, "num", null));
			treeFolder.setFilenum(RequestUtils.getString(request, "filenum", null));
			treeFolder.setFtype("1");
			treeFolder.setSlevel(RequestUtils.getString(request, "slevel", null));
			treeFolder.setSavetime(RequestUtils.getString(request, "savetime", null));
			
			TreeFolder parentModel = treeFolderService.getTreeFolderPrimaryKey(RequestUtils.getInteger(request, "parentId", null));
			if(parentModel != null ){
				treeFolder.setId(parentModel.getId());
				treeFolder.setLevel(parentModel.getLevel()+1);
			}else{
				treeFolder.setId("");
				treeFolder.setLevel(0);
			}
		
			treeFolderService.save(treeFolder);
			ITree itree = treeFolder ;
			JSONObject result = new JSONObject();
			result.put("treeFolder", JSON.toJSONString(itree));
			result.put("nodeType", RequestUtils.getInt(request, "type",0));
			
			return result.toJSONString().getBytes("UTF-8");
					//ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CellUploadInfoController/saveNode 错误异常：" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	
	@RequestMapping("/deleteNode")
	@ResponseBody
	public byte[] deleteNode(HttpServletRequest request, ModelMap modelMap) {
		String id = RequestUtils.getString(request, "id","");
		int indexId = RequestUtils.getInt(request, "indexId",0);
		try {
			int count = cellUpicInfoService.getCellUpicInfoCountByLikeId(id+"%");
			if(count > 0 ){
				return ResponseUtils.responseJsonResult(false);
			}
			//级联删除
			treeFolderService.deleteByPrimaryKeyCascade(indexId);
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CellUploadInfoController/deleteNode 错误异常：" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	@RequestMapping("/moveNode")
	public ModelAndView moveNode(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		return new ModelAndView("/isdp/dispatcher/move_node");
	}
	
	@RequestMapping("/saveMove")
	@ResponseBody
	public byte[] saveMove(HttpServletRequest request, ModelMap modelMap) {
		int selectedId = RequestUtils.getInt(request, "selectedId",0);
		TreeFolder selectedTreeFolder = treeFolderService.getMaxTreeFolderByParentId(selectedId);
		String selectedNextMaxNum = "" ;
		String tagerId = "" ;
		int parentId  ; 
		if(selectedTreeFolder == null){
			selectedTreeFolder = treeFolderService.getTreeFolderPrimaryKey(selectedId);
			tagerId = selectedTreeFolder.getId() ;
			selectedNextMaxNum = spriltStr(selectedTreeFolder.getNum()+".0");
			parentId = selectedTreeFolder.getIndexId();
		}else{
			String tid = selectedTreeFolder.getId().substring(0, selectedTreeFolder.getId().length()-1);
			tagerId = tid.substring(0, tid.lastIndexOf("|")+1);
			selectedNextMaxNum = spriltStr(selectedTreeFolder.getNum());
			parentId = selectedTreeFolder.getParentId() ;
		}
		
		
		String checkedIds = RequestUtils.getString(request, "checkedIds","");
		StringTokenizer st = new StringTokenizer(checkedIds,",");
		while(st.hasMoreElements()){
			int checkedId =  Integer.parseInt((String)st.nextElement());
			try {
				TreeFolder checkedTreeFolder = treeFolderService.getTreeFolderPrimaryKey(checkedId);
				
				checkedTreeFolder.setParentId(parentId);
				treeFolderService.updateTreeFoldeByMove(checkedTreeFolder,tagerId,selectedNextMaxNum);
				
				return ResponseUtils.responseJsonResult(true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("CellUploadInfoController/deleteNode 错误异常：" + e.getMessage());
			}
		}
		return ResponseUtils.responseJsonResult(false);
	}
	/**
	 * 跳转文件分类页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadNode")
	public ModelAndView uploadNode(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		String nodeId = RequestUtils.getString(request, "nodeId", "");
		request.setAttribute("nodeId", nodeId);
		return new ModelAndView("/isdp/dispatcher/upload_node");
	}
	/**
	 * 导入文件分类
	 * @param request
	 * @return
	 */
	@SuppressWarnings("resource")
	@RequestMapping("/saveUploadNode")
	@ResponseBody
	public byte[] saveUploadNode(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile>  fileMap = req.getFileMap();
		Set<String> keys = fileMap.keySet();
		try {
			for (String key : keys) {
				MultipartFile file = fileMap.get(key);
					InputStream is = file.getInputStream();
					InputStreamReader isr = new InputStreamReader(is,"GBK");
					BufferedReader  br = new BufferedReader (isr);
					String data = br.readLine() ;
					List<TreeFolder> treeModels = new ArrayList<TreeFolder>();
					TreeFolder treeFolder = null ;
					//9 公路工程施工工艺//9//0//&//秘密//永久
					String [] datas ;
					String[] indexNames ;
					int datasLength = 0 ;
					while(data != null ){
						if(!"".equals(data) && !data.startsWith("资料") && !data.startsWith("格式：")){
							datas = data.split("//");
							datasLength = datas.length ;
							treeFolder = new TreeFolder();
							treeFolder.setIndexName(datas[0]);
							if(!StringUtils.isEmpty(datas[0])){
								indexNames = datas[0].split(" ");
								treeFolder.setNum(indexNames[0]);;
								treeFolder.setSindexName(indexNames[1]);
							}
							if(datasLength > 1){
								treeFolder.setFilenum(datas[1]);
							}
							if(datasLength >2 && !StringUtils.isEmpty(datas[2])){
								treeFolder.setZtype(Integer.parseInt(datas[2]));
							}
							if(datasLength >3 && !StringUtils.isEmpty(datas[3])){
								treeFolder.setContent(datas[3].replace("&", "\n"));
							}
							if(datasLength > 4){
								treeFolder.setSlevel(datas[4]);
							}
							if(datasLength > 5){
								treeFolder.setSavetime(datas[5]);
							}
							treeModels.add(treeFolder);
						}
						data = br.readLine() ;
					}
					JSONArray responseJSON = getTreeArrayNode(treeModels);
					return responseJSON.toJSONString().getBytes("UTF-8") ;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}
		return ResponseUtils.responseJsonResult(false);
	}
	/**
	 * 获取ztree格式
	 * @param treeModels
	 * @return
	 */
	private JSONArray getTreeArrayNode(List<TreeFolder> treeModels) {
		JSONArray array = new JSONArray() ;
		JSONObject jsonObj  ;
		 for (TreeFolder treeFolder : treeModels) {
			 jsonObj = (JSONObject) JSON.toJSON(treeFolder);
			 jsonObj.put("name", jsonObj.get("indexName"));
			 if(!isChildren(array, jsonObj)){
				 array.add(jsonObj);
			 }
		}
		return array;
	}
	private boolean isChildren(JSONArray parentArray ,JSONObject children){
		JSONObject parent ; 
		JSONArray childrenArray ;
		boolean flag = false ;
		for (Object object : parentArray) {
			parent = (JSONObject)object;
			if(children.get("num").toString().startsWith(parent.get("num").toString()) 
					&& children.get("num").toString().substring(parent.get("num").toString().length()+1, 
							children.get("num").toString().length()).indexOf(".")==-1){
				if(parent.containsKey("children")){//如果有子节点
					childrenArray = (JSONArray) parent.get("children");
					childrenArray.add(children);
					parent.put("children", childrenArray);
				}else{
					childrenArray =  new JSONArray();
					childrenArray.add(children);
					parent.put("children", childrenArray);
				}
				flag = true ;
			}else if(parent.containsKey("children")){//如果父节点有子节点则 再查找
				if (this.isChildren((JSONArray) parent.get("children"), children)){
					flag = true ;
				}
			}
		}
		
		return flag ;
	}
	
	@RequestMapping("/saveImportNodes")
	@ResponseBody
	public byte[] saveImportNodes(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		//Map<String, Object> params = RequestUtils.getParameterMap(request);
	
		//logger.debug("params:" + params);
		
		int nodeId = RequestUtils.getInt(request, "nodeId" ,-1);
		boolean isNew = RequestUtils.getBoolean(request, "isNew");
		String nodes =  RequestUtils.getString(request, "nodeAarray","");
		try {
			JSONArray nodeArray = JSON.parseArray(nodes);
			JSONObject node  ;
			TreeFolder treeFolder  ;
			TreeFolder tf  ;
			for (Object object : nodeArray) {
				node =  (JSONObject) object;
				treeFolder = JSON.parseObject(node.toJSONString(), TreeFolder.class);
				treeFolder.setParentId(isNew?-1:nodeId);
				treeFolderService.save(treeFolder);
				if(isNew){
					treeFolder.setId(treeFolder.getIndexId()+"|");
				}else{
					tf = treeFolderService.getTreeFolderPrimaryKey(nodeId);
					treeFolder.setId(tf.getId()+treeFolder.getIndexId()+"|");
				}
				treeFolderService.save(treeFolder);
				if(node.containsKey("children")){
					JSONArray childrens = (JSONArray) node.get("children");
					saveChildNode(childrens, treeFolder);
				}
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CellUploadInfoController/saveImportNodes  错误异常：" + e.getMessage());
		}
		return ResponseUtils.responseJsonResult(false);
	}
	
	private void saveChildNode(JSONArray childrens, TreeFolder parentTreeFolder){
		JSONObject node  ;
		TreeFolder treeFolder  ;
		for (Object object : childrens) {
			node =  (JSONObject) object;
			treeFolder = JSON.parseObject(node.toJSONString(), TreeFolder.class);
			treeFolder.setParentId(parentTreeFolder.getIndexId());
			treeFolderService.save(treeFolder);
			treeFolder.setId(parentTreeFolder.getId()+treeFolder.getIndexId()+"|");
			treeFolderService.save(treeFolder);
			if(node.containsKey("children")){
				JSONArray c = (JSONArray) node.get("children");
				saveChildNode(c, treeFolder);
			}
		}
	}
	
}


