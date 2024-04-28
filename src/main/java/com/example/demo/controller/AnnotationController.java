package com.example.demo.controller;

import com.example.demo.annotation.crossparameter.PermissionControl;
import com.example.demo.annotation.crossparameter.TypePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/annotation")
@Validated
public class AnnotationController {

    private final Logger log = LoggerFactory.getLogger(AnnotationController.class);

    @PermissionControl(value = TypePermission.ONBOARDING, typeClass = AnnotationController.class, methodName = "myController1", parameterNameValidation = "onboardingRequest")
    @PostMapping
    public ResponseEntity<Void> myController1(@RequestBody OnboardingRequest onboardingRequest, @RequestParam(required = false) String value1) {
        log.info("method=myController1.");
        return ResponseEntity.noContent().build();
    }

//    @PermissionControl(value = TypePermission.ACCOUNT_ID, typeClass = AnnotationController.class, methodName = "myController2", parameterNameValidation = "accountId")
    @PermissionControl(typeClass = AnnotationController.class, methodName = "myController2", parameterNameValidation = "accountId")
    @GetMapping
    public ResponseEntity<Void> myController2(@RequestParam(required = false) String page, @RequestParam(required = false) String accountId) {
        log.info("method=myController2");
        return ResponseEntity.noContent().build();
    }

}
