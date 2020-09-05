package xyz.anfun.customer_service.netty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class ChatMessage {
    private MessageType type;
    private String to;
    private Object content;
    private String sender;

    public ChatMessage() {

    }

    public ChatMessage(Object content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}
