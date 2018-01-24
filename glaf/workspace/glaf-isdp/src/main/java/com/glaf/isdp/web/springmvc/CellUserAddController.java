package com.glaf.isdp.web.springmvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.query.CellRepInfoQuery;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.CellRepInfoService;

@Controller("/isdp/cellUserAdd")
@RequestMapping("/isdp/cellUserAdd")
public class CellUserAddController {

	private IFieldInterfaceService fieldInterfaceService;

	private CellDataTableService cellDataTableService;
	
	private CellRepInfoService cellRepInfoService;
	
	@RequestMapping(params = "method=list")
	public ModelAndView list(HttpServletRequest request) throws Exception {
		String systemName = Environment.getCurrentSystemName();
		String id = RequestUtils.getString(request, "id");
		if(id == null || StringUtils.isEmpty(id)){
			throw new Exception("请传入参数id");
		}
		String tableId = RequestUtils.getString(request, "tableId");
		String type = RequestUtils.getString(request, "type","list");
		int pagesize = RequestUtils.getInt(request, "pagesize");
		
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);

		JSONObject results = new JSONObject();

		CellDataTable table = cellDataTableService.getCellDataTable(tableId);
		if (table == null) {
			throw new Exception(Environment.getCurrentSystemName()+":找不到表：tableId=" + tableId);
		}
		results.put("table", table);

		JSONArray columnArray = new JSONArray();
		JSONObject columnObject = null;
		if ("list".equalsIgnoreCase(type)) {
			// 如果是普通表，从interface表中查询字段
			List<FieldInterface> list = fieldInterfaceService.getFieldsByFrmType(tableId);
			for (FieldInterface model : list) {
				columnObject = new JSONObject();
				columnObject.put("field", model.getDname());
				columnObject.put("title", model.getFname());
				columnArray.add(columnObject);
			}
		} else if ("cell".equalsIgnoreCase(type)){
			//如果是cell表，从cell_repinfo表中查询字段
			CellRepInfoQuery query = new CellRepInfoQuery();
			query.setFrmType(tableId);
			List<CellRepInfo> cellRepinfoList = cellRepInfoService.list(query);
			for(CellRepInfo model : cellRepinfoList){
				columnObject = new JSONObject();
				columnObject.put("field", model.getDname());
				columnObject.put("title", model.getFname());
				columnArray.add(columnObject);
			}
		}
		results.put("columns", columnArray);
		results.put("pageSize", pagesize==0?PageResult.DEFAULT_PAGE_SIZE:pagesize);
		request.setAttribute("results", results);

		Environment.setCurrentSystemName(systemName);

		return new ModelAndView("/isdp/cell_useradd/cell_useradd_list");
	}
	
	@Resource
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}
	
	@Resource(name="com.glaf.isdp.service.cellDataTableService")
	public void setCellDataTableService(CellDataTableService cellDataTableService) {
		this.cellDataTableService = cellDataTableService;
	}

	@Resource(name="com.glaf.isdp.service.cellRepInfoService")
	public void setCellRepInfoService(CellRepInfoService cellRepInfoService) {
		this.cellRepInfoService = cellRepInfoService;
	}

}
