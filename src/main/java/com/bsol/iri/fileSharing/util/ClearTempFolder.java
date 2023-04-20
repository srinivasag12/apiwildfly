package com.bsol.iri.fileSharing.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClearTempFolder {

	private static final Logger log = LoggerFactory.getLogger(ClearTempFolder.class);

	public void clearAllFileOlderThan24Hr() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		File file = new java.io.File("/temp");
		Date yestedDay = new Date(cal.getTime().getTime());
		Date filedate = null;
		try {
			for (File f : file.listFiles()) {
				Path path = Paths.get(f.getAbsolutePath());
				BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
				FileTime fileTime = attr.lastModifiedTime();
				filedate = new Date(fileTime.toMillis());
				if (filedate.before(yestedDay)) {
					log.info("temp file deleted " + f);
					f.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
