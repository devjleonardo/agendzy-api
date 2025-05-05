package com.agendzy.api.entrypoint.http.resource.business;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/businesses")
@RequiredArgsConstructor
public class BusinessResource {

//    private final CreateBusinessUseCase createBusinessUseCase;
//
//    @PostMapping
//    public ResponseEntity<Object> createBusinessWithOwner(@RequestBody @Valid CreateBusinessInput input) {
//        return HandlingResponse.execute(createBusinessUseCase.execute(input));
//    }

}
