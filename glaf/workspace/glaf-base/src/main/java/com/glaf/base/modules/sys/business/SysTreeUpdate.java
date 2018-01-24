package com.glaf.base.modules.sys.business;

import com.glaf.core.tree.helper.TreeUpdateBean;

public class SysTreeUpdate {
	
	public void updateTreeIds(){
		TreeUpdateBean updateBean = new TreeUpdateBean();
		updateBean.updateTreeIds("default", "sys_tree", null, "ID", "PARENT", "TREEID", null, null);
	}

}
