package com.ybxt.traceserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

    @Resource
    private PersonDataClient personDataClient;

    @Resource
    private TraceDao traceDao;

    @Override
    public TraceData GetTraceDataById(String id) {
        TraceData traceData = traceDao.queryTraceDataByID(id);
        if (traceData == null) {
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
        List<TraceData> traceDataList=traceDao.queryTraceDataByPersonID(person_id);
        if (traceDataList == null) {
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
            List<TraceData> traceDataList = traceDao.queryTraceDataByPersonID(
                    String.valueOf(personData.getId()));
            if (traceDataList == null) {
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
