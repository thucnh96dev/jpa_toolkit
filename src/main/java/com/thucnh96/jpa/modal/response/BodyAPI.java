package com.thucnh96.jpa.modal.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :22/10/2021 - 10:21 AM
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodyAPI<T extends Object> {

    private T data;
    private String message;
    private int code;
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss",shape = JsonFormat.Shape.STRING,locale = "vi_VN" )
    private Date  timestamp;
    private Map<String, Object> messages;

    public BodyAPI(){
        this.timestamp = new Date();
        this.code = HttpStatus.OK.value();
    }

    public BodyAPI(T data){
        this.timestamp =  new Date();
        this.code = HttpStatus.OK.value();
        this.message = HttpStatus.OK.toString();
        this.data = data;
    }

    public BodyAPI(String message ){
        this.timestamp =  new Date();
        this.code = HttpStatus.OK.value();
        this.message = message;
    }

    public BodyAPI( T data,String message){
        this.timestamp = new Date();
        this.code = HttpStatus.OK.value();
        this.message = message;
        this.data = data;
    }

    public BodyAPI( T data, Map<String, Object> messages){
        this.timestamp = new Date();
        this.code = HttpStatus.OK.value();
        this.messages = messages;
        this.data = data;
    }

}