package com.jiuaoedu.evaluation.dto;

public class Result<T> {
    private int status;
    private String message;
    private T data;

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage("success!");
        result.setStatus(1);
        return result;
    }
    public static <T> Result<T> fail(String message){
        Result<T> result = new Result<>();
        result.setMessage(message);
        result.setStatus(1);
        return result;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
