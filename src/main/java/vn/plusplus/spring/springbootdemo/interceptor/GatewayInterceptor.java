package vn.plusplus.spring.springbootdemo.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import vn.plusplus.spring.springbootdemo.repository.ApiRepository;
import vn.plusplus.spring.springbootdemo.repository.entity.ApiEntity;
import vn.plusplus.spring.springbootdemo.services.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

@Service
public class GatewayInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LogManager.getLogger(GatewayInterceptor.class);

    @Autowired
    ApiRepository apiRepository;

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(GatewayConstant.START_PROCESSING_TIME, System.currentTimeMillis());
        String sessionId = createSessionId();
        ThreadContext.put(GatewayConstant.CORRELATION_ID_HEADER, sessionId);
        logger.info("========== Start process request [{}]:[{}]", request.getMethod(), request.getServletPath());
        return verifyRequest(request);
    }

    /**
     * Logging all successfully request here, if request throw exception then it's logged in exceptionTranslator
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
        Long startProcessingTime = (Long) request.getAttribute(GatewayConstant.START_PROCESSING_TIME);
        Long endProcessingTime = System.currentTimeMillis();

        logger.info("========== End to process request [{}]:[{}] with [{}]. Processing time: [{}] milliseconds ==========", request.getMethod(), request.getServletPath(), response.getStatus(), endProcessingTime - startProcessingTime);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private boolean verifyRequest(HttpServletRequest request) {
        printHeader(request);
        String httpMethod = request.getMethod();
        String servletPath = request.getServletPath();
        String accessToken = request.getHeader(GatewayConstant.AUTHORIZATION_HEADER);
        ApiEntity apiEntity = getMatchingAPI(httpMethod, servletPath);
        if (apiEntity.getIsRequiredAccessToken()) {
            if (StringUtils.isEmpty(accessToken)) {
                logger.error("Authorization field in header is null or empty");
                throw new RuntimeException("Missing token");
            }
            Payload payload = tokenService.getPayload(accessToken, apiEntity);
            request.setAttribute(GatewayConstant.PAYLOAD, payload);
        }

        logger.info("Request validated. Start forward request to controller");
        return true;
    }

    private String createSessionId() {
        String sessionToken = UUID.randomUUID().toString();
        return sessionToken;
    }

    private ApiEntity getMatchingAPI(String httpMethod, String path) {
        path = path.trim();
        if (path.endsWith("/")) {
            path = path.substring(0, path.lastIndexOf("/"));
        }

        AntPathMatcher matcher = new AntPathMatcher();
        //TODO caching list API
        List<ApiEntity> apiLists = apiRepository.findAll();
        for (ApiEntity apiEntity : apiLists) {
            if (matcher.match(apiEntity.getPattern(), path) && httpMethod.equals(apiEntity.getHttpMethod())) {
                logger.info("Found api matched");
                return apiEntity;
            }
        }
        throw new RuntimeException("Not found any API matching");
    }

    private void printHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            logger.debug("Header: {} - {}", header, request.getHeader(header));
        }
    }
}