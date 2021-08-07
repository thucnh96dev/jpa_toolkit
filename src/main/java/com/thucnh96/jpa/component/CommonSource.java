package com.thucnh96.jpa.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.util.ResourceUtils;

import com.thucnh96.jpa.progress.ExcuteFile;

/**
 * 
 * @author thucnh
 *
 */
public class CommonSource {
	    private String path;
	    private String package_project;

	    public CommonSource(String path, String package_project) {
	        this.path = path;
	        this.package_project = package_project;
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

	    /**
	     * 
	     * @throws IOException
	     */
	    public void run() throws IOException {
	        String folder = "utils";
	        String headerFile = "package ".concat(this.package_project).concat(".").concat(folder).concat(";") ;
	        String commonConstant = headerFile.concat("\n").concat(initTextCommon("common/CommonConstant.txt")) ;
	        String dtoMapper =  headerFile.concat("\n").concat(initTextCommon("common/DTOmapper.txt"));
	        ExcuteFile.createFile(this.path,folder,"CommonConstant",commonConstant);
	        ExcuteFile.createFile(this.path,folder,"DTOmapper",dtoMapper);
	    }

}
