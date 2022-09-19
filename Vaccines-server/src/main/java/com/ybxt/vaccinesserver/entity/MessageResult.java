package com.ybxt.vaccinesserver.entity;

import lombok.Data;

/**
 * 返回结果实体类
 */
@Data
public class MessageResult {
    private String code;
    private String message;
    private Object data;

    public void SuccessMessageResult(Object data) {
        this.code= String.valueOf(Code.OK);
        this.data=data;
    }

    public void ErrorMessageResult(String message) {
        this.code=String.valueOf(Code.ERR);
        this.message=message;
    }
}
