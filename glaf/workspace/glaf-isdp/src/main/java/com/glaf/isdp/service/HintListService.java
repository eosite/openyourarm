package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.HintList;
import com.glaf.isdp.query.HintListQuery;

@Transactional(readOnly = true)
public interface HintListService {

	HintList getHintById(String id);

	List<HintList> getHintLists(HintListQuery query);

	int getHintListCount(HintListQuery query );
}
