package com.ybxt.syncserver.status;

import com.ybxt.syncserver.client.Client;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.BlockChainModel;
import com.ybxt.syncserver.entity.BlockModel;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.thread.HeartBeatThread;
import com.ybxt.syncserver.thread.StatusBeginThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


/**
 * 初始化
 *
 * @author 15399
 * @date 2022/10/13
 */
@Component
@Slf4j
public class init {

    @Resource
    private ServerModel serverModel;
    @Resource
    private Client client;
    @Resource
    private status status;
    @Resource
    private BlockChainModel blockChainModel;
    @Resource
    private statusOperator statusOperator;
    @Resource
    private SyncConfig syncConfig;

    /**
     * 初始化状态
     */
    @PostConstruct
    private void initServer(){
        log.info("注册服务");
        boolean bool = client.ServerRegister(serverModel);
        if (!bool) {
            log.error("注册服务失败");
            System.exit(0);
        }

        log.info("初始化创世区块");
        BlockModel Genesis = blockChainModel.createBlock("begin");
        blockChainModel.addBlock(Genesis);

        log.info("初始化心跳线程");
        new Thread(new HeartBeatThread(client,serverModel,syncConfig)).start();

        log.info("初始化状态控制器线程");
        new Thread(new StatusBeginThread(status,statusOperator)).start();
    }
}
