package xyz.anfun.customer_service.util;


import lombok.Data;

@Data
public class Response {
    private int status;
    private String message;
    private Object data;
    private String error;

    public Response() { }

    public Response(int status, Object data, String message, String error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public static Response successResponse(Object dada){
        Response response = new Response();
        response.setData(dada);
        response.setStatus(200);
        return response;
    }

    public static Response errResponse(int code, String err){
        Response response = new Response();
        response.setStatus(code);
        response.setError(err);
        return response;
    }
}
