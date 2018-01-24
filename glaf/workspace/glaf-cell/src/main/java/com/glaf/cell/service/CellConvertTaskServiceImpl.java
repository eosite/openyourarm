package com.glaf.cell.service;

import java.io.UnsupportedEncodingException;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.cell.HtmlConverter;
import com.glaf.cell.domain.CellCategory;
import com.glaf.cell.domain.CellObject;
import com.glaf.cell.domain.ElementExtProp;
import com.glaf.cell.domain.FileDotExt;
import com.glaf.cell.mapper.CellConvertTaskMapper;
import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.domain.ConvertElemTmplData;
import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.domain.ConvertElemTmplValid;
import com.glaf.convert.domain.ConvertPageTmpl;
import com.glaf.convert.query.ConvertElemTmplQuery;
import com.glaf.convert.service.ConvertElemPropTmplService;
import com.glaf.convert.service.ConvertElemTmplDataService;
import com.glaf.convert.service.ConvertElemTmplFormlService;
import com.glaf.convert.service.ConvertElemTmplRefService;
import com.glaf.convert.service.ConvertElemTmplService;
import com.glaf.convert.service.ConvertElemTmplValidService;
import com.glaf.convert.service.ConvertPageTmplService;
import com.glaf.isdp.domain.CellRepInfo2;
import com.glaf.isdp.query.CellRepInfo2Query;
import com.glaf.isdp.service.CellRepInfo2Service;

