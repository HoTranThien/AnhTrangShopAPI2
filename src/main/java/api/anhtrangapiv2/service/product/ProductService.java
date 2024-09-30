package api.anhtrangapiv2.service.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.javafaker.Faker;

import api.anhtrangapiv2.dtos.ProductDTO;
import api.anhtrangapiv2.models.ChildrenCategory;
import api.anhtrangapiv2.models.Collection;
import api.anhtrangapiv2.models.Color;
import api.anhtrangapiv2.models.ImgProduct;
import api.anhtrangapiv2.models.ParentCategory;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.models.ProductColor;
import api.anhtrangapiv2.models.ProductSize;
import api.anhtrangapiv2.models.Size;
import api.anhtrangapiv2.repositories.ChildrenCategoryRepository;
import api.anhtrangapiv2.repositories.CollectionRepository;
import api.anhtrangapiv2.repositories.ColorRepository;
import api.anhtrangapiv2.repositories.ImgProductRepository;
import api.anhtrangapiv2.repositories.OrderRepository;
import api.anhtrangapiv2.repositories.ParentCategoryRepository;
import api.anhtrangapiv2.repositories.ProductColorRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.repositories.ProductSizeRepository;
import api.anhtrangapiv2.repositories.SizeRepository;
import api.anhtrangapiv2.responses.ChildrenCategoryResponse;
import api.anhtrangapiv2.responses.CollectionResponse;
import api.anhtrangapiv2.responses.ColorResponse;
import api.anhtrangapiv2.responses.ImgProductResponse;
import api.anhtrangapiv2.responses.ParentCategoryResponse;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ProductResponse;
import api.anhtrangapiv2.responses.SizeResponse;
import api.anhtrangapiv2.service.S3Storage.S3StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductServie{

    private final S3StorageService s3StorageService;

    private final ProductRepository productRepository;

    private final CollectionRepository collectionRepository;

    private final ParentCategoryRepository parentCategoryRepository;

    private final ChildrenCategoryRepository childrenCategoryRepository;

    private final SizeRepository sizeRepository;

    private final ColorRepository colorRepository;

    private final ProductColorRepository productColorRepository;

    private final ProductSizeRepository productSizeRepository;

    private final OrderRepository orderRepository;

    private final ImgProductRepository imgProductRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO pro, MultipartFile[] files) throws Exception {
        Collection existingCollection = collectionRepository.findById(pro.getCollectionId())
        .orElseThrow(()-> new RuntimeException("The collection doesn't exist"));
        ParentCategory existingParentCategory = parentCategoryRepository.findById(pro.getParentCategoryId())
        .orElseThrow(()-> new RuntimeException("The parent category doesn't exist"));
        ChildrenCategory existingChildrenCategory = childrenCategoryRepository.findById(pro.getChildrenCategoryId())
        .orElseThrow(()-> new RuntimeException("The children category doesn't exist"));
        if(existingChildrenCategory.getParentCategory()!=existingParentCategory){
            throw new RuntimeException("The children category does't belong to the parent category");
        }

        Product newProduct = Product.builder()
        .collection(existingCollection)
        .parentCategory(existingParentCategory)
        .childrenCategory(existingChildrenCategory)
        .name(pro.getName())
        .cost(pro.getCost())
        .sale_cost(pro.getSale_cost())
        .description(pro.getDescription())
        .quantity(pro.getQuantity())
        .isnew(pro.isIsnew())
        .build();
        newProduct = productRepository.save(newProduct);

        for(int sizeid:pro.getSizeIds()){
            Size existingSize = sizeRepository.findById(sizeid)
            .orElseThrow(()-> new RuntimeException("The size doesn't exist"));
            productSizeRepository.save(ProductSize.builder()
            .product(newProduct)
            .size(existingSize).build());
        }
        for(int colorid:pro.getColorIds()){
            Color existingColor = colorRepository.findById(colorid)
            .orElseThrow(()-> new RuntimeException("The color doesn't exist"));
            productColorRepository.save(ProductColor.builder()
            .product(newProduct)
            .color(existingColor).build());
        }

        for(MultipartFile file : files){
            String url = s3StorageService.uploadFile(file);
            imgProductRepository.save(ImgProduct.builder()
            .product(newProduct)
            .link(url)
            .build());
        }
        return newProduct;
    }

    public String fake(){
        // Faker faker = new Faker();
        // for(int i = 0; i < 200; i++){
        //     String name = faker.commerce().productName();
        //     if(productRepository.existsByName(name)){
        //         continue;
        //     }
        //     // Collection existingCollection = collectionRepository.findById(faker.number().numberBetween(1, 3))
        //     // .orElseThrow(()-> new RuntimeException("The collection doesn't exist"));
        //     Collection existingCollection = collectionRepository.findById(3)
        //     .orElseThrow(()-> new RuntimeException("The collection doesn't exist"));
        //     ChildrenCategory existingChildrenCategory = childrenCategoryRepository.findById(faker.number().numberBetween(9, 12))
        //     .orElseThrow(()-> new RuntimeException("The children category doesn't exist"));
        //     ParentCategory existingParentCategory = parentCategoryRepository.findById(existingChildrenCategory.getParentCategory().getId())
        //     .orElseThrow(()-> new RuntimeException("The parent category doesn't exist"));
        //     long cost = faker.number().numberBetween(50000, 3000000);
        //     long sale_cost = faker.random().nextBoolean()?cost - cost*faker.number().numberBetween(8,50)/100:0;

        //     Product newProduct = Product.builder()
        //     .collection(existingCollection)
        //     .parentCategory(existingParentCategory)
        //     .childrenCategory(existingChildrenCategory)
        //     .name(name)
        //     .cost(cost)
        //     .sale_cost(sale_cost)
        //     .description(faker.lorem().sentence())
        //     .quantity(faker.number().numberBetween(10, 20000))
        //     .isnew(faker.random().nextBoolean())
        //     .build();
        //     newProduct = productRepository.save(newProduct);

        //     List<Integer> allSizes = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //     List<Integer> allColors = Arrays.asList(new Integer[]{1, 2, 3, 4, 5, 6});
        //     List<String> allImgs = Arrays.asList(
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/77+1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/77+2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/77+3.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/ao+thun+2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/ao+thun+3.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/ao+thun.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/b+1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/b2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/j1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/j2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/j3.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/k1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/k2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/k3.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/k6.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/m4+1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/m4+2.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/m4+3.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/n1.jpg",
        //         "https://anhtrangbucket.s3.ap-southeast-2.amazonaws.com/n2.jpg"
        //     );

        //     List<Integer> collectedSizes = RandomCollect(allSizes,faker.number().numberBetween(1, allSizes.size()));
        //     List<Integer> collectedColors = RandomCollect(allColors,faker.number().numberBetween(1, allSizes.size()));
        //     List<String> collectedImgs = RandomCollect(allImgs,3);


        //     for(int sizeid:collectedSizes){
        //         Size existingSize = sizeRepository.findById(sizeid)
        //         .orElseThrow(()-> new RuntimeException("The size doesn't exist"));
        //         productSizeRepository.save(ProductSize.builder()
        //         .product(newProduct)
        //         .size(existingSize).build());
        //     }
        //     for(int colorid:collectedColors){
        //         Color existingColor = colorRepository.findById(colorid)
        //         .orElseThrow(()-> new RuntimeException("The color doesn't exist"));
        //         productColorRepository.save(ProductColor.builder()
        //         .product(newProduct)
        //         .color(existingColor).build());
        //     }
    
        //     for(String url : collectedImgs){
        //         imgProductRepository.save(ImgProduct.builder()
        //         .product(newProduct)
        //         .link(url)
        //         .build());
        //     }
        // }
        return "Complete!!!";
    }
    private <T> List<T> RandomCollect(List<T> arr, int number){
        Faker faker = new Faker();
        List<T> result = new ArrayList<>();
        while(result.size() < number){
            T item = arr.get(faker.random().nextInt(arr.size()));
            if(!result.contains(item)) result.add(item);
        }
        return result;
    }
    
    @Override
    @Transactional
    public String deleteProduct(int id) {
        Product existingProduct = findProductById(id);
        
        productRepository.delete(existingProduct);
        return "Delete success";
    }
    
    @Override
    public ProductResponse findOneProductById(int id) {
        Product existingProduct = findProductById(id);

        List<SizeResponse> productSize = existingProduct.getProductSize().stream()
            .map(ps -> SizeResponse.builder()
            .id(ps.getSize().getId())
            .name(ps.getSize().getName())
            .build()).toList();

        List<ColorResponse> productColor = existingProduct.getProductColor().stream()
            .map(pc -> ColorResponse.builder()
            .id(pc.getColor().getId())
            .name(pc.getColor().getName())
            .code(pc.getColor().getCode())
            .build()).toList();

        ParentCategoryResponse parentCategory = ParentCategoryResponse.builder()
            .id(existingProduct.getParentCategory().getId())
            .name(existingProduct.getParentCategory().getName()).build();

        ChildrenCategoryResponse childrenCategory = ChildrenCategoryResponse.builder()
            .id(existingProduct.getChildrenCategory().getId())
            .name(existingProduct.getChildrenCategory().getName()).build();

        CollectionResponse collection = CollectionResponse.builder()
            .id(existingProduct.getCollection().getId())
            .name(existingProduct.getCollection().getName())
            .build();

        List<ImgProductResponse> imgProduct = existingProduct.getImgProduct().stream()
            .map(ip -> ImgProductResponse.builder()
            .id(ip.getId())
            .link(ip.getLink()).build())
            .toList();
        ProductResponse productResponse = ProductResponse.builder()
        .id(existingProduct.getId())
        .name(existingProduct.getName())
        .cost(existingProduct.getCost())
        .sale_cost(existingProduct.getSale_cost())
        .description(existingProduct.getDescription())
        .quantity(existingProduct.getQuantity())
        .isnew(existingProduct.isIsnew())
        .productColor(productColor)
        .productSize(productSize)
        .collection(collection)
        .imgProduct(imgProduct)
        .parentCategory(parentCategory)
        .childrenCategory(childrenCategory)
        .build();
        return productResponse;
    }

    @Override
    public ProductListResponse findByQuery(String key,PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findByQuery(key,pageRequest);
        List<ProductResponse> productResponses = convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    public ProductListResponse findNewProducts(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findByIsnew(pageRequest);
        List<ProductResponse> productResponses = convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    public ProductListResponse findSaleProducts(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findBySale_cost(pageRequest);
        List<ProductResponse> productResponses = convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id).orElseThrow(
            ()-> new RuntimeException("Product not found with id: " + id));
    }

    // public Product findProductById2(int id) {
    //     return productRepository.findProductById2(id);
    // }
    // public Product findProductById3(int id) {
    //     return productRepository.findProductById3(id);
    // }
    public List<ProductResponse> convertToProductResponse(List<Product> products){
        return products.stream()
        .map(p -> {
            List<SizeResponse> productSize = p.getProductSize().stream()
            .map(ps -> SizeResponse.builder()
            .id(ps.getSize().getId())
            .name(ps.getSize().getName())
            .build()).toList();

            List<ColorResponse> productColor = p.getProductColor().stream()
            .map(pc -> ColorResponse.builder()
            .id(pc.getColor().getId())
            .name(pc.getColor().getName())
            .code(pc.getColor().getCode())
            .build()).toList();

            ParentCategoryResponse parentCategory = ParentCategoryResponse.builder()
            .id(p.getParentCategory().getId())
            .name(p.getParentCategory().getName()).build();

            ChildrenCategoryResponse childrenCategory = ChildrenCategoryResponse.builder()
            .id(p.getChildrenCategory().getId())
            .name(p.getChildrenCategory().getName()).build();

            CollectionResponse collection = CollectionResponse.builder()
            .id(p.getCollection().getId())
            .name(p.getCollection().getName())
            .build();

            List<ImgProductResponse> imgProduct = p.getImgProduct().stream()
            .map(ip -> ImgProductResponse.builder()
            .id(ip.getId())
            .link(ip.getLink()).build())
            .toList();

            return ProductResponse.builder()
            .id(p.getId())
            .name(p.getName())
            .cost(p.getCost())
            .sale_cost(p.getSale_cost())
            .description(p.getDescription())
            .quantity(p.getQuantity())
            .isnew(p.isIsnew())
            .collection(collection)
            .productColor(productColor)
            .productSize(productSize)
            .imgProduct(imgProduct)
            .parentCategory(parentCategory)
            .childrenCategory(childrenCategory)
            .build();
        })
        .toList();
    }
    @Override
    public ProductListResponse findAllProducts(PageRequest pageRequest) {
        Page<Product> productPage = productRepository.findAll(pageRequest);
        List<ProductResponse> productResponses = convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(int id, ProductDTO pro, MultipartFile[] files) throws Exception {
        Product existingProduct = findProductById(id);
        ParentCategory existingParentCategory = parentCategoryRepository.findById(pro.getParentCategoryId())
        .orElseThrow(()-> new RuntimeException("Parent category not found"));

        ChildrenCategory existingChildrenCategory = childrenCategoryRepository.findById(pro.getChildrenCategoryId())
        .orElseThrow(()-> new RuntimeException("Children category not found"));

        if(existingChildrenCategory.getParentCategory()!=existingParentCategory){
            throw new RuntimeException("The children category does't belong to the parent category");
        }

        Collection existingCollection = collectionRepository.findById(pro.getCollectionId())
        .orElseThrow(()-> new RuntimeException("Collection not found"));

        existingProduct.setName(pro.getName());
        existingProduct.setCost(pro.getCost());
        existingProduct.setSale_cost(pro.getSale_cost());
        existingProduct.setDescription(pro.getDescription());
        existingProduct.setQuantity(pro.getQuantity());
        existingProduct.setIsnew(pro.isIsnew());
        existingProduct.setChildrenCategory(existingChildrenCategory);
        existingProduct.setParentCategory(existingParentCategory);
        existingProduct.setCollection(existingCollection);
        
 
        List<ProductSize> existingProductSizes = productSizeRepository.findByProductId(id);
        List<ProductSize> setProductSize = new ArrayList<>();
        for(ProductSize ps:existingProductSizes){
            if(!pro.getSizeIds().contains(ps.getSize().getId())){
                productSizeRepository.delete(ps);
            }
            else{
                setProductSize.add(ps);
            }
        }
        List<Integer> existingSize = existingProductSizes.stream()
        .map(ps -> ps.getSize().getId()).toList();

        for(int sizeId:pro.getSizeIds()){
            if(!existingSize.contains(sizeId)){
                Size size = sizeRepository.findById(sizeId).orElseThrow(()-> new RuntimeException("Size isn't existent"));
                ProductSize newProductSize = ProductSize.builder()
                .product(existingProduct).size(size).build();
                productSizeRepository.save(newProductSize);
                setProductSize.add(newProductSize);
            }
        }
        existingProduct.setProductSize(setProductSize);

        List<ProductColor> existingProductColors = productColorRepository.findByProductId(id);
        List<ProductColor> setProductColor = new ArrayList<>();

        for(ProductColor pc:existingProductColors){
            if(!pro.getColorIds().contains(pc.getColor().getId())){
                productColorRepository.delete(pc);
            }
            else{
                setProductColor.add(pc);
            }
        }
        List<Integer> existingColor = existingProductColors.stream()
        .map(pc -> pc.getColor().getId()).toList();

        for(int colorId:pro.getColorIds()){
            if(!existingColor.contains(colorId)){
                Color color = colorRepository.findById(colorId).orElseThrow(()-> new RuntimeException("Color isn't existent"));
                ProductColor newProductColor = ProductColor.builder()
                .product(existingProduct).color(color).build();
                productColorRepository.save(newProductColor);
                setProductColor.add(newProductColor);
            }
        }
        existingProduct.setProductColor(setProductColor);

        List<ImgProduct> existingImgProduct = imgProductRepository.findByProductId(id);
        for(Integer imgId:pro.getRemovedImgs()){
            if(imgId>0){
                ImgProduct imgProduct = imgProductRepository.findById(imgId).orElseThrow(
                    () -> new RuntimeException("Can not delete the image"));
                existingImgProduct.remove(imgProduct);
                imgProductRepository.delete(imgProduct);
                s3StorageService.deleteFile(imgProduct.getLink());
            }
        }
        if(files != null){
            for(MultipartFile file : files){
                String url;
                try {
                    url = s3StorageService.uploadFile(file);
                } catch (Exception ex) {
                    throw new RuntimeException("Fail Uploading Image");
                }
                ImgProduct newImgProduct = ImgProduct.builder()
                .product(existingProduct)
                .link(url)
                .build();
                imgProductRepository.save(newImgProduct);
                existingImgProduct.add(newImgProduct);
            }
        }
        existingProduct.setImgProduct(existingImgProduct);
        productRepository.save(existingProduct);
        return findOneProductById(id);
    }

}
