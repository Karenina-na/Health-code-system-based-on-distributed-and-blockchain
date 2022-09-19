package com.ybxt.vaccinesserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.vaccinesserver.client.PersonDataClient;
import com.ybxt.vaccinesserver.dao.VaccinesDao;
import com.ybxt.vaccinesserver.entity.Code;
import com.ybxt.vaccinesserver.entity.MessageResult;
import com.ybxt.vaccinesserver.entity.PersonData;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import com.ybxt.vaccinesserver.service.VaccinesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VaccinesServiceImpl implements VaccinesService {

    @CreateCache(name = "GetVaccinesDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataById;
    @CreateCache(name = "GetVaccinesDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataByPersonID;
    @CreateCache(name = "GetVaccinesDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataByIdentityID;

    @Resource
    private PersonDataClient personDataClient;

    @Resource
    private VaccinesDao vaccinesDao;

    @Override
    public VaccinesData GetVaccinesDataById(String id) {
        VaccinesData vaccinesData;
        String vaccinesDataStr = GetVaccinesDataById.get(Long.parseLong(id));
        if(vaccinesDataStr == null){
            vaccinesData = vaccinesDao.queryVaccinesDataByID(id);
            GetVaccinesDataById.put(Long.parseLong(id), JSON.toJSONString(vaccinesData));
        }else {
            vaccinesData = JSON.parseObject(vaccinesDataStr, VaccinesData.class);
        }
        if(vaccinesData == null){
            return null;
        }
        int ID= vaccinesData.getPerson_id();
        MessageResult m=personDataClient.getPersonDataById(String.valueOf(ID));
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            vaccinesData.setPersonData(personData);
            return vaccinesData;
        }
        return null;
    }

    @Override
    public List<VaccinesData> GetVaccinesDataByPersonID(String person_id) {
        List<VaccinesData> vaccinesDataList;
        String vaccinesDataStr = GetVaccinesDataByPersonID.get(Long.parseLong(person_id));
        if(vaccinesDataStr == null){
            vaccinesDataList = vaccinesDao.queryVaccinesDataByPersonID(person_id);
            GetVaccinesDataByPersonID.put(Long.parseLong(person_id), JSON.toJSONString(vaccinesDataList));
        }else {
            vaccinesDataList = JSON.parseArray(vaccinesDataStr, VaccinesData.class);
        }
        if(vaccinesDataList == null){
            return null;
        }
        MessageResult m=personDataClient.getPersonDataById(person_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            for (VaccinesData vaccinesData : vaccinesDataList) {
                String jsonObject = JSON.toJSONString( m.getData());
                PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
                vaccinesData.setPersonData(personData);
            }
            return vaccinesDataList;
        }
        return null;
    }

    @Override
    public List<VaccinesData> GetVaccinesDataByIdentityID(String identity_id) {
        MessageResult m = personDataClient.getPersonDataByIdCard(identity_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);

            List<VaccinesData> vaccinesDataList;
            String vaccinesDataStr = GetVaccinesDataByIdentityID.get(Long.parseLong(String.valueOf(personData.getId())));
            if(vaccinesDataStr == null){
                vaccinesDataList = vaccinesDao.queryVaccinesDataByPersonID(String.valueOf(personData.getId()));
                GetVaccinesDataByIdentityID.put(Long.parseLong(identity_id), JSON.toJSONString(vaccinesDataList));
            }else {
                vaccinesDataList = JSON.parseArray(vaccinesDataStr, VaccinesData.class);
            }
            if(vaccinesDataList == null){
                return null;
            }
            for (VaccinesData vaccinesData : vaccinesDataList) {
                vaccinesData.setPersonData(personData);
            }
            return vaccinesDataList;
        }
        return null;
    }
}
