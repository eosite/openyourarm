package com.glaf.util;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

import com.glaf.core.util.AnnotationUtils;

public class JPAUtils {
	private static final Log logger = LogFactory.getLog(JPAUtils.class);

	public static Collection<String> findJPAEntity(String packagePrefix) {
		AnnotationDB db = getAnnotationDB(packagePrefix);
		Map<String, Set<String>> annotationIndex = db.getAnnotationIndex();
		Set<String> entities = annotationIndex.get("javax.persistence.Entity");
		Collection<String> sortSet = new TreeSet<String>();
		if (entities != null && !entities.isEmpty()) {
			for (String str : entities) {
				if (packagePrefix != null && str.contains(packagePrefix)) {
					sortSet.add(str);
				}
			}
		}
		return sortSet;
	}

	public static AnnotationDB getAnnotationDB(String packagePrefix) {
		AnnotationDB db = new AnnotationDB();
		db.addIgnoredPackages("org");
		db.setScanClassAnnotations(true);
		String[] scanPackages = new String[1];
		scanPackages[0] = packagePrefix;
		db.setScanPackages(scanPackages);

		URL url = ClasspathUrlFinder.findClassBase(AnnotationUtils.class);
		try {
			logger.debug("scan url:" + url.toURI().toString());
			db.scanArchives(url);
			URL[] urls = ClasspathUrlFinder.findClassPaths();
			for (URL url2 : urls) {
				logger.debug("scan url:" + url2.toURI().toString());
				db.scanArchives(url2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		return db;
	}
}
