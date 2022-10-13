package com.ybxt.syncserver.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 *
 * @author 15399
 * @date 2022/10/12
 */
@Configuration
@Data
public class SyncConfig {

    /**
     * 服务名
     */
    @Value("${themis.server.name}")
    private String ServerName;
    /**
     * 服务ip
     */
    @Value("${themis.server.ip}")
    private String ServerIP;
    /**
     * 端口号
     */
    @Value("${server.port}")
    private String Port;
    /**
     * 集群
     */
    @Value("${themis.server.colony}")
    private String ServerColony;
    /**
     * 命名空间
     */
    @Value("${themis.server.namespace}")
    private String ServerNamespace;

    /**
     * themisIP
     */
    @Value("${themis.address.ip}")
    private String ThemisIP;

    /**
     * themisPort
     */
    @Value("${themis.address.port}")
    private String ThemisPort;

    /**
     * Themis 协议
     */
    @Value("${themis.address.protocol}")
    private String ThemisProtocol;

    /**
     * Themis URL前缀
     */
    @Value("${themis.address.prefix}")
    private String ThemisPrefix;

    /**
     * 状态超时
     */
    @Value("${themis.status-timeout}")
    private Integer StatusTimeout;

    /**
     * 心跳超时
     */
    @Value("${themis.heartbeat-timeout}")
    private Integer HeartBeatTimeout;
}
