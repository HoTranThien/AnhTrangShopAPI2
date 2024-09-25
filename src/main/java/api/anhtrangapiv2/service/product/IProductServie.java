package api.anhtrangapiv2.service.product;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.ProductDTO;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ProductResponse;

public interface IProductServie {
    ProductListResponse findAllProducts(PageRequest pageRequest);
    ProductListResponse findNewProducts(PageRequest pageRequest);
    ProductListResponse findSaleProducts(PageRequest pageRequest);
    ProductListResponse findByQuery(String query,PageRequest pageRequest);
    ProductResponse findOneProductById(int id);
    Product findProductById(int id);
    Product createProduct(ProductDTO pro, MultipartFile[] files) throws Exception;
    ProductResponse updateProduct(int id,ProductDTO pro, MultipartFile[] files) throws Exception;
    String deleteProduct(int id);
}
