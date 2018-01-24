package com.glaf.base.project.web.rest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.project.domain.Project;
import com.glaf.base.project.service.ProjectService;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.Database;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.domain.CollectData;
import com.glaf.datamgr.domain.SqlDefinition;
import com.glaf.datamgr.query.CollectDataQuery;
import com.glaf.datamgr.query.SqlDefinitionQuery;
import com.glaf.datamgr.service.CollectDataService;
import com.glaf.datamgr.service.SqlDefinitionService;

@Controller("/rs/datamgr/dailySearch")
@Path("/rs/datamgr/dailySearch")
public class DailySearchResourceRest {

	protected static final Log logger = LogFactory.getLog(DailySearchResourceRest.class);

	private CollectDataService collectDataService;

	private ProjectService projectService;

	private IDatabaseService databaseService;

	private SqlDefinitionService sqlDefinitionService;

	private List<Dictory> getDialySqlSets(String userId) {
		BaseDataManager bdm = BaseDataManager.getInstance();
		List<Dictory> dictorys = bdm.getDictoryList("daily_sql_set");

		List<SqlDefinition> sql_list = sqlDefinitionService.getSqlDefinitionsByUserId(userId);
		Map<String, SqlDefinition> sqlMap = new HashMap<String, SqlDefinition>();
		if (sql_list != null && !sql_list.isEmpty()) {
			for (SqlDefinition sql : sql_list) {
				sqlMap.put(sql.getCode(), sql);
			}
		}

		List<Dictory> dictoryList = new ArrayList<Dictory>();
		for (Dictory model : dictorys) {
			if (sqlMap.get(model.getCode()) != null) {
				dictoryList.add(model);
			}
		}

		return dictoryList;
	}

	private List<String> getDialySqlCodes(String userId) {
		List<String> array = new ArrayList<String>();
		List<Dictory> dictorys = getDialySqlSets(userId);
		for (Dictory dict : dictorys) {
			array.add(dict.getCode());
		}
		return array;
	}

	private List<String> getDialySqlNames(String userId) {
		List<String> array = new ArrayList<String>();
		List<Dictory> dictorys = getDialySqlSets(userId);
		for (Dictory dict : dictorys) {
			array.add(dict.getName());
		}
		return array;
	}

