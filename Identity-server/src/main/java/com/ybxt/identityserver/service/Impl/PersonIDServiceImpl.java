package com.ybxt.identityserver.service.Impl;

import com.ybxt.identityserver.dao.PersonIDDao;
import com.ybxt.identityserver.entity.PersonData;
import com.ybxt.identityserver.service.PersonIDService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonIDServiceImpl implements PersonIDService {

    @Resource
    private PersonIDDao personIDDao;

    @Override
    public String GetName(String person_id) {
        return personIDDao.selectNameByPersonID(person_id);
    }

    @Override
    public String GetGender(String person_id) {
        return personIDDao.selectGenderByPersonID(person_id);
    }

    @Override
    public String GetPhone(String person_id) {
        return personIDDao.selectPhoneNumberByPersonID(person_id);
    }

    @Override
    public PersonData GetPersonData(String person_id) {
        return personIDDao.selectPersonDataByPersonID(person_id);
    }
}