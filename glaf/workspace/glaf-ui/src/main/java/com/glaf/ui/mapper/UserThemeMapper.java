package com.glaf.ui.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.ui.model.UserTheme;
import com.glaf.ui.query.UserThemeQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.ui.mapper.UserThemeMapper")
public interface UserThemeMapper {

	void deleteUserThemes(UserThemeQuery query);

	void deleteUserThemeById(Integer id);

	UserTheme getUserThemeById(Integer id);

	int getUserThemeCount(UserThemeQuery query);

	List<UserTheme> getUserThemes(UserThemeQuery query);

	void insertUserTheme(UserTheme model);

	void updateUserTheme(UserTheme model);

	void colseCourse(Integer id);

}
