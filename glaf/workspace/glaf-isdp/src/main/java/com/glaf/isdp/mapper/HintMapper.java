package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.Hint;
import com.glaf.isdp.query.HintQuery;

@Component("com.glaf.isdp.mapper.HintMapper")
public interface HintMapper {
	
	Hint getHintById(String id);
	
	List<Hint> getHints(HintQuery query);
	
	int getHintCount(HintQuery query);
}
