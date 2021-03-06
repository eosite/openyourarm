/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.watcher;

import java.io.File;
import java.util.Arrays;

/**
 * File resources watcher
 *
 * The file watcher checks directory and all its subdirectories for file changes
 * and notifies its listeners accordingly
 */
public class FileWatcher extends AbstractResourceWatcher<FileChangesListener> {

	private FileObserver rootFileObserver;

	/**
	 * Creates new file watcher on the given directory
	 */
	public FileWatcher(File file) {
		rootFileObserver = new FileObserver(file);
	}

	@Override
	protected void doInit() {
		rootFileObserver.init(true);
	}

	@Override
	protected void doCheckAndNotify() {
		rootFileObserver.checkAndNotify();
	}

	private static FileObserver[] EMPTY_DIRECTORY = new FileObserver[0];

	private class FileObserver {
		private File file;
		private boolean exists;
		private long length;
		private long lastModified;
		private boolean isDirectory;
		private FileObserver[] children;

		public FileObserver(File file) {
			this.file = file;
		}

		public void checkAndNotify() {
			boolean prevExists = exists;
			boolean prevIsDirectory = isDirectory;
			long prevLength = length;
			long prevLastModified = lastModified;

			exists = file.exists();

			if (exists) {
				isDirectory = file.isDirectory();
				if (isDirectory) {
					length = 0;
					lastModified = 0;
				} else {
					length = file.length();
					lastModified = file.lastModified();
				}
			} else {
				isDirectory = false;
				length = 0;
				lastModified = 0;
			}

			// Perform notifications and update children for the current file
			if (prevExists) {
				if (exists) {
					if (isDirectory) {
						if (prevIsDirectory) {
							// Remained a directory
							updateChildren();
						} else {
							// File replaced by directory
							onFileDeleted();
							onDirectoryCreated(false);
						}
					} else {
						if (prevIsDirectory) {
							// Directory replaced by file
							onDirectoryDeleted();
							onFileCreated(false);
						} else {
							// Remained file
							if (prevLastModified != lastModified
									|| prevLength != length) {
								onFileChanged();
							}
						}
					}
				} else {
					// Deleted
					if (prevIsDirectory) {
						onDirectoryDeleted();
					} else {
						onFileDeleted();
					}
				}
			} else {
				// Created
				if (exists) {
					if (isDirectory) {
						onDirectoryCreated(false);
					} else {
						onFileCreated(false);
					}
				}
			}

		}

		private void init(boolean initial) {
			exists = file.exists();
			if (exists) {
				isDirectory = file.isDirectory();
				if (isDirectory) {
					onDirectoryCreated(initial);
				} else {
					length = file.length();
					lastModified = file.lastModified();
					onFileCreated(initial);
				}
			}
		}

		private FileObserver createChild(File file, boolean initial) {
			FileObserver child = new FileObserver(file);
			child.init(initial);
			return child;
		}

		private File[] listFiles() {
			File[] files = file.listFiles();
			if (files != null) {
				Arrays.sort(files);
			}
			return files;
		}

		private FileObserver[] listChildren(boolean initial) {
			File[] files = listFiles();
			if (files != null && files.length > 0) {
				FileObserver[] children = new FileObserver[files.length];
				for (int i = 0; i < files.length; i++) {
					children[i] = createChild(files[i], initial);
				}
				return children;
			} else {
				return EMPTY_DIRECTORY;
			}
		}

		private void updateChildren() {
			File[] files = listFiles();
			if (files != null && files.length > 0) {
				FileObserver[] newChildren = new FileObserver[files.length];
				int child = 0;
				int file = 0;
				while (file < files.length || child < children.length) {
					int compare;

					if (file >= files.length) {
						compare = -1;
					} else if (child >= children.length) {
						compare = 1;
					} else {
						compare = children[child].file.compareTo(files[file]);
					}

					if (compare == 0) {
						// Same file copy it and update
						children[child].checkAndNotify();
						newChildren[file] = children[child];
						file++;
						child++;
					} else {
						if (compare > 0) {
							// This child doesn't appear in the old list - init
							// it
							newChildren[file] = createChild(files[file], false);
							file++;
						} else {
							// The child from the old list is missing in the new
							// list
							// Delete it
							deleteChild(child);
							child++;
						}
					}
				}
				children = newChildren;
			} else {
				// No files - delete all children
				for (int child = 0; child < children.length; child++) {
					deleteChild(child);
				}
				children = EMPTY_DIRECTORY;
			}
		}

		private void deleteChild(int child) {
			if (children[child].exists) {
				if (children[child].isDirectory) {
					children[child].onDirectoryDeleted();
				} else {
					children[child].onFileDeleted();
				}
			}
		}

		private void onFileCreated(boolean initial) {
			for (FileChangesListener listener : listeners()) {
				if (initial) {
					listener.onFileInit(file);
				} else {
					listener.onFileCreated(file);
				}
			}
		}

		private void onFileDeleted() {
			for (FileChangesListener listener : listeners()) {
				listener.onFileDeleted(file);
			}
		}

		private void onFileChanged() {
			for (FileChangesListener listener : listeners()) {
				listener.onFileChanged(file);
			}
		}

		private void onDirectoryCreated(boolean initial) {
			for (FileChangesListener listener : listeners()) {
				if (initial) {
					listener.onDirectoryInit(file);
				} else {
					listener.onDirectoryCreated(file);
				}
			}
			children = listChildren(initial);
		}

		private void onDirectoryDeleted() {
			// First delete all children
			for (int child = 0; child < children.length; child++) {
				deleteChild(child);
			}
			for (FileChangesListener listener : listeners()) {
				listener.onDirectoryDeleted(file);
			}
		}

	}

}
