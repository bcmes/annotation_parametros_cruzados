package com.example.demo.controller;

import com.example.demo.annotation.ConsistentParameters;
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

//    @MyAnnotation(Type.TYPE_A)
    @ConsistentParameters
    @PostMapping
    public ResponseEntity<Void> myController1(@RequestBody AnyPayload anyPayload, @RequestParam(required = false) String value1) {
        log.info("Entrou no controller1.");
        return ResponseEntity.noContent().build();
    }

}
