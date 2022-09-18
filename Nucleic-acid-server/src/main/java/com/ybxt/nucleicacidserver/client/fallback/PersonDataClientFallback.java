package com.ybxt.nucleicacidserver.client.fallback;


import com.ybxt.nucleicacidserver.client.PersonDataClient;
import com.ybxt.nucleicacidserver.entity.MessageResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonDataClientFallback implements FallbackFactory<PersonDataClient> {
    @Override
    public PersonDataClient create(Throwable cause) {
        return new PersonDataClient() {
            @Override
            public MessageResult getPersonDataById(String id) {
                MessageResult m=new MessageResult();
                m.ErrorMessageResult("sentinel降级");
                return m;
            }

            @Override
            public MessageResult getPersonDataByIdCard(String identity) {
                MessageResult m=new MessageResult();
                m.ErrorMessageResult("sentinel降级");
                return m;
            }
        };
    }
}
