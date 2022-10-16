package com.ybxt.syncserver;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.block_chain.BlockChainTempQueue;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.thread.leaderThread.LeaderSyncFollowDataThread;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对外操作
 *
 * @author 15399
 * @date 2022/10/15
 */
@Component
public class operator {

    @Resource
    private BlockChainTempQueue blockChainTempQueue;
    @Resource
    private   FollowClient followClient;
    @Resource
    private  ServerModel serverModel;
    @Resource
    private ServerModelList serverModelList;

    /**
     * 添加块
     *
     * @param content 内容
     */
    public void AddBlock(String content){
        BlockModel blockModel=new BlockModel();
        blockModel.setContent(content);
        blockModel.setHash(blockChainTempQueue.getHash());
        blockModel.createHash();
        blockChainTempQueue.AddBlock(blockModel);
        new Thread(new LeaderSyncFollowDataThread(followClient,serverModel,serverModelList,blockModel)).start();
    }
}
