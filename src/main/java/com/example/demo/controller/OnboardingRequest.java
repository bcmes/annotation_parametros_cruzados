package com.example.demo.controller;

public class OnboardingRequest {

    private String automationCnpj;
    private String integrationCnpj;

    public String getAutomationCnpj() {
        return automationCnpj;
    }

    public void setAutomationCnpj(String automationCnpj) {
        this.automationCnpj = automationCnpj;
    }

    public String getIntegrationCnpj() {
        return integrationCnpj;
    }

    public void setIntegrationCnpj(String integrationCnpj) {
        this.integrationCnpj = integrationCnpj;
    }

    @Override
    public String toString() {
        return "OnboardingRequest{" +
                "automationCnpj='" + automationCnpj + '\'' +
                ", integrationCnpj='" + integrationCnpj + '\'' +
                '}';
    }
}
