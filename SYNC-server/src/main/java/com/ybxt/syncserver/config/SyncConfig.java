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
     * udp端口号
     */
    @Value("${themis.server.udo-port}")
    private Integer UDPPort;

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
    @Value("${themis.follow-status-timeout}")
    private Integer FollowStatusTimeout;

    /**
     * themis心跳超时
     */
    @Value("${themis.heartbeat-timeout}")
    private Integer ThemisHeartBeatTimeout;

    /**
     * Leader发送心跳间隔
     */
    @Value("${themis.leader.heartbeat-timeout}")
    private Integer LeaderHeartBeatTimeout;

    /**
     * Leader超时时间
     */
    @Value("${themis.leader.time-out}")
    private Integer LeaderTimeOut;

    /**
     * Leader同步server列表
     */
    @Value("${themis.leader.server-List-time}")
    private Integer LeaderSyncListTime;
}
