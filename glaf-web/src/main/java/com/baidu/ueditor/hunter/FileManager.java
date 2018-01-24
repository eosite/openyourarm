package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.glaf.web.domain.PageResource;
import com.glaf.web.query.PageResourceQuery;
import com.glaf.web.service.PageResourceService;

public class FileManager {

	private String dir = null;
	private String dbDir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	private PageResourceService pageResourceService = null;

	public FileManager(Map<String, Object> conf) {

		this.rootPath = (String) conf.get("rootPath");
		this.dir = this.rootPath + (String) conf.get("dir");
		this.dbDir = (String) conf.get("dir");
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");
		this.pageResourceService = (PageResourceService) conf.get("pageResourceService");
	}

	public State listFile(int index) {

		File dir = new File(this.dir);
		State state = null;

		if (!dir.exists()) {
			return new BaseState(false, AppInfo.NOT_EXIST);
		}

		if (!dir.isDirectory()) {
			return new BaseState(false, AppInfo.NOT_DIRECTORY);
		}

		Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

		if (index < 0 || index > list.size()) {
			state = new MultiState(true);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
			state = this.getState(fileList);
		}

		state.putInfo("start", index);
		state.putInfo("total", list.size());

		return state;

	}

	public State listFileFromDataBase(int index) {
		MultiState state = null;
		PageResourceQuery query=new PageResourceQuery();
		query.setResPathLike(this.dbDir);
        List<PageResource> pageReourceList=pageResourceService.list(query);
        state = new MultiState(true);
        BaseState fileState = null;
        for(PageResource pageReource:pageReourceList){
        	fileState= new BaseState(true);
        	fileState.putInfo("url", this.rootPath+pageReource.getResPath());
			state.addState(fileState);
        }
		state.putInfo("start", index);
		state.putInfo("total", pageReourceList.size());

		return state;

	}

	private State getState(Object[] files) {

		MultiState state = new MultiState(true);
		BaseState fileState = null;

		File file = null;

		for (Object obj : files) {
			if (obj == null) {
				break;
			}
			file = (File) obj;
			fileState = new BaseState(true);
			fileState.putInfo("url", PathFormat.format(this.getPath(file)));
			state.addState(fileState);
		}

		return state;

	}

	private String getPath(File file) {

		String path = file.getAbsolutePath();

		return path.replace(this.rootPath, "/");

	}

	private String[] getAllowFiles(Object fileExt) {

		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

}
