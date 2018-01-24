package com.glaf.report.web.springmvc;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.BaseDataInfo;
@Controller("/spread/report")
@RequestMapping("/spread/report/")
public class SpreadReportController {
		@RequestMapping("/view")
		public ModelAndView view(HttpServletRequest request, ModelMap modelMap)
				throws UnsupportedEncodingException {
	        //获取表单数据服务地址
			BaseDataManager bm=BaseDataManager.getInstance();
			BaseDataInfo baseData=bm.getBaseData("getReportData", "spreadReportServer");
			if(baseData!=null){
				modelMap.put("serverUrl", baseData.getExt1());
			}
			return new ModelAndView("/spreadjs/report_view", modelMap);
		}
}
