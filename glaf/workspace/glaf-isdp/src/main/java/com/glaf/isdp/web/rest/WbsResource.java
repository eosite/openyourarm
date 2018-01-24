package com.glaf.isdp.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.isdp.domain.CellFillForm;
import com.glaf.isdp.domain.ProjCellAndFiles;
import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.query.CellFillFormQuery;
import com.glaf.isdp.query.ProjCellAndFilesQuery;
import com.glaf.isdp.service.CellFillFormService;
import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.ProjCellAndFilesService;
import com.glaf.isdp.service.ProjMuiProjListService;
import com.glaf.isdp.service.TableActionService;
import com.glaf.isdp.service.TreepInfoService;
import com.glaf.isdp.util.JSONConvertUtil;

@Controller
@Path("/rs/isdp/wbs")
public class WbsResource {

	@Resource(name = "com.glaf.isdp.service.treepInfoService")
	protected TreepInfoService treepInfoService;

	@Resource(name = "com.glaf.isdp.service.tableActionService")
	protected TableActionService tableActionService;

	@Resource(name = "com.glaf.isdp.service.projMuiProjListService")
	protected ProjMuiProjListService projMuiProjListService;

	@Resource(name = "com.glaf.isdp.service.projCellAndFilesService")
	protected ProjCellAndFilesService projCellAndFilesService;

	@Resource(name = "com.glaf.isdp.service.cellFillFormService")
	protected CellFillFormService cellFillFormService;

	private DotUseService dotUseService;

