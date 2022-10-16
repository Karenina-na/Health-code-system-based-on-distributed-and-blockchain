package com.ybxt.syncserver.thread.leaderThread;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.Code;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;

import java.util.List;

/**
 * Leader超时时间线程
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class LeaderTimeOutThread implements Runnable {
    private final FollowClient followClient;
    private final SyncConfig syncConfig;
    private final status status;
    private final ServerModelList serversModelList;

    public LeaderTimeOutThread(FollowClient followClient, SyncConfig syncConfig,
                                status status,ServerModelList serversModelList) {
        this.followClient = followClient;
        this.syncConfig = syncConfig;
        this.status = status;
        this.serversModelList = serversModelList;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("创建Leader超时时间线程");
        Thread.sleep(syncConfig.getLeaderTimeOut()*1000);
        synchronized (status.getStatus()){
            status.setStatus(StatusEnum.FOLLOW);
            log.info("Leader超时");
        }
        synchronized (serversModelList){
            List<ServerModel> serverModels = serversModelList.getServerModelList();
            serverModels.stream().iterator().forEachRemaining(serverModel -> {
                MessageResult ask = followClient.ask(serverModel, status.getLeader().getLeader());
                if (!ask.getCode().equals(new Code().getSuccess())){
                    log.info("Leader发送ask应答错误"+ask.getMessage()+" "+serverModel.getIP());
                }
            });
        }
    }
}
