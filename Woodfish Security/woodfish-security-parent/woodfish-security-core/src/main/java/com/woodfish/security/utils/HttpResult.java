package com.woodfish.security.utils;

import com.woodfish.security.utils.enums.HttpStatusEnum;
import lombok.Data;

@Data
public class HttpResult {
    private Enum status;
    private String msg;
    private String data;

    public HttpResult() {
    }

    public HttpResult(String data) {
    }

    public HttpResult(Enum status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult isOk(String data){
        return new HttpResult(HttpStatusEnum.OK,"请求成功",data);
    }
}
