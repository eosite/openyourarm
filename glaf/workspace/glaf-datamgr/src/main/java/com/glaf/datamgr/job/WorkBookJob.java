package com.glaf.datamgr.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.job.BaseJob;
import com.glaf.core.util.DateUtils;

/**
 * 生成排班任务(目前只生成下个月的日期数据)
 * 
 * @author klaus.wang
 *
 */
public class WorkBookJob extends BaseJob {

	protected static final Log logger = LogFactory.getLog(WorkBookJob.class);

	protected JobExecutionContext context;

	protected String tableName = "", mField = "", wField = "", idColumn = "id";

	public void runJob(JobExecutionContext context) throws JobExecutionException {
		this.context = context;
		try {

			QueryRunner qr = new QueryRunner(DBConnectionFactory.getDataSource(), true);

			this.init(qr);

			Boolean isCreated = this.isCreated(qr);

			if (isCreated) {
				return;
			}

			this.autoCreate(qr);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 初始化参数信息
	 * 
	 * @param qr
	 * @throws SQLException
	 */
	protected void init(QueryRunner qr) throws SQLException {

		String t = "cell_useradd10931", m = "cell_useradd10931_user1", tk = "tableName", mk = "mField";
		
		Map<String, Object> parameters = context.getJobDetail().getJobDataMap();

		if (parameters.containsKey(tk) && parameters.containsKey(mk)) {

			JSONObject json = new JSONObject(parameters), o;

			o = json.getJSONObject(tk);
			
			this.tableName = MapUtils.getString(o, "stringVal", t);

			o = json.getJSONObject(mk);
			
			this.mField = MapUtils.getString(o, "stringVal", m);

			return;
		}

		Map<String, Map<String, Object>> map = this.getDictoryByCode("workBookJobSets", qr);


		if (map == null) {
			this.tableName = t;

			this.mField = m;

			return;
		}

		Map<String, Object> def = new HashMap<>(), o = map.getOrDefault(tk, def);

		this.tableName = MapUtils.getString(o, "VALUE_", t);

		o = map.getOrDefault(mk, def);
		this.mField = MapUtils.getString(o, "VALUE_", m);

	}

	/**
	 * 获取字典信息
	 * 
	 * @param code
	 * @param qr
	 * @return
	 * @throws SQLException
	 */
	protected Map<String, Map<String, Object>> getDictoryByCode(String code, QueryRunner qr) throws SQLException {

		String sql = "select * from sys_dictory WHERE TYPEID IN (SELECT id FROM sys_tree WHERE CODE = ?)";

		List<Map<String, Object>> list = qr.query(sql, new MapListHandler(), new Object[] { code });

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Map<String, Map<String, Object>> map = new HashMap<>();
		for (Map<String, Object> m : list) {
			map.put(MapUtils.getString(m, "code"), m);
		}
		return map;

	}

	/**
	 * 判断下个月排班是否创建
	 * 
	 * @param qr
	 * @return
	 * @throws SQLException
	 */
	protected Boolean isCreated(QueryRunner qr) throws SQLException {

		String sql = MessageFormat.format("SELECT * FROM  {0} WHERE {1} >= ?", this.tableName, this.mField);

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 2;
		if (month > 12) {
			year += 1;
			month = 1;
		}

		String firstDate = year + "-" + new DecimalFormat("00").format(month) + "-01";// 下个月第一天

		return qr.query(sql, new ResultSetHandler<Boolean>() {
			public Boolean handle(ResultSet rs) throws SQLException {
				return rs != null && rs.next();
			}
		}, new Object[] { firstDate });
	}

	/**
	 * 生成数据
	 * 
	 * @param conn
	 */
	protected void autoCreate(QueryRunner qr) throws SQLException {

		IdGenerator idGenerator = ContextFactory.getBean("idGenerator");

		String sql = MessageFormat.format("INSERT INTO {0} ({1}, {2}) VALUES (?, ?)", this.tableName, this.idColumn,
				this.mField);

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 2;// 获取下个月
		if (month > 12) {
			year += 1;
			month = 1;
		}
		int days = DateUtils.getYearMonthDays(year, month), index = 1;

		try {

			String id;
			Object date;
			Object params[][] = new Object[days][2];
			for (; index <= days; index++) {
				id = idGenerator.getNextId(this.tableName, this.idColumn, "admin");

				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, month - 1);
				cal.set(Calendar.DAY_OF_MONTH, index);

				date = new java.sql.Date(cal.getTimeInMillis());

				params[index - 1] = new Object[] { id, date };
			}

			qr.batch(sql, params);
		} catch (Exception ex) {
			throw ex;
		}

	}

	public static void main(String[] args) {

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH) + 2;
		if (month > 12) {
			year += 1;
			month = 1;
		}
		int days = DateUtils.getYearMonthDays(year, month), index = 1;
		Calendar cal = Calendar.getInstance();
		for (; index <= days; index++) {
			System.out.println(index);

			// System.out.println(new Date(year - 1900, month, index));

			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, index);

			System.out.println(cal.getTime() + " ;" + cal.getTimeInMillis() + "; " + cal.getTime().getTime());

		}
	}

}
