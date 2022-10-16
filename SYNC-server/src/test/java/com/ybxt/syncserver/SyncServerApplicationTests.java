package com.ybxt.syncserver;

import com.ybxt.syncserver.client.ThemisClient;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.ServerModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SyncServerApplicationTests {

    @Resource
    ThemisClient themisClient;

    @Resource
    SyncConfig config;

    @Test
    void ServerRegisterTest(){
        boolean f= themisClient.ServerRegister(new ServerModel(
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
        boolean f= themisClient.ServerHeartBeat(new ServerModel(
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
        ServerModel s= themisClient.GetLeaderServer(new ServerModel(
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
        List<ServerModel> s= themisClient.GetServerList(new ServerModel(
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
        Integer s= themisClient.GetServerCount(new ServerModel(
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
        Boolean b= themisClient.ElectLeaderServer(new ServerModel(
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
