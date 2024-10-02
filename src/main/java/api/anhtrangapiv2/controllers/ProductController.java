package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.ProductDTO;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.product.ProductService;
import api.anhtrangapiv2.service.redis.RedisService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="${api.prefix}/product")
public class ProductController {

    private final ProductService productService;
    private final RedisService redisService;

    @GetMapping(path = "getall")
    ResponseEntity<Object> getAll(
        @RequestParam(defaultValue = "0", required= false) int page,
        @RequestParam(defaultValue = "10", required= false) int limit
    ) throws Exception{
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());

        String key = redisService.getKey("All_Products", pageRequest);
        ProductListResponse productListResponse = redisService.findAllProducts(key);
        if(productListResponse == null){
            productListResponse = productService.findAllProducts(pageRequest);
            redisService.saveAllProducts(key, productListResponse);
        }
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productListResponse).build());
    }
    
    @GetMapping(path = "getone/{id}")
    ResponseEntity<Object> getOne(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.findOneProductById(id)).build());
    }

    // @GetMapping(path = "testredis")
    // ResponseEntity<Object> test(){
    //     return ResponseEntity.ok(ResponseToClient.builder()
    //     .message("OK")
    //     .status(HttpStatus.OK)
    //     .data(redisService.test()).build());
    // }

    @GetMapping(path = "new")
    ResponseEntity<Object> getNewProducts(
        @RequestParam(defaultValue = "0", required= false) int page,
        @RequestParam(defaultValue = "10", required= false) int limit
    ) throws Exception{
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());
        String key = redisService.getKey("New", pageRequest);
        ProductListResponse productListResponse = redisService.findAllProducts(key);
        if(productListResponse == null){
            productListResponse = productService.findNewProducts(pageRequest);
            redisService.saveAllProducts(key, productListResponse);
        }
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productListResponse).build());
    }

    @GetMapping(path = "sale")
    ResponseEntity<Object> getSaleProducts(
        @RequestParam(defaultValue = "0", required= false) int page,
        @RequestParam(defaultValue = "10", required= false) int limit
    ) throws Exception{
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());
        String key = redisService.getKey("Sale", pageRequest);

        ProductListResponse productListResponse = redisService.findAllProducts(key);
        if(productListResponse == null){
            productListResponse = productService.findSaleProducts(pageRequest);
            redisService.saveAllProducts(key, productListResponse);
        }
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productListResponse).build());
    }

    @GetMapping(path = "search/{key}")
    ResponseEntity<Object> getProductsByQuery(
        @PathVariable String key,
        @RequestParam(defaultValue = "0", required= false) int page,
        @RequestParam(defaultValue = "10", required= false) int limit
        ){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("name").ascending());
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.findByQuery(key,pageRequest)).build());
    }

    @PostMapping(path = "create")
    ResponseEntity<Object> create(@ModelAttribute @Valid ProductDTO pro,
                @RequestParam("files") MultipartFile[] files) throws Exception{
        redisService.clear();             
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.createProduct(pro, files))
        .build());
    }
    @PostMapping(path = "fake")
    ResponseEntity<Object> createFaker() throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.fake())
        .build());
    }
    @PutMapping(path = "/update/{id}")
    ResponseEntity<Object> update(@PathVariable int id,@ModelAttribute @Valid ProductDTO pro,
                            @RequestParam(value = "files",required = false) MultipartFile[] files) throws Exception{
        redisService.clear(); 
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.updateProduct(id, pro, files))
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<Object> delete(@PathVariable int id){
        redisService.clear(); 
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.deleteProduct(id)).build());
    }
}
