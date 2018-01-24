package com.glaf.cell.web.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.glaf.cell.domain.FileDotExt;
import com.glaf.cell.service.CellConvertTaskService;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.cell.service.CellConvertService;
import com.glaf.form.cell.service.DataSetConvertService;
import com.glaf.form.cell.service.FormulaConvertService;
import com.glaf.form.cell.service.PageConvertService;

@RestController
public class ConvertRestResource {
	@Autowired
	protected CellConvertTaskService cellConvertTaskService;
	@Autowired
	protected FormulaConvertService formulaConvertService;

	@RequestMapping(value = "/cell/convert")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] convert(@Context HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject jsonObj = new JSONObject();
		int intusedomain=RequestUtils.getInt(request,"usedomain", 0);
		int needConvertCells = cellConvertTaskService.getCellTemplateCount(intusedomain, new Date(99, 1, 1));
		int pageTotal = needConvertCells % 10 > 0 ? needConvertCells / 10 + 1 : needConvertCells / 10;
		List<FileDotExt> fileDotExts = null;
		int succCount = 0;
		int defaultCount = 0;
		for (int i = 1; i <= pageTotal; i++) {
			fileDotExts = cellConvertTaskService.getCellTemplatesByPageId(i, 10, intusedomain, new Date(99, 1, 1));
			for (FileDotExt fileDotExt : fileDotExts)
				if (cellConvertTaskService.convertCellTemplate(fileDotExt,"kendo")) {
					succCount++;
				} else {
					defaultCount++;
				}
		}
		jsonObj.put("return", "转换成功" + succCount + "条，失败" + defaultCount + "条");
		////System.out.println("转换成功" + succCount + "条，失败" + defaultCount + "条");
		//公式二次转换
		formulaConvertService.convertAllFormula();
		
		return jsonObj.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping(value = "/cell/convertDataSet")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] convertDataSet(@Context HttpServletRequest request) throws Exception {
		this.convert();
		return null;
	}

	@Autowired
	DataSetConvertService dataSetConvertService;

	@Autowired
	PageConvertService pageConvertService;

	/**
	 * 转换规则
	 */
	private void convert() {

		List<CellConvertService> cellConvertServices = new ArrayList<CellConvertService>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
				add(pageConvertService); // 转换页面
				add(dataSetConvertService); // 转换数据集
			}
		};

		for (CellConvertService cellConvertService : cellConvertServices) {

			cellConvertService.convert();
		}

	}

}
