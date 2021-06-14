package vn.plusplus.spring.springbootdemo.exception.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMessage {

    public static final String DEFAULT_LANG = "default";

    private String description;
    private List<MessageInLanguage> language;
    private final Map<String, String> messages = new HashMap<>();

    public void initLanguageMap() {
        for (MessageInLanguage messageInLanguage : language) {
            messages.put(messageInLanguage.getCode(), messageInLanguage.getMessage());
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage(String language) {
        return messages.get(language);
    }

    public void setLanguage(List<MessageInLanguage> language) {
        this.language = language;
    }
}

class MessageInLanguage {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
