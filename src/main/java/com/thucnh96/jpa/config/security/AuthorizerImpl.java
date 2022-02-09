//package com.thucnh96.jpa.config.security;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.core.ResolvableType;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.lang.annotation.Annotation;
//import java.util.Arrays;
//import java.util.Optional;
//
///**
// * @author : thucnh
// * @mailto : thucnh96.dev@gmail.com
// * @created :17/11/2021 - 9:26 AM
// */
//@Service("authorizer")
//@Log4j2
//public class AuthorizerImpl implements Authorizer {
//
//
//    /**
//     *
//     * @param authentication
//     * @param action
//     * @param callerObj
//     * @return boolean
//     */
//    @Override
//    public boolean authorize(Authentication authentication, String action, Object callerObj) {
//        String securedPath = extractSecuredPath(callerObj);
//        if (securedPath==null || "".equals(securedPath.trim())) {//login, logout
//            return true;
//        }
//        String menuCode = securedPath.substring(1);//Bỏ dấu "/" ở đầu Path
//        boolean isAllow = false;
//        try {
//            UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
//            if (user==null){
//                return isAllow;
//            }
//            String userId = (String)user.getPrincipal();
//            if (userId==null || "".equals(userId.trim())) {
//                return isAllow;
//            }
//            //Truy vấn vào CSDL theo userId + menuCode + action
//            //Nếu có quyền thì
//            {
//                isAllow = true;
//            }
//        } catch (Exception e) {
//            log.error(e.toString(), e);
//            throw e;
//        }
//        return isAllow;
//    }
//
//    // Lay ra securedPath duoc Annotate RequestMapping trong Controller
//    private String extractSecuredPath(Object callerObj) {
//        Class<?> clazz = ResolvableType.forClass(callerObj.getClass()).getRawClass();
//        Optional<Annotation> annotation = Arrays.asList(clazz.getAnnotations()).stream()
//                .filter((ann) -> ann instanceof RequestMapping).findFirst();
//        log.debug("FOUND CALLER CLASS: {}", ResolvableType.forClass(callerObj.getClass()).getType().getTypeName());
//        String result = null;
//        if (annotation.isPresent()) {
//            RequestMapping t = (RequestMapping) annotation.get();
//
//            String[] strings = t.value();
//            for (String s : strings) {
//                System.out.println("VALUE: "+s);
//            }
//            System.out.println(t.method());
//
//        }
//        return null;
//    }
//}
