package com.glaf.isdp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.QueryUtils;
import com.glaf.datamgr.domain.SynchronizationRule;
import com.glaf.datamgr.service.SynchronizationRuleService;
import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.service.DotUseService;
import com.glaf.isdp.service.TreepInfoService;

public class TableDataBean {
	protected static final Log logger = LogFactory.getLog(TableDataBean.class);

	protected DotUseService dotUseService;

	protected IDatabaseService databaseService;

	protected TreepInfoService treepInfoService;

	protected SynchronizationRuleService synchronizationRuleService;

	public TableDataBean() {

	}

	public IDatabaseService getDatabaseService() {
		if (databaseService == null) {
			databaseService = ContextFactory.getBean("databaseService");
		}
		return databaseService;
	}

	public DotUseService getDotUseService() {
		if (dotUseService == null) {
			dotUseService = ContextFactory.getBean("com.glaf.isdp.service.dotUseService");
		}
		return dotUseService;
	}

	public JSONObject getFillForm(Map<String, Object> params) {
		JSONObject responseJSON = new JSONObject();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		String currSystemName = Environment.getCurrentSystemName();
		try {
			Database database = getDatabaseService().getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}
			String index_id = ParamUtils.getString(params, "index_id");
			if (StringUtils.isNotEmpty(index_id) && StringUtils.isNumeric(index_id)) {
				TreepInfo treepInfo = getTreepInfoService().getTreepInfo(Integer.parseInt(index_id));
				// String sql = " select * from cell_fillform where index_id = "
				// + index_id;
				// List<Map<String, Object>> dataList =
				// tablePageService.getListData(sql, params);
				List<Map<String, Object>> dataList = getDotUseService().getDotUseCellFillFormByTreepinfoId(0, 2000,
						treepInfo.getId());

				if (dataList != null && !dataList.isEmpty()) {
					responseJSON.put("total", dataList.size());
					JSONArray array = new JSONArray();
					int startIndex = 0;
					Map<String, Object> rowMap = null;
					for (Map<String, Object> dataMap : dataList) {
						rowMap = QueryUtils.lowerKeyMap(dataMap);
						JSONObject jsonObject = JsonUtils.toJSONObject(rowMap);
						jsonObject.put("startIndex", startIndex);
						String fileid = Base64.encodeBase64String(rowMap.get("fileid").toString().getBytes("UTF-8"));
						jsonObject.put("fileid", fileid);
						array.add(jsonObject);
					}
					responseJSON.put("rows", array);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return responseJSON;
	}

	public SynchronizationRuleService getSynchronizationRuleService() {
		if (synchronizationRuleService == null) {
			synchronizationRuleService = ContextFactory.getBean("synchronizationRuleService");
		}
		return synchronizationRuleService;
	}

	public JSONArray getTreeJson(Map<String, Object> params) {
		JSONArray result = new JSONArray();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		String ruleId = ParamUtils.getString(params, "ruleId");
		if (StringUtils.isNotEmpty(ruleId)) {
			String currSystemName = Environment.getCurrentSystemName();
			try {
				Database database = getDatabaseService().getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
				logger.debug("currentSystemName:" + Environment.getCurrentSystemName());
				SynchronizationRule rule = getSynchronizationRuleService().getSynchronizationRule(ruleId);
				logger.debug("rule:" + rule);
				if (rule != null && StringUtils.isNotEmpty(rule.getSouceTable())) {
					logger.debug(rule.getSouceTable());
					List<String> tables = rule.getSouceTables();
					// List<String> tables2 = new ArrayList<String>();
					// QueryHelper helper = new QueryHelper();
					Iterator<String> iterator = tables.iterator();
					while (iterator.hasNext()) {
						// String tablename = iterator.next();
						// if (DBUtils.tableExists(tablename)) {
						// SqlExecutor sqlExecutor = new SqlExecutor();
						// sqlExecutor.setSql(" select count(*) from " +
						// tablename);
						// sqlExecutor.setParameter(params);
						// int total = helper.getTotal(sqlExecutor);
						// if (total > 0) {
						// tables2.add(tablename);
						// }
						// }
					}

					if (tables.size() > 0) {
						StringBuilder buffer = new StringBuilder();
						buffer.append(" select * from cell_data_table where issubtable='1' and tablename in ( ");
						iterator = tables.iterator();
						while (iterator.hasNext()) {
							String tablename = iterator.next();
							buffer.append("'").append(tablename).append("'");
							if (iterator.hasNext()) {
								buffer.append(", ");
							}
						}
						buffer.append(" ) ");
						QueryHelper helper = new QueryHelper();
						List<Map<String, Object>> tableList = helper.getResultList(Environment.getCurrentSystemName(),
								buffer.toString(), params);

						if (tableList != null && !tableList.isEmpty()) {
							List<TreeComponent> trees = new ArrayList<TreeComponent>();
							for (Map<String, Object> dataMap : tableList) {
								TreeComponent tree = new TreeComponent();
								tree.setTitle(ParamUtils.getString(dataMap, "name"));
								tree.setId(ParamUtils.getString(dataMap, "tablename"));
								trees.add(tree);
							}
							TreeHelper treeHelper = new TreeHelper();
							result = treeHelper.getJSONArray(trees, 0);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currSystemName);
			}
		}
		return result;
	}

	public JSONArray getTreepinfoJson(long databaseId, int index_id) {
		JSONArray result = new JSONArray();
		Map<String, Object> params = new HashMap<String, Object>();
		String currSystemName = Environment.getCurrentSystemName();
		try {
			Database database = getDatabaseService().getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			// int total = tablePageService.getQueryCount("select
			// count(index_id) from treepinfo ", params);

			StringBuilder buffer = new StringBuilder();
			buffer.append(" select a.id, a.index_id, a.parent_id, a.index_name, a.nlevel, a.listno, c.childrenCount ");
			buffer.append(" FROM treepinfo a ");
			buffer.append(" LEFT JOIN ");
			buffer.append("   ( SELECT parent_id, ");
			buffer.append("            COUNT(index_id) AS childrenCount ");
			buffer.append("    FROM treepinfo ");
			buffer.append("    WHERE nodetype='0' ");
			buffer.append("      AND intisuse=1 ");
			buffer.append("    GROUP BY parent_id ) c ON a.index_id=c.parent_id ");
			buffer.append(" WHERE a.nodeType='0' ");
			buffer.append(" AND a.intIsUse=1 ");

			if (index_id > 0) {
				buffer.append(" AND a.parent_id = ").append(index_id);
			} else {
				buffer.append(" AND a.parent_id = -1 or a.parent_id = 0 ");
			}

			buffer.append(" ORDER BY a.parent_id, a.listNo  ");
			logger.debug(buffer.toString());
			QueryHelper helper = new QueryHelper();
			List<Map<String, Object>> resultList = helper.getResultList(Environment.getCurrentSystemName(),
					buffer.toString(), params);
			if (resultList != null && !resultList.isEmpty()) {
				logger.debug("tableList size:" + resultList.size());
				Map<String, Object> rowMap = null;
				for (Map<String, Object> dataMap : resultList) {
					rowMap = QueryUtils.lowerKeyMap(dataMap);
					JSONObject json = new JSONObject();
					json.put("indexId", ParamUtils.getString(rowMap, "index_id"));
					json.put("name", ParamUtils.getString(rowMap, "index_name"));
					json.put("parentId", ParamUtils.getString(rowMap, "parent_id"));
					json.put("level", ParamUtils.getInt(rowMap, "nlevel"));
					int childrenCount = ParamUtils.getInt(rowMap, "childrencount");
					if (childrenCount > 0) {
						json.put("isParent", true);
					} else {
						json.put("isParent", false);
					}
					result.add(json);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return result;
	}

	public JSONArray getTreepinfoJson(Map<String, Object> params) {
		JSONArray result = new JSONArray();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		long index_id = ParamUtils.getLong(params, "indexId");
		String currSystemName = Environment.getCurrentSystemName();
		try {
			Database database = getDatabaseService().getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			// int total = tablePageService.getQueryCount("select
			// count(index_id) from treepinfo ", params);

			StringBuilder buffer = new StringBuilder();
			buffer.append(" select a.id, a.index_id, a.parent_id, a.index_name, a.nlevel, a.listno, c.childrenCount ");
			buffer.append(" FROM treepinfo a ");
			buffer.append(" LEFT JOIN ");
			buffer.append("   ( SELECT parent_id, ");
			buffer.append("            COUNT(index_id) AS childrenCount ");
			buffer.append("    FROM treepinfo ");
			buffer.append("    WHERE nodetype='0' ");
			buffer.append("      AND intisuse=1 ");
			buffer.append("    GROUP BY parent_id ) c ON a.index_id=c.parent_id ");
			buffer.append(" WHERE a.nodeType='0' ");
			buffer.append("   AND a.intIsUse=1 ");

			if (index_id >= 0) {
				buffer.append(" AND a.parent_id = ").append(index_id);
			} else {
				buffer.append(" AND a.parent_id = -10000 ");
			}

			buffer.append(" ORDER BY a.parent_id, a.listNo  ");
			logger.debug(buffer.toString());
			QueryHelper helper = new QueryHelper();
			List<Map<String, Object>> resultList = helper.getResultList(Environment.getCurrentSystemName(),
					buffer.toString(), params);
			if (resultList != null && !resultList.isEmpty()) {
				logger.debug("tableList size:" + resultList.size());
				Map<String, Object> rowMap = null;
				for (Map<String, Object> dataMap : resultList) {
					rowMap = QueryUtils.lowerKeyMap(dataMap);
					JSONObject json = new JSONObject();
					json.put("indexId", ParamUtils.getString(rowMap, "index_id"));
					json.put("name", ParamUtils.getString(rowMap, "index_name"));
					json.put("parentId", ParamUtils.getString(rowMap, "parent_id"));
					json.put("level", ParamUtils.getInt(rowMap, "nlevel"));
					int childrenCount = ParamUtils.getInt(rowMap, "childrencount");
					if (childrenCount > 0) {
						json.put("isParent", true);
					} else {
						json.put("isParent", false);
					}
					result.add(json);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return result;
	}

	public TreepInfoService getTreepInfoService() {
		if (treepInfoService == null) {
			treepInfoService = ContextFactory.getBean("com.glaf.isdp.service.treepInfoService");
		}
		return treepInfoService;
	}

	public JSONObject getWbsTables(Map<String, Object> params) {
		JSONObject responseJSON = new JSONObject();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		String currSystemName = Environment.getCurrentSystemName();
		try {
			Database database = getDatabaseService().getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}
			String index_id = ParamUtils.getString(params, "index_id");
			String usernmu = ParamUtils.getString(params, "usernmu");
			if (StringUtils.isNotEmpty(index_id) && StringUtils.isNumeric(index_id)) {
				// issubtable='0' and
				String sql = " select * from cell_data_table where filedot_fileid in ( select filedot_fileid from cell_fillform where index_id = "
						+ index_id + " ) ";
				if (StringUtils.isNotEmpty(usernmu)) {
					params.put("usernmuLike", "%" + usernmu + "%");
					StringBuilder buffer = new StringBuilder();
					buffer.append(" select * from cell_data_table where filedot_fileid in (");
					buffer.append(
							"   select f.filedot_fileid from cell_fillform f inner join treepinfo t on f.index_id = t.index_id  ");
					buffer.append("   where f.index_id = ").append(index_id);
					buffer.append("   and t.usernmu like #{usernmuLike} ");
					buffer.append(" ) ");
					sql = buffer.toString();
				}
				QueryHelper helper = new QueryHelper();
				List<Map<String, Object>> dataList = helper.getResultList(Environment.getCurrentSystemName(), sql,
						params);
				if (dataList != null && !dataList.isEmpty()) {
					responseJSON.put("total", dataList.size());
					JSONArray array = new JSONArray();
					int startIndex = 0;
					Map<String, Object> rowMap = null;
					for (Map<String, Object> dataMap : dataList) {
						rowMap = QueryUtils.lowerKeyMap(dataMap);
						JSONObject jsonObject = JsonUtils.toJSONObject(rowMap);
						jsonObject.put("startIndex", startIndex);
						jsonObject.put("id", dataMap.get("tablename"));
						array.add(jsonObject);
					}
					responseJSON.put("rows", array);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return responseJSON;
	}

	public JSONArray getWbsTreeJson(Map<String, Object> params) {
		JSONArray result = new JSONArray();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		long index_id = ParamUtils.getLong(params, "id");
		String currSystemName = Environment.getCurrentSystemName();
		try {
			Database database = getDatabaseService().getDatabaseById(databaseId);
			if (database != null) {
				Environment.setCurrentSystemName(database.getName());
			}

			// int total = tablePageService.getQueryCount("select
			// count(index_id) from treepinfo ", params);

			StringBuilder buffer = new StringBuilder();
			buffer.append(" select a.id, a.index_id, a.parent_id, a.index_name, a.nlevel, a.listno, c.childrenCount ");
			buffer.append(" FROM treepinfo a ");
			buffer.append(" LEFT JOIN ");
			buffer.append("   ( SELECT parent_id, ");
			buffer.append("            COUNT(index_id) AS childrenCount ");
			buffer.append("    FROM treepinfo ");
			buffer.append("    WHERE nodetype='0' ");
			buffer.append("      AND intisuse=1 ");
			buffer.append("    GROUP BY parent_id ) c ON a.index_id=c.parent_id ");
			buffer.append(" WHERE a.nodeType='0' ");
			buffer.append("   AND a.intIsUse=1 ");

			if (index_id != 0) {
				buffer.append(" AND a.parent_id = ").append(index_id);
			} else {
				buffer.append(" AND a.parent_id = -10000 ");
			}

			buffer.append(" ORDER BY a.parent_id, a.listNo  ");

			QueryHelper helper = new QueryHelper();
			List<Map<String, Object>> resultList = helper.getResultList(Environment.getCurrentSystemName(),
					buffer.toString(), params);
			if (resultList != null && !resultList.isEmpty()) {
				logger.debug("tableList size:" + resultList.size());
				Map<String, Object> rowMap = null;
				List<TreeComponent> treeList = new ArrayList<TreeComponent>();
				Map<String, Boolean> parentMap = new HashMap<String, Boolean>();
				Map<String, TreeComponent> treeMap = new HashMap<String, TreeComponent>();
				for (Map<String, Object> dataMap : resultList) {
					rowMap = QueryUtils.lowerKeyMap(dataMap);
					TreeComponent tree = new TreeComponent();
					tree.setId(ParamUtils.getString(rowMap, "index_id"));
					tree.setTitle(ParamUtils.getString(rowMap, "index_name"));
					String pId = ParamUtils.getString(rowMap, "parent_id");
					if (pId != null && pId.trim().length() > 0) {
						TreeComponent parent = treeMap.get(pId);
						tree.setParent(parent);
						tree.setParentId(pId);
					}
					treeList.add(tree);
					treeMap.put(tree.getId(), tree);
					int childrenCount = ParamUtils.getInt(rowMap, "childrencount");
					if (childrenCount > 0) {
						parentMap.put(tree.getId(), true);
					}
				}

				List<TreeComponent> trees = new ArrayList<TreeComponent>();
				for (TreeComponent tree : treeList) {
					String pId = tree.getParentId();
					if (pId != null && pId.trim().length() > 0) {
						TreeComponent parent = treeMap.get(pId);
						tree.setParent(parent);
					}
					tree.setCls("folder");
					trees.add(tree);
				}

				logger.debug("trees->" + treeList.size());
				TreeHelper helper2 = new TreeHelper();
				result = helper2.getJSONArray(trees, 0);
				for (int i = 0; i < result.size(); i++) {
					JSONObject child = result.getJSONObject(i);
					if (parentMap.get(child.getString("id")) != null) {
						child.put("isParent", true);
					}
				}
				logger.debug("result:" + result.size());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			Environment.setCurrentSystemName(currSystemName);
		}
		return result;
	}

	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	public void setDotUseService(DotUseService dotUseService) {
		this.dotUseService = dotUseService;
	}

	public void setSynchronizationRuleService(SynchronizationRuleService synchronizationRuleService) {
		this.synchronizationRuleService = synchronizationRuleService;
	}

	public void setTreepInfoService(TreepInfoService treepInfoService) {
		this.treepInfoService = treepInfoService;
	}

}
