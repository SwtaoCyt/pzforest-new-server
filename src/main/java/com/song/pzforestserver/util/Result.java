package com.song.pzforestserver.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// ��Ӧ���������
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // ״̬��
    private int code;

    // ״̬��Ϣ
    private String message;

    // ����
    private T data;

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    // �ɹ�����Ӧ���
    public static <T> Result<T> success() {
        return new Result<>(200, "OK", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "OK", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    // ʧ�ܵ���Ӧ���
    public static <T> Result<T> error() {
        return new Result<>(500, "Internal Server Error", null);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }

    // ת��ΪMap
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return map;
    }
}
