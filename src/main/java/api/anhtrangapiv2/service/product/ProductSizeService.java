package api.anhtrangapiv2.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.models.*;
import api.anhtrangapiv2.repositories.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSizeService {
    // @Autowired
    // private final ProductSizeRepository productSizeRepository;
    // @Autowired
    // private final ProductRepository productRepository;
    // @Autowired
    // private final SizeRepository sizeRepository;

    // public ProductSize findById(int id) throws Exception{
    //     ProductSize existingProductSize = productSizeRepository.findById(id).orElseThrow(() -> 
    //     new RuntimeException("ProductSize was Not Found "));
    //     return existingProductSize;
    // }
    // public String deleteById(int id) throws Exception{
    //     ProductSize existingProductSize = findById(id);
    //     productSizeRepository.delete(existingProductSize);
    //     return "Delete Success";
    // }
    // public ProductSize create(int productId, int sizeId) throws Exception{
    //     Product existingProduct = productRepository.findById(productId).orElseThrow(() -> 
    //     new RuntimeException("Product was Not Found "));

    //     Size existingSize = sizeRepository.findById(sizeId).orElseThrow(() -> 
    //     new RuntimeException("Size was Not Found "));

    //     if(productSizeRepository.existsByProductIdAndSizeId(productId, sizeId))
    //     throw new RuntimeException("The pair of this product and this size have been existed!");
    //     else{
    //         ProductSize newProductSize = ProductSize.builder()
    //         .product(existingProduct)
    //         .size(existingSize)
    //         .build();
    //         productSizeRepository.save(newProductSize);
    //     }
    //     return newProductSize;
    // }
}
