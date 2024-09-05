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

import api.anhtrangapiv2.dto.ChildrenCategoryDTO;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.children_category.ChildrenCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/childrencategory")
public class ChildrenCategoryController {
    @Autowired
    private final ChildrenCategoryService childrenCategoryService;

    @GetMapping(path = "/getall")
    ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.getAllChildrenCategory())
        .build());
    }

    @PostMapping(path = "/create")
    ResponseEntity<Object> create(@RequestBody @Valid ChildrenCategoryDTO cc){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.createChildrentCategory(cc))
        .build());
    }

    @PutMapping(path = "/update/{id}")
    ResponseEntity<Object> update(@PathVariable int id, @RequestBody @Valid ChildrenCategoryDTO cc){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.updateChildrentCategory(id, cc))
        .build());
    }

    @DeleteMapping(path = "delete/{id}")
    ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.deleteChildrenCategory(id))
        .build());
    }
}
