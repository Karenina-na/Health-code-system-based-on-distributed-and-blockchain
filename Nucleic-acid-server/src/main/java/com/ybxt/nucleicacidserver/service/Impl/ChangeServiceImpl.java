package com.ybxt.nucleicacidserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.nucleicacidserver.client.PersonDataClient;
import com.ybxt.nucleicacidserver.dao.ChangeDataDao;
import com.ybxt.nucleicacidserver.dao.NucleicAcidDao;
import com.ybxt.nucleicacidserver.entity.Code;
import com.ybxt.nucleicacidserver.entity.MessageResult;
import com.ybxt.nucleicacidserver.entity.NucleicAcidData;
import com.ybxt.nucleicacidserver.entity.PersonData;
import com.ybxt.nucleicacidserver.service.ChangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChangeServiceImpl implements ChangeService {

    @CreateCache(name = "GetNucleicAcidDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataById;
    @CreateCache(name = "GetNucleicAcidDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataByPersonID;
    @CreateCache(name = "GetNucleicAcidDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetNucleicAcidDataByIdentityID;

    @Resource
    NucleicAcidDao nucleicAcidDao;

    @Resource
    private ChangeDataDao changeDataDao;

    @Resource
    private PersonDataClient personDataClient;

    @Override
    public boolean insertNucleicAcidData(NucleicAcidData nucleicAcidData){
        int index=changeDataDao.insertNucleicAcidData(nucleicAcidData);
        return index == 1;
    }

    @Override
    public boolean deleteNucleicAcidData(String id) {
        NucleicAcidData nucleicAcidData =nucleicAcidDao.queryNucleicAcidDataByID(id);
        if (nucleicAcidData==null){
            return false;
        }
        int index=changeDataDao.deleteNucleicAcidData(id);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(nucleicAcidData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetNucleicAcidDataById.remove(Long.parseLong(id));
            GetNucleicAcidDataByPersonID.remove(Long.valueOf(nucleicAcidData.getPerson_id()));
            GetNucleicAcidDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateNucleicAcidData(NucleicAcidData nucleicAcidData) {
        NucleicAcidData oldNucleicAcidData =nucleicAcidDao.queryNucleicAcidDataByID(String.valueOf(nucleicAcidData.getId()));
        if (oldNucleicAcidData==null){
            return false;
        }
        int index=changeDataDao.updateNucleicAcidData(nucleicAcidData);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(nucleicAcidData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetNucleicAcidDataById.remove(Long.valueOf(nucleicAcidData.getId()));
            GetNucleicAcidDataByPersonID.remove(Long.valueOf(nucleicAcidData.getPerson_id()));
            GetNucleicAcidDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }
}