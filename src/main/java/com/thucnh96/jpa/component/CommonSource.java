package com.thucnh96.jpa.component;

import com.thucnh96.jpa.progress.ExcuteFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
	        String folderValidator = "validator";

	        String headerFile = "package ".concat(this.package_project).concat(".").concat(folder).concat(";") ;
	        String headerFileValidator = "package ".concat(this.package_project).concat(".").concat(folderValidator).concat(".base").concat(";\n") ;

	        String commonConstant = headerFile.concat("\n").concat(initTextCommon("common/CommonConstant.txt")) ;
	        String dtoMapper =  headerFile.concat("\n").concat(initTextCommon("common/DTOmapper.txt"));

	        String existingValidatorText =  headerFileValidator.concat("\n").concat(initTextCommon("common/ExistingValidator.txt"));
	        String iValidatorText =  headerFileValidator.concat("\n").concat(initTextCommon("common/IValidator.txt"));
	        String throwableValidatorText=  headerFileValidator.concat("\n").concat(initTextCommon("common/ThrowableValidator.txt"));

	        ExcuteFile.createFile(this.path,folder,"CommonConstant",commonConstant);
	        ExcuteFile.createFile(this.path,folder,"DTOmapper",dtoMapper);

			ExcuteFile.createFile(this.path,folderValidator,"ExistingValidator",existingValidatorText);
			ExcuteFile.createFile(this.path,folderValidator,"IValidator",iValidatorText);
			ExcuteFile.createFile(this.path,folderValidator,"ThrowableValidator",throwableValidatorText);



	    }

}
