package com.ybxt.syncserver;

import com.ybxt.syncserver.client.Client;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SyncServerApplicationTests {

    @Resource
    Client client;

    @Resource
    SyncConfig config;

    @Test
    void ServerRegisterTest(){
        boolean f=client.ServerRegister(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(f);
    }

    @Test
    void ServerHeartBeatTest(){
        boolean f=client.ServerHeartBeat(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(f);
    }

    @Test
    void GerLeaderTest(){
        ServerModel s=client.GetLeaderServer(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(s);
    }

    @Test
    void GetServerListTest(){
        List<ServerModel> s=client.GetServerList(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(s);
    }

    @Test
    void GetServerListNumTest(){
        Integer s=client.GetServerCount(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(s);
    }

    @Test
    void ElectionTest(){
        Boolean b=client.ElectLeaderServer(new ServerModel(
                config.getServerIP(),
                config.getPort(),
                config.getServerName(),
                null,
                config.getServerColony(),
                config.getServerNamespace()
        ));
        System.out.println(b);
    }
}
