package com.ybxt.syncserver.entity.message;

import lombok.Data;

/**
 * 消息结果
 * 返回结果实体类
 *
 * @author 15399
 * @date 2022/10/15
 */
@Data
public class MessageResult {
    private String code;
    private String message;
    private Object data;
    private Code c=new Code();

    public void SuccessMessageResult(Object data) {
        this.code= String.valueOf(c.getSuccess());
        this.data=data;
    }

    public void ErrorMessageResult(String message) {
        this.code=String.valueOf(c.getError());
        this.message=message;
    }
}
