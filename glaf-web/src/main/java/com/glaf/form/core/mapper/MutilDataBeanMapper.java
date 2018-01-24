package com.glaf.form.core.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.core.base.TableModel;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

/**
 * 
 * Mapper接口
 *
 */

/**
 * @author laizf
 *
 */
@Component("com.glaf.form.core.mapper.MutilDataBeanMapper")
public interface MutilDataBeanMapper {

	void updateTableDataByPrimaryKey(TableModel model);

}
