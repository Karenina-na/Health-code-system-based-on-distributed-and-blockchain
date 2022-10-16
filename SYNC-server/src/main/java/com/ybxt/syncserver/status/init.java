package com.ybxt.syncserver.status;

import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.block_chain.BlockChainModel;
import com.ybxt.syncserver.entity.block_chain.BlockModel;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.status.core.status;
import com.ybxt.syncserver.thread.themisThread.ThemisHeartBeatThread;
import com.ybxt.syncserver.thread.StatusBeginThread;
import com.ybxt.syncserver.thread.themisThread.UdpListenerThread;
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
    private ThemisClient themisClient;
    @Resource
    private status status;
    @Resource
    private BlockChainModel blockChainModel;
    @Resource
    private statusOperator statusOperator;
    @Resource
    private SyncConfig syncConfig;
    @Resource
    private ThemisClient client;

    /**
     * 初始化状态
     */
    @PostConstruct
    private void initServer(){
        log.info("注册服务");
        boolean bool = themisClient.ServerRegister(serverModel);
        if (!bool) {
            log.error("注册服务失败");
            System.exit(0);
        }

        log.info("初始化创世区块");
        BlockModel Genesis = blockChainModel.createBlock("begin");
        blockChainModel.addBlock(Genesis);

        log.info("初始化心跳线程");
        new Thread(new ThemisHeartBeatThread(themisClient,serverModel,syncConfig)).start();

        log.info("初始化themis-udp接收线程");
        new Thread(new UdpListenerThread(syncConfig,status,serverModel,client)).start();

        log.info("初始化状态控制器线程");
        new Thread(new StatusBeginThread(status,statusOperator)).start();
    }
}
