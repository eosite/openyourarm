package com.glaf.isdp.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.base.DataRequest;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.query.CellDataTableQuery;
import com.glaf.isdp.query.CellRepInfoQuery;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.CellRepInfoService;
import com.glaf.isdp.service.CellUserAddService;
import com.glaf.isdp.util.JSONConvertUtil;

@Controller
@Path("/rs/isdp/cellUserAddRest")
public class CellUserAddRest {

	protected CellUserAddService cellUserAddService;
	protected CellDataTableService cellDataTableService;
	protected CellRepInfoService cellRepInfoService;
	protected IFieldInterfaceService fieldInterfaceService;

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellUserAddService")
	public void setCellUserAddService(CellUserAddService cellUserAddService) {
		this.cellUserAddService = cellUserAddService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellDataTableService")
	public void setCellDataTableService(CellDataTableService cellDataTableService) {
		this.cellDataTableService = cellDataTableService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.cellRepInfoService")
	public void setCellRepInfoService(CellRepInfoService cellRepInfoService) {
		this.cellRepInfoService = cellRepInfoService;
	}

	@javax.annotation.Resource(name = "fieldInterfaceService")
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@GET
	@POST
	@Path("/getListData")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] getListData(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest)
			throws Exception {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);

		int start = 0;
		int pageSize = PageResult.DEFAULT_PAGE_SIZE;

		int pageno = dataRequest.getPage();
		pageSize = dataRequest.getPageSize();

		start = (pageno - 1) * pageSize;

		if (start < 0) {
			start = 0;
		}

		if (pageSize <= 0) {
			pageSize = PageResult.DEFAULT_PAGE_SIZE;
		}

		// String type = RequestUtils.getString(request, "type","list");
		String tableName = RequestUtils.getString(request, "tableName");

		JSONObject results = new JSONObject();
		try {
			Environment.setCurrentSystemName(databaseCode);
			// if("list".equalsIgnoreCase(type)){
			int total = cellUserAddService.getDataCountBySql("select count(id) from " + tableName);
			results.put("total", total);
			if (total > 0) {
				String sql = "select * from " + tableName + " where 1=1 order by id";
				List<Map<String, Object>> list = cellUserAddService.getDataListByQueryCriteria(sql, start, pageSize);
				results.put("rows", JSONConvertUtil.mapListToJSONArray(list));
				results.put("total", total);
				results.put("pageSize", pageSize);
				results.put("page", pageno);
			} else {
				JSONArray rows = new JSONArray();
				results.put("rows", rows);
				results.put("total", total);
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(systemName);
		}

		return results.toJSONString().getBytes("UTF-8");

	}

	/**
	 * 根据主表获取从表
	 * 
	 * @param cellDataTable
	 * @return
	 */
	public List<CellDataTable> getChildTableList(CellDataTable cellDataTable, boolean type) {
		// 获取从表
		List<CellDataTable> childTableList = new ArrayList<CellDataTable>();
		if (cellDataTable != null) {
			CellDataTableQuery query = new CellDataTableQuery();
			query.setIsSubTable("1");
			if (type) {
				query.setFileDotFileId(cellDataTable.getFileDotFileId());
			} else {
				query.setTopId(cellDataTable.getTopId());
			}

			childTableList = cellDataTableService.list(query);
		}
		return childTableList;
	}

	/**
	 * 获取表信息，并转换成JSONObject
	 * 
	 * @param cellDataTable cell_data_table对象
	 * @return
	 * @throws Exception
	 */
	public JSONObject getCellToJSONObject(CellDataTable cellDataTable) throws Exception {
		JSONObject jobject = extractCellDataTableToJSON(cellDataTable);
		// 获取表字段
		CellRepInfoQuery query = new CellRepInfoQuery();
		query.setFileDotFileId(cellDataTable.getFileDotFileId());
		query.setFrmType(cellDataTable.getId());
		query.setOrderBy("listno");
		List<CellRepInfo> list = cellRepInfoService.list(query);

		jobject.put("fields", JSONConvertUtil.toJSONArray(list));

		return jobject;
	}

	/**
	 * 获取表信息，并转换成JSONObject
	 * 
	 * @param cellDataTable cell_data_table对象
	 * @return
	 * @throws Exception
	 */
	public JSONObject getTableToJSONObject(CellDataTable cellDataTable) throws Exception {
		JSONObject jobject = extractCellDataTableToJSON(cellDataTable);
		// 获取表字段
		FieldInterfaceQuery query = new FieldInterfaceQuery();
		query.setFrmtype(cellDataTable.getId());
		query.setOrderBy("listno");
		List<FieldInterface> fields = fieldInterfaceService.list(query);
		jobject.put("fields", JSONConvertUtil.toJSONArray(fields));

		return jobject;
	}

	/**
	 * 将CellDataTable数据转成JSON
	 * 
	 * @param cellDataTable
	 * @return
	 */
	private JSONObject extractCellDataTableToJSON(CellDataTable cellDataTable) {
		JSONObject jobject = new JSONObject();
		jobject.put("tableId", cellDataTable.getId());
		jobject.put("tableName", cellDataTable.getTableName());
		jobject.put("name", cellDataTable.getName());
		jobject.put("addType", cellDataTable.getAddType());
		jobject.put("isSubTable", cellDataTable.getIsSubTable());
		jobject.put("fileDotFileId", cellDataTable.getFileDotFileId());
		jobject.put("topId", cellDataTable.getTopId());
		return jobject;
	}
}
