package com.glaf.sys.mapper;

import java.util.List;
import org.springframework.stereotype.Component;
import com.glaf.sys.domain.*;
import com.glaf.sys.query.*;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.sys.mapper.ProjMuiprojlistMapper")
public interface ProjMuiprojlistMapper {

	void deleteProjMuiprojlists(ProjMuiprojlistQuery query);

	void deleteProjMuiprojlistById(Integer id);

	ProjMuiprojlist getProjMuiprojlistById(Integer id);

	int getProjMuiprojlistCount(ProjMuiprojlistQuery query);

	List<ProjMuiprojlist> getProjMuiprojlists(ProjMuiprojlistQuery query);

	void insertProjMuiprojlist(ProjMuiprojlist model);

	void updateProjMuiprojlist(ProjMuiprojlist model);
	
	ProjMuiprojlist getLocalProjMuiprojlist();

}
