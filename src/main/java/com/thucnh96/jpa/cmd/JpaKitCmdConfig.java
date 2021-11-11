package com.thucnh96.jpa.cmd;

import com.thucnh96.jpa.JpaClientGenerateFactory;
import com.thucnh96.jpa.JpaClientSchemaFactory;
import com.thucnh96.jpa.JpaToolApplication;
import com.thucnh96.jpa.modal.payload.ProjectIto;
import com.thucnh96.jpa.service.PushMessageService;
import com.thucnh96.jpa.service.generate.Generate;
import com.thucnh96.jpa.service.schema.Schema;
import org.springframework.util.StringUtils;

import static com.thucnh96.jpa.utils.OrmUtil.convertToOrm;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 3:56 PM
 */
public class JpaKitCmdConfig {

    private JpaKitConverterConfig config;
    private PushMessageService pushMessageService;

    public JpaKitCmdConfig(JpaKitConverterConfig config,PushMessageService pushMessageService){
        this.config =  config;
        this.pushMessageService = pushMessageService;
    }
    public static String excuteCommand(JpaKitConverterConfig config,PushMessageService pushMessageService) throws Exception {
       return new JpaKitCmdConfig(config,pushMessageService).generate() ;
    }

    public   String generate() throws Exception {

        ProjectIto project = new ProjectIto();
        if (!StringUtils.isEmpty(config.getOut())) {
            project.setPath(config.getOut());
        }else {
            String jarPath = JpaToolApplication.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
                    .getPath();
            String jarName = jarPath.substring(jarPath.lastIndexOf("/") + 1);
            String jarFolder = jarPath.replace(jarName,"");
            project.setPath(jarFolder);
        }
        project.setUrl(config.getUrl());
        project.setPassword(config.getPassword());
        project.setUsername(config.getUsername());
        project.setType(config.getType());
        project.setPrefix(config.getPrefix());
        project.setSubfix(config.getSubfix());

        project.setDtoFolder("dto");
        project.setSepcFolder("spec");
        project.setRepositoryFolder("dao");
        project.setRestFolder("rest");
        project.setEntityFolder("domain");

        String message;

        try {
            Schema schema = JpaClientSchemaFactory.getSchema(convertToOrm(project.getType()),project);
            Generate generate = JpaClientGenerateFactory.getGenerate(convertToOrm(project.getType()));
            generate.gen(schema.getTables(),project,pushMessageService);
            message = "generated localtion : " +project.getPath();
        }catch (Exception e){
            message = e.getMessage();
        }
        return message;
        }

}
