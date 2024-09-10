package api.anhtrangapiv2.service.product;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.ProductDTO;
import api.anhtrangapiv2.models.Product;

public interface IProductServie {
    List<Product> findAllProducts();
    Product getProductById(int id);
    Product createProduct(ProductDTO pro, MultipartFile[] files) throws Exception;
    Product updateProduct(int id,ProductDTO pro, MultipartFile[] files);
    String deleteProduct(int id);
}
