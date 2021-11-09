package com.thucnh96.jpa.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :26/10/2021 - 1:13 PM
 */
public class SchemaUtils {

    public static  String entityName(String tb){
        String result = "" ;
        if (tb.indexOf("-") != -1) {
            tb =  tb.replaceAll("-", "  ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= tb;

        }else if (tb.indexOf("_") != 1){
            tb =  tb.replaceAll("_", " ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= tb;
        }else{
            tb =   StringUtils.capitalize(tb);
            result= tb;
        }
        return result ;
    }

    public static  String columName(String tb){
        String result = "" ;
        if (tb.indexOf("-") != -1) {
            tb =  tb.replaceAll("-", "  ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            tb = WordUtils.uncapitalize(tb) ;
            result= tb;

        }else if (tb.indexOf("_") != 1){
            tb =  tb.replaceAll("_", " ");
            tb =  WordUtils.capitalize(tb);
            tb =  tb.replaceAll("\\s+","");
            result= WordUtils.uncapitalize(tb) ;
        }else{
            result= tb;
        }
        return result ;
    }

}
