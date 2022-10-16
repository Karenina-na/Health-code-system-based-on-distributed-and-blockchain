package com.ybxt.syncserver.controller;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.block_chain.BlockChainModel;
import com.ybxt.syncserver.entity.block_chain.BlockChainTempQueue;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.thread.followThread.FollowAckDataSyncThread;
import com.ybxt.syncserver.thread.leaderThread.LeaderAckFollowSyncThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/themis/leader")
@Slf4j
public class LeaderController {

    @Resource
    private status status;

    @Resource
    private FollowClient followClient;
    @Resource
    private ServerModel serverModel;
    @Resource
    private BlockChainModel blockChainModel;
    @Resource
    private ServerModelList serversModelList;
    @Resource
    private BlockChainTempQueue blockChainTempQueue;

    /**
     * leader同意账本同步
     *
     * @param serverModel leader服务器
     * @return {@link MessageResult}
     */
    @PostMapping("/ack")
    private MessageResult ack(@RequestBody ServerModel serverModel){
        MessageResult messageResult = new MessageResult();
        if (!status.getLeader().getLeader().getIP().equals(serverModel.getIP())||
                !status.getLeader().getLeader().getPort().equals(serverModel.getPort())) {
            messageResult.ErrorMessageResult("leader服务器信息不匹配");
            return messageResult;
        }
        if (status.getStatus().equals( StatusEnum.LEADER)){
            synchronized (status.getLeader().getAckCount()){
                status.getLeader().setAckCount(status.getLeader().getAckCount()+1);
                if (status.getLeader().getAckCount()>=status.getLeader().getServerNum()/2){
                    new Thread(new LeaderAckFollowSyncThread(this.serverModel,followClient,serversModelList)).start();
                    new Thread(new FollowAckDataSyncThread(blockChainTempQueue,blockChainModel)).start();
                    log.info("leader同意账本同步");
                }
            }
            messageResult.SuccessMessageResult("leader收到回信");
            return messageResult;
        }
        messageResult.ErrorMessageResult("当前状态不是LEADER");
        return messageResult;
    }
}
