package api.anhtrangapiv2.service.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import api.anhtrangapiv2.service.S3Storage.S3StorageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductServie{
    @Autowired
    private final S3StorageService s3StorageService;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CollectionRepository collectionRepository;
    @Autowired
    private final ParentCategoryRepository parentCategoryRepository;
    @Autowired
    private final ChildrenCategoryRepository childrenCategoryRepository;
    @Autowired
    private final SizeRepository sizeRepository;
    @Autowired
    private final ColorRepository colorRepository;
    @Autowired
    private final ProductColorRepository productColorRepository;
    @Autowired
    private final ProductSizeRepository productSizeRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ImgProductRepository imgProductRepository;

    // private String name;
    
    // private Long cost;

    // private Long sale_cost;

    // private String description;

    // private int quantity;

    // private boolean isnew;

    // private int collectionId;

    // private int parent_categoryId;

    // private int ChildrenCategoryId;

    // private int sizeId;

    // private int colorId;
    @Override
    @Transactional
    public Product createProduct(ProductDTO pro, MultipartFile[] files) throws Exception {
        Collection existingCollection = collectionRepository.findById(pro.getCollectionId())
        .orElseThrow(()-> new RuntimeException("The collection doesn't exist"));
        ParentCategory existiParentCategory = parentCategoryRepository.findById(pro.getParentCategoryId())
        .orElseThrow(()-> new RuntimeException("The parent category doesn't exist"));
        ChildrenCategory existingChildrenCategory = childrenCategoryRepository.findById(pro.getChildrenCategoryId())
        .orElseThrow(()-> new RuntimeException("The children category doesn't exist"));
        if(existingChildrenCategory.getParentCategory()!=existiParentCategory){
            throw new RuntimeException("The children category does't belong to the parent category");
        }

        Product newProduct = Product.builder()
        .collection(existingCollection)
        .parentCategory(existiParentCategory)
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

    @Override
    @Transactional
    public String deleteProduct(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(
            ()-> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product updateProduct(int id, ProductDTO pro, MultipartFile[] files) {
        // TODO Auto-generated method stub
        return null;
    }

}
