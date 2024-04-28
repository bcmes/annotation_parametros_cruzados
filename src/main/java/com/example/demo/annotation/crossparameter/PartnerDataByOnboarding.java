package com.example.demo.annotation.crossparameter;

import com.example.demo.controller.OnboardingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PartnerDataByOnboarding implements PartnerDataBy<OnboardingRequest> {

    private final Logger log = LoggerFactory.getLogger(PartnerDataByOnboarding.class);

    @Override
    public PartnerData get(OnboardingRequest param) {
        log.info("method=get, message=dado usado para buscar dados dos parceiros [{}]", param);
        return new PartnerData(param.getIntegrationCnpj(), param.getAutomationCnpj());
    }
}
