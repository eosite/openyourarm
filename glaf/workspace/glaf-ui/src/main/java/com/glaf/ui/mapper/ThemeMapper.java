package com.glaf.ui.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.ui.model.Theme;
import com.glaf.ui.query.ThemeQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.ui.mapper.ThemeMapper")
public interface ThemeMapper {

	void deleteThemes(ThemeQuery query);

	void deleteThemeById(Integer id);

	Theme getThemeById(Integer id);

	int getThemeCount(ThemeQuery query);

	List<Theme> getThemes(ThemeQuery query);

	void insertTheme(Theme model);

	void updateTheme(Theme model);

}
