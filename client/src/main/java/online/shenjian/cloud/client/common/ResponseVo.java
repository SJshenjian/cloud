package online.shenjian.cloud.client.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public static ResponseVo message(int code, String message) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code);
        responseVo.setMessage(message);
        return responseVo;
    }

    public static ResponseVo message(ResponseCode responseCode) {
        ResponseVo responseVo = message(responseCode.val(), responseCode.des());
        return responseVo;
    }

    public static ResponseVo message(int code, String message, Object data) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code);
        responseVo.setMessage(message);
        responseVo.setData(data);
        return responseVo;
    }

    public static ResponseVo success() {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseCode.SUCCESS.val());
        return responseVo;
    }

    public static ResponseVo success(Object data) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseCode.SUCCESS.val());
        responseVo.setData(data);
        return responseVo;
    }

    public static ResponseVo error(String message) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseCode.FAIL.val());
        responseVo.setMessage(message);
        return responseVo;
    }
}