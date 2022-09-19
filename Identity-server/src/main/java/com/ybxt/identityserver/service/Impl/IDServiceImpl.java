package com.ybxt.identityserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.identityserver.dao.IDDao;
import com.alicp.jetcache.Cache;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.IDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IDServiceImpl implements IDService {
    @CreateCache(name = "GerPersonNameByIDCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonNameByIDCache;
    @CreateCache(name = "GetPersonGenderByIDCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonGenderByIDCache;
    @CreateCache(name = "GetPersonIdentityByIDCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonIdentityByIDCache;
    @CreateCache(name = "GetPersonPhoneByIDCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonPhoneByIDCache;
    @CreateCache(name = "GerPersonDataByIDCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonDataByIDCache;

    @Resource
    private IDDao idDao;

    @Override
    public String GetName(String id) {
        String name=GerPersonNameByIDCache.get(Long.valueOf(id));
        if(name==null){
            name = idDao.selectNameByID(id);
            GerPersonNameByIDCache.put(Long.valueOf(id),name);
        }
        return name;
    }

    @Override
    public String GetGender(String id) {
        String gender=GetPersonGenderByIDCache.get(Long.valueOf(id));
        if(gender==null){
            gender = idDao.selectGenderByID(id);
            GetPersonGenderByIDCache.put(Long.valueOf(id),gender);
        }
        return gender;
    }

    @Override
    public String GetPersonID(String id) {
        String identity= GetPersonIdentityByIDCache.get(Long.valueOf(id));
        if(identity==null){
            identity = idDao.selectPersonIDByID(id);
            GetPersonIdentityByIDCache.put(Long.valueOf(id),identity);
        }
        return identity;
    }

    @Override
    public String GetPhone(String id) {
        String phone= GetPersonPhoneByIDCache.get(Long.valueOf(id));
        if(phone==null){
            phone = idDao.selectPhoneNumberByID(id);
            GetPersonPhoneByIDCache.put(Long.valueOf(id),phone);
        }
        return phone;
    }

    @Override
    public PersonData GetPersonData(String person_id) {
        String s= GerPersonDataByIDCache.get(Long.valueOf(person_id));
        if(s==null){
            PersonData personData = idDao.selectPersonDataById(person_id);
            GerPersonDataByIDCache.put(Long.valueOf(person_id), JSON.toJSONString(personData));
            return personData;
        }
        return JSON.parseObject(s, PersonData.class);
    }
}