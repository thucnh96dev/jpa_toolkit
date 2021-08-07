package com.thucnh96.jpa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @author thucnh
 *
 */
public class Ziping {
	/**
	 * Get path of files relative to input directory.
	 * @param listFiles
	 * @param inputDirectory
	 * @param zipOutputStream
	 * @throws IOException
	 */
	public static void createZipFile(List<File> listFiles, File inputDirectory, ZipOutputStream zipOutputStream) throws IOException {
		for (File file : listFiles) {
			String filePath = file.getCanonicalPath();
			int lengthDirectoryPath = inputDirectory.getCanonicalPath().length();
			int lengthFilePath = file.getCanonicalPath().length();

			String zipFilePath = filePath.substring(lengthDirectoryPath + 1, lengthFilePath);

			ZipEntry zipEntry = new ZipEntry(zipFilePath);
			zipOutputStream.putNextEntry(zipEntry);

			FileInputStream inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = inputStream.read(bytes)) >= 0) {
				zipOutputStream.write(bytes, 0, length);
			}
			zipOutputStream.closeEntry();
			System.out.println("Zipped file:" + filePath);
		}
		zipOutputStream.close();
	}

	/**
	 * Get list of all files recursively by iterating through sub directories
	 * @param listFiles
	 * @param inputDirectory
	 * @return
	 * @throws IOException
	 */
	public static List<File> listFiles(List listFiles, File inputDirectory) throws IOException {
		File[] allFiles = inputDirectory.listFiles();
		for (File file : allFiles) {
			if (file.isDirectory()) {
				listFiles(listFiles, file);
			} else {
				listFiles.add(file);
			}
		}
		return listFiles;
	}

}
