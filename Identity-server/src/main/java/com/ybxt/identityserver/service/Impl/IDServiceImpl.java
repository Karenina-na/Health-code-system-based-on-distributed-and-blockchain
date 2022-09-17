package com.ybxt.identityserver.service.Impl;

import com.ybxt.identityserver.dao.IDDao;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.IDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IDServiceImpl implements IDService {

    @Resource
    private IDDao idDao;

    @Override
    public String GetName(String id) {
        return idDao.selectNameByID(id);
    }

    @Override
    public String GetGender(String id) {
        return idDao.selectGenderByID(id);
    }

    @Override
    public String GetPersonID(String id) {
        return idDao.selectPersonIDByID(id);
    }

    @Override
    public String GetPhone(String id) {
        return idDao.selectPhoneNumberByID(id);
    }

    @Override
    public PersonData GetPersonData(String person_id) {
        return idDao.selectPersonDataById(person_id);
    }
}