package com.ybxt.syncserver.thread;

import com.ybxt.syncserver.client.Client;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatThread implements Runnable {

    private final Client client;
    private final ServerModel serverModel;
    private final SyncConfig syncConfig;

    public HeartBeatThread(Client client, ServerModel serverModel, SyncConfig syncConfig) {
        this.client = client;
        this.serverModel = serverModel;
        this.syncConfig = syncConfig;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(syncConfig.getHeartBeatTimeout());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = client.ServerHeartBeat(serverModel);
            if (!b) {
                log.warn("心跳发送失败");
            }
        }
    }
}
