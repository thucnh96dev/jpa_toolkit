package com.thucnh96.jpa.client;


import com.thucnh96.jpa.JpaClientGenerateFactory;
import com.thucnh96.jpa.JpaClientSchemaFactory;
import com.thucnh96.jpa.modal.DbType;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.service.generate.Generate;
import com.thucnh96.jpa.service.schema.Schema;
import com.thucnh96.jpa.utils.Ziping;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

import static com.thucnh96.jpa.utils.OrmUtil.convertToOrm;

@Controller
public class APIGenerate {

    @Autowired
    private PushMessageService pushMessageService;

    @GetMapping(value = "/")
    public String view(Model model){
        model.addAttribute("type", DbType.values());
        return "index";
    }

    @PostMapping(value = "/generate")
    public void view(@RequestBody ProjectIto project, HttpServletResponse response)  {
        String uuidFolder = UUID.randomUUID().toString();
        String folderTemp = System.getProperty("java.io.tmpdir").concat(File.separator).concat(uuidFolder);
        try {
            project.setPath(folderTemp);
            Schema schema = JpaClientSchemaFactory.getSchema(convertToOrm(project.getType()),project);
            Generate generate = JpaClientGenerateFactory.getGenerate(convertToOrm(project.getType()));
            generate.gen(schema.getTables(),project,pushMessageService);
            projectZip(folderTemp,project.getProjectName(),response);
        }catch (Exception e){
            e.printStackTrace();
            pushMessageService.sendMessage(e.getMessage());
        }
    }
    private void projectZip(String folderTemp,String projectName,HttpServletResponse response) throws IOException {
        String projectNameFolder = StringUtils.isEmpty(projectName) ? "project.zip" : projectName.trim().concat(".zip");
        String zip_file_name = folderTemp.concat(File.separator).concat(projectNameFolder);
        File inputDirectory = new File(folderTemp);
        File outputZip = new File(zip_file_name);
        outputZip.getParentFile().mkdirs();
        List listFiles = new ArrayList();
        //Populate all files...iterate through directories/subdirectories...
        //recursively
        System.out.printf("1. Input directory %s has following files:\n",
                inputDirectory.getCanonicalPath());
        Ziping.listFiles(listFiles, inputDirectory);
        //Create zip output stream to zip files
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputZip));
        //Create zip files by recursively iterating through directories
        //and sub directories....
        //  pushMessageService.sendMessage("\n2. Output Zipped file at location:"+outputZip.getCanonicalPath());
        System.out.println("\n2. Output Zipped file at location:"+outputZip.getCanonicalPath());
        Ziping.createZipFile(listFiles, inputDirectory, zipOutputStream);
        InputStream inputStream = new FileInputStream(outputZip);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename="+projectNameFolder);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
        outputZip.delete();
    }
}
