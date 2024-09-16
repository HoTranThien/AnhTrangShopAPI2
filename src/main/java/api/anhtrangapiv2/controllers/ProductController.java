package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.product.ProductService;
import api.anhtrangapiv2.service.product.ProductSizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final ProductSizeService productSizeService;

    @GetMapping(path = "getall")
    ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.findAllProducts()).build());
    }
    
    @GetMapping(path = "getone/{id}")
    ResponseEntity<Object> getOne(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.findOneProductById(id)).build());
    }
    // @GetMapping(path = "getone2/{id}")
    // ResponseEntity<Object> getOne2(@PathVariable int id){
    //     return ResponseEntity.ok(ResponseToClient.builder()
    //     .message("OK")
    //     .status(HttpStatus.OK)
    //     .data(productService.findProductById2(id)).build());
    // }
    // @GetMapping(path = "getone3/{id}")
    // ResponseEntity<Object> getOne3(@PathVariable int id){
    //     return ResponseEntity.ok(ResponseToClient.builder()
    //     .message("OK")
    //     .status(HttpStatus.OK)
    //     .data(productService.findProductById3(id)).build());
    // }

    @PostMapping(path = "create")
    ResponseEntity<Object> create(@ModelAttribute @Valid ProductDTO pro,
                @RequestParam("files") MultipartFile[] files) throws Exception{
                            
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.createProduct(pro, files))
        .build());
    }

    @PutMapping(path = "/update/{id}")
    ResponseEntity<Object> update(@PathVariable int id,@ModelAttribute @Valid ProductDTO pro,
                            @RequestParam(value = "files",required = false) MultipartFile[] files) throws Exception{
        System.out.println("\u001B[32m" + "++++++++" + pro + "\u001B[0m");
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.updateProduct(id, pro, files))
        .build());
    }

    @DeleteMapping(path = "/delete/{id}")
    ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productService.deleteProduct(id)).build());
    }
}
