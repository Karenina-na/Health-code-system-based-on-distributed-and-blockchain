package com.ybxt.syncserver.controller;

import com.ybxt.syncserver.client.LeaderClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.*;
import com.ybxt.syncserver.entity.block_chain.BlockChainModel;
import com.ybxt.syncserver.entity.block_chain.BlockChainTempQueue;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.message.Code;
import com.ybxt.syncserver.entity.status.FollowSyncDataModel;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.thread.followThread.FollowAckDataSyncThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/themis/follow")
@Slf4j
public class FollowController {
    @Resource
    private status status;
    @Resource
    private SyncConfig syncConfig;
    @Resource
    private BlockChainModel blockChainModel;
    @Resource
    private LeaderClient leaderClient;
    @Resource
    private BlockChainTempQueue blockChainTempQueue;

    /**
     * 重置心跳超时时间
     *
     * @param serverModel leader服务器
     * @return {@link MessageResult}
     */
    @PostMapping("/heartBeat")
    public MessageResult heartBeat(@RequestBody ServerModel serverModel) {
        MessageResult messageResult = new MessageResult();
        if (!status.getLeader().getLeader().getIP().equals(serverModel.getIP())||
                !status.getLeader().getLeader().getPort().equals(serverModel.getPort())) {
            messageResult.ErrorMessageResult("leader服务器信息不匹配");
            log.info("leader服务器信息不匹配");
            return messageResult;
        }
        if (status.getStatus().equals(StatusEnum.FOLLOW)){
            status.getLock().lock();
            status.setTimeout(syncConfig.getFollowStatusTimeout());
            status.getLock().unlock();
            messageResult.SuccessMessageResult("重置心跳超时时间成功");
            return messageResult;
        }
        messageResult.ErrorMessageResult("当前状态不是FOLLOW");
        return messageResult;
    }

    /**
     * 数据同步
     *
     * @param dataModel leader服务器
     * @return {@link MessageResult}
     */
    @PostMapping("/sync")
    public MessageResult sync(@RequestBody FollowSyncDataModel dataModel){
        MessageResult messageResult = new MessageResult();
        if (!status.getLeader().getLeader().getIP().equals(dataModel.getLeader().getIP())||
                !status.getLeader().getLeader().getPort().equals(dataModel.getLeader().getIP())) {
            messageResult.ErrorMessageResult("leader服务器信息不匹配");
            return messageResult;
        }
        if (status.getStatus().equals( StatusEnum.FOLLOW)){
            status.getLock().lock();
            status.setTimeout(syncConfig.getFollowStatusTimeout());
            status.getLock().unlock();
            BlockModel blockModel=dataModel.getBlockModel();
            boolean bool = blockModel.checkBlock();
            if (!bool){
                messageResult.ErrorMessageResult("数据校验失败-数据不完整");
                return messageResult;
            }
            if (!blockModel.getPreviousHash().equals(blockChainTempQueue.getHash())){
                messageResult.ErrorMessageResult("数据校验失败-前hash值不匹配");
                return messageResult;
            }
            synchronized (blockChainTempQueue){
                blockChainTempQueue.AddBlock(blockModel);
            }
            messageResult.SuccessMessageResult("数据同步成功");
            return messageResult;
        }
        messageResult.ErrorMessageResult("当前状态不是FOLLOW");
        return messageResult;
    }

    /**
     *
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
        if (status.getStatus().equals( StatusEnum.FOLLOW)){
            new Thread(new FollowAckDataSyncThread(blockChainTempQueue,blockChainModel)).start();
            messageResult.SuccessMessageResult("创建同步数据协程成功");
            return messageResult;
        }
        messageResult.ErrorMessageResult("当前状态不是FOLLOW");
        return messageResult;
    }

    /**
     * leader 告知follow
     *
     * @param serverModel 服务器模式
     * @return {@link MessageResult}
     */
    @PostMapping("/ask")
    private MessageResult ask(@RequestBody ServerModel serverModel){
        MessageResult messageResult = new MessageResult();
        if (!status.getLeader().getLeader().getIP().equals(serverModel.getIP())||
                !status.getLeader().getLeader().getPort().equals(serverModel.getPort())) {
            messageResult.ErrorMessageResult("leader服务器信息不匹配");
            return messageResult;
        }
        if (status.getStatus().equals( StatusEnum.FOLLOW)){
            MessageResult ack = leaderClient.ack(status.getLeader().getLeader(),
                    status.getLeader().getLeader());
            if (ack.getCode().equals(new Code().getSuccess())){
                messageResult.SuccessMessageResult("leader服务器同意账本同步");
            }
            return messageResult;
        }
        messageResult.ErrorMessageResult("当前状态不是FOLLOW");
        return messageResult;
    }
}
