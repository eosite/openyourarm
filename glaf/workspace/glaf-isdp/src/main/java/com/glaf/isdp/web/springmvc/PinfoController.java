package com.glaf.isdp.web.springmvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.base.modules.sys.query.FieldInterfaceQuery;
import com.glaf.base.modules.sys.service.IFieldInterfaceService;
import com.glaf.core.config.Environment;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.RequestUtils;
import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.domain.TreepName;
import com.glaf.isdp.query.CellUTableTreeQuery;
import com.glaf.isdp.service.CellUTableTreeService;
import com.glaf.isdp.service.TreepNameService;

@Controller("/isdp/wbs/pinfo")
@RequestMapping("/isdp/wbs/pinfo")
public class PinfoController {

	private CellUTableTreeService cellUTableTreeService;

	private TreepNameService treepNameService;

	private IFieldInterfaceService fieldInterfaceService;

	private ITablePageService tablePageService;

	@RequestMapping(params = "method=show")
	public ModelAndView show(HttpServletRequest request) {
		String systemName = Environment.getCurrentSystemName();
		this.handleData(request, systemName);

		Environment.setCurrentSystemName(systemName);
		return new ModelAndView("/isdp/wbs/pinfo");
	}

	@SuppressWarnings("unchecked")
	private void handleData(HttpServletRequest request, String systemName) {
		String databaseCode = RequestUtils.getString(request, "databaseCode", systemName);
		Environment.setCurrentSystemName(databaseCode);

		int domainIndexId = this.getDomainIndexId();
		int tableType = this.getTableType();

		TreepName treepName = treepNameService.getTreepNameByDomainIndexId(domainIndexId);
		request.setAttribute("treepName", treepName);

		List<CellUTableTree> cellUTableTreeList = this.getCellUTableTreeList(domainIndexId, tableType);
		request.setAttribute("cellUTableTreeList", cellUTableTreeList);

		List<Integer> listShowIndexIds = new ArrayList<Integer>();
		listShowIndexIds.add(-99);
		for (CellUTableTree model : cellUTableTreeList) {
			listShowIndexIds.add(model.getIndexId());
		}

		FieldInterfaceQuery interfaceQuery = new FieldInterfaceQuery();
		interfaceQuery.setFrmtype("pinfo");
		interfaceQuery.setListShowIndexIds(listShowIndexIds);
		List<FieldInterface> interfaceList = fieldInterfaceService.getInterfaceListByQuery(interfaceQuery);
		request.setAttribute("interfaceList", interfaceList);

		String querySql = "SELECT a.* FROM pinfo a,treepname b WHERE a.index_id=b.index_id and a.dtag=1 and b.domain_index="
				+ domainIndexId + " order by a.id asc";
		List<Object> pinfoList = tablePageService.getQueryList(querySql, 0, 2, null);
		Map<String, Object> pinfo = new HashMap<String, Object>();
		if (pinfoList != null && pinfoList.size() > 0) {
			pinfo = (Map<String, Object>) pinfoList.get(0);
		}
		request.setAttribute("pinfo", pinfo);
	}

	private int getTableType() {
		return 11;
	}

	private int getDomainIndexId() {
		return 2;
	}

	/**
	 * 获取工程项目信息TAB
	 * 
	 * @param domainIndexId 项目域ID
	 * @param tableType
	 * @return
	 */
	private List<CellUTableTree> getCellUTableTreeList(int domainIndexId, int tableType) {
		List<CellUTableTree> cellUTableTreeList = new ArrayList<CellUTableTree>();

		CellUTableTreeQuery query = new CellUTableTreeQuery();
		query.setTableType(tableType);
		query.setBusiessId("pinfo");
		query.setDomainIndex(domainIndexId);
		query.setSqlCondition(" and parent_id<>-1");
		query.setOrderBy("listno");
		cellUTableTreeList = cellUTableTreeService.list(query);

		if (cellUTableTreeList == null || cellUTableTreeList.size() == 0) {
			query.setSqlCondition(null);
			query.setDomainIndex(null);
			query.setIndexName("工程项目");
			cellUTableTreeList = cellUTableTreeService.list(query);
			cellUTableTreeList.get(0).setIndexName(cellUTableTreeList.get(0).getIndexName() + "著录");
		}
		return cellUTableTreeList;
	}

	@RequestMapping(params = "method=update")
	public ModelAndView update(HttpServletRequest request) {
		String systemName = Environment.getCurrentSystemName();
		this.handleData(request, systemName);

		Environment.setCurrentSystemName(systemName);
		return new ModelAndView("/isdp/wbs/pinfo_update");
	}

	@Resource(name = "com.glaf.isdp.service.cellUTableTreeService")
	public void setCellUTableTreeService(CellUTableTreeService cellUTableTreeService) {
		this.cellUTableTreeService = cellUTableTreeService;
	}

	@Resource(name = "com.glaf.isdp.service.treepNameService")
	public void setTreepNameService(TreepNameService treepNameService) {
		this.treepNameService = treepNameService;
	}

	@Resource(name = "fieldInterfaceService")
	public void setFieldInterfaceService(IFieldInterfaceService fieldInterfaceService) {
		this.fieldInterfaceService = fieldInterfaceService;
	}

	@Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

}
