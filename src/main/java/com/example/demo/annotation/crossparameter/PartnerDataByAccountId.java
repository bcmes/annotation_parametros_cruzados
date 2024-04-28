package com.example.demo.annotation.crossparameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PartnerDataByAccountId implements PartnerDataBy<String> {

    private final Logger log = LoggerFactory.getLogger(PartnerDataByAccountId.class);

    @Override
    public PartnerData get(String param) {
        log.info("method=get, message=dado usado para buscar dados dos parceiros [{}]", param);
        //faz e acontece....
        return new PartnerData("integrationCnpj123", "automationCnpj456");
    }
}