@Service("com.glaf.cell.service.cellConvertTaskService")
@Transactional(readOnly = true)
public class CellConvertTaskServiceImpl implements CellConvertTaskService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected CellConvertTaskMapper cellConvertTaskMapper;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ConvertPageTmplService convertPageTmplService;

	protected ConvertElemTmplService convertElemTmplService;

	protected ConvertElemPropTmplService convertElemPropTmplService;

	protected ConvertElemTmplFormlService convertElemTmplFormlService;

	protected ConvertElemTmplValidService convertElemTmplValidService;

	protected ConvertElemTmplDataService convertElemTmplDataService;

	protected CellRepInfo2Service cellRepInfo2Service;

	protected CellConvertReferenceService cellConvertReferenceService;

	protected ConvertElemTmplRefService convertElemTmplRefService;

	@Override
	public int getCellTemplateCount(int useDomain, Date startDate) {
		 
		return cellConvertTaskMapper.getCellTemplateCount(useDomain,
				new SimpleDateFormat("yyyy-MM-dd-HH mm:ss").format(startDate));
	}

	@Override
	public List<FileDotExt> getCellTemplatesByPageId(int pageId, int pageSize,
			int useDomain, Date startDate) {
		int start = (pageId - 1) * pageSize;
		RowBounds rowBounds = new RowBounds(start, pageSize);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("useDomain", useDomain);
		paramMap.put("startDate",
				new SimpleDateFormat("yyyy-MM-dd-HH mm:ss").format(startDate));
		List<FileDotExt> rows = sqlSessionTemplate.selectList(
				"getCellTemplates", paramMap, rowBounds);
		return rows;
	}

	@Transactional(propagation = Propagation.NEVER)
	@Override
	public boolean convertCellTemplate(FileDotExt cellTemplate,String desPageType) {
		boolean flag = false;
		byte[] xmlContent = cellTemplate.getXmlContent();
		if (xmlContent == null) {
			logger.error("转换模板的XML解析文件为空");
			flag = false;
		} else {
			try {
				logger.debug(new SimpleDateFormat("HH:mm:ss")
						.format(new Date())
						+ "开始转换模板"
						+ cellTemplate.getFileName());
				// 保存转换后的模板记录
				CellObject cellObject = HtmlConverter
						.ConvertCellViewToPageView(new String(xmlContent),desPageType);
				logger.debug(new SimpleDateFormat("HH:mm:ss")
						.format(new Date())
						+ "转换模板完成"
						+ cellTemplate.getFileName());
				logger.debug(new SimpleDateFormat("HH:mm:ss")
						.format(new Date())
						+ "开始保存转换数据"
						+ cellTemplate.getFileName());
				cellObject = convertPageTmpl(cellTemplate, cellObject);
				logger.debug(new SimpleDateFormat("HH:mm:ss")
						.format(new Date())
						+ "结束保存转换数据"
						+ cellTemplate.getFileName());
				// 更新模板元素扩展属性
				convertCellElemExtProp(cellObject.getFileid(),
						cellObject.getElems());
				// 转换变长规则
				convertVarAreaAndRefRule(cellObject.getCvtId(),
						cellObject.getFileid(), cellObject.getElems());
				//二次生成模板（将变长区的元素lable修改为input，并且添加name属性，name值与变长设置单元格的ID一致）
				complPageTemplate(cellObject.getCvtId(),cellObject.getDesContent());
				flag = true;
			} catch (UnsupportedEncodingException e) {
				flag = false;
				logger.error("转换模板出错：" + e.getMessage());
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 保存转换后的模板主表
	 * 
	 * @param cellTemplate
	 * @param cellObject
	 * @return
	 */
	@Transactional(propagation = Propagation.NEVER)
	public CellObject convertPageTmpl(FileDotExt cellTemplate,
			CellObject cellObject) {
		//byte[] desContent = cellObject.getDesContent();
		ConvertPageTmpl convertPageTmpl = new ConvertPageTmpl();
		convertPageTmpl.setCvtSrcContent(cellTemplate.getFileContent());
		convertPageTmpl.setCvtSrcFileName(cellTemplate.getFileName());
		convertPageTmpl.setCvtSrcName(cellTemplate.getDotName());
		convertPageTmpl.setCvtXmlContent(cellTemplate.getXmlContent());
		convertPageTmpl.setCvtDesExt("jsp");
		//convertPageTmpl.setCvtDesContent(desContent);
		convertPageTmpl.setCvtType("cell");
		convertPageTmpl.setFileDotFieldId(cellTemplate.getFileID());
		convertPageTmpl.setEffectiveFlag(1);
		convertPageTmpl.setStatus(1);
		convertPageTmplService.save(convertPageTmpl);
		cellObject.setCvtId(convertPageTmpl.getCvtId());
		cellObject.setFileid(cellTemplate.getFileID());
		// 保存属性
		List<Map<String, String>> propList = cellObject.getPropList();
		if (propList != null) {
			int rowIndex = 0;
			int colIndex = 0;
			String formula = null;
			//ConvertElemPropTmpl convertElemPropTmpl = null;
			ConvertElemTmpl convertElemTmpl = null;
			ConvertElemTmplForml convertElemTmplForml = null;
			String elemId = null;
			String elemType=null;
			Map<String, ConvertElemTmpl> elems = new HashMap<String, ConvertElemTmpl>();
			for (Map<String, String> propMap : propList) {
				if (propMap.containsKey("rowIndex")
						&& propMap.containsKey("colIndex")) {
					rowIndex = Integer.parseInt(propMap.get("rowIndex"));
					colIndex = Integer.parseInt(propMap.get("colIndex"));
					// 保存属性
					convertElemPropTmplService.batchSave(propMap,
							convertPageTmpl.getCvtId(), rowIndex, colIndex);
					// 保存页面有效元素
					if (!propMap.containsKey("valid")
							|| (propMap.containsKey("valid")&&propMap.get("valid").equals("true"))) {
						convertElemTmpl = new ConvertElemTmpl();
						convertElemTmpl.setCvtId(convertPageTmpl.getCvtId());
						elemType=propMap.get("nodeType");
						if (elemType.equals("datefield")) {
							elemType = "datepicker";
						} else if (elemType.equals("label")) {
							elemType = "label";
						} else if (elemType.equals("textfield")) {
							elemType = "textbox";
						} else if (elemType.equals("double")) {
							//elemType = "numerictextbox";
							elemType = "textbox";
						} else if (elemType.equals("numberfield")) {
							//elemType = "numerictextbox";
							elemType = "textbox";
						} else {
							elemType = "textbox";
						}
						elemId = elemType + "_" + rowIndex + "_"
								+ colIndex;
						convertElemTmpl.setDataRole(elemType);
						convertElemTmpl.setElemId(elemId);
						convertElemTmpl.setRowIndex(rowIndex);
						convertElemTmpl.setColIndex(colIndex);
						convertElemTmpl.setDisplay(propMap.get("isHide")
								.equals("True") ? "1" : "0");
						convertElemTmpl.setCreateDatetime(new Date());
						convertElemTmpl.setModifyDatetime(new Date());
						convertElemTmplService.save(convertElemTmpl);
						elems.put(rowIndex + "_" + colIndex, convertElemTmpl);
						// 保存公式
						if (propMap.containsKey("formula")) {
							formula = propMap.get("formula");
							if (formula != null && formula.trim().length() > 0) {
								convertElemTmplForml = new ConvertElemTmplForml();
								convertElemTmplForml
										.setCvtElemId(convertElemTmpl
												.getCvtElemId());
								convertElemTmplForml.setFormlContent(formula);
								convertElemTmplForml.setUseConditon("true");
								convertElemTmplForml
										.setCreateDatetime(new Date());
								convertElemTmplForml
										.setModifyDatetime(new Date());
								convertElemTmplFormlService
										.save(convertElemTmplForml);
							}
						}
					}
				}
			}
			cellObject.setElems(elems);
		}
		return cellObject;
	}

	@javax.annotation.Resource
	public void setCellConvertTaskMapper(
			CellConvertTaskMapper cellConvertTaskMapper) {
		this.cellConvertTaskMapper = cellConvertTaskMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setConvertPageTmplService(
			ConvertPageTmplService convertPageTmplService) {
		this.convertPageTmplService = convertPageTmplService;
	}

	@javax.annotation.Resource
	public void setConvertElemPropTmplService(
			ConvertElemPropTmplService convertElemPropTmplService) {
		this.convertElemPropTmplService = convertElemPropTmplService;
	}

	@javax.annotation.Resource
	public void setConvertElemTmplFormlService(
			ConvertElemTmplFormlService convertElemTmplFormlService) {
		this.convertElemTmplFormlService = convertElemTmplFormlService;
	}

	@javax.annotation.Resource
	public void setConvertElemTmplService(
			ConvertElemTmplService convertElemTmplService) {
		this.convertElemTmplService = convertElemTmplService;
	}

	@javax.annotation.Resource
	public void setConvertElemTmplValidService(
			ConvertElemTmplValidService convertElemTmplValidService) {
		this.convertElemTmplValidService = convertElemTmplValidService;
	}

	@javax.annotation.Resource
	public void setConvertElemTmplDataService(
			ConvertElemTmplDataService convertElemTmplDataService) {
		this.convertElemTmplDataService = convertElemTmplDataService;
	}

	@javax.annotation.Resource
	public void setCellRepInfo2Service(CellRepInfo2Service cellRepInfo2Service) {
		this.cellRepInfo2Service = cellRepInfo2Service;
	}

	@javax.annotation.Resource
	public void setCellConvertReferenceService(
			CellConvertReferenceService cellConvertReferenceService) {
		this.cellConvertReferenceService = cellConvertReferenceService;
	}

	@javax.annotation.Resource
	public void setConvertElemTmplRefService(
			ConvertElemTmplRefService convertElemTmplRefService) {
		this.convertElemTmplRefService = convertElemTmplRefService;
	}

	/**
	 * 查询CELL表扩展属性配置更新到转换规则表中
	 */
	@Override
	public boolean convertCellElemExtProp(String fileId,
			Map<String, ConvertElemTmpl> elems) {
		List<ElementExtProp> elementsExtProp = cellConvertTaskMapper
				.getFileElementsExtProp(fileId);

		int row_index = 0;
		int col_index = 0;
		ConvertElemTmpl convertElemTmpl = null;
		int cellType = 0;
		//int node_index = 0;
		int order_index=0;
		int order_con=0;
		for (ElementExtProp elementExtProp : elementsExtProp) {
			row_index = elementExtProp.getOst_row();
			col_index = elementExtProp.getOst_col();
			cellType = elementExtProp.getCellType();
			//node_index = elementExtProp.getNodeIndex();
			order_index=elementExtProp.getOrderIndex();
			order_con=elementExtProp.getOrderCon();
			if (elems.containsKey(row_index + "_" + col_index)) {
				convertElemTmpl = elems.get(row_index + "_" + col_index);
				convertElemTmpl.setDefaultVal(elementExtProp.getDefaultValue());
				convertElemTmpl.setDType(elementExtProp.getdType());
				convertElemTmpl.setElemType(elementExtProp.getInType());
				convertElemTmpl.setPrint(elementExtProp.getIsPrintValue());
				convertElemTmpl.setReadOnly("0");
				convertElemTmpl.setLen(elementExtProp.getStrlen());
				convertElemTmpl.setDigit(elementExtProp.getDataPoint());
				convertElemTmpl.setIsMustFill(elementExtProp.getIsMustfill());
				convertElemTmpl.setIsDataOnly(elementExtProp.getIsDataOnly());
				convertElemTmpl.setElemName(elementExtProp.getfName());
				convertElemTmpl.setRepinfoListId(elementExtProp.getListId());
				convertElemTmplService.save(convertElemTmpl);
				// 转换验证规则
				convertValidRule(convertElemTmpl);
				// 转换数据规则
				convertDataRule(elementExtProp, convertElemTmpl);
				// 转换引用规则
				// 引基础数据
				// 引其他数据
				// 引用工程数据
				if (cellType == 6) {
					convertProjectRefRule(fileId, elementExtProp,
							convertElemTmpl);
				}
				//引用规范
				else if(cellType == 0&&order_index>0&&order_con==0){
					convertCriterionRef(elementExtProp,convertElemTmpl);
				}
				//引规范参数
				else if(cellType == 8&&order_index>0&&order_con==1){
					convertCriterionParamRef(elementExtProp,convertElemTmpl);
				}
				// 引用分类
				// 引规范
				// 引规范参数
				// 引字典表repinfo2处理
				// 引计算表
				// 引审核公式
				// 引审核参数

			}
		}
		return false;
	}

	/**
	 * 转换验证规则
	 * 
	 * @param convertElemTmpl
	 */
	public void convertValidRule(ConvertElemTmpl convertElemTmpl) {
		ConvertElemTmplValid pConvertElemTmplValid = null;
		Map<String, String> params = null;
		// 保存验证规则
		params = new HashMap<String, String>();
		params.put("dType", convertElemTmpl.getDType());
		if (convertElemTmpl.getLen() != null)
			params.put("len", convertElemTmpl.getLen().toString());
		// 验证唯一性
		if (convertElemTmpl.getIsDataOnly() != null
				&& convertElemTmpl.getIsDataOnly().equals("1")) {
			convertElemTmplValidService.createVaildRule(pConvertElemTmplValid,
					convertElemTmpl.getCvtElemId(), "unique", params);
		}
		// 验证必填
		if (convertElemTmpl.getIsMustFill() != null
				&& convertElemTmpl.getIsMustFill().equals("1")) {
			convertElemTmplValidService.createVaildRule(pConvertElemTmplValid,
					convertElemTmpl.getCvtElemId(), "require", params);
		}
		// 验证长度
		if (convertElemTmpl.getLen() != null && convertElemTmpl.getLen() > 0) {
			convertElemTmplValidService.createVaildRule(pConvertElemTmplValid,
					convertElemTmpl.getCvtElemId(), "length", params);
		}
		// 验证小数点
		if (convertElemTmpl.getDigit() != null
				&& convertElemTmpl.getDigit() > 0) {
			convertElemTmplValidService.createVaildRule(pConvertElemTmplValid,
					convertElemTmpl.getCvtElemId(), "decimaldigits", params);
		}
		// 验证数值类型
		if (convertElemTmpl.getDType() != null
				&& convertElemTmpl.getDType().trim().length() > 0) {
			convertElemTmplValidService.createVaildRule(pConvertElemTmplValid,
					convertElemTmpl.getCvtElemId(), "datatype", params);
		}
	}

	/**
	 * 转换数据规则
	 * 
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertDataRule(ElementExtProp elementExtProp,
			ConvertElemTmpl convertElemTmpl) {
		ConvertElemTmplData convertElemTmplData = new ConvertElemTmplData();
		convertElemTmplData.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplData.setTableName(elementExtProp.getTableName());
		convertElemTmplData.setFieldName(elementExtProp.getColumnName());
		convertElemTmplData.setSubTable(elementExtProp.getIsSubTable());
		convertElemTmplData.setDataTableId(elementExtProp.getDataTableId());
		convertElemTmplData.setCreateDatetime(new Date());
		convertElemTmplData.setModifyDatetime(new Date());
		convertElemTmplDataService.save(convertElemTmplData);
	}

	/**
	 * 转换引用工程信息规则
	 * 
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertProjectRefRule(String fileId,
			ElementExtProp elementExtProp, ConvertElemTmpl convertElemTmpl) {
		int node_index = elementExtProp.getNodeIndex();
		String sql = null;
		String refType = "pj_" + node_index;
		;
		switch (node_index) {
		// 引用所属节点
		case 1:
			sql = cellConvertReferenceService
					.getSelfWbsNodeReference(elementExtProp);
			break;
		// 引用工程项目
		case 2:
			sql = cellConvertReferenceService
					.getProjectReference(elementExtProp);
			break;
		// 引用单位工程
		case 3:
			sql = cellConvertReferenceService
					.getSubProjectReference(elementExtProp);
			break;
		// 引用分部工程
		case 4:
			sql = cellConvertReferenceService
					.getSubProjectReference(elementExtProp);
			break;
		// 引用分项工程
		case 5:
			sql = cellConvertReferenceService
					.getSubProjectReference(elementExtProp);
			break;
		// 引用WBS名称规则
		case 6:
			sql = cellConvertReferenceService.getWbsNameReference(fileId,
					elementExtProp);
			break;
		// 引用工程类型
		case 7:
			sql = cellConvertReferenceService.getProjectTypeReference(elementExtProp);
			break;
		// 引用WBS启动表格
		case 8:
			sql = cellConvertReferenceService
					.getStartWbsNodeReference(elementExtProp);
			break;
		default:
			break;
		}
		if (sql != null && sql.trim().length() > 0) {
			ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
			convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
			convertElemTmplRef.setCreateDatetime(new Date());
			convertElemTmplRef.setRefCondition("true");
			convertElemTmplRef.setUseCondition("true");
			convertElemTmplRef.setTranstionFlag("0");
			convertElemTmplRef.setRefContent(sql);
			convertElemTmplRef.setRefType(refType);
			convertElemTmplRefService.save(convertElemTmplRef);
		}
	}

	/**
	 * 转换引用规则
	 * 
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertRefRule(ElementExtProp elementExtProp,
			ConvertElemTmpl convertElemTmpl) {

	}

	/**
	 * 转换变长区和引用规则
	 * 
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertVarAreaAndRefRule(Long cvtId, String fileId,
			Map<String, ConvertElemTmpl> elems) {
		List<CellRepInfo2> cellRepInfo2List = getFileVarAreaAndRefRuleByFileId(fileId);
		int type = 0;
		int start_row = 0, start_col = 0;
		for (CellRepInfo2 cellRepInfo2 : cellRepInfo2List) {
			type = cellRepInfo2.getType();
			start_row = cellRepInfo2.getOstRow()==null?0:cellRepInfo2.getOstRow();
			start_col = cellRepInfo2.getOstCol()==null?0:cellRepInfo2.getOstCol();
			ConvertElemTmpl convertElemTmpl = elems.get(start_row + "_"
					+ start_col);
			// 变长处理
			if (type == 0) {
				convertVarAreaRule(cvtId, cellRepInfo2);
			}
			// 引入字典
			else if (type == 51) {
				convertDictionaryRefRule(cellRepInfo2, convertElemTmpl);
			}// 引入CELL表数据
			else if (type == 24) {
				convertCellDataRef(cellRepInfo2, convertElemTmpl);
			}// 引入业务表数据
			else if (type == 23) {
				convertBusinessDataRef(cellRepInfo2, convertElemTmpl);
			}//规范值统计规则
			else if (type == 27) {
				convertCriterionStatRef(fileId, cellRepInfo2,convertElemTmpl,elems);
			}
		}
	}

	/**
	 * 转换变长区
	 * 
	 * @param cellRepInfo2
	 */
	public void convertVarAreaRule(Long cvtId, CellRepInfo2 cellRepInfo2) {
		int start_row = 0, start_col = 0, end_row = 0, end_col = 0;
		int way = 0;// 0纵向 1横向
		start_row = cellRepInfo2.getOstRow();
		start_col = cellRepInfo2.getOstCol();
		end_row = cellRepInfo2.getOstRowEnd()==null?0: cellRepInfo2.getOstRowEnd();
		end_col = cellRepInfo2.getOstColEnd()==null?0: cellRepInfo2.getOstColEnd();
		way = cellRepInfo2.getOstWay();
		// 获取变长区栏目
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fileId", cellRepInfo2.getFileDotFileId());
		param.put("startRowIndex", start_row);
		param.put("endRowIndex", end_row);
		param.put("startColIndex", start_col);
		param.put("endColIndex", end_col);
		param.put("way", way);
		List<CellCategory> cellCategorys = null;
		CellCategory currCellCategory = null;
		CellCategory nextCellCategory = null;
		int categoryEndRowIndex = 0, categoryEndColIndex = 0;
		cellCategorys = cellConvertTaskMapper.getVarAreaCellCategorys(param);
		for (int i = 0; i < cellCategorys.size(); i++) {
			currCellCategory = cellCategorys.get(i);
			if (i < cellCategorys.size()-1) {
				nextCellCategory = cellCategorys.get(i + 1);
			} else {
				nextCellCategory = new CellCategory();
				nextCellCategory.setRow_index(end_row+1);
				nextCellCategory.setCol_index(end_col+1);
			}
			if (way == 1) {
				categoryEndRowIndex = nextCellCategory.getRow_index() - 1;
				categoryEndColIndex = end_col;
			} else if (way == 0) {
				categoryEndRowIndex = end_row;
				categoryEndColIndex = nextCellCategory.getCol_index() - 1;
			}
			// 更新变长位置
			cellConvertTaskMapper.updateCellCategoryVarArea(
					currCellCategory.getRow_index(),
					currCellCategory.getCol_index(), categoryEndRowIndex,
					categoryEndColIndex, cvtId,""+way);
		}
		//更新变长标识为1
		cellConvertTaskMapper.updateVarAreaElemsByCvtId(cvtId);
		//修正变长区元素类型为textbox
		cellConvertTaskMapper.updateCellVarAreaElemDataRole(cvtId);
	}

	/**
	 * 获取CELL表引用规则与变长规则
	 * 
	 * @param fileId
	 * @return
	 */
	public List<CellRepInfo2> getFileVarAreaAndRefRuleByFileId(String fileId) {
		CellRepInfo2Query query = new CellRepInfo2Query();
		query.setFileDotFileId(fileId);
		List<CellRepInfo2> cellRepInfo2List = cellRepInfo2Service.list(query);
		return cellRepInfo2List;
	}

	/**
	 * 转换字典引用规则
	 * 
	 * @param cellRepInfo2
	 * @param convertElemTmpl
	 */
	public void convertDictionaryRefRule(CellRepInfo2 cellRepInfo2,
			ConvertElemTmpl convertElemTmpl) {
		// 插入取值规则
		ConvertElemTmplForml convertElemTmplForml  = new ConvertElemTmplForml();
		convertElemTmplForml.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		if (cellRepInfo2.getDname().equals("value")) {
			// 插入公式			
			// 获取字典表
			String tableName = cellRepInfo2.getTableName();
			convertElemTmplForml.setFormlContent("GETDICTIONARY('" + tableName
					+ "')");			
			convertElemTmplFormlService.save(convertElemTmplForml);
			// 写入引用规则查询字典的SQL 此处需要将cell字典表数据转入到物理表
			String sql = cellConvertReferenceService
					.getDirecoryReference(cellRepInfo2.getTableName());
			ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
			convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
			convertElemTmplRef.setCreateDatetime(new Date());
			convertElemTmplRef.setRefCondition("true");
			convertElemTmplRef.setUseCondition("true");
			convertElemTmplRef.setTranstionFlag("0");
			convertElemTmplRef.setRefContent(sql);
			convertElemTmplRef.setRefType("dictionary");
			convertElemTmplRefService.save(convertElemTmplRef);
		}// 插入维度赋值规则
		else {
			// 插入公式
			// 获取维度定义
			String tableName = cellRepInfo2.getTableName();
			String dimName = cellRepInfo2.getDname();
			// 根据字典表名和维度定义唯一标识获取维度方位、行号或列号
			convertElemTmplForml.setFormlContent("SETDICDIM('" + tableName
					+ "','" + dimName + "')");
			convertElemTmplForml.setUseConditon("true");
			convertElemTmplForml.setCreateDatetime(new Date());
			convertElemTmplForml.setModifyDatetime(new Date());
			convertElemTmplFormlService.save(convertElemTmplForml);
		}
	}

	/**
	 * 转换引用CELL表数据规则
	 * 
	 * @param cellRepInfo2
	 * @param convertElemTmpl
	 */
	public void convertCellDataRef(CellRepInfo2 cellRepInfo2,
			ConvertElemTmpl convertElemTmpl) {
		String sql = cellConvertReferenceService.getCellDataReference(
				cellRepInfo2.getTableName(), cellRepInfo2.getFormula(),
				cellRepInfo2.getFrmType());
		ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
		convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplRef.setCreateDatetime(new Date());
		convertElemTmplRef.setRefCondition("true");
		convertElemTmplRef.setUseCondition("true");
		convertElemTmplRef.setTranstionFlag("0");
		convertElemTmplRef.setRefContent(sql);
		convertElemTmplRef.setRefType("celldata");
		convertElemTmplRefService.save(convertElemTmplRef);
	}

	/**
	 * 转换引用业务表数据规则
	 * 
	 * @param cellRepInfo2
	 * @param convertElemTmpl
	 */
	public void convertBusinessDataRef(CellRepInfo2 cellRepInfo2,
			ConvertElemTmpl convertElemTmpl) {
		String sql = cellConvertReferenceService.getBusinessDataReference(
				cellRepInfo2.getTableName(), cellRepInfo2.getFormula());
		ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
		convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplRef.setCreateDatetime(new Date());
		convertElemTmplRef.setRefCondition("true");
		convertElemTmplRef.setUseCondition("true");
		convertElemTmplRef.setTranstionFlag("0");
		convertElemTmplRef.setRefContent(sql);
		convertElemTmplRef.setRefType("businessdata");
		convertElemTmplRefService.save(convertElemTmplRef);
	}
	/**
	 * 转换规范规则
	 * @param cellRepInfo2
	 * @param convertElemTmpl
	 */
	public void convertCriterionRef(ElementExtProp elementExtProp,
			ConvertElemTmpl convertElemTmpl){
		String sql=cellConvertReferenceService.getCriterionReference(elementExtProp);
		ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
		convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplRef.setCreateDatetime(new Date());
		convertElemTmplRef.setRefCondition("true");
		convertElemTmplRef.setUseCondition("true");
		convertElemTmplRef.setTranstionFlag("0");
		convertElemTmplRef.setRefContent(sql);
		convertElemTmplRef.setRefType("criterion");
		convertElemTmplRefService.save(convertElemTmplRef);
		//公式规则 设置单元格引用规范
		ConvertElemTmplForml convertElemTmplForml  = new ConvertElemTmplForml();
		convertElemTmplForml.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		//设置单元格引用规则的ID到页面全局变量中 （前端通过map存储，汇总统计规范数据时，可通过引入关系获取规范字段，然后通过规范字段获取字段值）
		convertElemTmplForml.setFormlContent("SETVARIABLE('"+convertElemTmpl.getElemId().replaceFirst("label", "textbox")+"','"+elementExtProp.getOrderIndex()+"')");
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		convertElemTmplFormlService.save(convertElemTmplForml);
	}
	/**
	 * 转换规范参数
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertCriterionParamRef(ElementExtProp elementExtProp,
			ConvertElemTmpl convertElemTmpl){
		String sql=cellConvertReferenceService.getCriterionParamReference(elementExtProp);
		ConvertElemTmplRef convertElemTmplRef = new ConvertElemTmplRef();
		convertElemTmplRef.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplRef.setCreateDatetime(new Date());
		convertElemTmplRef.setRefCondition("true");
		convertElemTmplRef.setUseCondition("true");
		convertElemTmplRef.setTranstionFlag("0");
		convertElemTmplRef.setRefContent(sql);
		convertElemTmplRef.setRefType("criterionParam");
		convertElemTmplRefService.save(convertElemTmplRef);
		//公式规则
		ConvertElemTmplForml convertElemTmplForml  = new ConvertElemTmplForml();
		convertElemTmplForml.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		//设置规范参数值
		convertElemTmplForml.setFormlContent("SETVARIABLE('criterion_"+elementExtProp.getOrderIndex()+"','"+elementExtProp.getDataFieldName()+"')");
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		convertElemTmplFormlService.save(convertElemTmplForml);
	}
	/**
	 * 转换规范统计规则
	 * @param elementExtProp
	 * @param convertElemTmpl
	 */
	public void convertCriterionStatRef(String fileId,CellRepInfo2 cellRepInfo2,ConvertElemTmpl convertElemTmpl,Map<String, ConvertElemTmpl> elems){
		//获取规范统计值字段定义
		ElementExtProp statElementExtProp=cellConvertTaskMapper.getCritStatFieldExtProp(fileId,cellRepInfo2.getDname());
		//获取转换后的定义
		ConvertElemTmpl statConvertElemTmpl=elems.get(statElementExtProp.getOst_row()+"_"+statElementExtProp.getOst_col());
		//公式规则
		ConvertElemTmplForml convertElemTmplForml  = new ConvertElemTmplForml();
		convertElemTmplForml.setCvtElemId(convertElemTmpl.getCvtElemId());
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		//设置统计规范公式 （参数1:规范设置元素唯一标识，根据元素唯一标识获取规范唯一标识，根据规范唯一标识获取需统计的录入值 参数2:统计方式 0检测点数 1合格点数 2不合格点数）
		convertElemTmplForml.setFormlContent("CRITERIONSTAT('"+statConvertElemTmpl.getElemId().replaceFirst("label", "textbox")+"','"+cellRepInfo2.getOstWay()+"')");
		convertElemTmplForml.setUseConditon("true");
		convertElemTmplForml.setCreateDatetime(new Date());
		convertElemTmplForml.setModifyDatetime(new Date());
		convertElemTmplFormlService.save(convertElemTmplForml);
	}
	/**
	 * 
	 * @param cvtId
	 * @param pageTemplate
	 * @throws UnsupportedEncodingException 
	 */
	public void complPageTemplate(long cvtId,byte[] pageTemplate) throws UnsupportedEncodingException{
		//获取变长设置
		ConvertElemTmplQuery query=new ConvertElemTmplQuery();
		query.setVararea("1");
		query.setCvtId(cvtId);
		List<ConvertElemTmpl> convertElemTmpls=convertElemTmplService.list(query);
		int startRowIndex=0;
		int startColIndex=0;
		int endRowIndex=0;
		int endColIndex=0;
		String oldElemId=null;
		String title=null;
		String dataRole=null;
		String elemId=null;
		String nDataRole=null;
		Element element=null;
		Document doc = Jsoup
				.parse(new String(pageTemplate,"utf-8"));
		for(ConvertElemTmpl convertElemTmpl:convertElemTmpls){
			startRowIndex=convertElemTmpl.getRowIndex();
			startColIndex=convertElemTmpl.getColIndex();
			endRowIndex=convertElemTmpl.getEndRowIndex()!=null?convertElemTmpl.getEndRowIndex():0;
			endColIndex=convertElemTmpl.getEndColIndex()!=null?convertElemTmpl.getEndColIndex():0;
			dataRole=convertElemTmpl.getDataRole();
			title=convertElemTmpl.getElemName();
			elemId=convertElemTmpl.getElemId();
			for(int i=startRowIndex;i<=endRowIndex;i++){
				for(int j=startColIndex;j<=endColIndex;j++){
					//获取元素
					oldElemId=dataRole+"_"+i+"_"+j;
					element=doc.getElementById(oldElemId);
					if(element==null){
						oldElemId="label_"+i+"_"+j;
						element=doc.getElementById(oldElemId);
						nDataRole="textbox";
					}else{
						nDataRole=dataRole;
					}
					if(element!=null){
					element.attr("title", title+"_"+i+"_"+j);
					element.attr("data-role", nDataRole);
					element.attr("crtltype", "kendo");
					element.attr("name",elemId);
					element.attr("id", nDataRole+"_"+i+"_"+j);
					element.text("");
					element.tagName("input");
					}
				}
			}
		}
			byte[] newpageTemplate= doc.html().getBytes("UTF-8");
			//更新转换后的模板
			convertPageTmplService.updatePageTemplate(cvtId,newpageTemplate);
		
	}
}
