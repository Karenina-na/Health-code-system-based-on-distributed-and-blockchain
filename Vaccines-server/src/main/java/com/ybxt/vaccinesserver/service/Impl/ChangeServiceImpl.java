package com.ybxt.vaccinesserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.vaccinesserver.client.PersonDataClient;
import com.ybxt.vaccinesserver.dao.ChangeDataDao;
import com.ybxt.vaccinesserver.dao.VaccinesDao;
import com.ybxt.vaccinesserver.entity.Code;
import com.ybxt.vaccinesserver.entity.MessageResult;
import com.ybxt.vaccinesserver.entity.PersonData;
import com.ybxt.vaccinesserver.entity.VaccinesData;
import com.ybxt.vaccinesserver.service.ChangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChangeServiceImpl implements ChangeService {

    @CreateCache(name = "GetVaccinesDataById", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataById;
    @CreateCache(name = "GetVaccinesDataByPersonID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataByPersonID;
    @CreateCache(name = "GetVaccinesDataByIdentityID", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetVaccinesDataByIdentityID;

    @Resource
    private VaccinesDao vaccinesDao;

    @Resource
    private ChangeDataDao changeDataDao;

    @Resource
    private PersonDataClient personDataClient;

    @Override
    public boolean insertVaccinesData(VaccinesData VaccinesData){
        int index=changeDataDao.insertVaccinesData(VaccinesData);
        return index == 1;
    }

    @Override
    public boolean deleteVaccinesData(String id) {
        VaccinesData VaccinesData =vaccinesDao.queryVaccinesDataByID(id);
        if (VaccinesData==null){
            return false;
        }
        int index=changeDataDao.deleteVaccinesData(id);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(VaccinesData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetVaccinesDataById.remove(Long.parseLong(id));
            GetVaccinesDataByPersonID.remove(Long.valueOf(VaccinesData.getPerson_id()));
            GetVaccinesDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateVaccinesData(VaccinesData VaccinesData) {
        VaccinesData oldVaccinesData =vaccinesDao.queryVaccinesDataByID(String.valueOf(VaccinesData.getId()));
        if (oldVaccinesData==null){
            return false;
        }
        int index=changeDataDao.updateVaccinesData(VaccinesData);
        if (index==0){
            return false;
        }
        MessageResult m = personDataClient.getPersonDataById(String.valueOf(VaccinesData.getPerson_id()));
        if (String.valueOf(Code.OK).equals(m.getCode())){
            String jsonObject = JSON.toJSONString( m.getData());
            PersonData personData = JSONObject.parseObject(jsonObject, PersonData.class);
            GetVaccinesDataById.remove(Long.valueOf(VaccinesData.getId()));
            GetVaccinesDataByPersonID.remove(Long.valueOf(VaccinesData.getPerson_id()));
            GetVaccinesDataByIdentityID.remove(Long.valueOf(personData.getIdentity()));
            return true;
        }
        return false;
    }
}