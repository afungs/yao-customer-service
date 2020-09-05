package xyz.anfun.customer_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Component;
import xyz.anfun.customer_service.netty.model.ChatMessage;

import java.util.Map;

public class JSONUtils {
    private static ObjectMapper mapper;

    public static ObjectMapper getMapper() {
        synchronized (JSONUtils.class){
            if (mapper == null){
                mapper = SpringContextUtil.getBean(ObjectMapper.class);
            }
        }
        return mapper;
    }

    public static String objectToString(Object object) throws JsonProcessingException {
        return getMapper().writeValueAsString(object);
    }

    public static JsonNode jsonToObject(String json) throws JsonProcessingException {
        return getMapper().readValue(json, JsonNode.class);
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return getMapper().readValue(json, typeReference);
    }


    public static Map jsonToMap(String json) throws JsonProcessingException {
        return getMapper().readValue(json, Map.class);
    }

    public static ChatMessage jsonToChatMessage(String json) throws JsonProcessingException {
        return getMapper().readValue(json, ChatMessage.class);
    }
}
