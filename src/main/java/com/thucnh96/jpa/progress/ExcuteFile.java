package com.thucnh96.jpa.progress;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.thucnh96.jpa.service.PushMessageService;

/**
 * 
 * @author thucnh
 *
 */
public class ExcuteFile {
	/**
	 * 
	 * @param path
	 * @param foder
	 * @param fileName
	 * @param fileContent
	 * @param pushMessageService
	 * @throws IOException
	 */
	  public static void createFile(String path, String foder, String fileName, String fileContent, PushMessageService pushMessageService) throws IOException{
	        String fullPatchFile = path.concat("/").concat(foder).concat("/").concat(fileName).concat(".java");
	        String dirFolder = Paths.get(path, foder).toString();
	        File fileDir = new File(dirFolder);
	        if (!fileDir.exists()) {
	            fileDir.mkdirs();
	        }
	        File myObj = new File(fullPatchFile);
	        myObj.createNewFile();

	        FileWriter myWriter = new FileWriter(fullPatchFile);
	        myWriter.write(fileContent);
	        myWriter.close();
	        String message = "Generator ".concat("file: ").concat(fileName).concat(".java");
	        pushMessageService.sendMessage(message);
	    }
	  /**
	   * 
	   * @param path
	   * @param foder
	   * @param fileName
	   * @param fileContent
	   * @throws IOException
	   */
	    public static void createFile(String path, String foder, String fileName, String fileContent) throws IOException {
	        String fullPatchFile = path.concat("/").concat(foder).concat("/").concat(fileName).concat(".java");
	        String dirFolder = Paths.get(path, foder).toString();
	        File fileDir = new File(dirFolder);
	        if (!fileDir.exists()) {
	            fileDir.mkdirs();
	        }
	        File myObj = new File(fullPatchFile);
	        myObj.createNewFile();

	        FileWriter myWriter = new FileWriter(fullPatchFile);
	        myWriter.write(fileContent);
	        myWriter.close();
	    }
}
