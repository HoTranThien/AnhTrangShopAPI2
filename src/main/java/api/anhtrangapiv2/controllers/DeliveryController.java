package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import api.anhtrangapiv2.dto.DeliveryDTO;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.delivery.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {
    @Autowired
    private final DeliveryService deliveryService;

    @GetMapping(path = "/getall")
    ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(deliveryService.getAllDelivery()).build());
    }

    @PostMapping(path = "/create")
    ResponseEntity<Object> create(@RequestBody @Valid DeliveryDTO d){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(deliveryService.createDelivery(d)).build());
    }

    @PutMapping(path = "/update/{id}")
    ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid DeliveryDTO d){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(deliveryService.updateDelivery(id, d))
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(deliveryService.deletDelivery(id)).build());
    }
}
