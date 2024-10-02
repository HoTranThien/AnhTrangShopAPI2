package api.anhtrangapiv2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.CollectionDTO;
import api.anhtrangapiv2.models.Collection;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.S3Storage.S3StorageService;
import api.anhtrangapiv2.service.collection.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import api.anhtrangapiv2.service.redis.RedisService;

@Controller
@RequestMapping("${api.prefix}/collection")
@RequiredArgsConstructor
public class CollectionController {
    
    private S3StorageService s3StorageService;

    private final CollectionService collectionService;
    private final RedisService redisService;

    
    @GetMapping(path = "/getall")
    public ResponseEntity<Object> getall(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.getAllCollection())
        .build());
    }
    @GetMapping(path = "/getonewithproducts/{id}")
    public ResponseEntity<Object> getOneWithProducts(
        @PathVariable int id,
        @RequestParam(defaultValue = "0", required= false) int page,
        @RequestParam(defaultValue = "10", required= false) int limit
        ) throws Exception{
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());
                String key = redisService.getKey("Collection",id, pageRequest);
        ProductListResponse productListResponse = redisService.findAllProducts(key);
        if(productListResponse == null){
            productListResponse = collectionService.getOneWithProducts(id,pageRequest);
            redisService.saveAllProducts(key, productListResponse);
        }
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productListResponse)
        .build());
    }

    @GetMapping(path = "/getone/{id}")
    public ResponseEntity<Object> getOne(@PathVariable int id) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.getOne(id))
        .build());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> create(@ModelAttribute("collection") @Valid CollectionDTO collectionDTO,
    @RequestParam("file") @Valid MultipartFile file) throws RuntimeException{
        Collection result = collectionService.createCollection(file, collectionDTO);
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(result)
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.deleteCollection(id)).build());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> upadte(@PathVariable int id,
    @ModelAttribute CollectionDTO co, @RequestParam(required = false) MultipartFile file) throws Exception{
        redisService.clear();
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(collectionService.updateCollection(id, co, file)).build());
    }
}
