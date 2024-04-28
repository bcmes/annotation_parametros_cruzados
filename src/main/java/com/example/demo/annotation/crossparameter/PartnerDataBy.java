package com.example.demo.annotation.crossparameter;

import java.util.List;

public interface PartnerDataBy<T> {

    PartnerData get(T param);

    static PartnerDataBy select(List<PartnerDataBy> partnerDataBy, TypePermission typePermission) {
        return switch (typePermission) {
            case ONBOARDING -> partnerDataBy.stream().filter(e->e instanceof PartnerDataByOnboarding).findFirst().get();
            case ACCOUNT_ID -> partnerDataBy.stream().filter(e->e instanceof PartnerDataByAccountId).findFirst().get();
            case NONE -> null;
        };
    }
}
