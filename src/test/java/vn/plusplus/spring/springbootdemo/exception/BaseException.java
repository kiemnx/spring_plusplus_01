package vn.plusplus.spring.springbootdemo.exception;

import java.util.List;

public class BaseException extends RuntimeException {
    private ErrorCode code; // contains http_code and error_code
    private String[] messageParams = new String[0];  // contains value for placeholders of message
    private String logMessage; // custom log message

    public BaseException(ErrorCode code) {
        super(code.code());
        this.code = code;
    }

    public BaseException(ErrorCode code, List<String> messageParams) {
        super(code.code());
        this.code = code;
        this.messageParams = messageParams.toArray(new String[0]);
    }

    public BaseException(ErrorCode code, String logMessage) {
        super(code.code());
        this.code = code;
        this.logMessage = logMessage;
    }

    public ErrorCode getCode() {
        return code;
    }

    public void setCode(ErrorCode code) {
        this.code = code;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public String[] getMessageParams() {
        return messageParams;
    }
}
