package com.glaf.form.cell.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.JdbcUtils;
import com.glaf.core.util.UUID32;
import com.glaf.core.util.threads.ThreadFactory;
import com.glaf.datamgr.jdbc.JdbcInsert;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRulePropertyService;

@Service("pageConvertService")
public class PageConvertServiceImpl extends CellConvertServiceImpl implements PageConvertService {

	@Autowired
	private FormPageService formPageService;

	private Set<String> set = null;

	public PageConvertServiceImpl() {
		sqlProperties = super.getCellPropertiesByName("page.sql.properties");
	}

	public void init() {
		set = new HashSet<String>();
		FormPageQuery query = new FormPageQuery();
		List<FormPage> list = formPageService.list(query);
		if (list != null && !list.isEmpty())
			for (FormPage fPage : list)
				set.add(fPage.getId());
		list = null;
	}

	public Boolean contains(String formPageId) {
		return set.contains(formPageId);
	}

	@Override
	public boolean convert() {
		logger.debug("-------------------start convert-------------------");
		String sql = super.getProperty("page.tmp.sql");// 要运行的SQL语句
		ResultSet rSet = null;
		Connection connection = null;
		PreparedStatement spsmt = null;// 查询
		PreparedStatement dpsmt = null;// 页面规则
		PreparedStatement upsmt = null;// 修改页面
		int update = 0;
		try {
			this.init();
			FormPage page = new FormPage();
			String id = prefix + "parent";
			// page = formPageService.getFormPage(id);
			if (!this.contains(id)) {
				page.setExtend(true);
			}
			page.setId(id);
			page.setName("cell 表页面");
			page.setUpdateDate(new Date());
			page.setLocked(0);
			page.setDeleteFlag(0);
			page.setFormType("1");
			formPageService.save(page);

			String parentId = page.getId();

			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

			spsmt = connection.prepareStatement(sql.toString());

			dpsmt = connection.prepareStatement("DELETE FROM FORM_RULE WHERE PAGEID_ = ?");

			upsmt = connection
					.prepareStatement("UPDATE FORM_PAGE SET NAME_ = ?, UPDATEDATE_ = ?, FORMHTML_ = ? WHERE ID_ = ?");

			rSet = spsmt.executeQuery();
			if (rSet != null && rSet.next()) {
				Document html;
				String btnsTemplate = "<div style=\"text-align:center;vertical-align: middle;width: 300px;\"><button id='button%s' crtltype=\"kendo\" data-role=\"button\" class='k-button'>保存</button><button id='button%s' crtltype=\"kendo\" data-role=\"button\" class='k-button' >提交</button></div>";
				JdbcInsert<FormPage> jdbcInsert = new JdbcInsert<FormPage>();
				jdbcInsert.setConnection(connection);
				Map<String, Map<String, Map<String, String>>> map = this.getComponentMap(connection);
				do {
					int CVT_ID_ = rSet.getInt("CVT_ID_");
					// String CVT_TYPE_ = rSet.getString("CVT_TYPE_");
					String CVT_SRC_FILENAME_ = rSet.getString("CVT_SRC_FILENAME_");
					String CVT_DES_CONTENT_ = this.getResultSetStream2String(rSet, "CVT_DES_CONTENT_");

					Date CREAT_DATETIME_ = rSet.getDate("CREAT_DATETIME_");
					Date MODIFY_DATETIME_ = rSet.getDate("MODIFY_DATETIME_");

					id = prefix + CVT_ID_;

					page = new FormPage();

					if (CVT_DES_CONTENT_ != null) {
						html = Jsoup.parse(CVT_DES_CONTENT_);
						if (html != null) {
							html.body().append(String.format(btnsTemplate, "0_" + CVT_ID_, "1_" + CVT_ID_));
							CVT_DES_CONTENT_ = html.html();
						}
					} else {
						logger.error("convert cell page html content is null!");
						continue;
					}

					page.setFormHtml(CVT_DES_CONTENT_);
					page.setId(id);
					page.setCreateBy(createBy);
					page.setCreateDate(CREAT_DATETIME_);
					page.setUpdateDate(MODIFY_DATETIME_);
					page.setName(CVT_SRC_FILENAME_);
					page.setParentId(parentId);
					page.setLocked(0);
					page.setDeleteFlag(0);
					page.setFormType("0");

					// formPageService.save(page); // 太慢了
					if (!this.contains(id)) {
						jdbcInsert.addBatch(page);
					} else {
						fillPreparedStatement(upsmt, CVT_SRC_FILENAME_, new Date(), CVT_DES_CONTENT_, id);
						upsmt.addBatch();
						update++;

						dpsmt.setString(1, id);
						dpsmt.addBatch();
					}
					this.createElementRule(connection, CVT_ID_, map);// 生成页面所有元素的基本规则

				} while (rSet.next());

				if (jdbcInsert.batchSize() > 0) {
					jdbcInsert.executeBatch();
				}
				if (update > 0) {
					upsmt.executeBatch();
					dpsmt.executeBatch();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rSet);
			JdbcUtils.close(spsmt);
			JdbcUtils.close(dpsmt);
		}
		logger.debug("-------------------end convert-------------------");
		return false;
	}

	/**
	 * 生成页面所有元素的基本规则
	 * 
	 * @param connection
	 */
	public void createElementRule(Connection connection, int cvtId, Map<String, Map<String, Map<String, String>>> map) {
		String Elesql = super.getProperty("page.ele.sql");// 获取cell
		// 表页面里面的所有元素

		ResultSet rSet = null;
		PreparedStatement spsmt = null;
		try {
			spsmt = connection.prepareStatement(Elesql);
			spsmt.setInt(1, cvtId);

			rSet = spsmt.executeQuery();
			int index = 0;
			if (rSet != null && rSet.next()) {
				JdbcInsert<FormRule> fInsert = new JdbcInsert<FormRule>();
				fInsert.setConnection(connection);
				Map<String, Map<String, String>> Cap;
				JSONObject ruleJson = null;
				JSONArray jsonArray = new JSONArray();
				FormRule fRule;
				Date date = new Date();
				String elem_id_, cvt_id_, elem_name_, DATAROLE_, componentId;
				do {
					ruleJson = new JSONObject();
					fRule = new FormRule();
					elem_name_ = this.getString(rSet, "elem_name_".toUpperCase());
					cvt_id_ = rSet.getString("cvt_id_".toUpperCase());
					elem_id_ = rSet.getString("elem_id_".toUpperCase());
					DATAROLE_ = rSet.getString("DATAROLE_");
					if (map.containsKey(DATAROLE_)) {
						Cap = map.get(DATAROLE_);

						componentId = this.setValue(Cap, "id", ruleJson, elem_id_);
						this.setValue(Cap, "html", ruleJson, elem_name_);
						this.setValue(Cap, "enabled", ruleJson, true);
						this.setValue(Cap, "visible", ruleJson, true);

						if (Cap.containsKey("inparamdefined")) {
							final JSONObject inParamDefined = new JSONObject();

							inParamDefined.put("name", elem_name_ + " 参数");
							inParamDefined.put("param", "col" + (System.currentTimeMillis() + index++));

							jsonArray = new JSONArray() {
								/**
								 * 
								 */
								private static final long serialVersionUID = 1L;

								{
									add(inParamDefined);
								}
							};
							this.setValue(Cap, "inparamdefined", ruleJson, jsonArray);
						}
						fRule.setId(UUID32.getUUID());
						fRule.setPageId(prefix + cvt_id_);
						fRule.setName(elem_id_);
						if (componentId != null) {
							fRule.setComponentId(Long.parseLong(componentId));
						}
						fRule.setCreateBy(createBy);
						fRule.setCreateDate(date);
						fRule.setValue(ruleJson.toJSONString());
						fInsert.addBatch(fRule);

						ThreadFactory.run(new SaveProperties(formRulePropertyService, fRule));
						// //System.out.println(fRule);
					}
				} while (rSet.next());

				// 生成两个按钮规则

				if (fInsert.batchSize() > 0) {
					fInsert.executeBatch();
				}
			}
			map = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run createElementRule error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(rSet);
			JdbcUtils.close(spsmt);
		}

	}

	private class SaveProperties implements Runnable {
		private FormRulePropertyService formRulePropertyService;
		private FormRule fRule;

		public SaveProperties(FormRulePropertyService formRulePropertyService, FormRule fRule) {
			this.formRulePropertyService = formRulePropertyService;
			this.fRule = fRule;
		}

		@Override
		public void run() {
			if (this.formRulePropertyService != null && this.fRule != null) {
				formRulePropertyService.isRuleProperties(fRule);
				formRulePropertyService.saveComExt(fRule);
			}
		}
	}

	@Override
	public boolean convert(String cvtId) {
		// TODO Auto-generated method stub
		return false;
	}

}
