package com.ybxt.syncserver.entity.message;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Code {
    private String success="20010";
    private String error="20011";

    public boolean isSuccess(String code){
        return code.equals(success);
    }

    public boolean isError(String code){
        return code.equals(error);
    }
}
