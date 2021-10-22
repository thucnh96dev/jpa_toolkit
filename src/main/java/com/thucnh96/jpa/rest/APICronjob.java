package com.thucnh96.jpa.rest;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :22/10/2021 - 8:37 AM
 */
@RestController
@RequestMapping(value = "/cronjob")
public class APICronjob extends AbstractAPI {

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
                Map<String, Object> crons = getTextCron(expression,cronType,Locale.getDefault(),new Locale("vi","VN"));
                result.put("crons", crons);
                result.put("cronType", cronType);
            } catch (Exception e) {
                result.put("msg", e.getMessage());
            }
            String ip = getRemoteIp(request);
            result.put("ip",ip);
            return toResponse(result);
        }

        private Map<String, Object> getTextCron(String expression,CronType cronType, Locale... locales){
            Map<String, Object> result = new HashMap<>();
            ResourceBundle resourceBundle;
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(cronType);
            CronParser parser = new CronParser(cronDefinition);
            for (Locale locale : locales){
                if ( null == locale ){
                    resourceBundle  =  ResourceBundle.getBundle("message", Locale.getDefault());
                }else {
                    resourceBundle  =  ResourceBundle.getBundle("message", locale);
                }
                CronDescriptor descriptor = new CronDescriptor(resourceBundle);
                String  crontext = descriptor.describe(parser.parse(expression));
                result.put(prefixJson.concat(locale.getLanguage()),crontext);
            }
            return  result;
        }
}
