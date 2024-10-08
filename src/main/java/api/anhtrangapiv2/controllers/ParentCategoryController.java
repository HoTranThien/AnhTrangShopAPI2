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

import api.anhtrangapiv2.dtos.ParentCategoryDTO;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.parent_category.ParentCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import api.anhtrangapiv2.service.redis.RedisService;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/parentcategory")
public class ParentCategoryController {

    private final ParentCategoryService parentCategoryService;
    private final RedisService redisService;

    @GetMapping(path = "/getall")
    public ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(parentCategoryService.getAllParentCategory())
        .build());
    }

    // @GetMapping(path = "/get/{id}")
    // public ResponseEntity<Object> get(@PathVariable int id){
    //     return ResponseEntity.ok(ResponseToClient.builder()
    //     .message("OK")
    //     .status(HttpStatus.OK)
    //     .data(parentCategoryService.find(id))
    //     .build());
    // }
    @PostMapping(path = "/create")
    public ResponseEntity<Object> create(@RequestBody @Valid ParentCategoryDTO pc){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(parentCategoryService.createParentCategory(pc))
        .build());

    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid ParentCategoryDTO pc){
        redisService.clear();
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(parentCategoryService.updateParentCategory(id, pc))
        .build());
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(parentCategoryService.deleteParentCategory(id))
        .build());
    }
}
