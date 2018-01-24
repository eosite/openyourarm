package com.glaf.isdp.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.query.RdataFieldQuery;
import com.glaf.isdp.query.RdataTableQuery;
import com.glaf.isdp.query.RinterfaceQuery;
import com.glaf.isdp.service.RdataFieldService;
import com.glaf.isdp.service.RdataTableService;
import com.glaf.isdp.service.RinterfaceService;
import com.glaf.isdp.util.ColumnTypeUtils;
import com.glaf.isdp.util.RdataTableDomainFactory;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/isdp/rdataTable")
@RequestMapping("/isdp/rdataTable")
public class RdataTableController {
	protected static final Log logger = LogFactory.getLog(RdataTableController.class);

	protected RdataTableService rdataTableService;

	@Autowired
	protected RdataFieldService rdataFieldService;

	@Autowired
	protected RinterfaceService rinterfaceService;

	public RdataTableController() {

	}

	@javax.annotation.Resource(name = "com.glaf.isdp.service.rdataTableService")
	public void setRdataTableService(RdataTableService rdataTableService) {
		this.rdataTableService = rdataTableService;
	}

	/**
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getColumnsByTableId")
	public byte[] getColumnsByTableId(HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		Environment.setCurrentSystemName(RequestUtils.getString(request, "systemName", currentSystemName));
		try {
			String tableid = RequestUtils.getString(request, "tableid");
			RdataTable rdataTable = null;
			if (StringUtils.isNotBlank(tableid)) {
				rdataTable = rdataTableService.getRdataTable(tableid);
				if (rdataTable != null) {

					RinterfaceQuery rquery = new RinterfaceQuery();
					rquery.setFrmtype(tableid);
					List<Rinterface> list = rinterfaceService.list(rquery);
					// fieldInterfaceService.getFieldsByFrmType(tableid);

					Map<String, Rinterface> tmp = new HashMap<String, Rinterface>();
					if (list != null && !list.isEmpty()) {
						for (Rinterface fi : list) {
							tmp.put(fi.getDname(), fi);
						}
					}
					JSONObject json;
					String fieldName;

					RdataFieldQuery query = new RdataFieldQuery();
					query.setTableid(tableid);
					List<RdataField> cdfs = rdataFieldService.list(query);
					if (cdfs != null && !cdfs.isEmpty()) {
						JSONArray jsons = new JSONArray();
						JSONObject result = new JSONObject();
						for (RdataField cdf : cdfs) {
							json = new JSONObject();
							fieldName = cdf.getFieldname();
							if (tmp.containsKey(fieldName)) {
								json.put("listId", tmp.get(fieldName).getListId());
								json.put("text", tmp.get(fieldName).getFname());
								json.put("strlen", tmp.get(fieldName).getStrlen());
								json.put("dType", tmp.get(fieldName).getDtype());

								json.put("fieldId", cdf.getId());
								json.put("fieldName", cdf.getFieldname());

								json.put("tableId", rdataTable.getId());
								json.put("tableName", rdataTable.getTablename());
								json.put("tableNameCN", rdataTable.getName());
								jsons.add(json);
							}
						}
						result.put("table", rdataTable.toJsonObject());
						result.put("columns", jsons);
						return result.toJSONString().getBytes("UTF-8");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return null;
	}
	/**
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getColumnsByTableName")
	public byte[] getColumnsByTableName(HttpServletRequest request) throws IOException {
		String currentSystemName = Environment.getCurrentSystemName();
		Environment.setCurrentSystemName(RequestUtils.getString(request, "systemName", currentSystemName));
		try {
			String tableName = RequestUtils.getString(request, "tableName");
			RdataTable rdataTable = null;
			if (StringUtils.isNotBlank(tableName)) {
				rdataTable = rdataTableService.getRdataTableByTableName(tableName);
				if (rdataTable != null) {

					RinterfaceQuery rquery = new RinterfaceQuery();
					rquery.setFrmtype(rdataTable.getId());
					List<Rinterface> list = rinterfaceService.list(rquery);
					// fieldInterfaceService.getFieldsByFrmType(tableid);

					Map<String, Rinterface> tmp = new HashMap<String, Rinterface>();
					if (list != null && !list.isEmpty()) {
						for (Rinterface fi : list) {
							tmp.put(fi.getDname(), fi);
						}
					}
					JSONObject json;
					String fieldName;

					RdataFieldQuery query = new RdataFieldQuery();
					query.setTableid(rdataTable.getId());
					List<RdataField> cdfs = rdataFieldService.list(query);
					if (cdfs != null && !cdfs.isEmpty()) {
						JSONArray jsons = new JSONArray();
						JSONObject result = new JSONObject();
						for (RdataField cdf : cdfs) {
							json = new JSONObject();
							fieldName = cdf.getFieldname();
							if (tmp.containsKey(fieldName)) {
								json.put("listId", tmp.get(fieldName).getListId());
								json.put("text", tmp.get(fieldName).getFname());
								json.put("strlen", tmp.get(fieldName).getStrlen());
								json.put("dType", tmp.get(fieldName).getDtype());

								json.put("fieldId", cdf.getId());
								json.put("fieldName", cdf.getFieldname());

								json.put("tableId", rdataTable.getId());
								json.put("tableName", rdataTable.getTablename());
								json.put("tableNameCN", rdataTable.getName());
								jsons.add(json);
							}
						}
						result.put("table", rdataTable.toJsonObject());
						result.put("columns", jsons);
						return result.toJSONString().getBytes("UTF-8");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return null;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		RdataTable rdataTable = new RdataTable();
		Tools.populate(rdataTable, params);

		rdataTable.setTablename(request.getParameter("tablename"));
		rdataTable.setName(request.getParameter("name"));
		rdataTable.setAddtype(RequestUtils.getInt(request, "addtype"));
		rdataTable.setMaxuser(RequestUtils.getInt(request, "maxuser"));
		rdataTable.setMaxsys(RequestUtils.getInt(request, "maxsys"));
		rdataTable.setUserid(request.getParameter("userid"));
		rdataTable.setCtime(RequestUtils.getDate(request, "ctime"));
		rdataTable.setContent(request.getParameter("content"));
		rdataTable.setSysnum(request.getParameter("sysnum"));
		rdataTable.setIssubtable(request.getParameter("issubtable"));
		rdataTable.setTopid(request.getParameter("topid"));
		rdataTable.setFiledotFileid(request.getParameter("filedotFileid"));
		rdataTable.setIndexId(RequestUtils.getInt(request, "indexId"));
		rdataTable.setWinWidth(RequestUtils.getInt(request, "winWidth"));
		rdataTable.setWinHeight(RequestUtils.getInt(request, "winHeight"));
		rdataTable.setIntQuote(RequestUtils.getInt(request, "intQuote"));
		rdataTable.setIntLineEdit(RequestUtils.getInt(request, "intLineEdit"));
		rdataTable.setPrintfileid(request.getParameter("printfileid"));
		rdataTable.setINTUSESTREEWBS(RequestUtils.getInt(request, "INTUSESTREEWBS"));
		rdataTable.setIntUseIf(RequestUtils.getInt(request, "intUseIf"));

		rdataTable.setUserid(actorId);
		rdataTableService.save(rdataTable);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveRdataTable")
	public byte[] saveRdataTable(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTable rdataTable = new RdataTable();
		try {
			Tools.populate(rdataTable, params);
			rdataTable.setTablename(request.getParameter("tablename"));
			rdataTable.setName(request.getParameter("name"));
			rdataTable.setAddtype(RequestUtils.getInt(request, "addtype"));
			rdataTable.setMaxuser(RequestUtils.getInt(request, "maxuser"));
			rdataTable.setMaxsys(RequestUtils.getInt(request, "maxsys"));
			rdataTable.setUserid(request.getParameter("userid"));
			rdataTable.setCtime(RequestUtils.getDate(request, "ctime"));
			rdataTable.setContent(request.getParameter("content"));
			rdataTable.setSysnum(request.getParameter("sysnum"));
			rdataTable.setIssubtable(request.getParameter("issubtable"));
			rdataTable.setTopid(request.getParameter("topid"));
			rdataTable.setFiledotFileid(request.getParameter("filedotFileid"));
			rdataTable.setIndexId(RequestUtils.getInt(request, "indexId"));
			rdataTable.setWinWidth(RequestUtils.getInt(request, "winWidth"));
			rdataTable.setWinHeight(RequestUtils.getInt(request, "winHeight"));
			rdataTable.setIntQuote(RequestUtils.getInt(request, "intQuote"));
			rdataTable.setIntLineEdit(RequestUtils.getInt(request, "intLineEdit"));
			rdataTable.setPrintfileid(request.getParameter("printfileid"));
			rdataTable.setINTUSESTREEWBS(RequestUtils.getInt(request, "INTUSESTREEWBS"));
			rdataTable.setIntUseIf(RequestUtils.getInt(request, "intUseIf"));
			// rdataTable.setCreateBy(actorId);
			this.rdataTableService.save(rdataTable);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/saveTableInfo")
	public byte[] saveTableInfo(HttpServletRequest request) throws Exception {
		String currentSystemName = Environment.getCurrentSystemName();
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
	//	Connection connection = DBConnectionFactory.getConnection(systemName);
	//	String dbtype = DBConnectionFactory.getDatabaseType(connection);
		try {
			RdataTable table = this.getSaveTable(request, loginContext); // 创建表
			if (table != null) {
				String columnsStr = RequestUtils.getString(request, "columns");
				Environment.setCurrentSystemName(systemName);
				String dbtype = DBConnectionFactory.getDatabaseType();
				if (StringUtils.isNotBlank(columnsStr)) {
					List<JSONObject> columns = JSONArray.parseArray(columnsStr, JSONObject.class);
					if (columns != null) {
						int fieldLen;
						String tableid = table.getId();
						JSONObject result = new JSONObject();
						String fieldType, name, fieldId, listId, referenced_field = "", referenced_table = "",
								userindex = "";
					//	LoginContext loginContext = RequestUtils.getLoginContext(request);
						for (JSONObject json : columns) {
							fieldType = json.getString("dType");
							fieldLen = json.getIntValue("strlen");
							name = json.getString("text");
							fieldId = json.getString("fieldId");
							listId = json.getString("listId");

							String columnType = ColumnTypeUtils.getColumnType(fieldType, fieldLen, dbtype);
							RdataField rdataField = null;
							Rinterface rinterface = null;
							if (StringUtils.isNotBlank(fieldId)) {
								rdataField = rdataFieldService.getRdataField(fieldId);
							}
							if (rdataField != null) { // 更新字段

								rdataField.setUserindex(userindex);
								rdataField.setTablename(referenced_table);
								rdataField.setFormula("");
								rdataField.setDname(referenced_field);

								rinterface = rinterfaceService.getRinterface(listId); // 默认这个是有值的
								if (rinterface != null) {
									rinterface.setFname(StringUtils.trim(name));
									rinterface.setDtype(fieldType);
									rinterface.setStrlen(fieldLen);
								}
								rdataTableService.modifyColumn(table.getTablename(), columnType, rdataField,
										rinterface);
							} else { // 新增字段
								int maxUser = rdataFieldService.getNextMaxUser(tableid);
								String fieldName = RConstant.getColumnName(table.getTablename(), maxUser);

								// table.getTablename() + "_user" + maxUser;

								rdataField = new RdataField();
								rdataField.setMaxuser(maxUser);
								rdataField.setTableid(tableid);
								rdataField.setCtime(new Date());
								rdataField.setFieldname(fieldName);
								rdataField.setUserindex(userindex);
								rdataField.setTablename(referenced_table);
								rdataField.setUserid(loginContext.getActorId());
								rdataField.setFormula("");
								rdataField.setDname(referenced_field);

								rinterface = new Rinterface();
								rinterface.setDatapoint(3);
								rinterface.setDname(fieldName);
								rinterface.setFname(StringUtils.trim(name));
								rinterface.setIssystem("1");
								rinterface.setDtype(fieldType);
								rinterface.setStrlen(fieldLen);
								rinterface.setFrmtype(table.getId());

								rdataTableService.addColumn(table.getTablename(), fieldName, columnType, rdataField,
										rinterface);

							}
							json.put("fieldId", rdataField.getId());
							if (rinterface != null) {
								json.put("listId", rinterface.getListId());
							}

							json.put("fieldName", rdataField.getFieldname());
							json.put("tableId", table.getId());
							json.put("tableName", table.getTablename());
							json.put("tableNameCN", table.getName());
						}
						result.put("table", table);
						result.put("columns", columns);
						return result.toJSONString().getBytes("UTF-8");
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	private RdataTable getSaveTable(HttpServletRequest request, LoginContext loginContext) {
	//	LoginContext loginContext = RequestUtils.getLoginContext(request);

		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);
			String name = RequestUtils.getString(request, "name");// 表中文描述
			Integer indexId = RequestUtils.getInteger(request, "indexId", 1);
			String tableid = RequestUtils.getString(request, "tableid");
			RdataTable rdataTable = null;
			if (StringUtils.isNotBlank(tableid)) {
				rdataTable = rdataTableService.getRdataTable(tableid);
			}

			if (rdataTable != null) {// 修改表
				rdataTable.setName(name);
				rdataTable.setIndexId(indexId);
				rdataTableService.save(rdataTable);
			} else {
				int maxUser = rdataTableService.getNextMaxUser();

				// 在r_data_table中插入一条记录
				rdataTable = new RdataTable();
				rdataTable.setUserid(loginContext.getActorId());
				rdataTable.setCtime(new Date());
				rdataTable.setMaxuser(maxUser);
				rdataTable.setName(name);
				rdataTable.setIssubtable("0");
				rdataTable.setTopid("");
				rdataTable.setIndexId(indexId);
				rdataTable.setAddtype(1);
				//获取创建表类型
				String tableType=RequestUtils.getString(request, "tableType");
				if(("imp").equals(tableType)){
					//创建接口表
					rdataTableService.saveImpTable(rdataTable);
				}else{
				    this.rdataTableService.save(rdataTable);
				}

			}

			return rdataTable;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return null;
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody RdataTable saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RdataTable rdataTable = new RdataTable();
		try {
			Tools.populate(rdataTable, model);
			rdataTable.setTablename(ParamUtils.getString(model, "tablename"));
			rdataTable.setName(ParamUtils.getString(model, "name"));
			rdataTable.setAddtype(ParamUtils.getInt(model, "addtype"));
			rdataTable.setMaxuser(ParamUtils.getInt(model, "maxuser"));
			rdataTable.setMaxsys(ParamUtils.getInt(model, "maxsys"));
			rdataTable.setUserid(ParamUtils.getString(model, "userid"));
			rdataTable.setCtime(ParamUtils.getDate(model, "ctime"));
			rdataTable.setContent(ParamUtils.getString(model, "content"));
			rdataTable.setSysnum(ParamUtils.getString(model, "sysnum"));
			rdataTable.setIssubtable(ParamUtils.getString(model, "issubtable"));
			rdataTable.setTopid(ParamUtils.getString(model, "topid"));
			rdataTable.setFiledotFileid(ParamUtils.getString(model, "filedotFileid"));
			rdataTable.setIndexId(ParamUtils.getInt(model, "indexId"));
			rdataTable.setWinWidth(ParamUtils.getInt(model, "winWidth"));
			rdataTable.setWinHeight(ParamUtils.getInt(model, "winHeight"));
			rdataTable.setIntQuote(ParamUtils.getInt(model, "intQuote"));
			rdataTable.setIntLineEdit(ParamUtils.getInt(model, "intLineEdit"));
			rdataTable.setPrintfileid(ParamUtils.getString(model, "printfileid"));
			rdataTable.setINTUSESTREEWBS(ParamUtils.getInt(model, "INTUSESTREEWBS"));
			rdataTable.setIntUseIf(ParamUtils.getInt(model, "intUseIf"));
			rdataTable.setUserid(actorId);
			this.rdataTableService.save(rdataTable);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return rdataTable;
	}

	/**
	 * 删除字段
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteColumn")
	public byte[] deleteColumn(@Context HttpServletRequest request) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			String tablename = RequestUtils.getString(request, "tablename");
			String FieldId = RequestUtils.getString(request, "FieldId");
			String listId = RequestUtils.getString(request, "listId");
			String dname = RequestUtils.getString(request, "dname");

			rdataTableService.deleteColumn(tablename, dname, listId, FieldId);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		RdataTable rdataTable = rdataTableService.getRdataTable(request.getParameter("id"));

		Tools.populate(rdataTable, params);

		rdataTable.setTablename(request.getParameter("tablename"));
		rdataTable.setName(request.getParameter("name"));
		rdataTable.setAddtype(RequestUtils.getInt(request, "addtype"));
		rdataTable.setMaxuser(RequestUtils.getInt(request, "maxuser"));
		rdataTable.setMaxsys(RequestUtils.getInt(request, "maxsys"));
		rdataTable.setUserid(request.getParameter("userid"));
		rdataTable.setCtime(RequestUtils.getDate(request, "ctime"));
		rdataTable.setContent(request.getParameter("content"));
		rdataTable.setSysnum(request.getParameter("sysnum"));
		rdataTable.setIssubtable(request.getParameter("issubtable"));
		rdataTable.setTopid(request.getParameter("topid"));
		rdataTable.setFiledotFileid(request.getParameter("filedotFileid"));
		rdataTable.setIndexId(RequestUtils.getInt(request, "indexId"));
		rdataTable.setWinWidth(RequestUtils.getInt(request, "winWidth"));
		rdataTable.setWinHeight(RequestUtils.getInt(request, "winHeight"));
		rdataTable.setIntQuote(RequestUtils.getInt(request, "intQuote"));
		rdataTable.setIntLineEdit(RequestUtils.getInt(request, "intLineEdit"));
		rdataTable.setPrintfileid(request.getParameter("printfileid"));
		rdataTable.setINTUSESTREEWBS(RequestUtils.getInt(request, "INTUSESTREEWBS"));
		rdataTable.setIntUseIf(RequestUtils.getInt(request, "intUseIf"));

		rdataTableService.save(rdataTable);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					RdataTable rdataTable = rdataTableService.getRdataTable(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (rdataTable != null && (StringUtils.equals(rdataTable.getUserid(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// rdataTable.setDeleteFlag(1);
						rdataTableService.save(rdataTable);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			RdataTable rdataTable = rdataTableService.getRdataTable(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (rdataTable != null && (StringUtils.equals(rdataTable.getUserid(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// rdataTable.setDeleteFlag(1);
				rdataTableService.save(rdataTable);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@ResponseBody
	@RequestMapping("/deleteTable")
	public byte[] deleteTable(HttpServletRequest request, ModelMap modelMap) {
		String currentSystemName = Environment.getCurrentSystemName();
		try {
			String systemName = RequestUtils.getString(request, "systemName", currentSystemName);
			Environment.setCurrentSystemName(systemName);

			LoginContext loginContext = RequestUtils.getLoginContext(request);

			String tableid = RequestUtils.getString(request, "id", "");

			if (!loginContext.isSystemAdministrator()) {
				throw new RuntimeException("非管理员不能删除表格! 表格id:" + tableid);
			}
			
			this.rdataTableService.deleteTable(tableid);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		} finally {
			Environment.setCurrentSystemName(currentSystemName);
		}
		return ResponseUtils.responseResult(true);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTable rdataTable = rdataTableService.getRdataTable(request.getParameter("id"));
		if (rdataTable != null) {
			request.setAttribute("rdataTable", rdataTable);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("rdataTable.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/isdp/rdataTable/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTable rdataTable = rdataTableService.getRdataTable(request.getParameter("id"));
		request.setAttribute("rdataTable", rdataTable);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("rdataTable.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/isdp/rdataTable/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("rdataTable.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/isdp/rdataTable/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTableQuery query = new RdataTableQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = rdataTableService.getRdataTableCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<RdataTable> list = rdataTableService.getRdataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (RdataTable rdataTable : list) {
					JSONObject rowJSON = rdataTable.toJsonObject();
					rowJSON.put("id", rdataTable.getId());
					rowJSON.put("rowId", rdataTable.getId());
					rowJSON.put("rdataTableId", rdataTable.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		RdataTableQuery query = new RdataTableQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		RdataTableDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = rdataTableService.getRdataTableCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<RdataTable> list = rdataTableService.getRdataTablesByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (RdataTable rdataTable : list) {
					JSONObject rowJSON = rdataTable.toJsonObject();
					rowJSON.put("id", rdataTable.getId());
					rowJSON.put("rdataTableId", rdataTable.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/rdataTable/list", modelMap);
	}

}
