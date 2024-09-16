package api.anhtrangapiv2.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.anhtrangapiv2.models.*;
import api.anhtrangapiv2.models.ProductColor;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.repositories.ProductColorRepository;
import api.anhtrangapiv2.repositories.ColorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductColorService {
    // @Autowired
    // private final ProductColorRepository productColorRepository;
    // @Autowired
    // private final ProductRepository productRepository;
    // @Autowired
    // private final ColorRepository ColorRepository;

    // public ProductColor findById(int id) throws Exception{
    //     ProductColor existingProductColor = productColorRepository.findById(id).orElseThrow(() -> 
    //     new RuntimeException("ProductColor was Not Found "));
    //     return existingProductColor;
    // }
    // public String deleteById(int id) throws Exception{
    //     ProductColor existingProductColor = findById(id);
    //     productColorRepository.delete(existingProductColor);
    //     return "Delete Success";
    // }
    // public ProductColor create(int productId, int colorId) throws Exception{
    //     Product existingProduct = productRepository.findById(productId).orElseThrow(() -> 
    //     new RuntimeException("Product was Not Found "));

    //     Color existingColor = ColorRepository.findById(colorId).orElseThrow(() -> 
    //     new RuntimeException("Color was Not Found "));

    //     if(productColorRepository.existsByProductIdAndColorId(productId, colorId))
    //     throw new RuntimeException("The pair of this product and this Color have been existed!");
    //     else{
    //         ProductColor newProductColor = ProductColor.builder()
    //         .product(existingProduct)
    //         .Color(existingColor)
    //         .build();
    //         productColorRepository.save(newProductColor);
    //     }
    //     return newProductColor;
    // }
}
