package com.glaf.isdp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.JdbcUtils;

import com.glaf.isdp.domain.TreepInfo;
import com.glaf.isdp.domain.TreepInfoCount;
import com.glaf.isdp.mapper.TreepInfoMapper;
import com.glaf.isdp.query.TreepInfoQuery;
import com.glaf.isdp.service.TreepInfoService;

@Service("com.glaf.isdp.service.treepInfoService")
@Transactional(readOnly = true)
public class TreepInfoServiceImpl implements TreepInfoService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreepInfoMapper treepInfoMapper;

	protected ITableDataService tableDataService;

	public TreepInfoServiceImpl() {

	}

	public int count(TreepInfoQuery query) {
		return treepInfoMapper.getTreepInfoCount(query);
	}

	/**
	 * 统计WBS结构
	 * 
	 * @param projtype
	 * @return
	 */
	public int countProjectType(String projtype) {
		return treepInfoMapper.countProjectType(projtype);
	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			treepInfoMapper.deleteTreepInfoById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> indexIds) {
		if (indexIds != null && !indexIds.isEmpty()) {
			for (Integer id : indexIds) {
				treepInfoMapper.deleteTreepInfoById(id);
			}
		}
	}

	@Override
	public List<TreepInfo> getAllWBSTreepInfos(TreepInfoQuery query) {
		return treepInfoMapper.getAllWBSTreepInfos(query);
	}

	@Override
	public List<TreepInfo> getCellData(TreepInfoQuery query) {
		return this.treepInfoMapper.getCellData(query);
	}

	@Override
	public List<TreepInfo> getFirstSubItem(TreepInfoQuery query) {
		return treepInfoMapper.getFirstSubItem(query);
	}

	@Override
	public List<TreepInfo> getFirstSubItemByCriteria(int start, int pageSize, String idLike) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreepInfo> rows = sqlSessionTemplate.selectList("getFirstSubItem", idLike, rowBounds);
		return rows;
	}

	@Override
	public List<TreepInfo> getLastSubSection(TreepInfoQuery query) {
		return treepInfoMapper.getLastSubSection(query);
	}

	public TreepInfo getTreepInfo(Integer id) {
		if (id == null) {
			return null;
		}
		TreepInfo treepInfo = treepInfoMapper.getTreepInfoById(id);
		return treepInfo;
	}

	@Override
	public List<TreepInfo> getTreepInfoByProjType(String projtype) {
		return this.treepInfoMapper.getTreepInfoByProjType(projtype);
	}

	@Override
	public List<TreepInfo> getTreepInfoByTreedotIndexId(int parentId, String treedotIndexId) {
		return this.treepInfoMapper.getTreepInfoByTreedotIndexId(parentId, treedotIndexId);
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreepInfoCountByQueryCriteria(TreepInfoQuery query) {
		return treepInfoMapper.getTreepInfoCount(query);
	}

	@Override
	public List<TreepInfo> getTreepInfoListByParentId(Integer parentId) {
		return this.treepInfoMapper.getTreepInfoListByParentId(parentId);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreepInfo> getTreepInfosByQueryCriteria(int start, int pageSize, TreepInfoQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreepInfo> rows = sqlSessionTemplate.selectList("getTreepInfos", query, rowBounds);
		return rows;
	}

	@Override
	public List<TreepInfo> getUnitList() {
		return treepInfoMapper.getTreepInfoByProjType("1");
	}

	@Transactional
	public void insertAll(String type, List<TreepInfoCount> rows) {
		treepInfoMapper.deleteTreepInfoCountByType(type);

		if (rows != null && !rows.isEmpty()) {
			for (TreepInfoCount bean : rows) {
				TableModel tableModel = new TableModel();
				tableModel.setTableName("TREEPINFO_COUNT");

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH) + 1;
				int week = calendar.get(Calendar.WEEK_OF_YEAR);

				bean.setRunYear(year);
				bean.setRunMonth(month);
				bean.setRunWeek(week);
				bean.setRunDay(DateUtils.getNowYearMonthDay());

				if (month <= 3) {
					bean.setRunQuarter(1);
				} else if (month > 3 && month <= 6) {
					bean.setRunQuarter(2);
				}
				if (month > 6 && month <= 9) {
					bean.setRunQuarter(3);
				}
				if (month > 9) {
					bean.setRunQuarter(4);
				}

				ColumnModel idColumn = new ColumnModel();
				idColumn.setColumnName("ID_");
				idColumn.setJavaType("Long");
				idColumn.setValue(idGenerator.nextId("TREEPINFO_COUNT"));
				tableModel.addColumn(idColumn);

				ColumnModel typeColumn = new ColumnModel();
				typeColumn.setColumnName("TYPE_");
				typeColumn.setJavaType("String");
				typeColumn.setValue(type);
				tableModel.addColumn(typeColumn);

				ColumnModel databaseIdColumn = new ColumnModel();
				databaseIdColumn.setColumnName("DATABASEID_");
				databaseIdColumn.setJavaType("Long");
				databaseIdColumn.setValue(bean.getDatabaseId());
				tableModel.addColumn(databaseIdColumn);

				ColumnModel mappingColumn = new ColumnModel();
				mappingColumn.setColumnName("MAPPING_");
				mappingColumn.setJavaType("String");
				mappingColumn.setValue(bean.getMapping());
				tableModel.addColumn(mappingColumn);

				ColumnModel titleColumn = new ColumnModel();
				titleColumn.setColumnName("TITLE_");
				titleColumn.setJavaType("String");
				titleColumn.setValue(bean.getMapping());
				tableModel.addColumn(titleColumn);

				ColumnModel intValue1Column = new ColumnModel();
				intValue1Column.setColumnName("INTVALUE1_");
				intValue1Column.setJavaType("Integer");
				intValue1Column.setValue(bean.getIntValue1());
				tableModel.addColumn(intValue1Column);

				ColumnModel intValue2Column = new ColumnModel();
				intValue2Column.setColumnName("INTVALUE2_");
				intValue2Column.setJavaType("Integer");
				intValue2Column.setValue(bean.getIntValue2());
				tableModel.addColumn(intValue2Column);

				ColumnModel intValue3Column = new ColumnModel();
				intValue3Column.setColumnName("INTVALUE3_");
				intValue3Column.setJavaType("Integer");
				intValue3Column.setValue(bean.getIntValue3());
				tableModel.addColumn(intValue3Column);

				ColumnModel intValue4Column = new ColumnModel();
				intValue4Column.setColumnName("INTVALUE4_");
				intValue4Column.setJavaType("Integer");
				intValue4Column.setValue(bean.getIntValue4());
				tableModel.addColumn(intValue4Column);

				ColumnModel intValue5Column = new ColumnModel();
				intValue5Column.setColumnName("INTVALUE5_");
				intValue5Column.setJavaType("Integer");
				intValue5Column.setValue(bean.getIntValue5());
				tableModel.addColumn(intValue5Column);

				ColumnModel intValue6Column = new ColumnModel();
				intValue6Column.setColumnName("INTVALUE6_");
				intValue6Column.setJavaType("Integer");
				intValue6Column.setValue(bean.getIntValue6());
				tableModel.addColumn(intValue6Column);

				ColumnModel intValue7Column = new ColumnModel();
				intValue7Column.setColumnName("INTVALUE7_");
				intValue7Column.setJavaType("Integer");
				intValue7Column.setValue(bean.getIntValue7());
				tableModel.addColumn(intValue7Column);

				ColumnModel intValue8Column = new ColumnModel();
				intValue8Column.setColumnName("INTVALUE8_");
				intValue8Column.setJavaType("Integer");
				intValue8Column.setValue(bean.getIntValue8());
				tableModel.addColumn(intValue8Column);

				ColumnModel intValue9Column = new ColumnModel();
				intValue9Column.setColumnName("INTVALUE9_");
				intValue9Column.setJavaType("Integer");
				intValue9Column.setValue(bean.getIntValue9());
				tableModel.addColumn(intValue9Column);

				ColumnModel intValue10Column = new ColumnModel();
				intValue10Column.setColumnName("INTVALUE10_");
				intValue10Column.setJavaType("Integer");
				intValue10Column.setValue(bean.getIntValue10());
				tableModel.addColumn(intValue10Column);

				ColumnModel longValue1Column = new ColumnModel();
				longValue1Column.setColumnName("LONGVALUE1_");
				longValue1Column.setJavaType("Long");
				longValue1Column.setValue(bean.getLongValue1());
				tableModel.addColumn(longValue1Column);

				ColumnModel longValue2Column = new ColumnModel();
				longValue2Column.setColumnName("LONGVALUE2_");
				longValue2Column.setJavaType("Long");
				longValue2Column.setValue(bean.getLongValue2());
				tableModel.addColumn(longValue2Column);

				ColumnModel longValue3Column = new ColumnModel();
				longValue3Column.setColumnName("LONGVALUE3_");
				longValue3Column.setJavaType("Long");
				longValue3Column.setValue(bean.getLongValue3());
				tableModel.addColumn(longValue3Column);

				ColumnModel longValue4Column = new ColumnModel();
				longValue4Column.setColumnName("LONGVALUE4_");
				longValue4Column.setJavaType("Long");
				longValue4Column.setValue(bean.getLongValue4());
				tableModel.addColumn(longValue4Column);

				ColumnModel longValue5Column = new ColumnModel();
				longValue5Column.setColumnName("LONGVALUE5_");
				longValue5Column.setJavaType("Long");
				longValue5Column.setValue(bean.getLongValue5());
				tableModel.addColumn(longValue5Column);

				ColumnModel longValue6Column = new ColumnModel();
				longValue6Column.setColumnName("LONGVALUE6_");
				longValue6Column.setJavaType("Long");
				longValue6Column.setValue(bean.getLongValue6());
				tableModel.addColumn(longValue6Column);

				ColumnModel longValue7Column = new ColumnModel();
				longValue7Column.setColumnName("LONGVALUE7_");
				longValue7Column.setJavaType("Long");
				longValue7Column.setValue(bean.getLongValue7());
				tableModel.addColumn(longValue7Column);

				ColumnModel longValue8Column = new ColumnModel();
				longValue8Column.setColumnName("LONGVALUE8_");
				longValue8Column.setJavaType("Long");
				longValue8Column.setValue(bean.getLongValue8());
				tableModel.addColumn(longValue8Column);

				ColumnModel longValue9Column = new ColumnModel();
				longValue9Column.setColumnName("LONGVALUE9_");
				longValue9Column.setJavaType("Long");
				longValue9Column.setValue(bean.getLongValue9());
				tableModel.addColumn(longValue9Column);

				ColumnModel longValue10Column = new ColumnModel();
				longValue10Column.setColumnName("LONGVALUE10_");
				longValue10Column.setJavaType("Long");
				longValue10Column.setValue(bean.getLongValue10());
				tableModel.addColumn(longValue10Column);

				ColumnModel doubleValue1Column = new ColumnModel();
				doubleValue1Column.setColumnName("DOUBLEVALUE1_");
				doubleValue1Column.setJavaType("Double");
				doubleValue1Column.setValue(bean.getDoubleValue1());
				tableModel.addColumn(doubleValue1Column);

				ColumnModel doubleValue2Column = new ColumnModel();
				doubleValue2Column.setColumnName("DOUBLEVALUE2_");
				doubleValue2Column.setJavaType("Double");
				doubleValue2Column.setValue(bean.getDoubleValue2());
				tableModel.addColumn(doubleValue2Column);

				ColumnModel doubleValue3Column = new ColumnModel();
				doubleValue3Column.setColumnName("DOUBLEVALUE3_");
				doubleValue3Column.setJavaType("Double");
				doubleValue3Column.setValue(bean.getDoubleValue3());
				tableModel.addColumn(doubleValue3Column);

				ColumnModel doubleValue4Column = new ColumnModel();
				doubleValue4Column.setColumnName("DOUBLEVALUE4_");
				doubleValue4Column.setJavaType("Double");
				doubleValue4Column.setValue(bean.getDoubleValue4());
				tableModel.addColumn(doubleValue4Column);

				ColumnModel doubleValue5Column = new ColumnModel();
				doubleValue5Column.setColumnName("DOUBLEVALUE5_");
				doubleValue5Column.setJavaType("Double");
				doubleValue5Column.setValue(bean.getDoubleValue5());
				tableModel.addColumn(doubleValue5Column);

				ColumnModel doubleValue6Column = new ColumnModel();
				doubleValue6Column.setColumnName("DOUBLEVALUE6_");
				doubleValue6Column.setJavaType("Double");
				doubleValue6Column.setValue(bean.getDoubleValue6());
				tableModel.addColumn(doubleValue6Column);

				ColumnModel doubleValue7Column = new ColumnModel();
				doubleValue7Column.setColumnName("DOUBLEVALUE7_");
				doubleValue7Column.setJavaType("Double");
				doubleValue7Column.setValue(bean.getDoubleValue7());
				tableModel.addColumn(doubleValue7Column);

				ColumnModel doubleValue8Column = new ColumnModel();
				doubleValue8Column.setColumnName("DOUBLEVALUE8_");
				doubleValue8Column.setJavaType("Double");
				doubleValue8Column.setValue(bean.getDoubleValue8());
				tableModel.addColumn(doubleValue8Column);

				ColumnModel doubleValue9Column = new ColumnModel();
				doubleValue9Column.setColumnName("DOUBLEVALUE9_");
				doubleValue9Column.setJavaType("Double");
				doubleValue9Column.setValue(bean.getDoubleValue9());
				tableModel.addColumn(doubleValue9Column);

				ColumnModel doubleValue10Column = new ColumnModel();
				doubleValue10Column.setColumnName("DOUBLEVALUE10_");
				doubleValue10Column.setJavaType("Double");
				doubleValue10Column.setValue(bean.getDoubleValue10());
				tableModel.addColumn(doubleValue10Column);

				ColumnModel stringValue1Column = new ColumnModel();
				stringValue1Column.setColumnName("STRINGVALUE1_");
				stringValue1Column.setJavaType("String");
				stringValue1Column.setValue(bean.getStringValue1());
				tableModel.addColumn(stringValue1Column);

				ColumnModel stringValue2Column = new ColumnModel();
				stringValue2Column.setColumnName("STRINGVALUE2_");
				stringValue2Column.setJavaType("String");
				stringValue2Column.setValue(bean.getStringValue2());
				tableModel.addColumn(stringValue2Column);

				ColumnModel stringValue3Column = new ColumnModel();
				stringValue3Column.setColumnName("STRINGVALUE3_");
				stringValue3Column.setJavaType("String");
				stringValue3Column.setValue(bean.getStringValue3());
				tableModel.addColumn(stringValue3Column);

				ColumnModel stringValue4Column = new ColumnModel();
				stringValue4Column.setColumnName("STRINGVALUE4_");
				stringValue4Column.setJavaType("String");
				stringValue4Column.setValue(bean.getStringValue4());
				tableModel.addColumn(stringValue4Column);

				ColumnModel stringValue5Column = new ColumnModel();
				stringValue5Column.setColumnName("STRINGVALUE5_");
				stringValue5Column.setJavaType("String");
				stringValue5Column.setValue(bean.getStringValue5());
				tableModel.addColumn(stringValue5Column);

				ColumnModel stringValue6Column = new ColumnModel();
				stringValue6Column.setColumnName("STRINGVALUE6_");
				stringValue6Column.setJavaType("String");
				stringValue6Column.setValue(bean.getStringValue6());
				tableModel.addColumn(stringValue6Column);

				ColumnModel stringValue7Column = new ColumnModel();
				stringValue7Column.setColumnName("STRINGVALUE7_");
				stringValue7Column.setJavaType("String");
				stringValue7Column.setValue(bean.getStringValue7());
				tableModel.addColumn(stringValue7Column);

				ColumnModel stringValue8Column = new ColumnModel();
				stringValue8Column.setColumnName("STRINGVALUE8_");
				stringValue8Column.setJavaType("String");
				stringValue8Column.setValue(bean.getStringValue8());
				tableModel.addColumn(stringValue8Column);

				ColumnModel stringValue9Column = new ColumnModel();
				stringValue9Column.setColumnName("STRINGVALUE9_");
				stringValue9Column.setJavaType("String");
				stringValue9Column.setValue(bean.getStringValue9());
				tableModel.addColumn(stringValue9Column);

				ColumnModel stringValue10Column = new ColumnModel();
				stringValue10Column.setColumnName("STRINGVALUE10_");
				stringValue10Column.setJavaType("String");
				stringValue10Column.setValue(bean.getStringValue10());
				tableModel.addColumn(stringValue10Column);

				ColumnModel runYearColumn = new ColumnModel();
				runYearColumn.setColumnName("RUNYEAR_");
				runYearColumn.setJavaType("Integer");
				runYearColumn.setValue(bean.getRunYear());
				tableModel.addColumn(runYearColumn);

				ColumnModel runMonthColumn = new ColumnModel();
				runMonthColumn.setColumnName("RUNMONTH_");
				runMonthColumn.setJavaType("Integer");
				runMonthColumn.setValue(bean.getRunMonth());
				tableModel.addColumn(runMonthColumn);

				ColumnModel runWeekColumn = new ColumnModel();
				runWeekColumn.setColumnName("RUNWEEK_");
				runWeekColumn.setJavaType("Integer");
				runWeekColumn.setValue(bean.getRunWeek());
				tableModel.addColumn(runWeekColumn);

				ColumnModel runQuarterColumn = new ColumnModel();
				runQuarterColumn.setColumnName("RUNQUARTER_");
				runQuarterColumn.setJavaType("Integer");
				runQuarterColumn.setValue(bean.getRunQuarter());
				tableModel.addColumn(runQuarterColumn);

				ColumnModel runDayColumn = new ColumnModel();
				runDayColumn.setColumnName("RUNDAY_");
				runDayColumn.setJavaType("Integer");
				runDayColumn.setValue(bean.getRunDay());
				tableModel.addColumn(runDayColumn);

				ColumnModel jobNoColumn = new ColumnModel();
				jobNoColumn.setColumnName("JOBNO_");
				jobNoColumn.setJavaType("String");
				jobNoColumn.setValue(bean.getJobNo());
				tableModel.addColumn(jobNoColumn);

				tableDataService.insertTableData(tableModel);
			}
		}
	}

	public List<TreepInfo> list(TreepInfoQuery query) {
		List<TreepInfo> list = treepInfoMapper.getTreepInfos(query);
		return list;
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@Transactional
	public void save(TreepInfo treepInfo) {
		if (treepInfo.getIndexId() == null) {
			treepInfo.setIndexId(idGenerator.nextId("TREEPINFO").intValue());
			// treepInfo.setCreateDate(new Date());
			// treepInfo.setDeleteFlag(0);
			treepInfoMapper.insertTreepInfo(treepInfo);
		} else {
			treepInfoMapper.updateTreepInfo(treepInfo);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Resource
	public void setTableDataService(ITableDataService tableDataService) {
		this.tableDataService = tableDataService;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.TreepInfoMapper")
	public void setTreepInfoMapper(TreepInfoMapper treepInfoMapper) {
		this.treepInfoMapper = treepInfoMapper;
	}

	@Override
	public int validateHasChildrens(Integer index_id, String projType) {
		return treepInfoMapper.validateHasChildrens(index_id, projType);
	}
}
