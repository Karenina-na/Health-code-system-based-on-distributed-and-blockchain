package com.ybxt.syncserver.client;

import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.MessageResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class LeaderClient {

    @Resource
    private SyncConfig config;

    @Resource
    private RestTemplate restTemplate;

    /**
     * Leader ack应答
     *
     * @param serverModel 服务器信息
     * @param leaderModel leader
     * @return {@link MessageResult}
     */
    public MessageResult ack(ServerModel serverModel, ServerModel leaderModel) {
        String baseUrl =config.getThemisProtocol() + serverModel.getIP() + ":" + serverModel.getPort()
                + "/themis/leader/ack";
        return restTemplate.postForObject(baseUrl, leaderModel, MessageResult.class);
    }
}
