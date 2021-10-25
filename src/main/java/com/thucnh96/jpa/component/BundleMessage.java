package com.thucnh96.jpa.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :25/10/2021 - 8:56 AM
 */
@Component
public class BundleMessage {

    @Autowired
    private MessageSource messageSource;

    /**
     * l
     * @param key
     * @return
     */
    public Object getMessage(String key){
       return getMessageSource(key,null,"", Locale.getDefault());
    }

    /**
     *
     * @param key
     * @param locale
     * @return
     */
    public Object getMessage(String key,Locale locale){
        return getMessageSource(key,null,"", locale);
    }

    /**
     *
     * @param key
     * @param args
     * @return
     */
    public Object getMessage(String key, Object[] args){
        return getMessageSource(key,args,"", Locale.getDefault());
    }

    /**
     *
     * @param key
     * @param args
     * @param locale
     * @return
     */
    public Object getMessage(String key, Object[] args, Locale locale){
        return getMessageSource(key,args,"", locale);
    }

    /**
     *
     * @param key
     * @param args
     * @param defaultMsg
     * @param locale
     * @return
     */
    public Object getMessage(String key, Object[] args, String defaultMsg, Locale locale){
        return getMessageSource(key,args,defaultMsg, locale);
    }

    //Đây là một cách thuận tiện để sử dụng và không dựa trên yêu cầu base Spring
    private Object getMessageSource(String code, Object[] args, String defaultMsg, Locale locale) {
        if (locale == null){
            locale = LocaleContextHolder.getLocale();
        }
        return messageSource.getMessage(code, args, defaultMsg, locale);
    }

    /**
     *
     * @param key
     * @param baseName
     * @return
     */
    public Object getMessageBundle(String key, String baseName ){
        return  getMessageBundleSource(key,baseName,new Object[]{},Locale.getDefault());
    }

    /**
     *
     * @param key
     * @param baseName
     * @param locale
     * @return
     */
    public Object getMessageBundle(String key,String baseName,Locale locale){
        return   getMessageBundleSource(key,baseName,new Object[]{},locale);
    }

    /**
     *
     * @param key
     * @param baseName
     * @param args
     * @return
     */
    public Object getMessageBundle(String key,String baseName,Object[] args){
        return getMessageBundleSource(key,baseName,args,Locale.getDefault());
    }

    /**
     *
     * @param key
     * @param baseName
     * @param args
     * @param locale
     * @return
     */
    public Object getMessageBundle(String key,String baseName,Object[] args, Locale locale){
        return getMessageBundleSource(key,baseName,args,locale);
    }

    /**
     *
     * @param key
     * @param baseName
     * @param args
     * @param locale
     * @return
     */
    private Object getMessageBundleSource(String key,String baseName,Object[] args, Locale locale){
        ResourceBundle bundle = ResourceBundle.getBundle(baseName,locale);
        return  MessageFormat.format((String) bundle.getObject(key),args);
    }


}
