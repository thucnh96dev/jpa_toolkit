package com.thucnh96.jpa.rest;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.thucnh96.jpa.component.BundleMessage;
import com.thucnh96.jpa.cronJob.application.SchedulingRunnable;
import com.thucnh96.jpa.cronJob.config.CronTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :22/10/2021 - 8:37 AM
 */
@RestController
@RequestMapping(value = "/cronjob")
public class APICronjob extends AbstractAPI {


    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Autowired
    private BundleMessage bundleMessage;

    String prefixJson = "cronDes_";

    @GetMapping(value = "/convert")
    public ResponseEntity<Object> cronJobConverter(
            @RequestParam(name = "expression") String expression,
            @RequestParam(name = "cronType",required = false) CronType cronType,
            HttpServletRequest  request) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (cronType == null){
                cronType = CronType.QUARTZ;
            }
            Locale locale = request.getLocale(); //new Locale("vi","VN");
            Map<String, Object> crons = getTextCron(expression,cronType,DEFAULT,locale);
            result.put("crons", crons);
            result.put("cronType", cronType);
            String msg = (String) bundleMessage.getMessage("test.api",VIETNAM);
            result.put("bundleMessage", msg);
        } catch (Exception e) {
            result.put("msg", e.getMessage());
        }
        String ip = getRemoteIp(request);
        result.put("ip",ip);
        return toResponse(result);
    }

    @GetMapping()
    public ResponseEntity<Object> addConJob(
            @RequestParam(name = "expression") String expression,
            HttpServletRequest  request) {
        Map<String, Object> result = new HashMap<>();
        String message ;
        try {
            Locale locale = request.getLocale(); //new Locale("vi","VN");
            Map<String, Object> crons = getTextCron(expression,CronType.SPRING,DEFAULT,locale);
            result.put("crons", crons);
            SchedulingRunnable task = new SchedulingRunnable("demologTask", "taskWithParams", "haha", 23);
            cronTaskRegistrar.addCronTask(task,expression);
            message = "RUNNING";
        } catch (Exception e) {
            message = e.getMessage();
        }
        String ip = getRemoteIp(request);
        result.put("ip",ip);
        return toResponse(result, message);
    }

    private Map<String, Object> getTextCron(String expression,CronType cronType, Locale... locales){
        Map<String, Object> result = new HashMap<>();
        ResourceBundle resourceBundle;
        CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(cronType);
        CronParser parser = new CronParser(cronDefinition);
        for (Locale locale : locales){
            if ( null == locale ){
                resourceBundle  =  ResourceBundle.getBundle("i18n/message", DEFAULT);
            }else {
                resourceBundle  =  ResourceBundle.getBundle("i18n/message", locale);
            }
            CronDescriptor descriptor = new CronDescriptor(resourceBundle);
            String  crontext = descriptor.describe(parser.parse(expression));
            result.put(prefixJson.concat(locale.getLanguage()),crontext);
        }
        return  result;
    }
}
