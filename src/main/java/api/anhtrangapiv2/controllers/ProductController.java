package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api/v1/product")
public class ProductController {

    @Autowired
    private final ProductService productServiec;
    @GetMapping(path="/get")
    public @ResponseBody ResponseEntity<String> get(){
        return ResponseEntity.ok(productServiec.get());
    }
    @GetMapping(path="/getall")
    public Iterable<Product> getall(){
        return productServiec.getAll();
    }
    @GetMapping(path = "/getone/{id}")
    public ResponseEntity<String> getone(@PathVariable int id){
        return ResponseEntity.ok("adsad = "+id);
    }
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        return ResponseEntity.ok("1123");
    }
}
