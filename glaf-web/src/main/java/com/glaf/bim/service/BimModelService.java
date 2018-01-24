package com.glaf.bim.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.bim.domain.BimModel;

@Transactional(readOnly = true)
public interface BimModelService {

	@Transactional
	void saveMsg(String bimId, List<BimModel> tree);
}
