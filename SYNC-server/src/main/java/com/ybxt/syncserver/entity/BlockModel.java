package com.ybxt.syncserver.entity;

import com.ybxt.syncserver.util.encryption;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 块模型
 *
 * @author 15399
 * @date 2022/10/13
 */
@Data
public class BlockModel {
    private Integer id;
    private String content;
    private long timeStamp;
    private String hash;
    private String previousHash;

    /**
     * 创建哈希
     */
    public void createHash(){
        this.timeStamp=System.currentTimeMillis();
        this.hash= encryption.getSHA256StrJava(id + content + timeStamp + previousHash);
    }

    /**
     * 检查哈希
     *
     * @return boolean
     */
    public boolean checkBlock(){
        return this.hash.equals(encryption.getSHA256StrJava(id + content + timeStamp + previousHash));
    }
}
