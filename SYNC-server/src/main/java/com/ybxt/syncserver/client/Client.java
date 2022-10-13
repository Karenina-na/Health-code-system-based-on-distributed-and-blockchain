package com.ybxt.syncserver.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ybxt.syncserver.config.SyncConfig;
import com.ybxt.syncserver.entity.Code;
import com.ybxt.syncserver.entity.ServerModel;
import com.ybxt.syncserver.entity.ThemisResultModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户端
 *
 * @author 15399
 * @date 2022/10/12
 */
@Component
public  class Client {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private Code code;

    @Resource
    private SyncConfig config;

    /**
     * 服务器注册
     *
     * @param s 服务器信息
     * @return boolean
     */
    public boolean ServerRegister(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/register";
        ThemisResultModel result = restTemplate.postForObject(url, s, ThemisResultModel.class);
        return result != null && code.isSuccess(result.getCode());
    }

    /**
     * 服务器心跳
     *
     * @param s 服务器信息
     * @return boolean
     */
    public  boolean ServerHeartBeat(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/beat";
        restTemplate.put(url,s);
        return true;
    }

    /**
     * 得到领导服务器
     *
     * @param s 服务器信息
     * @return {@link ServerModel}
     */
    public  ServerModel GetLeaderServer(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/getLeader";
        ThemisResultModel result = restTemplate.postForObject(url,s,ThemisResultModel.class);
        if(result==null||!code.isSuccess(result.getCode())) {
            return null;
        }
        String jsonObject = JSON.toJSONString( result.getData());
        ServerModel server=JSONObject.parseObject(jsonObject, ServerModel.class);
        if (server.getIP()!=null && server.getPort()!=null&&
                !"".equals(server.getIP()) &&  !"".equals(server.getIP())) {
            return server;
        }
        return null;
    }

    /**
     * 获取服务器列表
     *
     * @param s 服务器信息
     * @return {@link List}<{@link ServerModel}>
     */
    public List<ServerModel> GetServerList(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/getServers";
        ThemisResultModel result = restTemplate.postForObject(url,s,ThemisResultModel.class);
        if(result==null||!code.isSuccess(result.getCode())) {
            return null;
        }
        String jsonObject = JSON.toJSONString( result.getData());
        List<ServerModel> servers=JSONObject.parseArray(jsonObject, ServerModel.class);
        if (servers!=null && servers.size()>0) {
            return servers;
        }
        return null;
    }

    /**
     * 获得服务器数
     *
     * @param s 服务器信息
     * @return {@link Integer}
     */
    public Integer GetServerCount(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/getServersNum";
        ThemisResultModel result = restTemplate.postForObject(url,s,ThemisResultModel.class);
        if(result==null||!code.isSuccess(result.getCode())) {
            return null;
        }
        String jsonObject = JSON.toJSONString( result.getData());
        return JSONObject.parseObject(jsonObject, Integer.class);
    }

    /**
     * 选出领袖服务器
     *
     * @param s 服务器信息
     * @return {@link Boolean}
     */
    public Boolean ElectLeaderServer(ServerModel s){
        String  baseURL=config.getThemisProtocol()+config.getThemisIP()+":"+config.getThemisPort()+
                config.getThemisPrefix();
        String url=baseURL+"/message/election";
        restTemplate.put(url,s);
        return true;
    }
}
