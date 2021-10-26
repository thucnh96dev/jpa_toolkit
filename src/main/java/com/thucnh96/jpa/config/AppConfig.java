package com.thucnh96.jpa.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@Log4j2
public class AppConfig {
    public static final String _CHARSET = "UTF-8";
    public static final String _APPNAME = "JPA_TOOL";
    public static final String _AUTHOR = "THUCNH";

    public static final String [] _BASENAMES = {
            "classpath:/i18n/language",
            "classpath:/i18n/message",
            "classpath:/i18n/statuscode"
    }; // TODO add new message sources classpath.


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(_CHARSET);
        messageSource.setBasenames( _BASENAMES);
        messageSource.setCacheSeconds(10);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
