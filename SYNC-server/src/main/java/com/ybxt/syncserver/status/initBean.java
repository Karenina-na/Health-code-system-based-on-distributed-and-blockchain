package com.ybxt.syncserver.status;

import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.BlockChainModel;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 初始化Bean
 *
 * @author 15399
 * @date 2022/10/13
 */
@Configuration
@Slf4j
public class initBean {

    @Resource
    private SyncConfig syncConfig;

    /**
     * 状态
     *
     * @return {@link status}
     */
    @Bean
    public status status(){
        log.info("init status");
        status status = new status();
        status.setStatus(StatusEnum.FOLLOW);
        return status;
    }

    /**
     * 块链模型
     *
     * @return {@link BlockChainModel}
     */
    @Bean
    public BlockChainModel blockChainModel(){
        log.info("init blockChainModel");
        BlockChainModel blockChainModel = new BlockChainModel();
        blockChainModel.createBlock("创世区块");
        return blockChainModel;
    }

    /**
     * 服务器信息
     *
     * @return {@link ServerModel}
     */
    @Bean
    public ServerModel serverModel(){
        log.info("init serverModel");
        ServerModel serverModel=new ServerModel();
        serverModel.setIP(syncConfig.getServerIP());
        serverModel.setPort(syncConfig.getPort());
        serverModel.setName(syncConfig.getServerName());
        serverModel.setColony(syncConfig.getServerColony());
        serverModel.setNamespace(syncConfig.getServerNamespace());
        return serverModel;
    }
}
