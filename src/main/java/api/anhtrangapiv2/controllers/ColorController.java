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

import api.anhtrangapiv2.dtos.ColorDTO;
import api.anhtrangapiv2.models.Color;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.color.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import api.anhtrangapiv2.service.redis.RedisService;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/color")
public class ColorController {

    private final ColorService colorService;
    private final RedisService redisService;

    @GetMapping(path = "/getall")
    public ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(colorService.getAllColors())
        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> create( @RequestBody @Valid ColorDTO color){
        Color newColor = colorService.createColor(color);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(newColor)
        .build());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid ColorDTO color){
        Color updatedColor = colorService.updateColor(id, color);
        redisService.clear();
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(updatedColor)
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        colorService.deleteColor(id);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data("Delete successfull")
        .build());
    }

}