	@GET
	@POST
	@RequestMapping("/cellTableList")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] cellTableList(@Context HttpServletRequest request)
			throws Exception {
		int indexId = RequestUtils.getInteger(request, "indexId", 0);
		String currentSystemName = Environment.getCurrentSystemName();

		JSONObject result = new JSONObject();
		try {
			String systemName = RequestUtils.getString(request, "systemName",
					currentSystemName);
			int page = RequestUtils.getInt(request, "page", 1);
			int limit = RequestUtils.getInt(request, "rows",
					PageResult.DEFAULT_PAGE_SIZE);
			boolean showSubTable = RequestUtils.getBoolean(request,
					"showSubTable");

			int start = 0;

			start = (page - 1) * limit;

			if (start < 0) {
				start = 0;
			}

			if (limit <= 0) {
				limit = PageResult.DEFAULT_PAGE_SIZE;
			}

			Environment.setCurrentSystemName(systemName);

			String readURL = request.getContextPath()
					+ "/isdp/websiteWBS/cellweb?systemName=" + systemName;
			String downloadURL = request.getContextPath()
					+ "/mx/isdp/wbs/cellweb?type=1&databaseCode=" + systemName;

			if (showSubTable) {
				TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);
				int total = dotUseService
						.getDotUseCellFillFormCountByTreepinfoId(treepInfo
								.getId());
				result.put("total", total);

				if (total > 0) {
					JSONArray rows = new JSONArray();
					List<Map<String, Object>> list = dotUseService
							.getDotUseCellFillFormByTreepinfoId(start, limit,
									treepInfo.getId());
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);

						JSONObject row = new JSONObject();
						row.put("id", map.get("ID").toString());
						row.put("name", map.get("name").toString());

						String fileID = Base64.encodeBase64String(map
								.get("fileID").toString().getBytes("UTF-8"));
						row.put("fileID", fileID);
						row.put("readURL", readURL + "&fileID=" + fileID);
						row.put("downloadURL", downloadURL + "&fileID="
								+ fileID);

						rows.add(row);
					}

					result.put("rows", rows);
				} else {
					JSONArray rows = new JSONArray();
					result.put("rows", rows);
				}
			} else {
				int total = dotUseService
						.getDotUseCellFillFormCountByIndexId(indexId);
				result.put("total", total);

				if (total > 0) {
					JSONArray rows = new JSONArray();
					List<Map<String, Object>> list = dotUseService
							.getDotUseCellFillFormByIndexId(start, limit,
									indexId);
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);

						JSONObject row = new JSONObject();
						row.put("id", map.get("ID").toString());
						row.put("name", map.get("name").toString());

						String fileID = Base64.encodeBase64String(map
								.get("fileID").toString().getBytes("UTF-8"));
						row.put("fileID", fileID);
						row.put("readURL", readURL + "&fileID=" + fileID);
						row.put("downloadURL", downloadURL + "&fileID="
								+ fileID);

						rows.add(row);
					}

					result.put("rows", rows);
				} else {
					JSONArray rows = new JSONArray();
					result.put("rows", rows);
				}
			}
		} catch (Exception e) {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 表格检查
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/tableCheck")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] tableCheck(@Context HttpServletRequest request)
			throws Exception {
		int indexId = RequestUtils.getInt(request, "indexId", 0);
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		JSONObject results = new JSONObject();

		ProjCellAndFilesQuery query = new ProjCellAndFilesQuery();
		query.setIndexId(indexId);
		int total = projCellAndFilesService
				.getProjCellAndFilesCountByQueryCriteria(query);
		results.put("total", total);

		List<ProjCellAndFiles> list = projCellAndFilesService
				.getProjCellList(indexId);
		results.put("rows", JSONConvertUtil.toJSONArray(list));

		Environment.setCurrentSystemName(systemName);
		return results.toString().getBytes("UTF-8");
	}

	/**
	 * 文件检查
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/fileCheck")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] fileCheck(@Context HttpServletRequest request)
			throws Exception {
		int indexId = RequestUtils.getInt(request, "indexId", 0);
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		JSONObject results = new JSONObject();

		ProjCellAndFilesQuery query = new ProjCellAndFilesQuery();
		query.setIntType(1);
		query.setIndexId(indexId);

		int total = projCellAndFilesService
				.getProjCellAndFilesCountByQueryCriteria(query);
		results.put("total", total);

		List<ProjCellAndFiles> list = projCellAndFilesService.list(query);
		results.put("rows", JSONConvertUtil.toJSONArray(list));

		Environment.setCurrentSystemName(systemName);
		return results.toString().getBytes("UTF-8");
	}

	/**
	 * 检查表格是否有多张
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/checkTableNum")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public void checkTableNum(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		int indexId = RequestUtils.getInt(request, "indexId", 0);
		String fileDotFileId = RequestUtils.getString(request, "fileDotFileId",
				"");
		String name = RequestUtils.getString(request, "name", "");
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		CellFillFormQuery query = new CellFillFormQuery();
		query.setIndexId(indexId);
		query.setFileDotFileId(fileDotFileId);
		query.setOrderBy("id");
		int total = cellFillFormService
				.getCellFillFormCountByQueryCriteria(query);

		JSONObject jobject = new JSONObject();
		jobject.put("total", total);
		if (total > 1) {
			JSONArray jArray = new JSONArray();
			List<CellFillForm> cellFillFormList = cellFillFormService
					.list(query);
			int i = 1;
			for (CellFillForm model : cellFillFormList) {
				JSONObject obj = new JSONObject();
				obj.put("useId", RequestUtils.encodeString(model.getId()));
				obj.put("name", name + "(第" + (i++) + "页)");
				jArray.add(obj);
			}
			jobject.put("cells", jArray);
		}
		Environment.setCurrentSystemName(systemName);

		response.reset();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(jobject.toJSONString());
		writer.flush();
	}

	/**
	 * 检测项目
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GET
	@POST
	@Path("/checkItem")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] checkItem(@Context HttpServletRequest request)
			throws Exception {
		// int indexId = RequestUtils.getInt(request, "indexId", 0);

		JSONObject results = new JSONObject();

		// results.put("total", total);

		// results.put("rows", JSONConvertUtil.toJSONArray(list));

		return results.toString().getBytes("UTF-8");
	}

	/**
	 * 取消计划
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/cancelProject")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] cancelProject(@Context HttpServletRequest request)
			throws IOException {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		int indexId = RequestUtils.getInt(request, "indexId");
		TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);
		String tableName = "";
		String fieldString = "";
		String whereCondition = "";
		if (treepInfo != null) {
			String getIndexIdSQL = "select index_id from treepInfo where id like '"
					+ treepInfo.getId() + "%'";
			String getFileDotFileIdSQL = "select filedot_fileid from mycell_tasks_sub where index_id in(select wbsindex from treepInfo  where index_id in("
					+ getIndexIdSQL + ")";
			String getCellMyTaskMainIdSQL = "select id from cell_mytaskmain where index_id in("
					+ getIndexIdSQL + ")";
			String getProcessIdSQL = "select id from flow_process where main_id in("
					+ getCellMyTaskMainIdSQL + " and fromtasksid='project')";

			// 删除设计值表
			tableName = "dotuse";
			whereCondition = " and topid in(select id from cell_fillform where index_id in("
					+ getIndexIdSQL
					+ ") and  filedot_fileid not in("
					+ getFileDotFileIdSQL + ") and intistasks=101))";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除设计值表填写的表格
			tableName = "cell_fillform";
			whereCondition = " and index_id in(" + getIndexIdSQL
					+ ") and  filedot_fileid not in(" + getFileDotFileIdSQL
					+ ") and intistasks=101)";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 更新节点所挂表格
			tableName = "proj_cellandfiles";
			fieldString = " intPage1=0,intPage2=intPage0,intfinish=0,useId='' ";
			whereCondition = " and index_id in(" + getIndexIdSQL + ")";
			tableActionService.updateTableByWhereCause(tableName, fieldString,
					whereCondition);
			// 删除邮件
			tableName = "email_send_flowfinish";
			whereCondition = " and activ_id in(select id from flow_activ where process_id in(select id from flow_process where Main_id in("
					+ getCellMyTaskMainIdSQL + ")))";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除执行流程
			tableName = "flow_activ";
			whereCondition = " and process_id in(" + getProcessIdSQL + ")";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除流程跳转定义
			tableName = "flow_forward";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除流程状态
			tableName = "flow_station";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除工作流
			tableName = "flow_process";
			whereCondition = " and id in(" + getProcessIdSQL + ")";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除任务包
			tableName = "cell_mytaskmain";
			whereCondition = " and index_id in(" + getIndexIdSQL
					+ ") and fromtasksid='project'";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除任务
			tableName = "cell_mytask";
			whereCondition = " and index_id in(" + getIndexIdSQL + ")";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除报审包
			tableName = "proj_Inspection_addworkbag";
			whereCondition = " and index_id in(" + getIndexIdSQL + ")";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除报审表格
			tableName = "proj_Inspection_form";
			whereCondition = " and proj_Inspection_id in(select id from proj_Inspection where index_id in("
					+ getIndexIdSQL + "))";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除报审
			tableName = "proj_Inspection";
			whereCondition = " and index_id in(" + getIndexIdSQL + ");";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除附件
			tableName = "fileatt";
			whereCondition = " and topid in(select id from pfile where pid in("
					+ getIndexIdSQL + "))";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 删除文件
			tableName = "pfile";
			whereCondition = " and pid in(" + getIndexIdSQL + ")";
			tableActionService.deleteTableByWhereCause(tableName,
					whereCondition);
			// 更新节点状态
			tableName = "treepInfo";
			fieldString = " finishint=-1,cell3=cell1,cell2=0,intcellfinish=0 ";
			whereCondition = " and id like '" + treepInfo.getId() + "'";
			tableActionService.updateTableByWhereCause(tableName, fieldString,
					whereCondition);

			Environment.setCurrentSystemName(systemName);
			return ResponseUtils.responseJsonResult(false);
		}
		Environment.setCurrentSystemName(systemName);

		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 恢复计划
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/recoverProject")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] recoverProject(@Context HttpServletRequest request)
			throws IOException {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		int indexId = RequestUtils.getInt(request, "indexId");
		TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);

		if (treepInfo != null) {
			String tableName = "treepInfo";
			String fieldString = " finishint=0,strButtonStar='启动...' ";
			String whereCondition = " and id like '" + treepInfo.getId()
					+ "%' ";
			tableActionService.updateTableByWhereCause(tableName, fieldString,
					whereCondition);

			fieldString = " strButtonStar='自动' ";
			whereCondition = " and id like '"
					+ treepInfo.getId()
					+ "%' and wbsindex in(select intusedomain from flow_activ_d where process_id in(select id from flow_process_d where main_id in(select id from s_treewbs_autostart where inttype=0)) and typeofact=0) ";
			tableActionService.updateTableByWhereCause(tableName, fieldString,
					whereCondition);

			Environment.setCurrentSystemName(systemName);
			return ResponseUtils.responseJsonResult(true);
		}
		Environment.setCurrentSystemName(systemName);

		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 启动计划
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/startProject")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] startProject(@Context HttpServletRequest request)
			throws IOException {
		String systemName = Environment.getCurrentSystemName();

		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		int indexId = RequestUtils.getInt(request, "indexId");
		TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);
		if (treepInfo.getFinishInt() == -2) {
			// 终止后启动
			treepInfo.setFinishInt(1);
			treepInfo.setBdates(new Date());
			treepInfo.setStrButtonStar("终止...");
			treepInfoService.save(treepInfo);

			Environment.setCurrentSystemName(systemName);
			return ResponseUtils.responseJsonResult(true);
		} else {
			// 启动计划流程复杂，暂不做处理
			// ProjMuiProjList projMuiProjList =
			// projMuiProjListService.getLocalProjMuiProjList(1);
			Environment.setCurrentSystemName(systemName);
		}

		Environment.setCurrentSystemName(systemName);
		return ResponseUtils.responseJsonResult(false);
	}

	/**
	 * 终止计划
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/stopProject")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] stopProject(@Context HttpServletRequest request)
			throws IOException {
		String systemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode",
				systemName);
		Environment.setCurrentSystemName(databaseCode);

		int indexId = RequestUtils.getInt(request, "indexId");

		TreepInfo treepInfo = treepInfoService.getTreepInfo(indexId);
		if (treepInfo != null) {
			treepInfo.setFinishInt(-2);
			treepInfo.setBdates(new Date());
			treepInfo.setStrButtonStar("启动...");
			treepInfoService.save(treepInfo);

			Environment.setCurrentSystemName(systemName);
			return ResponseUtils.responseJsonResult(false);
		}

		Environment.setCurrentSystemName(systemName);
		return ResponseUtils.responseJsonResult(false);
	}

	public void setTreepInfoService(TreepInfoService treepInfoService) {
		this.treepInfoService = treepInfoService;
	}

	public void setTableActionService(TableActionService tableActionService) {
		this.tableActionService = tableActionService;
	}

	public void setProjMuiProjListService(
			ProjMuiProjListService projMuiProjListService) {
		this.projMuiProjListService = projMuiProjListService;
	}

	public void setProjCellAndFilesService(
			ProjCellAndFilesService projCellAndFilesService) {
		this.projCellAndFilesService = projCellAndFilesService;
	}

	@Resource(name = "com.glaf.isdp.service.dotUseService")
	public void setDotUseService(DotUseService dotUseService) {
		this.dotUseService = dotUseService;
	}

}
