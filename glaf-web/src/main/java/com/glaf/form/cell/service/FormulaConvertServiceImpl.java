package com.glaf.form.cell.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.domain.ConvertElemTmplFormlExt;
import com.glaf.convert.query.ConvertElemTmplQuery;
import com.glaf.convert.service.ConvertElemTmplFormlService;
import com.glaf.convert.service.ConvertElemTmplService;
import com.glaf.core.config.CustomProperties;
import com.glaf.form.cell.util.FormulaToEventUtil;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;

@Service("formulaConvertService")
public class FormulaConvertServiceImpl implements FormulaConvertService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	protected ConvertElemTmplFormlService convertElemTmplFormlService;

	@Autowired
	protected ConvertElemTmplService convertElemTmplService;

	@Autowired
	protected FormulaToEventUtil formulaToEventUtil;

	private Set<String> formulaConstant;
	private Map<String, String> colNumConstant;
	private Map<String, ConvertElemTmpl> convertElemTmplMap;
	private Set<String> canConvertMethod;
	private Set<String> needReplaceMethod;
	private boolean isload = false;

	/**
	 * 替换公式
	 * 
	 * @param formulaStr
	 *            需要替换的公式字符串
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param cvtElemId
	 *            表CVT_ELEM_TMPL的CVT_ELEM_ID_
	 * @return
	 */
	@Override
	public String convertFormula(String formulaStr, String cvtId,
			String cvtElemId) {
		if (!isload) {
			loadFormulaConstant();
			loadColNumConstant();
			loadConvertElemTmplMap();
			loadCanConvertMethod();
			loadNeedReplaceMethod();
			isload = true;
		}

		Stack<String> method = new Stack<String>(); // 保存方法名
		Stack<Character> bracket = new Stack<Character>();// 保存对应括号
		Stack<String> params = new Stack<String>(); // 保存数字
		// Stack<Integer> paramCount = new Stack<Integer>(); // 保存参数个数

		JSONObject resultJson = new JSONObject();// 最后的返回结果
		StringBuffer convertResult = new StringBuffer();// 公式转换结果
		StringBuffer tempstr = new StringBuffer();// 拼凑字符
		Set<String> varList = new LinkedHashSet<String>();// 变量列表

		// 记录能够单独处理的方法
		String methodstr = null;
		String tempmethodstr = null;
		StringBuffer paramsstr = new StringBuffer();
		boolean isArea = false;// 判断是否属于区间

		char preChar = '~';// 保存前一个字符
		boolean noOperation = true;// 用于判断是否只有单元格
		for (int i = 0; i < formulaStr.length(); i++) {

			char c = formulaStr.charAt(i);
			if (!formulaConstant.contains(String.valueOf(c))) {
				// 转换and、or条件
				if (tempstr.indexOf("and") > -1) {
					tempstr.replace(tempstr.indexOf("and"), tempstr.length(),
							"&& ");
					convertResult.append(tempstr);
					tempstr.setLength(0);
				} else if (tempstr.indexOf("or") > -1) {
					tempstr.replace(tempstr.indexOf("or"), tempstr.length(),
							"|| ");
					convertResult.append(tempstr);
					tempstr.setLength(0);
				} else {
					tempstr.append(c);
				}
			} else {
				Map<String, String> elemIdMap = null;
				switch (c) {
				case '(':
					noOperation = false;
					// 前面是方法
					if (canConvertMethod.contains(tempstr.toString())) {
						method.push(tempstr.toString());
						tempmethodstr = tempstr.toString();
						bracket.push(')');
					} else {
						if (needReplaceMethod.contains(tempstr.toString()
								.toLowerCase())) {
							// 替换js中属于关键字的方法，如if等
							tempmethodstr = tempstr.toString().toLowerCase();
							String cm = CustomProperties
									.getString(tempmethodstr);
							tempstr.replace(tempstr.indexOf(tempmethodstr),
									tempstr.length(), cm);
							cm = null;
						}
						convertResult.append(tempstr).append("(");
					}
					tempstr.setLength(0);
					break;
				case ',':
					noOperation = false;
					// 前面是参数
					if (isArea) {
						// 如果是区间的单元格，不做处理，并清空数据
						// 因为区间单元格只取第一个domId即可在:之前已取过，此处不必再取
						isArea = false;
						tempstr.setLength(0);
					}
					if (canConvertMethod.contains(tempmethodstr)) {
						if (paramsstr.length() > 0) {
							paramsstr.append(tempstr.toString());
							params.push(paramsstr.toString());
							paramsstr.setLength(0);
						} else {
							params.push(tempstr.toString());
						}
					} else {
						convertResult.append(convertOperation(cvtId,
								tempstr.toString(), ",", varList));
					}
					tempstr.setLength(0);
					break;
				case '=':
					noOperation = false;
					// 前面是单元格或数值
					String operation = "==";
					if (preChar == '<' || preChar == '>') {
						operation = "=";
						preChar = '~';
					}
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), operation, varList));
					tempstr.setLength(0);
					break;
				case '<':
					noOperation = false;
					preChar = '<';
					// 前面是单元格或数值
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), "<", varList));
					tempstr.setLength(0);
					break;
				case '>':
					noOperation = false;
					if (preChar == '<') {
						// 判断是否为<>，转换为!=
						convertResult.replace(convertResult.lastIndexOf("<"),
								convertResult.length(), "!");
						convertResult.append("=");
						preChar = '~';
					} else {
						// 前面是单元格或数值
						convertResult.append(convertOperation(cvtId,
								tempstr.toString(), ">", varList));
					}
					preChar = '>';
					tempstr.setLength(0);
					break;
				case '+':
					noOperation = false;
					// 加号运算
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), "+", varList));
					tempstr.setLength(0);
					break;
				case '-':
					noOperation = false;
					// 减号运算
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), "-", varList));
					tempstr.setLength(0);
					break;
				case '*':
					noOperation = false;
					// 乘号运算
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), "*", varList));
					tempstr.setLength(0);
					break;
				case '/':
					noOperation = false;
					// 除号运算
					convertResult.append(convertOperation(cvtId,
							tempstr.toString(), "/", varList));
					tempstr.setLength(0);
					break;
				case ':':
					noOperation = false;
					// 单元格区间
					elemIdMap = getElemId(cvtId, tempstr.toString());
					if (elemIdMap.get("src") == null) {
						convertResult.append(tempstr.toString());
					} else {
						varList.add(elemIdMap.get("src"));
						convertResult.append("c_gn('" + elemIdMap.get("rst")
								+ "')");
					}
					isArea = true;// 设置目前属于区间的单元格转换，下一次","前的单元格将不处理
					tempstr.setLength(0);

					break;
				case ')':
					noOperation = false;
					// 结束一个方法
					if (isArea) {
						// 如果是区间的单元格，不做处理，并清空数据
						isArea = false;
						tempstr.setLength(0);
					}

					if (!method.isEmpty()) {
						methodstr = method.pop();
					}
					if (canConvertMethod.contains(methodstr)) {
						if (tempstr.length() > 0) {
							params.push(tempstr.toString());
						}

						convertResult.append(convertMethod(cvtId, methodstr,
								params, varList));
						methodstr = null;
						bracket.pop();
					} else {
						elemIdMap = getElemId(cvtId, tempstr.toString());
						if (elemIdMap.get("src") != null) {
							varList.add(elemIdMap.get("src"));
							convertResult.append(
									"c_gv('" + elemIdMap.get("rst") + "')")
									.append(")");
						} else {
							convertResult.append(tempstr).append(")");
						}
					}
					tempstr.setLength(0);
					break;
				case '!':
					// 前面是引用页，清除引用页信息
					tempstr.setLength(0);
					break;
				default:
					convertResult.append(tempstr);
					break;
				}
			}
		}
		if (noOperation) {
			// 没有运算符，即只有单元格的时候
			Map<String, String> elemIdMap = getElemId(cvtId, tempstr.toString());
			if (elemIdMap.get("src") != null) {
				varList.add(elemIdMap.get("src"));
				convertResult.append("c_gv('" + elemIdMap.get("rst") + "')");
			} else {
				convertResult.append(tempstr.toString());
			}
		}

		/**
		 * {<br>
		 * eleId：CVT_ELEM_TMPL_FORML 中的 CVT_ELEM_ID_,<br>
		 * varList：公式变量集合, 如：textbox_11_22,<br>
		 * resultFormula：转换后的公式<br>
		 * }
		 */
		resultJson.put("eleId", cvtElemId);
		resultJson.put("varList", varList.toArray());
		resultJson.put("resultFormula", convertResult.toString());

		return resultJson.toJSONString();
	}

	/**
	 * 判断字符串是否为单元格，能直接获取到domId并转换
	 * 
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param data
	 *            数据字符串
	 * @param operation
	 *            运算符
	 * @return 如果能够获取到domId，则返回获取domValue的方法; 如果不能获取到domId，则直接返回原数据
	 */
	private String convertOperation(String cvtId, String data,
			String operation, Set<String> varList) {
		Map<String, String> elemIdMap = getElemId(cvtId, data);
		if (elemIdMap.get("src") == null) {
			return data + operation;
		} else {
			varList.add(elemIdMap.get("src"));
			return "c_gv('" + elemIdMap.get("rst") + "')" + operation;
		}
	}

	/**
	 * 转换可以自行处理的方法
	 * 
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param methodstr
	 *            方法名
	 * @param params
	 *            参数值列表
	 * @return
	 */
	private String convertMethod(String cvtElemId, String methodstr,
			Stack<String> params, Set<String> varList) {
		if ("cell".equalsIgnoreCase(methodstr)) {
			Map<String, String> elemIdMap = null;
			if (params.size() == 1) {
				// 只有一个参数时,Cell(第1页!A1)
				String rowcolstr = params.pop();
				elemIdMap = getElemId(cvtElemId, rowcolstr);
			} else if (params.size() == 3) {
				// 有三个参数时,Cell(2,2,1);
				String page = params.pop();
				String row = params.pop();
				String col = params.pop();
				elemIdMap = getElemId(cvtElemId, Integer.parseInt(col),
						Integer.parseInt(row));
			}
			varList.add(elemIdMap.get("src"));
			return "c_gv('" + elemIdMap.get("rst") + "')";
		}
		return null;
	}

	/**
	 * 根据列、行返回DOM节点的ID
	 * 
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param col
	 *            列
	 * @param row
	 *            行
	 * @return
	 */
	private Map<String, String> getElemId(String cvtId, int col, int row) {
		ConvertElemTmpl elem = convertElemTmplMap.get(cvtId + "_" + row + "_"
				+ col);
		Map<String, String> result = new HashMap<String, String>();
		if (elem != null) {
			result.put("src", elem.getElemId());
			result.put("rst", parseElemId(elem));
		}
		return result;
	}

	/**
	 * 根据行、列字符串返回DOM节点的ID
	 * 
	 * @param cvtId
	 *            表CVT_PAGE_TMPL的CVT_ID_
	 * @param rowcolstr
	 *            如：Q10
	 * @return String 返回ElemId
	 * 
	 */
	private Map<String, String> getElemId(String cvtId, String rowcolstr) {
		Map<String, String> rowcolMap = getRowColString(rowcolstr.toString());
		String row = rowcolMap.get("row");
		String col = colNumConstant.get("COLUMN_" + rowcolMap.get("col"));

		ConvertElemTmpl elem = convertElemTmplMap.get(cvtId + "_" + row + "_"
				+ col);

		Map<String, String> result = new HashMap<String, String>();
		if (elem != null) {
			result.put("src", elem.getElemId());
			result.put("rst", parseElemId(elem));
		}
		return result;
	}

	/**
	 * 转换行列及变长区变量
	 * 
	 * @param elem
	 * @return
	 */
	private String parseElemId(ConvertElemTmpl elem) {
		String elemId = elem.getElemId();

		if (elem.getVararea() != null && "1".equals(elem.getVararea())) {
			if (elem.getDirection() != null && "1".equals(elem.getDirection())) {
				// 横向
				elemId = elemId.substring(0, elemId.lastIndexOf("_"))
						+ "_{area}";
			}
			if (elem.getDirection() != null && "0".equals(elem.getDirection())) {
				// 竖向
				elemId = elemId.substring(0, elemId.indexOf("_"))
						+ "_{area}_"
						+ elemId.substring(elemId.lastIndexOf("_") + 1,
								elemId.length());
			}
		}
		return elemId;
	}

	/**
	 * 根据单元格行列，分隔出对应的行、列字符串
	 * 
	 * @param rowcolstr
	 *            如：Q10
	 * @return Map<String, String> 返回行列的Map {"row":"10","col":"Q"}
	 */
	private Map<String, String> getRowColString(String rowcolstr) {
		String colTemp = "";// 列
		String rowTemp = "";// 行
		for (int j = 0; j < rowcolstr.length(); j++) {
			char ct = rowcolstr.charAt(j);
			if (NumberUtils.isNumber(String.valueOf(ct))) {
				rowTemp += String.valueOf(ct);
			} else {
				colTemp += String.valueOf(ct);
			}
		}
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put("row", rowTemp);
		tempMap.put("col", colTemp);
		return tempMap;
	}

	/**
	 * 查询表中所有公式并替换
	 */
	@Override
	public void convertAllFormula() {
		if (!isload) {
			loadFormulaConstant();
			loadColNumConstant();
			loadConvertElemTmplMap();
			loadCanConvertMethod();
			loadNeedReplaceMethod();
			isload = true;
		}

		List<ConvertElemTmplFormlExt> list = convertElemTmplFormlService
				.getAllConvertElemFormlExts();

		for (ConvertElemTmplFormlExt formula : list) {
			if (formula.getFormlContent() != null
					&& StringUtils.isNotEmpty(formula.getFormlContent())
					&& (formula.getCvtFormlContent() == null || StringUtils
							.isEmpty(formula.getCvtFormlContent()))) {
				// 公式不为空，并且转换后的公式字段为空
				String cvtId = String.valueOf(formula.getCvtId());
				String cvtElemId = String.valueOf(formula.getCvtElemId());
				String content = formula.getFormlContent();

				String result = this.convertFormula(content, cvtId, cvtElemId);
				formula.setCvtFormlContent(result);
				convertElemTmplFormlService.save(formula);
			}
		}

	}

	/**
	 * 加载需要替换的方法，从基础数据中设置，如if。 <br>
	 * 属性js关键字的方法，都需要进行转换处理
	 * 替换后的方法从/WEB-INF/conf/props/custom/formula_method.properties配置
	 */
	private void loadNeedReplaceMethod() {
		if (needReplaceMethod == null || needReplaceMethod.isEmpty()) {
			needReplaceMethod = new HashSet<String>();
			Iterator<FormDictory> formDictorys = FormDictoryFactory
					.getInstance().getFormDictoryIteratorByTreeCode(
							"need_replace_method");
			while (formDictorys.hasNext()) {
				FormDictory dict = formDictorys.next();
				needReplaceMethod.add(dict.getValue());
			}
		}
	}

	/**
	 * 加载能够直接转换的Cell方法，从基础数据中设置
	 */
	private void loadCanConvertMethod() {
		if (canConvertMethod == null || canConvertMethod.isEmpty()) {
			canConvertMethod = new HashSet<String>();
			Iterator<FormDictory> formDictorys = FormDictoryFactory
					.getInstance().getFormDictoryIteratorByTreeCode(
							"can_convert_method");
			while (formDictorys.hasNext()) {
				FormDictory dict = formDictorys.next();
				canConvertMethod.add(dict.getValue());
			}
		}
	}

	/**
	 * 加载表达式常量符号，从基础数据中设置
	 */
	private void loadFormulaConstant() {
		if (formulaConstant == null || formulaConstant.isEmpty()) {
			formulaConstant = new HashSet<String>();
			Iterator<FormDictory> formDictorys = FormDictoryFactory
					.getInstance().getFormDictoryIteratorByTreeCode(
							"cell_formula_constant");
			while (formDictorys.hasNext()) {
				FormDictory dict = formDictorys.next();
				formulaConstant.add(dict.getValue());
			}
		}
	}

	/**
	 * 加载Cell表格列号对应的数字。如列A对应1，列B对应2，从基础数据中设置
	 */
	private void loadColNumConstant() {
		if (colNumConstant == null || colNumConstant.isEmpty()) {
			colNumConstant = new HashMap<String, String>();
			Iterator<FormDictory> formDictorys = FormDictoryFactory
					.getInstance().getFormDictoryIteratorByTreeCode(
							"cell_formula_colnum");
			while (formDictorys.hasNext()) {
				FormDictory dict = formDictorys.next();
				colNumConstant.put(dict.getCode(), dict.getValue());
			}
		}
	}

	/**
	 * 加载行列对应的domId
	 */
	private void loadConvertElemTmplMap() {
		// if (convertElemTmplMap == null || convertElemTmplMap.isEmpty()) {
		convertElemTmplMap = new HashMap<String, ConvertElemTmpl>();

		ConvertElemTmplQuery query = new ConvertElemTmplQuery();
		List<ConvertElemTmpl> list = convertElemTmplService.list(query);
		for (ConvertElemTmpl cvt : list) {
			String key = cvt.getCvtId() + "_" + cvt.getRowIndex() + "_"
					+ cvt.getColIndex();
			convertElemTmplMap.put(key, cvt);
		}
		// }
	}

}
