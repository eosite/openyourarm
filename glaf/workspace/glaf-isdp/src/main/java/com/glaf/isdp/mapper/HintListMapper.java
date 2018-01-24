package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.HintList;
import com.glaf.isdp.query.HintListQuery;

@Component("com.glaf.isdp.mapper.HintListMapper")
public interface HintListMapper {
	
	HintList getHintListById(String id);
	
	List<HintList> getHintLists(HintListQuery query);
	
	int getHintLitCount(HintListQuery query);
}
