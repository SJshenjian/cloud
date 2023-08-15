package online.shenjian.cloud.client.common;

public enum ResponseCode {

    SUCCESS(200, "成功"),
    FAIL(-1, "失败"),
    UN_AUTHORIZED(401, "用户未授权,请联系管理员"),
    TOKEN_EXPIRATION(402, "登录已过期,请退出后重新登录"),

    LICENSE_EXPIRED(403, "授权已失效");

    private Integer val;
    private String des;

    ResponseCode(Integer val, String des) {
        this.val = val;
        this.des = des;
    }

    public Integer val() {
        return val;
    }

    public String des() {
        return des;
    }
}