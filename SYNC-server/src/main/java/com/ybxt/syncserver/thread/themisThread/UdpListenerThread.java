package com.ybxt.syncserver.thread.themisThread;

import com.alibaba.fastjson.JSONObject;
import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.status.core.StatusEnum;
import com.ybxt.syncserver.status.core.status;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * udp-themis广播leader
 *
 * @author 15399
 * @date 2022/10/14
 */
@Slf4j
public class UdpListenerThread implements Runnable {

    /**
     * 消息队列
     */
    private final SyncConfig syncConfig;
    private final status status;
    private final ServerModel serverModel;
    private final ThemisClient client;

    public UdpListenerThread( SyncConfig syncConfig, status status,ServerModel serverModel, ThemisClient client) {
        this.syncConfig = syncConfig;
        this.status = status;
        this.serverModel = serverModel;
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void run() {
        DatagramSocket ds=new DatagramSocket(syncConfig.getUDPPort()); //接收端口号的消息
        while(true){
            byte[] bys=new byte[1024];
            DatagramPacket dp=new DatagramPacket(bys,bys.length);
            ds.receive(dp);
            String jsonObject=new String(dp.getData(),0,dp.getLength());
            ServerModel serverModel = JSONObject.parseObject(jsonObject, ServerModel.class);
            log.info("接收到选举广播消息:{}",serverModel);
            if (serverModel.getIP().equals(syncConfig.getServerIP())
                    && serverModel.getPort().equals(syncConfig.getPort())) {
                status.getLeader().setLeader(this.serverModel);
                status.getLeader().setServerNum(client.GetServerCount(serverModel));
                status.getLeader().setAckCount(0);
                status.setStatus(StatusEnum.LEADER);
            }else{
                status.getLeader().setLeader(serverModel);
                status.getLeader().setServerNum(0);
                status.getLeader().setAckCount(0);
                status.setStatus(StatusEnum.FOLLOW);
            }
        }
    }
}
