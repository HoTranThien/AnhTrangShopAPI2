package api.anhtrangapiv2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> getAll(){
        return productRepository.findAll();
    }

    public String get(){
        return "get all products";
    }
}
