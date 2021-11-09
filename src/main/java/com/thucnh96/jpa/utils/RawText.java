package com.thucnh96.jpa.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 
 * @author thucnh
 *
 */
public class RawText {

	    private RawText() {
	    }
		/**
		 * 
		 * @param pathFile
		 * @return content file
		 * @throws IOException
		 */
	    private String initTextCommon(String pathFile) throws IOException {
	        String classpath = "classpath:".concat(pathFile);
	        File file = ResourceUtils.getFile(classpath);
	        String content = new String(Files.readAllBytes(file.toPath()));
	        return content;
	    }

		public static String rawTextCommon(String pathFile) throws IOException {
			String classpath = "classpath:".concat(pathFile);
			File file = ResourceUtils.getFile(classpath);
			String content = new String(Files.readAllBytes(file.toPath()));
			return content;
		}


}
