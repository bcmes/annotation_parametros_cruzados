package com.example.demo.adapter;

import com.example.demo.port.TokenInformation;
import org.springframework.stereotype.Component;

@Component
public class TokenInformationAdapter implements TokenInformation {
    @Override
    public String get(String accessToken) {
        return "11111111111111";
    }
}
