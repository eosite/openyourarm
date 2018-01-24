package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CvtRuntimeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> taskCodes;
	protected Collection<String> appActorIds;
	protected String taskName;
	protected String taskNameLike;
	protected List<String> taskNames;
	protected Date runTimeGreaterThanOrEqual;
	protected Date runTimeLessThanOrEqual;
	protected Integer runFlag;
	protected Integer runFlagGreaterThanOrEqual;
	protected Integer runFlagLessThanOrEqual;
	protected List<Integer> runFlags;
	protected Integer succCount;
	protected Integer succCountGreaterThanOrEqual;
	protected Integer succCountLessThanOrEqual;
	protected List<Integer> succCounts;
	protected Integer failCount;
	protected Integer failCountGreaterThanOrEqual;
	protected Integer failCountLessThanOrEqual;
	protected List<Integer> failCounts;

	public CvtRuntimeQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getTaskNameLike() {
		if (taskNameLike != null && taskNameLike.trim().length() > 0) {
			if (!taskNameLike.startsWith("%")) {
				taskNameLike = "%" + taskNameLike;
			}
			if (!taskNameLike.endsWith("%")) {
				taskNameLike = taskNameLike + "%";
			}
		}
		return taskNameLike;
	}

	public List<String> getTaskNames() {
		return taskNames;
	}

	public Date getRunTimeGreaterThanOrEqual() {
		return runTimeGreaterThanOrEqual;
	}

	public Date getRunTimeLessThanOrEqual() {
		return runTimeLessThanOrEqual;
	}

	public Integer getRunFlag() {
		return runFlag;
	}

	public Integer getRunFlagGreaterThanOrEqual() {
		return runFlagGreaterThanOrEqual;
	}

	public Integer getRunFlagLessThanOrEqual() {
		return runFlagLessThanOrEqual;
	}

	public List<Integer> getRunFlags() {
		return runFlags;
	}

	public Integer getSuccCount() {
		return succCount;
	}

	public Integer getSuccCountGreaterThanOrEqual() {
		return succCountGreaterThanOrEqual;
	}

	public Integer getSuccCountLessThanOrEqual() {
		return succCountLessThanOrEqual;
	}

	public List<Integer> getSuccCounts() {
		return succCounts;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public Integer getFailCountGreaterThanOrEqual() {
		return failCountGreaterThanOrEqual;
	}

	public Integer getFailCountLessThanOrEqual() {
		return failCountLessThanOrEqual;
	}

	public List<Integer> getFailCounts() {
		return failCounts;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setTaskNames(List<String> taskNames) {
		this.taskNames = taskNames;
	}

	public void setRunTimeGreaterThanOrEqual(Date runTimeGreaterThanOrEqual) {
		this.runTimeGreaterThanOrEqual = runTimeGreaterThanOrEqual;
	}

	public void setRunTimeLessThanOrEqual(Date runTimeLessThanOrEqual) {
		this.runTimeLessThanOrEqual = runTimeLessThanOrEqual;
	}

	public void setRunFlag(Integer runFlag) {
		this.runFlag = runFlag;
	}

	public void setRunFlagGreaterThanOrEqual(Integer runFlagGreaterThanOrEqual) {
		this.runFlagGreaterThanOrEqual = runFlagGreaterThanOrEqual;
	}

	public void setRunFlagLessThanOrEqual(Integer runFlagLessThanOrEqual) {
		this.runFlagLessThanOrEqual = runFlagLessThanOrEqual;
	}

	public void setRunFlags(List<Integer> runFlags) {
		this.runFlags = runFlags;
	}

	public void setSuccCount(Integer succCount) {
		this.succCount = succCount;
	}

	public void setSuccCountGreaterThanOrEqual(Integer succCountGreaterThanOrEqual) {
		this.succCountGreaterThanOrEqual = succCountGreaterThanOrEqual;
	}

	public void setSuccCountLessThanOrEqual(Integer succCountLessThanOrEqual) {
		this.succCountLessThanOrEqual = succCountLessThanOrEqual;
	}

	public void setSuccCounts(List<Integer> succCounts) {
		this.succCounts = succCounts;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public void setFailCountGreaterThanOrEqual(Integer failCountGreaterThanOrEqual) {
		this.failCountGreaterThanOrEqual = failCountGreaterThanOrEqual;
	}

	public void setFailCountLessThanOrEqual(Integer failCountLessThanOrEqual) {
		this.failCountLessThanOrEqual = failCountLessThanOrEqual;
	}

	public void setFailCounts(List<Integer> failCounts) {
		this.failCounts = failCounts;
	}

	public CvtRuntimeQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public CvtRuntimeQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public CvtRuntimeQuery taskNames(List<String> taskNames) {
		if (taskNames == null) {
			throw new RuntimeException("taskNames is empty ");
		}
		this.taskNames = taskNames;
		return this;
	}

	public CvtRuntimeQuery runTimeGreaterThanOrEqual(Date runTimeGreaterThanOrEqual) {
		if (runTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("runTime is null");
		}
		this.runTimeGreaterThanOrEqual = runTimeGreaterThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery runTimeLessThanOrEqual(Date runTimeLessThanOrEqual) {
		if (runTimeLessThanOrEqual == null) {
			throw new RuntimeException("runTime is null");
		}
		this.runTimeLessThanOrEqual = runTimeLessThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery runFlag(Integer runFlag) {
		if (runFlag == null) {
			throw new RuntimeException("runFlag is null");
		}
		this.runFlag = runFlag;
		return this;
	}

	public CvtRuntimeQuery runFlagGreaterThanOrEqual(Integer runFlagGreaterThanOrEqual) {
		if (runFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("runFlag is null");
		}
		this.runFlagGreaterThanOrEqual = runFlagGreaterThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery runFlagLessThanOrEqual(Integer runFlagLessThanOrEqual) {
		if (runFlagLessThanOrEqual == null) {
			throw new RuntimeException("runFlag is null");
		}
		this.runFlagLessThanOrEqual = runFlagLessThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery runFlags(List<Integer> runFlags) {
		if (runFlags == null) {
			throw new RuntimeException("runFlags is empty ");
		}
		this.runFlags = runFlags;
		return this;
	}

	public CvtRuntimeQuery succCount(Integer succCount) {
		if (succCount == null) {
			throw new RuntimeException("succCount is null");
		}
		this.succCount = succCount;
		return this;
	}

	public CvtRuntimeQuery succCountGreaterThanOrEqual(Integer succCountGreaterThanOrEqual) {
		if (succCountGreaterThanOrEqual == null) {
			throw new RuntimeException("succCount is null");
		}
		this.succCountGreaterThanOrEqual = succCountGreaterThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery succCountLessThanOrEqual(Integer succCountLessThanOrEqual) {
		if (succCountLessThanOrEqual == null) {
			throw new RuntimeException("succCount is null");
		}
		this.succCountLessThanOrEqual = succCountLessThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery succCounts(List<Integer> succCounts) {
		if (succCounts == null) {
			throw new RuntimeException("succCounts is empty ");
		}
		this.succCounts = succCounts;
		return this;
	}

	public CvtRuntimeQuery failCount(Integer failCount) {
		if (failCount == null) {
			throw new RuntimeException("failCount is null");
		}
		this.failCount = failCount;
		return this;
	}

	public CvtRuntimeQuery failCountGreaterThanOrEqual(Integer failCountGreaterThanOrEqual) {
		if (failCountGreaterThanOrEqual == null) {
			throw new RuntimeException("failCount is null");
		}
		this.failCountGreaterThanOrEqual = failCountGreaterThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery failCountLessThanOrEqual(Integer failCountLessThanOrEqual) {
		if (failCountLessThanOrEqual == null) {
			throw new RuntimeException("failCount is null");
		}
		this.failCountLessThanOrEqual = failCountLessThanOrEqual;
		return this;
	}

	public CvtRuntimeQuery failCounts(List<Integer> failCounts) {
		if (failCounts == null) {
			throw new RuntimeException("failCounts is empty ");
		}
		this.failCounts = failCounts;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("taskName".equals(sortColumn)) {
				orderBy = "E.TASKNAME_" + a_x;
			}

			if ("runTime".equals(sortColumn)) {
				orderBy = "E.RUNTIME_" + a_x;
			}

			if ("runFlag".equals(sortColumn)) {
				orderBy = "E.RUNFLAG_" + a_x;
			}

			if ("succCount".equals(sortColumn)) {
				orderBy = "E.SUCCCOUNT_" + a_x;
			}

			if ("failCount".equals(sortColumn)) {
				orderBy = "E.FAILCOUNT_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("taskCode", "TASKCODE_");
		addColumn("taskName", "TASKNAME_");
		addColumn("runTime", "RUNTIME_");
		addColumn("runFlag", "RUNFLAG_");
		addColumn("succCount", "SUCCCOUNT_");
		addColumn("failCount", "FAILCOUNT_");
		addColumn("log", "LOG_");
	}

}