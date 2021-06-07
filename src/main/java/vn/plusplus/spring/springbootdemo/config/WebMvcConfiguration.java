package vn.plusplus.spring.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.plusplus.spring.springbootdemo.interceptor.GatewayInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    GatewayInterceptor gatewayInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(gatewayInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
    }

}
