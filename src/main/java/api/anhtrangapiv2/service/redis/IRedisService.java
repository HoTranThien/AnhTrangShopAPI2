package api.anhtrangapiv2.service.redis;

import org.springframework.data.domain.PageRequest;

import api.anhtrangapiv2.responses.ProductListResponse;

public interface IRedisService {
    void clear();
    ProductListResponse findAllProducts(String key);
    void saveAllProducts(String key,ProductListResponse products)throws Exception;
}
