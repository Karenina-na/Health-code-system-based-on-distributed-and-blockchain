package com.ybxt.traceserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.traceserver.client.PersonDataClient;
import com.ybxt.traceserver.dao.TraceDao;
import com.ybxt.traceserver.entity.Code;
import com.ybxt.traceserver.entity.MessageResult;
import com.ybxt.traceserver.entity.PersonData;
import com.ybxt.traceserver.entity.TraceData;
import com.ybxt.traceserver.service.TraceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TraceServiceImpl implements TraceService{

    @CreateCache(name = "GetTraceDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataById;
    @CreateCache(name = "GetTraceDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataByPersonID;
    @CreateCache(name = "GetTraceDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetTraceDataByIdentityID;

    @Resource
    private PersonDataClient personDataClient;

    @Resource
    private TraceDao traceDao;

    @Override
    public TraceData GetTraceDataById(String id) {
        TraceData traceData;
        String data=GetTraceDataById.get(Long.valueOf(id));
        if (data == null) {
            traceData=traceDao.queryTraceDataByID(id);
            GetTraceDataById.put(Long.valueOf(id), JSON.toJSONString(traceData));
        }else{
            traceData=JSON.parseObject(data,TraceData.class);
        }
        if (traceData==null){
            return null;
        }
        int ID= traceData.getPerson_id();
        MessageResult m=personDataClient.getPersonDataById(String.valueOf(ID));
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            traceData.setPersonData(personData);
            return traceData;
        }
        return null;
    }

    @Override
    public List<TraceData> GetTraceDataByPersonID(String person_id) {
        List<TraceData> traceDataList;
        String data=GetTraceDataByPersonID.get(Long.valueOf(person_id));
        if (data == null) {
            traceDataList=traceDao.queryTraceDataByPersonID(person_id);
            GetTraceDataByPersonID.put(Long.valueOf(person_id), JSON.toJSONString(traceDataList));
        }else{
            traceDataList=JSON.parseArray(data,TraceData.class);
        }
        if (traceDataList==null){
            return null;
        }
        MessageResult m=personDataClient.getPersonDataById(person_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            for (TraceData traceData : traceDataList) {
                String jsonObject = JSON.toJSONString( m.getData());
                PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
                traceData.setPersonData(personData);
            }
            return traceDataList;
        }
        return null;
    }

    @Override
    public List<TraceData> GetTraceDataByIdentityID(String identity_id) {
        MessageResult m = personDataClient.getPersonDataByIdCard(identity_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            List<TraceData> traceDataList;
            String data=GetTraceDataByIdentityID.get(Long.valueOf(identity_id));
            if (data == null) {
                traceDataList=traceDao.queryTraceDataByPersonID(String.valueOf(personData.getId()));
                GetTraceDataByIdentityID.put(Long.valueOf(identity_id), JSON.toJSONString(traceDataList));
            }else{
                traceDataList=JSON.parseArray(data,TraceData.class);
            }
            if (traceDataList==null){
                return null;
            }
            for (TraceData traceData : traceDataList) {
                traceData.setPersonData(personData);
            }
            return traceDataList;
        }
        return null;
    }
}
