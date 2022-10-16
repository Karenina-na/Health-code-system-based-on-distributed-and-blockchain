package com.ybxt.syncserver.thread.themisThread;

import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Themis心跳线程
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class ThemisHeartBeatThread implements Runnable {

    private final ThemisClient themisClient;
    private final ServerModel serverModel;
    private final SyncConfig syncConfig;

    public ThemisHeartBeatThread(ThemisClient themisClient, ServerModel serverModel, SyncConfig syncConfig) {
        this.themisClient = themisClient;
        this.serverModel = serverModel;
        this.syncConfig = syncConfig;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(syncConfig.getThemisHeartBeatTimeout());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = themisClient.ServerHeartBeat(serverModel);
            if (!b) {
                log.warn("心跳发送失败");
            }
        }
    }
}
