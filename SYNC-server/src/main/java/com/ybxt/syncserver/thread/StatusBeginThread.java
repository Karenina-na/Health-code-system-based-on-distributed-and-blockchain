package com.ybxt.syncserver.thread;

import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.status.statusOperator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 状态控制器线程
 *
 * @author 15399
 * @date 2022/10/13
 */
@Slf4j
public class StatusBeginThread implements Runnable {

    private final status status;

    private final statusOperator statusOperator;

    public StatusBeginThread(status status, statusOperator statusOperator) {
        this.status = status;
        this.statusOperator = statusOperator;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            switch (status.getStatus()) {
                case StatusEnum.FOLLOW:
                    statusOperator.Follow();
                    break;
                case StatusEnum.CANDIDATE:
                    statusOperator.Candidate();
                    break;
                case StatusEnum.LEADER:
                    statusOperator.Leader();
                    break;
                case StatusEnum.CLOSE:
                    log.info("CLOSE");
                    return;
            }
        }
    }
}
