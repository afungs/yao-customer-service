package xyz.anfun.customer_service.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PageResponse {
    private int status;
    private String message;
    private Object data;
    private String error;
    // 每页数量
    @JsonProperty("page_size")
    private int pageSize;
    // 当前页
    @JsonProperty("page_no")
    private int pageNo;
    // 总页数
    private long total;

    public PageResponse() {
    }

    public PageResponse(int status, Object data, int pageSize, int pageNo, long total, String message, String error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.total = total;
    }
}
