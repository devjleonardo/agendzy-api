package com.agendzy.api.entrypoint.http.resource;

import lombok.AllArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class DefaultResource {

    private final BuildProperties buildProperties;

    @GetMapping
    public ResponseEntity<Object> buildInfo() {
        return ResponseEntity.ok(buildProperties);
    }

}
