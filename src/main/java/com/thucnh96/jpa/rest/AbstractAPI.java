package com.thucnh96.jpa.rest;

import com.thucnh96.jpa.modal.response.BodyAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :22/10/2021 - 10:19 AM
 */
public abstract class AbstractAPI {

    protected ResponseEntity<Object> toResponse(Object data){
        BodyAPI body = new BodyAPI(data);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    protected ResponseEntity<Object> toResponse(Object data,String message){
        BodyAPI body = new BodyAPI(data,message);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    protected ResponseEntity<Object> toResponse(Object data, Map<String, Object> messages){
        BodyAPI body = new BodyAPI(data,messages);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    protected ResponseEntity<Object> toResponse(Object data, String message, HttpHeaders headers){
        BodyAPI body = new BodyAPI(data,message);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    protected ResponseEntity<Object> toResponse(String message){
        BodyAPI body = new BodyAPI(message);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
    public String getRemoteIp(HttpServletRequest request) {
        if (StringUtils.isNotBlank(request.getHeader("X-Real-IP"))) {
            return request.getHeader("X-Real-IP");
        } else if (StringUtils.isNotBlank(request.getHeader("X-Forwarded-For"))) {
            return request.getHeader("X-Forwarded-For");
        } else if (StringUtils.isNotBlank(request.getHeader("Proxy-Client-IP"))) {
            return request.getHeader("Proxy-Client-IP");
        }
        return request.getRemoteAddr();
    }

    protected Locale VIETNAM;
    protected Locale DEFAULT;

    {
        VIETNAM = new Locale("vi", "VN");
        DEFAULT = Locale.getDefault();
    }


}
