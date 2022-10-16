package com.ybxt.syncserver.client;

import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.status.FollowSyncDataModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class FollowClient {
    @Resource
    private SyncConfig config;

    @Resource
    private RestTemplate restTemplate;

    /**
     * Follow ack应答
     *
     * @param serverModel 服务器模式
     * @param leaderModel 领导模式
     * @return {@link MessageResult}
     */
    public MessageResult ack(ServerModel serverModel,ServerModel leaderModel) {
        String baseUrl =config.getThemisProtocol() + serverModel.getIP() + ":" + serverModel.getPort()
                + "/themis/follow/ack";
        return restTemplate.postForObject(baseUrl, leaderModel, MessageResult.class);
    }

    /**
     * Follow ask询问
     *
     * @param serverModel 服务器模式
     * @param leaderModel 领导模式
     * @return {@link MessageResult}
     */
    public MessageResult ask(ServerModel serverModel,ServerModel leaderModel) {
        String baseUrl =config.getThemisProtocol() + serverModel.getIP() + ":" + serverModel.getPort()
                + "/themis/follow/ask";
        return restTemplate.postForObject(baseUrl, leaderModel, MessageResult.class);
    }

    /**
     * Follow 数据同步
     *
     * @param serverModel 服务器模式
     * @param dataModel   数据模型
     * @return {@link MessageResult}
     */
    public MessageResult sync(ServerModel serverModel, FollowSyncDataModel dataModel) {
        String baseUrl =config.getThemisProtocol() + serverModel.getIP() + ":" + serverModel.getPort()
                + "/themis/follow/sync";
        return restTemplate.postForObject(baseUrl, dataModel, MessageResult.class);
    }

    /**
     * Follow心跳
     *
     * @param serverModel 服务器模式
     * @param leaderModel 领导模式
     * @return {@link MessageResult}
     */
    public MessageResult heartBeat(ServerModel serverModel,ServerModel leaderModel) {
        String baseUrl =config.getThemisProtocol() + serverModel.getIP() + ":" + serverModel.getPort()
                + "/themis/follow/heartBeat";
        return restTemplate.postForObject(baseUrl, leaderModel, MessageResult.class);
    }
}
