package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import api.anhtrangapiv2.dtos.*;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create")
    ResponseEntity<Object> create(@RequestBody @Valid OrderDTO order) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.creatOrder(order)).build());
    }
    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<Object> delete(@PathVariable int id) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.deleteOrder(id)).build());
    }
    @GetMapping(path = "/getall")
    ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.findAllOrders()).build());
    }
    @GetMapping(path = "/getone/{id}")
    ResponseEntity<Object> getOne(@PathVariable int id) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.findOneOrder(id)).build());
    }
    @PutMapping(path = "/update/{id}")
        ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid UpdatedOrderDTO order) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.updateOrder(id, order))
        .build());
    }
    @GetMapping(path = "/status")
    ResponseEntity<Object> getStatus(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(orderService.getOrderStatusColumnType()).build());
    }
}
