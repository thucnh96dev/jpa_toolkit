package com.thucnh96.jpa.rest;


import com.thucnh96.jpa.constants.JpaConstants;
import com.thucnh96.jpa.converter.MysqlOrmMappingDataConverter;
import com.thucnh96.jpa.modal.Table;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.service.orm.AbstractOrmGenerate;
import com.thucnh96.jpa.service.orm.MysqlOrmGenerate;
import com.thucnh96.jpa.service.orm.PostgresOrmGenerate;
import com.thucnh96.jpa.utils.Ziping;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipOutputStream;

@Controller
public class APIGenerate {

    @Autowired
    private PushMessageService pushMessageService;

    @GetMapping(value = "/")
    public String view(Model model){
        model.addAttribute("type", JpaConstants.DbType.values());
        return "index";
    }

    @PostMapping(value = "/generate")
    public void view(@RequestBody ProjectIto project, HttpServletResponse response){
        String uuidFolder = UUID.randomUUID().toString();
        String folderTemp = System.getProperty("java.io.tmpdir").concat(File.separator).concat(uuidFolder);
        Map<String, Object> result = new HashMap<>();
        try {
            String type = project.getType();
            String url = project.getUrl();
            String username = project.getUsername();
            String password = project.getPassword();
            List<Table> tables = new ArrayList<>();
            AbstractOrmGenerate abstractOrmGenerate = null;
            if ( JpaConstants.DbType.MYSQL.toString().equals(type) ){
               abstractOrmGenerate = new MysqlOrmGenerate(folderTemp, project.getEntityFolder(),project.getServiceFolder(),
                        project.getRepositoryFolder(),project.getRestFolder(), project.getPackages(),
                        project.getDtoFolder(),project.getSepcFolder(),url,username,password,
                        pushMessageService, new MysqlOrmMappingDataConverter());

            }else if (JpaConstants.DbType.POSTGRES.toString().equals(type)){
                abstractOrmGenerate = new PostgresOrmGenerate(folderTemp, project.getEntityFolder(),project.getServiceFolder(),
                        project.getRepositoryFolder(),project.getRestFolder(), project.getPackages(),
                        project.getDtoFolder(),project.getSepcFolder(),url,username,password,
                        pushMessageService, new MysqlOrmMappingDataConverter());
            }
            abstractOrmGenerate.genProgress();
            if (abstractOrmGenerate !=null){
                String zip_file_name = folderTemp.concat(File.separator).concat("project.zip");
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
                response.setHeader("Content-Disposition", "attachment; filename="+"project.zip");
                IOUtils.copy(inputStream, response.getOutputStream());
                response.flushBuffer();
                inputStream.close();
                outputZip.delete();
            }
            result.put("tables",tables);
        }catch (Exception e){
            result.put("msg",e.getMessage());
        }
    }
}
