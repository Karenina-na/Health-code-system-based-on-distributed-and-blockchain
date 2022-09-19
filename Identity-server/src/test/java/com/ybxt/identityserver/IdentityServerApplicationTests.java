package com.ybxt.identityserver;

import com.ybxt.identityserver.service.IDService;
import com.ybxt.identityserver.service.IdentityService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class IdentityServerApplicationTests {

    @Resource
    private IDService idService;

    @Resource
    private IdentityService personService;

    @Test
    void IDServiceTest(){
        String id="1";
        System.out.println(idService.GetName(id));
        System.out.println(idService.GetGender(id));
        System.out.println(idService.GetPhone(id));
        System.out.println(idService.GetPersonID(id));
        System.out.println(idService.GetPersonData(id));
    }

    @Test
    void PersonIDServiceTest(){
        String PersonID="111111111111111111";
        System.out.println(personService.GetName(PersonID));
        System.out.println(personService.GetGender(PersonID));
        System.out.println(personService.GetPhone(PersonID));
        System.out.println(personService.GetPersonData(PersonID));
    }
}
