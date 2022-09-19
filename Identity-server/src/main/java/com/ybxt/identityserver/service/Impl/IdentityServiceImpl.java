package com.ybxt.identityserver.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.identityserver.dao.IdentityDAO;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.IdentityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IdentityServiceImpl implements IdentityService {

    @CreateCache(name = "GerPersonNameByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonNameByIdentityCache;
    @CreateCache(name = "GetPersonGenderByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonGenderByIdentityCache;
    @CreateCache(name = "GetPersonPhoneByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonPhoneByIdentityCache;
    @CreateCache(name = "GerPersonDataByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonDataByIdentityCache;

    @Resource
    private IdentityDAO personIDDao;

    @Override
    public String GetName(String person_id) {
        String name=GerPersonNameByIdentityCache.get(Long.valueOf(person_id));
        if(name==null){
            name = personIDDao.selectNameByPersonID(person_id);
            GerPersonNameByIdentityCache.put(Long.valueOf(person_id),name);
        }
        return name;
    }

    @Override
    public String GetGender(String person_id) {
        String gender=GetPersonGenderByIdentityCache.get(Long.valueOf(person_id));
        if(gender==null){
            gender = personIDDao.selectGenderByPersonID(person_id);
            GetPersonGenderByIdentityCache.put(Long.valueOf(person_id),gender);
        }
        return gender;
    }

    @Override
    public String GetPhone(String person_id) {
        String phone=GetPersonPhoneByIdentityCache.get(Long.valueOf(person_id));
        if(phone==null){
            phone = personIDDao.selectPhoneNumberByPersonID(person_id);
            GetPersonPhoneByIdentityCache.put(Long.valueOf(person_id),phone);
        }
        return phone;
    }

    @Override
    public PersonData GetPersonData(String person_id) {
        String s= GerPersonDataByIdentityCache.get(Long.valueOf(person_id));
        if(s==null){
            PersonData personData = personIDDao.selectPersonDataByPersonID(person_id);
            GerPersonDataByIdentityCache.put(Long.valueOf(person_id), JSON.toJSONString(personData));
            return personData;
        }
        return JSON.parseObject(s, PersonData.class);
    }
}