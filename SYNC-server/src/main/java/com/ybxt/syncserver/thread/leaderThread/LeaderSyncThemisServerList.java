package com.ybxt.syncserver.thread.leaderThread;

import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Themis-Leader同步服务器列表
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class LeaderSyncThemisServerList implements Runnable {

    private final SyncConfig syncConfig;
    private final ServerModel serverModel;
    private final ThemisClient themisClient;
    private final status status;
    private final ServerModelList serverModelList;

    public LeaderSyncThemisServerList(SyncConfig syncConfig,ServerModelList serverModelList,
                                      ServerModel serverModel, ThemisClient themisClient,
                                      status status) {
        this.syncConfig = syncConfig;
        this.serverModelList = serverModelList;
        this.serverModel = serverModel;
        this.themisClient = themisClient;
        this.status = status;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("创建同步服务器列表线程");
        while (true){
            if (!status.getStatus().equals(StatusEnum.LEADER)){
                return;
            }
            synchronized (serverModelList){
                List<ServerModel> serverModels = themisClient.GetServerList(this.serverModel);
                serverModelList.setServerModelList(serverModels);
            }
            Thread.sleep(syncConfig.getLeaderSyncListTime());
        }
    }
}
