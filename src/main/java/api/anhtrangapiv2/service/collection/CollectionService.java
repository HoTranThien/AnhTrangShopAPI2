package api.anhtrangapiv2.service.collection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.responses.CollectionResponse;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ProductResponse;
import api.anhtrangapiv2.dtos.CollectionDTO;
import api.anhtrangapiv2.models.Collection;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.repositories.CollectionRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.service.S3Storage.S3StorageService;
import api.anhtrangapiv2.service.product.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionService implements ICollectionService{

    private final CollectionRepository collectionRepository;

    private final S3StorageService s3StorageService;

    private final ProductRepository productRepository;

    private final ProductService productService;

    @Override
    @Transactional
    public Collection createCollection(MultipartFile img,CollectionDTO co) throws RuntimeException {
        if(collectionRepository.existsByName(co.getName())){
            throw new RuntimeException("Collection already exists with name: " + co.getName());
        }
        String imgUrl;
        try {
            imgUrl = s3StorageService.uploadFile(img);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image: " + e.getMessage());
        }
        Collection collection = Collection.builder()
        .name(co.getName())
        .img(imgUrl)
        .build();
        return collectionRepository.save(collection);
    }

    @Override
    @Transactional
    public String deleteCollection(int id) {
        Collection existingCollection = getCollectionById(id);
        if(productRepository.existsByCollectionId(id)){
            throw new RuntimeException("Cannot delete this collection because of associated products");
        }
        s3StorageService.deleteFile(existingCollection.getImg());
        collectionRepository.deleteById(id);
        return "Delete success";
    }

    @Override
    public List<CollectionResponse> getAllCollection() {
        List<CollectionResponse> collectionResponses = collectionRepository.findAll().stream()
        .map(c -> CollectionResponse.builder()
        .id(c.getId())
        .name(c.getName())
        .img(c.getImg())
        .build()).toList();
        return collectionResponses;
    }
    
    @Override
    public CollectionResponse getOne(int id) {
        Collection existingCollection = getCollectionById(id);
        CollectionResponse collectionResponse = CollectionResponse.builder()
        .id(existingCollection.getId())
        .name(existingCollection.getName())
        .img(existingCollection.getImg())
        .build();
        return collectionResponse;
    }

    @Override
    public ProductListResponse getOneWithProducts(int id,PageRequest pageRequest) throws Exception{
        if(!productRepository.existsByCollectionId(id)){
            return ProductListResponse.builder()
            .products(null)
            .total(0)
            .build();
        }

        Page<Product> productPage = productRepository.findByCollectionId(id, pageRequest);
        List<ProductResponse> productResponses = productService.convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    public Collection getCollectionById(int id) {
        return collectionRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Collection not found with id: " + id));
    }

    @Override
    @Transactional
    public Collection updateCollection(int id, CollectionDTO co, MultipartFile file) throws Exception{
        Collection existingCollection = getCollectionById(id);
        if(collectionRepository.existsByName(co.getName())&& !existingCollection.getName().equals(co.getName())){
            throw new RuntimeException("The Collection name already exists");
        }
        else{
            if (file != null){
                s3StorageService.deleteFile(existingCollection.getImg());
                String newImg = s3StorageService.uploadFile(file);
                existingCollection.setImg(newImg);
            }
            if(co.getName() != null){
                existingCollection.setName(co.getName());
            }
            return collectionRepository.save(existingCollection);
        }
    }

}
