package com.glaf.workflow.core.service;


import org.springframework.transaction.annotation.Transactional;

import com.glaf.workflow.core.domain.ProcessInsMapping;

@Transactional(readOnly = true)
public interface StartImpProcessService {
	/**
	 * 通过待办流程列表启动本地流程
	 */
	public void startProcesses();
	/**
	 *  通过待办流程启动本地流程
	 */
	public void startProcess(ProcessInsMapping processInsMapping);
}
