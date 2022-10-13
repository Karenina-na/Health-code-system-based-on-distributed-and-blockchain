package com.ybxt.syncserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务模型
 *
 * @author 15399
 * @date 2022/10/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerModel {

    /**
     * ip
     */
    private String IP;
    /**
     * 端口
     */
    private String port;
    /**
     * 名字
     */
    private String name;
    /**
     * 时间
     */
    private String time;
    /**
     * 集群
     */
    private String colony;
    /**
     * 命名空间
     */
    private String namespace;
}
