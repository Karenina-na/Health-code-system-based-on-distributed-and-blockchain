package com.ybxt.identityserver.service.Impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.ybxt.identityserver.dao.ChangeDataDao;
import com.ybxt.identityserver.dao.IDDao;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.ChangeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ChangeServiceImpl  implements ChangeService {

    @CreateCache(name = "GerPersonNameByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonNameByIdentityCache;
    @CreateCache(name = "GetPersonGenderByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonGenderByIdentityCache;
    @CreateCache(name = "GetPersonPhoneByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GetPersonPhoneByIdentityCache;
    @CreateCache(name = "GerPersonDataByIdentityCache", expire = 3600, cacheType = CacheType.REMOTE)
    private Cache<Long,String> GerPersonDataByIdentityCache;
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
    private ChangeDataDao changeDataDao;

    @Resource
    private IDDao idDao;

    @Override
    public boolean insertPersonData(PersonData personData) {
        int index = changeDataDao.insertPersonData(personData);
        return index == 1;
    }

    @Override
    public boolean deletePersonData(String id) {
        String identity = idDao.selectIdentityByID(id);
        int index = changeDataDao.deletePersonData(id);
        if (index==1){
            GerPersonNameByIdentityCache.remove(Long.valueOf(identity));
            GetPersonGenderByIdentityCache.remove(Long.valueOf(identity));
            GetPersonPhoneByIdentityCache.remove(Long.valueOf(identity));
            GerPersonDataByIdentityCache.remove(Long.valueOf(identity));
            GerPersonNameByIDCache.remove(Long.valueOf(id));
            GetPersonGenderByIDCache.remove(Long.valueOf(id));
            GetPersonIdentityByIDCache.remove(Long.valueOf(id));
            GetPersonPhoneByIDCache.remove(Long.valueOf(id));
            GerPersonDataByIDCache.remove(Long.valueOf(id));
            return true;
        }
        return false;

    }

    @Override
    public boolean updatePersonData(PersonData personData) {
        PersonData OldPersonData = idDao.selectPersonDataById(String.valueOf(personData.getId()));
        if (personData.getId()==null || !personData.getIdentity().equals(OldPersonData.getIdentity())){
            return false;
        }
        int index = changeDataDao.updatePersonData(personData);
        if (index==1){
            if (personData.getName()!=null){
                GerPersonNameByIdentityCache.remove(Long.valueOf(personData.getIdentity()));
                GerPersonNameByIDCache.remove(Long.valueOf(personData.getId()));
            }
            if (personData.getGender()!=null){
                GetPersonGenderByIdentityCache.remove(Long.valueOf(personData.getIdentity()));
                GetPersonGenderByIDCache.remove(Long.valueOf(personData.getId()));
            }
            if (personData.getPhone()!=null){
                GetPersonPhoneByIdentityCache.remove(Long.valueOf(personData.getIdentity()));
                GetPersonPhoneByIDCache.remove(Long.valueOf(personData.getId()));
            }
            GerPersonDataByIdentityCache.remove(Long.valueOf(personData.getIdentity()));
            GerPersonDataByIDCache.remove(Long.valueOf(personData.getId()));
            return true;
        }
        return false;
    }
}
