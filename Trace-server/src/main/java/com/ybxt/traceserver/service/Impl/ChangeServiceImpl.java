package com.ybxt.traceserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.traceserver.client.PersonDataClient;
import com.ybxt.traceserver.dao.ChangeDataDao;
import com.ybxt.traceserver.dao.TraceDao;
import com.ybxt.traceserver.entity.Code;
import com.ybxt.traceserver.entity.MessageResult;
import com.ybxt.traceserver.entity.PersonData;
import com.ybxt.traceserver.entity.TraceData;
import com.ybxt.traceserver.service.ChangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChangeServiceImpl implements ChangeService {

    @CreateCache(name = "GetTraceDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataById;
    @CreateCache(name = "GetTraceDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataByPersonID;
    @CreateCache(name = "GetTraceDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataByIdentityID;

    @Resource
    private TraceDao traceDao;

    @Resource
    private ChangeDataDao changeDataDao;

    @Resource
    private PersonDataClient personDataClient;

    @Override
    public boolean insertTraceData(TraceData TraceData){
        int index=changeDataDao.insertTraceData(TraceData);
        return index == 1;
    }

    @Override
    public boolean deleteTraceData(String id) {
        TraceData TraceData =traceDao.queryTraceDataByID(id);
        if (TraceData==null){
            return false;
        }
        int index=changeDataDao.deleteTraceData(id);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(TraceData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetTraceDataById.remove(Long.parseLong(id));
            GetTraceDataByPersonID.remove(Long.valueOf(TraceData.getPerson_id()));
            GetTraceDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateTraceData(TraceData TraceData) {
        TraceData oldTraceData =traceDao.queryTraceDataByID(String.valueOf(TraceData.getId()));
        if (oldTraceData==null){
            return false;
        }
        int index=changeDataDao.updateTraceData(TraceData);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(TraceData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetTraceDataById.remove(Long.valueOf(TraceData.getId()));
            GetTraceDataByPersonID.remove(Long.valueOf(TraceData.getPerson_id()));
            GetTraceDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }
}