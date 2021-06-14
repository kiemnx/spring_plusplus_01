package vn.plusplus.spring.springbootdemo.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinfast.iot.platform.apigateway.controller.response.GeneralResponse;
import com.vinfast.iot.platform.apigateway.controller.response.ResponseStatus;
import com.vinfast.iot.platform.apigateway.domain.EcosystemHttpRequest;
import com.vinfast.iot.platform.apigateway.domain.EcosystemHttpResponse;
import com.vinfast.iot.platform.apigateway.exception.BaseException;
import com.vinfast.iot.platform.apigateway.factory.ResponseFactory;
import com.vinfast.iot.platform.apigateway.service.AuditLogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class ExceptionTranslator {

    private static Logger logger = LogManager.getLogger();
    private static Map<String, ErrorMessage> errorMessages = new ConcurrentHashMap<>();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private Environment env;

    /**
     * Refresh cache map after calling refresh endpoint
     *
     * @param event
     */
    @EventListener(RefreshScopeRefreshedEvent.class)
    public void onRefresh(RefreshScopeRefreshedEvent event) {
        logger.info("Refresh exception cache from event {}", event.getName());
        errorMessages.clear();
    }

    /**
     * Handle all exception originated from controller:
     * - Convert to centralized error message
     * - Log response
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(HttpServletRequest request, Exception e) throws Exception {

        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), org.springframework.web.bind.annotation.ResponseStatus.class) != null) {
            throw e;
        }
        ResponseEntity<GeneralResponse> responseEntity = getResponseEntity(request, e);
        auditLogService.saveAuditLog(new EcosystemHttpRequest(request), new EcosystemHttpResponse(responseEntity));
        return responseEntity;
    }

    private ResponseEntity getResponseEntity(HttpServletRequest req, Exception e) {
        String language = getErrorMessageLanguage(req);
        try {
            if (e instanceof BaseException) {
                BaseException baseException = (BaseException) e;
                String errorCode = baseException.getCode().code();
                ErrorMessage errorMessageDetail = getErrorMessageDetail(errorCode);
                String message = getErrorMessage(language, baseException, errorMessageDetail);
                String description = errorMessageDetail == null ? baseException.getCode().name().toLowerCase() : errorMessageDetail.getDescription();
                if (!StringUtils.isEmpty(baseException.getLogMessage())) {
                    logger.error(() -> String.format("Exception occurs: [%s] %s", description, baseException.getLogMessage()));
                }
                return ResponseFactory.error(baseException.getCode().status(), errorCode, message, description);
            } else {
                logger.error(e.getMessage(), e);
                return generalError(language);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            try {
                return generalError(language);
            } catch (Exception unexpectedError) {
                logger.error(unexpectedError.getMessage(), unexpectedError);
                // Error should not happen here
                return ResponseFactory.generalError();
            }
        }
    }

    /**
     * Return error message in selected language
     *
     * @param language
     * @param baseException
     * @param errorMessageDetail
     * @return
     * @throws IOException
     */
    private String getErrorMessage(String language, BaseException baseException, ErrorMessage errorMessageDetail) throws IOException {
        if (errorMessageDetail == null) {
            return baseException.getCode().name().toLowerCase();
        } else {
            if (StringUtils.isEmpty(errorMessageDetail.getMessage(language))) {
                // If message is empty in db we'll try to return "general_error" message in selected language instead
                ErrorMessage generalError = getErrorMessageDetail(ResponseStatus.GENERAL_ERROR_CODE);
                if (generalError == null) {
                    return "";
                } else {
                    return generalError.getMessage(language);
                }
            } else {
                if (baseException.getMessageParams().length == 0) {
                    return errorMessageDetail.getMessage(language);
                } else {
                    return String.format(errorMessageDetail.getMessage(language), (Object[]) baseException.getMessageParams());
                }
            }
        }
    }

    private String getErrorMessageLanguage(HttpServletRequest req) {
        String acceptLanguageHeader = req.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (!StringUtils.isEmpty(acceptLanguageHeader) && !"*".equals(acceptLanguageHeader)) {
            return acceptLanguageHeader;
        } else {
            return ErrorMessage.DEFAULT_LANG;
        }
    }

    private ErrorMessage getErrorMessageDetail(String errorCode) throws IOException {
        if (!errorMessages.containsKey(errorCode)) {
            String errorMessageStr = env.getProperty("error-message." + errorCode);
            if (StringUtils.isEmpty(errorMessageStr)) {
                return null;
            } else {
                ErrorMessage errorMessage = objectMapper.readValue(errorMessageStr, ErrorMessage.class);
                errorMessage.initLanguageMap();
                errorMessages.put(errorCode, errorMessage);
            }
        }
        return errorMessages.get(errorCode);
    }

    private ResponseEntity generalError(String language) throws IOException {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setCode(ResponseStatus.GENERAL_ERROR_CODE);
        ErrorMessage errorMessage = getErrorMessageDetail(ResponseStatus.GENERAL_ERROR_CODE);
        if (errorMessage == null) {
            responseStatus.setMessage(ResponseStatus.GENERAL_ERROR_MESSAGE);
        } else {
            responseStatus.setMessage(errorMessage.getMessage(language));
        }
        GeneralResponse<Object> responseObject = new GeneralResponse<>(responseStatus);
        return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
