package com.ybxt.syncserver.thread.leaderThread;

import com.ybxt.syncserver.client.FollowClient;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.message.Code;
import com.ybxt.syncserver.entity.message.MessageResult;
import com.ybxt.syncserver.entity.message.ServerModelList;
import com.ybxt.syncserver.entity.status.FollowSyncDataModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Leader发送Follow数据线程同步
 *
 * @author 15399
 * @date 2022/10/15
 */
@Slf4j
public class LeaderSyncFollowDataThread implements Runnable {

    private  final FollowClient followClient;
    private final  ServerModel serverModel;
    private final ServerModelList serverModelList;
    private final BlockModel blockModel;


    public LeaderSyncFollowDataThread(FollowClient followClient, ServerModel serverModel,
                                      ServerModelList serverModelList,BlockModel blockModel ) {
        this.followClient = followClient;
        this.serverModel = serverModel;
        this.serverModelList = serverModelList;
        this.blockModel = blockModel;
    }

    @Override
    public void run() {
        synchronized (serverModelList){
            List<ServerModel> serverModels=serverModelList.getServerModelList();
            serverModels.stream().iterator().forEachRemaining(model -> {
                FollowSyncDataModel Data=new FollowSyncDataModel();
                Data.setBlockModel(blockModel);
                Data.setLeader(serverModel);
                MessageResult messageResult = followClient.sync(model, Data);
                if (!messageResult.getCode().equals(new Code().getSuccess())){
                    log.info("Leader发送sync应答错误"+messageResult.getMessage()+" "+model.getIP());
                }
            });
        }
    }
}
