package com.ybxt.syncserver.status;

import com.ybxt.syncserver.client.Client;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class statusOperator {

    @Resource
    private status status;

    @Resource
    private SyncConfig syncConfig;

    @Resource
    private Client client;

    @Resource
    private ServerModel serverModel;

    /**
     * FOLLOW
     *
     * @throws InterruptedException 中断异常
     */
    public void Follow() throws InterruptedException {
        log.info("开始FOLLOW");
        status.setTimeout(syncConfig.getStatusTimeout());
        while (true) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            status.setTimeout(status.getTimeout() - 1);
            if (status.getTimeout() <= 0) {
                log.info("FOLLOW 超时");
                status.setStatus(StatusEnum.CANDIDATE);
                return;
            }
        }
    }

    /**
     * CANDIDATE
     *
     * @throws InterruptedException 中断异常
     */
    public void Candidate() throws InterruptedException {
        log.info("CANDIDATE调用选举接口");
        Boolean flag = client.ElectLeaderServer(serverModel);
        status.setStatus(StatusEnum.FOLLOW);
        if (flag) {
            log.info("发送选举请求成功");
        } else {
            log.info("发送选举请求失败");
        }
    }
}
