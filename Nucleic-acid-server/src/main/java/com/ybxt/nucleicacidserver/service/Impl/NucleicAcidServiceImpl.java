package com.ybxt.nucleicacidserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.nucleicacidserver.client.PersonDataClient;
import com.ybxt.nucleicacidserver.dao.NucleicAcidDao;
import com.ybxt.nucleicacidserver.entity.Code;
import com.ybxt.nucleicacidserver.entity.MessageResult;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import com.ybxt.nucleicacidserver.entity.PersonData;
import com.ybxt.nucleicacidserver.service.NucleicAcidService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NucleicAcidServiceImpl implements NucleicAcidService {

    @CreateCache(name = "GetNucleicAcidDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataById;
    @CreateCache(name = "GetNucleicAcidDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataByPersonID;
    @CreateCache(name = "GetNucleicAcidDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataByIdentityID;

    @Resource
    private NucleicAcidDao nucleicAcidDao;

    @Resource
    private PersonDataClient personDataClient;

    @Override
    public NucleicAcidData GetNucleicAcidDataById(String id) {
        NucleicAcidData nucleicAcidData;
        String data=GetNucleicAcidDataById.get(Long.valueOf(id));
        if (data == null) {
            nucleicAcidData=nucleicAcidDao.queryNucleicAcidDataByID(id);
            GetNucleicAcidDataById.put(Long.valueOf(id), JSON.toJSONString(nucleicAcidData));
        }else{
            nucleicAcidData=JSON.parseObject(data,NucleicAcidData.class);
        }
        if (nucleicAcidData==null){
            return null;
        }
        int ID= nucleicAcidData.getPerson_id();
        MessageResult m=personDataClient.getPersonDataById(String.valueOf(ID));
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            nucleicAcidData.setPersonData(personData);
            return nucleicAcidData;
        }
        return null;
    }

    @Override
    public List<NucleicAcidData> GetNucleicAcidDataByPersonID(String person_id) {
        List<NucleicAcidData> nucleicAcidDataList;
        String data=GetNucleicAcidDataByPersonID.get(Long.valueOf(person_id));
        if (data == null) {
            nucleicAcidDataList=nucleicAcidDao.queryNucleicAcidDataByPersonID(person_id);
            GetNucleicAcidDataByPersonID.put(Long.valueOf(person_id), JSON.toJSONString(nucleicAcidDataList));
        }else{
            nucleicAcidDataList=JSON.parseArray(data,NucleicAcidData.class);
        }
        if (nucleicAcidDataList==null){
            return null;
        }
        MessageResult m=personDataClient.getPersonDataById(person_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            for (NucleicAcidData nucleicAcidData : nucleicAcidDataList) {
                String jsonObject = JSON.toJSONString( m.getData());
                PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
                nucleicAcidData.setPersonData(personData);
            }
            return nucleicAcidDataList;
        }
        return null;
    }

    @Override
    public List<NucleicAcidData> GetNucleicAcidDataByIdentityID(String identity_id) {
        MessageResult m = personDataClient.getPersonDataByIdCard(identity_id);
        if (String.valueOf(Code.OK).equals(m.getCode())) {
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            List<NucleicAcidData> nucleicAcidDataList;
            String data=GetNucleicAcidDataByIdentityID.get(Long.valueOf(identity_id));
            if (data == null) {
                nucleicAcidDataList=nucleicAcidDao.queryNucleicAcidDataByPersonID(String.valueOf(personData.getId()));
                GetNucleicAcidDataByIdentityID.put(Long.valueOf(identity_id), JSON.toJSONString(nucleicAcidDataList));
            }else{
                nucleicAcidDataList=JSON.parseArray(data,NucleicAcidData.class);
            }
            if (nucleicAcidDataList==null){
                return null;
            }
            for (NucleicAcidData nucleicAcidData : nucleicAcidDataList) {
                nucleicAcidData.setPersonData(personData);
            }
            return nucleicAcidDataList;
        }
        return null;
    }
}
