package com.glaf.report.core.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import com.glaf.report.core.domain.*;
import com.glaf.report.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.report.core.mapper.ReportCategoryMapper")
public interface ReportCategoryMapper {

	void deleteReportCategorys(ReportCategoryQuery query);

	void deleteReportCategoryById(Integer id);

	ReportCategory getReportCategoryById(Integer id);

	int getReportCategoryCount(ReportCategoryQuery query);

	List<ReportCategory> getReportCategorys(ReportCategoryQuery query);

	void insertReportCategory(ReportCategory model);

	void updateReportCategory(ReportCategory model);
	
	void rename(@Param("categoryId")Long categoryId,@Param("name")String name,@Param("modifier")String modifier,@Param("modifyDatetime")Date modifyDatatime);
	
	void move(@Param("categoryId")Long categoryId,@Param("pId")Long pId,@Param("treeId")String treeId,@Param("modifier")String modifier,@Param("modifyDatetime")Date modifyDatatime);

}
