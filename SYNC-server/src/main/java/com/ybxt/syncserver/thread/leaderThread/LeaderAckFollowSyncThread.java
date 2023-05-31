package com.ybxt.syncserver.thread.leaderThread;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.message.ServerModelList;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Leader循环发送follow ack同步区块线程
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class LeaderAckFollowSyncThread implements Runnable {

    private final ServerModel serverModel;
    private final FollowClient followClient;
    private final ServerModelList serverModelList;

    public LeaderAckFollowSyncThread(ServerModel serverModel, FollowClient followClient,ServerModelList serverModelList) {
        this.serverModel = serverModel;
        this.followClient = followClient;
        this.serverModelList = serverModelList;
    }

    @Override
    public void run() {
        synchronized (serverModelList){
            List<ServerModel> serverModels = serverModelList.getServerModelList();
            serverModels.stream().iterator().forEachRemaining(model -> {
                MessageResult ack = followClient.ack(model, serverModel);
                log.info("leader发送ack给follow服务器:{}", model.getName()+"-"+ack.getMessage());
            });
        }
    }
}
