package com.ybxt.syncserver.thread.followThread;

import com.ybxt.syncserver.entity.block_chain.BlockChainModel;
import com.ybxt.syncserver.entity.block_chain.BlockChainTempQueue;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Follow数据同步线程
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class FollowAckDataSyncThread implements Runnable {

    private BlockChainTempQueue blockChainTempQueue;
    private final BlockChainModel blockChainModel;

    public FollowAckDataSyncThread(BlockChainTempQueue blockChainTempQueue,BlockChainModel blockChainModel) {
        this.blockChainTempQueue = blockChainTempQueue;
        this.blockChainModel = blockChainModel;
    }

    @Override
    public void run() {
        Queue<BlockModel> temp;
        synchronized (blockChainTempQueue){
            temp=blockChainTempQueue.getBlockChainTempQueue();
            blockChainTempQueue.setBlockChainTempQueue(new LinkedBlockingDeque<>());
        }
        temp.stream().iterator().forEachRemaining(blockChainModel::addBlock);
        log.info("数据同步完成");
    }
}
