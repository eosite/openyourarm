package com.glaf.theme.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

 
@Transactional(readOnly = true)
public interface SysThemeService {
	 
	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	JSONArray getThemeTreeByThemeId(String id);
}
