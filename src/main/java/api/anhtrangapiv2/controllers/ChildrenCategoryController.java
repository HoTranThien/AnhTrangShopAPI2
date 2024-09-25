package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;

import api.anhtrangapiv2.dtos.ChildrenCategoryDTO;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.children_category.ChildrenCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/childrencategory")
public class ChildrenCategoryController {

    private final ChildrenCategoryService childrenCategoryService;

    @GetMapping(path = "/getall")
    ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.getAllChildrenCategory())
        .build());
    }

    @GetMapping(path = "/getonewithproducts/{id}")
    ResponseEntity<Object> getOneWithProducts(
            @PathVariable int id,
            @RequestParam(defaultValue = "0", required= false) int page,
            @RequestParam(defaultValue = "10", required= false) int limit
        ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.getOneWithProducts(id,pageRequest))
        .build());
    }

    @GetMapping(path = "/getone/{id}")
    ResponseEntity<Object> getOne(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(childrenCategoryService.getOne(id))
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
