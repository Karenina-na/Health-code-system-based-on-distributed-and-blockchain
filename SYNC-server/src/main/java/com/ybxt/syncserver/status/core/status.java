package com.ybxt.syncserver.status.core;

import com.ybxt.syncserver.entity.status.LeaderStatusModel;
import lombok.Data;

import java.util.concurrent.locks.Lock;

@Data
public class status {
    private String status;
    private Lock lock=new java.util.concurrent.locks.ReentrantLock();
    private LeaderStatusModel leader=new LeaderStatusModel();
    private Integer Timeout;
    private Integer IP;
}
