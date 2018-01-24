package com.glaf.form.web.springmvc;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.query.ConvertElemTmplQuery;
import com.glaf.convert.service.ConvertElemTmplRefService;
import com.glaf.convert.service.ConvertElemTmplService;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.cell.service.CellConvertService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.video.service.VideoDeveloperService;

/**
 * 通用树结构数据调用
 * 
 * @author Administrator
 *
 */
@Controller("/form/cellData")
@RequestMapping("/form/cellData")
public class FormCellDataController {
	protected static final Log logger = LogFactory.getLog(FormCellDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;
	
	@Autowired
	protected VideoDeveloperService videoDeveloperService;
	
	@Autowired
	protected ConvertElemTmplRefService convertElemTmplRefService;
	
	@Autowired
	protected ConvertElemTmplService convertElemTmplService;

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	/**
	 * 获取cell表所有引用参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refData")
	public @ResponseBody byte[] getRefData(HttpServletRequest request) throws Exception {
		String pageId = RequestUtils.getString(request, "pageId");
		String wbsInstId = RequestUtils.getString(request, "wbsInstId");
		String domainId = "1";
		
		Long cvtId = Long.parseLong(pageId.replace(CellConvertService.prefix, ""));
		ConvertElemTmplQuery query = new ConvertElemTmplQuery();
		query.setCvtId(cvtId);
		List<ConvertElemTmpl> lists = convertElemTmplService.list(query);
		List<ConvertElemTmplRef> convertElemTmplRefs = convertElemTmplRefService.getConvertElemTmplRefsByCvtId(cvtId);
		ConvertElemTmpl convertElemTmpl = null ;
		JSONArray retAry = new JSONArray();
		JSONObject retObj = null;
		JSONObject valObj = null ;
		String sql = null ;
		String refType = null ;
		Long databaseId = 0l;
		List<Map<String, Object>> dataList = null ;
		for (ConvertElemTmplRef convertElemTmplRef : convertElemTmplRefs) {
			retObj = new JSONObject();
			valObj = new JSONObject();
			refType = convertElemTmplRef.getRefType() ;
			valObj.put("refType", refType );
			sql = convertElemTmplRef.getRefContent();
			if(refType!=null && refType.startsWith("criterion")){
				sql = sql.replaceAll("@wbsInstId", wbsInstId).replaceAll("@domainId", domainId);
			}
			dataList = mutilDatabaseBean.getDataListBySql(sql, databaseId);
			valObj.put("refVal", getRefValJSONObject(dataList));
			
			convertElemTmpl = getConvertElemTmpl(convertElemTmplRef.getCvtElemId(), lists);
			retObj.put(convertElemTmpl.getElemId(), valObj); //JSON对象值
			retAry.add(retObj);
		}
		//[ {'textbox_11_15' : { 'refType' : 'criterion' , 'refVal' : {'colA':'valA' , 'colB':'valB'} } ]
		return retAry.toJSONString().getBytes("UTF-8") ;
	}
	
	/**
	 * 返回查询结果的第一条记录结果集
	 * @param dataList
	 * @return
	 */
	private static JSONObject getRefValJSONObject(List<Map<String, Object>> dataList){
		JSONObject retObj = new JSONObject();
		if(dataList!=null && dataList.size()>0){
			Map<String, Object> map = dataList.get(0);
			Set<String> keys = map.keySet();
			String value = null ;
			for (String key : keys) {
				value = (String) map.get(key);
				retObj.put(key, value);
			}
		}
		return retObj ;
	}
	/**
	 * 返回对象
	 * @param cvtElemId
	 * @param lists
	 * @return
	 */
	private static ConvertElemTmpl getConvertElemTmpl(Long cvtElemId ,List<ConvertElemTmpl> lists){
		for (ConvertElemTmpl convertElemTmpl : lists) {
			if(cvtElemId.equals(convertElemTmpl.getCvtElemId()))
				return convertElemTmpl;
		}
		return null ;
	}
	
	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}



	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

}
