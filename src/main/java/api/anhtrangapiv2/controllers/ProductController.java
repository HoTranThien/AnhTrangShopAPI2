package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.ProductDTO;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/product")
public class ProductController {

    @Autowired
    private final ProductService productServiec;

    @GetMapping(path = "getall")
    ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productServiec.findAllProducts()).build());
    }
    
    @PostMapping(path = "create")
    ResponseEntity<Object> create(@ModelAttribute @Valid ProductDTO pro,
                @RequestParam("files") MultipartFile[] files) throws Exception{
                            
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(productServiec.createProduct(pro, files))
        .build());
    }
}
