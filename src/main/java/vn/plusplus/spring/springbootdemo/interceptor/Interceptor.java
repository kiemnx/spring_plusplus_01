package vn.plusplus.spring.springbootdemo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request,  HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        String path = request.getContextPath();
        String method = request.getMethod();
        return true;
    }

    private void checkToken(){

    }
    private void checkRole(){

    }
}
