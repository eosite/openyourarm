package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.Hint;
import com.glaf.isdp.query.HintQuery;

@Transactional(readOnly = true)
public interface HintService {

	Hint getHintById(String id);

	List<Hint> getHints(HintQuery query);

	int getHintCount(HintQuery query);
}
