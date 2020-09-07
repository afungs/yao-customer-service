package xyz.anfun.customer_service.netty.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
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

    public ChatMessage(MessageType type, Object content, String sender, String to) {
        this.type = type;
        this.to = to;
        this.content = content;
        this.sender = sender;
    }

    public ChatMessage(Object content, String sender) {
        this.content = content;
        this.sender = sender;
    }
}
