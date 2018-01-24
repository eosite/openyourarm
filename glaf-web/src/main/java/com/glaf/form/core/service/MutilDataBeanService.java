package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.base.TableModel;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

 
@Transactional(readOnly = true)
public interface MutilDataBeanService {
	 

	@Transactional
	void updateTableData(TableModel model);

}
