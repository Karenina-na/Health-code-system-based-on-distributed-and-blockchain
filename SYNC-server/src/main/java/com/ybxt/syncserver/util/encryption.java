package com.ybxt.syncserver.util;
import org.apache.commons.codec.digest.DigestUtils;

public class encryption {

    //SHA256
    public static String getSHA256StrJava(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
