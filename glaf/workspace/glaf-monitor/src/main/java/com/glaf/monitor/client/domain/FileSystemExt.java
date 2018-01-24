package com.glaf.monitor.client.domain;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;

public class FileSystemExt{
	FileSystemUsage usage;
	FileSystem fileSystem;
	public FileSystemUsage getUsage() {
		return usage;
	}

	public void setUsage(FileSystemUsage usage) {
		this.usage = usage;
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

}
