package com.glaf.isdp.web.springmvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.util.RequestUtils;

@Controller("/isdp/collect")
@RequestMapping("/isdp/collect")
public class CollectController {
	@Resource
	protected IFieldInterfaceService fieldInterface;
	
	@RequestMapping(params = "method=showCharts")
	public ModelAndView showCharts(HttpServletRequest request) throws Exception {
		String tableName = RequestUtils.getString(request, "tableName");
		int completionType = RequestUtils.getInt(request, "completionType");
		int tree = RequestUtils.getInt(request, "tree");
		request.setAttribute("tree", tree);
		if(tableName==null || tableName.isEmpty()){
			throw new Exception("Error:获取表名错误,没有传入表名参数!");
		}
		
		tableName = tableName.toUpperCase();
		JSONObject results = new JSONObject();
		
		List<FieldInterface> list = fieldInterface.getInterfacesByTableId(tableName);
		JSONArray series = new JSONArray();
		JSONArray columns = new JSONArray();
		JSONArray groupByField = new JSONArray();
		JSONObject jobject = null;
		for(FieldInterface model : list){
			
			if(model.getListId().equalsIgnoreCase(tableName+"_0")){
				request.setAttribute("chartName", this.getChartName(model.getFname(),completionType));
			}
			
			if(model.getIsListShow()!=null && model.getIsListShow().equals("1")){
				jobject = new JSONObject();
				jobject.put("field", model.getDname());
				jobject.put("title", model.getFname());
				jobject.put("dtype", model.getDtype());
				if(model.getDname().toUpperCase().indexOf("PERCENT")!=-1){
					jobject.put("format", "{0:p1}");
				}
				columns.add(jobject);
			}
			
			if(model.getShowtype()!= null && model.getShowtype().equals("1")){
				jobject = new JSONObject();
				jobject.put("field", model.getDname());
				jobject.put("name", model.getFname());
				series.add(jobject);
			}
			
			if(model.getIsGroupBy()!=null && model.getIsGroupBy().equals("1")){
				jobject = new JSONObject();
				jobject.put("value", model.getDname());
				jobject.put("text", model.getFname());
				groupByField.add(jobject);
			}
		}
		results.put("groupByField",groupByField);
		results.put("series", series);
		results.put("columns", columns);
		request.setAttribute("results", results);
		
		ModelAndView modelAndView = null;
		if("KEY_POINT_SET".equalsIgnoreCase(tableName)){
			modelAndView = new ModelAndView("/isdp/collect/key_point_set");
		}else if("MATERIAL_INSPECTION_PLAN".equalsIgnoreCase(tableName)){
			modelAndView = new ModelAndView("/isdp/collect/material_inspection_plan");
		}else if("COMPLETION_CASE_STATIS".equalsIgnoreCase(tableName)){
			modelAndView = new ModelAndView("/isdp/collect/completion_case_statis_table");
		}else if("COMPLETION_TREEPINFO".equalsIgnoreCase(tableName)){
			modelAndView = new ModelAndView("/isdp/collect/completion_treepinfo");
		}else if("QUALITY_QUESTION_STAT".equalsIgnoreCase(tableName)){
			String type=RequestUtils.getString(request, "type");
			modelAndView = new ModelAndView("/isdp/collect/fb_question_stat_"+type);
		}
		
		return modelAndView;
	}

	/**
	 * 取得图表标题
	 * @param tableName
	 * @param fname
	 * @return
	 */
	private Object getChartName(String fname,Integer completionType) {
		String chartName = "";
		switch(completionType){
			case 1:
				chartName = "开工报告"+fname;
				break;
			case 2:
				chartName = "施工放样"+fname;
				break;
			case 3:
				chartName = "中间交工"+fname;
				break;
			default:
				chartName = fname;
		}
		return chartName;
	}

	public void setFieldInterface(IFieldInterfaceService fieldInterface) {
		this.fieldInterface = fieldInterface;
	}
	
	
}