	@GET
	@POST
	@Path("/columnCharts")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] columnCharts(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {

		Long projectId = RequestUtils.getLong(request, "projectId");
		String runDay = RequestUtils.getString(request, "runDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		List<Long> projectIds = new ArrayList<Long>();
		projectIds.add(projectId);

		JSONObject jobject = this.getData4Project(projectIds, Integer.parseInt(runDay), codes, 0, 100);
		JSONArray dataArray = jobject.getJSONArray("rows");

		return dataArray.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/columnChartsGrid")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] columnChartsGrid(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		Long projectId = RequestUtils.getLong(request, "projectId");
		String runDay = RequestUtils.getString(request, "runDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		List<Long> projectIds = new ArrayList<Long>();
		projectIds.add(projectId);

		JSONObject jobject = this.getData4Project(projectIds, Integer.parseInt(runDay), codes, 0, 100);

		return jobject.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 生成柱状图
	 * 
	 * @param dataSet
	 *            数据源
	 * @param title
	 *            标题
	 * @param x_title
	 *            x轴标题
	 * @param y_title
	 *            y轴标题
	 * @return
	 */
	private JFreeChart createBarCharts(DefaultCategoryDataset dataSet, String title, String x_title, String y_title) {
		JFreeChart jfreeChart = ChartFactory.createBarChart(title, x_title, y_title, dataSet, PlotOrientation.VERTICAL,
				true, true, false);

		Font font = new Font("宋体", Font.PLAIN, 12);
		jfreeChart.getTitle().setFont(new Font("宋体", Font.BOLD, 16)); // 设置标题字体
		jfreeChart.getLegend().setItemFont(font);// 设置图例类别字体

		// 获取绘图区对象
		CategoryPlot plot = jfreeChart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE); // 设置绘图区背景色
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY); // 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY); // 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false

		CategoryAxis categoryAxis = plot.getDomainAxis();
		categoryAxis.setCategoryMargin(0.3);
		categoryAxis.setTickLabelFont(font);// 设置坐标轴标尺值字体
		categoryAxis.setUpperMargin(0.02);// 左边距 边框距离
		categoryAxis.setLowerMargin(0.02);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
		categoryAxis.setMaximumCategoryLabelLines(2);
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setUpperMargin(0.10);
		rangeAxis.setAutoRange(true);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0.10);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// ItemLabelPosition p = new ItemLabelPosition(
		// ItemLabelAnchor.INSIDE11, TextAnchor.CENTER_RIGHT,
		// TextAnchor.CENTER_RIGHT, -Math.PI / 2.0);
		// renderer.setPositiveItemLabelPosition(p);

		ItemLabelPosition p2 = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT,
				TextAnchor.CENTER_LEFT, -Math.PI / 2.0);
		renderer.setPositiveItemLabelPositionFallback(p2);

		return jfreeChart;
	}

	/**
	 * 生成Charts图片
	 * 
	 * @param chartType
	 *            图表类型：0：折线图；1：柱状图
	 * @param records
	 *            数据
	 * @param title
	 *            标题
	 * @param categorys
	 *            X轴分类
	 * @param x_title
	 *            X轴标题
	 * @param y_title
	 *            Y轴标题
	 * @param imageWidth
	 *            图片宽度
	 * @param imageHeight
	 *            图片高度
	 * @return
	 */
	private BufferedImage createChartsImage(int chartType, JSONArray records, String title, List<String> categorys,
			String x_title, String y_title, int imageWidth, int imageHeight) {

		// 创建序列
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		JFreeChart chart = null;
		if (chartType == 0) {
			// 创建折线图
			for (int i = 0; i < records.size(); i++) {
				JSONObject obj = records.getJSONObject(i);

				for (String category : categorys) {
					Date tempDate = DateUtils.toDate(category);
					dataSet.addValue((Number) obj.getInteger("_" + DateUtils.getYearMonthDay(tempDate) + "_"),
							obj.getString("name"), category);
				}
			}

			chart = this.createLineCharts(dataSet, title, x_title, y_title);
		} else if (chartType == 1) {
			// 创建柱状图
			for (int i = 0; i < records.size(); i++) {
				JSONObject obj = records.getJSONObject(i);

				for (String field : categorys) {
					dataSet.addValue((Number) obj.getInteger(field), obj.getString(field + "_name"),
							obj.getString("title"));
				}

			}
			chart = this.createBarCharts(dataSet, title, x_title, y_title);
		}

		if (chart != null) {
			BufferedImage image = chart.createBufferedImage(imageWidth, imageHeight);
			return image;
		}
		return null;
	}

	/**
	 * 生成折线图
	 * 
	 * @param dataSet
	 *            数据源
	 * @param title
	 *            标题
	 * @param x_title
	 *            x轴标题
	 * @param y_title
	 *            y轴标题
	 * @return
	 */
	private JFreeChart createLineCharts(DefaultCategoryDataset dataSet, String title, String x_title, String y_title) {
		JFreeChart jfreeChart = ChartFactory.createLineChart(title, x_title, y_title, dataSet, PlotOrientation.VERTICAL,
				true, true, false);
		Font font = new Font("宋体", Font.PLAIN, 12);
		jfreeChart.getTitle().setFont(new Font("宋体", Font.BOLD, 16)); // 设置标题字体
		jfreeChart.getLegend().setItemFont(font);// 设置图例类别字体

		// 获取绘图区对象
		CategoryPlot plot = jfreeChart.getCategoryPlot();
		plot.setBackgroundPaint(Color.WHITE); // 设置绘图区背景色
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY); // 设置水平方向背景线颜色
		plot.setRangeGridlinesVisible(true);// 设置是否显示水平方向背景线,默认值为true
		plot.setDomainGridlinePaint(Color.LIGHT_GRAY); // 设置垂直方向背景线颜色
		plot.setDomainGridlinesVisible(true); // 设置是否显示垂直方向背景线,默认值为false

		CategoryAxis domainAxis = plot.getDomainAxis();// 取得横轴
		domainAxis.setLabelFont(font); // 设置横轴字体
		domainAxis.setTickLabelFont(font);// 设置坐标轴标尺值字体
		domainAxis.setLowerMargin(0.01);// 左边距 边框距离
		domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
		domainAxis.setMaximumCategoryLabelLines(2);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		ValueAxis rangeAxis = plot.getRangeAxis();// 取得纵轴
		rangeAxis.setLabelFont(font);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());// Y轴显示整数
		rangeAxis.setAutoRangeMinimumSize(1); // 最小跨度
		rangeAxis.setUpperMargin(0.18);// 上边距,防止最大的一个数据靠近了坐标轴。
		rangeAxis.setLowerBound(0); // 最小值显示0
		rangeAxis.setAutoRange(false); // 不自动分配Y轴数据
		rangeAxis.setTickMarkStroke(new BasicStroke(1.6f)); // 设置坐标标记大小
		rangeAxis.setTickMarkPaint(Color.BLACK); // 设置坐标标记颜色

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberAxis.setAutoRangeIncludesZero(false);
		numberAxis.setUpperMargin(0.12D);

		return jfreeChart;
	}

	@GET
	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long projectId = RequestUtils.getLong(request, "projectId");

		Integer runDay = RequestUtils.getInt(request, "runDay");

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = RequestUtils.getInt(request, "pageNo");
		limit = RequestUtils.getInt(request, "pageSize");

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		List<String> codes = getDialySqlCodes(loginContext.getActorId());

		CollectDataQuery query = new CollectDataQuery();
		query.setProjectId(projectId);
		query.setRunDay(runDay);
		query.setCodes(codes);

		int total = collectDataService.getCollectDataCount(query);
		JSONObject result = new JSONObject();
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

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<CollectData> list = collectDataService.getCollectDatasByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (CollectData collectData : list) {
					JSONObject rowJSON = new JSONObject();
					rowJSON.put("id", collectData.getId());
					rowJSON.put("databaseId", collectData.getDatabaseId());
					rowJSON.put("title", collectData.getTitle());
					rowJSON.put("code", collectData.getCode());
					rowJSON.put("name", collectData.getName());
					rowJSON.put("count", collectData.getCount());
					rowJSON.put("sqlDefId", collectData.getSqlDefId());
					rowJSON.put("runYear", collectData.getRunYear());
					rowJSON.put("runMonth", collectData.getRunMonth());
					rowJSON.put("runWeek", collectData.getRunWeek());
					rowJSON.put("runQuarter", collectData.getRunQuarter());
					rowJSON.put("runDay", collectData.getRunDay());
					rowJSON.put("createBy", collectData.getCreateBy());
					rowJSON.put("createTime", collectData.getCreateTime());

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

	@GET
	@POST
	@Path("/data4date")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data4date(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String projectId = RequestUtils.getString(request, "projectId", "all");
		Integer startRunDay = RequestUtils.getInt(request, "startRunDay");
		Integer endRunDay = RequestUtils.getInt(request, "endRunDay");

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = RequestUtils.getInt(request, "pageNo");
		limit = RequestUtils.getInt(request, "pageSize");

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		List<String> codes = getDialySqlCodes(loginContext.getActorId());

		JSONObject result = getData4Date(Long.parseLong(projectId), startRunDay, endRunDay, codes, 0,
				PageResult.DEFAULT_PAGE_SIZE);

		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/data4project")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data4project(@Context HttpServletRequest request) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String projectId = RequestUtils.getString(request, "projectId", "all");
		Integer runDay = RequestUtils.getInt(request, "runDay");
		logger.debug("查询项目ID:" + projectId + ";查询日期:" + runDay);

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = RequestUtils.getInt(request, "pageNo");
		limit = RequestUtils.getInt(request, "pageSize");

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		List<String> codes = getDialySqlCodes(loginContext.getActorId());

		List<Long> projectIds = new ArrayList<Long>();
		if (projectId.equalsIgnoreCase("all")) {
			List<Project> list = projectService.getProjects(loginContext.getActorId());
			for (Project project : list) {
				projectIds.add(project.getId());
			}
		} else {
			projectIds.add(Long.parseLong(projectId));
		}

		JSONObject result = getData4Project(projectIds, runDay, codes, 0, PageResult.DEFAULT_PAGE_SIZE);

		return result.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 导出柱状图数据到Excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/exportExcel4ColumnCharts")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportExcel4ColumnCharts(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		String projectId = RequestUtils.getString(request, "projectId", "all");
		String runDay = RequestUtils.getString(request, "runDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		List<Long> projectIds = new ArrayList<Long>();
		if ("all".equalsIgnoreCase(projectId)) {

		} else {
			projectIds.add(Long.parseLong(projectId));
		}

		SqlDefinitionQuery query = new SqlDefinitionQuery();
		query.setCodes(codes);
		List<SqlDefinition> sqlDefinitionList = sqlDefinitionService.list(query);
		List<String> titles = new ArrayList<String>();
		for (SqlDefinition model : sqlDefinitionList) {
			titles.add(model.getName());
		}

		JSONObject jobject = this.getData4Project(projectIds, Integer.parseInt(runDay), codes, 0, 100);
		JSONArray dataArray = jobject.getJSONArray("rows");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("records", dataArray);
		map.put("titles", titles);
		map.put("fields", codes);
		String projectName = dataArray.getJSONObject(0).getString("projectName");

		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = null;
		ByteArrayOutputStream baos = null;
		ByteArrayOutputStream img_baos = null;
		try {
			String templatePath = SystemProperties.getConfigRootPath() + "/conf/report/templates/daily_results_4.xlsx";

			workbook = transformer.transformXLS(new FileInputStream(templatePath), map);
			workbook.setSheetName(0, projectName);

			img_baos = new ByteArrayOutputStream();
			Sheet sheet = workbook.getSheetAt(0);

			int COL_WIDTH = 13000;
			int ROW_HEIGHT = 5000;

			int imageWidth = 115 * (titles.size() < 10 ? 10 : titles.size()), imageHeight = 410;
			BufferedImage image = createChartsImage(1, dataArray, projectName, codes, "", "", imageWidth, imageHeight);
			ImageIO.write(image, "PNG", img_baos);
			ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
			anchor.setDx1(getAnchorX(0, COL_WIDTH));
			anchor.setDy1(getAnchorY(0, ROW_HEIGHT));

			anchor.setDx2(getAnchorX(imageWidth, COL_WIDTH));
			anchor.setDy2(getAnchorY(imageHeight, ROW_HEIGHT));

			anchor.setCol1(0);// 左上角列
			anchor.setRow1(0);// 左上角行
			anchor.setCol2((titles.size() + 7) < 10 ? 10 : titles.size() + 7);// 右下角列
			anchor.setRow2(20);// 右下角行

			Drawing patriarch = sheet.createDrawingPatriarch();// 创建绘图工具对象
			patriarch.createPicture(anchor, workbook.addPicture(img_baos.toByteArray(), Workbook.PICTURE_TYPE_PNG));

			baos = new ByteArrayOutputStream();
			workbook.write(baos);
			ResponseUtils.download(request, response, baos.toByteArray(), "results.xls");
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException ex) {
				}
			}
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(img_baos);
			workbook = null;
			baos = null;
			img_baos = null;
		}
	}

	/**
	 * 按日期导出数据到Excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/exportExcel4date")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportExcel4date(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Long projectId = RequestUtils.getLong(request, "projectId");
		Integer startRunDay = RequestUtils.getInt(request, "startRunDay");
		Integer endRunDay = RequestUtils.getInt(request, "endRunDay");

		Project project = projectService.getProject(projectId);

		List<String> codes = getDialySqlCodes(loginContext.getActorId());
		String codesParam = RequestUtils.getString(request, "codes");
		if (StringUtils.isNotEmpty(codesParam)) {
			codes = Arrays.asList(codesParam.split(","));
		}

		List<String> dataTitles = new ArrayList<String>();
		List<String> titles = new ArrayList<String>();
		for (int day = startRunDay; day <= endRunDay; day++) {
			titles.add(DateUtils.getDate(DateUtils.toDate(String.valueOf(day))));
			dataTitles.add(String.valueOf("day_" + day));
		}

		JSONObject jobject = this.getData4Date(projectId, startRunDay, endRunDay, codes, 0,
				PageResult.DEFAULT_PAGE_SIZE);
		JSONArray dataArray = jobject.getJSONArray("rows");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("project", project);
		map.put("records", dataArray);
		map.put("titles", titles);
		map.put("dataTitles", dataTitles);
		map.put("startRunDay", DateUtils.getDate(DateUtils.toDate(String.valueOf(startRunDay))));
		map.put("endRunDay", DateUtils.getDate(DateUtils.toDate(String.valueOf(endRunDay))));

		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = null;
		ByteArrayOutputStream baos = null;
		try {
			String templatePath = SystemProperties.getConfigRootPath() + "/conf/report/templates/daily_results_2.xlsx";

			workbook = transformer.transformXLS(new FileInputStream(templatePath), map);
			Sheet sheet = workbook.getSheetAt(0);
			List<Long> databaseIds = projectService.getDatabaseIds(projectId);

			int row = 2;
			for (int i = databaseIds.size(); i > 0; i--) {
				sheet.addMergedRegion(new CellRangeAddress(row, codes.size() + row - 1, 0, 0));
				row = row + codes.size();
			}

			baos = new ByteArrayOutputStream();
			workbook.write(baos);

			ResponseUtils.download(request, response, baos.toByteArray(), "results.xls");
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException ex) {
				}
			}
			IOUtils.closeQuietly(baos);
			workbook = null;
			baos = null;
		}

	}

	/**
	 * 导出折线图数据到Excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/exportExcel4LineCharts")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportExcel4LineCharts(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		String databaseId = RequestUtils.getString(request, "databaseId", "all");
		String startRunDay = RequestUtils.getString(request, "startRunDay");
		String endRunDay = RequestUtils.getString(request, "endRunDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		JSONObject results = lineChartGridData(databaseId, startRunDay, endRunDay, codes);

		List<String> titles = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		Date start = DateUtils.toDate(startRunDay), end = DateUtils.toDate(endRunDay), tempDate = start;
		while (tempDate.getTime() - end.getTime() <= 0) {
			String runday = DateUtils.getDateTime("yyyyMMdd", tempDate);

			titles.add(DateUtils.getDate(tempDate));
			fields.add("_" + runday + "_");

			cal.setTime(tempDate);
			cal.add(Calendar.DATE, 1);
			tempDate = cal.getTime();
		}

		JSONArray dataArray = results.getJSONArray("rows");
		Database database = (Database) results.get("database");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("records", dataArray);
		map.put("titles", titles);
		map.put("fields", fields);

		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = null;
		ByteArrayOutputStream baos = null;
		ByteArrayOutputStream img_baos = null;
		try {
			String templatePath = SystemProperties.getConfigRootPath() + "/conf/report/templates/daily_results_3.xlsx";

			workbook = transformer.transformXLS(new FileInputStream(templatePath), map);
			workbook.setSheetName(0, database.getTitle());

			img_baos = new ByteArrayOutputStream();
			Sheet sheet = workbook.getSheetAt(0);

			int COL_WIDTH = 13000;
			int ROW_HEIGHT = 5000;

			int imageWidth = 115 * titles.size(), imageHeight = 410;
			BufferedImage image = createChartsImage(0, dataArray, database.getTitle(), titles, "", "", imageWidth,
					imageHeight);
			ImageIO.write(image, "PNG", img_baos);
			ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
			anchor.setDx1(getAnchorX(0, COL_WIDTH));
			anchor.setDy1(getAnchorY(0, ROW_HEIGHT));

			anchor.setDx2(getAnchorX(imageWidth, COL_WIDTH));
			anchor.setDy2(getAnchorY(imageHeight, ROW_HEIGHT));

			anchor.setCol1(0);// 左上角列
			anchor.setRow1(0);// 左上角行
			anchor.setCol2(titles.size() + 2);// 右下角列
			anchor.setRow2(20);// 右下角行

			Drawing patriarch = sheet.createDrawingPatriarch();// 创建绘图工具对象
			patriarch.createPicture(anchor, workbook.addPicture(img_baos.toByteArray(), Workbook.PICTURE_TYPE_PNG));

			baos = new ByteArrayOutputStream();
			workbook.write(baos);
			ResponseUtils.download(request, response, baos.toByteArray(), "results.xls");
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException ex) {
				}
			}
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(img_baos);
			workbook = null;
			baos = null;
			img_baos = null;
		}

	}

	/**
	 * 按项目导出数据到Excel
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/exportExcel4project")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public void exportExcel4project(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String projectId = RequestUtils.getString(request, "projectId", "all");
		String runDay = RequestUtils.getString(request, "runDay");

		// 基础数据处理
		String codesParam = RequestUtils.getString(request, "codes");
		List<String> codes = getDialySqlCodes(loginContext.getActorId());
		if (StringUtils.isNotEmpty(codesParam)) {
			codes = Arrays.asList(codesParam.split(","));
		}
		codes.add(0, "title");// title代表标段

		List<String> titles = new ArrayList<String>();
		titles.add("标段");
		titles.addAll(getDialySqlNames(loginContext.getActorId()));

		// List<String> titles =
		// Arrays.asList("标段,已填资料,报审数量,评定份数,树外表格,节点数量,取消节点,树外节点".split(","));

		// String[] sqlTitles = codesParam.split(",");

		// 查询所有项目
		List<Project> projectList = new ArrayList<Project>();
		if (projectId.equalsIgnoreCase("all")) {
			projectList = projectService.getProjects(loginContext.getActorId());
		} else {
			Project project = projectService.getProject(Long.parseLong(projectId));
			projectList.add(project);
		}

		// 处理Excel，分别查询后合并Sheet
		String templatePath = SystemProperties.getConfigRootPath() + "/conf/report/templates/daily_results_1.xlsx";
		InputStream inputStream = null;
		ByteArrayOutputStream baos = null;
		BufferedOutputStream bos = null;
		Workbook workbook = null;
		try {
			List<Map<String, Object>> objects = new ArrayList<Map<String, Object>>();
			List<String> newSheetNames = new ArrayList<String>();
			for (Project project : projectList) {
				List<Long> projectIds = new ArrayList<Long>();
				projectIds.add(project.getId());

				JSONObject jobject = null;
				if (loginContext.isSystemAdministrator() && StringUtils.equals(projectId, "all")) {
					jobject = this.getData4Project(projectIds, Integer.parseInt(runDay), 0,
							PageResult.DEFAULT_PAGE_SIZE);
				} else {
					jobject = this.getData4Project(projectIds, Integer.parseInt(runDay), codes, 0,
							PageResult.DEFAULT_PAGE_SIZE);
				}

				JSONArray dataArray = jobject.getJSONArray("rows");

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("projectName", project.getName());
				map.put("records", dataArray);
				map.put("titles", titles);
				map.put("sqlTitles", codes);
				map.put("runDay", DateUtils.getDate(DateUtils.toDate(runDay)));
				
				if (dataArray != null && dataArray.size() > 0) {
					map.put("createBy", dataArray.getJSONObject(0).get("createBy"));
				}

				objects.add(map);

				newSheetNames.add(project.getName());
			}

			inputStream = FileUtils.getInputStream(templatePath);
			XLSTransformer transformer = new XLSTransformer();
			workbook = transformer.transformMultipleSheetsList(inputStream, objects, newSheetNames, "model", null, 0);

			baos = new ByteArrayOutputStream();
			bos = new BufferedOutputStream(baos);
			workbook.write(bos);
			baos.flush();
			bos.flush();
			ResponseUtils.download(request, response, baos.toByteArray(), "results.xls");
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException ex) {
				}
			}
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(bos);
			workbook = null;
			baos = null;
		}
	}

	private int getAnchorX(int px, int colWidth) {
		return (int) Math.round(((double) 701 * 16000.0 / 301) * ((double) 1 / colWidth) * px);
	}

	private int getAnchorY(int px, int rowHeight) {
		return (int) Math.round(((double) 144 * 8000 / 301) * ((double) 1 / rowHeight) * px);
	}

	/**
	 * 按日期获取数据
	 * 
	 * @param projectId
	 * @param startRunDay
	 * @param endRunDay
	 * @param codes
	 * @param start
	 * @param limit
	 * @return
	 */
	private JSONObject getData4Date(Long projectId, Integer startRunDay, Integer endRunDay, List<String> codes,
			int start, int limit) {

		List<Long> databaseIds = projectService.getDatabaseIds(projectId);

		CollectDataQuery query = new CollectDataQuery();
		query.setDatabaseIds(databaseIds);
		query.setRunDayGreaterThanOrEqual(startRunDay);
		query.setRunDayLessThanOrEqual(endRunDay);
		query.setCodes(codes);
		query.setOrderBy("DATABASEID_,RUNDAY_,CODE_");
		List<CollectData> list = collectDataService.list(query);

		JSONObject result = new JSONObject();
		if (list != null && list.size() > 0) {
			result.put("total", list.size());
			result.put("totalCount", list.size());
			result.put("totalRecords", list.size());
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);

			Map<String, JSONObject> tempMap = new HashMap<String, JSONObject>();
			JSONObject rowJSON = null;
			for (CollectData data : list) {
				String key = data.getDatabaseId() + "_" + data.getCode();
				if (tempMap.get(key) != null) {
					rowJSON = tempMap.get(key);
					rowJSON.put(String.valueOf("day_" + data.getRunDay()), data.getCount());
				} else {
					rowJSON = new JSONObject();
					rowJSON.put("primaryKey", key);
					rowJSON.put("databaseId", data.getDatabaseId());
					rowJSON.put("title", data.getTitle());
					rowJSON.put("code", data.getCode());
					rowJSON.put("name", data.getName());
					rowJSON.put("day_" + data.getRunDay(), data.getCount());
					rowJSON.put("runDay", data.getRunDay());
					rowJSON.put("sqldefid", data.getSqlDefId());
					rowsJSON.add(rowJSON);
					tempMap.put(key, rowJSON);
				}

			}

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", list == null ? 0 : list.size());
		}
		return result;
	}

	/**
	 * 按项目获取数据
	 * 
	 * @param projectId
	 *            项目id
	 * @param runDay
	 *            查询日期
	 * @param start
	 *            分页开始
	 * @param limit
	 * @return
	 */
	private JSONObject getData4Project(List<Long> projectId, Integer runDay, int start, int limit) {
		List<Long> databaseIds = projectService.getDatabaseIds(projectId);

		CollectDataQuery query = new CollectDataQuery();

		query.setRunDay(runDay);

		query.setOrderBy("DATABASEID_,RUNDAY_,CODE_");

		JSONObject result = new JSONObject();
		if (databaseIds != null && databaseIds.size() > 0) {
			Collections.sort(databaseIds);
			query.setDatabaseIds(databaseIds);
			result.put("total", databaseIds.size());
			result.put("totalCount", databaseIds.size());
			result.put("totalRecords", databaseIds.size());
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			List<CollectData> list = collectDataService.list(query);

			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);

			for (Long databaseId : databaseIds) {
				JSONObject rowJSON = new JSONObject();
				rowJSON.put("databaseId", databaseId);

				if (list != null && !list.isEmpty()) {
					for (CollectData collectData : list) {
						if (collectData.getDatabaseId() == databaseId) {
							// logger.debug(">>"+collectData.getTitle());
							rowJSON.put("title", collectData.getTitle());
							rowJSON.put("createBy", collectData.getCreateBy());
							rowJSON.put("projectName", collectData.getProjectName());
							rowJSON.put(collectData.getCode(), collectData.getCount());
							rowJSON.put(collectData.getCode() + "_name", collectData.getName());
						}
					}
				}
				rowsJSON.add(rowJSON);
			}

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", databaseIds == null ? 0 : databaseIds.size());
		}
		return result;
	}

	/**
	 * 按项目获取数据
	 * 
	 * @param projectId
	 *            项目id
	 * @param runDay
	 *            查询日期
	 * @param start
	 *            分页开始
	 * @param limit
	 * @return
	 */
	private JSONObject getData4Project(List<Long> projectId, Integer runDay, List<String> codes, int start, int limit) {
		List<Long> databaseIds = projectService.getDatabaseIds(projectId);

		CollectDataQuery query = new CollectDataQuery();

		query.setRunDay(runDay);
		query.setCodes(codes);
		query.setOrderBy("DATABASEID_,RUNDAY_,CODE_");

		JSONObject result = new JSONObject();
		if (databaseIds != null && databaseIds.size() > 0) {
			Collections.sort(databaseIds);
			query.setDatabaseIds(databaseIds);
			result.put("total", databaseIds.size());
			result.put("totalCount", databaseIds.size());
			result.put("totalRecords", databaseIds.size());
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			List<CollectData> list = collectDataService.list(query);

			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);

			for (Long databaseId : databaseIds) {
				JSONObject rowJSON = new JSONObject();
				rowJSON.put("databaseId", databaseId);

				if (list != null && !list.isEmpty()) {
					for (CollectData collectData : list) {
						if (collectData.getDatabaseId() == databaseId) {
							// logger.debug(">>"+collectData.getTitle());
							rowJSON.put("title", collectData.getTitle());
							rowJSON.put("createBy", collectData.getCreateBy());
							rowJSON.put("projectName", collectData.getProjectName());
							rowJSON.put(collectData.getCode(), collectData.getCount());
							rowJSON.put(collectData.getCode() + "_name", collectData.getName());
						}
					}
				}
				rowsJSON.add(rowJSON);
			}

		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", databaseIds == null ? 0 : databaseIds.size());
		}
		return result;
	}

	/**
	 * 获取折线图Grid数据
	 * 
	 * @param databaseId
	 * @param startRunDay
	 * @param endRunDay
	 * @param codes
	 * @return
	 */
	private JSONObject lineChartGridData(String databaseId, String startRunDay, String endRunDay, List<String> codes) {
		CollectDataQuery query = new CollectDataQuery();
		if ("all".equalsIgnoreCase(databaseId)) {

		} else {
			query.setDatabaseId(Long.parseLong(databaseId));
		}
		query.setRunDayGreaterThanOrEqual(Integer.parseInt(startRunDay));
		query.setRunDayLessThanOrEqual(Integer.parseInt(endRunDay));
		query.setCodes(codes);
		query.setOrderBy("RUNDAY_,CODE_");
		List<CollectData> list = collectDataService.list(query);

		int total = 0;
		JSONObject results = new JSONObject();
		JSONArray rows = new JSONArray();
		JSONObject jobject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			CollectData model = list.get(i);

			JSONObject obj = jobject.getJSONObject(model.getCode());
			if (obj == null) {
				obj = new JSONObject();
				rows.add(obj);
				total++;
			}

			int max = 0, min = 0;
			if (obj.containsKey("max")) {
				max = obj.getIntValue("max");
			} else {
				max = model.getCount();
			}
			if (obj.containsKey("min")) {
				min = obj.getIntValue("min");
			} else {
				min = model.getCount();
			}

			max = max > model.getCount() ? max : model.getCount();
			min = min < model.getCount() ? min : model.getCount();

			obj.put("_" + model.getRunDay() + "_", model.getCount());
			obj.put("name", model.getName());
			obj.put("code", model.getCode());
			obj.put("max", max);
			obj.put("min", min);
			obj.put("difference", max - min);

			jobject.put(model.getCode(), obj);
		}
		results.put("rows", rows);
		results.put("total", total);

		Database database = databaseService.getDatabaseById(Long.parseLong(databaseId));
		results.put("database", database);

		return results;
	}

	/**
	 * 折线图数据获取
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@GET
	@POST
	@Path("/lineCharts")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] lineCharts(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		String startRunDay = RequestUtils.getString(request, "startRunDay");
		String endRunDay = RequestUtils.getString(request, "endRunDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		CollectDataQuery query = new CollectDataQuery();
		query.setDatabaseId(databaseId);
		query.setRunDayGreaterThanOrEqual(Integer.parseInt(startRunDay));
		query.setRunDayLessThanOrEqual(Integer.parseInt(endRunDay));
		query.setCodes(codes);
		query.setOrderBy("RUNDAY_,CODE_");
		List<CollectData> list = collectDataService.list(query);

		JSONObject jobject = new JSONObject();
		for (int i = 0; i < list.size(); i++) {
			CollectData model = list.get(i);

			JSONObject obj = jobject.getJSONObject(String.valueOf(model.getRunDay()));
			if (obj == null) {
				obj = new JSONObject();
			}

			obj.put("runDay", model.getRunDay());
			obj.put(model.getCode(), model.getCount());

			jobject.put(String.valueOf(model.getRunDay()), obj);
		}

		Calendar cal = Calendar.getInstance();
		Date start = DateUtils.toDate(startRunDay), end = DateUtils.toDate(endRunDay), tempDate = start;
		JSONArray result = new JSONArray();
		while (tempDate.getTime() - end.getTime() <= 0) {
			String runday = DateUtils.getDateTime("yyyyMMdd", tempDate);

			JSONObject rs = jobject.getJSONObject(runday);
			if (rs == null) {
				JSONObject obj = new JSONObject();
				obj.put("runDay", runday);
				result.add(obj);
			} else {
				result.add(rs);
			}

			cal.setTime(tempDate);
			cal.add(Calendar.DATE, 1);
			tempDate = cal.getTime();
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/lineChartsGrid")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] lineChartsGrid(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException {
		String databaseId = RequestUtils.getString(request, "databaseId");
		String startRunDay = RequestUtils.getString(request, "startRunDay");
		String endRunDay = RequestUtils.getString(request, "endRunDay");
		String codeParams = RequestUtils.getString(request, "codes");
		List<String> codes = Arrays.asList(codeParams.split(","));

		JSONObject results = lineChartGridData(databaseId, startRunDay, endRunDay, codes);

		return results.toJSONString().getBytes("UTF-8");
	}

	@Resource
	public void setCollectDataService(CollectDataService collectDataService) {
		this.collectDataService = collectDataService;
	}

	@Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Resource
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	@Resource
	public void setSqlDefinitionService(SqlDefinitionService sqlDefinitionService) {
		this.sqlDefinitionService = sqlDefinitionService;
	}

}
