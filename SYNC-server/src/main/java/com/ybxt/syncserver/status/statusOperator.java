package com.ybxt.syncserver.status;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.Code;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.thread.leaderThread.LeaderSyncThemisServerList;
import com.ybxt.syncserver.thread.leaderThread.LeaderTimeOutThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class statusOperator {

    @Resource
    private status status;

    @Resource
    private SyncConfig syncConfig;

    @Resource
    private ThemisClient themisClient;

    @Resource
    private ServerModel serverModel;

    @Resource
    private FollowClient followClient;
    @Resource
    private ServerModelList serversModelList;

    /**
     * FOLLOW
     *
     * @throws InterruptedException 中断异常
     */
    public void Follow() throws InterruptedException {
        log.info("开始FOLLOW");
        status.setTimeout(syncConfig.getFollowStatusTimeout());
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            status.getLock().lock();
            status.setTimeout(status.getTimeout() - 1);
            if (status.getTimeout() <= 0) {
                log.info("FOLLOW 超时");
                status.setStatus(StatusEnum.CANDIDATE);
                status.getLock().unlock();
                return;
            }
            status.getLock().unlock();
        }
    }

    /**
     * CANDIDATE
     *
     * @throws InterruptedException 中断异常
     */
    public void Candidate() throws InterruptedException {
        log.info("CANDIDATE调用选举接口");
        status.setStatus(StatusEnum.FOLLOW);
        Thread.sleep(10);
        Boolean flag = themisClient.ElectLeaderServer(serverModel);
        if (flag) {
            log.info("发送选举请求成功");
        } else {
            log.info("发送选举请求失败");
        }
        Thread.sleep(2000);
    }

    /**
     * LEADER
     *
     * @throws InterruptedException 中断异常
     */
    public void Leader() throws InterruptedException {
        log.info("开始LEADER");
        new Thread(new LeaderTimeOutThread(followClient, syncConfig, status,serversModelList)).start();
        new Thread(new LeaderSyncThemisServerList(syncConfig, serversModelList, serverModel, themisClient, status)).start();
        while (true) {
            if (!status.getStatus().equals(StatusEnum.LEADER)){
                return;
            }
            Thread.sleep(syncConfig.getLeaderHeartBeatTimeout());
            synchronized (serversModelList) {
                List<ServerModel> serverModels = serversModelList.getServerModelList();
                serverModels.stream().iterator().forEachRemaining(model -> {
                    MessageResult messageResult = followClient.heartBeat(model, serverModel);
                    if (!messageResult.getCode().equals(new Code().getSuccess())) {
                        log.info("心跳失败:"+model.getName()+" "+model.getIP());
                    }
                });
            }
        }
    }
}
